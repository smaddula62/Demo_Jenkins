/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.doc;

import com.mss.ediscv.po.PurchaseOrderBean;
import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;


/**
 *
 * @author miracle
 */
public class DocRepositoryAction extends ActionSupport implements
ServletRequestAware {
    
    
    
    private HttpServletRequest httpServletRequest;
	private String sqlQuery;
	private String docSearchQuery;
	private String submitFrm;
	private String resultType;
	

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
        private List correlationList;
        private List docTypeList;
        private String corrattribute;
        private String corrvalue;     
        private String corrattribute1;
        private String corrvalue1;
        private String corrattribute2;
        private String corrvalue2;        
        private String status;
        private String ackStatus;
   //new fields added     
           private List senderIdList;
           private List receiverIdList; 
           private List senderNameList;
           private List receiverNameList;
          private List<DocRepositoryBean> documentList;
        
        

	private static Logger logger = Logger.getLogger(DocRepositoryAction.class
			.getName());
	
	
	
	public String prepare() throws Exception {
            
            //System.out.println("in DocRepositor Action----");
		logger.info("Entered into the ::::SearchDocRepositoryAction :::: prepare ");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		try {
                    
                    httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_INV_LIST);
                    httpSession.removeAttribute(AppConstants.SES_PO_LIST);
                
			if (getSubmitFrm() != null && (!getSubmitFrm().equals("frmDBGrid"))) {
				if (httpSession.getAttribute(AppConstants.SES_DOCUMENTS_QUERY) != null) {
					httpSession.removeAttribute(AppConstants.SES_DOCUMENTS_QUERY);
				}
			} else if (getSubmitFrm() == null && httpSession.getAttribute(AppConstants.SES_DOCUMENTS_QUERY) != null) {
				httpSession.removeAttribute(AppConstants.SES_DOCUMENTS_QUERY);
			}
                        List corrList;
                        List docList;
                        List senderList;
                        List receiverList;
                        List senderNameList;
                        List receiverNameList;
                        corrList = DataSourceDataProvider.getInstance().getCorrelationNames(0,1);
                        docList = DataSourceDataProvider.getInstance().getDocumentTypeList();
                        senderList = DataSourceDataProvider.getInstance().getSenderIdlist();
                        receiverList = DataSourceDataProvider.getInstance().getReciverIdlist();
                        senderNameList= DataSourceDataProvider.getInstance().getSenderNamelist();
                        receiverNameList= DataSourceDataProvider.getInstance().getReciverNamelist();
                        setCorrelationList(corrList);
                        setDocTypeList(docList);
                        setSenderIdList(senderList);
                        setReceiverIdList(receiverList);
                        setSenderNameList(senderNameList);
                        setReceiverNameList(receiverNameList);
                        setDocdatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
			resultType = SUCCESS;
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.error("problem :: " + ex.getMessage()
						+ " method name :: prepare() :: class name :: "
						+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					AppConstants.REQ_EXCEPTION_MSG, ex.getMessage());
			resultType = "error";
		}
	       }
		logger.info("End of ::::SearchDOCUMENTSAction :::: prepare ");
		return resultType;
	}
        
        
        public String getDocumentSearchQuery() throws Exception {
           // System.out.println("getQuery---");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		try {
			if (logger.isDebugEnabled()) {
				logger
						.info("Entered into the ::::SearchpuchaseAction :::: getPurchaseSearchQuery ");
			}
			prepare();
                               
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



			documentList = ServiceLocator.getDocumentService().buildDocumentQuery(this);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_DOC_LIST,documentList);
                               
                        
                      //  System.out.println("value"+getSampleValue());
                        
			resultType = SUCCESS;

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
			resultType = "error";
		}
	       }
		logger.info("End of ::::SearchPOAction :::: getPOSearchQuery ");
		return resultType;
	}

        
        
        public void setServletRequest(HttpServletRequest reqObj) {
		this.setHttpServletRequest(reqObj);
	}

	/**
	 * @param reqObj
	 */
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
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
    public boolean getCk850() {
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
    public boolean getCk855() {
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
    public boolean getCk860() {
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
    public boolean getCk856() {
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
    public boolean getCk810() {
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
    public boolean getCk820() {
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
   //new files added
    

    public List getSenderIdList() {
        return senderIdList;
    }

    public void setSenderIdList(List senderIdList) {
        this.senderIdList = senderIdList;
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

    public List getSenderNameList() {
        return senderNameList;
    }

    public void setSenderNameList(List senderNameList) {
        this.senderNameList = senderNameList;
    }
    

}
