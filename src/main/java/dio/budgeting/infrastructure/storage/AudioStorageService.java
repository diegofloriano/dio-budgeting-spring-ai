package dio.budgeting.infrastructure.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class AudioStorageService {

    private final Path storagePath = Path.of(System.getProperty("java.io.tmpdir"), "dio_budgeting_audio");

    public AudioStorageService() throws IOException {
        Files.createDirectories(storagePath);
    }

    public String store(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + (file.getOriginalFilename() != null ? file.getOriginalFilename() : "audio.mp3");
        Path target = storagePath.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}