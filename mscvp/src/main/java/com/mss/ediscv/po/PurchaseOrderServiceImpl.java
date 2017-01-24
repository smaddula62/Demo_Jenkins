
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.po;

import com.mss.ediscv.util.ConnectionProvider;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocatorException;
import com.mss.ediscv.util.WildCardSql;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;
import java.util.List;
import java.util.StringTokenizer;
/**
 *
 * @author miracle
 */
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
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
	private ArrayList<PurchaseOrderBean> purchaseList;
	private PurchaseOrderBean purchaseBean;

	private static Logger logger = Logger.getLogger(PurchaseOrderServiceImpl.class
			.getName());
        /**
         * 
         * 
         * @param purchaseOrderAction
         * @return
         * @throws ServiceLocatorException 
         */
        
        public ArrayList<PurchaseOrderBean> buildPurchaseQuery(PurchaseOrderAction purchaseOrderAction)throws ServiceLocatorException {
            StringBuffer purchaseSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: PurchaseOrderServiceImpl :::: buildPurchaseQuery");
            
                String poDateTo = purchaseOrderAction.getPoDateTo();
                String poDateFrom = purchaseOrderAction.getPoDateFrom();
                String poRecId ="";
                 if(!purchaseOrderAction.getPoRecId().equals("-1")) 
                     poRecId= purchaseOrderAction.getPoRecId();
                String poRecName ="";
                  if(!purchaseOrderAction.getPoRecName().equals("-1")) 
                     poRecName= purchaseOrderAction.getPoRecName();
                String poSenderId ="";
                if(!purchaseOrderAction.getPoSenderId().equals("-1")) 
                    poSenderId= purchaseOrderAction.getPoSenderId();
                String poSenderName ="";
                if(!purchaseOrderAction.getPoSenderName().equals("-1")) 
                   poSenderName= purchaseOrderAction.getPoSenderName();
              //  String poNumber = purchaseOrderAction.getPoNumber();
                String ackStatus = purchaseOrderAction.getAckStatus();
              //  String gsNumber = purchaseOrderAction.getGsNumber();
                String status = purchaseOrderAction.getStatus();
                
                  String corrattribute = purchaseOrderAction.getCorrattribute();
                  String corrvalue = purchaseOrderAction.getCorrvalue();
                  //System.out.println("doctype-->"+doctype+"-->corrattribute-->"+corrattribute+"-->corrvalue-->"+corrvalue);
                  String corrattribute1 = purchaseOrderAction.getCorrattribute1();
                  String corrvalue1 = purchaseOrderAction.getCorrvalue1();
                  
                   String corrattribute2 = purchaseOrderAction.getCorrattribute2();
                  String corrvalue2 = purchaseOrderAction.getCorrvalue2();
                  
                   String doctype="";
                  if(!purchaseOrderAction.getDocType().equals("-1"))
                  doctype = purchaseOrderAction.getDocType();
                
                         purchaseSearchQuery.append("SELECT DISTINCT(FILES.FILE_ID) as FILE_ID,PO.PO_NUMBER as PO_NUMBER,PO.SO_NUMBER as SO_NUMBER,"
                                 + "PO.SAP_IDOC_NUMBER as SAP_IDOC_NUMBER,PO.ORDER_DATE as ORDER_DATE,PO.SHIP_DATE as SHIP_DATE,"
                                 + "PO.ORDER_STATUS AS ORDER_STATUS,PO.ISA_CONTROL_NUMBER as ISA_CONTROL_NUMBER,PO.ITEM_QTY as ITEM_QTY,"
                                 + "FILES.DIRECTION as DIRECTION,FILES.GS_CONTROL_NUMBER as GS_CONTROL_NUMBER,FILES.STATUS as STATUS ,FILES.ACK_STATUS as ACK_STATUS ,TP2.NAME as RECEIVER_NAME,TP1.NAME as SENDER_NAME,"
                                 + "FILES.DATE_TIME_RECEIVED as DATE_TIME_RECEIVED,FILES.REPROCESSSTATUS "
                                 + "FROM PO "
                                + "LEFT OUTER JOIN FILES ON (PO.PO_NUMBER=FILES.PRI_KEY_VAL AND PO.FILE_ID = FILES.FILE_ID)"
                                + "LEFT OUTER JOIN TP TP1 ON (TP1.ID=FILES.SENDER_ID) "
                                + "LEFT OUTER JOIN TP TP2 ON (TP2.ID=FILES.RECEIVER_ID)");
                        purchaseSearchQuery.append(" WHERE 1=1 AND FILES.FLOWFLAG like 'M' ");
                        
                     
                        
                        // FOr PO
                if(corrattribute.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue.trim().toUpperCase()));
		}
                
                if(corrattribute1.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue1.trim().toUpperCase()));
		}
                
                if(corrattribute2.equalsIgnoreCase("PO Number"))
                       // || corrattribute.equalsIgnoreCase("Invoice Number")  || corrattribute.equalsIgnoreCase("Shipment Number") || corrattribute.equalsIgnoreCase("Cheque Number") )
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.PRI_KEY_VAL",
					corrvalue2.trim().toUpperCase()));
		}
                
                if(corrattribute.equalsIgnoreCase("ISA Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue.trim().toUpperCase()));
		}
                }
                
                   if(corrattribute1.equalsIgnoreCase("ISA Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue1.trim().toUpperCase()));
		}
                }
                   
                      if(corrattribute2.equalsIgnoreCase("ISA Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ISA_Number",
					corrvalue2.trim().toUpperCase()));
		}
                }
                  
                      // gs number
                if(corrattribute.equalsIgnoreCase("GS Number")){
                if (corrvalue != null && !"".equals(corrvalue.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue.trim().toUpperCase()));
		}
                }
                 if(corrattribute1.equalsIgnoreCase("GS Number")){
                if (corrvalue1 != null && !"".equals(corrvalue1.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue1.trim().toUpperCase()));
		}
                }
                if(corrattribute2.equalsIgnoreCase("GS Number")){
                if (corrvalue2 != null && !"".equals(corrvalue2.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.GS_CONTROL_NUMBER",
					corrvalue2.trim().toUpperCase()));
		}
                }
                
                        //Status
                         if (status != null && !"-1".equals(status.trim())) {
                                purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.STATUS",
                                                status.trim()));
                        }
                           //ACK_STATUS
                         if (ackStatus != null && !"-1".equals(ackStatus.trim())) {
                                purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.ACK_STATUS",
                                                ackStatus.trim()));
                        }
                         //Doc Type
                            if (doctype != null && !"".equals(doctype.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("FILES.TRANSACTION_TYPE",
					doctype.trim()));
		}
                        if (poDateFrom != null && !"".equals(poDateFrom)) {
                            
                           StringTokenizer st = new StringTokenizer(poDateFrom," ");
                           String poDateFrom1 = st.nextToken();
                          // System.out.println("poDateFrom1--->"+poDateFrom1);
                         
                            tmp_Recieved_From = DateUtility.getInstance().formatToDB2Date(poDateFrom1);
					
                            purchaseSearchQuery.append(" AND PO.Order_Date >= '" + tmp_Recieved_From
					+ "'");
                        }
                        if (poDateTo != null && !"".equals(poDateTo)) {
                            
                            StringTokenizer st1 = new StringTokenizer(poDateTo," ");
                            String poDateTo1 = st1.nextToken();
                           // System.out.println("poDateFrom1--->"+poDateTo1);
                            
                            tmp_Recieved_ToTime = DateUtility.getInstance().formatToDB2Date(poDateTo1);
					
                        	purchaseSearchQuery.append(" AND PO.Order_Date <= '" + tmp_Recieved_ToTime
					+ "'");
                        }
                        
                      
                        if (poSenderId != null && !"".equals(poSenderId.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("TP1.ID",
					poSenderId.trim().toUpperCase()));
                        }
                        if (poRecId != null && !"".equals(poRecId.trim())) {
                            purchaseSearchQuery.append(WildCardSql.getWildCardSql1("TP2.ID",
					poRecId.trim().toUpperCase()));
                        }
                        
                        if (poSenderName != null && !"".equals(poSenderName.trim())) {
			purchaseSearchQuery.append(WildCardSql.getWildCardSql1("TP1.NAME",
					poSenderName.trim().toUpperCase()));
                        }
                        if (poRecName != null && !"".equals(poRecName.trim())) {
                            purchaseSearchQuery.append(WildCardSql.getWildCardSql1("TP2.NAME",
					poRecName.trim().toUpperCase()));
                        }
                 purchaseSearchQuery.append("order by DATE_TIME_RECEIVED DESC fetch first 50 rows only");  
                 
                 String searchQuery=purchaseSearchQuery.toString();
                 
                 
             //  System.out.println("Po query --->"+searchQuery);
                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        purchaseList  = new ArrayList<PurchaseOrderBean>();
                        

                        while(resultSet.next()) {
                         
                            PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
                            
                            purchaseOrderBean.setFileId(resultSet.getString("FILE_ID"));
                            purchaseOrderBean.setPo(resultSet.getString("PO_Number"));
                            purchaseOrderBean.setSo(resultSet.getString("SO_Number"));
                            purchaseOrderBean.setSapIdoc(resultSet.getString("SAP_IDOC_Number"));
                            purchaseOrderBean.setPoDate(resultSet.getString("Order_Date"));
                            purchaseOrderBean.setShipDate(resultSet.getString("Ship_Date"));
                            purchaseOrderBean.setPoStatus(resultSet.getString("Order_Status"));
                            purchaseOrderBean.setIsaControl(resultSet.getString("ISA_Control_Number"));
                            purchaseOrderBean.setItemQty(resultSet.getString("Item_Qty"));
                            purchaseOrderBean.setStatus(resultSet.getString("STATUS"));
                             String direction = resultSet.getString("DIRECTION");    
                            purchaseOrderBean.setDirection(direction);
                           if("INBOUND".equalsIgnoreCase(direction)){
                                purchaseOrderBean.setPname(resultSet.getString("SENDER_NAME"));
                            }
                            else
                            {
                                purchaseOrderBean.setPname(resultSet.getString("RECEIVER_NAME"));
                            }
                            purchaseOrderBean.setGsControlNumber(resultSet.getString("GS_CONTROL_NUMBER"));
                            purchaseOrderBean.setReProcessStatus(resultSet.getString("REPROCESSSTATUS"));
                            purchaseOrderBean.setAckStatus(resultSet.getString("ACK_STATUS"));
                            purchaseOrderBean.setDate_time_rec(resultSet.getTimestamp("DATE_TIME_RECEIVED"));
                            purchaseList.add(purchaseOrderBean);
                            
                        }
                        
                 }catch (SQLException e) {
			System.out.println("I am in catch block coming in po serviceIMpl");
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
                // System.out.println("Length--->"+purchaseList.size());
                return purchaseList;
            }

    /**
     * @return the purchaseBean
     */
    public PurchaseOrderBean getPurchaseBean() {
        return purchaseBean;
    }

    /**
     * @param purchaseBean the purchaseBean to set
     */
    public void setPurchaseBean(PurchaseOrderBean purchaseBean) {
        this.purchaseBean = purchaseBean;
    }

}
