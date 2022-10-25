package com.amh.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Depense;
import com.amh.demo.services.ApproService;
import com.amh.demo.services.CaisseService;
import com.amh.demo.services.DepenseService;
import com.amh.demo.services.GestionnaireService;
import com.amh.demo.services.PersonnelService;

@Controller
public class HomeController {
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
	
	
	@GetMapping(value = "/")
	public String viewHomePage(Model model) {
		long nCaisse=caisseService.countCaisse();
		int sommeCaisseDouala=caisseService.totalCaisseByLibelle("AMH Douala");
		int sommeCaisseYde=caisseService.totalCaisseByLibelle("AMH Yaounde");
		
		int sommeDepenseDouala=depenseService.sumDepenseByCaisse("AMH Douala");
		int sommeDepenseYde=depenseService.sumDepenseByCaisse("AMH Yaounde");
		//int sommeDepenseYde=12;
		
		int sommeApproDla=approService.getApproCaisseById(2);
		int sommeApproYde=approService.getApproCaisseById(1);
		
		long nGestionnaire=gestionnaireService.countGestionnaire();
		long nPersonnel=personnelService.countPersonnel();
		
		List<Depense> depenses=depenseService.getAllDepenses();
		model.addAttribute("depenses",depenses);
		
		List<ApprovisionnementCaisse> historiqueAppro=approService.getAllAppro();
		model.addAttribute("historiqueAppro",historiqueAppro);
		
		model.addAttribute("nbreCaisse", nCaisse);
		model.addAttribute("sommeCaisseDla", sommeCaisseDouala);
		model.addAttribute("sommeCaisseYde", sommeCaisseYde);
		
		model.addAttribute("sommeApproDla", sommeApproDla);
		model.addAttribute("sommeApproYde", sommeApproYde);
		
		model.addAttribute("sommeDepenseDouala", sommeDepenseDouala);
		model.addAttribute("sommeDepenseYde", sommeDepenseYde);
		
		model.addAttribute("nGestionnaire", nGestionnaire);
		model.addAttribute("nPersonnel", nPersonnel);
		return "index";
	}
	
}
