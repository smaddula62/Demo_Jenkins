/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.po;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.DateUtility;
import com.mss.ediscv.util.ServiceLocator;
import com.mss.ediscv.util.ServiceLocatorException;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author miracle
 */
public class PurchaseOrderAction extends ActionSupport implements
ServletRequestAware {
    private HttpServletRequest httpServletRequest;
	private String sqlQuery;
	private String poSearchList;
	private String submitFrm;
	private String resultType;
	private String currentDsnName;
        
        /*
         * Search Fields
         */
        private String poDateTo;
        private String poDateFrom;
        private String poRecId;
        private String poRecName;
        private String poSenderId;
        private String poSenderName;
        private String poNumber;
        private String sampleValue;
        private String check;
        private String ackStatus;
        private String gsNumber;
        private String status;
        
        private List correlationList;
        private List docTypeList;
        private String corrattribute;
        private String corrvalue;
        
        private String corrattribute1;
        private String corrvalue1;
        private String corrattribute2;
        private String corrvalue2;
        
        private String docType;
        //new added fileds
           private List senderIdList;
           private List receiverIdList; 
           private List senderNameList;
           private List receiverNameList;
        
        private List<PurchaseOrderBean> purchaseOrderList;
	private static Logger logger = Logger.getLogger(PurchaseOrderAction.class
			.getName());
	
	
	
	public String prepare() throws Exception {
            
          //  System.out.println("in DocRepositor Action----");
		logger.info("Entered into the ::::SearchPoAction :::: prepare ");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		try {
                    httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_DOC_LIST);
                    httpSession.removeAttribute(AppConstants.SES_INV_LIST);
                   
			if (getSubmitFrm() != null && (!getSubmitFrm().equals("frmDBGrid"))) {
				if (httpSession.getAttribute(AppConstants.SES_PO_LIST) != null) {
					httpSession.removeAttribute(AppConstants.SES_PO_LIST);
				}
			} else if (getSubmitFrm() == null && httpSession.getAttribute(AppConstants.SES_PO_LIST) != null) {
				httpSession.removeAttribute(AppConstants.SES_PO_LIST);
			}
                        
                        List corrList;
                        List docList;
                        List senderList;
                        List receiverList;
                        List senderNameList;
                        List receiverNameList;
                        corrList = DataSourceDataProvider.getInstance().getCorrelationNames(1,1);
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
                        setPoDateTo(DateUtility.getInstance().getCurrentMySqlDateTime1()); 
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
		logger.info("End of ::::SearchPurchaseOrderAction :::: prepare ");
		return resultType;
	}
        
        /*
         * Method : getPurchaseSearchQuery()
         */
        
        public String getPurchaseSearchQuery() {
           
            resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		try {
			if (logger.isDebugEnabled()) {
				logger.info("Entered into the ::::SearchpuchaseAction :::: getPurchaseSearchQuery ");
			}
			
                        //System.out.println("before search");
			prepare();
		              // System.out.println("after search"); 
                                    if(getCheck()==null)
                                    {
                                        setCheck("1");
                                    }
                                    
                                    else if(getCheck().equals(""))
                                        {
                                           setCheck("1");
                                        }
                                   
			purchaseOrderList=ServiceLocator.getPurchaseService().buildPurchaseQuery(this);
			httpServletRequest.getSession(false).setAttribute(AppConstants.SES_PO_LIST, purchaseOrderList);
                        
			resultType = SUCCESS;

		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.error("problem :: "+ ex.getMessage()
								+ " method name :: getpurchaseSearchQuery() :: class name :: "
								+ getClass().getName());
			}
			httpServletRequest.getSession(false).setAttribute(
					AppConstants.REQ_EXCEPTION_MSG, ex.getMessage());
			resultType = "error";
		}
		logger.info("End of ::::PurchaseAction :::: getpurchaseSearchQuery ");
	       }
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
        
        
      /* public void setServletRequest(HttpServletRequest httpServletRequest) {
          this.httpServletRequest = httpServletRequest;
       }*/
       /*public HttpServletRequest getServletRequest() {
            return httpServletRequest;
        }*/

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setPoSearchList(String poSearchList) {
		this.poSearchList = poSearchList;
	}

	public String getPoSearchList() {
		return poSearchList;
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
     * @return the poDateTo
     */
    public String getPoDateTo() {
        return poDateTo;
    }

    /**
     * @param poDateTo the poDateTo to set
     */
    public void setPoDateTo(String poDateTo) {
        this.poDateTo = poDateTo;
    }

    /**
     * @return the poDateFrom
     */
    public String getPoDateFrom() {
        return poDateFrom;
    }

    /**
     * @param poDateFrom the poDateFrom to set
     */
    public void setPoDateFrom(String poDateFrom) {
        this.poDateFrom = poDateFrom;
    }

    /**
     * @return the poRecId
     */
    public String getPoRecId() {
        return poRecId;
    }

    /**
     * @param poRecId the poRecId to set
     */
    public void setPoRecId(String poRecId) {
        this.poRecId = poRecId;
    }

    /**
     * @return the poRecName
     */
    public String getPoRecName() {
        return poRecName;
    }

    /**
     * @param poRecName the poRecName to set
     */
    public void setPoRecName(String poRecName) {
        this.poRecName = poRecName;
    }

    /**
     * @return the poSenderId
     */
    public String getPoSenderId() {
        return poSenderId;
    }

    /**
     * @param poSenderId the poSenderId to set
     */
    public void setPoSenderId(String poSenderId) {
        this.poSenderId = poSenderId;
    }

    /**
     * @return the poSenderName
     */
    public String getPoSenderName() {
        return poSenderName;
    }

    /**
     * @param poSenderName the poSenderName to set
     */
    public void setPoSenderName(String poSenderName) {
        this.poSenderName = poSenderName;
    }

    /**
     * @return the poNumber
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * @param poNumber the poNumber to set
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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
     * @return the gsNumber
     */
    public String getGsNumber() {
        return gsNumber;
    }

    /**
     * @param gsNumber the gsNumber to set
     */
    public void setGsNumber(String gsNumber) {
        this.gsNumber = gsNumber;
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
