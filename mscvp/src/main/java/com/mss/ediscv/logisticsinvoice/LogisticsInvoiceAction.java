/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsinvoice;

import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.AuthorizationManager;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author miracle
 */
public class LogisticsInvoiceAction extends ActionSupport implements ServletRequestAware{
    
     private HttpServletRequest httpServletRequest;
        private String resultType;
        private String sqlQuery;
        private String docSearchQuery;
        private String submitFrm;
        private String currentDsnName;
        private String datepickerfrom;
        private String datepickerTo;
        private String invSenderId;
        private String invSenderName;
        private String invReceiverId;
        private String invReceiverName;
      
        private String sampleValue;
        private String docType;
      
        private String check;
        private String corrattribute;
        private String corrvalue;
         private String corrattribute1;
        private String corrvalue1;
        
        private String status;
        private String ackStatus;
        private List correlationList;
        private Map docTypeMap;
        private List docTypeList;
        private List<LogisticsInvoiceBean> ltInvoiceList;
         private static Logger logger = Logger.getLogger(LogisticsInvoiceAction.class
			.getName());
    
    public String execute() throws Exception {
        logger.info("Entered into the ::::LogisticsInvoiceAction :::: prepare ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("L_INVOICE",userRoleId)){  
         String defaultFlowId = httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString();
        String defaultFlowName = DataSourceDataProvider.getInstance().getFlowNameByFlowID(defaultFlowId);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTRESPONSE_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOG_DOC_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOGSHIPMENT_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOAD_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTINVOICE_LIST);
         List corrList;
                        List docList;
        
                            corrList = DataSourceDataProvider.getInstance().getCorrelationNames(4,2);
                  
                            docList = DataSourceDataProvider.getInstance().getDocumentTypeList(4,2);
                      setDatepickerTo(DateUtility.getInstance().getCurrentMySqlDateTime1());
                        setCorrelationList(corrList);
                        setDocTypeList(docList);
    if (!defaultFlowName.equals("Logistics")){
        defaultFlowId = DataSourceDataProvider.getInstance().getFlowIdByFlowName("Logistics");
        httpServletRequest.getSession(false).setAttribute(AppConstants.SES_USER_DEFAULT_FLOWID, defaultFlowId);
    }
            setResultType(SUCCESS);
                 }
}
            
    logger.info("End of ::::LogisticsInvoiceAction :::: prepare ");
		return getResultType();
    }
    
    
    public String getInvoiceSearchQuery() throws Exception {
           // System.out.println("getQuery---");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("L_INVOICE",userRoleId)){  
		try {
			if (logger.isDebugEnabled()) {
				logger
						.info("Entered into the ::::SearchLogisticsInvoiceAction :::: getInvoiceSearchQuery ");
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



			ltInvoiceList = ServiceLocator.getLogInvoiceService().buildLogInvoiceQuery(this);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_LTINVOICE_LIST,ltInvoiceList);
                               
                        
                      //  System.out.println("value"+getSampleValue());
                        
			setResultType(SUCCESS);

		}  catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger
						.error("problem :: "
								+ ex.getMessage()
								+ " method name :: getInvoiceSearchQuery() :: class name :: "
								+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					AppConstants.REQ_EXCEPTION_MSG, ex.getMessage());
			setResultType("error");
		}
	       }
               }
		logger.info("End of ::::SearchLogisticsInvoiceAction :::: getInvoiceSearchQuery ");
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
     * @return the sqlQuery
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * @param sqlQuery the sqlQuery to set
     */
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    /**
     * @return the docSearchQuery
     */
    public String getDocSearchQuery() {
        return docSearchQuery;
    }

    /**
     * @param docSearchQuery the docSearchQuery to set
     */
    public void setDocSearchQuery(String docSearchQuery) {
        this.docSearchQuery = docSearchQuery;
    }

    /**
     * @return the submitFrm
     */
    public String getSubmitFrm() {
        return submitFrm;
    }

    /**
     * @param submitFrm the submitFrm to set
     */
    public void setSubmitFrm(String submitFrm) {
        this.submitFrm = submitFrm;
    }

    /**
     * @return the currentDsnName
     */
    public String getCurrentDsnName() {
        return currentDsnName;
    }

    /**
     * @param currentDsnName the currentDsnName to set
     */
    public void setCurrentDsnName(String currentDsnName) {
        this.currentDsnName = currentDsnName;
    }

   
    /**
     * @return the sampleValue
     */
    public String getSampleValue() {
        return sampleValue;
    }

    /**
     * @param sampleValue the sampleValue to set
     */
    public void setSampleValue(String sampleValue) {
        this.sampleValue = sampleValue;
    }

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
     * @return the check
     */
    public String getCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(String check) {
        this.check = check;
    }

    /**
     * @return the corrattribute
     */
    public String getCorrattribute() {
        return corrattribute;
    }

    /**
     * @param corrattribute the corrattribute to set
     */
    public void setCorrattribute(String corrattribute) {
        this.corrattribute = corrattribute;
    }

    /**
     * @return the corrvalue
     */
    public String getCorrvalue() {
        return corrvalue;
    }

    /**
     * @param corrvalue the corrvalue to set
     */
    public void setCorrvalue(String corrvalue) {
        this.corrvalue = corrvalue;
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
     * @return the docTypeMap
     */
    public Map getDocTypeMap() {
        return docTypeMap;
    }

    /**
     * @param docTypeMap the docTypeMap to set
     */
    public void setDocTypeMap(Map docTypeMap) {
        this.docTypeMap = docTypeMap;
    }



    /**
     * @return the invSenderId
     */
    public String getInvSenderId() {
        return invSenderId;
    }

    /**
     * @param invSenderId the invSenderId to set
     */
    public void setInvSenderId(String invSenderId) {
        this.invSenderId = invSenderId;
    }

    /**
     * @return the invSenderName
     */
    public String getInvSenderName() {
        return invSenderName;
    }

    /**
     * @param invSenderName the invSenderName to set
     */
    public void setInvSenderName(String invSenderName) {
        this.invSenderName = invSenderName;
    }

    /**
     * @return the invReceiverId
     */
    public String getInvReceiverId() {
        return invReceiverId;
    }

    /**
     * @param invReceiverId the invReceiverId to set
     */
    public void setInvReceiverId(String invReceiverId) {
        this.invReceiverId = invReceiverId;
    }

    /**
     * @return the invReceiverName
     */
    public String getInvReceiverName() {
        return invReceiverName;
    }

    /**
     * @param invReceiverName the invReceiverName to set
     */
    public void setInvReceiverName(String invReceiverName) {
        this.invReceiverName = invReceiverName;
    }

    /**
     * @return the corrattribute1
     */
    public String getCorrattribute1() {
        return corrattribute1;
    }

    /**
     * @param corrattribute1 the corrattribute1 to set
     */
    public void setCorrattribute1(String corrattribute1) {
        this.corrattribute1 = corrattribute1;
    }

    /**
     * @return the corrvalue1
     */
    public String getCorrvalue1() {
        return corrvalue1;
    }

    /**
     * @param corrvalue1 the corrvalue1 to set
     */
    public void setCorrvalue1(String corrvalue1) {
        this.corrvalue1 = corrvalue1;
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
     * @return the datepickerfrom
     */
    public String getDatepickerfrom() {
        return datepickerfrom;
    }

    /**
     * @param datepickerfrom the datepickerfrom to set
     */
    public void setDatepickerfrom(String datepickerfrom) {
        this.datepickerfrom = datepickerfrom;
    }

    /**
     * @return the datepickerTo
     */
    public String getDatepickerTo() {
        return datepickerTo;
    }

    /**
     * @param datepickerTo the datepickerTo to set
     */
    public void setDatepickerTo(String datepickerTo) {
        this.datepickerTo = datepickerTo;
    }

}
