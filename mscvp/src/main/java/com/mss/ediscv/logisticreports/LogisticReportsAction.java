/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticreports;
import com.mss.ediscv.reports.*;
import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.log4j.Logger;
/**
 *
 * @author miracle
 */
public class LogisticReportsAction extends ActionSupport implements ServletRequestAware{

      private static Logger logger = Logger.getLogger(LogisticReportsAction.class.getName());
			
 private HttpServletRequest httpServletRequest;   
 private String resultType;
 private String formAction;
  private List correlationList;
        private List docTypeList;
           private String docdatepicker;
        private String docdatepickerfrom;
        private String docSenderId;
        private String docSenderName;
        private String docType;
        private String status;
        private String ackStatus;
        private String docBusId;
        private String docRecName;
        private String check;
        // new fields added
       
        
         private List<LogisticReportsBean> documentList;
        private Map partnerMap;
   public String getLogisticReports() throws Exception {
            
           logger.info("Entered into the ::::SearchDocRepositorAction :::: prepare ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
         String defaultFlowId = httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString();
        String defaultFlowName = DataSourceDataProvider.getInstance().getFlowNameByFlowID(defaultFlowId);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOG_DOC_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOGSHIPMENT_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOAD_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTRESPONSE_LIST);
         
        
                        List corrList;
                        List docList;
        
                            corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,2);
                  
                            docList = DataSourceDataProvider.getInstance().getDocumentTypeList(0,2);
                      setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
                        setCorrelationList(corrList);
                        setDocTypeList(docList);
    if (!defaultFlowName.equals("Logistics")){
        defaultFlowId = DataSourceDataProvider.getInstance().getFlowIdByFlowName("Logistics");
        httpServletRequest.getSession(false).setAttribute(AppConstants.SES_USER_DEFAULT_FLOWID, defaultFlowId);
    }
            setResultType(SUCCESS);
}
                 logger.info("End of ::::SearchDOCUMENTSAction :::: prepare ");
		return getResultType();
   }
   //documentList = ServiceLocator.getLogisticReportsService().getDocumentList(this);
 
   
   public String logisticreportsSearch() {
          // System.out.println("in tp Action----");
			setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		try {
			if (logger.isDebugEnabled()) {
				logger
						.info("Entered into the ::::SearchpuchaseAction :::: getPurchaseSearchQuery ");
			}
			execute();
                               
                                    if(getCheck()==null)
                                    {
                                       // System.out.println("in if"+getCheck());
                                        setCheck("1");
                                    }
                                    else if(getCheck().equals(""))
                                        {
                                       // System.out.println("in else"+getCheck());
                                        setCheck("1");
                                        }
                                   
                                 //System.out.println("setCheck-->"+getCheck());


                             List corrList;
                        List docList;
        
                            corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,2);
                  
                            docList = DataSourceDataProvider.getInstance().getDocumentTypeList(0,2);
                      //setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
                        setCorrelationList(corrList);
                        setDocTypeList(docList);
			documentList = ServiceLocator.getLogisticReportsService().getDocumentList(this);
                       System.out.println("the logis  list is --->"+documentList);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_LOG_DOC_LIST,documentList);
                               
                        
                      //  System.out.println("value"+getSampleValue());
                        
			setResultType(SUCCESS);

		}  catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger
						.error("problem :: "
								+ ex.getMessage()
								+ " method name :: getPOSearchQuery() :: class name :: "
								+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					AppConstants.REQ_EXCEPTION_MSG, ex.getMessage());
			setResultType("error");
		}
	       }
		logger.info("End of ::::SearchPOAction :::: getPOSearchQuery ");
		return getResultType();
   } 
 
  public String getDashboard() {
          // System.out.println("in tp Action----");
		logger.info("Entered into the ::::ReportsAction :::: getDashboard ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   try{
                       List docList;
                       // corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,1);
                        docList = DataSourceDataProvider.getInstance().getDocumentTypeList();
                        
                       // setCorrelationList(corrList);
                        setDocTypeList(docList);
                   setPartnerMap(DataSourceDataProvider.getInstance().getDashboardPartnerMap());
                    setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
                        	resultType = SUCCESS;
                  setResultType(SUCCESS);
                   }catch(Exception exception){
                       setResultType(ERROR);
                   }
               }
 return getResultType();
   }
 
    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * @return the resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * @param resultType the resultType to set
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return the formAction
     */
    public String getFormAction() {
        return formAction;
    }

    /**
     * @param formAction the formAction to set
     */
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }

    /**
     * @return the correlationList
     */
    public List getCorrelationList() {
        return correlationList;
    }

    /**
     * @param correlationList the correlationList to set
     */
    public void setCorrelationList(List correlationList) {
        this.correlationList = correlationList;
    }

    /**
     * @return the docTypeList
     */
    public List getDocTypeList() {
        return docTypeList;
    }

    /**
     * @param docTypeList the docTypeList to set
     */
    public void setDocTypeList(List docTypeList) {
        this.docTypeList = docTypeList;
    }

    /**
     * @return the docSenderId
     */
    public String getDocSenderId() {
        return docSenderId;
    }

    /**
     * @param docSenderId the docSenderId to set
     */
    public void setDocSenderId(String docSenderId) {
        this.docSenderId = docSenderId;
    }

    /**
     * @return the docSenderName
     */
    public String getDocSenderName() {
        return docSenderName;
    }

    /**
     * @param docSenderName the docSenderName to set
     */
    public void setDocSenderName(String docSenderName) {
        this.docSenderName = docSenderName;
    }

    /**
     * @return the docReceiverId
     */


    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the ackStatus
     */
    public String getAckStatus() {
        return ackStatus;
    }

    /**
     * @param ackStatus the ackStatus to set
     */
    public void setAckStatus(String ackStatus) {
        this.ackStatus = ackStatus;
    }

    /**
     * @return the partnerMap
     */
    public Map getPartnerMap() {
        return partnerMap;
    }

    /**
     * @param partnerMap the partnerMap to set
     */
    public void setPartnerMap(Map partnerMap) {
        this.partnerMap = partnerMap;
    }

    public String getDocdatepicker() {
        return docdatepicker;
    }

    public void setDocdatepicker(String docdatepicker) {
        this.docdatepicker = docdatepicker;
    }

    public String getDocdatepickerfrom() {
        return docdatepickerfrom;
    }

    public void setDocdatepickerfrom(String docdatepickerfrom) {
        this.docdatepickerfrom = docdatepickerfrom;
    }

    public String getDocBusId() {
        return docBusId;
    }

    public void setDocBusId(String docBusId) {
        this.docBusId = docBusId;
    }

    public String getDocRecName() {
        return docRecName;
    }

    public void setDocRecName(String docRecName) {
        this.docRecName = docRecName;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }


 
 
 
}
