/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.schdular;

import com.mss.ediscv.general.GeneralService;
import com.mss.ediscv.general.UserInfoBean;
import com.mss.ediscv.po.PurchaseOrderBean;
import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.AuthorizationManager;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.MailManager;
import com.mss.ediscv.util.PasswordUtil;
import com.mss.ediscv.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author miracle
 */
public class SchdularAction extends ActionSupport implements
ServletRequestAware,ServletResponseAware {
   
   private HttpServletRequest httpServletRequest; 
	private String sqlQuery;
	private String docSearchQuery;
	private String submitFrm;
	private String resultType;
	private String userEmail;
     private String weekelyReport;
	private String currentDsnName;        
       private Map userMap; 
       private String schtitle;
       private String comments;
       private String status;
       private String schType;
         private String schhours;
          private String schhrFormat;
        private int id;
        private int schid;
       private String currentAction;
       private String userPageId;
       private String ReciverIds;
       private String Userid;
     private String schStartdate;
       private String docdatepickerfrom;
       private String loginId;
       String resultMessage;
        private String schminutes;
	/* password used to store password of the employee */
	private String password;
        private int scheduleRefId;
      // private ArrayList<MasterStatusList> usersList;
       //schtitle,comments
  private String inputPath;
    // private String URL="/images/flower.GIF";
    private String contentDisposition="FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private HttpServletResponse httpServletResponse;
    //private int id;
    private String extranalmailids;
       private String fileName;
       private List receiverids;
       private String reportsType;
    
    
    public String getDocdatepickerfrom() {
        return docdatepickerfrom;
    }

    public void setDocdatepickerfrom(String docdatepickerfrom) {
        this.docdatepickerfrom = docdatepickerfrom;
    }
     
        
         // private List<SchdularBean> documentList;
        
         private List<SchdularBean> schdularList;

	private static Logger logger = Logger.getLogger(SchdularAction.class
			.getName());
	
	
        
        

	
   public String getSchedular() throws Exception {
    
		logger.info("Entered into the ::::PartnerAction :::: addPartner ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   
                  setUserMap(DataSourceDataProvider.getInstance().getUserMap());
                  setSchStartdate(DateUtility.getInstance().getCurrentMySqlDateTime1());
              
                  // setFormAction("doAddPartner");
                        if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_DOCREPORT_LIST);
                        
			resultType = SUCCESS;
                  setResultType(SUCCESS);
               }
 return getResultType();
  }
       
   public String getSchedularsearch() {
          // System.out.println("in tp Action----");
		logger.info("Entered into the ::::ReportsAction :::: reportsSearch ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   try{
       schdularList = ServiceLocator.getSchdularService().getSchdularList(this);
        if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_SCHDULAR_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_SCHDULAR_LIST);
          //  List corrList;
                   setSchStartdate(DateUtility.getInstance().getCurrentMySqlDateTime1());
                      if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_SCHDULAR_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_SCHDULAR_LIST);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_SCHDULAR_LIST,schdularList);
                        
                        	resultType = SUCCESS;
                  setResultType(SUCCESS);
                   }catch(Exception exception){
                       setResultType(ERROR);
                   }
               }
 return getResultType();
   }
   
       public String getSchedularAdd() throws Exception{
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: addPartner ");
		setResultType(LOGIN);
	      if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                     setUserMap(DataSourceDataProvider.getInstance().getUserMap());  
     logger.info("extaranal email ids-->"+getExtranalmailids());
                        setCurrentAction("../partner/doAddSchdular.action");
                         setUserPageId("0");
			resultType = SUCCESS;
                  setResultType(SUCCESS);
               }
 return getResultType();
  }
       
      public String doAddSchdular() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: doAddPartner ");
		setResultType(LOGIN);
	     if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                     setUserMap(DataSourceDataProvider.getInstance().getUserMap());
                  logger.info("partnerName-->"+getSchtitle());
                   logger.info("internalIdentifier-->"+getSchType());
                   
                  // logger.info("partnerIdentifier-->"+getSchTime());
                   logger.info("applicationId-->"+getStatus());
                   logger.info("countryCode-->"+getUserEmail());
                    logger.info("iddddd-->"+getId());
                      logger.info("extaranal email ids-->"+getExtranalmailids());
                   //logger.info("status-->"+getStatus()); 
                   setCurrentAction("doAddSchdular");
                  setUserPageId("0");
               String resultMessage = ServiceLocator.getSchdularService().SchdularAdd(this);
                   getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   
                  resultType = SUCCESS;
               }
 return resultType;
  } 
      
      public String getSchedularEdit() throws Exception {
                 logger.info("Entered into the ::::PartnerAction :::: partnerEdit ");
                 setResultType(LOGIN);
                 
                if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) { 
                      setUserMap(DataSourceDataProvider.getInstance().getUserMap());
                         logger.info("partnerName-->"+getSchtitle());
                   logger.info("internalIdentifier-->"+getSchType());
                      logger.info("iddddd-->"+getId());
                  // logger.info("partnerIdentifier-->"+getSchTime());
                   logger.info("applicationId-->"+getStatus());
                    logger.info("countryCode-->"+getUserEmail());
               logger.info("extaranal email ids-->"+getExtranalmailids());
                   //logger.info("status-->"+getStatus());  
                 ServiceLocator.getSchdularService().schdularEdit(this);
                 setCurrentAction("doUpdateSchduler");
                      setUserPageId("1"); 
                   resultType = SUCCESS;
                  
               }
                 
                  return resultType;
             }     
      
