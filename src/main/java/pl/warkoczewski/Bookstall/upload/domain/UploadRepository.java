package pl.warkoczewski.Bookstall.upload.domain;

import java.util.Optional;

public interface UploadRepository {

    Optional<Upload> findById(String id);

    Upload save(Upload upload);

    void removeById(String id);
}
