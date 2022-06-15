package com.amh.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amh.demo.entities.GestionnaireCaisse;

public interface GestionnaireCaisseRepository extends JpaRepository<GestionnaireCaisse, Long>{
	@Query(value = "SELECT count(idGestionnaire) FROM GestionnaireCaisse where isdeleted = 0")
	public long count();
	
	@Override
	@Query("SELECT g FROM GestionnaireCaisse g where g.isdeleted = 0")
	List<GestionnaireCaisse> findAll();
	
	@Query("SELECT g FROM GestionnaireCaisse g where g.utilisateur =:user")
	GestionnaireCaisse findByUtilisateur(@Param("user") String utilisateur);
	
	@Query("SELECT g.utilisateur FROM GestionnaireCaisse g where g.utilisateur =:user")
	String findByUserExists(@Param("user") String utilisateur);
	
	
}

