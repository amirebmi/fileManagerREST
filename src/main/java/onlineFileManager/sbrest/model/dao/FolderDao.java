package onlineFileManager.sbrest.model.dao;

import java.util.List;

import onlineFileManager.sbrest.model.Folder;

public interface FolderDao {

	List<Folder> getRoot();

	List<Folder> getSubFolders(int id);

	Folder getFolder(int id);

	Folder createFolder(Folder folder);

	void deleteFolder(Folder folder);

	long getTotalSubFolders(Folder folder);

}
