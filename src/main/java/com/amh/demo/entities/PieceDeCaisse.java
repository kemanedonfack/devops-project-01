package com.amh.demo.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PieceDeCaisse implements Serializable{
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPieceDeCaisse;
    private Date dateEdition;
    private boolean isdeleted;
    
	@ManyToOne
	private Personnel personnel;
	
    @ManyToOne
	private Depense depense;
    
	public PieceDeCaisse() {
		super();
	}
	public PieceDeCaisse(Personnel personnel, Depense depense, int idPieceDeCaisse, Date dateEdition,
			boolean isdeleted) {
		super();
		this.personnel = personnel;
		this.depense = depense;
		this.idPieceDeCaisse = idPieceDeCaisse;
		this.dateEdition = dateEdition;
		this.isdeleted = isdeleted;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	public Depense getDepense() {
		return depense;
	}
	public void setDepense(Depense depense) {
		this.depense = depense;
	}
	public long getIdPieceDeCaisse() {
		return idPieceDeCaisse;
	}
	public void setIdPieceDeCaisse(long idPieceDeCaisse) {
		this.idPieceDeCaisse = idPieceDeCaisse;
	}
	public Date getDateEdition() {
		return dateEdition;
	}
	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}
	public boolean isIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
    
    
    
    
}
