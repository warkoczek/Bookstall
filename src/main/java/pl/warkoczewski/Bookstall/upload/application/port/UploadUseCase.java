package pl.warkoczewski.Bookstall.upload.application.port;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand{
        String filename;
        byte[] file;
        String contentType;
    }
}
