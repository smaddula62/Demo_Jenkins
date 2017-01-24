/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.schdular;

/**
 *
 * @author miracle
 */

    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mss.ediscv.util.ConnectionProvider;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.MailManager;
import com.mss.ediscv.util.PasswordUtil;
import com.mss.ediscv.util.ServiceLocatorException;
import com.mss.ediscv.util.WildCardSql;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.security.Timestamp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author miracle
 */
public class SchdularServiceImpl implements SchdularService{
    private HttpServletRequest httpServletRequest;
    private String resultMessage;
     Connection connection = null;
	PreparedStatement preparedStatement = null;
          CallableStatement callableStatement = null;
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
	
        private static Logger logger = Logger.getLogger(SchdularServiceImpl.class.getName());
        String responseString=null;
    private ArrayList<SchdularBean> schdularList;
 
       public ArrayList<SchdularBean> getSchdularList(SchdularAction schdularAction)throws ServiceLocatorException{
         StringBuffer documentSearchQuery = new StringBuffer();
		logger.info("Entered into the :::: ReportsServiceImpl :::: getDocumentList");            
                  String status = schdularAction.getStatus();
                 // String ackStatus = reportsAction.getAckStatus();
                
                  documentSearchQuery.append("SELECT SCH_ID,SCH_TITLE,SCH_TYPE,SCH_TS,SCH_STATUS from SCHEDULER ");
		documentSearchQuery.append("WHERE 1=1 ");

                //Status
                 if (status != null && !"-1".equals(status.trim())) {
			documentSearchQuery.append(WildCardSql.getWildCardSql1("SCH_STATUS",
					status.trim()));
		}   
                                        
                 // documentSearchQuery.append(" order by SCH_RUN_TS DESC fetch first 50 rows only");        
                // documentSearchQuery.append(" WITH UR");
                  System.out.println("DOC queryquery prasad-->"+documentSearchQuery.toString());
                    String searchQuery = documentSearchQuery.toString();

                 
                 try {
                     connection = ConnectionProvider.getInstance().getConnection();

                    
			statement = connection.createStatement();
			
			resultSet=statement.executeQuery(searchQuery);
                        
                        schdularList  = new ArrayList<SchdularBean>();
                        
                        while(resultSet.next()) {
                            SchdularBean schdularBean = new SchdularBean();
                            schdularBean.setId(resultSet.getInt("SCH_ID"));
                            schdularBean.setSchtitle(resultSet.getString("SCH_TITLE"));
                            schdularBean.setSchtype(resultSet.getString("SCH_TYPE"));
                            schdularBean.setSchhrFormat(resultSet.getString("SCH_TS")); 
                            schdularBean.setStatus(resultSet.getString("SCH_STATUS")); 
                            
                            schdularList.add(schdularBean);  
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
                return schdularList;
}
   
  public String SchdularAdd(SchdularAction schdularAction) throws ServiceLocatorException {
         StringBuilder str = new StringBuilder(schdularAction.getSchhours());
          Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
                   String time=schdularAction.getSchhours()+" "+schdularAction.getSchhrFormat();
                   System.out.println("time"+time);
        try {
   
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO SCHEDULER(SCH_TITLE,SCH_TYPE,SCH_STATUS,SCH_TS,RECIVER_IDS,EXTRANAL_EMAILIDS) VALUES (?,?,?,?,?,?)");            
           // preparedStatement.setInt(1, schdularAction.getId());
            preparedStatement.setString(1, schdularAction.getSchtitle());
            preparedStatement.setString(2, schdularAction.getSchType());
            preparedStatement.setString(3,"Active");
           preparedStatement.setString(4,time);
            preparedStatement.setString(5, schdularAction.getUserEmail());
             preparedStatement.setString(6, schdularAction.getExtranalmailids());
          //  preparedStatement.executeUpdate();
             int i=preparedStatement.executeUpdate();
             if(i>0){
                 responseString= "<font color='green'>Schduler added succesfully.</font>";
             }else {
                 responseString= "<font color='red'>Please try again!</font>";
             }
                   if(time != null && !time.equals("")){
                       int count=0;
                 preparedStatement = connection.prepareStatement("select count(*) as total from SCHEDULER where SCH_TYPE=? AND SCH_TS=?");   
                    preparedStatement.setString(1, schdularAction.getSchType());
                    //preparedStatement.setString(2,schdularAction.getStatus());
                     String time1=schdularAction.getSchhours()+" "+schdularAction.getSchhrFormat();
                     System.out.println("time================="+time1);
                    preparedStatement.setString(2,time1);
                    resultSet = preparedStatement.executeQuery();
                            if(resultSet.next()) 
                            {
                              count = resultSet.getInt("total");
                            }
                        System.out.println("time================="+count);     
                if(count>1)
                {
                    responseString= "<font color='green'>SchdulerTime alredy In Running Please try to anthor time.</font>";  
                }
        }     
            
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in TP IMpl"+e.getMessage());
			// TODO Auto-generated catch block
                        responseString = "<font color='red'>Please try with different Id!</font>";
			e.printStackTrace();
		}catch(Exception ex){
                    responseString = "<font color='red'>Please try again!</font>";
			System.out.println("hi"+ex.getMessage());
		}finally{
	           try{
	             
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        
        return responseString;
    }
  
  
  
  public SchdularAction schdularEdit(SchdularAction schdularAction) throws ServiceLocatorException{
       // StringBuffer schdularSearchQuery = new StringBuffer();
                 
        try {
             connection = ConnectionProvider.getInstance().getConnection();
             //partnerSearchQuery.append("select tp.ID as PartnerId,tp.NAME as PartnerName,tp_details.INTERNALIDENTIFIER,tp_details.APPLICATIONID,tp_details.STATE,tp.STATUS,tp.MODIFIED_TS,tp.MODIFIED_BY,tp.CREATED_TS from tp LEFT OUTER JOIN tp_details on(tp_details.TP_ID=tp.ID) WHERE 1=1 AND tp.ID='"+partnerAction.getPartnerIdentifier()+"'");
               preparedStatement = connection.prepareStatement("SELECT SCH_ID, SCH_TITLE,SCH_TYPE,SCH_TS,RECIVER_IDS,EXTRANAL_EMAILIDS from SCHEDULER WHERE SCH_ID=?");
               System.out.println("Query-->"+preparedStatement);
                preparedStatement.setInt(1, schdularAction.getId());
              resultSet = preparedStatement.executeQuery();
             
             if(resultSet.next()){
                    schdularAction.setId(resultSet.getInt("SCH_ID"));
                   schdularAction.setSchtitle(resultSet.getString("SCH_TITLE"));
                   schdularAction.setSchType(resultSet.getString("SCH_TYPE"));
                   String time1=resultSet.getString("SCH_TS");
                  System.out.println("time44444================="+time1);
                  String[] parts = time1.split(" ");
                    String hours = parts[0];    
                    String hoursformate = parts[1];
                  schdularAction.setSchhours(hours);
                  schdularAction.setSchhrFormat(hoursformate);
                String Email=resultSet.getString("RECIVER_IDS"); 
                System.out.println("Email"+Email);
               String[] reciverids = Email.split(",");
          System.out.println("reciverids-----"+reciverids.toString());
           List<String> wordList = Arrays.asList(reciverids);
          //List<String> itemList = new ArrayList<String>();
     // for (String item : reciverids) {
        // itemList.add(item);
     // }
           System.out.println("wordList-------"+wordList);
       Iterator<String> iter = wordList.iterator();
       List<String> copy = new ArrayList<String>();
      while (iter.hasNext())
           copy.add(iter.next().trim());
     // Iterator itr=new Iterator<Object>
      schdularAction.setReceiverids(copy);
      schdularAction.setExtranalmailids(resultSet.getString("EXTRANAL_EMAILIDS"));
      System.out.println("itemlist-------"+resultSet.getString("EXTRANAL_EMAILIDS"));
             }
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in TP IMpl"+e.getMessage());
			// TODO Auto-generated catch block
                        responseString = "<font color='red'>Please try with different Id!</font>";
			e.printStackTrace();
		}catch(Exception ex){
                    responseString = "<font color='red'>Please try again!</font>";
			System.out.println("hi"+ex.getMessage());
		}finally{
	           try{
	             if(resultSet!=null){
	                   resultSet.close();
	                   resultSet = null;
	               }
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        return schdularAction;
    }
  
  
      public String updateSchdular(SchdularAction schdularAction) throws ServiceLocatorException {
        logger.info("Entered into the :::: UserServiceImpl :::: updateUser");
   String time=schdularAction.getSchhours()+" "+schdularAction.getSchhrFormat();    
       Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
         connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE SCHEDULER SET SCH_TITLE=?,SCH_TYPE=?,SCH_TS=?,RECIVER_IDS=?,EXTRANAL_EMAILIDS=? WHERE SCH_ID='"+schdularAction.getId()+"'");
            preparedStatement.setString(1,schdularAction.getSchtitle());
             preparedStatement.setString(2,schdularAction.getSchType());
            preparedStatement.setString(3,time);
             preparedStatement.setString(4,schdularAction.getUserEmail());
              preparedStatement.setString(5,schdularAction.getExtranalmailids());
              //System.out.println(schdularAction.getId());
         int i=preparedStatement.executeUpdate();
             if(i>0){
                 responseString= "<font color='green'>Schdular updated succesfully.</font>";
             }else {
                 responseString= "<font color='red'>Please try again!</font>";
             }
          
            
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in SchdualerServiceIMpl"+e.getMessage());
			// TODO Auto-generated catch block
                        responseString = "<font color='red'>Please try with different Id!</font>";
			e.printStackTrace();
		}catch(Exception ex){
                    responseString = "<font color='red'>Please try later!</font>";
			System.out.println("hi"+ex.getMessage());
		}finally{
	           try{
	             
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        
        
        return responseString;
    }
 
      
     public boolean getAuthdownloadUsercheck(SchdularAction schdularAction) throws ServiceLocatorException {
        boolean isUserExist= false;
         logger.info("Entered into the :::: UserServiceImpl :::: userCheckExist");
            String password=null;
               String username=null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
         PasswordUtil passwordUtility=new PasswordUtil();    
        try{
            connection = ConnectionProvider.getInstance().getConnection();
           // statement = connection.createStatement();
           preparedStatement = connection.prepareStatement("SELECT LOGINID,PASSWD FROM M_USER WHERE LOGINID=?");
            preparedStatement.setString(1, schdularAction.getLoginId());
               resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               
                username=resultSet.getString("LOGINID");
                password = resultSet.getString("PASSWD");
                 String decryptedPwd=passwordUtility.decryptPwd(password);
               if(decryptedPwd.equals(schdularAction.getPassword())){ 
                  System.out.println(decryptedPwd);
                 isUserExist=true;   
               }
            }
      
                    
            System.out.println("password"+password);
            
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in User IMpl"+e.getMessage());
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
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        //System.out.println(isUserExist);
        return isUserExist;
    } 
     
     
      public String SchdularRecordPath(SchdularAction schdularAction) throws ServiceLocatorException {
        logger.info("Entered into the :::: UserServiceImpl :::: updateUser");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
          String reportpath=null;
        try{
            connection = ConnectionProvider.getInstance().getConnection();
          //  statement = connection.createStatement();
           preparedStatement = connection.prepareStatement("SELECT SCH_REPORTPATH from SCH_LOOKUPS where SCH_REF_ID=?");
           //System.out.println("preparedStatement"+preparedStatement);
            preparedStatement.setInt(1,schdularAction.getScheduleRefId());
            System.out.println("ref schid--------------->"+schdularAction.getScheduleRefId());
               resultSet = preparedStatement.executeQuery();
                // System.out.println("resultSet"+resultSet);
            while(resultSet.next()){               
                reportpath=resultSet.getString("SCH_REPORTPATH");
                System.out.println("reportpath"+reportpath);
            }          
                           
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in User IMpl"+e.getMessage());
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
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        System.out.println(reportpath);
        return reportpath;
    }
    
      public String SchdularEmailids(SchdularAction schdularAction) throws ServiceLocatorException {
        logger.info("Entered into the :::: UserServiceImpl :::: updateUser");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
          String Emailids=null;
        try{
            connection = ConnectionProvider.getInstance().getConnection();
          //  statement = connection.createStatement();
           preparedStatement = connection.prepareStatement("SELECT RECIVER_IDS FROM SCHEDULER LEFT OUTER JOIN SCH_LOOKUPS ON (SCHEDULER.SCH_ID = SCH_LOOKUPS.SCH_ID) where SCH_REF_ID=?");
           //System.out.println("preparedStatement"+preparedStatement);
            preparedStatement.setInt(1,schdularAction.getScheduleRefId());
            System.out.println("ref schid--------------->"+schdularAction.getScheduleRefId());
               resultSet = preparedStatement.executeQuery();
                // System.out.println("resultSet"+resultSet);
            while(resultSet.next()){               
                Emailids=resultSet.getString("RECIVER_IDS");
                System.out.println("reportpath"+Emailids);
            }          
                           
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in User IMpl"+e.getMessage());
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
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        System.out.println(Emailids);
        return Emailids;
    }    
     
}

 /*  try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE TP SET NAME=?,MODIFIED_BY=?,MODIFIED_TS=?, STATUS=? WHERE ID=?");
            
           
            preparedStatement.setString(1, partnerAction.getPartnerName());
            preparedStatement.setString(2, partnerAction.getCreatedBy());
            preparedStatement.setTimestamp(3, DateUtility.getInstance().getCurrentDB2Timestamp());
            preparedStatement.setString(4, partnerAction.getStatus());
             preparedStatement.setString(5, partnerAction.getPartnerIdentifier());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = null;
          //  preparedStatement = connection.prepareStatement("INSERT INTO TP_DETAILS(TP_ID,TP_NAME,INTERNALIDENTIFIER,APPLICATIONID,STATE) VALUES(?,?,?,?,?)");
              preparedStatement = connection.prepareStatement("UPDATE TP_DETAILS SET TP_NAME=?,INTERNALIDENTIFIER=?,APPLICATIONID=?,STATE=? WHERE TP_ID =?");
            
            preparedStatement.setString(1,partnerAction.getPartnerName());
            preparedStatement.setString(2,partnerAction.getInternalIdentifier());
            preparedStatement.setString(3,partnerAction.getApplicationId());
            preparedStatement.setString(4,partnerAction.getCountryCode());
            preparedStatement.setString(5,partnerAction.getPartnerIdentifier());
             int i=preparedStatement.executeUpdate();
             if(i>0){
                 responseString= "<font color='green'>Partner updated succesfully.</font>";
             }else {
                 responseString= "<font color='red'>Please try again!</font>";
             }
             
            
        }catch (SQLException e) {
			System.out.println("I am in catch block coming in TP IMpl"+e.getMessage());
			// TODO Auto-generated catch block
                        responseString = "<font color='red'>Please try with different Id!</font>";
			e.printStackTrace();
		}catch(Exception ex){
                    responseString = "<font color='red'>Please try again!</font>";
			System.out.println("hi"+ex.getMessage());
		}finally{
	           try{
	             
	               if(preparedStatement!=null){
	                   preparedStatement.close();
	                   preparedStatement = null;
	               }
	               
	               if(connection != null){
	                   connection.close();
	                   connection = null;
	               }
	           }catch (SQLException se){
	               throw new ServiceLocatorException(se);
	           }
	       }
        
        return responseString;
    } */

    

