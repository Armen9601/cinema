package com.example.common.util;

import com.example.common.properties.MovieProperties;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

@RequiredArgsConstructor
@Component
public class FileUploadUtil {

   // @Value("${movie.movieImg}")
    //String movieImg = "C:/Users/User/Desktop/e/";

    private final MovieProperties movieProperties;

    public void compressProductImage(BufferedImage image, String uploadPath, String extension) {
        try {
            File compressedImageFile = new File(uploadPath);
            OutputStream outputStream = new FileOutputStream(compressedImageFile);
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
            ImageWriter writer = writers.next();
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.5f);
            }
            writer.write(null, new IIOImage(image, null, null), param);
            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String compressImage(MultipartFile file, String uploadDir, String fileName, boolean isResize) throws IOException {
        String originalFilename = System.currentTimeMillis() + "_" + fileName;
        String uploadPath = uploadDir + originalFilename;
        File image = new File(uploadPath);
        file.transferTo(image);
        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (isResize) {
            BufferedImage resize = Scalr.resize(bi, 250, 400);
            compressProductImage(resize, uploadPath, "png");
        }
        return originalFilename;
    }

    public String getSmallPicUrl(MultipartFile multipartFile,boolean isResize) throws IOException {
        String png = "intermediate.png";
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(multipartFile.getBytes(), png);
        return compressImage(customMultipartFile, movieProperties.getMovieImg(), png,isResize);
    }
}





