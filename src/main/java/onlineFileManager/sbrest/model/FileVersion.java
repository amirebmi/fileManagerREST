package onlineFileManager.sbrest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "versions")
public class FileVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "version_number")
	private Integer versionNumber;

	private Date createdOn;

	@Lob
	private byte[] data;

	@Column(name = "file_size")
	public long size;

	// Files and FileVersions Relationship -- Many versions has one file
	@ManyToOne
	private File file;

	public FileVersion() {
	}

	public FileVersion(Date createdOn, byte[] data, long size, Integer versionNumber, File parentFile) {
		this.createdOn = createdOn;
		this.data = data;
		this.size = size;
		this.versionNumber = versionNumber;
		this.file = parentFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
