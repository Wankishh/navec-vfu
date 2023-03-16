package com.navec.image;

import com.navec.environment.Env;
import com.navec.listing.Listing;
import com.navec.utils.TimestampUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final Env env;

    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    public ImageService(ImageRepository imageRepository, Env env) {
        this.imageRepository = imageRepository;
        this.env = env;
    }

    public List<Image> updateImagesWithListing(List<Long> imageIds, Listing listing) {
        List<Image> images = this.imageRepository.findAllById(imageIds);
        images.forEach(image -> image.setListing(listing));
        return this.imageRepository.saveAll(images);
    }

    public ImageDto uploadFile(MultipartFile file) throws IOException {
        String newFileName = generateFileName();
        String fileNameAndExt = newFileName + "." + getExt(file.getOriginalFilename());
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileNameAndExt);
        Files.write(fileNameAndPath, file.getBytes());

        Image newImage = new Image();

        newImage.setName(fileNameAndExt);
        newImage.setOriginalName(file.getOriginalFilename());
        newImage.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        newImage.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        Image savedImage = this.imageRepository.save(newImage);

        return new ImageDto(savedImage, this.env.getGetBaseImageUri());
    }

    private String generateFileName() {
        return "navec" +
                "_" +
                System.currentTimeMillis();
    }

    private String getExt(String originalFileName) {
        if(originalFileName == null) {
            return "png";
        }
        String[] splitter = originalFileName.split("\\.");
        if(splitter[1] != null) {
            return splitter[1];
        }
        return "png";
    }
}
