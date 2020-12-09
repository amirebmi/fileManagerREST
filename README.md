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
1. List all top-level files and folders --> http://localhost:8080 GET
2. List all files and folders in an existing folder --> http://localhost:8080/folders/{folderId} GET
3. Get a folder's parent folder's name --> http://localhost:8080/folders/parentName/{folderId} GET
4. Get a folder's parent folder's id --> http://localhost:8080/folders/parentId/{folderId} GET
5. Create a new top-level folder --> http://localhost:8080/folders POST
6. Create a new folder in an existing folder --> http://localhost:8080/folders POST
7. Upload a file to the top level (i.e. no parent folder) --> http://localhost:8080/files POST
8. Upload a file to an existing folder - no file in the folder with the same name --> http://localhost:8080/folders/{folderId}/files POST
9. Download a specific version of a file --> http://localhost:8080/files/{fileId}/versions/{versionNumber} GET
10. Download a file without specifying a version --> http://localhost:8080/files/{fileId} GET
11. Delete a file --> http://localhost:8080/files/{fileId} DELETE
12. Delete a folder --> http://localhost:8080/folders/{folderId} DELETE


# How to install
1. Clone this repository 
https://github.com/amirebmi/onlineFileManagerREST.git
2. Create a file under **src/main/resources** and name it *application.properties*
3. Copy the following lines and update with your own database information: 

```java
spring.datasource.url=jdbc:mysql://YOUR_DATABASE_URL/YOUR_DATABASE_NAME?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.servlet.multipart.enabled=true
```

# Future Versions and Goals
- This application will be developed as a web-application in the next few versions.
- To access to the front-end design, which is done using ReactJs, please go [to this repository](https://github.com/amirebmi/onlineFileManager-react-spa).
