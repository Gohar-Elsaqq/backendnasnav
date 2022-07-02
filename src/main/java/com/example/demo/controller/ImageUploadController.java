package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageUplodeService;

@RestController
@RequestMapping(path = "/image")
@CrossOrigin(origins = "http://localhost:4200")

public class ImageUploadController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ImageUplodeService imageUplodeService;

	@PostMapping("/upload")
	public ResponseEntity<?> uplaodImage2(@RequestParam("imageFile") MultipartFile file,
			                      
			                             @RequestParam("description")String description,
			                             @RequestParam("category")String category)
			                            		 throws IOException {

		if(imageUplodeService.upLodeImage(file, description , category) == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		};
		
		
		return ResponseEntity.status(HttpStatus.OK).body("successfull");
		
	}

	@GetMapping(path = { "/get/{imageName}" })
	public Image getImage(@PathVariable("imageName") String imageName) throws IOException {
//				return imageUplodeService.getImage(imageName);

		return imageUplodeService.getImage(imageName);

	}

	@GetMapping(value = "/imageList")
	public List<Image> getAll() {
		return imageUplodeService.getAll();
	}

	@GetMapping(value = "/getById/{id}")
	public Image getById(@PathVariable("id") long id) {
		return imageUplodeService.getById(id);
	}
	@GetMapping(value = "/status/{status}")
	public List<Image>findByStatus(@PathVariable("status") String status){
		return imageUplodeService.getStats(status);
	}
	
	@PutMapping(value = "/update")
	public int update(@RequestParam("newStatus")String newStatus,@RequestParam("id")long id) {
		
		 return imageUplodeService.updateImage(newStatus, id);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// compress the image bytes before storing it in the database

	public static byte[] compressBytes(byte[] data) {

		Deflater deflater = new Deflater();

		deflater.setInput(data);

		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		byte[] buffer = new byte[1024];

		while (!deflater.finished()) {

			int count = deflater.deflate(buffer);

			outputStream.write(buffer, 0, count);

		}

		try {

			outputStream.close();

		} catch (IOException e) {

		}

		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();

	}

	// uncompress the image bytes before returning it to the angular application

	public static byte[] decompressBytes(byte[] data) {

		Inflater inflater = new Inflater();

		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		byte[] buffer = new byte[1024];

		try {

			while (!inflater.finished()) {

				int count = inflater.inflate(buffer);

				outputStream.write(buffer, 0, count);

			}

			outputStream.close();

		} catch (IOException ioe) {

		} catch (DataFormatException e) {

		}

		return outputStream.toByteArray();

	}

}
