package com.amh.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amh.demo.entities.Caisse;

public interface CaisseRepository extends JpaRepository<Caisse, Long>{

	public List<Caisse> findByLibelleContains(String parameter);

	@Query(value = "SELECT count(idCaisse) FROM Caisse where isdeleted = 0")
	public long count();
	
	@Query(value = "SELECT SUM(c.montantEnCaisse) FROM Caisse c where c.isdeleted = 0 and c.libelle=:nomCaisse GROUP BY c.idCaisse")
	public int totalCaisse(@Param("nomCaisse") String nomCaisse);

}
