Automobile Spare Parts Ecommerce
================================

Technology:
- JSP + Servlet MVC
- MySQL
- Maven WAR
- JSTL

Setup:
1. Start the local database after every PC restart:
   sh scripts/start-local-database.sh

2. Configure database credentials if needed:
   DB_URL=jdbc:mysql://localhost:3306/auto_spare_parts_db?useSSL=false&serverTimezone=UTC
   DB_USER=root
   DB_PASSWORD=your_password

3. In Eclipse:
   Import -> Existing Maven Projects -> select this project
   Right click project -> Maven -> Update Project

4. Add Tomcat 9 in Eclipse:
   Window -> Show View -> Servers
   New Server -> Apache -> Tomcat v9.0 Server

5. Run:
   Right click project -> Run As -> Run on Server

Build manually if needed:
   mvn clean package

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
- Registration, login, salted SHA-256 password hashing, session handling, auth filter, remember-me cookie
- Profile image upload and product image upload
- Admin product CRUD
- User product catalog, cart, checkout, and order history
