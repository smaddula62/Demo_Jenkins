/*******************************************************************************
 *
 * Project : Miracle Supply Chain Visibility Portal v1.0
 *
 * Package : com.mss.ediscv.griddownload
 *
 * Date    : april 17, 2013 5:22:19 pm
 *
 * Author  : Santish kola <skola2@miraclesoft.com>
 *
 * File    : GridDownloadAction.java
 *
 * 
 * *****************************************************************************
 */



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.griddownload;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.mss.ediscv.doc.DocRepositoryBean;


import com.mss.ediscv.documentVisibility.DocumentVisibilityBean;
import com.mss.ediscv.editracking.TrackInOutBean;
import com.mss.ediscv.inv.InvoiceBean;
import com.mss.ediscv.logisticeditracking.LogisticTrackInOutBean;
import com.mss.ediscv.logisticreports.LogisticReportsBean;
import com.mss.ediscv.logisticsdoc.LogisticsDocBean;
import com.mss.ediscv.logisticsinvoice.LogisticsInvoiceBean;
import com.mss.ediscv.logisticsloadtendering.LogisticsLoadBean;
import com.mss.ediscv.logisticsshipment.LtShipmentBean;
import com.mss.ediscv.ltResponse.LtResponseBean;
import com.mss.ediscv.payments.PaymentBean;
import com.mss.ediscv.po.PurchaseOrderBean;
import com.mss.ediscv.reports.ReportsBean;
import com.mss.ediscv.shipment.ShipmentBean;
import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.Properties;
import com.mss.ediscv.util.ServiceLocator;
import com.mss.ediscv.util.ServiceLocatorException;
import com.opensymphony.xwork2.Action;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;



//start

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author Ajay Tummapala <atummapala@miraclesoft.com>
 *
 * This Class.... ENTER THE DESCRIPTION HERE
 */
