package com.bhaiti.kela.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.ExcelToJson.ExcelToJsonConverter;
import com.demo.ExcelToJson.ExcelToJsonConverterConfig;
import com.demo.ExcelToJson.pojo.ExcelWorkbook;

@RestController
public class FileUploadController {

	String UPLOAD_DIR = "E:\\FileUploads";
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> fileUpload(@RequestParam(value="file") MultipartFile file) throws IOException {
		
		String originalFileName = file.getOriginalFilename();
		
		String originalFileExtension = getFileExtension(originalFileName);
		
		String randomFileName = getRandomString() + originalFileExtension;
		
		File uploadedFile = new File(UPLOAD_DIR, randomFileName);
        uploadedFile.createNewFile();
        
        FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        
        ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();
        String json = "";
        
        try {
        	config.setSourceFile(uploadedFile.getAbsolutePath());
        	config.setFormatDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa"));
    		ExcelWorkbook book = ExcelToJsonConverter.convert(config);
    		json = book.toJson(config.isPretty());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return new ResponseEntity<Object>(json, HttpStatus.OK);
	}
	
	private String getRandomString() {
		return new Random().nextInt(999999) + "_" + System.currentTimeMillis();
	}
	
	private String getFileExtension(String fileName) {
		String fileExtention = fileName.substring(fileName.lastIndexOf('.'));
		return fileExtention;
	}
	
}
