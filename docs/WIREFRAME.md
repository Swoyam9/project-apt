# Wireframe And Prototype Notes

## Public/Login Flow

```mermaid
flowchart TD
    A["Landing/Home"] --> B["Product Catalog"]
    A --> C["Login"]
    C --> D["User Home"]
    C --> E["Admin Dashboard"]
    C --> F["Register"]
```

## User Screens

```mermaid
flowchart LR
    A["Header: brand, products, cart, orders, logout"] --> B["Product grid"]
    B --> C["Add to cart"]
    C --> D["Cart table"]
    D --> E["Checkout form"]
    E --> F["Order history"]
```

## Admin Screens

```mermaid
flowchart LR
    A["Admin dashboard stats"] --> B["Products table"]
    B --> C["Add product form"]
    B --> D["Edit product form"]
    B --> E["Delete product action"]
```

Prototype implementation:
- Shared navigation in `src/main/webapp/components/header.jsp`.
- Responsive layout and visual styling in `src/main/webapp/css/style.css`.
- Customer catalog/cart/order pages in `WEB-INF/views/user`.
- Admin product management pages in `WEB-INF/views/admin`.
