# RestServer Readme (will update)

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

6. Then restart the tomcat server

7. Access the __http://<your tomcat server>:<port>/ocmanager/v1/api/tenant__ with __POST__ request, you will get the __Hello World!__