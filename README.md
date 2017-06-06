# RestServer Readme

__NOTE: Following is the manual steps to quick setup the rest server dev environment, the readme will be update to get the Jersey jars with Maven in build scripts__

## How to deploy the OCManager REST API into the tomcat

1. Clone the code form the git
```
git clone git@github.com:OCManager/RestServer.git
```

2. Install the tomcat into your dev environemnt

3. Create the folders under the __<TOMCAT_HOME>/webapps__
```
mkdir -p <TOMCAT_HOME>/webapps/ocmanager/WEB-INF
mkdir <TOMCAT_HOME>/webapps/ocmanager/WEB-INF/classes
mkdir <TOMCAT_HOME>/webapps/ocmanager/WEB-INF/lib
```

4. Compile the code under src folder, the copy the whole package into the __<TOMCAT_HOME>/webapps/ocmanager/WEB-INF/classes__ folder

5. Go the the web https://jersey.github.io/download.html
to download the Jersey jars, then copy the them into the __<TOMCAT_HOME>/webapps/ocmanager/WEB-INF/lib__ folder

6. Copy the __WebContent/WEB-INF/web.xml__ into the __<TOMCAT_HOME>/webapps/ocmanager/WEB-INF__ folder

7. Then restart the tomcat server

8. Access the __http://<your tomcat server>:<port>/ocmanager/v1/api/tenant__ with __POST__ request, you will get the __Hello World!__



## How to deploy the OCManager persistence

__pre-request:__ you need install the mysql DB and run the database/mysql/initOCManager.sql to create the database. And insert some data into the tables

1. Go to the link: https://github.com/mybatis/mybatis-3/releases to download 3.4.4 jars

2. Then copy the them into the __<TOMCAT_HOME>/webapps/ocmanager/WEB-INF/lib__ folder

3. Because in the __OCManager REST API deployment step 4__ we already copy the class files, here not need to deploy the persistence code

4. Then restart the tomcat server

5. Then Access __http://<your tomcat server>:<port>/ocmanager/v1/api/tenant/<tenant id>__ you can see the data
