package com.amh.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class JasperExport {

	String printFormat;
	String printFileName;
	HttpServletResponse response;
	List<Map<String, Object>> listDataToPrint;
	Map<String, Object> dataToPrint;
	String jrxmlFileName;
	String fileSavePath;

	public JasperExport(String printFormat, String printFileName, HttpServletResponse response,
			List<Map<String, Object>> dataToPrint, String jrxmlFileName, String fileSavePath) {
		super();
		this.printFormat = printFormat;
		this.printFileName = printFileName;
		this.response = response;
		this.listDataToPrint = dataToPrint;
		this.jrxmlFileName = jrxmlFileName;
		this.fileSavePath = fileSavePath;
	}

	public JasperExport(String printFormat, String printFileName, HttpServletResponse response,
			Map<String, Object> dataToPrint, String jrxmlFileName, String fileSavePath) {
		super();
		this.printFormat = printFormat;
		this.printFileName = printFileName;
		this.response = response;
		this.dataToPrint = dataToPrint;
		this.jrxmlFileName = jrxmlFileName;
		this.fileSavePath = fileSavePath;
	}

	public void export() throws JRException, IOException {
		File file = ResourceUtils.getFile("classpath:reporting/" + jrxmlFileName + ".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDataToPrint);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("CollectionBeanParam", dataSource);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

		if (printFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(fileSavePath + printFileName + ".hmtl");
		}
		if (printFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, fileSavePath + printFileName + ".pdf");
		}
		if (printFormat.equalsIgnoreCase("xls")) {
			JRXlsxExporter exporter = new JRXlsxExporter();
			SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
			reportConfigXLS.setSheetNames(new String[] { "sheet1" });
			exporter.setConfiguration(reportConfigXLS);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			response.setHeader("Content-Disposition", "attachment;filename=" + printFileName + ".xlsx");
			response.setContentType("application/octet-stream");
			exporter.exportReport();
		}
	}

	public void exportOrdrePaiement() throws JRException, IOException {
		File file = ResourceUtils.getFile("classpath:reporting/" + jrxmlFileName + ".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("CollectionBeanParam", dataToPrint);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

		if (printFormat.equalsIgnoreCase("pdf")) {
			JRPdfExporter exporterPDF = new JRPdfExporter();
			SimplePdfReportConfiguration reportConfigPDF = new SimplePdfReportConfiguration();
			reportConfigPDF.setSizePageToContent(true);
			reportConfigPDF.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("AMH");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");

			exporterPDF.setConfiguration(reportConfigPDF);
			exporterPDF.setConfiguration(exportConfig);
			exporterPDF.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			exporterPDF.setExporterInput(new SimpleExporterInput(jasperPrint));

			response.setHeader("Content-Disposition", "attachment;filename=" + printFileName + ".pdf");
			response.setContentType("application/octet-stream");
			exporterPDF.exportReport();

		}
	}
}
