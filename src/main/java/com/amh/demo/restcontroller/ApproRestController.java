package com.amh.demo.restcontroller;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Caisse;
import com.amh.demo.services.ApproService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/approvisionnement")
public class ApproRestController {

    @Autowired
    ApproService approService;

    @GetMapping(value = "/{idCaisse}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getTotalApprovisionnement(@PathVariable int idCaisse){
        return approService.getApproCaisseById(idCaisse);
    }

    @GetMapping(value = "/{idCaisse}/historique", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApprovisionnementCaisse> getHistoApprovisionnement(@PathVariable int idCaisse){
        return approService.getHistoAppro(idCaisse);
    }


}
