package onlineFileManager.sbrest.model;

import java.util.Date;

public class FolderDto {

	private Integer id;

	private String name;

	private Date dateCreated;

	private Folder parentFolder;

	private Integer parentFolderId;

	private String parentFolderName;

	public FolderDto() {
	}

	public FolderDto(Folder folder) {

		this.id = folder.getId();
		this.name = folder.getName();
		this.dateCreated = folder.getDateCreated();

		if (folder.getParentFolder() != null) {
			this.parentFolderId = folder.getParentFolder().getId();
			this.parentFolderName = folder.getParentFolder().getName();
		}

	}

	public FolderDto(int parentId, String parentName) {
		this.id = parentId;
		this.name = parentName;
	}

	public FolderDto(int id) {

		this.id = null;

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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public Integer getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Integer parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getParentFolderName() {
		return parentFolderName;
	}

	public void setParentFolderName(String parentFolderName) {
		this.parentFolderName = parentFolderName;
	}

}
