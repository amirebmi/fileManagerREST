package onlineFileManager.sbrest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "folders")
public class Folder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String Name;

	@Column(name = "date_created")
	private Date dateCreated;

	private boolean isPublic = false;

	// Folders and itself Relationship - A folder can have many sub-folders
	@OneToMany(mappedBy = "parentFolder", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private Set<Folder> subfolders;

	// Folders and itself Relationship - Folder relationship with its sub-folders
	@ManyToOne
	private Folder parentFolder;

	// Folders-Files Relationship - One folder can have many files
	@OneToMany(mappedBy = "folder", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private Set<File> files;

	// Folders and Users Relationship -- Many folders can have one user
	@ManyToOne
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Set<Folder> getSubfolders() {
		return subfolders;
	}

	public void setSubfolders(Set<Folder> subfolders) {
		this.subfolders = subfolders;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
