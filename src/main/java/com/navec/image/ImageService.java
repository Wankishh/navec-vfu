package com.navec.image;

import com.navec.environment.Env;
import com.navec.listing.Listing;
import com.navec.storage.Storage;
import com.navec.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    private final Env env;

    private final Storage storage;

    public ImageService(ImageRepository imageRepository,
                        Env env,
                        Storage storage) {
        this.imageRepository = imageRepository;
        this.env = env;
        this.storage = storage;
    }

    public List<Image> updateImagesWithListing(List<Long> imageIds, Listing listing) {
        List<Image> images = this.imageRepository.findAllById(imageIds);
        images.forEach(image -> image.setListing(listing));
        return this.imageRepository.saveAll(images);
    }

    public void removeImagesForListing(Long listingId) {
        this.imageRepository.deleteByListingId(listingId);
    }

    public ImageDto uploadFile(MultipartFile file) throws IOException {
        String newFileName = generateFileName();
        String fileNameAndExt = newFileName + "." + getExt(file.getOriginalFilename());
        this.storage.putFile(fileNameAndExt, file.getInputStream());
        Image newImage = new Image();

        newImage.setName(fileNameAndExt);
        newImage.setOriginalName(file.getOriginalFilename());
        newImage.setCreatedAt(TimestampUtils.getCurrentTimestamp());
        newImage.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        Image savedImage = this.imageRepository.save(newImage);

        return new ImageDto(savedImage, this.env.getBaseImageUri());
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
