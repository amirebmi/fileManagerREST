package onlineFileManager.sbrest.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import onlineFileManager.sbrest.model.File;

@Transactional
public interface FileVersionRepository extends JpaRepository<File, Long> {
}
