package onlineFileManager.sbrest.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String userName;

	private String password;

	// Folder and Users Relationship -- One user can have many folders
	@OneToMany(mappedBy = "user")
	private Set<Folder> folders;

	// Users and Files Relationship -- One user can have many files
	@OneToMany(mappedBy = "user")
	private Set<File> files;

	// Users and Files SHARING Relationship -- One user can have many files
	@ManyToMany
	@JoinTable(name = "shared_files", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private Set<File> sharedFiles;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<File> getSharedFiles() {
		return sharedFiles;
	}

	public void setSharedFiles(Set<File> sharedFiles) {
		this.sharedFiles = sharedFiles;
	}

}