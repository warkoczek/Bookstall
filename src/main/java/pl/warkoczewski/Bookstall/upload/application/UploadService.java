package pl.warkoczewski.Bookstall.upload.application;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UploadService implements UploadUseCase {

    private Map<String, Upload> storage = new ConcurrentHashMap<>();

    @Override
    public Upload save(SaveUploadCommand command) {
        String newId = RandomStringUtils.random(8);
        Upload upload = new Upload(newId, command.getFile(), command.getContentType(),  command.getFilename(), LocalDateTime.now());
        storage.put(upload.getId(), upload);
        System.out.println("Upload saved: " + upload.getFilename() + " with newId " + newId);
        return upload;
    }
}
