package com.amh.demo.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.amh.demo.entities.Depense;
import com.amh.demo.repository.DepenseRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;

@Service
public class ReportService {
	
	@Autowired
	private DepenseRepository depenseRepository;
	
	public String exportReportPieceDeCaisse(String reportFormat) throws FileNotFoundException, JRException {
		List<Depense> depenses=depenseRepository.findAll();
		String path="C:\\Users\\AMH Consulting Group\\Desktop";
		File file=ResourceUtils.getFile("classpath:pieceReport.jrxml");
		JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(depenses);
		Map<String, Object> parameters=new HashMap<>();
		parameters.put("Created by", "AMHCaisseApp");
		JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, parameters,dataSource);
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\pieceDeCaisse.pdf");
		}
		return path+"\\pieceDeCaisse.pdf";
		
	}
}
