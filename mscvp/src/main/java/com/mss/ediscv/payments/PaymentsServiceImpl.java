/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.payments;

import com.mss.ediscv.util.ConnectionProvider;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;


import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocatorException;
import com.mss.ediscv.util.WildCardSql;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 *
 * @author miracle
 */
public class PaymentsServiceImpl implements PaymentsService {
    
   
        
	Connection connection = null;
	PreparedStatement preparedStatement = null;
        Statement statement = null;
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
        private ArrayList<PaymentBean> paymentList;
        private PaymentBean paymentBean;
	private static Logger logger = Logger.getLogger(PaymentsServiceImpl.class.getName());
        
        public ArrayList<PaymentBean> buildpaymentSQuery(PaymentsAction paymentsAction) throws ServiceLocatorException {
            StringBuffer paymentSearchQuery = new StringBuffer();
            logger.info("Entered into the :::: PaymentsServiceImpl :::: buildpaymentSQuery");
            
            String paDateTo = paymentsAction.getPaDateTo();
            String paDateFrom= paymentsAction.getPaDateFrom();
            String paSenderId ="";
             if(!paymentsAction.getPaSenderId().equals("-1"))
                  paSenderId = paymentsAction.getPaSenderId();
            String paSenderName ="";
             if(!paymentsAction.getPaSenderName().equals("-1"))
                  paSenderName = paymentsAction.getPaSenderName();
            String paRecId ="";
             if(!paymentsAction.getPaRecId().equals("-1"))
                  paRecId = paymentsAction.getPaRecId();
            String paRecName ="";
             if(!paymentsAction.getPaRecName().equals("-1"))
                  paRecName = paymentsAction.getPaRecName();
            //String paChequeNo = paymentsAction.getPaChequeNo();
            // New Search field 
           // String paChequeAmt = paymentsAction.getPaChequeAmt();
            
            String status = paymentsAction.getStatus();
            String ackStatus = paymentsAction.getAckStatus();
            
             String corrattribute = paymentsAction.getCorrattribute();
                  String corrvalue = paymentsAction.getCorrvalue();
                  
             String corrattribute1 = paymentsAction.getCorrattribute1();
                  String corrvalue1 = paymentsAction.getCorrvalue1();
                  String doctype="";
                  if(!paymentsAction.getDocType().equals("-1"))
                  doctype = paymentsAction.getDocType();
                /*paymentSearchQuery.append("SELECT * FROM Payment");
		paymentSearchQuery.append(" WHERE 1=1 ");*/
             paymentSearchQuery.append("SELECT DISTINCT(PAYMENT.FILE_ID) as FILE_ID,FILES.TRANSACTION_TYPE,TP1.ID as Sender_ID, TP1.NAME as Sender_Name, "
                     + "PAYMENT.DATE as Date, PAYMENT.Check_Amount as Check_Amount,FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED, "
                     + "PAYMENT.Check_Number as Check_Number,PAYMENT.INVOICE_NUMBER as INVOICE_NUMBER,PAYMENT.PO_NUMBER as PO_NUMBER,"
                     + "TP2.NAME as ReceiverName,FILES.ACK_STATUS as ACK_STATUS,FILES.STATUS as STATUS,FILES.REPROCESSSTATUS FROM PAYMENT ");
                paymentSearchQuery.append("LEFT OUTER JOIN FILES ON (PAYMENT.FILE_ID=FILES.FILE_ID) ");
                paymentSearchQuery.append("LEFT OUTER JOIN TP TP1 ON (TP1.ID=FILES.SENDER_ID) ");
                paymentSearchQuery.append("LEFT OUTER JOIN TP TP2 ON (TP2.ID=FILES.RECEIVER_ID) ");
		paymentSearchQuery.append("WHERE 1=1 AND FLOWFLAG like 'M' ");
            
                if (paDateFrom != null && !"".equals(paDateFrom)) {
                    
                     StringTokenizer st = new StringTokenizer(paDateFrom," ");
                           String paDateFrom1 = st.nextToken();
                           
			tmp_Recieved_From = DateUtility.getInstance().formatToDB2Date(paDateFrom1);
			paymentSearchQuery.append(" AND PAYMENT.DATE >= '" + tmp_Recieved_From
					+ "'");
		}
		if (paDateTo != null && !"".equals(paDateTo)) {
                    
                     StringTokenizer st = new StringTokenizer(paDateTo," ");
                           String paDateTo1 = st.nextToken();
                           
			tmp_Recieved_ToTime = DateUtility.getInstance().formatToDB2Date(paDateTo1);
			paymentSearchQuery.append(" AND PAYMENT.DATE <= '" + tmp_Recieved_ToTime
					+ "'");
		}
                
                if(corrattribute.equalsIgnoreCase("Cheque Number"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                if(corrattribute.equalsIgnoreCase("Cheque Amount"))
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("PAYMENT.Check_Amount",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("Cheque Number"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
               if(corrattribute1.equalsIgnoreCase("Cheque Amount"))
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("PAYMENT.Check_Amount",
					corrvalue1.trim().toUpperCase()));
		}
                
                /*if (paChequeNo != null && !"".equals(paChequeNo.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("PAYMENT.Check_Number",
					paChequeNo.trim().toUpperCase()));
		}*/
               /* if (paChequeAmt != null && !"".equals(paChequeAmt.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("PAYMENT.Check_Amount",
					paChequeAmt.trim().toUpperCase()));
		}*/
                //Doc Type
                 if (doctype != null && !"".equals(doctype.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                 //Status
                if (status != null && !"-1".equals(status.trim())) {
                    paymentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
                                status.trim()));
                }
                //ACK_STATUS
                if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
                    paymentSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
                                ackStatus.trim()));
                }
                
