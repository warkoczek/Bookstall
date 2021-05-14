package pl.warkoczewski.Bookstall.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;
import pl.warkoczewski.Bookstall.upload.db.UploadJpaRepository;
import pl.warkoczewski.Bookstall.upload.domain.Upload;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadService implements UploadUseCase {

    private final UploadJpaRepository uploadRepository;

    @Override
    public Optional<Upload> getById(Long id) {
        return uploadRepository.findById(id);
    }

    @Override
    public Upload save(SaveUploadCommand command) {
        Upload upload = new Upload(command.getFilename(), command.getContentType(), command.getFile());
        uploadRepository.save(upload);
        System.out.println("Upload saved: " + upload.getFilename() + " with newId " + upload.getId());
        return upload;
    }

    @Override
    public void removeById(Long id) {
        uploadRepository.deleteById(id);
    }
}
