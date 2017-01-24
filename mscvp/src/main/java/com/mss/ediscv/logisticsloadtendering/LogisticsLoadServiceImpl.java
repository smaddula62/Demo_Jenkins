/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsloadtendering;

import com.mss.ediscv.doc.DocRepositoryBean;
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
public class LogisticsLoadServiceImpl implements LogisticsLoadService{
    
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
	private ArrayList<LogisticsLoadBean> documentList;
	private LogisticsLoadBean logisticsBean;

	private static Logger logger = Logger.getLogger(com.mss.ediscv.shipment.ShipmentServiceImpl.class
			.getName());

     public ArrayList<LogisticsLoadBean> buildLoadQuery(LogisticsLoadAction logisticsDocAction)throws ServiceLocatorException {
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
                  
                  documentSearchQuery.append("SELECT tf.FILE_ID as file_id,tf.ISA_NUMBER as isa_number,tl.SHIPMENT_ID as SHIPMENT_ID,"
                          + "tf.FILE_TYPE as file_type,tf.FILE_ORIGIN as file_origin,tf.TRANSACTION_TYPE as tran_type,"
                          + "tf.ACK_STATUS as ack_status,tf.DIRECTION as direction,tf.DATE_TIME_RECEIVED as datetime,"
                          + "tf.STATUS as status,tp2.NAME as name,tf.SEC_KEY_VAL as secval,tf.REPROCESSSTATUS as REPROCESSSTATUS "
                          + "FROM Transport_loadtender tl LEFT OUTER JOIN FILES TF ON "
                          + "(tl.FILE_ID=tf.FILE_ID and tl.SHIPMENT_ID=tf.SEC_KEY_VAL)  "
                          + "LEFT OUTER JOIN TP TP1 ON(TP1.ID=TF.SENDER_ID ) "
                          + "LEFT OUTER JOIN TP TP2 ON(TP2.ID=TF.RECEIVER_ID)");
                  
		documentSearchQuery.append(" WHERE 1=1 AND tf.FLOWFLAG LIKE '%L%'");
 
             
                // FOr PO
                if(corrattribute.equalsIgnoreCase("Shipment Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.SHIPMENT_ID",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("Shipment Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.SHIPMENT_ID",
					corrvalue1.trim().toUpperCase()));
		}
                
                if(corrattribute2.equalsIgnoreCase("Shipment Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.SHIPMENT_ID",
					corrvalue2.trim().toUpperCase()));
		}
                
                // For Invoice / Shipment / Cheque
                  //if(corrattribute1.equalsIgnoreCase("PO Number") || corrattribute1.equalsIgnoreCase("Invoice Number")  || corrattribute1.equalsIgnoreCase("Shipment Number") || corrattribute1.equalsIgnoreCase("Cheque Number") )
                
                if(corrattribute.equalsIgnoreCase("BOL Number"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.BOL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("BOL Number"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.BOL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                  
               if(corrattribute2.equalsIgnoreCase("BOL Number"))
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.BOL_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                // isa 
                
                if(corrattribute.equalsIgnoreCase("PO Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.PO_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("PO Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.PO_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
                      if(corrattribute2.equalsIgnoreCase("PO Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.PO_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                // bol
                if(corrattribute.equalsIgnoreCase("CO Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.CO_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                if(corrattribute1.equalsIgnoreCase("CO Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.CO_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("CO Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("tl.CO_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                
                if (doctype != null && !"".equals(doctype.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TF.TRANSACTION_TYPE",
					doctype.trim()));
		}
                //Status
                 if (status != null && !"-1".equals(status.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TF.STATUS",
					status.trim()));
		}
                 //ACK_STATUS
                 if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TF.ACK_STATUS",
					ackStatus.trim()));
		}
                // gs number
             /*   if(corrattribute.equalsIgnoreCase("GS Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_FILES.GS_CONTROL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                 if(corrattribute1.equalsIgnoreCase("GS Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_FILES.GS_CONTROL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("GS Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_FILES.GS_CONTROL_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
               */  
                
                
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
			documentSearchQuery.append(" AND tf.DATE_TIME_RECEIVED <= '" + tmp_Recieved_From
					+ "'");
		}
		if (docdatepickerfrom != null && !"".equals(docdatepickerfrom)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(docdatepickerfrom);
			documentSearchQuery.append(" AND tf.DATE_TIME_RECEIVED >= '" + tmp_Recieved_From
					+ "'");
		}
                        
                  documentSearchQuery.append(" order by DATE_TIME_RECEIVED DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                 System.out.println("load query-->"+documentSearchQuery.toString());
                    String searchQuery = documentSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        documentList  = new ArrayList<LogisticsLoadBean>();
                        
                        while(resultSet.next()) {
                            LogisticsLoadBean logisticsdocBean = new LogisticsLoadBean();
                            logisticsdocBean.setFile_id(resultSet.getString("file_id"));
                            logisticsdocBean.setFile_origin(resultSet.getString("file_origin"));
                            logisticsdocBean.setFile_type(resultSet.getString("file_type"));
                            logisticsdocBean.setIsa_number(resultSet.getString("isa_number"));
                            logisticsdocBean.setTransaction_type(resultSet.getString("tran_type"));
                            logisticsdocBean.setDirection(resultSet.getString("direction"));
                            logisticsdocBean.setDate_time_rec(resultSet.getTimestamp("datetime"));
                            logisticsdocBean.setStatus(resultSet.getString("status"));
                            logisticsdocBean.setPname(resultSet.getString("name"));
                            logisticsdocBean.setPoNumber(resultSet.getString("secval"));
                            logisticsdocBean.setReProcessStatus(resultSet.getString("REPROCESSSTATUS"));
                            logisticsdocBean.setAckStatus(resultSet.getString("ack_status"));
                            logisticsdocBean.setShipmentId(resultSet.getString("SHIPMENT_ID"));
                            
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
                // System.out.println("Length--->"+purchaseList.size());
                return documentList;
            }

    
    
    
}
