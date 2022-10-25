package com.amh.demo.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Depense;
import com.amh.demo.repository.ApprovisionnementCaisseRepository;
import com.amh.demo.repository.DepenseRepository;

import net.sf.jasperreports.engine.JRException;

@Service
public class DepenseService {
	@Autowired
	private DepenseRepository depenseRepository;
	@Autowired
	ApprovisionnementCaisseRepository approvisionnementCaisseRepository;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

	public List<Depense> getAllDepenses() {
		return depenseRepository.findAll();
	}

	public boolean save(Depense depense) {
		depenseRepository.save(depense);
		return true;
	}

	public Depense findDepensetById(long id) {
		return depenseRepository.findById(id).get();
	}

	public Depense getDepenseById(Long id) {
		return depenseRepository.findById(id).get();
	}

	public void deleteDepense(Long id) {
		depenseRepository.deleteById(id);
	}

	public List<Depense> searchHistoriqueDepense(String parameter) {
		return depenseRepository.findByMotifContains(parameter);
	}

	public int sumDepenseByCaisse(String libCaisse) {
		Object x=depenseRepository.totalDepenseParCaisse(libCaisse);
		if(x==null) {
			return 0;
		}else {
			//return (int) depenseRepository.totalDepenseParCaisse(libCaisse);
			return Integer.valueOf(String.valueOf(x));
		}
		
	}

	public void deletedDepense(Depense depense) {
		depenseRepository.save(depense);
	}

	public void updateCaisseSortie(Depense depenseData) {
		Depense depense = depenseRepository.findById(depenseData.getIdDepense()).get();
		depense.setBeneficiaire(depenseData.getBeneficiaire());
		depense.setCaisse(depenseData.getCaisse());
		depense.setMotif(depenseData.getMotif());
		depense.setPersonnel(depenseData.getPersonnel());
		depense.setMontant(depenseData.getMontant());
		depense.setImputationComptable(depenseData.getImputationComptable());
		depenseRepository.flush();
	}

	public void exportDepenseHistorique(String printFormat, HttpServletResponse response)
			throws JRException, IOException {
		List<Depense> depenses = depenseRepository.findAll();
		List<Map<String, Object>> dataToPrint = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Depense depense : depenses) {
			map.put("montant", depense.getMontant());
			map.put("dateDepense", depense.getDateDepense());
			map.put("motif", depense.getMotif());
			map.put("agence", depense.getCaisse().getLibelle());
			map.put("codeCaisse", depense.getCaisse().getCodeCaisse());
			dataToPrint.add(map);
		}

		JasperExport jasper = new JasperExport(printFormat, "historique_depenses", response, dataToPrint,
				"historique_depense_report", "D:\\jasper\\");
		jasper.export();
	}

	public void exportPdfPieceDeCaisse(Long id, HttpServletResponse response) throws JRException, IOException {
		Depense depense = depenseRepository.findById(id).get();
		LocalDate dateActuelle = LocalDate.now();
		String fileName = "piece_caisse_" + depense.getPersonnel().getNom() + "_" + dateFormatter.format(dateActuelle);
		Map<String, Object> dataToPrint = new HashMap<String, Object>();
		dataToPrint.put("employe", depense.getPersonnel().getNom());
		dataToPrint.put("beneficiaire", depense.getBeneficiaire());
		dataToPrint.put("motif", depense.getMotif());
		dataToPrint.put("montant", depense.getMontant());
		dataToPrint.put("dateEnregistrement", dateFormatter.format(depense.getDateDepense()));
		dataToPrint.put("gestionnaire", depense.getGestionnaireCaisse().getUtilisateur());
		dataToPrint.put("numeroPiece",
				"D" + String.valueOf(dateActuelle.getYear()) + String.valueOf(dateActuelle.getMonthValue())
						+ String.valueOf(dateActuelle.getDayOfMonth()) + depense.getIdDepense());
		dataToPrint.put("rootPath", getUsersProjectRootDirectory());

		JasperExport jasper = new JasperExport("pdf", fileName, response, dataToPrint, "ordre_paiement_espece_report",
				"D:\\jasper\\");

		jasper.exportOrdrePaiement();

	}

	public void exportPdfApproCaisse(Long id, HttpServletResponse response) throws JRException, IOException {
		ApprovisionnementCaisse approvisionnment = approvisionnementCaisseRepository.findById(id).get();
		LocalDate dateActuelle = LocalDate.now();
		String fileName = "appro_caisse_" + dateFormatter.format(dateActuelle);
		Map<String, Object> dataToPrint = new HashMap<String, Object>();
		dataToPrint.put("employe", approvisionnment.getGestionnaireCaisse().getUtilisateur());
		dataToPrint.put("motif", "approvisionnement caisse");
		dataToPrint.put("montant", approvisionnment.getMontantApproCaisse());
		dataToPrint.put("dateEnregistrement", dateFormatter.format(approvisionnment.getDateApprovisionnement()));
		dataToPrint.put("gestionnaire", approvisionnment.getGestionnaireCaisse().getUtilisateur());
		dataToPrint.put("numeroPiece",
				"A" + String.valueOf(dateActuelle.getYear()) + String.valueOf(dateActuelle.getMonthValue())
						+ String.valueOf(dateActuelle.getDayOfMonth()) + approvisionnment.getIdApprovisionnement());
		dataToPrint.put("rootPath", getUsersProjectRootDirectory());

		JasperExport jasper = new JasperExport("pdf", fileName, response, dataToPrint, "ordre_appro_caisse_report",
				"D:\\jasper\\");

		jasper.exportOrdrePaiement();

	}

	public String getUsersProjectRootDirectory() {
		String envRootDir = System.getProperty("user.dir");
		Path rootDIr = Paths.get(".").normalize().toAbsolutePath();
		if (rootDIr.startsWith(envRootDir)) {
			return envRootDir;
		} else {
			throw new RuntimeException("Root dir not found in user directory.");
		}
	}

	public List<Depense> getHistoriqueDepense(int idCaisse) {
		return depenseRepository.getHistoDepense(idCaisse);
	}

	public int getTotalDepense(int idCaisse){
		Object x=depenseRepository.totalDepense(idCaisse);
		if(x==null) {
			return 0;
		}else {
			//return (int) depenseRepository.totalDepenseParCaisse(libCaisse);
			return Integer.valueOf(String.valueOf(x));
		}
	}
}
