package onlineFileManager.sbrest.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.FileFolderDTO;
import onlineFileManager.sbrest.model.FileVersion;
import onlineFileManager.sbrest.model.Folder;
import onlineFileManager.sbrest.model.FolderDto;
import onlineFileManager.sbrest.model.dao.FileDao;
import onlineFileManager.sbrest.model.dao.FileRepository;
import onlineFileManager.sbrest.model.dao.FileVersionRepository;
import onlineFileManager.sbrest.model.dao.FolderDao;

@CrossOrigin
@RestController
public class FoldersController {

	@Autowired
	private FolderDao folderDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	FileRepository fileRepository;

	@Autowired
	FileVersionRepository fileVersionRepository;

	// Get root directory that includes root folders & files
	@GetMapping
	public List<FileFolderDTO> getRoot() {

		List<Folder> folders = folderDao.getRoot(); // Get root folders
		List<File> files = fileDao.getRoot(); // Get root files

		// A list that merges files and folders to be displayed
		List<FileFolderDTO> dtos = new ArrayList<FileFolderDTO>();

		for (Folder folder : folders) {
			FileFolderDTO dto = new FileFolderDTO();
			dto.setId(folder.getId());
			dto.setName(folder.getName());
			dto.setFolder(true);
			dtos.add(dto);
		}
		for (File file : files) {
			FileFolderDTO dto = new FileFolderDTO();
			dto.setId(file.getId());
			dto.setName(file.getName());
			dto.setType(file.getType());
			dto.setSize(file.getSize());
			dto.setFolder(false);
			dtos.add(dto);
		}
		return dtos;
	}

	// Get sub-folders & files
	@GetMapping("/folders/{id}")
	public List<FileFolderDTO> getSubFolders(@PathVariable Integer id) {

		List<Folder> folders = folderDao.getSubFolders(id);
		List<File> files = fileDao.getFiles(id);

		// if there's no folder and file in the directory
		if (folders.size() == 0 && files.size() == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No files and folders");
		}

		// List to merge files and folders
		List<FileFolderDTO> dtos = new ArrayList<FileFolderDTO>();

		// Create folder(s) object -- will be merged to files object
		for (Folder folder : folders) {
			FileFolderDTO dto = new FileFolderDTO();
			dto.setId(folder.getId());
			dto.setName(folder.getName());
			//dto.setParentFolderId(folder.getParentFolder().getId());
			dto.setParentFolderId(folder.getParentFolder().getId());
			dto.setFolder(true);
			dtos.add(dto);
		}
		// Create file(s) object -- will be merged to folders object
		for (File file : files) {
			FileFolderDTO dto = new FileFolderDTO();
			dto.setId(file.getId());
			dto.setName(file.getName());
			//dto.setParentFolderId(file.getFolder().getId());
			dto.setParentFolderId(file.getFolder().getParentFolder().getId());
			dto.setType(file.getType());
			dto.setSize(file.getSize());
			dto.setFolder(false);
			dtos.add(dto);
		}
		return dtos; // return an object that has both files and folders
	}

	// Create a new folder
	@PostMapping("/folders")
	@ResponseStatus(HttpStatus.CREATED)
	public FolderDto createFolder(@RequestBody FolderDto dto) {

		// Create a new Folder object
		Folder folder = new Folder();

		// Set the needed properties
		folder.setName(dto.getName());
		folder.setDateCreated(new Date());

		// If folder has a parent, assign the parent to the created folder
		if (dto.getParentFolderId() != null) {
			Folder parent = folderDao.getFolder(dto.getParentFolderId());
			folder.setParentFolder(parent);
		}

		// Create the folder in the database
		folder = folderDao.createFolder(folder);

		return new FolderDto(folder);
	}

	// Delete folder and its files
	@DeleteMapping("/folders/{id}")
	public String deleteFolder(@PathVariable Integer id) {

		// Get the folder object that's going to be deleted
		Folder folder = folderDao.getFolder(id);

		// Get the total number of folders that are inside of the to-be-deleted folder
		long totalSubFolders = folderDao.getTotalSubFolders(folder);

		// Get the total number of files that are going to be deleted
		long totalFiles = fileDao.getTotalDeletedFiles(folder);

		// Delete folder(s) and (file(s); if there's any)
		folderDao.deleteFolder(folder);

		// Return the number of deleted folder(s) and file(s)
		return (totalSubFolders + 1) + " folder(s) deleted.\n" + (totalFiles) + " file(s) deleted.";
	}

