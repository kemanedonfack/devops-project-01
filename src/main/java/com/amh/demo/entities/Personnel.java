package com.amh.demo.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Personnel implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPersonnel;
	private String nom;
	private String service;
	private String surcussale;
	private String contactInfo;
	private boolean isdeleted;
	
	@OneToMany(mappedBy = "personnel",cascade = CascadeType.ALL)
    private Set<PieceDeCaisse> pieceDeCaisse;
	
	@OneToMany(mappedBy = "personnel",cascade = CascadeType.ALL)
    private Set<Depense> depense;

	public Personnel() {
		super();
	}


	public Long getIdPersonnel() {
		return idPersonnel;
	}

	public void setIdPersonnel(Long idPersonnel) {
		this.idPersonnel = idPersonnel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSurcussale() {
		return surcussale;
	}

	public void setSurcussale(String surcussale) {
		this.surcussale = surcussale;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Set<PieceDeCaisse> getPieceDeCaisse() {
		return pieceDeCaisse;
	}

	public void setPieceDeCaisse(Set<PieceDeCaisse> pieceDeCaisse) {
		this.pieceDeCaisse = pieceDeCaisse;
	}

	public Set<Depense> getDepense() {
		return depense;
	}

	public void setDepense(Set<Depense> depense) {
		this.depense = depense;
	}
public Personnel(Long idPersonnel, String nom, String service, String contactInfo, boolean isdeleted,
			Set<PieceDeCaisse> pieceDeCaisse) {
		super();
		this.idPersonnel = idPersonnel;
		this.nom = nom;
		this.service = service;
		this.contactInfo = contactInfo;
		this.isdeleted = isdeleted;
		this.pieceDeCaisse = pieceDeCaisse;
	}

	public Personnel(Long idPersonnel, String nom, String service, String contactInfo, boolean isdeleted) {
		super();
		this.idPersonnel = idPersonnel;
		this.nom = nom;
		this.service = service;
		this.contactInfo = contactInfo;
		this.isdeleted = isdeleted;
	}



}
