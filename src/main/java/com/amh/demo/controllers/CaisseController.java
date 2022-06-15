package com.amh.demo.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Caisse;
import com.amh.demo.entities.Depense;
import com.amh.demo.entities.GestionnaireCaisse;
import com.amh.demo.entities.Personnel;
import com.amh.demo.services.ApproService;
import com.amh.demo.services.CaisseService;
import com.amh.demo.services.DepenseService;
import com.amh.demo.services.GestionnaireService;
import com.amh.demo.services.PersonnelService;
import com.amh.demo.services.ReportService;

import net.sf.jasperreports.engine.JRException;

@Controller
public class CaisseController {

	@Autowired
	private CaisseService caisseService;

	@Autowired
	private GestionnaireService gestionnaireService;

	@Autowired
	private ApproService approService;

	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private DepenseService depenseService;

	@GetMapping("/caisse_search")
	public String viewCaissePageSearch(Model model,
			@RequestParam(name = "caisse", defaultValue = "") String parameter) {
		model.addAttribute("caisses", caisseService.searchCaisse(parameter));
		return "caisseView/listeCaisse";
	}

	@RequestMapping("/caisses")
	public String viewCaissePage(Model model) {
		List<Caisse> listCaisse = caisseService.getAllCaisse();
		model.addAttribute("caisses", listCaisse);
		return "caisseView/listeCaisse";
	}

	// Service : Approvisionnement des caisses

	@RequestMapping("/historique_approvisionnement")
	public String viewHistoriqueApproPage(Model model) {
		List<ApprovisionnementCaisse> historiqueAppro = approService.getAllAppro();
		model.addAttribute("historiqueAppro", historiqueAppro);
		return "caisseView/historiqueApprov";
	}

	@RequestMapping("/add_approvisionnement")
	public String viewApproCaissePage(Model model) {
		List<Caisse> caisses = caisseService.getAllCaisse();
		List<GestionnaireCaisse> gestionnaires = gestionnaireService.getAllGestionnaire();
		ApprovisionnementCaisse approCaisse = new ApprovisionnementCaisse();

		model.addAttribute("approCaisse", approCaisse);
		model.addAttribute("gestionnaires", gestionnaires);
		model.addAttribute("caisses", caisses);

		return "caisseView/provideCaisse";
	}

