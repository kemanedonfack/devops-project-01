package com.amh.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@JsonIgnoreProperties({ "hibernateLazyInitializer", "gestionnaireCaisse", "caisse", "pieceDeCaisse","personnel" })
@Entity
public class Depense implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDepense;
	private String motif;
	private int montant;
	private LocalDateTime dateDepense;
	private String beneficiaire;
	private String imputationComptable;
	private boolean isdeleted;
	
	@ManyToOne
	private GestionnaireCaisse gestionnaireCaisse;
	
	@ManyToOne
	private Caisse caisse;
	
	@ManyToOne
	private Personnel personnel;
	
	@OneToMany(mappedBy = "depense",cascade = CascadeType.ALL)
    private Set<PieceDeCaisse> pieceDeCaisse;

	public Depense() {
		super();
	}

	public Depense(String motif, int montant, LocalDateTime dateDepense, String beneficiaire, String imputationComptable, boolean isdeleted, GestionnaireCaisse gestionnaireCaisse, Caisse caisse, Personnel personnel, Set<PieceDeCaisse> pieceDeCaisse) {
		this.motif = motif;
		this.montant = montant;
		this.dateDepense = dateDepense;
		this.beneficiaire = beneficiaire;
		this.imputationComptable = imputationComptable;
		this.isdeleted = isdeleted;
		this.gestionnaireCaisse = gestionnaireCaisse;
		this.caisse = caisse;
		this.personnel = personnel;
		this.pieceDeCaisse = pieceDeCaisse;
	}

	public Long getIdDepense() {
		return idDepense;
	}

	public void setIdDepense(Long idDepense) {
		this.idDepense = idDepense;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public LocalDateTime getDateDepense() {
		return dateDepense;
	}

	public void setDateDepense(LocalDateTime dateDepense) {
		this.dateDepense = dateDepense;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public String getImputationComptable() {
		return imputationComptable;
	}

	public void setImputationComptable(String imputationComptable) {
		this.imputationComptable = imputationComptable;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public GestionnaireCaisse getGestionnaireCaisse() {
		return gestionnaireCaisse;
	}

	public void setGestionnaireCaisse(GestionnaireCaisse gestionnaireCaisse) {
		this.gestionnaireCaisse = gestionnaireCaisse;
	}

	public Caisse getCaisse() {
		return caisse;
	}

	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Set<PieceDeCaisse> getPieceDeCaisse() {
		return pieceDeCaisse;
	}

	public void setPieceDeCaisse(Set<PieceDeCaisse> pieceDeCaisse) {
		this.pieceDeCaisse = pieceDeCaisse;
	}

	
}
