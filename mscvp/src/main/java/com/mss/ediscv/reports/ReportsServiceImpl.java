/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.reports;

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
public class ReportsServiceImpl implements ReportsService{
     Connection connection = null;
	PreparedStatement preparedStatement = null;
        Statement statement = null;
	ResultSet resultSet = null;
             String tmp_Recieved_From = "";
	String tmp_Recieved_ToTime = "";
	String strFormat = "yyyy-MM-dd";
	DateFormat myDateFormat = new SimpleDateFormat(strFormat);
	Calendar cal = new GregorianCalendar();
	java.util.Date now = cal.getTime();
	long time = now.getTime();
	java.sql.Date date = new java.sql.Date(time);
	
        private static Logger logger = Logger.getLogger(ReportsServiceImpl.class.getName());
        String responseString=null;
    private ArrayList<ReportsBean> documentList;
    
    public ArrayList<ReportsBean> getDocumentList(ReportsAction reportsAction)throws ServiceLocatorException{
         StringBuffer documentSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: ReportsServiceImpl :::: getDocumentList");
                 
                 
                    String docdatepicker= reportsAction.getDocdatepicker();
                  String docdatepickerfrom= reportsAction.getDocdatepickerfrom();
                  String docSenderId="";
                    if(!reportsAction.getDocSenderId().equals("-1"))
                  docSenderId = reportsAction.getDocSenderId();
                  String docSenderName="";
                    if(!reportsAction.getDocSenderName().equals("-1"))
                  docSenderName = reportsAction.getDocSenderName();
                  String docBusId="";
                    if(!reportsAction.getDocReceiverId().equals("-1"))
                  docBusId = reportsAction.getDocReceiverId();
                  String docRecName="";
                   if(!reportsAction.getDocReceiverName().equals("-1"))
                  docRecName = reportsAction.getDocReceiverName();
                  
                  String doctype="";
                  if(!reportsAction.getDocType().equals("-1"))
                  doctype = reportsAction.getDocType();
                  
                
                  
                  String status = reportsAction.getStatus();
                  String ackStatus = reportsAction.getAckStatus();
               
                  
                  documentSearchQuery.append("SELECT DISTINCT(FILES.FILE_ID) as FILE_ID,FILES.ISA_NUMBER as ISA_NUMBER,"
                          + "FILES.FILE_TYPE as FILE_TYPE,FILES.FILE_ORIGIN as FILE_ORIGIN,"
                          + "FILES.TRANSACTION_TYPE as TRANSACTION_TYPE,FILES.DIRECTION as DIRECTION,"
                          + "FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED,FILES.STATUS as STATUS,FILES.ACK_STATUS as ACK_STATUS,"
                          + "TP2.NAME as RECEIVER_NAME,TP1.NAME as SENDER_NAME,FILES.SEC_KEY_VAL,FILES.REPROCESSSTATUS,FILES.ERR_MESSAGE as ERR_MESSAGE FROM FILES " 
                          + "LEFT OUTER JOIN TP TP1 ON (TP1.ID=FILES.SENDER_ID) LEFT OUTER JOIN TP TP2 "
                          + "ON (TP2.ID=FILES.RECEIVER_ID)"
                          + "LEFT OUTER JOIN ASN ON (ASN.FILE_ID = FILES.FILE_ID)");
                  
		documentSearchQuery.append(" WHERE 1=1 AND FLOWFLAG like 'M' ");
 
             
              
                
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
                  System.out.println("DOC queryquery prasad-->"+documentSearchQuery.toString());
                    String searchQuery = documentSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        documentList  = new ArrayList<ReportsBean>();
                        
                        while(resultSet.next()) {
                            ReportsBean reportsBean = new ReportsBean();
                            reportsBean.setFile_id(resultSet.getString("FILE_ID"));
                            reportsBean.setFile_origin(resultSet.getString("FILE_ORIGIN"));
                            reportsBean.setFile_type(resultSet.getString("FILE_TYPE"));
                            reportsBean.setIsa_number(resultSet.getString("ISA_NUMBER"));
                            reportsBean.setTransaction_type(resultSet.getString("TRANSACTION_TYPE"));
                            String direction = resultSet.getString("DIRECTION");
                            reportsBean.setDirection(direction);
                            reportsBean.setDate_time_rec(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            reportsBean.setStatus(resultSet.getString("STATUS"));
                            
                            //
                            if(direction.equalsIgnoreCase("INBOUND")){
                                reportsBean.setPname(resultSet.getString("SENDER_NAME"));
                            }else{
                                reportsBean.setPname(resultSet.getString("RECEIVER_NAME"));
                            }
                            
                            reportsBean.setPoNumber(resultSet.getString("SEC_KEY_VAL"));
                            reportsBean.setReProcessStatus(resultSet.getString("REPROCESSSTATUS"));
                            reportsBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            reportsBean.setErrorMessage(resultSet.getString("ERR_MESSAGE"));
                            documentList.add(reportsBean);  
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