                 if (paSenderId != null && !"".equals(paSenderId.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
					paSenderId.trim().toUpperCase()));
		}
                if (paSenderName != null && !"".equals(paSenderName.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
					paSenderName.trim().toUpperCase()));
		}
                
                // Reciver Id and Reciver Name conditions 
                 if (paRecId != null && !"".equals(paRecId.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
					paRecId.trim().toUpperCase()));
		}
                if (paRecName != null && !"".equals(paRecName.trim())) {
			paymentSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
					paRecName.trim().toUpperCase()));
		} 
               
                paymentSearchQuery.append("order by DATE_TIME_RECEIVED ASC fetch first 50 rows only"); 
              //  System.out.println(" payments query-->"+paymentSearchQuery.toString());
                
		logger.info("End of :::: PaymentsServiceImpl :::: buildpaymentSQuery");
                String searchQuery=paymentSearchQuery.toString();
                
                try {
                    
                    connection = ConnectionProvider.getInstance().getConnection();
			statement = connection.createStatement();
                      //  System.out.println("before execute query!!");
			resultSet=statement.executeQuery(searchQuery);
                     //   System.out.println("after execute query!!!");
                        paymentList = new ArrayList<PaymentBean>();
                     //   System.out.println("before while loop!!");
                        while(resultSet.next()) {
                         //   System.out.println("in while loop!!");
                           PaymentBean  paymentBean = new PaymentBean();
                           paymentBean.setSenderId(resultSet.getString("Sender_ID"));
                           paymentBean.setSenderName(resultSet.getString("Sender_Name"));
                           paymentBean.setDate(resultSet.getString("Date"));
                           paymentBean.setCheckAmount(resultSet.getString("Check_Amount"));
                           paymentBean.setCheckNumber(resultSet.getString("Check_Number"));
                           paymentBean.setFileId(resultSet.getString("FILE_ID"));
                           paymentBean.setReceiverName(resultSet.getString("ReceiverName"));
                           paymentBean.setInvNumber(resultSet.getString("INVOICE_NUMBER"));
                           paymentBean.setPonumber(resultSet.getString("PO_NUMBER"));
                           paymentBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                           paymentBean.setStatus(resultSet.getString("STATUS"));
                           //
                           paymentBean.setTransType(resultSet.getString("TRANSACTION_TYPE"));
                           paymentBean.setDate_time_rec(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            paymentBean.setReProcessStatus(resultSet.getString("REPROCESSSTATUS"));
                           paymentList.add(paymentBean);
                        }
                }catch (SQLException e) {
			System.out.println("I am in catch block coming in IMpl");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception ex){
			System.out.println("hi"+ex.getMessage());
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
                
		//return paymentSearchQuery.toString();	
                return paymentList;
        }
}
