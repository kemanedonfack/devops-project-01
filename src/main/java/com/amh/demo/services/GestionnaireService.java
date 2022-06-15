package com.amh.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.GestionnaireCaisse;
import com.amh.demo.repository.GestionnaireCaisseRepository;

@Service
public class GestionnaireService {
	@Autowired
	private GestionnaireCaisseRepository gestionnaireCaisseRepository;

	public List<GestionnaireCaisse> getAllGestionnaire() {
		return gestionnaireCaisseRepository.findAll();
	}

	public void save(GestionnaireCaisse gestionnaireCaisse) {
		gestionnaireCaisseRepository.save(gestionnaireCaisse);
	}

	public GestionnaireCaisse getApproById(Long id) {
		return gestionnaireCaisseRepository.findById(id).get();
	}

	public void deleteGestionnaire(Long id) {
		gestionnaireCaisseRepository.deleteById(id);
	}

	public GestionnaireCaisse findGestionnaireById(long id) {
		return gestionnaireCaisseRepository.findById(id).get();
	}

	public void updateGestionnaireCaisse(GestionnaireCaisse gestionnaire) {
		gestionnaireCaisseRepository.save(gestionnaire);
	}

	public void deleteGestionnaireCaisse(GestionnaireCaisse gestionnaire) {
		gestionnaireCaisseRepository.save(gestionnaire);
	}

	public long countGestionnaire() {
		return gestionnaireCaisseRepository.count();
	}

	public GestionnaireCaisse getGestionnaireByName(String utilisateur) {
		return gestionnaireCaisseRepository.findByUtilisateur(utilisateur);
	}

	public String getGestionnaireByNameExists(String utilisateur) {
		try {
			return gestionnaireCaisseRepository.findByUserExists(utilisateur);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
