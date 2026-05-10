Automobile Spare Parts Ecommerce
================================

Technology:
- JSP + Servlet MVC
- MySQL
- Maven WAR
- JSTL

Setup:
1. Start XAMPP MySQL.

2. In phpMyAdmin, import the database if tables are missing:
   src/main/resources/xampp_phpmyadmin_restore.sql

   If you are creating the database for the first time, this file will create
   auto_spare_parts_db and add the users, categories, products, orders, and
   order_items tables.

3. Configure database credentials if needed:
   DB_URL=jdbc:mysql://localhost:3306/auto_spare_parts_db?useSSL=false&serverTimezone=UTC
   DB_USER=root
   DB_PASSWORD=your_password

4. In Eclipse:
   Import -> Existing Maven Projects -> select this project
   Right click project -> Maven -> Update Project

5. Add Tomcat 9 in Eclipse:
   Window -> Show View -> Servers
   New Server -> Apache -> Tomcat v9.0 Server

6. Run:
   Right click project -> Run As -> Run on Server

If login shows "MySQL JDBC driver not found":
   Right click project -> Refresh
   Right click project -> Maven -> Update Project
   Clean and restart the Tomcat server in Eclipse

Build manually if needed:
   mvn clean package

Optional project-local database:
   sh scripts/start-local-database.sh

Stop local database:
   sh scripts/stop-local-database.sh

Default admin:
- Email: admin@autospares.com
- Password: admin123

MVC packages:
- com.ecommerce.controller: Servlet controllers
- com.ecommerce.dao: JDBC database access
- com.ecommerce.models: Entity/model classes
- com.ecommerce.utils: DB, password, and validation utilities
- com.ecommerce.filter: Authentication and role filter
- WEB-INF/views: JSP screens

Coursework coverage:
- UI prototype in JSP/CSS
- Wireframe/prototype notes in docs/WIREFRAME.md
- 3NF schema in database.sql and ERD in docs/ERD.md
- Extra XAMPP table script in src/main/resources/xampp_required_tables.sql
- phpMyAdmin restore script in src/main/resources/xampp_phpmyadmin_restore.sql
- Registration, login, salted SHA-256 password hashing, session handling, auth filter, remember-me cookie
- Profile image upload and product image upload
- Admin product CRUD
- User product catalog, cart, checkout, and order history
