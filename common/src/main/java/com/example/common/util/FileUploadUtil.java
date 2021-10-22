package com.example.common.util;

import com.example.common.properties.MovieProperties;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
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
public class FileUploadUtil {

    static MovieProperties movieProperties;

    public static void compressProductImage(BufferedImage image, String uploadPath, String extension) {
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

    public static String compressImage(MultipartFile file, String uploadDir, String fileName) throws IOException {
        String originalFilename = System.currentTimeMillis() + "_" + fileName;
        String uploadPath = uploadDir + originalFilename;
        File image = new File(uploadPath);
        file.transferTo(image);
        BufferedImage bi = ImageIO.read(file.getInputStream());
        BufferedImage resize = Scalr.resize(bi, 250, 400);
        compressProductImage(resize, uploadPath, "png");
        return originalFilename;
    }

    public static String getSmallPicUrl(MultipartFile multipartFile) throws IOException {
        String png = "intermediate.png";
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(multipartFile.getBytes(), png);
        String smallPicUrl = FileUploadUtil.compressImage(customMultipartFile, movieProperties.getMovieImg(), png);

        return smallPicUrl;
    }

    public static String getPicUrl(MultipartFile multipartFile) throws IOException {
        String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(movieProperties.getMovieImg() + File.separator + picUrl));
        return picUrl;
    }
}





