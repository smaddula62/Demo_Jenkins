/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsshipment;

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

public class LogisticsShipmentAction  extends ActionSupport implements ServletRequestAware{
    private HttpServletRequest httpServletRequest;
    
        private String resultType;
        private String sqlQuery;
        private String docSearchQuery;
        private String submitFrm;
        private String currentDsnName;
        private String status;
        private String senderId;
        private String senderName;
        private String receiverId;
        private String receiverName;
         private String datepickerfrom;
         private String datepickerTo;
        private String ackStatus;
        private List correlationList;
        private String docType;
        private List docTypeList;
         private String corrattribute;
        private String corrvalue;
        private String corrattribute1;
        private String corrvalue1;
        
         private static Logger logger = Logger.getLogger(LogisticsShipmentAction.class
			.getName());

         private List<LtShipmentBean> ltShipmentList;
         public String execute() throws Exception {
        logger.info("Entered into the ::::LogisticsShipmentAction :::: execute ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                    setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("L_SHIPMENT",userRoleId)){  
         String defaultFlowId = httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString();
        String defaultFlowName = DataSourceDataProvider.getInstance().getFlowNameByFlowID(defaultFlowId);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTRESPONSE_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOG_DOC_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOGSHIPMENT_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOAD_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTSHIPMENT_LIST);
         
         List corrList;
                        List docList;
        
                            corrList = DataSourceDataProvider.getInstance().getCorrelationNames(3,2);
                  
                            docList = DataSourceDataProvider.getInstance().getDocumentTypeList(3,2);
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
               logger.info("End of ::::LogisticsShipmentAction :::: execute ");
		return getResultType();
    }
         
         
     public String getShipmentSearchList() throws Exception {
           // System.out.println("getQuery---");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("L_SHIPMENT",userRoleId)){  
		try {
			if (logger.isDebugEnabled()) {
				logger
						.info("Entered into the ::::LogisticsShipmentAction :::: getShipmentSearchList ");
			}
			execute();
                               
                      // System.out.println("hii");
			ltShipmentList = ServiceLocator.getLogShipmentService().getLtResponseList(this);
                       
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_LTSHIPMENT_LIST,ltShipmentList);
                      
			setResultType(SUCCESS);

		}  catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger
						.error("problem :: "
								+ ex.getMessage()
								+ " method name :: getShipmentSearchList() :: class name :: "
								+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					"exceptionMessage", ex.getMessage());
			setResultType(ERROR);
		}
	       }
               }
		logger.info("End of ::::LogisticsShipmentAction :::: getShipmentSearchList ");
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
     * @return the senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId the receiverId to set
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
}
