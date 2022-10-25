package com.amh.demo.restcontroller;

import com.amh.demo.entities.ApprovisionnementCaisse;
import com.amh.demo.entities.Depense;
import com.amh.demo.services.ApproService;
import com.amh.demo.services.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/depenses")
public class DepenseRestController {

    @Autowired
    DepenseService depenseService;

    @GetMapping(value = "/{idCaisse}/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getTotalDepense(@PathVariable int idCaisse){
        return depenseService.getTotalDepense(idCaisse);
    }

    @GetMapping(value = "/{idCaisse}/historique", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Depense> getHistoriqueDepense(@PathVariable int idCaisse){
        return depenseService.getHistoriqueDepense(idCaisse);
    }


}
