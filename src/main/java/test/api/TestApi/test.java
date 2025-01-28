package test.api.TestApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import test.api.models.EmployeeRequest;



@RestController
public class test {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/test")
    public String getMethodName() {
        return "Hello World";
    }

    @PostMapping(path = "/long", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testMultipath(@RequestParam("file") MultipartFile file, @ModelAttribute EmployeeRequest body) {
        System.out.println(body.firstname());
        System.out.println(file.getOriginalFilename());
        return ResponseEntity.ok("Test Complete");
    }
    

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testUploadFile(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            String oldFileName = file.getOriginalFilename();
            @SuppressWarnings("null")
            String fileName = UUID.randomUUID().toString() + oldFileName.substring(oldFileName.lastIndexOf("."));
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("File uploaded successfully: " + filePath.toString());
        } catch (IOException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    
}
