/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.reports;
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
public class ReportsAction extends ActionSupport implements ServletRequestAware{

      private static Logger logger = Logger.getLogger(ReportsAction.class.getName());
			
 private HttpServletRequest httpServletRequest;   
 private String resultType;
 private String formAction;
  private List correlationList;
        private List docTypeList;
           private String docdatepicker;
        private String docdatepickerfrom;
        private String docSenderId;
        private String docSenderName;
        private String docReceiverId;
        private String docReceiverName;
        private String docType;
        private String status;
        private String ackStatus;
        // new fields added
           private List senderIdList;
           private List receiverIdList; 
           private List senderNameList;
           private List receiverNameList;
        
         private List<ReportsBean> documentList;
        private Map partnerMap;
   public String getReports() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: addPartner ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                  // setFormAction("doAddPartner");
                    //  List corrList;
                        List docList;
                        List senderList;
                        List receiverList;
                        List senderNameList;
                        List receiverNameList;
                       // corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,1);
                        docList = DataSourceDataProvider.getInstance().getDocumentTypeList();
                        senderList = DataSourceDataProvider.getInstance().getSenderIdlist();
                        receiverList = DataSourceDataProvider.getInstance().getReciverIdlist();
                        senderNameList= DataSourceDataProvider.getInstance().getSenderNamelist();
                        receiverNameList= DataSourceDataProvider.getInstance().getReciverNamelist();
                       // setCorrelationList(corrList);
                        setDocTypeList(docList);
                        setSenderIdList(senderList);
                        setReceiverIdList(receiverList);
                        setSenderNameList(senderNameList);
                        setReceiverNameList(receiverNameList);
                         setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
                        if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_DOCREPORT_LIST);
                        
			resultType = SUCCESS;
                  setResultType(SUCCESS);
               }
 return getResultType();
  }
 
   
   public String reportsSearch() {
          // System.out.println("in tp Action----");
		logger.info("Entered into the ::::ReportsAction :::: reportsSearch ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   try{
       documentList = ServiceLocator.getReportsService().getDocumentList(this);
        if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_DOCREPORT_LIST);
          //  List corrList;
                        List docList;
                        List senderList;
                        List receiverList;
                        List senderNameList;
                        List receiverNameList;
                //        corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,1);
                       docList = DataSourceDataProvider.getInstance().getDocumentTypeList();
                        senderList = DataSourceDataProvider.getInstance().getSenderIdlist();
                        receiverList = DataSourceDataProvider.getInstance().getReciverIdlist();
                        senderNameList= DataSourceDataProvider.getInstance().getSenderNamelist();
                        receiverNameList= DataSourceDataProvider.getInstance().getReciverNamelist(); 
                  //      setCorrelationList(corrList);
                        setDocTypeList(docList);
                        setSenderIdList(senderList);
                        setReceiverIdList(receiverList);
                        setSenderNameList(senderNameList);
                        setReceiverNameList(receiverNameList);
                      if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_DOCREPORT_LIST)!=null)
                            httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_DOCREPORT_LIST);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_DOCREPORT_LIST,documentList);
                        
                        	resultType = SUCCESS;
                  setResultType(SUCCESS);
                   }catch(Exception exception){
                       setResultType(ERROR);
                   }
               }
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
    public String getDocReceiverId() {
        return docReceiverId;
    }

    /**
     * @param docReceiverId the docReceiverId to set
     */
    public void setDocReceiverId(String docReceiverId) {
        this.docReceiverId = docReceiverId;
    }

    /**
     * @return the docReceiverName
     */
    public String getDocReceiverName() {
        return docReceiverName;
    }

    /**
     * @param docReceiverName the docReceiverName to set
     */
    public void setDocReceiverName(String docReceiverName) {
        this.docReceiverName = docReceiverName;
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

    public List getReceiverIdList() {
        return receiverIdList;
    }

    public void setReceiverIdList(List receiverIdList) {
        this.receiverIdList = receiverIdList;
    }

    public List getReceiverNameList() {
        return receiverNameList;
    }

    public void setReceiverNameList(List receiverNameList) {
        this.receiverNameList = receiverNameList;
    }

    public List getSenderIdList() {
        return senderIdList;
    }

    public void setSenderIdList(List senderIdList) {
        this.senderIdList = senderIdList;
    }

    public List getSenderNameList() {
        return senderNameList;
    }

    public void setSenderNameList(List senderNameList) {
        this.senderNameList = senderNameList;
    }
 
 
 
}
