package com.amh.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.Caisse;
import com.amh.demo.repository.CaisseRepository;

@Service
public class CaisseService {
	
	@Autowired
	private CaisseRepository caisseRepository;
	
	public List<Caisse> getAllCaisse(){
		return caisseRepository.findAll();
	}
	
	public void save(Caisse caisse) {
		caisseRepository.save(caisse);
	}
	
	public Caisse getCaisseById(Long id) {
		return caisseRepository.findById(id).get();
	}
	
	public void deleteCaisse(Long id) {
		caisseRepository.deleteById(id);
	}
	
	public long countCaisse() {
		return caisseRepository.count();
	}
	
	public List<Caisse> searchCaisse(String parameter){
		return caisseRepository.findByLibelleContains(parameter);
	}
	
	public int totalCaisseByLibelle(String libelleCaisse) {
		return Integer.valueOf(caisseRepository.totalCaisse(libelleCaisse));
	}
	

	public void saveEditCaisse(Caisse caisseData) {
		Caisse caisse = caisseRepository.findById(caisseData.getIdCaisse()).get();
		caisse.setCodeCaisse(caisseData.getCodeCaisse());
		caisse.setLibelle(caisseData.getLibelle());
		caisseRepository.flush();
	}


}
