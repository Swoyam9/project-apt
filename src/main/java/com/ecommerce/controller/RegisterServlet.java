package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.PasswordUtils;
import com.ecommerce.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/register")
@MultipartConfig(maxFileSize = 1024 * 1024 * 3)
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (ValidationUtils.isBlank(fullName) || !ValidationUtils.isValidEmail(email) ||
                !ValidationUtils.isValidPhone(phone) || !ValidationUtils.isStrongEnoughPassword(password) ||
                !password.equals(confirmPassword)) {
            request.setAttribute("error", "Please complete the form correctly. Password must be at least 6 characters.");
            doGet(request, response);
            return;
        }

        try {
            if (userDAO.findByEmail(email) != null) {
                request.setAttribute("error", "An account with this email already exists.");
                doGet(request, response);
                return;
            }

            String image = saveFile(request.getPart("profileImage"), "/uploads/profiles", "default-profile.png");
            String salt = PasswordUtils.generateSalt();
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setPasswordSalt(salt);
            user.setPasswordHash(PasswordUtils.hashPassword(password, salt));
            user.setRole("USER");
            user.setProfileImage(image);

            userDAO.register(user);
            request.setAttribute("success", "Registration successful. Please log in.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Registration failed", e);
        }
    }

    private String saveFile(Part part, String folder, String fallback) throws IOException {
        if (part == null || part.getSize() == 0) {
            return fallback;
        }
        String submitted = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String extension = submitted.contains(".") ? submitted.substring(submitted.lastIndexOf(".")) : "";
        String fileName = System.currentTimeMillis() + extension;
        String realPath = getServletContext().getRealPath(folder);
        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        part.write(new File(uploadDir, fileName).getAbsolutePath());
        return fileName;
    }
}
