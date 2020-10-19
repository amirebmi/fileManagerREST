package onlineFileManager.sbrest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@Column(name = "date_uploaded")
	private Date dateUploaded;

	private String type;

	@Column(name = "file_size")
	private long size;

	@Column(name = "current_version")
	private Integer currentVersion;

	@Column(name = "is_public")
	private boolean isPublic = false;

	// Folders-Files Relationship - Many files can have one folder
	@ManyToOne
	private Folder folder;

	// Users and Files Relationship -- Many files can have one user
	@ManyToOne
	private User user;

	// Users and Files SHARING Relationship -- One file can have many users
	@ManyToMany(mappedBy = "sharedFiles")
	private Set<User> sharedUsers;

	// Files and FileVersions Relationship -- One file can have many versions
	@OneToMany(mappedBy = "file", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private Set<FileVersion> fileVersions;

	// Constructors
	public File() {
	}

	public File(String name, Date dateUploaded, String type, long size, boolean isPublic, Integer currentVersion,
			Folder folder) {
		this.name = name;
		this.dateUploaded = dateUploaded;
		this.type = type;
		this.size = size;
		this.isPublic = isPublic;
		this.currentVersion = currentVersion;
		this.folder = folder;
	}

	public File(String name, Date dateUploaded, String type, long size, boolean isPublic, Integer currentVersion) {
		this.name = name;
		this.dateUploaded = dateUploaded;
		this.type = type;
		this.size = size;
		this.isPublic = isPublic;
		this.currentVersion = currentVersion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Integer getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(Integer currentVersion) {
		this.currentVersion = currentVersion;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getSharedUsers() {
		return sharedUsers;
	}

	public void setSharedUsers(Set<User> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}

	public Set<FileVersion> getFileVersions() {
		return fileVersions;
	}

	public void setFileVersions(Set<FileVersion> fileVersions) {
		this.fileVersions = fileVersions;
	}

}