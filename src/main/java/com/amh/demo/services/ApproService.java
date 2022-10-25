package com.amh.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.repository.ApprovisionnementCaisseRepository;

@Service
public class ApproService {
	@Autowired
	private ApprovisionnementCaisseRepository approvisionnementCaisseRepository;

	public List<ApprovisionnementCaisse> getAllAppro() {
		return approvisionnementCaisseRepository.getAllApprovisionnement();
	}

	public boolean save(ApprovisionnementCaisse approvisionnementCaisse) {
		approvisionnementCaisseRepository.save(approvisionnementCaisse);
		return true;
	}

	public ApprovisionnementCaisse getApproById(Long id) {
		return approvisionnementCaisseRepository.findById(id).get();
	}

	public void deleteAppro(Long id) {
		approvisionnementCaisseRepository.deleteById(id);
	}

	public ApprovisionnementCaisse getApproCaisseById(Long id) {
		return approvisionnementCaisseRepository.findById(id).get();
	}

	public int getApproCaisseById(int idCaisse) {
		Object val = approvisionnementCaisseRepository.totalApproParCaisse(idCaisse);
		if (val == null) {
			return 0;
		} else {
			return Integer.valueOf(String.valueOf(val));
		}
	}

	public List<ApprovisionnementCaisse> getHistoAppro(int idCaisse) {
		return approvisionnementCaisseRepository.getHistoApprovisionnement(idCaisse);
	}

}
