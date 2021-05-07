package pl.warkoczewski.Bookstall.upload.application.port;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

import java.util.Optional;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    Optional<Upload> getById(String id);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand{
        String filename;
        byte[] file;
        String contentType;
    }
}
