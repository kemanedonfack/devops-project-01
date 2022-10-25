package com.amh.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amh.demo.entities.ApprovisionnementCaisse;

public interface ApprovisionnementCaisseRepository extends JpaRepository<ApprovisionnementCaisse, Long> {
	
	@Query(value = "SELECT montant_appro_caisse FROM approvisionnement_caisse "
			+ "WHERE id_approvisionnement = (SELECT MAX(id_approvisionnement) FROM approvisionnement_caisse WHERE caisse_id_caisse=:idCaisse)",nativeQuery = true)
	public Object totalApproParCaisse(@Param("idCaisse") int idCaisse);
	
	@Query(value = "SELECT a FROM ApprovisionnementCaisse a ORDER BY a.idApprovisionnement DESC")
	public List<ApprovisionnementCaisse> getAllApprovisionnement();

	@Query(value = "SELECT * FROM approvisionnement_caisse WHERE caisse_id_caisse=:idCaisse",nativeQuery = true)
	public List<ApprovisionnementCaisse> getHistoApprovisionnement(@Param("idCaisse") int idCaisse);
	
}
