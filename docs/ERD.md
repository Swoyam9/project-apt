# ERD

```mermaid
erDiagram
    USERS ||--o{ ORDERS : places
    CATEGORIES ||--o{ PRODUCTS : contains
    ORDERS ||--o{ ORDER_ITEMS : has
    PRODUCTS ||--o{ ORDER_ITEMS : appears_in

    USERS {
        int id PK
        varchar full_name
        varchar email UK
        varchar phone
        varchar address
        varchar password_hash
        varchar password_salt
        enum role
        varchar profile_image
        varchar remember_token
        datetime remember_token_expiry
        timestamp created_at
    }

    CATEGORIES {
        int id PK
        varchar name UK
        varchar description
    }

    PRODUCTS {
        int id PK
        int category_id FK
        varchar name
        varchar brand
        varchar part_number UK
        text description
        decimal price
        int stock_quantity
        varchar image_path
        timestamp created_at
        timestamp updated_at
    }

    ORDERS {
        int id PK
        int user_id FK
        decimal total_amount
        enum status
        varchar shipping_address
        timestamp order_date
    }

    ORDER_ITEMS {
        int id PK
        int order_id FK
        int product_id FK
        int quantity
        decimal unit_price
    }
```

Normalization summary:
- First normal form: columns store atomic values and each table has a primary key.
- Second normal form: non-key attributes depend on the whole primary key; many-to-many order product details are separated into `order_items`.
- Third normal form: no transitive dependency such as category name inside `products`; category details live only in `categories`.
