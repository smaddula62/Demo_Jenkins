/*
 ******************************************************************************
 *
 * Project : Miracle Supply Chain Visibility portal v2.0
 *
 * Package : com.mss.mscvp.utill
 *
 * Date    : Mar 11, 2013 1:38:00 PM
 *
 * Author  : Nagireddy Seerapu <nseerapu@miraclesoft.com>
 *
 * File    : ShipmentSearchAction.java
 *
 * 
 * *****************************************************************************
 */
package com.mss.ediscv.shipment;


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
 * @author miracle
 *
 */
public class ShipmentSearchAction extends ActionSupport implements
ServletRequestAware {
	
	
	private String datepicker;
	private String datepickerfrom;
	private String senderId;
	private String senderName;
	private String buId;
	private String recName;
	private String asnNum;
	private String bolNum;
        private String poNum;
	
	private HttpServletRequest httpServletRequest;
	private String sqlQuery;
	private String poSearchQuery;
	private String submitFrm;
	private String resultType;
        private String sampleValue;
	private String check;
        
        private String ackStatus;
        private String status;
        
        private List<ShipmentBean> shipmentList;

	private String currentDsnName;
        
        private String corrattribute;
        private String corrvalue;
        
        private String corrattribute1;
        private String corrvalue1;
        
        private List correlationList;
        private List docTypeList;
        private String docType;
       // new files added 
           private List senderIdList;
           private List receiverIdList; 
           private List senderNameList;
           private List receiverNameList;
        
	private static Logger logger = Logger.getLogger(ShipmentSearchAction.class
			.getName());
	
	
	
	public String prepare() throws Exception {
            
          //  System.out.println("in shipment Action----");
		logger.info("Entered into the ::::SearchSHIPMENTAction :::: prepare ");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		try {
                    
                    httpSession.removeAttribute(AppConstants.SES_PAYMENT_LIST);
                   // httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
                    httpSession.removeAttribute(AppConstants.SES_DOC_LIST);
                    httpSession.removeAttribute(AppConstants.SES_INV_LIST);
                    httpSession.removeAttribute(AppConstants.SES_PO_LIST);
			if (getSubmitFrm() != null && (!getSubmitFrm().equals("frmDBGrid"))) {
				if (httpSession.getAttribute(AppConstants.SES_SHIPMENT_LIST) != null) {
					httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
				}
			} else if (getSubmitFrm() == null && httpSession.getAttribute(AppConstants.SES_SHIPMENT_LIST) != null) {
				httpSession.removeAttribute(AppConstants.SES_SHIPMENT_LIST);
			}
                        List corrList;
                        List docList;
                        List senderList;
                        List receiverList;
                        List senderNameList;
                        List receiverNameList;
                        
                       // if(httpSession.getAttribute(AppConstants.CORRELATION_LIST)!=null) {
                         //   corrList = (ArrayList)httpSession.getAttribute(AppConstants.CORRELATION_LIST);
                        
                       // }else {
                            corrList = DataSourceDataProvider.getInstance().getCorrelationNames(2,1);
                        
                            docList = DataSourceDataProvider.getInstance().getDocumentTypeList();
                           // httpSession.setAttribute(AppConstants.DOCMENT_TYPE_MAP, docMap);
                      //  }
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
                         setDatepicker(DateUtility.getInstance().getCurrentMySqlDateTime1());
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
		logger.info("End of ::::SearchShipmentAction :::: prepare ");
		return resultType;
	}
	
	
	
	/*
	 * getShipmentSearchQuery method will get the search query for grid.
	 */
	public String getShipmentSearchQuery() throws Exception {
           // System.out.println("getQuery---");
		resultType = LOGIN;
	       if(httpServletRequest.getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
		try {
			if (logger.isDebugEnabled()) {
				logger
						.info("Entered into the ::::SearchPOAction :::: getPOSearchQuery ");
			}
			
			prepare();
                        
                         // System.out.println("before if"+getCheck());
                                
                                  // System.out.println("in else"+getCheck());
                                    if(getCheck()==null)
                                    {
                                        //System.out.println("in if"+getCheck());
                                        setCheck("1");
                                    }
                                    else if(getCheck().equals(""))
                                        {
                                        //System.out.println("in else"+getCheck());
                                        setCheck("1");
                                        }
                                   
                                 //System.out.println("setCheck-->"+getCheck());




                        shipmentList = ServiceLocator.getShipmentService().buildshipmentSQuery(this);
                        httpServletRequest.getSession(false).setAttribute(AppConstants.SES_SHIPMENT_LIST,shipmentList);
			//purchaseOrderList = ServiceLocator.getPurchaseService().buildPurchaseQuery(this);
			//httpServletRequest.getSession(false).setAttribute(AppConstants.SES_PO_QUERY,purchaseOrderList);
      
			resultType = SUCCESS;

		} catch (Exception ex) {
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
	
	public String getPoNum() {
		return poNum;
	}

	public void setPoNum(String poNum) {
		this.poNum = poNum;
	}

	 /**
     * @return the datepicker
     */
    public String getDatepicker() {
        return datepicker;
    }

    /**
     * @param datepicker the datepicker to set
     */
    public void setDatepicker(String datepicker) {
        this.datepicker = datepicker;
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
     * @return the buId
     */
    public String getBuId() {
        return buId;
    }

    /**
     * @param buId the buId to set
     */
    public void setBuId(String buId) {
        this.buId = buId;
    }

    /**
     * @return the recName
     */
    public String getRecName() {
        return recName;
    }

    /**
     * @param recName the recName to set
     */
    public void setRecName(String recName) {
        this.recName = recName;
    }

    /**
     * @return the asnNum
     */
    public String getAsnNum() {
        return asnNum;
    }

    /**
     * @param asnNum the asnNum to set
     */
    public void setAsnNum(String asnNum) {
        this.asnNum = asnNum;
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

	public void setPoSearchQuery(String poSearchQuery) {
		this.poSearchQuery = poSearchQuery;
	}

	public String getPoSearchQuery() {
		return poSearchQuery;
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
     * @return the shipmentList
     */
    public List<ShipmentBean> getShipmentList() {
        return shipmentList;
    }

    /**
     * @param shipmentList the shipmentList to set
     */
    public void setShipmentList(List<ShipmentBean> shipmentList) {
        this.shipmentList = shipmentList;
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
