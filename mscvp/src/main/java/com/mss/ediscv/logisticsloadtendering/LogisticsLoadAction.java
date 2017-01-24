/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsloadtendering;

import com.mss.ediscv.logistics.LogisticsAction;
//import com.mss.ediscv.logistics.LogisticsBean;
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
public class LogisticsLoadAction extends ActionSupport implements ServletRequestAware{
    
     private HttpServletRequest httpServletRequest;
        private String resultType;
        private String sqlQuery;
        private String docSearchQuery;
        private String submitFrm;
        private String currentDsnName;
        private String docdatepicker;
        private String docdatepickerfrom;
        private String docSenderId;
        private String docSenderName;
        private String docBusId;
        private String docRecName;
        private String docIsa;
        private String docPoNum;
        private boolean ck850;
        private boolean ck855;
        private boolean ck860;
        private boolean ck856;
        private boolean ck810;
        private boolean ck820;
        private String sampleValue;
        private String docType;
        private String ttype;
        private String tnumber;
        private String ponumber;
        private String asnnumber;
        private String invnumber;
        private String bolNum;
        private String ChequeNum;
        private String check;
        private String corrattribute;
        private String corrvalue;
        private String corrattribute1;
        private String corrvalue1;
        private String corrattribute2;
        private String corrvalue2;
        private String status;
        private String ackStatus;
        private List correlationList;
        private List docTypeList;
        private List<LogisticsLoadBean> loadList;
         private static Logger logger = Logger.getLogger(LogisticsAction.class
			.getName());
    
