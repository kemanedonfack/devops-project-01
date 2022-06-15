package com.amh.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amh.demo.entities.Personnel;
import com.amh.demo.repository.PersonnelRepository;
import com.amh.demo.services.PersonnelService;

@Controller
public class PersonnelController {

	@Autowired
	PersonnelService personnelService;

	@RequestMapping("/personnel")
	public String getViewListingPersonnel(Model model) {
		model.addAttribute("personnels", personnelService.getAllPersonnel());
		return "personnel/listingPersonnel";
	}

	@GetMapping("/personnel_search")
	public String getViewListingPersonnelSearch(Model model,
			@RequestParam(name = "motCle", defaultValue = "") String parameter) {
		model.addAttribute("personnels", personnelService.searchPersonnel(parameter));
		return "personnel/listingPersonnel";
	}

	@RequestMapping("/add_personnel")
	public String getViewAddPersonnel(Model model) {
		Personnel personnel = new Personnel();
		model.addAttribute("personnel", personnel);
		return "personnel/addPersonnelForm";
	}

	@PostMapping("/insert_personnel")
	public String savePersonnel(@ModelAttribute("personnel") Personnel personnel) {
		personnelService.save(personnel);
		return "redirect:/personnel";
	}

	@RequestMapping("/edit_personnel/{id}")
	public ModelAndView showEditPersonnel(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("personnel/edit_personnel");
		Personnel personnel = personnelService.getPersonnelById(id);
		mav.addObject("personnel", personnel);

		return mav;
	}

	@RequestMapping("/deletePersonnel/{id}")
	public String deletePersonnel(@PathVariable(name = "id") Long id,RedirectAttributes redirAttrs) {
		Personnel personnel = personnelService.getPersonnelById(id);
		personnel.setIsdeleted(true);
		personnelService.save(personnel);
		redirAttrs.addFlashAttribute("success", "Deleted successfully...");
		return "redirect:/personnel";
	}

}
