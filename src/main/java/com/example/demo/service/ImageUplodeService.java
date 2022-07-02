package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;


@Service
public interface ImageUplodeService {
	

	 Image upLodeImage (MultipartFile file, String description , String category)throws IOException;
	 Image getImage(String name) throws IOException;
	 List<Image> getAll();
	 Image getById(long id);
	 List<Image> getStats(String status);
	 int updateImage(String newStatus,long id);


}