    public String execute() throws Exception {
        logger.info("Entered into the ::::SearchDocRepositorAction :::: prepare ");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("LOADTENDER",userRoleId)){  
         String defaultFlowId = httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString();
        String defaultFlowName = DataSourceDataProvider.getInstance().getFlowNameByFlowID(defaultFlowId);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOG_DOC_LIST);
        httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOGSHIPMENT_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LOAD_LIST);
         httpServletRequest.getSession(false).removeAttribute(AppConstants.SES_LTRESPONSE_LIST);
          setCorrelationList(DataSourceDataProvider.getInstance().getCorrelationNames(1,2));
          setDocTypeList(DataSourceDataProvider.getInstance().getDocumentTypeList(1,2));
          setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
    if (!defaultFlowName.equals("Logistics")){
        defaultFlowId = DataSourceDataProvider.getInstance().getFlowIdByFlowName("Logistics");
        httpServletRequest.getSession(false).setAttribute(AppConstants.SES_USER_DEFAULT_FLOWID, defaultFlowId);
    }
            setResultType(SUCCESS);
}
               }
               logger.info("End of ::::SearchDOCUMENTSAction :::: prepare ");
		return getResultType();
    }
    
    
    public String getloadSearchQuery() throws Exception {
           // System.out.println("getQuery---");
		setResultType(LOGIN);
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setResultType("accessFailed");
                   int userRoleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_ROLE_ID).toString());
                 if(AuthorizationManager.getInstance().isAuthorizedUser("LOADTENDER",userRoleId)){  
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



			loadList = ServiceLocator.getLoadService().buildLoadQuery(this);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_LOAD_LIST,loadList);
                               
                        
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
               }
		logger.info("End of ::::SearchPOAction :::: getPOSearchQuery ");
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
     * @return the docdatepicker
     */
    public String getDocdatepicker() {
        return docdatepicker;
    }

    /**
     * @param docdatepicker the docdatepicker to set
     */
    public void setDocdatepicker(String docdatepicker) {
        this.docdatepicker = docdatepicker;
    }

    /**
     * @return the docdatepickerfrom
     */
    public String getDocdatepickerfrom() {
        return docdatepickerfrom;
    }

    /**
     * @param docdatepickerfrom the docdatepickerfrom to set
     */
    public void setDocdatepickerfrom(String docdatepickerfrom) {
        this.docdatepickerfrom = docdatepickerfrom;
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
     * @return the docBusId
     */
    public String getDocBusId() {
        return docBusId;
    }

    /**
     * @param docBusId the docBusId to set
     */
    public void setDocBusId(String docBusId) {
        this.docBusId = docBusId;
    }

    /**
     * @return the docRecName
     */
    public String getDocRecName() {
        return docRecName;
    }

    /**
     * @param docRecName the docRecName to set
     */
    public void setDocRecName(String docRecName) {
        this.docRecName = docRecName;
    }

    /**
     * @return the docIsa
     */
    public String getDocIsa() {
        return docIsa;
    }

    /**
     * @param docIsa the docIsa to set
     */
    public void setDocIsa(String docIsa) {
        this.docIsa = docIsa;
    }

    /**
     * @return the docPoNum
     */
    public String getDocPoNum() {
        return docPoNum;
    }

    /**
     * @param docPoNum the docPoNum to set
     */
    public void setDocPoNum(String docPoNum) {
        this.docPoNum = docPoNum;
    }

    /**
     * @return the ck850
     */
    public boolean isCk850() {
        return ck850;
    }

    /**
     * @param ck850 the ck850 to set
     */
    public void setCk850(boolean ck850) {
        this.ck850 = ck850;
    }

    /**
     * @return the ck855
     */
    public boolean isCk855() {
        return ck855;
    }

    /**
     * @param ck855 the ck855 to set
     */
    public void setCk855(boolean ck855) {
        this.ck855 = ck855;
    }

    /**
     * @return the ck860
     */
    public boolean isCk860() {
        return ck860;
    }

    /**
     * @param ck860 the ck860 to set
     */
    public void setCk860(boolean ck860) {
        this.ck860 = ck860;
    }

    /**
     * @return the ck856
     */
    public boolean isCk856() {
        return ck856;
    }

    /**
     * @param ck856 the ck856 to set
     */
    public void setCk856(boolean ck856) {
        this.ck856 = ck856;
    }

    /**
     * @return the ck810
     */
    public boolean isCk810() {
        return ck810;
    }

    /**
     * @param ck810 the ck810 to set
     */
    public void setCk810(boolean ck810) {
        this.ck810 = ck810;
    }

    /**
     * @return the ck820
     */
    public boolean isCk820() {
        return ck820;
    }

    /**
     * @param ck820 the ck820 to set
     */
    public void setCk820(boolean ck820) {
        this.ck820 = ck820;
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
     * @return the ttype
     */
    public String getTtype() {
        return ttype;
    }

    /**
     * @param ttype the ttype to set
     */
    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    /**
     * @return the tnumber
     */
    public String getTnumber() {
        return tnumber;
    }

    /**
     * @param tnumber the tnumber to set
     */
    public void setTnumber(String tnumber) {
        this.tnumber = tnumber;
    }

    /**
     * @return the ponumber
     */
    public String getPonumber() {
        return ponumber;
    }

    /**
     * @param ponumber the ponumber to set
     */
    public void setPonumber(String ponumber) {
        this.ponumber = ponumber;
    }

    /**
     * @return the asnnumber
     */
    public String getAsnnumber() {
        return asnnumber;
    }

    /**
     * @param asnnumber the asnnumber to set
     */
    public void setAsnnumber(String asnnumber) {
        this.asnnumber = asnnumber;
    }

    /**
     * @return the invnumber
     */
    public String getInvnumber() {
        return invnumber;
    }

    /**
     * @param invnumber the invnumber to set
     */
    public void setInvnumber(String invnumber) {
        this.invnumber = invnumber;
    }

    /**
     * @return the bolNum
     */
    public String getBolNum() {
        return bolNum;
    }

    /**
     * @param bolNum the bolNum to set
     */
    public void setBolNum(String bolNum) {
        this.bolNum = bolNum;
    }

    /**
     * @return the ChequeNum
     */
    public String getChequeNum() {
        return ChequeNum;
    }

    /**
     * @param ChequeNum the ChequeNum to set
     */
    public void setChequeNum(String ChequeNum) {
        this.ChequeNum = ChequeNum;
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
     * @return the corrattribute2
     */
    public String getCorrattribute2() {
        return corrattribute2;
    }

    /**
     * @param corrattribute2 the corrattribute2 to set
     */
    public void setCorrattribute2(String corrattribute2) {
        this.corrattribute2 = corrattribute2;
    }

    /**
     * @return the corrvalue2
     */
    public String getCorrvalue2() {
        return corrvalue2;
    }

    /**
     * @param corrvalue2 the corrvalue2 to set
     */
    public void setCorrvalue2(String corrvalue2) {
        this.corrvalue2 = corrvalue2;
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

}