	@PostMapping("/insert_approvisionnement")
	public String saveApproCaisse(@ModelAttribute("approCaisse") ApprovisionnementCaisse approCaisse,
			RedirectAttributes redirAttrs) {
		approCaisse.setDateApprovisionnement(LocalDateTime.now());
		approCaisse.setLastMontantCaisse(
				caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).getMontantEnCaisse());
		caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse())
				.setMontantEnCaisse(approCaisse.getMontantApproCaisse()
						+ caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).getMontantEnCaisse());
		caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).setDateEtatCaisse(LocalDateTime.now());

		if (approService.save(approCaisse) == false) {
			redirAttrs.addFlashAttribute("error", "Une erreur est survenue...");
			return "redirect:/historique_approvisionnement";
		} else {
			redirAttrs.addFlashAttribute("success", "Approvisionnement de la caisse reussie avec succès...");
			return "redirect:/historique_approvisionnement";
		}

	}

	@PostMapping("/update_approCaisse")
	public String saveEditApproCaisse(@ModelAttribute("approCaisse") ApprovisionnementCaisse approCaisse,
			RedirectAttributes redirAttrs) {
		approCaisse.setDateApprovisionnement(LocalDateTime.now());
		
		approCaisse.setLastMontantCaisse(
				caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).getMontantEnCaisse());
		
		// Remets la caisse avant l'approvisionnement
		int AnnuleAppro=caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).getMontantEnCaisse()-approService.getApproCaisseById(approCaisse.getIdApprovisionnement()).getMontantApproCaisse();
		
		caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse())
				.setMontantEnCaisse(AnnuleAppro+approCaisse.getMontantApproCaisse());
		
		caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).setDateEtatCaisse(LocalDateTime.now());

		if (approService.save(approCaisse) == false) {
			redirAttrs.addFlashAttribute("error", "Une erreur est survenue...");
			return "redirect:/historique_approvisionnement";
		} else {
			redirAttrs.addFlashAttribute("success", "Modification effectuee de l'appro avec succes...");
			return "redirect:/historique_approvisionnement";
		}

	}

	@RequestMapping("/edit_approCaisse/{id}")
	public ModelAndView showEditApproCaissePage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("caisseView/editApproCaisse");
		ApprovisionnementCaisse approCaisse = approService.getApproById(id);
		List<Caisse> caisse = caisseService.getAllCaisse();
		List<GestionnaireCaisse> gestionnaires = gestionnaireService.getAllGestionnaire();

		mav.addObject("approCaisse", approCaisse);
		mav.addObject("gestionnaires", gestionnaires);
		mav.addObject("caisses", caisse);

		return mav;
	}
	
	@GetMapping("/delete_appro/{id}")
	public String deleteAppro(@PathVariable(name = "id") Long id, RedirectAttributes redirAttrs, ApprovisionnementCaisse approCaisse){
		approCaisse = approService.getApproById(id);
		
		int MontantAfterDeleted=caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse()).getMontantEnCaisse()-approCaisse.getMontantApproCaisse();
		caisseService.getCaisseById(approCaisse.getCaisse().getIdCaisse())
		.setMontantEnCaisse(MontantAfterDeleted);
		approService.deleteAppro(id);
		redirAttrs.addFlashAttribute("success", "Deleted successfully...");
		return "redirect:/historique_approvisionnement";
	}


	@RequestMapping("/print_appro_caise/{id}")
	public void imprimerApprovisionnementcaisse(@PathVariable(name = "id") Long id, HttpServletResponse response)
			throws JRException, IOException {
		depenseService.exportPdfApproCaisse(id, response);
	}

	// Service : sortie de caisse

	@RequestMapping("/historique_depenses")
	public String viewHistoriqueDepensePage(Model model) {
		List<Depense> depenses = depenseService.getAllDepenses();
		model.addAttribute("depenses", depenses);
		return "caisseView/histoDepense";
	}

	@GetMapping("/historique_depenses_search")
	public String viewHistoriqueDepensePageSearch(Model model,
			@RequestParam(name = "search", defaultValue = "") String parameter) {
		model.addAttribute("depenses", depenseService.searchHistoriqueDepense(parameter));
		return "caisseView/histoDepense";
	}

	@RequestMapping("/sortie_caisse")
	public String viewSortieCaissePage(Model model) {
		List<Caisse> caisses = caisseService.getAllCaisse();
		List<GestionnaireCaisse> gestionnaires = gestionnaireService.getAllGestionnaire();
		List<Personnel> personnel = personnelService.getAllPersonnel();

		Depense depense = new Depense();

		model.addAttribute("depenses", depense);
		model.addAttribute("gestionnaires", gestionnaires);
		model.addAttribute("personnel", personnel);
		model.addAttribute("caisses", caisses);

		return "caisseView/sortieCaisse";
	}

	@PostMapping("/insert_sortieCaisse")
	public String saveSortieCaisse(@ModelAttribute("depenses") Depense depense, RedirectAttributes redirAttrs) {
		depense.setDateDepense(LocalDateTime.now());
		caisseService.getCaisseById(depense.getCaisse().getIdCaisse())
				.setMontantEnCaisse(caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).getMontantEnCaisse()
						- depense.getMontant());
		caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).setDateEtatCaisse(LocalDateTime.now());

		if (depenseService.save(depense) == false) {
			redirAttrs.addFlashAttribute("error", "Une erreur est survenue...");
			return "redirect:/historique_depenses";
		} else {
			redirAttrs.addFlashAttribute("success", "Sauvegarde reussie avec succès...");
			return "redirect:/historique_depenses";
		}

	}

	@RequestMapping("/pieces_caisses")
	public String pieceCaissePage(Model model) {
		List<Depense> depenses = depenseService.getAllDepenses();
		model.addAttribute("depenses", depenses);
		return "caisseView/listPieceCaisse";
	}

	@RequestMapping("/etat_caisse")
	public String etatCaissePage() {
		return "";
	}

	@RequestMapping("/historique_depense_report")
	public void imprimerDepenseHistorique(HttpServletResponse response) throws JRException, IOException {
		depenseService.exportDepenseHistorique("xls", response);
	}

	@RequestMapping("/print_piece_caise/{id}")
	public void imprimerPieceDecaisse(@PathVariable(name = "id") Long id, HttpServletResponse response)
			throws JRException, IOException {
		depenseService.exportPdfPieceDeCaisse(id, response);
	}

	@RequestMapping("/add_caisse")
	public String getViewAddNewCaisse(Model model) {
		Caisse caisse = new Caisse();
		model.addAttribute("caisse", caisse);
		return "caisseView/addCaisse";
	}

	@PostMapping("/insert_caisse")
	public String saveCaisse(@ModelAttribute("caisse") Caisse caisse) {
		caisse.setDateEtatCaisse(LocalDateTime.now());
		caisseService.save(caisse);
		return "redirect:/caisses";
	}

	@RequestMapping("/edit_caisse/{id}")
	public ModelAndView showEditCaissePage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("caisseView/edit_caisse");
		Caisse caisse = caisseService.getCaisseById(id);
		mav.addObject("caisse", caisse);
		return mav;
	}

	@PostMapping("/save_editCaisse")
	public String saveEditCaisse(Caisse caisse) {
		caisse.setDateEtatCaisse(LocalDateTime.now());
		caisse.setCodeCaisse(caisse.getCodeCaisse());
		caisse.setLibelle(caisse.getLibelle());
		caisseService.saveEditCaisse(caisse);
		return "redirect:/caisses";
	}

	@RequestMapping("/edit_sortiecaisse/{id}")
	public String editSortieCaisse(@PathVariable(name = "id") Long id, Model model) {
		Depense depense = depenseService.getDepenseById(id);
		GestionnaireCaisse gestionnaire = depense.getGestionnaireCaisse();
		Caisse caisse = depense.getCaisse();
		Personnel personnel = depense.getPersonnel();

		model.addAttribute("depense", depense);
		model.addAttribute("gestionnaire", gestionnaire);
		model.addAttribute("caisse", caisse);
		model.addAttribute("personnel", personnel);
		return "caisseView/editSortieCaisse";
	}

	@PostMapping("/update_sortiecaisse")
	public String update_caisseSortie(Depense depense, RedirectAttributes redirAttrs) {

		depenseService.getDepenseById(depense.getIdDepense()).setDateDepense(LocalDateTime.now());
		int oldDepense = depenseService.getDepenseById(depense.getIdDepense()).getMontant();
		int depenseActuel = depense.getMontant();
		int montantCaisse = caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).getMontantEnCaisse();

		int beforeUpdateSolde = montantCaisse + oldDepense;
		int afterUpdateSolde = beforeUpdateSolde - depenseActuel;

		caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).setMontantEnCaisse(afterUpdateSolde);
		depenseService.updateCaisseSortie(depense);
		caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).setDateEtatCaisse(LocalDateTime.now());

		redirAttrs.addFlashAttribute("success", "Sortie modifiée dans votre caisse avec succes...");
		return "redirect:/historique_depenses";
	}

	@GetMapping("/delete_sortiecaisse/{id}")
	public String deleteAgent(@PathVariable(name = "id") Long id, RedirectAttributes redirAttrs)
			throws IllegalAccessException {
		Depense depense = depenseService.findDepensetById(id);
		int sommeDeleted = depense.getMontant();
		int montantCaisse = caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).getMontantEnCaisse();
		int newSolde = montantCaisse + sommeDeleted;

		caisseService.getCaisseById(depense.getCaisse().getIdCaisse()).setMontantEnCaisse(newSolde);
		depense.setIsdeleted(true);
		depenseService.deletedDepense(depense);
		redirAttrs.addFlashAttribute("success", "Sortie supprimée de la caisse avec succes...");
		return "redirect:/historique_depenses";
	}

}
