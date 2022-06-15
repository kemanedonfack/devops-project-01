package com.amh.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amh.demo.entities.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, Long>{
	@Query(value = "SELECT count(idPersonnel) FROM Personnel where isdeleted = 0")
	public long count();
	
	@Query(value="select p from Personnel p where p.isdeleted = 0")
	public List<Personnel> getAllEmployeesActive();

	public List<Personnel> findByNomContains(String nom);
}
