# Running on local
After you've cloned the repository to your local, run the following steps:
Right click on the project's root directory (HPMS)
1. Run As > Maven clean
2. Run As > Maven install
3. Maven > Update project
4. Run As > Run on Server

# Hibernate setup
Connecting to the database
1. Ensure XAMPP is running on your local
2. Create a database called hpms on your local
3. Create the related tables in the database
Note that the current URL in HibernateConfig.java is set to //localhost:3307, do update it according to your local port number.

# Layout
layout.jsp is used for the main layout throughout the site/
You may refer to the implementation in UserController - addUserForm function.
