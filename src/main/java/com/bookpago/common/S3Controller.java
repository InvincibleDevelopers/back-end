package invincibleDevs.bookpago.common;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @Operation(summary = "Upload file to S3", description = "Uploads a file to S3 and returns the URL and FileKey")
    public ResponseEntity<String> uploadToS3(@RequestPart("file") MultipartFile file) {
        try {
            String url = s3Service.uploadFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/image/{fileKey}")
    @Operation(summary = "Get image by fileKey", description = "Retrieves the image URL from S3 using the provided fileKey")
    public ResponseEntity<String> getImage(@PathVariable("fileKey") String fileKey) {
        try {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Image not found for fileKey: " + fileKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Image not found for fileKey: " + fileKey);
        }
    }
}
