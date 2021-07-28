package com.yzzzzun.upload.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.yzzzzun.upload.domain.UploadFile;

@Component
public class FileStore {

	@Value("${file.dir}")
	private String fileDir;

	public String getFullPath(String filename){
		return fileDir + filename;
	}

	public List<UploadFile> storeFiles(List<MultipartFile> files) throws IOException {

		List<UploadFile> storeFileResult = new ArrayList<>();
		for (MultipartFile file : files) {
			if(!file.isEmpty()){
				storeFileResult.add(storeFile(file));
			}
		}

		return storeFileResult;
	}

	public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
		if(multipartFile.isEmpty()){
			return null;
		}
		String originalFilename = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);
		String fullPath = getFullPath(storeFileName);
		multipartFile.transferTo(new File(fullPath));

		return new UploadFile(originalFilename, storeFileName);
	}

	private String createStoreFileName(String originalFilename) {
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + extractExt(originalFilename);
	}

	private String extractExt(String originalFileName){
		int pos = originalFileName.lastIndexOf(".");
		return originalFileName.substring(pos + 1);
	}
}
