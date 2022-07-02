package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "image")

public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1418188322423308174L;



	public Image( String name, String type, byte[] picByte) {
		super();
	
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}


	public Image(Long id, String name, String status, String type, byte[] picByte,String category,String description) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.type = type;
		this.picByte = picByte;
		this.category=category;
		this.description=description;
	}


	public Image() {
		super();
	}


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name = "name")
	private String name;
	


	@Column(name = "type")
	private String type;

	@Column(name = "pic_Byte", length = 1000)
	private byte[] picByte;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "category")
	String category;
	
	@Column(name = "description")
	String description;
	
	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getType() {

		return type;

	}

	public void setType(String type) {

		this.type = type;

	}

	public byte[] getPicByte() {

		return picByte;

	}

	public void setPicByte(byte[] picByte) {

		this.picByte = picByte;

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

}
