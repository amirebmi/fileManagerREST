package onlineFileManager.sbrest.model.dao;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.FileVersion;

public interface VersionDao {

	void addVersion(FileVersion version);

	int getNumberOfVersions(File file);

	FileVersion getVersion(File file, Integer versionNumber);

	void saveChanges(FileVersion file);

}
