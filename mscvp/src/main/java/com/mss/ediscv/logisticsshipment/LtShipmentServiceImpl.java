/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsshipment;

import com.mss.ediscv.util.ConnectionProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocatorException;
import com.mss.ediscv.util.WildCardSql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class LtShipmentServiceImpl implements LtShipmentService{
    Connection connection = null;
        Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
        String tmp_Recieved_From = "";
	String tmp_Recieved_ToTime = "";
	String strFormat = "yyyy-MM-dd";
	DateFormat myDateFormat = new SimpleDateFormat(strFormat);
	Calendar cal = new GregorianCalendar();
	java.util.Date now = cal.getTime();
	long time = now.getTime();
	java.sql.Date date = new java.sql.Date(time);
        
	int callableStatementUpdateCount;
	private ArrayList<LtShipmentBean> ltShipmentBeanList;
	private LtShipmentBean ltShipmentBean;

	private static Logger logger = Logger.getLogger(LtShipmentServiceImpl.class
			.getName());
        
     public ArrayList getLtResponseList(LogisticsShipmentAction logisticsShipmentAction)throws ServiceLocatorException {
            StringBuffer ltShipmentSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: LtResponseServiceImpl :::: getLtResponseList");
                 
             
               
                  String datepickerTo= logisticsShipmentAction.getDatepickerTo();
                  String datepickerfrom= logisticsShipmentAction.getDatepickerfrom();
            
                  String senderId= logisticsShipmentAction.getSenderId();
                  String senderName= logisticsShipmentAction.getSenderName();
                  String receiverId= logisticsShipmentAction.getReceiverId();
                  String receiverName= logisticsShipmentAction.getReceiverName();
                  //String docIsa= logisticsDocAction.getDocIsa();
                  String doctype="";
                  if(!logisticsShipmentAction.getDocType().equals("-1"))
                  doctype = logisticsShipmentAction.getDocType();
                  
                  String corrattribute = logisticsShipmentAction.getCorrattribute();
                  String corrvalue = logisticsShipmentAction.getCorrvalue();
                  //System.out.println("doctype-->"+doctype+"-->corrattribute-->"+corrattribute+"-->corrvalue-->"+corrvalue);
                  String corrattribute1 = logisticsShipmentAction.getCorrattribute1();
                  String corrvalue1 = logisticsShipmentAction.getCorrvalue1();
                  
              
                  
                  String status = logisticsShipmentAction.getStatus();
                  String ackStatus = logisticsShipmentAction.getAckStatus();
                  /*String docPoNum= docRepositoryAction.getDocPoNum();
                  String ponum= docRepositoryAction.getPonumber();
                  String asnnum= docRepositoryAction.getAsnnumber();
                  String invnum= docRepositoryAction.getInvnumber();
                  String bolnum= docRepositoryAction.getBolNum();
                  String chequeNum = docRepositoryAction.getChequeNum();*/
                  
                  ltShipmentSearchQuery.append("SELECT DISTINCT (FILES.FILE_ID) as FILE_ID,TRANSPORT_SHIPMENT.STOP_SEQ_NUM," 
                        +"FILES.ISA_NUMBER as ISA_NUMBER,FILES.FILE_TYPE as FILE_TYPE,FILES.CARRIER_STATUS  as CARRIER_STATUS,"
                        +"FILES.FILE_ORIGIN as FILE_ORIGIN,FILES.TRANSACTION_TYPE as TRANSACTION_TYPE,"
                        +"FILES.DIRECTION as DIRECTION,FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED,"
                        +"FILES.STATUS as STATUS,FILES.ACK_STATUS as ACK_STATUS,TP2.NAME as RECEIVER_NAME,"
                        +"FILES.SEC_KEY_VAL,FILES.REPROCESSSTATUS,TP2.NAME as RECEIVER_NAME,TRANSPORT_SHIPMENT.SHIPMENT_ID,TRANSPORT_SHIPMENT.ID as ID,TRANSPORT_SHIPMENT.PO_NUMBER "
                        +"FROM TRANSPORT_SHIPMENT "

                       
                         // +"LEFT OUTER JOIN FILES ON ((TRANSPORT_SHIPMENT.SHIPMENT_ID =FILES.SEC_KEY_VAL) "
                        //  + "AND ((TRANSPORT_SHIPMENT.BOL_NUMBER =FILES.PRI_KEY_VAL) OR "
                         // + "(TRANSPORT_SHIPMENT.STOP_SEQ_NUM =FILES.PRI_KEY_VAL))) "
                        +"LEFT OUTER JOIN FILES ON (TRANSPORT_SHIPMENT.FILE_ID =FILES.FILE_ID)"
                        +"LEFT OUTER JOIN TP TP1 ON (TP1.ID=FILES.SENDER_ID) "
                        +"LEFT OUTER JOIN TP TP2 ON (TP2.ID=FILES.RECEIVER_ID)");
                  
		ltShipmentSearchQuery.append(" WHERE 1=1 AND FILES.FLOWFLAG = 'L' ");
 
             
                // FOr PO
                if(corrattribute.equalsIgnoreCase("BOL Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.BOL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("BOL Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.BOL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                
                
                
                // For Invoice / Shipment / Cheque
                  //if(corrattribute1.equalsIgnoreCase("PO Number") || corrattribute1.equalsIgnoreCase("Invoice Number")  || corrattribute1.equalsIgnoreCase("Shipment Number") || corrattribute1.equalsIgnoreCase("Cheque Number") )
                
  
                
                if(corrattribute.equalsIgnoreCase("Shipment Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.SHIPMENT_ID",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("Shipment Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.SHIPMENT_ID",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
            
                     if(corrattribute.equalsIgnoreCase("PO Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.PO_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("PO Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.PO_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
                        if(corrattribute.equalsIgnoreCase("Stop Seq Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.STOP_SEQ_NUM",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("Stop Seq Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_SHIPMENT.STOP_SEQ_NUM",
					corrvalue1.trim().toUpperCase()));
		}
                }
                
                
                if (doctype != null && !"".equals(doctype.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                //Status
                 if (status != null && !"-1".equals(status.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
					status.trim()));
		}
                 //ACK_STATUS
                 if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
					ackStatus.trim()));
		}
               
             
                 
                
                
                if (receiverId != null && !"".equals(receiverId.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
					receiverId.trim().toUpperCase()));
		}
                
                if (senderId != null && !"".equals(senderId.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
					senderId.trim().toUpperCase()));
		}
                
                  if (senderName != null && !"".equals(senderName.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
					senderName.trim().toUpperCase()));
		}
                
                if (receiverName != null && !"".equals(receiverName.trim())) {
			ltShipmentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
					receiverName.trim().toUpperCase()));
		}
               
                if (datepickerTo != null && !"".equals(datepickerTo)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(datepickerTo);
			ltShipmentSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED <= '" + tmp_Recieved_From
					+ "'");
		}
		if (datepickerfrom != null && !"".equals(datepickerfrom)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(datepickerfrom);
			ltShipmentSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED >= '" + tmp_Recieved_From
					+ "'");
		}
                //ltShipmentSearchQuery.append(" order by DATE_TIME_RECEIVED DESC ");        
                        
                  ltShipmentSearchQuery.append(" order by DATE_TIME_RECEIVED DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                 System.out.println("Logistics Shipment query-->"+ltShipmentSearchQuery.toString());
                    String searchQuery = ltShipmentSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        ltShipmentBeanList  = new ArrayList<LtShipmentBean>();
                        
                        while(resultSet.next()) {
                           
                            ltShipmentBean = new LtShipmentBean();
                            ltShipmentBean.setInstanceId(resultSet.getString("FILE_ID"));
                            ltShipmentBean.setDateTime(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            ltShipmentBean.setAsnNum(resultSet.getString("SHIPMENT_ID"));
                            ltShipmentBean.setDirection(resultSet.getString("DIRECTION"));
                            //ltResponseBean.set(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            ltShipmentBean.setStatus(resultSet.getString("STATUS"));
                            ltShipmentBean.setPartner(resultSet.getString("RECEIVER_NAME"));
                            ltShipmentBean.setPoNum(resultSet.getString("PO_NUMBER"));
                           
                            
                            ltShipmentBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            ltShipmentBean.setCarrierStatus(resultSet.getString("CARRIER_STATUS"));
                            ltShipmentBean.setId(resultSet.getInt("ID"));
                            
                            
                            
                            ltShipmentBeanList.add(ltShipmentBean);   
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
                return ltShipmentBeanList;
            }
}