	// Upload file on the root level (top-level)
	@PostMapping("/files")
	public String uploadRoot(@RequestParam("file") MultipartFile file) {

		File fileInDatabase = fileDao.getFile(file.getOriginalFilename());

		try {
			// If this file is being uploaded for the first time in the folder
			if (fileInDatabase == null) {

				// Create the first version of the file in the File entity
				File newFile = new File(file.getOriginalFilename(), new Date(), file.getContentType(), file.getSize(),
						false, 1);

				// Save the file record into database
				File uploadedFile = fileVersionRepository.save(newFile);

				// Now, create a new version in the database
				FileVersion newVersion = new FileVersion(new Date(), file.getBytes(), file.getSize(), 1, uploadedFile);

				// Upload the new version
				fileRepository.save(newVersion);

			} else {
				// If the file has the same name as the file that has already been uploaded in
				// the database

				// Increase the file version in Files AND update the file's properties with the
				// new file's properties

				// Get the current file's version number from database
				Integer currentVersionNumber = fileInDatabase.getCurrentVersion();

				// Increase the version number
				fileInDatabase.setCurrentVersion(++currentVersionNumber);

				// Update the size
				fileInDatabase.setSize(file.getSize());

				// Update the date
				fileInDatabase.setDateUploaded(new Date());

				// Update the database with the new file's objects properties
				fileDao.saveChanges(fileInDatabase);

				// VERSION CONTROL
				// Create a new version of the file
				FileVersion newVersion = new FileVersion(new Date(), file.getBytes(), file.getSize(),
						currentVersionNumber, fileInDatabase);

				// Upload the new version
				fileRepository.save(newVersion);

			}

			return file.getOriginalFilename() + " uploaded successfully!";
		} catch (Exception e) {
			return "Failed to upload " + file.getOriginalFilename();
		}
	}

	// Upload in a folder
	@PostMapping("/folders/{id}/files")
	public String uploadNonRoot(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {

		// Get the parentFolder directory to upload the file inside it
		Folder parentFolder = folderDao.getFolder(id); // Get the folder object

		// If the specified directory that's been found using the id DOES NOT exist
		if (parentFolder == null) {
			return "Failed to upload " + file.getOriginalFilename() + ".\nWrong PathVariable";
		}
		File fileInDatabase = fileDao.getFile(file.getOriginalFilename(), parentFolder);

		try {

			// If this file is being uploaded for the first time in the folder
			if (fileInDatabase == null) {

				// Create the first version of the file in the File entity
				File newFile = new File(file.getOriginalFilename(), new Date(), file.getContentType(), file.getSize(),
						false, 1, parentFolder);

				// Save the file record into database
				File uploadedFile = fileVersionRepository.save(newFile);

				// Then, create a new version of the file in the database
				FileVersion newVersion = new FileVersion(new Date(), file.getBytes(), file.getSize(), 1, uploadedFile);

				// Upload the new version
				fileRepository.save(newVersion);

			} else {
				// If the file has the same name as the file that has already been uploaded in
				// the database

				// Increase the file version in Files AND update the file's properties with the
				// new file's properties

				// Get the current file's version number from database
				Integer currentVersionNumber = fileInDatabase.getCurrentVersion();

				// Increase the version number
				fileInDatabase.setCurrentVersion(++currentVersionNumber);

				// Update the size
				fileInDatabase.setSize(file.getSize());

				// Update the date
				fileInDatabase.setDateUploaded(new Date());

				// Update the database with the new file's objects properties
				fileDao.saveChanges(fileInDatabase);

				// VERSION CONTROL
				// Create a new version of the file
				FileVersion newVersion = new FileVersion(new Date(), file.getBytes(), file.getSize(),
						currentVersionNumber, fileInDatabase);

				// Upload the new version
				fileRepository.save(newVersion);

			}

			return file.getOriginalFilename() + " uploaded successfully!";
		} catch (Exception e) {
			return "Failed to upload " + file.getOriginalFilename();
		}
	}
}
