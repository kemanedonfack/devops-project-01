package com.amh.demo;

import com.amh.demo.entities.*;
import com.amh.demo.repository.*;
import com.amh.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.time.LocalDateTime;

@SpringBootApplication
public class AmhChekoutAppApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	PersonnelService personnelService;
	@Autowired
	CaisseService caisseService;
	@Autowired
	GestionnaireService gestionnaireService;
	@Autowired
	DepenseService depenseService;
	@Autowired
	ApproService approService;

	public static void main(String[] args) {
		SpringApplication.run(AmhChekoutAppApplication.class, args);
	}
	
	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(AmhChekoutAppApplication.class);
	  }

	@Override
	public void run(String... args) throws Exception {

		GestionnaireCaisse gestionnaireCaisse = new GestionnaireCaisse("kemane@gmail.com", "kemane", "admin", false);
		GestionnaireCaisse gestionnaireCaisse1 = new GestionnaireCaisse("ivan@gmail.com", "ivan", "admin", false);

		Personnel personnel = new Personnel(1L, "kemane","DevOps","690710856",false);

		Caisse caisseDla = new Caisse(1L, "AMH Yaounde", "123", 1000000, LocalDateTime.now(), false, null, null, null);
		Caisse caisseYde = new Caisse(2L, "AMH Douala", "124", 2000000, LocalDateTime.now(), false, null, null, null);

		// ajout des gestionnaires
		gestionnaireCaisse1.setCaisse(caisseDla);
		gestionnaireCaisse.setCaisse(caisseYde);

		caisseService.save(caisseDla);
		caisseService.save(caisseYde);
		personnelService.save(personnel);
		gestionnaireService.save(gestionnaireCaisse);
		gestionnaireService.save(gestionnaireCaisse1);

		/*

		HashSet<PieceDeCaisse> pieceDeCaisses = new HashSet<>();
		pieceDeCaisses.add(pieceDeCaisse);

		Depense depense = new Depense("Taxi Kemane", 1000, LocalDateTime.now(), "Kemane", "234", false, gestionnaireCaisse, caisse, personnel, pieceDeCaisses);
		HashSet<Depense> depenses = new HashSet<>();
		depenses.add(depense);

		Caisse caisse = new Caisse(1L, "caisse_DLA", "caisse1", 1000, 2022, false, null, null, null);
		PieceDeCaisse pieceDeCaisse = new PieceDeCaisse(personnel,depense,1,new Date(12),false);

		gestionnaireCaisseRepository.save(gestionnaireCaisse);
		personnelRepository.save(personnel);
		caisseRepository.save(caisse);
		depenseRepository.save(depense);
		pieceDeCaisseRepository.save(pieceDeCaisse);

		depense.setIdDepense(depense.getIdDepense());*/



	}
}
 