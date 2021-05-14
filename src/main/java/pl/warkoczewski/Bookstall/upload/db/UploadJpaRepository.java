package pl.warkoczewski.Bookstall.upload.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
