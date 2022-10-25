package com.amh.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@JsonIgnoreProperties({ "hibernateLazyInitializer", "gestionnaireCaisse", "depenses" })
@Entity
public class ApprovisionnementCaisse implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idApprovisionnement;
	private int montantApproCaisse;
	private int lastMontantCaisse;
	private String typeApprovisionnement;
	private LocalDateTime  dateApprovisionnement;
	
	
	@ManyToOne
	private Caisse caisse;
	
	@ManyToOne
	private GestionnaireCaisse gestionnaireCaisse;

	public ApprovisionnementCaisse() {
		super();
	}

	public Long getIdApprovisionnement() {
		return idApprovisionnement;
	}

	public void setIdApprovisionnement(Long idApprovisionnement) {
		this.idApprovisionnement = idApprovisionnement;
	}

	public int getMontantApproCaisse() {
		return montantApproCaisse;
	}

	public void setMontantApproCaisse(int montantApproCaisse) {
		this.montantApproCaisse = montantApproCaisse;
	}

	public int getLastMontantCaisse() {
		return lastMontantCaisse;
	}

	public void setLastMontantCaisse(int lastMontantCaisse) {
		this.lastMontantCaisse = lastMontantCaisse;
	}

	public String getTypeApprovisionnement() {
		return typeApprovisionnement;
	}

	public void setTypeApprovisionnement(String typeApprovisionnement) {
		this.typeApprovisionnement = typeApprovisionnement;
	}

	public LocalDateTime getDateApprovisionnement() {
		return dateApprovisionnement;
	}

	public void setDateApprovisionnement(LocalDateTime dateApprovisionnement) {
		this.dateApprovisionnement = dateApprovisionnement;
	}

	public Caisse getCaisse() {
		return caisse;
	}

	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
	}

	public GestionnaireCaisse getGestionnaireCaisse() {
		return gestionnaireCaisse;
	}

	public void setGestionnaireCaisse(GestionnaireCaisse gestionnaireCaisse) {
		this.gestionnaireCaisse = gestionnaireCaisse;
	}

	
}
