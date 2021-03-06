package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Donor;
import com.example.demo.service.DonorService;


@RestController
@RequestMapping(path="/donors")
public class DonorController {
	
	@Autowired
	private DonorService service;

	public void setService(DonorService service ) {
		
		this.service=service;
	}
	
	@GetMapping
	public List<Donor> findAll(){
		return this.service.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Donor> add(@RequestBody Donor entity){
		Donor addedEntity = this.service.addDonor(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedEntity);
	}
	
	@PutMapping(path="/{donorId}/{address}")
	public ResponseEntity<String> updateAddress(@PathVariable("donorId") int donorId
			,@PathVariable("address") String address){
		
		int count=this.service.updateAddress(donorId, address);
		return ResponseEntity.ok().body(count +" donor address updated");
	}
	
	@GetMapping(path="/srch/{address}")
	public List<Donor> findByAddress(@PathVariable("address") String address){
		return this.service.findByAddress(address);
		
	}
	
	@DeleteMapping(path="/{donorId}")
	public ResponseEntity<String> removeDonor(@PathVariable("donorId") int donorId){
		
		int count= this.service.remove(donorId);
		HttpStatus status = HttpStatus.NOT_FOUND;
		String message="Record Not Found";
		
		if(count==1)
		{
			status=HttpStatus.OK;
			message =" One Record Deleted";
		}
		
		return ResponseEntity.status(status).body(message);
	}
	

}
