package com.br.API.GamesRating.service;

import com.br.API.GamesRating.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImagemFromFile(MultipartFile multipartFile){
        var ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(!"png".equalsIgnoreCase(ext) && !"jpg".equalsIgnoreCase(ext)){
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }
        try {
            var img = ImageIO.read(multipartFile.getInputStream());
            if("png".equalsIgnoreCase(ext)){
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {
        var jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension){
        try {
            var os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        }catch (IOException e){
            throw new FileException("Erro ao ler Arquivo");
        }
    }
}
