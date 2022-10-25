package com.amh.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "approvisionnementCaisse", "gestionnaireCaisse", "depenses" })
@Entity
public class Caisse implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCaisse;
	private String libelle;
	private String codeCaisse;
	private int montantEnCaisse;
	private LocalDateTime dateEtatCaisse;
	private boolean isdeleted;

	@OneToMany(mappedBy = "caisse")
	private Set<Depense> depenses;

	@OneToMany(mappedBy = "caisse")
	private Set<ApprovisionnementCaisse> approvisionnementCaisse;

	@OneToMany(mappedBy = "caisse")
	private Set<GestionnaireCaisse> gestionnaireCaisse;

	public Caisse() {
		super();
	}

	public Caisse(Long idCaisse, String libelle, String codeCaisse, int montantEnCaisse, LocalDateTime dateEtatCaisse,
			boolean isdeleted, Set<Depense> depenses, Set<ApprovisionnementCaisse> approvisionnementCaisse,
			Set<GestionnaireCaisse> gestionnaireCaisse) {
		super();
		this.idCaisse = idCaisse;
		this.libelle = libelle;
		this.codeCaisse = codeCaisse;
		this.montantEnCaisse = montantEnCaisse;
		this.dateEtatCaisse = dateEtatCaisse;
		this.isdeleted = isdeleted;
		this.depenses = depenses;
		this.approvisionnementCaisse = approvisionnementCaisse;
		this.gestionnaireCaisse = gestionnaireCaisse;
	}

	public Long getIdCaisse() {
		return idCaisse;
	}

	public void setIdCaisse(Long idCaisse) {
		this.idCaisse = idCaisse;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCodeCaisse() {
		return codeCaisse;
	}

	public void setCodeCaisse(String codeCaisse) {
		this.codeCaisse = codeCaisse;
	}

	public int getMontantEnCaisse() {
		return montantEnCaisse;
	}

	public void setMontantEnCaisse(int montantEnCaisse) {
		this.montantEnCaisse = montantEnCaisse;
	}

	public LocalDateTime getDateEtatCaisse() {
		return dateEtatCaisse;
	}

	public void setDateEtatCaisse(LocalDateTime dateEtatCaisse) {
		this.dateEtatCaisse = dateEtatCaisse;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Set<Depense> getDepenses() {
		return depenses;
	}

	public void setDepenses(Set<Depense> depenses) {
		this.depenses = depenses;
	}

	public Set<ApprovisionnementCaisse> getApprovisionnementCaisse() {
		return approvisionnementCaisse;
	}

	public void setApprovisionnementCaisse(Set<ApprovisionnementCaisse> approvisionnementCaisse) {
		this.approvisionnementCaisse = approvisionnementCaisse;
	}

	public Set<GestionnaireCaisse> getGestionnaireCaisse() {
		return gestionnaireCaisse;
	}

	public void setGestionnaireCaisse(Set<GestionnaireCaisse> gestionnaireCaisse) {
		this.gestionnaireCaisse = gestionnaireCaisse;
	}

}
