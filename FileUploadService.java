package com.student.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	
public String uploadFile(MultipartFile file);
}
