package pl.warkoczewski.Bookstall.upload.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.warkoczewski.Bookstall.upload.application.port.UploadUseCase;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadUseCase upload;

    @GetMapping("/{id}")
    public ResponseEntity<UploadResponse> getUpload(@PathVariable String id){
        return upload.getById(id)
                .map(file -> {
                    UploadResponse response = new UploadResponse(file.getId(), file.getContentType(), file.getFilename(), file.getCreatedAt());
                    return ResponseEntity.ok(response);
        })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFileContent(@PathVariable String id){
        return upload.getById(id).map(
                file -> {
                    String contentDisposition = "attachment; filename=\"" + file.getFilename() + "\"";
                    byte[] bytes = file.getFile();
                    Resource resource = new ByteArrayResource(bytes);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .body(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @Value
    @AllArgsConstructor
    private static class UploadResponse{
        String id;
        String contentType;
        String filename;
        LocalDateTime createdAt;
    }
}
