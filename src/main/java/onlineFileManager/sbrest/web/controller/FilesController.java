package onlineFileManager.sbrest.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import onlineFileManager.sbrest.model.File;
import onlineFileManager.sbrest.model.FileVersion;
import onlineFileManager.sbrest.model.dao.FileDao;
import onlineFileManager.sbrest.model.dao.FileRepository;
import onlineFileManager.sbrest.model.dao.VersionDao;

@RestController
@RequestMapping("/files")
public class FilesController {

	@Autowired
	private FileDao fileDao;

	@Autowired
	private VersionDao versionDao;

	@Autowired
	FileRepository fileRepository;

	// Download a specific version of a file
	@GetMapping("/{id}/versions/{vid}")
	public ResponseEntity<byte[]> downloadFileVersion(@PathVariable Integer id, @PathVariable Integer vid) {

		File myFile = fileDao.getFile(id);

		FileVersion fileVersion = versionDao.getVersion(myFile, vid);

		if (myFile != null && fileVersion != null) {

			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + myFile.getName() + "\"")
					.contentType(MediaType.parseMediaType(myFile.getType())).body(fileVersion.getData());

		}

		return ResponseEntity.status(404).body(null);
	}

	// Download a file
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Integer id) {

		File myFile = fileDao.getFile(id);

		Integer currentVersionId = myFile.getCurrentVersion();

		FileVersion fileVersion = versionDao.getVersion(myFile, currentVersionId);
		if (myFile != null && fileVersion != null) {

			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + myFile.getName() + "\"")
					.contentType(MediaType.parseMediaType(myFile.getType())).body(fileVersion.getData());
		}
		return ResponseEntity.status(404).body(null);
	}

	// Delete a single file
	// Deleting a file will delete all of its versions
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFile(@PathVariable Integer id) {
		File file = fileDao.getFile(id);

		if (file == null) {
			return ResponseEntity.status(404).body("File DOES NOT exist.");
		}

		fileDao.DeleteFile(file);
		return ResponseEntity.status(204).body("File deleted successfully.");
	}
}
