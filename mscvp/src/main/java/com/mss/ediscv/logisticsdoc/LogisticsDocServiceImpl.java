/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsdoc;

import com.mss.ediscv.util.ConnectionProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocatorException;
import com.mss.ediscv.util.WildCardSql;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author miracle
 */
public class LogisticsDocServiceImpl implements LogisticsDocService{
    
     Connection connection = null;
        Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	CallableStatement callableStatement = null;
        
	String tmp_Recieved_From = "";
	String tmp_Recieved_ToTime = "";
	String strFormat = "yyyy-MM-dd";
	DateFormat myDateFormat = new SimpleDateFormat(strFormat);
	Calendar cal = new GregorianCalendar();
	java.util.Date now = cal.getTime();
	long time = now.getTime();
	java.sql.Date date = new java.sql.Date(time);
        
	int callableStatementUpdateCount;
	private ArrayList<LogisticsDocBean> documentList;
	private LogisticsDocBean logisticsBean;

	private static Logger logger = Logger.getLogger(com.mss.ediscv.shipment.ShipmentServiceImpl.class
			.getName());

     public ArrayList<LogisticsDocBean> buildDocumentQuery(LogisticsDocAction logisticsDocAction)throws ServiceLocatorException {
            StringBuffer documentSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: PurchaseOrderServiceImpl :::: buildPurchaseQuery");
                 
             
               
                  String docdatepicker= logisticsDocAction.getDocdatepicker();
                  String docdatepickerfrom= logisticsDocAction.getDocdatepickerfrom();
            
                  String docSenderId= logisticsDocAction.getDocSenderId();
                  String docSenderName= logisticsDocAction.getDocSenderName();
                  String docBusId= logisticsDocAction.getDocBusId();
                  String docRecName= logisticsDocAction.getDocRecName();
                  String docIsa= logisticsDocAction.getDocIsa();
                  String doctype="";
                  if(!logisticsDocAction.getDocType().equals("-1"))
                  doctype = logisticsDocAction.getDocType();
                  
                  String corrattribute = logisticsDocAction.getCorrattribute();
                  String corrvalue = logisticsDocAction.getCorrvalue();
                  //System.out.println("doctype-->"+doctype+"-->corrattribute-->"+corrattribute+"-->corrvalue-->"+corrvalue);
                  String corrattribute1 = logisticsDocAction.getCorrattribute1();
                  String corrvalue1 = logisticsDocAction.getCorrvalue1();
                  
                   String corrattribute2 = logisticsDocAction.getCorrattribute2();
                  String corrvalue2 = logisticsDocAction.getCorrvalue2();
                  
                  String status = logisticsDocAction.getStatus();
                  String ackStatus = logisticsDocAction.getAckStatus();
                  /*String docPoNum= docRepositoryAction.getDocPoNum();
                  String ponum= docRepositoryAction.getPonumber();
                  String asnnum= docRepositoryAction.getAsnnumber();
                  String invnum= docRepositoryAction.getInvnumber();
                  String bolnum= docRepositoryAction.getBolNum();
                  String chequeNum = docRepositoryAction.getChequeNum();*/
                  
                  documentSearchQuery.append("SELECT DISTINCT(FILES.FILE_ID) as FILE_ID,FILES.ID as ID,"
                          + "FILES.ISA_NUMBER as ISA_NUMBER,FILES.FILE_TYPE as FILE_TYPE,FILES.PRI_KEY_VAL as PRI_KEY_VAL,"
                          + "FILES.FILE_ORIGIN as FILE_ORIGIN,FILES.TRANSACTION_TYPE as TRANSACTION_TYPE,"
                          + "FILES.DIRECTION as DIRECTION,FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED,"
                          + "FILES.STATUS as STATUS,FILES.ACK_STATUS as ACK_STATUS,TP2.NAME as RECEIVER_NAME,"
                          + "FILES.SEC_KEY_VAL,FILES.REPROCESSSTATUS,FILES.FILENAME "
                          + "FROM FILES LEFT OUTER JOIN Transport_loadtender ten on (ten.FILE_ID=FILES.FILE_ID and ten.SHIPMENT_ID=FILES.SEC_KEY_VAL) "
                          + " LEFT OUTER JOIN TP TP1 "
                          + "ON (TP1.ID=FILES.SENDER_ID) LEFT OUTER JOIN TP TP2 ON (TP2.ID=FILES.RECEIVER_ID)");
                  
		documentSearchQuery.append(" WHERE 1=1 AND FLOWFLAG LIKE '%L%'");
                
                
 
             
                // FOr PO
                if(corrattribute.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
                
                if(corrattribute2.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue2.trim().toUpperCase()));
		}
                
                
                    if(corrattribute.equalsIgnoreCase("Ref Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("Ref Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
                
                if(corrattribute2.equalsIgnoreCase("Ref Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.SEC_KEY_VAL",
					corrvalue2.trim().toUpperCase()));
		}
                
                // For Invoice / Shipment / Cheque
                  //if(corrattribute1.equalsIgnoreCase("PO Number") || corrattribute1.equalsIgnoreCase("Invoice Number")  || corrattribute1.equalsIgnoreCase("Shipment Number") || corrattribute1.equalsIgnoreCase("Cheque Number") )
                
                if(corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number")  )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("Invoice Number")  || corrattribute1.equalsIgnoreCase("Shipment Number")  )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
                  
               if(corrattribute2.equalsIgnoreCase("Invoice Number")  || corrattribute2.equalsIgnoreCase("Shipment Number"))
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue2.trim().toUpperCase()));
		}
                // isa 
                
                if(corrattribute.equalsIgnoreCase("ISA Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("ISA Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
                      if(corrattribute2.equalsIgnoreCase("ISA Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue2.trim().toUpperCase()));
		}
                }
                      
                // GS number
                            // isa 
                
                if(corrattribute.equalsIgnoreCase("GS Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_Number",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("GS Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_Number",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
                      if(corrattribute2.equalsIgnoreCase("GS Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_Number",
					corrvalue2.trim().toUpperCase()));
		}
                }
                // -------------------------------------
                // bol
                if(corrattribute.equalsIgnoreCase("BOL Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.BOL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                if(corrattribute1.equalsIgnoreCase("BOL Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.BOL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("BOL Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.BOL_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                
                // CO
                if(corrattribute.equalsIgnoreCase("CO Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.CO_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                if(corrattribute1.equalsIgnoreCase("CO Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.CO_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("CO Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("ten.CO_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                
                // end -----------------------------------------CO
                
                //file name
                 if(corrattribute.equalsIgnoreCase("FILE NAME")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.FILENAME",
					corrvalue.trim().toUpperCase()));
		}
                }
                 if(corrattribute1.equalsIgnoreCase("FILE NAME")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.FILENAME",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("FILE NAME")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.FILENAME",
					corrvalue2.trim().toUpperCase()));
		}
                }
                //end --------------------------------------------filename
                if (doctype != null && !"".equals(doctype.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                //Status
                 if (status != null && !"-1".equals(status.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
					status.trim()));
		}
                 //ACK_STATUS
                 if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
					ackStatus.trim()));
		}
                // gs number
                if(corrattribute.equalsIgnoreCase("GS Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                 if(corrattribute1.equalsIgnoreCase("GS Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("GS Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                 
                
                
                if (docBusId != null && !"".equals(docBusId.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
					docBusId.trim().toUpperCase()));
		}
                
                if (docSenderId != null && !"".equals(docSenderId.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
					docSenderId.trim().toUpperCase()));
		}
                
                  if (docSenderName != null && !"".equals(docSenderName.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
					docSenderName.trim().toUpperCase()));
		}
                
                if (docRecName != null && !"".equals(docRecName.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
					docRecName.trim().toUpperCase()));
		}
               
                if (docdatepicker != null && !"".equals(docdatepicker)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(docdatepicker);
			documentSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED <= '" + tmp_Recieved_From
					+ "'");
		}
		if (docdatepickerfrom != null && !"".equals(docdatepickerfrom)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(docdatepickerfrom);
			documentSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED >= '" + tmp_Recieved_From
					+ "'");
		}
                        
                  documentSearchQuery.append(" order by DATE_TIME_RECEIVED DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                  System.out.println("DOC queryquery-->"+documentSearchQuery.toString());
                    String searchQuery = documentSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        documentList  = new ArrayList<LogisticsDocBean>();
                        
                        while(resultSet.next()) {
                            LogisticsDocBean logisticsdocBean = new LogisticsDocBean();
                            logisticsdocBean.setId(resultSet.getInt("ID"));
                            logisticsdocBean.setFile_id(resultSet.getString("FILE_ID"));
                            logisticsdocBean.setFile_origin(resultSet.getString("FILE_ORIGIN"));
                            logisticsdocBean.setFile_type(resultSet.getString("FILE_TYPE"));
                            logisticsdocBean.setIsa_number(resultSet.getString("ISA_NUMBER"));
                            logisticsdocBean.setTransaction_type(resultSet.getString("TRANSACTION_TYPE"));
                            logisticsdocBean.setDirection(resultSet.getString("DIRECTION"));
                            logisticsdocBean.setDate_time_rec(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            logisticsdocBean.setStatus(resultSet.getString("STATUS"));
                            logisticsdocBean.setPname(resultSet.getString("RECEIVER_NAME"));
                            //logisticsdocBean.setPoNumber(resultSet.getString("SEC_KEY_VAL"));
                            logisticsdocBean.setReProcessStatus(resultSet.getString("REPROCESSSTATUS"));
                            logisticsdocBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            logisticsdocBean.setFile_name(resultSet.getString("FILENAME"));
                            logisticsdocBean.setShipmentId(resultSet.getString("PRI_KEY_VAL"));
                            documentList.add(logisticsdocBean);  
                        }
                        
                 }   
                 catch (SQLException e) {
			System.out.println("I am in catch block coming in IMpl");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception ex){
			//System.out.println("hi"+ex.getMessage());
		}finally{
	           try{
	               if(resultSet!=null){
	                   resultSet.close();
	                   resultSet = null;
	               }
	               if(statement!=null){
	                   statement.close();
	                   statement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
                 System.out.println("Length--->"+documentList.size());
                return documentList;
            }

    
    
    
}
