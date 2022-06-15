package com.amh.demo.controllers;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Caisse;
import com.amh.demo.entities.GestionnaireCaisse;
import com.amh.demo.entities.UserPincipal;
import com.amh.demo.services.CaisseService;
import com.amh.demo.services.GestionnaireService;
import com.amh.demo.services.UserCaisseService;

@Controller
public class GestionnaireController {
	@Autowired
	GestionnaireService gestionnaireService;

	@Autowired
	CaisseService caisseService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/gestionnaire")
	public String getViewListingGestionnaire(Model model) {
		model.addAttribute("gestionnaires", gestionnaireService.getAllGestionnaire());
		return "gestionnaire/listingGestionnaire";
	}

	@GetMapping("/gestionnaire/addForm")
	public String getViewAddGestionnaire(Model model) {
		GestionnaireCaisse gestionnaireCaisse = new GestionnaireCaisse();
		List<Caisse> caisses = caisseService.getAllCaisse();

		model.addAttribute("gestionnaireCaisse", gestionnaireCaisse);
		model.addAttribute("caisses", caisses);
		return "gestionnaire/addGestionnaireForm";
	}

	@GetMapping("/gestionnaire_updateForm/{id}")
	public String getViewUpdateGestionnaire(@PathVariable(name = "id") Long id, Model model) {

		GestionnaireCaisse gestionnaire = gestionnaireService.findGestionnaireById(id);
		List<Caisse> caisses = caisseService.getAllCaisse();

		model.addAttribute("gestionnaire", gestionnaire);
		model.addAttribute("caisses", caisses);

		return "gestionnaire/updateGestionnaireForm";
	}

	@PostMapping("/gestionnaire/add")
	public String submitAddForm(@ModelAttribute("gestionnaire") GestionnaireCaisse gestionnaire, Model model,
			RedirectAttributes redirectAttributes) {
		/*Verification de l'existance du gestionnaire dans le système avant creation*/
		
		if (gestionnaireService.getGestionnaireByNameExists(gestionnaire.getUtilisateur())==null) {
			String encodedPassword = passwordEncoder.encode(gestionnaire.getPassword());

			GestionnaireCaisse g = new GestionnaireCaisse();
			g.setCaisse(gestionnaire.getCaisse());
			g.setRole(gestionnaire.getRole());
			g.setUtilisateur(gestionnaire.getUtilisateur());
			g.setPassword(encodedPassword);
			g.setIsdeleted(false);
			gestionnaireService.save(g);
			
			redirectAttributes.addFlashAttribute("success", "Nouvel utilisateur dans l'application...");
			return "redirect:/gestionnaire";
		} else {
			redirectAttributes.addFlashAttribute("danger", "Un utilisateur existe avec ce nom...");
			return "redirect:/gestionnaire";
		}

	}
	
	// Affichage du formulaire de modification
	@PostMapping("/gestionnaire/update")
	public String submitUpdateForm(@ModelAttribute("gestionnaire") GestionnaireCaisse gestionnaire) {
		gestionnaireService.updateGestionnaireCaisse(gestionnaire);
		return "redirect:/gestionnaire";
	}

	@GetMapping("/gestionnaire_updatepassword")
	public String getViewUpdatePwdGestionnaire(@AuthenticationPrincipal UserPincipal u, Model model) {
		GestionnaireCaisse gestionnaire = gestionnaireService.getGestionnaireByName(u.getUsername());
		model.addAttribute("gestionnaireInfo", gestionnaire);
		return "gestionnaire/updatePwdGestionnaireForm";
	}

	@PostMapping("/gestionnaire/updateprofiluser")
	public String submitUpdateProfilForm(GestionnaireCaisse gestionnaire, @AuthenticationPrincipal UserPincipal u,
			RedirectAttributes redirectAttributes) {

		if (gestionnaire.getPassword().isEmpty()) {
			gestionnaire.setPassword(u.getPassword());
			gestionnaireService.updateGestionnaireCaisse(gestionnaire);
			redirectAttributes.addFlashAttribute("success",
					"Pas de modification majeure prise en compte par l'application....");
			return "redirect:/gestionnaire_updatepassword";
		} else {
			gestionnaire.setPassword(passwordEncoder.encode(u.getPassword()));
			gestionnaireService.updateGestionnaireCaisse(gestionnaire);
			redirectAttributes.addFlashAttribute("success",
					"Modification du mot de passe reussie.Vous vous authentifiez desormais avec nos nouveaux identifiants...");
			return "redirect:/gestionnaire_updatepassword";
		}
	}

	@GetMapping("/gestionnaire/delete/{id}")
	public String deleteGestionnaire(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {
		GestionnaireCaisse gestionnaire = gestionnaireService.findGestionnaireById(id);
		gestionnaire.setIsdeleted(true);
		gestionnaireService.deleteGestionnaireCaisse(gestionnaire);
		redirectAttributes.addFlashAttribute("success", "Utilisateur mise en corbeille dans l'application...");
		return "redirect:/gestionnaire";
	}
}
