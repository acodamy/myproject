package com.student.service;

import java.io.File;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.studentfile.exception.FileStorageException;

public class FileUploadServiceImpl implements FileUploadService {

	@Override
	public String uploadFile(MultipartFile file) {
		String fileName= StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("invalid filename"+ fileName);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
}

	
