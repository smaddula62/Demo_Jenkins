/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.ltResponse;

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
public class LtResponseServiceImpl implements LtResponseService{
        
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
	private ArrayList<LtResponseBean> ltResponseList;
	private LtResponseBean ltResponseBean;

	private static Logger logger = Logger.getLogger(LtResponseServiceImpl.class
			.getName());
         public ArrayList<LtResponseBean> getLtResponseList(LtResponse ltResponse)throws ServiceLocatorException {
            StringBuffer ltResponseSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: LtResponseServiceImpl :::: getLtResponseList");
                 
             
               
                  String datepickerTo= ltResponse.getDatepickerTo();
                  String datepickerfrom= ltResponse.getDatepickerfrom();
            
                  String senderId= ltResponse.getSenderId();
                  String senderName= ltResponse.getSenderName();
                  String receiverId= ltResponse.getReceiverId();
                  String receiverName= ltResponse.getReceiverName();
                  //String docIsa= logisticsDocAction.getDocIsa();
                  String doctype="";
                  if(!ltResponse.getDocType().equals("-1"))
                  doctype = ltResponse.getDocType();
                  
                  String corrattribute = ltResponse.getCorrattribute();
                  String corrvalue = ltResponse.getCorrvalue();
                  //System.out.println("doctype-->"+doctype+"-->corrattribute-->"+corrattribute+"-->corrvalue-->"+corrvalue);
                  String corrattribute1 = ltResponse.getCorrattribute1();
                  String corrvalue1 = ltResponse.getCorrvalue1();
                  
              
                  
                  String status = ltResponse.getStatus();
                  String ackStatus = ltResponse.getAckStatus();
                  /*String docPoNum= docRepositoryAction.getDocPoNum();
                  String ponum= docRepositoryAction.getPonumber();
                  String asnnum= docRepositoryAction.getAsnnumber();
                  String invnum= docRepositoryAction.getInvnumber();
                  String bolnum= docRepositoryAction.getBolNum();
                  String chequeNum = docRepositoryAction.getChequeNum();*/
                  
                  ltResponseSearchQuery.append("SELECT DISTINCT(FILES.FILE_ID) as FILE_ID,TRANSPORT_LT_RESPONSE.REF_ID,TRANSPORT_LT_RESPONSE.SHIPMENT_ID as SHIPMENT_ID," 
                        +"FILES.ISA_NUMBER as ISA_NUMBER,FILES.FILE_TYPE as FILE_TYPE,"
                        +"FILES.FILE_ORIGIN as FILE_ORIGIN,FILES.TRANSACTION_TYPE as TRANSACTION_TYPE,"
                        +"FILES.DIRECTION as DIRECTION,FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED,"
                        +"FILES.STATUS as STATUS,FILES.ACK_STATUS as ACK_STATUS,TP2.NAME as RECEIVER_NAME,"
                        +"FILES.SEC_KEY_VAL,FILES.REPROCESSSTATUS "
                        +"FROM TRANSPORT_LT_RESPONSE "

                        +"LEFT OUTER JOIN FILES ON (TRANSPORT_LT_RESPONSE.FILE_ID =FILES.FILE_ID)"

                        +"LEFT OUTER JOIN TP TP1 ON (TP1.ID=FILES.SENDER_ID) "
                        +"LEFT OUTER JOIN TP TP2 ON (TP2.ID=FILES.RECEIVER_ID)");
                  
		ltResponseSearchQuery.append(" WHERE 1=1 AND FILES.FLOWFLAG = 'L' ");
 
             
                // FOr PO
                if(corrattribute.equalsIgnoreCase("Ref Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_LT_RESPONSE.REF_ID",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("Ref Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_LT_RESPONSE.REF_ID",
					corrvalue1.trim().toUpperCase()));
		}
                
                
                
                // For Invoice / Shipment / Cheque
                  //if(corrattribute1.equalsIgnoreCase("PO Number") || corrattribute1.equalsIgnoreCase("Invoice Number")  || corrattribute1.equalsIgnoreCase("Shipment Number") || corrattribute1.equalsIgnoreCase("Cheque Number") )
                
  
                
                if(corrattribute.equalsIgnoreCase("Shipment Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_LT_RESPONSE.SHIPMENT_ID",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("Shipment Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TRANSPORT_LT_RESPONSE.SHIPMENT_ID",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
            
                
                
                if (doctype != null && !"".equals(doctype.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                //Status
                 if (status != null && !"-1".equals(status.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
					status.trim()));
		}
                 //ACK_STATUS
                 if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
					ackStatus.trim()));
		}
               
             
                 
                
                
                if (receiverId != null && !"".equals(receiverId.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
					receiverId.trim().toUpperCase()));
		}
                
                if (senderId != null && !"".equals(senderId.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
					senderId.trim().toUpperCase()));
		}
                
                  if (senderName != null && !"".equals(senderName.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
					senderName.trim().toUpperCase()));
		}
                
                if (receiverName != null && !"".equals(receiverName.trim())) {
			ltResponseSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
					receiverName.trim().toUpperCase()));
		}
               
                if (datepickerTo != null && !"".equals(datepickerTo)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(datepickerTo);
			ltResponseSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED <= '" + tmp_Recieved_From
					+ "'");
		}
		if (datepickerfrom != null && !"".equals(datepickerfrom)) {
			  tmp_Recieved_From = DateUtility.getInstance().DateViewToDBCompare(datepickerfrom);
			ltResponseSearchQuery.append(" AND FILES.DATE_TIME_RECEIVED >= '" + tmp_Recieved_From
					+ "'");
		}
                        
                  ltResponseSearchQuery.append(" order by DATE_TIME_RECEIVED DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                 // System.out.println("Lt Response query-->"+ltResponseSearchQuery.toString());
                    String searchQuery = ltResponseSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        ltResponseList  = new ArrayList<LtResponseBean>();
                        
                        while(resultSet.next()) {
                            LtResponseBean ltResponseBean = new LtResponseBean();
                            ltResponseBean.setFileId(resultSet.getString("FILE_ID"));
                            ltResponseBean.setFileOrgin(resultSet.getString("FILE_ORIGIN"));
                            ltResponseBean.setFileType(resultSet.getString("FILE_TYPE"));
                            ltResponseBean.setIsaNum(resultSet.getString("ISA_NUMBER"));
                            ltResponseBean.setTransType(resultSet.getString("TRANSACTION_TYPE"));
                            ltResponseBean.setDirection(resultSet.getString("DIRECTION"));
                            //ltResponseBean.set(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            ltResponseBean.setStatus(resultSet.getString("STATUS"));
                            ltResponseBean.setPartnerName(resultSet.getString("RECEIVER_NAME"));
                            ltResponseBean.setPoNum(resultSet.getString("SEC_KEY_VAL"));
                            ltResponseBean.setReprocess(resultSet.getString("REPROCESSSTATUS"));
                            ltResponseBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            ltResponseBean.setRefId(resultSet.getString("REF_ID"));
                            ltResponseBean.setShipmentId(resultSet.getString("SHIPMENT_ID"));
                            
                            
                            ltResponseList.add(ltResponseBean);  
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
                return ltResponseList;
            }
}