public String doUpdateSchduler() throws Exception {
         setResultType(LOGIN);
		       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setCurrentAction("doUpdateSchduler");
                        //setSearchFields();
                             setUserPageId("1");   
                      logger.info("iddddd-->"+getId());
                       logger.info("extaranal email ids-->"+getExtranalmailids());
                    setUserMap(DataSourceDataProvider.getInstance().getUserMap());
             String resultMessage = ServiceLocator.getSchdularService().updateSchdular(this);
                   getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   
                  resultType = SUCCESS;
               }
 return resultType;
  }     
     


   public String authdownload() throws Exception {
    
                 String filePath = ServiceLocator.getSchdularService().SchdularRecordPath(this);
                 System.out.println(filePath);
                 DownloadReport(filePath);
			//resultType = SUCCESS;
                 // setResultType(SUCCESS);
               
 return getResultType();
  }
  public String authdownloadUser() throws Exception {
       	    setResultType(LOGIN);
         //   HttpSession userSession = httpServletRequest.getSession(true);
              // System.out.println("userName1111111111-->"+getLoginId());
            //System.out.println("password1111111-->"+getPassword()); 
           // boolean isUserExist = false;
         //  boolean  isUserExist = ServiceLocator.getSchdularService().getAuthdownloadUsercheck(this);
               // setCurrentAction("../partner/authdownloadUser.action");
          // if(isUserExist) 
          // {
            //String receiverIds = ServiceLocator.getSchdularService().SchdularEmailids(this);
          String filePath = ServiceLocator.getSchdularService().SchdularRecordPath(this);
        //  int userId = DataSourceDataProvider.getInstance().getUserIds(loginId);
      // if(AuthorizationManager.getInstance().isAuthorizedReceiver(receiverIds, userId)){
           DownloadReport(filePath);
      // }
      // else{
            resultMessage="<font color='green'> Username not authorized.</font>";  
         // getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
       //}//
           
      //  } 
       //    else{
       //      resultMessage="<font color='green'>Invalid Username and Password.</font>";  
       //   getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
       //    }   
         //  System.out.println("resultMessage"+resultMessage);
         setResultType(SUCCESS);
               
 return getResultType();
  

   }
  
    
