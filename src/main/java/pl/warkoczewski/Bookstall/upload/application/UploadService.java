package pl.warkoczewski.Bookstall.upload.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;
import pl.warkoczewski.Bookstall.upload.domain.Upload;
import pl.warkoczewski.Bookstall.upload.domain.UploadRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadService implements UploadUseCase {

    private final UploadRepository uploadRepository;

    @Override
    public Optional<Upload> getById(String id) {
        return uploadRepository.findById(id);
    }

    @Override
    public Upload save(SaveUploadCommand command) {
        String newId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        Upload upload = new Upload(newId, command.getFile(), command.getContentType(),  command.getFilename(), LocalDateTime.now());
        uploadRepository.save(upload);
        System.out.println("Upload saved: " + upload.getFilename() + " with newId " + newId);
        return upload;
    }

    @Override
    public void removeById(String id) {
        uploadRepository.removeById(id);
    }
}
