/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsinvoice;

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
public class LogisticsInvoiceServiceImpl implements LogisticsInvoiceService{
    
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
	private ArrayList<LogisticsInvoiceBean> invList;
	private LogisticsInvoiceBean logisticsBean;

	private static Logger logger = Logger.getLogger(com.mss.ediscv.shipment.ShipmentServiceImpl.class
			.getName());

     public ArrayList<LogisticsInvoiceBean> buildLogInvoiceQuery(LogisticsInvoiceAction logisticsInvoiceAction)throws ServiceLocatorException {
            StringBuffer invSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: LogisticsInvoiceServiceImpl :::: buildLogInvoiceQuery");
                 
             
               
                  String datepickerTo= logisticsInvoiceAction.getDatepickerTo();
                  String datepickerFrom= logisticsInvoiceAction.getDatepickerfrom();
            
                  String invSenderId= logisticsInvoiceAction.getInvSenderId();
                  String invSenderName= logisticsInvoiceAction.getInvSenderName();
                  String invRecId= logisticsInvoiceAction.getInvReceiverId();
                  String invRecName= logisticsInvoiceAction.getInvReceiverName();
                  //String invIsa= logisticsDocAction.getDocIsa();
                  String doctype="";
                  if(!logisticsInvoiceAction.getDocType().equals("-1"))
                  doctype = logisticsInvoiceAction.getDocType();
                  
                  String corrattribute = logisticsInvoiceAction.getCorrattribute();
                  String corrvalue = logisticsInvoiceAction.getCorrvalue();
                     String corrattribute1 = logisticsInvoiceAction.getCorrattribute1();
                  String corrvalue1 = logisticsInvoiceAction.getCorrvalue1();
                  //System.out.println("doctype-->"+doctype+"-->corrattribute-->"+corrattribute+"-->corrvalue-->"+corrvalue);
                 
                  
                  String status = logisticsInvoiceAction.getStatus();
                  String ackStatus = logisticsInvoiceAction.getAckStatus();
                  /*String docPoNum= docRepositoryAction.getDocPoNum();
                  String ponum= docRepositoryAction.getPonumber();
                  String asnnum= docRepositoryAction.getAsnnumber();
                  String invnum= docRepositoryAction.getInvnumber();
                  String bolnum= docRepositoryAction.getBolNum();
                  String chequeNum = docRepositoryAction.getChequeNum();*/
                  
                  invSearchQuery.append("SELECT DISTINCT("
                          + "TRANSPORT_INVOICE.INVOICE_NUMBER ) as INVOICE_NUMBER,"
                                            +"FILES.FILE_ID as FILE_ID,TRANSPORT_INVOICE.ID as ID,"
                                            +"TP2.NAME as REC_NAME,"
                                            +"TRANSPORT_INVOICE.PO_NUMBER as PO_NUMBER,"
                                            +"TRANSPORT_INVOICE.SHIPMENT_ID as SHIPMENT_ID,"
                                            //TRANSPORT_INVOICE.SHIPMENT_ID
                                            +"TRANSPORT_INVOICE.TOTAL_WEIGHT as TOTAL_WEIGHT,"
                                            +"TRANSPORT_INVOICE.TOTAL_AMOUNT as TOTAL_AMOUNT,"
                                            +"TRANSPORT_INVOICE.INVOICE_DATE as INVOICE_DATE,"
                                            +"FILES.STATUS as STATUS,"
                                            +"FILES.ACK_STATUS as ACK_STATUS"
                                            +" FROM TRANSPORT_INVOICE "
                                           // +"LEFT OUTER JOIN FILES"
                                           // +" ON (TRANSPORT_INVOICE.INVOICE_NUMBER = FILES.PRI_KEY_VAL "
                                            //+" AND "
                                           // +"TRANSPORT_INVOICE.SHIPMENT_ID=FILES.SEC_KEY_VAL)"
                                            +"LEFT OUTER JOIN FILES ON (TRANSPORT_INVOICE.FILE_ID =FILES.FILE_ID) "
                                            +" LEFT OUTER JOIN TP TP1 "
                                            +"ON (TP1.ID=FILES.SENDER_ID) "
                                            +"LEFT OUTER JOIN TP TP2 "
                                            +"ON (TP2.ID=FILES.RECEIVER_ID)");

		invSearchQuery.append(" WHERE 1=1 ");
                if (datepickerFrom != null && !"".equals(datepickerFrom)) {
                        tmp_Recieved_From = DateUtility.getInstance()
                                        .DateViewToDBCompare(datepickerFrom);
                        //System.out.println("tmp_Recieved_From---inINV---->"+tmp_Recieved_From);
                        invSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED >= '" + tmp_Recieved_From
                                        + "'");
                }
                if (datepickerTo != null && !"".equals(datepickerTo)) {
                        tmp_Recieved_ToTime = DateUtility.getInstance()
                                        .DateViewToDBCompare(datepickerTo);
                       // System.out.println("tmp_Recieved_ToTime-----ININV ---->"+tmp_Recieved_ToTime);
                        invSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED <= '" + tmp_Recieved_ToTime
                                        + "'");
                }
                      
                  		 if(corrattribute.equalsIgnoreCase("Invoice Number"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                                   		 if(corrattribute1.equalsIgnoreCase("Invoice Number"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
                    		 if(corrattribute.equalsIgnoreCase("PO Number"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_INVOICE.PO_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                                   		 if(corrattribute1.equalsIgnoreCase("PO Number"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_INVOICE.PO_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                   		 if(corrattribute.equalsIgnoreCase("Shipment Number"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_INVOICE.SHIPMENT_ID",
					corrvalue.trim().toUpperCase()));
		}
                                   		 if(corrattribute1.equalsIgnoreCase("Shipment Number"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_INVOICE.SHIPMENT_ID",
					corrvalue1.trim().toUpperCase()));
		}
                //DocType
                   if (doctype != null && !"".equals(doctype.trim())) {
			invSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                //Status
                if (status != null && !"-1".equals(status.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
                                status.trim()));
                }
                //ACK_STATUS
                if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
                                ackStatus.trim()));
                }
                        
                if (invSenderId != null && !"".equals(invSenderId.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
                                invSenderId.trim().toUpperCase()));
                }
                if (invRecId != null && !"".equals(invRecId.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
                                invRecId.trim().toUpperCase()));
                }

                if (invSenderName != null && !"".equals(invSenderName.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
                                invSenderName.trim().toUpperCase()));
                }

                if (invRecName != null && !"".equals(invRecName.trim())) {
                    invSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
                                invRecName.trim().toUpperCase()));
                }
             
                
                   // invSearchQuery.append(" order by INVOICE_DATE DESC");      
                  invSearchQuery.append(" order by INVOICE_DATE DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                  System.out.println("Logistics Inv query-->"+invSearchQuery.toString());
                    String searchQuery = invSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        invList  = new ArrayList<LogisticsInvoiceBean>();
                        
                        while(resultSet.next()) {
                            LogisticsInvoiceBean logisticsInvoiceBean = new LogisticsInvoiceBean();
                            logisticsInvoiceBean.setInstanceId(resultSet.getString("FILE_ID"));
                            logisticsInvoiceBean.setPartner(resultSet.getString("REC_NAME"));
                            logisticsInvoiceBean.setInvoiceNumber(resultSet.getString("INVOICE_NUMBER"));
                            logisticsInvoiceBean.setPoNumber(resultSet.getString("PO_NUMBER"));
                            logisticsInvoiceBean.setItemQty(resultSet.getString("TOTAL_WEIGHT"));
                            logisticsInvoiceBean.setInvAmount(resultSet.getString("TOTAL_AMOUNT"));
                            logisticsInvoiceBean.setShipmentId(resultSet.getString("SHIPMENT_ID"));
                            if(resultSet.getString("INVOICE_DATE")!=null) {
                            logisticsInvoiceBean.setInvDate(resultSet.getString("INVOICE_DATE"));
                            }
                            else {
                               logisticsInvoiceBean.setInvDate(""); 
                            }
                                
                            logisticsInvoiceBean.setStatus(resultSet.getString("STATUS"));
                            
                            logisticsInvoiceBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            logisticsInvoiceBean.setId(resultSet.getInt("ID"));
                            invList.add(logisticsInvoiceBean);  
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
                // System.out.println("Length--->"+purchaseList.size());*/
                return invList;
            }

    
    
    
}
