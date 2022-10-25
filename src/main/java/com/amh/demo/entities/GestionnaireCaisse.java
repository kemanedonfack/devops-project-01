package com.amh.demo.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class GestionnaireCaisse implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGestionnaire;
	private String utilisateur;
	private String password;
	private String role;
	private boolean isdeleted;
	
	@OneToMany(mappedBy = "gestionnaireCaisse")
    private Set<Depense> depenses;
	
	@OneToMany(mappedBy = "gestionnaireCaisse")
    private Set<ApprovisionnementCaisse> approvisionnementCaisse;
	
	@ManyToOne
    private Caisse caisse;
	
	public GestionnaireCaisse() {
		super();
	}

	public GestionnaireCaisse(String utilisateur, String password, String role, boolean isdeleted) {
		this.utilisateur = utilisateur;
		this.password = password;
		this.role = role;
		this.isdeleted = isdeleted;
	}

	public Long getIdGestionnaire() {
		return idGestionnaire;
	}

	public void setIdGestionnaire(Long idGestionnaire) {
		this.idGestionnaire = idGestionnaire;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public Caisse getCaisse() {
		return caisse;
	}

	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
	}

	
	
}
