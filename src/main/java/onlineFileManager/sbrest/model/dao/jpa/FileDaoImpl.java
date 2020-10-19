package onlineFileManager.sbrest.model.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.Folder;
import onlineFileManager.sbrest.model.dao.FileDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
public class FileDaoImpl implements FileDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<File> getRoot() {

		String query = "from File where folder = null";

		return entityManager.createQuery(query, File.class).getResultList();
	}

	@Override
	public List<File> getFiles(Integer folderId) {

		Folder folder = entityManager.find(Folder.class, folderId);

		String query = "from File where folder = :folder";

		return entityManager.createQuery(query, File.class).setParameter("folder", folder).getResultList();
	}

	@Override
	public File getFile(Integer id) {
		return entityManager.find(File.class, id);
	}

	@Override
	// For files on non-root directory
	public File getFile(String fileName, Folder folder) {

		String query = "from File where name = :fileName AND folder = :folder";

		try {
			var file = entityManager.createQuery(query, File.class).setParameter("fileName", fileName)
					.setParameter("folder", folder).getSingleResult();

			return file;

		} catch (Exception e) {
			File file = null;

			return file;
		}
	}

	@Override
	// For files on root directory
	public File getFile(String fileName) {

		String query = "from File where name = :fileName AND folder is null";

		try {
			var file = entityManager.createQuery(query, File.class).setParameter("fileName", fileName)
					.getSingleResult();
			return file;
		} catch (Exception e) {
			File file = null;

			return file;
		}

	}

	@Override
	@Transactional
	public void DeleteFile(File file) {

		entityManager.remove(file);
	}

	@Override
	public long getTotalDeletedFiles(Folder folder) {
		if (folder == null)
			return 0;
		if (folder.getSubfolders() == null)
			return 0;
		long n = folder.getFiles().size();

		for (Folder fldr : folder.getSubfolders()) {
			n += getTotalDeletedFiles(fldr);
		}
		return n;
	}

	@Override
	@Transactional
	public void saveChanges(File file) {
		entityManager.merge(file);
	}

}
