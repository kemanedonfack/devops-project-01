package com.amh.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.Personnel;
import com.amh.demo.repository.PersonnelRepository;

@Service
public class PersonnelService {
	
	@Autowired
	private PersonnelRepository personnelRepository;
	
	public List<Personnel> getAllPersonnel(){
		return personnelRepository.getAllEmployeesActive();
	}
	
	public void save(Personnel personnel) {
		personnelRepository.save(personnel);
	}
	
	public Personnel getPersonnelById(Long id) {
		return personnelRepository.findById(id).get();
	}
	
	public void delete(Long id) {
		personnelRepository.deleteById(id);
	}
	
	public long countPersonnel() {
		return personnelRepository.count();
	}
	
	public List<Personnel> searchPersonnel(String parameter){
		return personnelRepository.findByNomContains(parameter);
	}
	
	public String viewHomePage(){
		return "index";
	}

}
