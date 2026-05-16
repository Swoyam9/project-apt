package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;
import com.ecommerce.utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/admin/products/edit")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class UpdateProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.findById(id);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("product", product);
            request.setAttribute("categories", productDAO.findCategories());
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-product.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not load product", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = buildProduct(request);
            if (!isValid(product)) {
                request.setAttribute("error", "Please enter valid product details.");
                request.setAttribute("product", product);
                request.setAttribute("categories", productDAO.findCategories());
                request.getRequestDispatcher("/WEB-INF/views/admin/edit-product.jsp").forward(request, response);
                return;
            }
            Part image = request.getPart("image");
            product.setImagePath(image != null && image.getSize() > 0 ? saveFile(image) : request.getParameter("existingImage"));
            productDAO.update(product);
            request.getSession().setAttribute("success", "Product updated successfully.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not update product", e);
        }
    }

    private Product buildProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter("id")));
        product.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        product.setName(request.getParameter("name"));
        product.setBrand(request.getParameter("brand"));
        product.setPartNumber(request.getParameter("partNumber"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        return product;
    }

    private boolean isValid(Product product) {
        return product.getId() > 0 && product.getCategoryId() > 0 && !ValidationUtils.isBlank(product.getName()) &&
                !ValidationUtils.isBlank(product.getBrand()) && !ValidationUtils.isBlank(product.getPartNumber()) &&
                ValidationUtils.isPositivePrice(product.getPrice()) && ValidationUtils.isNonNegative(product.getStockQuantity());
    }

    private String saveFile(Part part) throws IOException {
        String submitted = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String extension = submitted.contains(".") ? submitted.substring(submitted.lastIndexOf(".")) : "";
        String fileName = System.currentTimeMillis() + extension;
        File uploadDir = new File(getServletContext().getRealPath("/uploads/products"));
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        part.write(new File(uploadDir, fileName).getAbsolutePath());
        return fileName;
    }
}
