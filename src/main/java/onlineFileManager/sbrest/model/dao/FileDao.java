package onlineFileManager.sbrest.model.dao;

import java.util.List;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.Folder;

public interface FileDao {

	List<File> getRoot();

	List<File> getFiles(Integer folderId);

	File getFile(Integer id); // Get a file by id

	File getFile(String fileName, Folder folder); // Get files by name using parent-folder id

	File getFile(String fileName); // Get files only by name

	void DeleteFile(File file);

	long getTotalDeletedFiles(Folder folder);

	void saveChanges(File file);
}
