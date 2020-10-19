package onlineFileManager.sbrest.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import onlineFileManager.sbrest.model.Folder;
import onlineFileManager.sbrest.model.dao.FolderDao;

@Repository
public class FolderDaoImpl implements FolderDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Folder> getRoot() {
		String query = "from Folder where parentFolder = null";

		return entityManager.createQuery(query, Folder.class).getResultList();
	}

	@Override
	public List<Folder> getSubFolders(int id) {
		Folder folder = entityManager.find(Folder.class, id);
		String query = "from Folder where parentFolder = :parentFolder";
		return entityManager.createQuery(query, Folder.class).setParameter("parentFolder", folder).getResultList();
	}

	@Override
	public Folder getFolder(int id) {
		Folder folder = entityManager.find(Folder.class, id);
		return folder;
	}

	@Override
	@Transactional
	public Folder createFolder(Folder folder) {
		return entityManager.merge(folder);
	}

	@Override
	@Transactional
	public void deleteFolder(Folder folder) {
		entityManager.remove(folder);
	}

	@Override
	public long getTotalSubFolders(Folder folder) {
		if (folder == null)
			return 0;
		if (folder.getSubfolders() == null)
			return 0;
		long n = folder.getSubfolders().size();

		for (Folder subFolder : folder.getSubfolders()) {
			n += getTotalSubFolders(subFolder);
		}
		return n;
	}

}
