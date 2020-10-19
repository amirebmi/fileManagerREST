package onlineFileManager.sbrest.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.FileVersion;
import onlineFileManager.sbrest.model.dao.VersionDao;

@Repository
public class VersionDaoImpl implements VersionDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addVersion(FileVersion version) {
		entityManager.merge(version);
	}

	@Override
	public int getNumberOfVersions(File file) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FileVersion getVersion(File file, Integer versionNumber) {
		String query = "from FileVersion where file = :file AND versionNumber = :version";

		try {

			var fileVersion = entityManager.createQuery(query, FileVersion.class).setParameter("file", file)
					.setParameter("version", versionNumber).getSingleResult();

			return fileVersion;
		} catch (Exception e) {
			FileVersion fileVersion = null;
			return fileVersion;
		}

	}

	@Override
	@Transactional
	public void saveChanges(FileVersion file) {
		entityManager.merge(file);
	}

}
