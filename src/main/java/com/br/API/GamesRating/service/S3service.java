package com.br.API.GamesRating.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.br.API.GamesRating.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3service {

  @Autowired private AmazonS3 amazonS3;

  private Logger LOG = LoggerFactory.getLogger(S3service.class);

  @Value("${AWS_NAME_BUCKET}")
  private String bucketName;

  public URI uploadFile(MultipartFile multipartFile) {
    try {
      var fileName = multipartFile.getOriginalFilename();
      var is = multipartFile.getInputStream();
      var contentType = multipartFile.getContentType();
      return uploadFile(is, fileName, contentType);
    } catch (IOException e) {
      throw new FileException("Erro de IO: " + e.getMessage());
    }
  }

  public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
    var meta = new ObjectMetadata();
    meta.setContentType(contentType);
    LOG.info("Iniciando Upload");
    amazonS3.putObject(bucketName, fileName, inputStream, meta);
    LOG.info("Upload finalizado");
    try {
      return amazonS3.getUrl(bucketName, fileName).toURI();
    } catch (URISyntaxException e) {
      throw new FileException("Erro ao converter URL para URI");
    }
  }
}
