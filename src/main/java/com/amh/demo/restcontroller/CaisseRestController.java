package com.amh.demo.restcontroller;

import com.amh.demo.entities.Caisse;
import com.amh.demo.services.ApproService;
import com.amh.demo.services.CaisseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/caisses")
public class CaisseRestController {

    @Autowired
    CaisseService caisseService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Caisse> getAllCaisses(){
        return caisseService.getAllCaisse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Caisse getCaissesById(@PathVariable Long id){
        return caisseService.getCaisseById(id);
    }

}
