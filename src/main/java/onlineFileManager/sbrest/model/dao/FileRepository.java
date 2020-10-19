package onlineFileManager.sbrest.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import onlineFileManager.sbrest.model.FileVersion;

@Transactional
public interface FileRepository extends JpaRepository<FileVersion, Long> {
}
