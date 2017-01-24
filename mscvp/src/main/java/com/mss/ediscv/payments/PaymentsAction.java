/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.payments;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocator;
import com.mss.ediscv.util.ServiceLocatorException;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
/**
 *
 * @author miracle
 */
public class PaymentsAction extends ActionSupport implements ServletRequestAware {
        
        private HttpServletRequest httpServletRequest;
	private String sqlQuery;
	private String paSearchQuery;
	private String submitFrm;
	private String resultType;
	
        /*
         * Search Fields
         */
        private String paDateTo;
        private String paDateFrom;
        private String paSenderId;
        private String paSenderName;
        private String paRecId;
        private String paRecName;
        private String paChequeNo;
        private String paChequeAmt;
        private String sampleValue;
        private String check;
        
        private String ackStatus;
        private String status;
        
        private List<PaymentBean> paymentList;
        
	private String currentDsnName;
        
        private String corrattribute;
        private String corrvalue;
        private List correlationList;
        private List docTypeList;
         private String corrattribute1;
        private String corrvalue1;
        
        private String docType;
      // new fields added
         private List senderIdList;
           private List receiverIdList; 
           private List senderNameList;
           private List receiverNameList;
        
	private static Logger logger = Logger.getLogger(PaymentsAction.class
			.getName());
	
	
	
	public String prepare() throws Exception {
            
           // System.out.println("in PaymentsAction Action----");
		logger.info("Entered into the ::::PaymentsAction :::: prepare ");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		try {
                    
                   // httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_DOC_LIST);
                    httpSession.removeAttribute(AppConstants.SES_INV_LIST);
                    httpSession.removeAttribute(AppConstants.SES_PO_LIST);
			if (getSubmitFrm() != null && (!getSubmitFrm().equals("frmDBGrid"))) {
				if (httpSession.getAttribute(AppConstants.SES_PAYMENT_LIST) != null) {
					httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
				}
			} else if (getSubmitFrm() == null && httpSession.getAttribute(AppConstants.SES_PAYMENT_LIST) != null) {
				httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
			}
                     setCorrelationList(DataSourceDataProvider.getInstance().getCorrelationNames(4,1));
                     setDocTypeList(DataSourceDataProvider.getInstance().getDocumentTypeList());
                     setSenderIdList(DataSourceDataProvider.getInstance().getSenderIdlist());
                     setReceiverIdList(DataSourceDataProvider.getInstance().getReciverIdlist());
                     setSenderNameList(DataSourceDataProvider.getInstance().getSenderNamelist());
                     setReceiverNameList(DataSourceDataProvider.getInstance().getReciverNamelist());
                      setPaDateTo(DateUtility.getInstance().getCurrentMySqlDateTime1());
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
		logger.info("End of ::::PaymentsAction :::: prepare ");
		return resultType;
	}
        
        /*
         * Get Search Query
         */
        public String getPaymentSearchQuery() {
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		try {
			if (logger.isDebugEnabled()) {
				logger.info("Entered into the ::::PaymentsAction :::: getPaymentSearchQuery ");
			}
			prepare();
                        
                        
                          //System.out.println("before if"+getCheck());
                                
                                   //System.out.println("in else"+getCheck());
                                    if(getCheck()==null)
                                    {
                                      //  System.out.println("in if"+getCheck());
                                        setCheck("1");
                                    }
                                    else if(getCheck().equals(""))
                                        {
                                       // System.out.println("in else"+getCheck());
                                        setCheck("1");
                                        }
                                   
                                // System.out.println("setCheck-->"+getCheck());



			paymentList = ServiceLocator.getPaymentService().buildpaymentSQuery(this);
                        httpServletRequest.getSession(false).setAttribute(AppConstants.SES_PAYMENT_LIST,paymentList);
			
                        
                        resultType = SUCCESS;
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.error("problem :: "+ ex.getMessage()
								+ " method name :: getPaymentsSearchQuery() :: class name :: "
								+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					AppConstants.REQ_EXCEPTION_MSG, ex.getMessage());
			resultType = "error";
		}
	       }
		logger.info("End of ::::SearchPOAction :::: getPaymentsSearchQuery ");
		return resultType;
        }

    /**
     * @return the paChequeAmt
     */
    public String getPaChequeAmt() {
        return paChequeAmt;
    }

    /**
     * @param paChequeAmt the paChequeAmt to set
     */
    public void setPaChequeAmt(String paChequeAmt) {
        this.paChequeAmt = paChequeAmt;
    }
        //@Override
	/*public void setServletRequest(final HttpServletRequest reqObj) {
		this.setHttpServletRequest(reqObj);
	}

	public void setHttpServletRequest(final HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}*/
    
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

	public void setPaSearchQuery(String paSearchQuery) {
		this.paSearchQuery = paSearchQuery;
	}

	public String getPaSearchQuery() {
		return paSearchQuery;
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
     * @return the paDateTo
     */
    public String getPaDateTo() {
        return paDateTo;
    }

    /**
     * @param paDateTo the paDateTo to set
     */
    public void setPaDateTo(String paDateTo) {
        this.paDateTo = paDateTo;
    }

    /**
     * @return the paDateFrom
     */
    public String getPaDateFrom() {
        return paDateFrom;
    }

    /**
     * @param paDateFrom the paDateFrom to set
     */
    public void setPaDateFrom(String paDateFrom) {
        this.paDateFrom = paDateFrom;
    }

    /**
     * @return the paSenderId
     */
    public String getPaSenderId() {
        return paSenderId;
    }

    /**
     * @param paSenderId the paSenderId to set
     */
    public void setPaSenderId(String paSenderId) {
        this.paSenderId = paSenderId;
    }

    /**
     * @return the paSenderName
     */
    public String getPaSenderName() {
        return paSenderName;
    }

    /**
     * @param paSenderName the paSenderName to set
     */
    public void setPaSenderName(String paSenderName) {
        this.paSenderName = paSenderName;
    }

    /**
     * @return the paRecId
     */
    public String getPaRecId() {
        return paRecId;
    }

    /**
     * @param paRecId the paRecId to set
     */
    public void setPaRecId(String paRecId) {
        this.paRecId = paRecId;
    }

    /**
     * @return the paRecName
     */
    public String getPaRecName() {
        return paRecName;
    }

    /**
     * @param paRecName the paRecName to set
     */
    public void setPaRecName(String paRecName) {
        this.paRecName = paRecName;
    }

    /**
     * @return the paChequeNo
     */
    public String getPaChequeNo() {
        return paChequeNo;
    }

    /**
     * @param paChequeNo the paChequeNo to set
     */
    public void setPaChequeNo(String paChequeNo) {
        this.paChequeNo = paChequeNo;
    }

    /**
     * @return the paymentList
     */
    public List<PaymentBean> getPaymentList() {
        return paymentList;
    }

    /**
     * @param paymentList the paymentList to set
     */
    public void setPaymentList(List<PaymentBean> paymentList) {
        this.paymentList = paymentList;
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
