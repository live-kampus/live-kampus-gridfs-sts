package com.capgemini.eventgridfs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.eventgridfs.entity.FileResources;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
@CrossOrigin("*")
public class eventGridFsController {


	@Autowired
	private GridFsOperations gridFsOperations;
	
	String fileId=" ";
	
	@PostMapping("/event/upload/{email}")
	public ResponseEntity saveFile(@RequestParam("filePath") MultipartFile file, @PathVariable String email) throws IOException{
		
		if(!file.isEmpty()) {
			System.out.println(file.getInputStream());
			//System.out.println(email);
		}
		
		DBObject metaData = new BasicDBObject();
		metaData.put("email", email);
		InputStream inputStream = file.getInputStream();
		metaData.put("type", "images");
		

		
		fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), metaData).getId().toString();
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/event/save/{email}")
	public ResponseEntity retrieveSingleImageFileUsingUsername(@PathVariable String email) throws Exception{
		GridFSDBFile dbFile = gridFsOperations.findOne(new Query(Criteria.where("metadata.email").is(email)));
	InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
	
	System.out.println(inputStreamResource);
	  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
	}
	
	
	@GetMapping("/retrieve/{id}")
	  public ResponseEntity retrieveVideoUsingId(@PathVariable String id) {
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("_id").is(id)));
		  InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
		  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
		  
	  }
	  
	  @GetMapping("/save")
	  public ResponseEntity<List<FileResources>> retrieveVideoFileForHomepage() throws IOException {
		  List<GridFSDBFile> dbFileList = gridFsOperations.find(null);
		  List<FileResources> fileResource = new ArrayList<>();
		  
		  int index = 0;
		  for(GridFSDBFile dbFL: dbFileList )
		  {
			  FileResources fr = new FileResources( dbFL.getId().toString());
			  fileResource.add(fr);
			 
			  System.out.println(dbFL.getInputStream());
			  
		  }
		  
		  return new ResponseEntity<List<FileResources>>(fileResource, HttpStatus.OK);
	  }
}
