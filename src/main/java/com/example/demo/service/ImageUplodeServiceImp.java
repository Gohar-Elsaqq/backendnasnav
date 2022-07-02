package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;

@Service
public class ImageUplodeServiceImp implements ImageUplodeService {
	@Autowired
	ImageRepository imageRepository;

	@Override
	public Image upLodeImage(MultipartFile file, String description, String category) throws IOException {

		List<String> imageCategrries = new ArrayList<>();
		imageCategrries.add("living");
		imageCategrries.add("machine");
		imageCategrries.add("nature");
		System.out.println("imageCategrries . toString : " + imageCategrries.toString());

		List<String> imageTypes = new ArrayList<>();
		imageTypes.add("jpg");
		imageTypes.add("png");
		imageTypes.add("gif");
		System.out.println("imageTypes . toString : " + imageTypes.toString());

		System.out.println("file.getContentType() - " + file.getContentType());
		String imageName = file.getOriginalFilename();
		System.out.println("imageName - " + imageName);
		int dotIndex = imageName.indexOf('.');
		System.out.println("dotIndex - " + dotIndex);
		String extension = imageName.substring(dotIndex + 1, imageName.length());
		System.out.println("extension - " + extension);
		if (!imageTypes.contains(extension)) {
			System.out.println("image type not supported , supported types are: " + imageTypes.toString());
			return null;
		}

		Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));

		if (imageCategrries.contains(category)) {
			img.setCategory(category);
		} else {
			System.out
					.println("image category not supported , supported categories are: " + imageCategrries.toString());
			return null;
		}

		System.out.println("Original Image Byte Size - " + file.getBytes().length);

		if (file.getBytes().length > 2 * 1024 * 1000) {
			System.out.println("image size excceeds size limit");
			return null;
		}

		img.setStatus("pending");
		img.setDescription(description);

		return imageRepository.save(img);

	}

	@Override
	public Image getImage(String name) throws IOException {
		Optional<Image> optional = imageRepository.findByName(name);

		Image img = new Image(optional.get().getName(), optional.get().getType(),

				decompressBytes(optional.get().getPicByte()));
		return img;
	}

	// GET ALL
	@Override
	public List<Image> getAll() {
		// TODO Auto-generated method stub
		return imageRepository.findAll();
	}

	@Override
	public Image getById(long id) {
		// TODO Auto-generated method stub
		return imageRepository.getById(id);
	}

	@Override
	public List<Image> getStats(String status) {
		return imageRepository.findByStatus(status);
	}

	@Override
	public int updateImage(String newStatus, long id) {
		
		if(newStatus ==null || imageRepository.getById(id) == null || id!=0) {
			System.out.println("Error");
			return 0;
			
		}
		return imageRepository.updateStatus(newStatus, id);
	}
	

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
