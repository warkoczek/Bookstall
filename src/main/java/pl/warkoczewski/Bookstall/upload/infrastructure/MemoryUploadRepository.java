package pl.warkoczewski.Bookstall.upload.infrastructure;

import pl.warkoczewski.Bookstall.upload.domain.Upload;
import pl.warkoczewski.Bookstall.upload.domain.UploadRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUploadRepository implements UploadRepository {

    private Map<String, Upload> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Upload> findById(String id){
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Upload save(Upload upload) {
        storage.put(upload.getId(), upload);
        return upload;
    }

    @Override
    public void removeById(String id) {
        storage.remove(id);
    }
}
