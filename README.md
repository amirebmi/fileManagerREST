#### Online File Manager (REST API) - Version 1.2


# Introduction

###### Online File Manager is an open-source Java web application that enables users to virtually manage their files and folders.


# Tools
- MAVEN 
- JPA/Hibernate
- Spring Boot Framework
- MySQL Database


# Features
- Create parent folder(s) and sub-folders
- Navigate through folders and sub-folder
- *[1] Delete a folder 
- *[2] Upload file(s)
- *[3] Delete a file 
- Download file(s) 
- Download a specific version of a file

###### *[1] Deleting a folder that includes files and nested sub-folder(s) will delete all its subfolder(s).
###### *[2] If a file with the same name already exists in a folder, a new version of the file will be created
###### *[3] All versions of a file will be deleted.

## Notes: 
- All of the functionalities can be tested using Postman. 
- The input and output are designed to be in JSON format.
- URL patterns are listed as below:
1. List all top-level files and folders --> **localhost:8080** GET
2. List all files and folders in an existing folder --> **localhost:8080/folders/{folderId}** GET
3. Create a new top-level folder --> **localhost:8080/folders** POST
4. Create a new folder in an existing folder --> **localhost:8080/folders** POST
5. Upload a file to the top level (i.e. no parent folder) --> **localhost:8080/files** POST
6. Upload a file to an existing folder - no file in the folder with the same name --> **localhost:8080/folders/{folderId}/files** POST
7. Download a specific version of a file --> **localhost:8080/files/{fileId}/versions/{versionNumber}** GET
8. Download a file without specifying a version --> **localhost:8080/files/{fileId}** GET
9. Delete a file --> **localhost:8080/files/{fileId}** DELETE
10. Delete a folder --> **localhost:8080/folders/{folderId}** DELETE


# How to install
1. Clone this repository => https://github.com/amirebmi/onlineFileManagerREST.git
2. Create a file under **src/main/resources** and name it *application.properties*
3. Copy the following lines and update with your own database information: 
- **spring.datasource.url=jdbc:mysql://YOUR_DATABASE_URL?allowPublicKeyRetrieval=true&useSSL=false**
- **spring.datasource.username=YOUR_DATABASE_USERNAME**
- **spring.datasource.password=YOUR_DATABASE_PASSWORD**
- **spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver**
- **spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect** (If you use different a version of MySQL, you need to specify it)
- **spring.jpa.generate-ddl=true**
- **spring.servlet.multipart.enabled=true**

# Future Versions and Goals
- This application will be developed as a web-application in the next few versions.
- In the next version user interface and security features will be implemented.
