# LogicMonitor_MiniURL
LogicMonitor MiniURL REST Service 


Step 1 : Build Instructions 

1. First you need to create a database.  Run the miniurl.sql script in MySQL workbench.
2. Open the MiniURL folder in any Java IDE which has Java EE Edition. 
3. Its a maven Project please run "Maven Build". Once the build is successful please follow the below mentione steps
4. Change the Database URL, user and password in the MiniURLDao. MiniURLDao path : /MiniURL/src/main/java/com/logicmonitor/miniurl/dao/MiniURLDao.java
   Please give your SQL miniurl database URL, user & password. 
	private static final String dbUrl = "";
	private static final String dbUser = ""; 
	private static final String dbPassword = "";
5. Once again run "Maven Build". Once the build is successful please follow the below mentione steps
6. MiniURL folder consists of the entire project including the Front End UI. Deploy the entire project(MiniURL folder) on Apache Tomcat Server.
7. If you are using any IDE which is Java EE compliant then you can add servers to the project and deploy the project using the IDE.


Step 2. Run Instructions inorder to test the web service.

1. Once the project is built successfully, open the following url in any browser http://localhost:8080/MiniURL/
2. Please check the portnumber in the URL, it may vary based on the port number on which the server is hosted.
3. If your running in an IDE the URL will automatically be opened as the home page.
4. There are 3 buttons to test the Web Service which inturn has 2 main text fields. One is the short URL & other is the Long URL. The Long URL will only accept maximum 2083 characters. Server side validations to handle invalid input are already in place.
5. The JSP page to test the service is in the same project folder /MiniURL/src/main/webapp/index.jsp.


Step 3. Pros & Cons of the Design are mentioned in the PDF document - LogicMonitor MiniURL HackerRank Submission