public void DownloadReport(String filepath){
      System.out.println("reportDownloads method");
     String responseString="";
        try { 
            httpServletResponse.setContentType("application/force-download");
            //System.out.println("response");
         //   System.out.println("resultMessage---"+resultMessage);
        File file = new File(filepath);
            if(file.exists()){
                fileName = file.getName();
                System.out.println("filename"+fileName);
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
                httpServletResponse.setContentType("text");
                httpServletResponse.getWriter().write(responseString);
                
            }else{
                throw new FileNotFoundException("File not found");
            }             
            
        }catch (FileNotFoundException ex) {
            try {
                httpServletResponse.sendRedirect("../general/exception.action?exceptionMessage='No File found'");
            } catch (IOException ex1) {
               // Logger.getLogger(DownloadAction.class.getName()).log(Level.SEVERE, null, ex1);
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
	    
       
     public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }


	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setDocSearchQuery(String docSearchQuery) {
		this.docSearchQuery = docSearchQuery;
	}

	public String getDocSearchQuery() {
		return docSearchQuery;
	}

	public void setSubmitFrm(String submitFrm) {
		this.submitFrm = submitFrm;
	}

	public String getSubmitFrm() {
		return submitFrm;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getResultType() {
		return resultType;
	}

	public void setCurrentDsnName(String currentDsnName) {
		this.currentDsnName = currentDsnName;
	}

	public String getCurrentDsnName() {
		return currentDsnName;
	}

    /**
     * @return the docdatepicker
     */


    public String getWeekelyReport() {
        return weekelyReport;
    }

    public void setWeekelyReport(String weekelyReport) {
        this.weekelyReport = weekelyReport;
    }

    public Map getUserMap() {
        return userMap;
    }

    public void setUserMap(Map userMap) {
        this.userMap = userMap;
    }



    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSchtitle() {
        return schtitle;
    }

    public void setSchtitle(String schtitle) {
        this.schtitle = schtitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchType() {
        return schType;
    }

    public void setSchType(String schType) {
        this.schType = schType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public String getUserPageId() {
        return userPageId;
    }

    public void setUserPageId(String userPageId) {
        this.userPageId = userPageId;
    }

    public String getReciverIds() {
        return ReciverIds;
    }

    public void setReciverIds(String ReciverIds) {
        this.ReciverIds = ReciverIds;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String Userid) {
        this.Userid = Userid;
    }

    public String getSchhours() {
        return schhours;
    }

    public void setSchhours(String schhours) {
        this.schhours = schhours;
    }

    public String getSchhrFormat() {
        return schhrFormat;
    }

    public void setSchhrFormat(String schhrFormat) {
        this.schhrFormat = schhrFormat;
    }

   
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSchStartdate() {
        return schStartdate;
    }

    public void setSchStartdate(String schStartdate) {
        this.schStartdate = schStartdate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScheduleRefId() {
        return scheduleRefId;
    }

    public void setScheduleRefId(int scheduleRefId) {
        this.scheduleRefId = scheduleRefId;
    }

    public int getSchid() {
        return schid;
    }

    public void setSchid(int schid) {
        this.schid = schid;
    }

    public List getReceiverids() {
        return receiverids;
    }

    public void setReceiverids(List receiverids) {
        this.receiverids = receiverids;
    }

    public String getExtranalmailids() {
        return extranalmailids;
    }

    public void setExtranalmailids(String extranalmailids) {
        this.extranalmailids = extranalmailids;
    }

    public String getReportsType() {
        return reportsType;
    }

    public void setReportsType(String reportsType) {
        this.reportsType = reportsType;
    }

    public String getSchminutes() {
        return schminutes;
    }

    public void setSchminutes(String schminutes) {
        this.schminutes = schminutes;
    }

   
  
public void setServletResponse(HttpServletResponse httpServletResponse) {
           
    this.httpServletResponse=httpServletResponse;
    }

   

  
    

    /**
     * @return the docSenderId
     */


}