public class GridDownloadAction implements
        Action,ServletRequestAware,ServletResponseAware{
    
   
    // private String URL="/images/flower.GIF";
    private String contentDisposition="FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private String fileName;
    private String downloadType;
    private String sheetType;
     private String inbound;
    private String outbound;
   private int scheduleId;
  private String reportattachment;
  private String startDate;
    public String getInbound() {
        return inbound;
    }

    public void setInbound(String inbound) {
        this.inbound = inbound;
    }

    public String getOutbound() {
        return outbound;
    }

    public void setOutbound(String outbound) {
        this.outbound = outbound;
    }
    /** Creates a new instance of DownloadAction */
    public GridDownloadAction() {
    }
    
    @Override
    public String execute() throws Exception {
        return null;
    }
    
    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    
    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        
        String responseString="";
        try {
         
            String fileLocation ="";
            //For creating Excel grind from Search result Grid
            System.out.println("getSheetType-->"+getSheetType());
            if(getSheetType().equals("document")&&getDownloadType().equals("xls")) {
            fileLocation = docExcelDownload();
            } else if(getSheetType().equals("documentReport")&&getDownloadType().equals("xls")) {
            fileLocation = docReportExcelDownload();
            }
            else if(getSheetType().equals("trackSummary")&&getDownloadType().equals("xls")) {
            fileLocation = docReportTrackingSummaryExcelDownload();
            }
            else if(getSheetType().equals("trackInOut")&&getDownloadType().equals("xls")) {
            fileLocation = docReportTrackingInOutExcelDownload();
            }
            
            else if(getSheetType().equals("trackInquiry")&&getDownloadType().equals("xls")) {
            fileLocation = docReportTrackingInquiryExcelDownload();
            }
            //documentReport/
            else if(getSheetType().equals("po")&&getDownloadType().equals("xls")) {
            fileLocation = poExcelDownload();
            }
            else if(getSheetType().equals("shipment")&&getDownloadType().equals("xls")) {
           fileLocation = shipmentExcelDownload();
            }
              else if(getSheetType().equals("invoice")&&getDownloadType().equals("xls")) {
           fileLocation = invoiceExcelDownload();
            }
             else if(getSheetType().equals("payment")&&getDownloadType().equals("xls")) {
           fileLocation = paymentExcelDownload();
            }
              else if(getSheetType().equals("logisticsDoc")&&getDownloadType().equals("xls")) {
           fileLocation = logisticsDocExcelDownload();
            }
               else if(getSheetType().equals("loadTendering")&&getDownloadType().equals("xls")) {
           fileLocation = loadTenderingExcelDownload();
            }
                else if(getSheetType().equals("ltResponse")&&getDownloadType().equals("xls")) {
           fileLocation = ltResponseExcelDownload();
            }
                  else if(getSheetType().equals("ltShipment")&&getDownloadType().equals("xls")) {
           fileLocation = ltShipmentExcelDownload();
            }
                else if(getSheetType().equals("ltInvoice")&&getDownloadType().equals("xls")) {
           fileLocation = ltInvoiceExcelDownload();
            }
            
            //
             else if(getSheetType().equals("docVisibility")&&getDownloadType().equals("xls")) {
           fileLocation = docVisibilityExcelDownload();
            }
           else if(getSheetType().equals("dash")&&getDownloadType().equals("xls")) {
           fileLocation = dashVisibilityExcelDownload(getInbound(),getOutbound());
            }
            else if(getSheetType().equals("dash")&&getDownloadType().equals("pdf")) {
           fileLocation = dashVisibilityPdfDownload(getInbound(),getOutbound());
            }
          // new excel sheets for logstic reports  
          else if(getSheetType().equals("logistictrackInOut")&&getDownloadType().equals("xls")) {
            fileLocation = logisticReportTrackingInOutExcelDownload();
            }   
           else if(getSheetType().equals("logistictrackInquiry")&&getDownloadType().equals("xls")) {
            fileLocation = logisticReportTrackingInquiryExcelDownload();
            }
              else if(getSheetType().equals("logistictrackSummary")&&getDownloadType().equals("xls")) {
            fileLocation = logisticReportTrackingSummaryExcelDownload();
            }
             else if(getSheetType().equals("logisticsReport")&&getDownloadType().equals("xls")) {
           fileLocation = logisticsReportsExcelDownload();
            }
            httpServletResponse.setContentType("application/force-download");
           // File file = new File(Properties.getProperty("mscvp.docCreationPath")+"SearchedExcelDocument.xls");
            
            System.out.println("file-->"+fileLocation);
                    File file = new File(fileLocation); 
                   Date date = new Date();
                   
                   fileName = (date.getYear()+1900)+"_"+(date.getMonth()+1)+"_"+date.getDate()+"_"+file.getName(); 
                 //  fileName = file.getName();
            if(file.exists()){
            inputStream = new FileInputStream(file);
            outputStream =  httpServletResponse.getOutputStream();
            httpServletResponse.setHeader("Content-Disposition","attachment;filename=\"" + fileName +"\"");
            int noOfBytesRead = 0;
            byte[] byteArray = null;
            while(true) {
                byteArray = new byte[1024];
                noOfBytesRead = inputStream.read(byteArray);
                if(noOfBytesRead==0) break;
                outputStream.write(byteArray, 0, noOfBytesRead);
            }
            responseString = "downLoaded!!";
                httpServletResponse.setContentType(getDownloadType());
                httpServletResponse.getWriter().write(responseString);
            }else{
                throw new FileNotFoundException("File not found");
            } 
        }catch (FileNotFoundException ex) {
            try {
                httpServletResponse.sendRedirect("../general/exception.action?exceptionMessage='No File found'");
            } catch (IOException ex1) {
                Logger.getLogger(GridDownloadAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }/*catch (ServiceLocatorException ex) {
            ex.printStackTrace();
        }*/finally {
            try {
                
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
        
    }
    
    
    
    /*
     * Method for Excel Format Document Download
     */
    
    public String docExcelDownload() {
String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
                    
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocRepository.xls");
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"DocRepository.xls");
            filePath = file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocRepository.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("DocRepository");
            HSSFRow row1;
            DocRepositoryBean docRepositoryBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Doc Repositry:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("FileFormat");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Trans_Type");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
             HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Reprocess");
             cellh1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            docRepositoryBean = (DocRepositoryBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(docRepositoryBean.getFile_id());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(docRepositoryBean.getFile_origin());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(docRepositoryBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           //cellD1.setCellValue(docRepositoryBean.getDate_time_rec());  
                         cellD1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(docRepositoryBean.getTransaction_type());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        
                          cellF1.setCellValue(docRepositoryBean.getDirection());   
                        
                        HSSFCell cellG1 = row1.createCell((short) 6);
                        
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                        
                        if(docRepositoryBean.getStatus()!= null) {
                               if(docRepositoryBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(docRepositoryBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        }
                        
                        
                    HSSFCell cellH1 = row1.createCell((short) 7);
                    
                 cellH1.setCellValue(docRepositoryBean.getReProcessStatus());      
             }
              
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
     /*
     * Method for Excel Format PO Download
     */
    
    public String poExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_PO_LIST);
                    
            File file = new File(Properties.getProperty("mscvp.poCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"Po.xls");
            filePath = file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"Po.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("PurchaseOrder");
            HSSFRow row1;
            PurchaseOrderBean purchaseOrderBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Purchase Order:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("PO #");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("PO Date");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("Partner");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("GS_Control #");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
               HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Reprocess");
             cellh1.setCellStyle(cellStyle);
             for (int i = 0; i < list.size(); i++) {
            purchaseOrderBean = (PurchaseOrderBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(purchaseOrderBean.getFileId());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(purchaseOrderBean.getPo());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(purchaseOrderBean.getPoDate());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(purchaseOrderBean.getPname());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(purchaseOrderBean.getGsControlNumber());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        
                          cellF1.setCellValue(purchaseOrderBean.getDirection());   
                        
                        HSSFCell cellG1 = row1.createCell((short) 6);
                        
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                        if(purchaseOrderBean.getStatus()!=null) {
                               if(purchaseOrderBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(purchaseOrderBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(purchaseOrderBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(purchaseOrderBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(purchaseOrderBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        }
                         HSSFCell cellH1 = row1.createCell((short) 7);
                        
                          cellH1.setCellValue(purchaseOrderBean.getReProcessStatus());   
                        
             }
                     
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
     /*
     * Method for Excel Format Shipment Download
     */
    
    public String shipmentExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_SHIPMENT_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.shipmentCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Shipment.xls");
              
            //filePath = file.getAbsolutePath()+"/Shipment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"Shipment.xls");
            filePath = file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"Shipment.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Shipment");
            HSSFRow row1;
            ShipmentBean shipmentBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Shipment :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("ASN #");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Direction");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Status");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("ACK Status");
             cellg1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            shipmentBean = (ShipmentBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(shipmentBean.getFile_id());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(shipmentBean.getAsnNo());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(shipmentBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(shipmentBean.getDate_time_rec().toString().substring(0, shipmentBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(shipmentBean.getDirection());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        
                         if(shipmentBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(shipmentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellF1.setCellValue(shipmentBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellF1.setCellStyle(cellStyle1);
                          
                        }else if(shipmentBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellF1.setCellValue(shipmentBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellF1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellF1.setCellValue(shipmentBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellF1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellG1 = row1.createCell((short) 6);
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                          
                         if(shipmentBean.getAckStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(shipmentBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(shipmentBean.getAckStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(shipmentBean.getAckStatus().equalsIgnoreCase("REJECTED")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(shipmentBean.getAckStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(shipmentBean.getAckStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    /*
     * Method for Excel Format Invoice Download
     */
    
    public String invoiceExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_INV_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.invoiceCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Invoice.xls");
              
           // filePath = file.getAbsolutePath()+"/Invoice.xls";
                
            //for XP
             //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"Invoice.xls");
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"Invoice.xls");
            filePath = file.getAbsolutePath()+ File.separator+"Invoice.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Invoice");
            HSSFRow row1;
            InvoiceBean invoiceBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Invoice :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Partner");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Invoice#");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("PO#");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Item Qty");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Invoice Amount");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Invoice Date");
             cellg1.setCellStyle(cellStyle);
              HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Status");
             cellh1.setCellStyle(cellStyle);
             HSSFCell celli1 = row1.createCell((short) 8);
             celli1.setCellValue("Ack Status");
             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            invoiceBean = (InvoiceBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(invoiceBean.getFileId());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(invoiceBean.getPname());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(invoiceBean.getInvNumber());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(invoiceBean.getPoNumber());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(invoiceBean.getItemQty());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(invoiceBean.getInvAmount());  
                          HSSFCell cellG1 = row1.createCell((short) 6);
                        cellG1.setCellValue(invoiceBean.getDate_time_rec().toString().substring(0, invoiceBean.getDate_time_rec().toString().lastIndexOf(":"))); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellH1 = row1.createCell((short) 7);
                         if(invoiceBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(invoiceBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellH1.setCellValue(invoiceBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellH1.setCellStyle(cellStyle1);
                          
                        }else if(invoiceBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellH1.setCellValue(invoiceBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellH1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellH1.setCellValue(invoiceBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellH1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellI1 = row1.createCell((short) 8);
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                          
                         if(invoiceBean.getAckStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(invoiceBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellI1.setCellValue(invoiceBean.getAckStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellI1.setCellStyle(cellStyle1);
                          
                        }else if(invoiceBean.getAckStatus().equalsIgnoreCase("REJECTED")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellI1.setCellValue(invoiceBean.getAckStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellI1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellI1.setCellValue(invoiceBean.getAckStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellI1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
                    e.printStackTrace();
                }
        return filePath;
    }
    
 /*
     * Method for Excel Format Invoice Download
     */
    
    public String paymentExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_PAYMENT_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.paymentCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"Payment.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"Payment.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Payment");
            HSSFRow row1;
            PaymentBean paymentBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Payment :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Partner");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Instance Id");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("PO#");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("Invoice#");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Date");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Cheque#");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Cheque Amount");
             cellg1.setCellStyle(cellStyle);
              HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Status");
             cellh1.setCellStyle(cellStyle);
             HSSFCell celli1 = row1.createCell((short) 8);
             celli1.setCellValue("Ack Status");
             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            paymentBean = (PaymentBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(paymentBean.getReceiverName());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(paymentBean.getFileId());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(paymentBean.getPonumber());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(paymentBean.getInvNumber());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(paymentBean.getDate());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(paymentBean.getCheckNumber());  
                          HSSFCell cellG1 = row1.createCell((short) 6);
                        cellG1.setCellValue(paymentBean.getCheckAmount()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellH1 = row1.createCell((short) 7);
                         if(paymentBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(paymentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellH1.setCellValue(paymentBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellH1.setCellStyle(cellStyle1);
                          
                        }else if(paymentBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellH1.setCellValue(paymentBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellH1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellH1.setCellValue(paymentBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellH1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellI1 = row1.createCell((short) 8);
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                          
                         if(paymentBean.getAckStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(paymentBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellI1.setCellValue(paymentBean.getAckStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellI1.setCellStyle(cellStyle1);
                          
                        }else if(paymentBean.getAckStatus().equalsIgnoreCase("REJECTED")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellI1.setCellValue(paymentBean.getAckStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellI1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellI1.setCellValue(paymentBean.getAckStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellI1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
/*
     * Method for Excel Format Logistics DocRepository Download
     */
    
    public String logisticsDocExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LOG_DOC_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.logisticsDocCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"logisticsDoc.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"logisticsDoc.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("logisticsDoc");
            HSSFRow row1;
            LogisticsDocBean logisticsDocBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
           HSSFCell cellpo7 = row1.createCell((short) 7);
//            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Logistics Document :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("FileFormat");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("InstanceId");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("TransType");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
             HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Shipment");
             cellh1.setCellStyle(cellStyle);
//             HSSFCell celli1 = row1.createCell((short) 8);
//             celli1.setCellValue("Ack Status");
//             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            logisticsDocBean = (LogisticsDocBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(logisticsDocBean.getFile_type());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(logisticsDocBean.getFile_id());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(logisticsDocBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(logisticsDocBean.getDate_time_rec().toString().substring(0, logisticsDocBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(logisticsDocBean.getTransaction_type());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(logisticsDocBean.getDirection());  
//                          HSSFCell cellG1 = row1.createCell((short) 6);
//                        cellG1.setCellValue(logisticsDocBean.getCheckAmount()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellG1 = row1.createCell((short) 6);
                         if(logisticsDocBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(logisticsDocBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(logisticsDocBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellH1 = row1.createCell((short) 7);
                        cellH1.setCellValue(logisticsDocBean.getShipmentId()); 

             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    /*
     * Method for Excel Format Load Tendering Download
     */
    
    public String loadTenderingExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LOAD_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.loadTenderingCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
        
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"loadTendering.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"loadTendering.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("loadTendering");
            HSSFRow row1;
            LogisticsLoadBean logisticsLoadBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
           HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("LoadTendering :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("FileFormat");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("InstanceId");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("TransType");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
              HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Shipment");
             cellh1.setCellStyle(cellStyle);
            HSSFCell celli1 = row1.createCell((short) 8);
            celli1.setCellValue("Repocess");
            celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            logisticsLoadBean = (LogisticsLoadBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(logisticsLoadBean.getFile_type());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(logisticsLoadBean.getFile_id());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(logisticsLoadBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(logisticsLoadBean.getDate_time_rec().toString().substring(0, logisticsLoadBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(logisticsLoadBean.getTransaction_type());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(logisticsLoadBean.getDirection());  
//                          HSSFCell cellG1 = row1.createCell((short) 6);
//                        cellG1.setCellValue(logisticsDocBean.getCheckAmount()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellG1 = row1.createCell((short) 6);
                         if(logisticsLoadBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(logisticsLoadBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(logisticsLoadBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(logisticsLoadBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(logisticsLoadBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(logisticsLoadBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellH1 = row1.createCell((short) 7);
                        cellH1.setCellValue(logisticsLoadBean.getShipmentId()); 
                         HSSFCell cellI1 = row1.createCell((short) 8);
                        cellI1.setCellValue(logisticsLoadBean.getReProcessStatus());

             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    /*
     * Method for Excel Format Load Tendering Download
     */
    
    public String ltResponseExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LTRESPONSE_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.ltResponseCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltResponse.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltResponse.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("ltResponse");
            HSSFRow row1;
            LtResponseBean ltResponseBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
//            HSSFCell cellpo7 = row1.createCell((short) 7);
//            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("LT Response :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("FileFormat");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("InstanceId");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("TransType");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Direction");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Status");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Shipment");
             cellg1.setCellStyle(cellStyle);
//              HSSFCell cellh1 = row1.createCell((short) 7);
//             cellh1.setCellValue("Reprocess");
//             cellh1.setCellStyle(cellStyle);
//             HSSFCell celli1 = row1.createCell((short) 8);
//             celli1.setCellValue("Ack Status");
//             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            ltResponseBean = (LtResponseBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(ltResponseBean.getFileType());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(ltResponseBean.getFileId());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(ltResponseBean.getPartnerName());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(ltResponseBean.getTransType());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(ltResponseBean.getDirection());  
                        
//                        HSSFCell cellF1 = row1.createCell((short) 5);
//                        cellF1.setCellValue(logisticsLoadBean.getDirection());  
//                          HSSFCell cellG1 = row1.createCell((short) 6);
//                        cellG1.setCellValue(logisticsDocBean.getCheckAmount()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellF1 = row1.createCell((short) 5);
                         if(ltResponseBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(ltResponseBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellF1.setCellValue(ltResponseBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellF1.setCellStyle(cellStyle1);
                          
                        }else if(ltResponseBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellF1.setCellValue(ltResponseBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellF1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellF1.setCellValue(ltResponseBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellF1.setCellStyle(cellStyle3);
                        
                        }
                         } 
                
//                        HSSFCell cellG1 = row1.createCell((short) 6);
//                          
//                         if(ltResponseBean.getAckStatus()!=null) {
//                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
//                               if(ltResponseBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
//                        
//                          
//                         font1.setColor(HSSFColor.GREEN.index);
//                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                         cellG1.setCellValue(ltResponseBean.getAckStatus().toUpperCase()); 
//                        cellStyle1.setFont(font1);
//                        cellG1.setCellStyle(cellStyle1);
//                          
//                        }else if(ltResponseBean.getAckStatus().equalsIgnoreCase("REJECTED")){
//                            
//                           font2.setColor(HSSFColor.RED.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                            cellG1.setCellValue(ltResponseBean.getAckStatus().toUpperCase()); 
//                            cellStyle2.setFont(font2);
//                            cellG1.setCellStyle(cellStyle2);
//                         
//                        }else{
//                           
//                          font3.setColor(HSSFColor.ORANGE.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                          cellG1.setCellValue(ltResponseBean.getAckStatus().toUpperCase()); 
//                          cellStyle3.setFont(font3);
//                          cellG1.setCellStyle(cellStyle3);
//                        
//                        }
//                        
//                        
//             }
//                         
                         HSSFCell cellH1 = row1.createCell((short) 6);
                        cellH1.setCellValue(ltResponseBean.getShipmentId()); 
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
     /*
     * Method to download excel for Lt Shipment
     * Date : 07/02/2013
     * 
     * 
     */
    
     public String ltShipmentExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LTSHIPMENT_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.ltShipmentCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltShipment.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltShipment.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("ltShipment");
            HSSFRow row1;
            LtShipmentBean ltShipmentBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
        //    HSSFCell cellpo7 = row1.createCell((short) 7);
//            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("LT Shipment :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("InstanceId");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("ASN#");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Direction");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Status");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("CarrierStatus");
             cellg1.setCellStyle(cellStyle);
             // HSSFCell cellh1 = row1.createCell((short) 7);
             //cellh1.setCellValue("Reprocess");
             //cellh1.setCellStyle(cellStyle);
//             HSSFCell celli1 = row1.createCell((short) 8);
//             celli1.setCellValue("Ack Status");
//             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            ltShipmentBean = (LtShipmentBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(ltShipmentBean.getInstanceId());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(ltShipmentBean.getAsnNum());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(ltShipmentBean.getPartner());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(ltShipmentBean.getDateTime());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(ltShipmentBean.getDirection());  
                        
//                        HSSFCell cellF1 = row1.createCell((short) 5);
//                        cellF1.setCellValue(logisticsLoadBean.getDirection());  
                         HSSFCell cellG1 = row1.createCell((short) 6);
                       cellG1.setCellValue(ltShipmentBean.getCarrierStatus()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellF1 = row1.createCell((short) 5);
                         if(ltShipmentBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(ltShipmentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellF1.setCellValue(ltShipmentBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellF1.setCellStyle(cellStyle1);
                          
                        }else if(ltShipmentBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellF1.setCellValue(ltShipmentBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellF1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellF1.setCellValue(ltShipmentBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellF1.setCellStyle(cellStyle3);
                        
                        }
                         } 
                
//                        HSSFCell cellG1 = row1.createCell((short) 6);
//                          
//                         if(ltShipmentBean.getAckStatus()!=null) {
//                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
//                               if(ltShipmentBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
//                        
//                          
//                         font1.setColor(HSSFColor.GREEN.index);
//                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                         cellG1.setCellValue(ltShipmentBean.getAckStatus().toUpperCase()); 
//                        cellStyle1.setFont(font1);
//                        cellG1.setCellStyle(cellStyle1);
//                          
//                        }else if(ltShipmentBean.getAckStatus().equalsIgnoreCase("REJECTED")){
//                            
//                           font2.setColor(HSSFColor.RED.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                            cellG1.setCellValue(ltShipmentBean.getAckStatus().toUpperCase()); 
//                            cellStyle2.setFont(font2);
//                            cellG1.setCellStyle(cellStyle2);
//                         
//                        }else{
//                           
//                          font3.setColor(HSSFColor.ORANGE.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                          cellG1.setCellValue(ltShipmentBean.getAckStatus().toUpperCase()); 
//                          cellStyle3.setFont(font3);
//                          cellG1.setCellStyle(cellStyle3);
//                        
//                        }
//                        
//                        
//             }
                         
                         
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
    
    
    /*
     * Method to download excel for Lt Invoice
     * Date : 07/02/2013
     * 
     * 
     */
    
     public String ltInvoiceExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LTINVOICE_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.ltInvoiceCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltInvoice.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"ltInvoice.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("ltInvoice");
            HSSFRow row1;
            LogisticsInvoiceBean logisticsInvoiceBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            //HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("LT Invoice :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("InstanceId");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Partner");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Invoice#");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("PO#");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Item Qty");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Inv Amount");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Inv Date");
             cellg1.setCellStyle(cellStyle);
              HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Status");
             cellh1.setCellStyle(cellStyle);
          //  HSSFCell celli1 = row1.createCell((short) 8);
            // celli1.setCellValue("Shipment");
            // celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            logisticsInvoiceBean = (LogisticsInvoiceBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(logisticsInvoiceBean.getInstanceId());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(logisticsInvoiceBean.getPartner());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(logisticsInvoiceBean.getInvoiceNumber());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(logisticsInvoiceBean.getPoNumber());  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(logisticsInvoiceBean.getItemQty());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(logisticsInvoiceBean.getInvAmount());  
                          HSSFCell cellG1 = row1.createCell((short) 6);
                       cellG1.setCellValue(logisticsInvoiceBean.getInvDate()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellH1 = row1.createCell((short) 7);
                         if(logisticsInvoiceBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(logisticsInvoiceBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellH1.setCellValue(logisticsInvoiceBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellH1.setCellStyle(cellStyle1);
                          
                        }else if(logisticsInvoiceBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellH1.setCellValue(logisticsInvoiceBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellH1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellH1.setCellValue(logisticsInvoiceBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellH1.setCellStyle(cellStyle3);
                        
                        }
                         } 
                
//                        HSSFCell cellI1 = row1.createCell((short) 8);
//                          
//                         if(logisticsInvoiceBean.getAckStatus()!=null) {
//                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
//                               if(logisticsInvoiceBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
//                        
//                          
//                         font1.setColor(HSSFColor.GREEN.index);
//                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                         cellI1.setCellValue(logisticsInvoiceBean.getAckStatus().toUpperCase()); 
//                        cellStyle1.setFont(font1);
//                        cellI1.setCellStyle(cellStyle1);
//                          
//                        }else if(logisticsInvoiceBean.getAckStatus().equalsIgnoreCase("REJECTED")){
//                            
//                           font2.setColor(HSSFColor.RED.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                            cellI1.setCellValue(logisticsInvoiceBean.getAckStatus().toUpperCase()); 
//                            cellStyle2.setFont(font2);
//                            cellI1.setCellStyle(cellStyle2);
//                         
//                        }else{
//                           
//                          font3.setColor(HSSFColor.ORANGE.index);
//                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
//                          cellI1.setCellValue(logisticsInvoiceBean.getAckStatus().toUpperCase()); 
//                          cellStyle3.setFont(font3);
//                          cellI1.setCellStyle(cellStyle3);
//                        
//                        }
//                        
//                        
//             }
               // HSSFCell cellI1 = row1.createCell((short) 8);
                 //      cellG1.setCellValue(logisticsInvoiceBean.getShipmentId());            
                         
             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
     /*
      * Method for DocumentVisibility Excel download
      * Author : Santosh Kola
      * Date : 01/05/2014
      */
     
      
    public String docVisibilityExcelDownload() {
String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute("searchResult");
                    
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocVisibility.xls");
            filePath = file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocVisibility.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("DocVisibility");
            HSSFRow row1;
            DocumentVisibilityBean documentVisibilityBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Doc Repositry:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("FileType");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Date Created");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("TransType");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Sender Id");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Receiver Id");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("IC #");
             cellg1.setCellStyle(cellStyle);
             HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("FC #");
             cellh1.setCellStyle(cellStyle);
             HSSFCell celli1 = row1.createCell((short) 8);
             celli1.setCellValue("MC #");
             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            documentVisibilityBean = (DocumentVisibilityBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(documentVisibilityBean.getInstanceId());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(documentVisibilityBean.getFile_type());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(documentVisibilityBean.getDate_time_rec().toString().substring(0, documentVisibilityBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           //cellD1.setCellValue(docRepositoryBean.getDate_time_rec());  
                         cellD1.setCellValue(documentVisibilityBean.getTransaction_type());
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(documentVisibilityBean.getSenderId());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                          cellF1.setCellValue(documentVisibilityBean.getReceiverId());   
                        
                        HSSFCell cellG1 = row1.createCell((short) 6);
                        cellG1.setCellValue(documentVisibilityBean.getInterchange_ControlNo());  
                         HSSFCell cellH1 = row1.createCell((short) 7);
                        cellH1.setCellValue(documentVisibilityBean.getFunctional_ControlNo()); 
                        
                         HSSFCell cellI1 = row1.createCell((short) 8);
                        cellI1.setCellValue(documentVisibilityBean.getMessage_ControlNo()); 
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                        
                     /*   if(documentVisibilityBean.getStatus()!= null) {
                               if(docRepositoryBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(docRepositoryBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        }
                        
                        
                    HSSFCell cellH1 = row1.createCell((short) 7);
                    
                 cellH1.setCellValue(docRepositoryBean.getReProcessStatus());      */
             }
              
             } 
             worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
            worksheet.autoSizeColumn((short) 5);
            worksheet.autoSizeColumn((short) 6);
            worksheet.autoSizeColumn((short) 7);
            worksheet.autoSizeColumn((short) 8);
           
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
     
     
     /*
     * Reports Generation
     * Date : 02/06/2015
     * 
     * 
     */
     
     
     
       
    public String docReportExcelDownload() {
String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocRepository.xls");
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"DocRepositoryReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"DocRepositoryReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("DocRepository");
            HSSFRow row1;
            ReportsBean docRepositoryBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Doc Repositry:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));
          
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Instance_Id");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("FileFormat");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Trans_Type");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
             HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("Reprocess");
             cellh1.setCellStyle(cellStyle);
             
              HSSFCell celli1 = row1.createCell((short) 8);
             celli1.setCellValue("ErrorMessage");
             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            docRepositoryBean = (ReportsBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(docRepositoryBean.getFile_id());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(docRepositoryBean.getFile_origin());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(docRepositoryBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           //cellD1.setCellValue(docRepositoryBean.getDate_time_rec());  
                         cellD1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(docRepositoryBean.getTransaction_type());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        
                          cellF1.setCellValue(docRepositoryBean.getDirection());   
                        
                        HSSFCell cellG1 = row1.createCell((short) 6);
                        
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                        
                        if(docRepositoryBean.getStatus()!= null) {
                               if(docRepositoryBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(docRepositoryBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(docRepositoryBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        }
                        
                        
                    HSSFCell cellH1 = row1.createCell((short) 7);
                    
                 cellH1.setCellValue(docRepositoryBean.getReProcessStatus());    
                 
                   HSSFCell cellI1 = row1.createCell((short) 8);
                    
                 cellI1.setCellValue(docRepositoryBean.getErrorMessage());    
             }
              worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
            worksheet.autoSizeColumn((short) 5);
            worksheet.autoSizeColumn((short) 6);
            worksheet.autoSizeColumn((short) 7);
            worksheet.autoSizeColumn((short) 8);
           
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
     
    /*
    **Dashboard pie charts excel generation
    */ 
     
     
     public String dashVisibilityExcelDownload(String inbound,String outbound)
     {
         String filePath = "";
        try {
            StringTokenizer inboundst=new StringTokenizer(inbound,"^");
            StringTokenizer outboundst=new StringTokenizer(outbound,"^");
            List inbounddata=new ArrayList();
            String inboundvalue=null;
            String outboundvalue=null;
            while(inboundst.hasMoreTokens())
            {
               inboundvalue=inboundst.nextToken();
               StringTokenizer inboundst1=new StringTokenizer(inboundvalue,"|");
               while(inboundst1.hasMoreTokens())
               {
                inbounddata.add(inboundst1.nextToken());
               }
            }
         
            List outbounddata=new ArrayList();
            while(outboundst.hasMoreTokens())
            {
                outboundvalue=outboundst.nextToken();
               StringTokenizer outboundst1=new StringTokenizer(outboundvalue,"|");
               while(outboundst1.hasMoreTokens())
               {
                outbounddata.add(outboundst1.nextToken());
               }
            }
           File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"DashboardReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"DashboardReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet my_sheet = workbook.createSheet("DashboardPiechartReport");
            HSSFSheet worksheet = workbook.createSheet("DashboardReportData");
            HSSFRow row1;
            DefaultPieDataset inbound_chart_data = new DefaultPieDataset();
            DefaultPieDataset outbound_chart_data = new DefaultPieDataset();
            String Inboundvalue=null;
            Number Inboundvalue2=0;
            String Inboundvalue1=null;
            String Outboundvalue=null;
            String Outboundvalue1=null;
            Number Outboundvalue2=0;
            Date date = new Date();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFCellStyle cellStyleHead = workbook.createCellStyle();
            HSSFFont font4 = workbook.createFont();
            HSSFFont fontHead = workbook.createFont();
            fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font4.setColor(HSSFColor.WHITE.index);
            cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFont(font4);          
            if(inbounddata.size()!=0||outbounddata.size()!=0)
            {
                    row1 = worksheet.createRow((short) 0);
                    row1 = worksheet.createRow((short) 1);
                    Cell cell = row1.createCell((short) 1);
                    cell.setCellValue("Dashboard Reports:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
                    cellStyleHead.setFont(fontHead);
                    cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    cell.setCellStyle(cellStyleHead);
                    worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));
                    int j=0;    
                    row1 = worksheet.createRow((short) j+3);
                    if(inbounddata.size()!=0)
                    {
                         HSSFCell cell1 = row1.createCell((short) 0);
                         cell1.setCellValue("PartnerName");  
                         HSSFCell cell2 = row1.createCell((short) 1);
                         cell2.setCellValue("INBOUND");  
                    }
                     if(outbounddata.size()!=0)
                     {
                     if(inbounddata.size()!=0)
                     {    
                         HSSFCell cell3 = row1.createCell((short) 2);
                         cell3.setCellValue("PartnerName");  
                         HSSFCell cell4 = row1.createCell((short) 3);
                         cell4.setCellValue("OUTBOUND");  
                     }
                     else
                     {
                         HSSFCell cell3 = row1.createCell((short) 0);
                         cell3.setCellValue("PartnerName");  
                         HSSFCell cell4 = row1.createCell((short) 1);
                         cell4.setCellValue("OUTBOUND");    
                     }
                     }
                     for (int i = 0; i < inbounddata.size();) 
                     {
                         Inboundvalue = String.valueOf( inbounddata.get(i));
                         Inboundvalue1 =  String.valueOf(inbounddata.get(i+1));
                         row1 = worksheet.createRow((short) j+4);
                         Inboundvalue2=(Number)Integer.parseInt(Inboundvalue1);
                         HSSFCell cellA1 = row1.createCell((short) 0);
                         cellA1.setCellValue(Inboundvalue);  
                         HSSFCell cellA2 = row1.createCell((short) 1);
                         cellA2.setCellValue(Inboundvalue1);  
                         i=i+2;
                         j=j+1;
                         inbound_chart_data.setValue(Inboundvalue,Inboundvalue2);
                     }

                     j=0;
                     for (int i = 0; i < outbounddata.size();) 
                     {
                         Outboundvalue = String.valueOf( outbounddata.get(i));
                         Outboundvalue1 = String.valueOf( outbounddata.get(i+1));
                         Outboundvalue2=(Number)Integer.parseInt(Outboundvalue1);
                         if(inbounddata.size()!=0&&i<inbounddata.size())
                         {
                                row1 = worksheet.getRow((short) j+4);
                                HSSFCell cellA1 = row1.createCell((short) 2);
                                cellA1.setCellValue(Outboundvalue);  
                                HSSFCell cellA2 = row1.createCell((short) 3);
                                cellA2.setCellValue(Outboundvalue1); 
                         }
                         else
                         {
                                row1 = worksheet.createRow((short) j+4);
                                if(inbounddata.size()!=0)
                                {
                                        HSSFCell cellA1 = row1.createCell((short) 2);
                                        cellA1.setCellValue(Outboundvalue);  
                                        HSSFCell cellA2 = row1.createCell((short) 3);
                                        cellA2.setCellValue(Outboundvalue1); 
                                }
                                else
                                {
                                        HSSFCell cellA1 = row1.createCell((short) 0);
                                        cellA1.setCellValue(Outboundvalue);  
                                        HSSFCell cellA2 = row1.createCell((short) 1);
                                        cellA2.setCellValue(Outboundvalue1); 
                                }
                            }
                            i=i+2;
                            j=j+1;
                            outbound_chart_data.setValue(Outboundvalue,Outboundvalue2);
                     }

                    worksheet.autoSizeColumn((short) 0);
                    worksheet.autoSizeColumn((short) 1);
                    worksheet.autoSizeColumn((short) 2);
                    worksheet.autoSizeColumn((short) 3);
                    worksheet.autoSizeColumn((short) 4);
                    worksheet.autoSizeColumn((short) 5);
                    worksheet.autoSizeColumn((short) 6);
                    worksheet.autoSizeColumn((short) 7);
                    worksheet.autoSizeColumn((short) 8);
           
             } 
           /* Specify the height and width of the Pie Chart */
                int width=480; /* Width of the chart */
                int height=350; /* Height of the chart */
                float quality=1; /* Quality factor */
                 
                Drawing drawing = my_sheet.createDrawingPatriarch();
                Row row = my_sheet.createRow((short) 1);
                Cell cell = row.createCell((short) 5);
                cell.setCellValue("Dashboard Pie Charts Reports:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
                cellStyleHead.setFont(fontHead);
                cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cell.setCellStyle(cellStyleHead);
                my_sheet.addMergedRegion(CellRangeAddress.valueOf("F2:N2"));
           if(inbounddata.size()!=0)
           {
              //System.out.println("inbound_chart_data"+inbound_chart_data);
               JFreeChart inboundPieChart=ChartFactory.createPieChart("Partner Inbound Transcations",inbound_chart_data,true,true,false);
                 /* We don't want to create an intermediate file. So, we create a byte array output stream 
                and byte array input stream
                And we pass the chart data directly to input stream through this */             
                /* Write chart as JPG to Output Stream */
                ByteArrayOutputStream inbound_out = new ByteArrayOutputStream();       
                ChartUtilities.writeChartAsJPEG(inbound_out,quality,inboundPieChart,width,height);
                int inbound_picture_id = workbook.addPicture(inbound_out.toByteArray(), workbook.PICTURE_TYPE_JPEG);
                inbound_out.close();
               
                /* Create the drawing container */
               
                /* Create an anchor point */
                ClientAnchor inbound_anchor = new HSSFClientAnchor() ;
                /* Define top left corner, and we can resize picture suitable from there */
                inbound_anchor.setCol1(2);
                inbound_anchor.setRow1(5);
                /* Invoke createPicture and pass the anchor point and ID */
                Picture  inbound_picture = drawing.createPicture(inbound_anchor, inbound_picture_id);
                
                /* Call resize method, which resizes the image */
                inbound_picture.resize();
         }
         if(outbounddata.size()!=0)
         {
                  ByteArrayOutputStream outbound_out = new ByteArrayOutputStream();
                  JFreeChart outboundPieChart=ChartFactory.createPieChart("Partner Outbound Transcations",outbound_chart_data,true,true,false);
                  ChartUtilities.writeChartAsJPEG(outbound_out,quality,outboundPieChart,width,height);
                  int outbound_picture_id = workbook.addPicture(outbound_out.toByteArray(), workbook.PICTURE_TYPE_JPEG);
                  outbound_out.close();
                  ClientAnchor outbound_anchor = new HSSFClientAnchor() ;
                /* Define top left corner, and we can resize picture suitable from there */
                   if(inbounddata.size()!=0)
                   {
                       outbound_anchor.setCol1(10);
                       outbound_anchor.setRow1(5);
                   }
                   else
                   {
                       outbound_anchor.setCol1(2);
                       outbound_anchor.setRow1(5);
                   }
                /* Invoke createPicture and pass the anchor point and ID */
                Picture  outbound_picture = drawing.createPicture(outbound_anchor, outbound_picture_id);
                outbound_picture.resize();
         }
                workbook.write(fileOut);
         	fileOut.flush();
		fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
                }
                catch (Exception e) {
			e.printStackTrace();
                }
        return filePath;
     }
     
     /*
     **Dashboard pie charts pdf generation
     */ 
     public String dashVisibilityPdfDownload(String inbound,String outbound)
     {
         String filePath = "";
        try {
            StringTokenizer inboundst=new StringTokenizer(inbound,"^");
            StringTokenizer outboundst=new StringTokenizer(outbound,"^");
            List inbounddata=new ArrayList();
            String inboundvalue=null;
            String outboundvalue=null;
            while(inboundst.hasMoreTokens())
            {
               inboundvalue=inboundst.nextToken();
               StringTokenizer inboundst1=new StringTokenizer(inboundvalue,"|");
               while(inboundst1.hasMoreTokens())
               {
                inbounddata.add(inboundst1.nextToken());
               }
            }
            List outbounddata=new ArrayList();
            while(outboundst.hasMoreTokens())
            {
               outboundvalue=outboundst.nextToken();
               StringTokenizer outboundst1=new StringTokenizer(outboundvalue,"|");
               while(outboundst1.hasMoreTokens())
               {
                   outbounddata.add(outboundst1.nextToken());
               }
            }
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"DashboardReport.pdf");
            filePath = file.getAbsolutePath()+ File.separator+"DashboardReport.pdf";
            DefaultPieDataset inbound_chart_data = new DefaultPieDataset();
            DefaultPieDataset outbound_chart_data = new DefaultPieDataset();
            String Inboundvalue=null;
            Number Inboundvalue2=0;
            String Inboundvalue1=null;
            String Outboundvalue=null;
            String Outboundvalue1=null;
            Number Outboundvalue2=0;
            PdfPTable inboundtable=new PdfPTable(2);
            PdfPTable outboundtable=new PdfPTable(2);
            inboundtable.setWidthPercentage(50.00f);
            outboundtable.setWidthPercentage(50.00f);
         if(inbounddata.size()!=0||outbounddata.size()!=0)
         {
                   inboundtable.addCell("PartnerName");
                   inboundtable.addCell("INBOUND");
                   for (int i = 0; i < inbounddata.size();) 
                   {
                    Inboundvalue = String.valueOf( inbounddata.get(i));
                    Inboundvalue1 =  String.valueOf(inbounddata.get(i+1));
                    Inboundvalue2=(Number)Integer.parseInt(Inboundvalue1);
           	    i=i+2;
		    inbound_chart_data.setValue(Inboundvalue,Inboundvalue2);
                    inboundtable.addCell(Inboundvalue);
                    inboundtable.addCell(Inboundvalue1);
                    inboundtable.completeRow();
           }
           
             outboundtable.addCell("PartnerName");
             outboundtable.addCell("OUTBOUND");
             for (int i = 0; i < outbounddata.size();) 
             {
                      Outboundvalue = String.valueOf( outbounddata.get(i));
                      Outboundvalue1 = String.valueOf( outbounddata.get(i+1));
                      Outboundvalue2=(Number)Integer.parseInt(Outboundvalue1);
                      i=i+2;
                      outbound_chart_data.setValue(Outboundvalue,Outboundvalue2);
                      outboundtable.addCell(Outboundvalue);
                      outboundtable.addCell(Outboundvalue1);
                      outboundtable.completeRow();
             }
            
                   
             } 
           /* Specify the height and width of the Pie Chart */
                int width=380; /* Width of the chart */
                int height=400; /* Height of the chart */
               Date date = new Date();
            PdfWriter writer = null;
             Document document = new Document(PageSize.A3);
            writer = PdfWriter.getInstance(document, fileOut);      
            document.open();
            Paragraph par=new Paragraph("Dashboard Pie Reports:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate(),FontFactory.getFont("Arial", 26f ));
            par.setAlignment(Paragraph.ALIGN_CENTER);
            
            document.add(par);

//my_pie_chart_data.setValue("a",50);
         if(inbounddata.size()!=0){
              //System.out.println("inbound_chart_data"+inbound_chart_data);
            
               JFreeChart inboundPieChart=ChartFactory.createPieChart("Partner Inbound Transcations",inbound_chart_data,true,true,false);
            PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height,
                new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                height);
 
        inboundPieChart.draw(graphics2d, rectangle2d);
         
        graphics2d.dispose();
        if(outbounddata.size()!=0){
        contentByte.addTemplate(template, 30, 630);
        }
        else
        {
            contentByte.addTemplate(template, 210, 630);
        }
        //document.right();
       
         //document.setMargins(0, 0, 0,0);
         
         }
         if(outbounddata.size()!=0){
         // document.newPage();      
         JFreeChart outboundPieChart=ChartFactory.createPieChart("Partner Outbound Transcations",outbound_chart_data,true,true,false);
         PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height,
                new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                height);
 
        outboundPieChart.draw(graphics2d, rectangle2d);
         
        graphics2d.dispose();
        if(inbounddata.size()!=0)
        {
            contentByte.addTemplate(template, 430, 630);
        }
        else
        {
            contentByte.addTemplate(template, 210, 630);
        }
         }
       
        /* if(inbounddata.size()!=0||outbounddata.size()!=0)
         {*/
            document.newPage();
            //inboundtable.setHorizontalAlignment(inboundtable.ALIGN_LEFT);
            Paragraph par1=new Paragraph("Dashboard Inbound and outbound Reports ",FontFactory.getFont("Arial", 26f ));
            par1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(par1);
            if(inbounddata.size()!=0)
            {
            Paragraph par2=new Paragraph("INBOUND REPORTS ",FontFactory.getFont("Arial", 18f ));
            par2.setSpacingBefore(20.00f);
            document.add(par2);
            inboundtable.setSpacingBefore(20.00f);
            inboundtable.setHorizontalAlignment(inboundtable.ALIGN_LEFT);
            document.add(inboundtable);
            }
            if(outbounddata.size()!=0)
            {
            Paragraph par3=new Paragraph("OUTBOUND REPORTS ",FontFactory.getFont("Arial", 18f ));
            par3.setSpacingBefore(20.00f);
            document.add(par3);
            outboundtable.setSpacingBefore(20.00f);
            outboundtable.setHorizontalAlignment(outboundtable.ALIGN_LEFT);
            document.add(outboundtable);
            }
        //  }
         document.close();
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
                }
                catch (Exception e) {
			e.printStackTrace();
                }
        return filePath;
     }
     
       /*
     * Reports edi tracking summary Generation
     * Date : 29/07/2015
     * 
     * 
     */
     
     
     
       
    public String docReportTrackingSummaryExcelDownload() {
       String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            int inboundTotal=0;
            int outboundTotal=0;
            double filesizeTotal=0;
            double filesizeTotal1=0;
            int total=0;
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"EDITrackingSummaryReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"EDITrackingSummaryReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("EDITrackingSummaryReport");
            HSSFRow row1;
            TrackInOutBean trackInOutBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
           
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
             
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
           
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 0);
            cell.setCellValue("EDI Tracking Summary:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("A2:F2"));
            //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("TRANSACTION TYPE");
             cella1.setCellStyle(cellStyle);
             //HSSFCellStyle cellStyle = workbook.createCellStyle(); 
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Partner");
             cellb1.setCellStyle(cellStyle);
             HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("FILE_SIZE");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("IN");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("OUT");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Total");
             cellf1.setCellStyle(cellStyle);
             int j=0;
             String trans_type="";
             for (int i = 0; i < list.size(); i++) {
            trackInOutBean = (TrackInOutBean) list.get(i);
           row1 = worksheet.createRow((short) i+5);
            
                        if(trackInOutBean.getTransaction_type()!=null&&!"".equalsIgnoreCase(trackInOutBean.getTransaction_type()))
                        {
                            trans_type=trackInOutBean.getTransaction_type();
                        //cellA1.setCellValue(); 
                        }
                        HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(trackInOutBean.getPname());
  
                            HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(trackInOutBean.getFilesizeTotal());
                         
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                          cellD1.setCellValue(trackInOutBean.getInbound());  
                        
                        HSSFCell cellE1 = row1.createCell((short) 4);
                       
                          
                         cellE1.setCellValue(trackInOutBean.getOutbound());
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(trackInOutBean.getTotal());  
                        if(trackInOutBean.getPname().equalsIgnoreCase("Total"))
                        {
                       //System.out.println("inside if i="+i+"j="+j+"doctype"+docRepositoryBean.getTransaction_type()+"celldoctype"+cellA1.getStringCellValue());CellRangeAddress.valueOf("A"+(j+5)+":A"+(i+5))
			 System.out.println("tans type"+trans_type );
                         row1 = worksheet.getRow((short) j+5);
                          HSSFCell cellA1 = row1.createCell((short) 0);
                          cellA1.setCellValue(trans_type);   
                        worksheet.addMergedRegion( new CellRangeAddress(
                        j+5, //first row (0-based)
                        i+5 , //last row  (0-based)
                        0, //first column (0-based)
                        0  //last column  (0-based)
                        ));  
                        j=i+1;
                        
                        inboundTotal=inboundTotal+trackInOutBean.getInbound();
                        outboundTotal=outboundTotal+trackInOutBean.getOutbound();
                        filesizeTotal=filesizeTotal+trackInOutBean.getFilesizeTotal();
                       //  filesizeTotal1=filesizeTotal1+trackInOutBean.getFilesize1();
                        total=total+trackInOutBean.getTotal();
                        }
                        
                      
             }
             row1 = worksheet.createRow((short) list.size()+5);
             
             //worksheet.addMergedRegion()
             HSSFCell cellA1 = row1.createCell((short) 0);
            cellA1.setCellValue("Total");  
	    HSSFCell cellB1 = row1.createCell((short) 1);
            cellB1.setCellValue("");  
             HSSFCell cellC1 = row1.createCell((short) 2);
            cellC1.setCellValue(filesizeTotal);
            HSSFCell cellD1 = row1.createCell((short) 3);
            cellD1.setCellValue(inboundTotal);  
            HSSFCell cellE1 = row1.createCell((short) 4);
            cellE1.setCellValue(outboundTotal);
            HSSFCell cellF1 = row1.createCell((short) 5);
            cellF1.setCellValue(total); 
            worksheet.addMergedRegion(new CellRangeAddress(
            list.size()+5, //first row (0-based)
            list.size()+5 , //last row  (0-based)
            0, //first column (0-based)
            1  //last column  (0-based)
                  ));

            worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
            worksheet.autoSizeColumn((short) 5);
            worksheet.autoSizeColumn((short) 6);
            worksheet.autoSizeColumn((short) 7);
            worksheet.autoSizeColumn((short) 8);
             worksheet.autoSizeColumn((short) 9);
           
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
     
     
    
    
    
    
      /*
     * Reports edi tracking In/Out Generation
     * Date : 29/07/2015
     * 
     * 
     */
     
     
     
       
    public String docReportTrackingInOutExcelDownload() {
       String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            int inboundTotal=0;
            int outboundTotal=0;
            
             int inbounddocTotal=0;
                 int outbounddocTotal=0;
                 int docTotal=0;
                 int allTotal=0;
            
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"EDITrackinginoutReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"EDITrackinginoutReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("EDITrackinginoutReport");
            HSSFRow row1;
           TrackInOutBean trackInOutBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
                trackInOutBean =(TrackInOutBean) list.get(0);
                 ArrayList inboundList=trackInOutBean.getInboundList();
                 ArrayList outboundList=trackInOutBean.getOutboundList();
                 ArrayList docType=trackInOutBean.getDocumentTypeList();
                 ArrayList dateMonth=trackInOutBean.getDateMonth();
                 ArrayList dateMonthdocType=trackInOutBean.getDateMonthdocType();
                
                
             HSSFCellStyle cellStyle = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("EDI Tracking IN/OUT:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:J2"));
            //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("TRANSACTION TYPE");
             cella1.setCellStyle(cellStyle);
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Direction");
             cellb1.setCellStyle(cellStyle);
              worksheet.autoSizeColumn((short) 0);
              worksheet.autoSizeColumn((short) 1);
              for (int i = 0; i < dateMonth.size(); i++) {
                  HSSFCell cellc1 = row1.createCell((short) i+2);
                  cellc1.setCellValue((String)dateMonth.get(i));
                  cellc1.setCellStyle(cellStyle);
                  worksheet.autoSizeColumn((short) i+2);
            }
              HSSFCell celld1 = row1.createCell((short) dateMonth.size()+2);
              celld1.setCellValue("Total");
              celld1.setCellStyle(cellStyle);
              worksheet.autoSizeColumn((short) dateMonth.size()+2);
              int k=0;
              for (int i = 0; i < docType.size(); i++) {
                int j=0;
                 if(inboundList.contains(docType.get(i)))
                 {
                     j=j+1;
                 }
                  if(outboundList.contains(docType.get(i)))
                {
                    j=j+1;
                }
                  if(j==1)
                  {
                      row1 = worksheet.createRow((short) i+k+4);
                      HSSFCell cellA1 = row1.createCell((short) 0);
                      cellA1.setCellValue((String)docType.get(i));
                          worksheet.addMergedRegion(new CellRangeAddress(
                            i+k+4, //first row (0-based)
                            i+k+5 , //last row  (0-based)
                            0, //first column (0-based)
                            0  //last column  (0-based)
                              ));
                      
                     if(inboundList.contains(docType.get(i)))
                        {
                         HSSFCell cellB1 = row1.createCell((short) 1);
                         cellB1.setCellValue("Inbound");  
                        }
                       if(outboundList.contains(docType.get(i)))
                        {
                          HSSFCell cellC1 = row1.createCell((short) 1);
                          cellC1.setCellValue("Outbound");  
                        }
                       row1 = worksheet.createRow((short) i+k+5);
                       HSSFCell cellD1 = row1.createCell((short) 1);
                       cellD1.setCellValue("Total");  
                        k=k+1;
                  }
                  
                  if(j==2)
                  {
                       row1 = worksheet.createRow((short) i+k+4);
                       HSSFCell cellA1 = row1.createCell((short) 0);
                       cellA1.setCellValue((String)docType.get(i));
                        worksheet.addMergedRegion(new CellRangeAddress(
                            i+k+4, //first row (0-based)
                            i+k+6 , //last row  (0-based)
                            0, //first column (0-based)
                            0  //last column  (0-based)
                              ));
                       HSSFCell cellB1 = row1.createCell((short) 1);
                       cellB1.setCellValue("Inbound");  
                       row1 = worksheet.createRow((short) i+k+5);
                       HSSFCell cellC1 = row1.createCell((short) 1);
                       cellC1.setCellValue("Outbound");  
                       row1 = worksheet.createRow((short) i+k+6);
                       HSSFCell cellD1 = row1.createCell((short) 1);
                       cellD1.setCellValue("Total");  
                        k=k+2;
                  }
                 
              }
              row1 = worksheet.createRow((short) docType.size()+k+4);
              HSSFCell cellA1 = row1.createCell((short) 0);
              cellA1.setCellValue("Total");
              worksheet.addMergedRegion(new CellRangeAddress(
                docType.size()+k+4, //first row (0-based)
                docType.size()+k+4 , //last row  (0-based)
                0, //first column (0-based)
                1  //last column  (0-based)
                  ));
              
              int inboundvalue=0;
              int outboundvalue=0;
              int inoutTotal=0;
              for (int j = 0; j < dateMonthdocType.size(); j++) 
                {
                    ArrayList temp=(ArrayList)dateMonthdocType.get(j);
                  ArrayList olddoctype=new ArrayList();
                  
                  int total=0;
                  k=0;
                   for (int i = 0; i < docType.size(); i++)
                  {
                      
                        for (int l = 1; l < temp.size(); l=l+4) 
                        {
                            int m=0;
                            if(temp.get(l).equals(docType.get(i)))
                            {
                                inboundvalue=(Integer)temp.get(l+1);
                                outboundvalue=(Integer)temp.get(l+2);
                                inoutTotal=(Integer)temp.get(l+3);
                                if(inboundList.contains(docType.get(i)))
                                {
                                    m=m+1;
                                }
                                 if(outboundList.contains(docType.get(i)))
                               {
                                   m=m+1;
                               }
                                    if(m==1)
                                    {
                                       row1 = worksheet.getRow((short) k+4);
                                       if(inboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellB1 = row1.createCell((short) j+2);
                                            cellB1.setCellValue(inboundvalue);  
                                        
                                          }
                                         if(outboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellC1 = row1.createCell((short) j+2);
                                           
                                            cellC1.setCellValue(outboundvalue);  
                                           
                                          }
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         
                                         cellD1.setCellValue(inoutTotal);  
                                         k=k+2;
                                    }

                                    if(m==2)
                                    {
                                        row1 = worksheet.getRow((short) k+4);
                                        HSSFCell cellB1 = row1.createCell((short) j+2);
                                        cellB1.setCellValue(inboundvalue); 
                                       row1 = worksheet.getRow((short) k+5);
                                        
                                         HSSFCell cellC1 = row1.createCell((short) j+2);
                                         
                                         cellC1.setCellValue(outboundvalue); 
                                        row1 = worksheet.getRow((short) k+6);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                        
                                         cellD1.setCellValue(inoutTotal);  
                                         
                                          k=k+3;
                                    }
                                total=(Integer)temp.get(l+3)+total;
                                 
                            }
                           else
                            {
                                if(!temp.contains(docType.get(i))&&!olddoctype.contains((String)docType.get(i)))
                                {
                                    olddoctype.add((String)docType.get(i));
                                if(inboundList.contains(docType.get(i)))
                                {
                                    m=m+1;
                                }
                                 if(outboundList.contains(docType.get(i)))
                               {
                                   m=m+1;
                               }
                                    if(m==1)
                                    {
                                       row1 = worksheet.getRow((short) k+4);
                                       if(inboundList.contains(docType.get(i)))
                                          {
                                           HSSFCell cellB1 = row1.createCell((short) j+2);
                                           cellB1.setCellValue(0);  
                                          }
                                         if(outboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellC1 = row1.createCell((short) j+2);
                                            cellC1.setCellValue(0);  
                                          }
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         cellD1.setCellValue(0);  
                                          k=k+2;
                                    }

                                    if(m==2)
                                    {
                                        
                                        row1 = worksheet.getRow((short) k+4);
                                        HSSFCell cellB1 = row1.createCell((short) j+2);
                                        cellB1.setCellValue(0);  
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellC1 = row1.createCell((short) j+2);
                                         cellC1.setCellValue(0);  
                                         row1 = worksheet.getRow((short) k+6);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         cellD1.setCellValue(0);  
                                          k=k+3;
                                    }
                                }
                            }
                           
                        }
                  }
                    
                   row1 = worksheet.getRow((short) k+4);
                   HSSFCell cellB1 = row1.createCell((short) j+2);
                   cellB1.setCellValue(total);
                }
              int m=0;
               for (int j = 0; j < docType.size();j++) 
                {
                    for (int i = 0; i < dateMonthdocType.size(); i++) 
                     {
                         ArrayList temp1=(ArrayList)dateMonthdocType.get(i);
                         for (int l = 1; l < temp1.size(); l=l+4) 
                         {
                            if(temp1.get(l).equals(docType.get(j)))
                             {
                                 inbounddocTotal=(Integer)temp1.get(l+1)+inbounddocTotal;
                                 outbounddocTotal=(Integer)temp1.get(l+2)+outbounddocTotal;
                                 docTotal=(Integer)temp1.get(l+3)+docTotal;
                             }
                         }
                     }
                    k=0;
                 if(inboundList.contains(docType.get(j)))
                 {
                     k=k+1;
                 }
                  if(outboundList.contains(docType.get(j)))
                {
                    k=k+1;
                }
                  if(k==1)
                  {
                     
                      row1 = worksheet.getRow((short) j+m+4);
                    
                     if(inboundList.contains(docType.get(j)))
                        {
                         HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
                         cellB1.setCellValue(inbounddocTotal);  
                        }
                       if(outboundList.contains(docType.get(j)))
                        {
                          HSSFCell cellC1 = row1.createCell((short) dateMonth.size()+2);
                          cellC1.setCellValue(outbounddocTotal);  
                        }
                       row1 = worksheet.getRow((short) j+m+5);
                       HSSFCell cellD1 = row1.createCell((short) dateMonth.size()+2);
                       cellD1.setCellValue(docTotal);  
                        m=m+1;
                  }
                  
                  if(k==2)
                  {
                      row1 = worksheet.getRow((short) j+m+4);
                 
                       HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
                       cellB1.setCellValue(inbounddocTotal);  
                       row1 = worksheet.getRow((short) j+m+5);
                       HSSFCell cellC1 = row1.createCell((short) dateMonth.size()+2);
                       cellC1.setCellValue(outbounddocTotal);  
                       row1 = worksheet.getRow((short) j+m+6);
                       HSSFCell cellD1 = row1.createCell((short) dateMonth.size()+2);
                       cellD1.setCellValue(docTotal);  
                        m=m+2;
                  }
                    
                    allTotal=allTotal+docTotal;
                    inbounddocTotal=0;
                    outbounddocTotal=0;
                    docTotal=0;
                }
              row1 = worksheet.getRow((short) docType.size()+m+4);
              HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
              cellB1.setCellValue(allTotal);
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
    
    
     public String docReportTrackingInquiryExcelDownload() {
String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocRepository.xls");
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"docEdiTrackingInquiryReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"docEdiTrackingInquiryReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("EdiTrackingInquiry");
            HSSFRow row1;
            TrackInOutBean docRepositoryBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
          //  HSSFCell cellpo5 = row1.createCell((short) 5);
          //  HSSFCell cellpo6 = row1.createCell((short) 6);
          //  HSSFCell cellpo7 = row1.createCell((short) 7);
          //  HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Edi Tracking Inquiry:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Trans_Type");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Date Sent");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Date Acked");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("ACK Code");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Partner");
             celle1.setCellStyle(cellStyle);
          
             
             for (int i = 0; i < list.size(); i++) {
            docRepositoryBean = (TrackInOutBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(docRepositoryBean.getTransaction_type());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           //cellD1.setCellValue(docRepositoryBean.getDate_time_rec());  
                            if(docRepositoryBean.getAckStatus()!= null) {
                               if(docRepositoryBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellD1.setCellStyle(cellStyle1);
                          
                        }else if(docRepositoryBean.getAckStatus().equalsIgnoreCase("REJECTED")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellD1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellD1.setCellStyle(cellStyle3);
                        
                        }
                        }
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(docRepositoryBean.getPname());        
             }
              worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
        
           
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    } 
    
    
     
     public String reportDownloads() {


 try {
            this.setScheduleId(Integer.parseInt(httpServletRequest.getParameter("Id").toString()));
            this.setReportattachment(ServiceLocator.getGridDownloadService().getReportattachment(this.getScheduleId(),this.getStartDate()));
            //fileName = this.getAttachmentLocation()
          //  .substring(this.getAttachmentLocation().lastIndexOf(Properties.getProperty("OS.Compatabliliy.Download"))+1,getAttachmentLocation().length());
            httpServletResponse.setContentType("application/force-download");
            
            File file = new File(getReportattachment());
            fileName = file.getName();
            inputStream = new FileInputStream(file);
            outputStream =  httpServletResponse.getOutputStream();
            httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + fileName +"\"");
            int noOfBytesRead = 0;
            byte[] byteArray = null;
            while(true) {
                byteArray = new byte[1024];
                noOfBytesRead = inputStream.read(byteArray);
                if(noOfBytesRead==-1) break;
                outputStream.write(byteArray, 0, noOfBytesRead);
            }
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }catch (ServiceLocatorException ex) {
            ex.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
 return null;

    } 
      
// new action implement for logistic reports
     
         public String logisticReportTrackingInOutExcelDownload() {
       String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            int inboundTotal=0;
            int outboundTotal=0;
            
             int inbounddocTotal=0;
                 int outbounddocTotal=0;
                 int docTotal=0;
                 int allTotal=0;
            
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"EDITrackinginoutlogisticReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"EDITrackinginoutlogisticReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("EDITrackinginoutlogisticReport");
            HSSFRow row1;
           LogisticTrackInOutBean trackInOutBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
                trackInOutBean =(LogisticTrackInOutBean) list.get(0);
                 ArrayList inboundList=trackInOutBean.getInboundList();
                 ArrayList outboundList=trackInOutBean.getOutboundList();
                 ArrayList docType=trackInOutBean.getDocumentTypeList();
                 ArrayList dateMonth=trackInOutBean.getDateMonth();
                 ArrayList dateMonthdocType=trackInOutBean.getDateMonthdocType();
                
                
             HSSFCellStyle cellStyle = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("EDI Tracking IN/OUT:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:J2"));
            //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("TRANSACTION TYPE");
             cella1.setCellStyle(cellStyle);
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Direction");
             cellb1.setCellStyle(cellStyle);
              worksheet.autoSizeColumn((short) 0);
              worksheet.autoSizeColumn((short) 1);
              for (int i = 0; i < dateMonth.size(); i++) {
                  HSSFCell cellc1 = row1.createCell((short) i+2);
                  cellc1.setCellValue((String)dateMonth.get(i));
                  cellc1.setCellStyle(cellStyle);
                  worksheet.autoSizeColumn((short) i+2);
            }
              HSSFCell celld1 = row1.createCell((short) dateMonth.size()+2);
              celld1.setCellValue("Total");
              celld1.setCellStyle(cellStyle);
              worksheet.autoSizeColumn((short) dateMonth.size()+2);
              int k=0;
              for (int i = 0; i < docType.size(); i++) {
                int j=0;
                 if(inboundList.contains(docType.get(i)))
                 {
                     j=j+1;
                 }
                  if(outboundList.contains(docType.get(i)))
                {
                    j=j+1;
                }
                  if(j==1)
                  {
                      row1 = worksheet.createRow((short) i+k+4);
                      HSSFCell cellA1 = row1.createCell((short) 0);
                      cellA1.setCellValue((String)docType.get(i));
                          worksheet.addMergedRegion(new CellRangeAddress(
                            i+k+4, //first row (0-based)
                            i+k+5 , //last row  (0-based)
                            0, //first column (0-based)
                            0  //last column  (0-based)
                              ));
                      
                     if(inboundList.contains(docType.get(i)))
                        {
                         HSSFCell cellB1 = row1.createCell((short) 1);
                         cellB1.setCellValue("Inbound");  
                        }
                       if(outboundList.contains(docType.get(i)))
                        {
                          HSSFCell cellC1 = row1.createCell((short) 1);
                          cellC1.setCellValue("Outbound");  
                        }
                       row1 = worksheet.createRow((short) i+k+5);
                       HSSFCell cellD1 = row1.createCell((short) 1);
                       cellD1.setCellValue("Total");  
                        k=k+1;
                  }
                  
                  if(j==2)
                  {
                       row1 = worksheet.createRow((short) i+k+4);
                       HSSFCell cellA1 = row1.createCell((short) 0);
                       cellA1.setCellValue((String)docType.get(i));
                        worksheet.addMergedRegion(new CellRangeAddress(
                            i+k+4, //first row (0-based)
                            i+k+6 , //last row  (0-based)
                            0, //first column (0-based)
                            0  //last column  (0-based)
                              ));
                       HSSFCell cellB1 = row1.createCell((short) 1);
                       cellB1.setCellValue("Inbound");  
                       row1 = worksheet.createRow((short) i+k+5);
                       HSSFCell cellC1 = row1.createCell((short) 1);
                       cellC1.setCellValue("Outbound");  
                       row1 = worksheet.createRow((short) i+k+6);
                       HSSFCell cellD1 = row1.createCell((short) 1);
                       cellD1.setCellValue("Total");  
                        k=k+2;
                  }
                 
              }
              row1 = worksheet.createRow((short) docType.size()+k+4);
              HSSFCell cellA1 = row1.createCell((short) 0);
              cellA1.setCellValue("Total");
              worksheet.addMergedRegion(new CellRangeAddress(
                docType.size()+k+4, //first row (0-based)
                docType.size()+k+4 , //last row  (0-based)
                0, //first column (0-based)
                1  //last column  (0-based)
                  ));
              
              int inboundvalue=0;
              int outboundvalue=0;
              int inoutTotal=0;
              for (int j = 0; j < dateMonthdocType.size(); j++) 
                {
                    ArrayList temp=(ArrayList)dateMonthdocType.get(j);
                  ArrayList olddoctype=new ArrayList();
                  
                  int total=0;
                  k=0;
                   for (int i = 0; i < docType.size(); i++)
                  {
                      
                        for (int l = 1; l < temp.size(); l=l+4) 
                        {
                            int m=0;
                            if(temp.get(l).equals(docType.get(i)))
                            {
                                inboundvalue=(Integer)temp.get(l+1);
                                outboundvalue=(Integer)temp.get(l+2);
                                inoutTotal=(Integer)temp.get(l+3);
                                if(inboundList.contains(docType.get(i)))
                                {
                                    m=m+1;
                                }
                                 if(outboundList.contains(docType.get(i)))
                               {
                                   m=m+1;
                               }
                                    if(m==1)
                                    {
                                       row1 = worksheet.getRow((short) k+4);
                                       if(inboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellB1 = row1.createCell((short) j+2);
                                            cellB1.setCellValue(inboundvalue);  
                                        
                                          }
                                         if(outboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellC1 = row1.createCell((short) j+2);
                                           
                                            cellC1.setCellValue(outboundvalue);  
                                           
                                          }
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         
                                         cellD1.setCellValue(inoutTotal);  
                                         k=k+2;
                                    }

                                    if(m==2)
                                    {
                                        row1 = worksheet.getRow((short) k+4);
                                        HSSFCell cellB1 = row1.createCell((short) j+2);
                                        cellB1.setCellValue(inboundvalue); 
                                       row1 = worksheet.getRow((short) k+5);
                                        
                                         HSSFCell cellC1 = row1.createCell((short) j+2);
                                         
                                         cellC1.setCellValue(outboundvalue); 
                                        row1 = worksheet.getRow((short) k+6);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                        
                                         cellD1.setCellValue(inoutTotal);  
                                         
                                          k=k+3;
                                    }
                                total=(Integer)temp.get(l+3)+total;
                                 
                            }
                           else
                            {
                                if(!temp.contains(docType.get(i))&&!olddoctype.contains((String)docType.get(i)))
                                {
                                    olddoctype.add((String)docType.get(i));
                                if(inboundList.contains(docType.get(i)))
                                {
                                    m=m+1;
                                }
                                 if(outboundList.contains(docType.get(i)))
                               {
                                   m=m+1;
                               }
                                    if(m==1)
                                    {
                                       row1 = worksheet.getRow((short) k+4);
                                       if(inboundList.contains(docType.get(i)))
                                          {
                                           HSSFCell cellB1 = row1.createCell((short) j+2);
                                           cellB1.setCellValue(0);  
                                          }
                                         if(outboundList.contains(docType.get(i)))
                                          {
                                            HSSFCell cellC1 = row1.createCell((short) j+2);
                                            cellC1.setCellValue(0);  
                                          }
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         cellD1.setCellValue(0);  
                                          k=k+2;
                                    }

                                    if(m==2)
                                    {
                                        
                                        row1 = worksheet.getRow((short) k+4);
                                        HSSFCell cellB1 = row1.createCell((short) j+2);
                                        cellB1.setCellValue(0);  
                                        row1 = worksheet.getRow((short) k+5);
                                         HSSFCell cellC1 = row1.createCell((short) j+2);
                                         cellC1.setCellValue(0);  
                                         row1 = worksheet.getRow((short) k+6);
                                         HSSFCell cellD1 = row1.createCell((short) j+2);
                                         cellD1.setCellValue(0);  
                                          k=k+3;
                                    }
                                }
                            }
                           
                        }
                  }
                    
                   row1 = worksheet.getRow((short) k+4);
                   HSSFCell cellB1 = row1.createCell((short) j+2);
                   cellB1.setCellValue(total);
                }
              int m=0;
               for (int j = 0; j < docType.size();j++) 
                {
                    for (int i = 0; i < dateMonthdocType.size(); i++) 
                     {
                         ArrayList temp1=(ArrayList)dateMonthdocType.get(i);
                         for (int l = 1; l < temp1.size(); l=l+4) 
                         {
                            if(temp1.get(l).equals(docType.get(j)))
                             {
                                 inbounddocTotal=(Integer)temp1.get(l+1)+inbounddocTotal;
                                 outbounddocTotal=(Integer)temp1.get(l+2)+outbounddocTotal;
                                 docTotal=(Integer)temp1.get(l+3)+docTotal;
                             }
                         }
                     }
                    k=0;
                 if(inboundList.contains(docType.get(j)))
                 {
                     k=k+1;
                 }
                  if(outboundList.contains(docType.get(j)))
                {
                    k=k+1;
                }
                  if(k==1)
                  {
                     
                      row1 = worksheet.getRow((short) j+m+4);
                    
                     if(inboundList.contains(docType.get(j)))
                        {
                         HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
                         cellB1.setCellValue(inbounddocTotal);  
                        }
                       if(outboundList.contains(docType.get(j)))
                        {
                          HSSFCell cellC1 = row1.createCell((short) dateMonth.size()+2);
                          cellC1.setCellValue(outbounddocTotal);  
                        }
                       row1 = worksheet.getRow((short) j+m+5);
                       HSSFCell cellD1 = row1.createCell((short) dateMonth.size()+2);
                       cellD1.setCellValue(docTotal);  
                        m=m+1;
                  }
                  
                  if(k==2)
                  {
                      row1 = worksheet.getRow((short) j+m+4);
                 
                       HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
                       cellB1.setCellValue(inbounddocTotal);  
                       row1 = worksheet.getRow((short) j+m+5);
                       HSSFCell cellC1 = row1.createCell((short) dateMonth.size()+2);
                       cellC1.setCellValue(outbounddocTotal);  
                       row1 = worksheet.getRow((short) j+m+6);
                       HSSFCell cellD1 = row1.createCell((short) dateMonth.size()+2);
                       cellD1.setCellValue(docTotal);  
                        m=m+2;
                  }
                    
                    allTotal=allTotal+docTotal;
                    inbounddocTotal=0;
                    outbounddocTotal=0;
                    docTotal=0;
                }
              row1 = worksheet.getRow((short) docType.size()+m+4);
              HSSFCell cellB1 = row1.createCell((short) dateMonth.size()+2);
              cellB1.setCellValue(allTotal);
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
       public String logisticReportTrackingInquiryExcelDownload() {
String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            if(!file.exists())
                file.mkdirs();
            
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
            //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Po.xls");
            //filePath = file.getAbsolutePath()+"/Po.xls";
            //for XP
             //FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ Properties.getProperty("os.compatability")+"DocRepository.xls");
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"LogisticEdiTrackingInquiryReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"LogisticEdiTrackingInquiryReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("LogisticEdiTrackingInquiryReport");
            HSSFRow row1;
            LogisticTrackInOutBean docRepositoryBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
          //  HSSFCell cellpo5 = row1.createCell((short) 5);
          //  HSSFCell cellpo6 = row1.createCell((short) 6);
          //  HSSFCell cellpo7 = row1.createCell((short) 7);
          //  HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Edi Tracking Inquiry:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("Trans_Type");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Date Sent");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Date Acked");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("ACK Code");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("Partner");
             celle1.setCellStyle(cellStyle);
          
             
             for (int i = 0; i < list.size(); i++) {
            docRepositoryBean = (LogisticTrackInOutBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(docRepositoryBean.getTransaction_type());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(docRepositoryBean.getDate_time_rec().toString().substring(0, docRepositoryBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           //cellD1.setCellValue(docRepositoryBean.getDate_time_rec());  
                            if(docRepositoryBean.getAckStatus()!= null) {
                               if(docRepositoryBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellD1.setCellStyle(cellStyle1);
                          
                        }else if(docRepositoryBean.getAckStatus().equalsIgnoreCase("REJECTED")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellD1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellD1.setCellValue(docRepositoryBean.getAckStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellD1.setCellStyle(cellStyle3);
                        
                        }
                        }
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(docRepositoryBean.getPname());        
             }
              worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
        
           
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
       
          public String logisticReportTrackingSummaryExcelDownload() {
       String filePath = "";
        try {
           // java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOC_LIST);
             java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST);
            File file = new File(Properties.getProperty("mscvp.docCreationPath"));
            int inboundTotal=0;
            int outboundTotal=0;
            double filesizeTotal=0;
            double filesizeTotal1=0;
            int total=0;
            if(!file.exists())
                file.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+ File.separator+"EDITrackinglogisticSummaryReport.xls");
            filePath = file.getAbsolutePath()+ File.separator+"EDITrackinglogisticSummaryReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("EDITrackinglogisticSummaryReport");
            HSSFRow row1;
            LogisticTrackInOutBean trackInOutBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                       
            if(list.size()!=0){
             HSSFCellStyle cellStyle = workbook.createCellStyle();
           
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
             
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
           
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 0);
            cell.setCellValue("EDI Tracking Summary:-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("A2:F2"));
            //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("TRANSACTION TYPE");
             cella1.setCellStyle(cellStyle);
             //HSSFCellStyle cellStyle = workbook.createCellStyle(); 
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("Partner");
             cellb1.setCellStyle(cellStyle);
             HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("FILE_SIZE");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("IN");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("OUT");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Total");
             cellf1.setCellStyle(cellStyle);
             int j=0;
             String trans_type="";
             for (int i = 0; i < list.size(); i++) {
            trackInOutBean = (LogisticTrackInOutBean) list.get(i);
           row1 = worksheet.createRow((short) i+5);
            
                        if(trackInOutBean.getTransaction_type()!=null&&!"".equalsIgnoreCase(trackInOutBean.getTransaction_type()))
                        {
                            trans_type=trackInOutBean.getTransaction_type();
                        //cellA1.setCellValue(); 
                        }
                        HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(trackInOutBean.getPname());
  
                            HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(trackInOutBean.getFilesizeTotal());
                         
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                          cellD1.setCellValue(trackInOutBean.getInbound());  
                        
                        HSSFCell cellE1 = row1.createCell((short) 4);
                       
                          
                         cellE1.setCellValue(trackInOutBean.getOutbound());
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(trackInOutBean.getTotal());  
                        if(trackInOutBean.getPname().equalsIgnoreCase("Total"))
                        {
                       //System.out.println("inside if i="+i+"j="+j+"doctype"+docRepositoryBean.getTransaction_type()+"celldoctype"+cellA1.getStringCellValue());CellRangeAddress.valueOf("A"+(j+5)+":A"+(i+5))
			 System.out.println("tans type"+trans_type );
                         row1 = worksheet.getRow((short) j+5);
                          HSSFCell cellA1 = row1.createCell((short) 0);
                          cellA1.setCellValue(trans_type);   
                        worksheet.addMergedRegion( new CellRangeAddress(
                        j+5, //first row (0-based)
                        i+5 , //last row  (0-based)
                        0, //first column (0-based)
                        0  //last column  (0-based)
                        ));  
                        j=i+1;
                        
                        inboundTotal=inboundTotal+trackInOutBean.getInbound();
                        outboundTotal=outboundTotal+trackInOutBean.getOutbound();
                        filesizeTotal=filesizeTotal+trackInOutBean.getFilesizeTotal();
                       //  filesizeTotal1=filesizeTotal1+trackInOutBean.getFilesize1();
                        total=total+trackInOutBean.getTotal();
                        }
                        
                      
             }
             row1 = worksheet.createRow((short) list.size()+5);
             
             //worksheet.addMergedRegion()
             HSSFCell cellA1 = row1.createCell((short) 0);
            cellA1.setCellValue("Total");  
	    HSSFCell cellB1 = row1.createCell((short) 1);
            cellB1.setCellValue("");  
             HSSFCell cellC1 = row1.createCell((short) 2);
            cellC1.setCellValue(filesizeTotal);
            HSSFCell cellD1 = row1.createCell((short) 3);
            cellD1.setCellValue(inboundTotal);  
            HSSFCell cellE1 = row1.createCell((short) 4);
            cellE1.setCellValue(outboundTotal);
            HSSFCell cellF1 = row1.createCell((short) 5);
            cellF1.setCellValue(total); 
            worksheet.addMergedRegion(new CellRangeAddress(
            list.size()+5, //first row (0-based)
            list.size()+5 , //last row  (0-based)
            0, //first column (0-based)
            1  //last column  (0-based)
                  ));

            worksheet.autoSizeColumn((short) 0);
            worksheet.autoSizeColumn((short) 1);
            worksheet.autoSizeColumn((short) 2);
            worksheet.autoSizeColumn((short) 3);
            worksheet.autoSizeColumn((short) 4);
            worksheet.autoSizeColumn((short) 5);
            worksheet.autoSizeColumn((short) 6);
            worksheet.autoSizeColumn((short) 7);
            worksheet.autoSizeColumn((short) 8);
             worksheet.autoSizeColumn((short) 9);
           
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }
    
    public String logisticsReportsExcelDownload() {
        String filePath = "";
        try {
            java.util.List list = (java.util.List) httpServletRequest.getSession(false).getAttribute(AppConstants.SES_LOG_DOC_LIST);
                   
            File file = new File(Properties.getProperty("mscvp.logisticsDocCreationPath"));
            if(!file.exists())
                file.mkdirs();
              
                  
            //SearchedDocument.xls
        //FileOutputStream fileOut = new FileOutputStream("C:\\docExcel.xls");
            //for linux
           // FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+"/Payment.xls");
              
            //filePath = file.getAbsolutePath()+"/Payment.xls";
                
            //for XP
             FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath()+Properties.getProperty("os.compatability")+"logisticsReport.xls");
            filePath = file.getAbsolutePath()+Properties.getProperty("os.compatability")+"logisticsReport.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("logisticsReport");
            HSSFRow row1;
            LogisticReportsBean logisticsDocBean = null;
            // index from 0,0... cell A1 is cell(0,0)
                     
            if(list.size()!=0){
                    
             HSSFCellStyle cellStyle = workbook.createCellStyle();
             HSSFCellStyle cellStyle1 = workbook.createCellStyle();
              HSSFCellStyle cellStyle2 = workbook.createCellStyle();
              HSSFCellStyle cellStyle3 = workbook.createCellStyle();
              HSSFCellStyle cellStyleHead = workbook.createCellStyle();
              HSSFFont font1 = workbook.createFont();
              HSSFFont font2 = workbook.createFont();
              HSSFFont font3 = workbook.createFont();
              HSSFFont font4 = workbook.createFont();
              HSSFFont fontHead = workbook.createFont();
              fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             // fontHead.setFontHeightInPoints((short)15);  //for font Size
              font4.setColor(HSSFColor.WHITE.index);
              
             cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
             cellStyle.setFont(font4);
		//start	
             Date date = new Date();
            row1 = worksheet.createRow((short) 0);
            HSSFCell cellpo0 = row1.createCell((short) 0);
            // cellpo0.setCellValue("Purchase Order");
            HSSFCell cellpo1 = row1.createCell((short) 1);
            HSSFCell cellpo2 = row1.createCell((short) 2);
           // cellpo2.setCellValue("Created Date");
            HSSFCell cellpo3 = row1.createCell((short) 3);
            //cellpo3.setCellValue((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            
            HSSFCell cellpo4 = row1.createCell((short) 4);
            HSSFCell cellpo5 = row1.createCell((short) 5);
            HSSFCell cellpo6 = row1.createCell((short) 6);
            HSSFCell cellpo7 = row1.createCell((short) 7);
//            HSSFCell cellpo8 = row1.createCell((short) 8);
            row1 = worksheet.createRow((short) 1);
            Cell cell = row1.createCell((short) 1);
            cell.setCellValue("Logistics Document :-Created Date : "+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate());
            cellStyleHead.setFont(fontHead);
            cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyleHead);
            worksheet.addMergedRegion(CellRangeAddress.valueOf("B2:F2"));



            
             //end
             row1 = worksheet.createRow((short) 3);
             
             HSSFCell cella1 = row1.createCell((short) 0);
             cella1.setCellValue("FileFormat");
             cella1.setCellStyle(cellStyle);
                        //HSSFCellStyle cellStyle = workbook.createCellStyle();
			
             HSSFCell cellb1 = row1.createCell((short) 1);
             cellb1.setCellValue("InstanceId");
             cellb1.setCellStyle(cellStyle);
              HSSFCell cellc1 = row1.createCell((short) 2);
             cellc1.setCellValue("Partner");
             cellc1.setCellStyle(cellStyle);
              HSSFCell celld1 = row1.createCell((short) 3);
             celld1.setCellValue("DateTime");
             celld1.setCellStyle(cellStyle);
              HSSFCell celle1 = row1.createCell((short) 4);
             celle1.setCellValue("TransType");
             celle1.setCellStyle(cellStyle);
              HSSFCell cellf1 = row1.createCell((short) 5);
             cellf1.setCellValue("Direction");
             cellf1.setCellStyle(cellStyle);
              HSSFCell cellg1 = row1.createCell((short) 6);
             cellg1.setCellValue("Status");
             cellg1.setCellStyle(cellStyle);
              HSSFCell cellh1 = row1.createCell((short) 7);
             cellh1.setCellValue("FileName");
             cellh1.setCellStyle(cellStyle);
//             HSSFCell celli1 = row1.createCell((short) 8);
//             celli1.setCellValue("Ack Status");
//             celli1.setCellStyle(cellStyle);
             
             for (int i = 0; i < list.size(); i++) {
            logisticsDocBean = (LogisticReportsBean) list.get(i);
            
             row1 = worksheet.createRow((short) i+4);

			HSSFCell cellA1 = row1.createCell((short) 0);
                       
                        cellA1.setCellValue(logisticsDocBean.getFile_type());  
			
			
			HSSFCell cellB1 = row1.createCell((short) 1);
                        
                          cellB1.setCellValue(logisticsDocBean.getFile_id());  
                        HSSFCell cellC1 = row1.createCell((short) 2);
                       
                          cellC1.setCellValue(logisticsDocBean.getPname());  
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                         
                           cellD1.setCellValue(logisticsDocBean.getDate_time_rec().toString().substring(0, logisticsDocBean.getDate_time_rec().toString().lastIndexOf(":")));  
                        HSSFCell cellE1 = row1.createCell((short) 4);
                        
                           cellE1.setCellValue(logisticsDocBean.getTransaction_type());  
                        
                        HSSFCell cellF1 = row1.createCell((short) 5);
                        cellF1.setCellValue(logisticsDocBean.getDirection());  
//                          HSSFCell cellG1 = row1.createCell((short) 6);
//                        cellG1.setCellValue(logisticsDocBean.getCheckAmount()); 
                        
                         // cellF1.setCellValue(shipmentBean.getStatus());   
                        
                       // HSSFCell cellG1 = row1.createCell((short) 5);
                        HSSFCell cellG1 = row1.createCell((short) 6);
                         if(logisticsDocBean.getStatus()!=null) {
                           //cellG1.setCellValue(purchaseOrderBean.getStatus());  
                               if(logisticsDocBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        
                          
                         font1.setColor(HSSFColor.GREEN.index);
                           //cellG1.setCellValue(docRepositoryBean.getStatus());  
                         cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                        cellStyle1.setFont(font1);
                        cellG1.setCellStyle(cellStyle1);
                          
                        }else if(logisticsDocBean.getStatus().equalsIgnoreCase("ERROR")){
                            
                           font2.setColor(HSSFColor.RED.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                            cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                            cellStyle2.setFont(font2);
                            cellG1.setCellStyle(cellStyle2);
                         
                        }else{
                           
                          font3.setColor(HSSFColor.ORANGE.index);
                            //cellG1.setCellValue(docRepositoryBean.getStatus());  
                          cellG1.setCellValue(logisticsDocBean.getStatus().toUpperCase()); 
                          cellStyle3.setFont(font3);
                          cellG1.setCellStyle(cellStyle3);
                        
                        }
                        
                        
             }
                          HSSFCell cellH1 = row1.createCell((short) 7);
                        cellH1.setCellValue(logisticsDocBean.getFile_name()); 

             }      
             } 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
    }       
          
    /**
     * @return the downloadType
     */
    public String getDownloadType() {
        return downloadType;
    }

    /**
     * @param downloadType the downloadType to set
     */
    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    /**
     * @return the sheetType
     */
    public String getSheetType() {
        return sheetType;
    }

    /**
     * @param sheetType the sheetType to set
     */
    public void setSheetType(String sheetType) {
        this.sheetType = sheetType;
    }

    public String getReportattachment() {
        return reportattachment;
    }

    public void setReportattachment(String reportattachment) {
        this.reportattachment = reportattachment;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
}
