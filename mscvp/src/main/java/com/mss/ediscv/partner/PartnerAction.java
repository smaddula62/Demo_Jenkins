/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.partner;

import com.mss.ediscv.util.AppConstants;
import com.mss.ediscv.util.DataSourceDataProvider;
import com.mss.ediscv.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.log4j.Logger;
/**
 *
 * @author miracle
 */
public class PartnerAction extends ActionSupport implements
ServletRequestAware{
     private static Logger logger = Logger.getLogger(PartnerAction.class
			.getName());
 private HttpServletRequest httpServletRequest;   
 private String resultType;
 private String formAction;
 /*
  * Partner for fields start
  */
 
 private String partnerId;
 private String partnerName;
 private String internalIdentifier;
 private String partnerIdentifier;
 private String applicationId;
 private String countryCode;
 private String status;
 private String createdBy;
 private String flowFlag;
  /*
  * Partner for fields end
  */
 
 /*
  * Delcahnnelinfo fileds start
  */
 private String routingName;
 private int sequence=1;
 private String routerId;
 
 private String businessProcessId;
 private String translationMapName;
 private String docExtractMapName;
 private int archiveFlag;
 private String archiveDirectory;
 private String outputFileName;
 private String outputFormat;
 private String producerMailBox;
 
 private int translationId;
 private int documentExtarctId;
 private int producerMailBoxId;
 
 
 /* private String routingName;
   private String routingName;
    private String routingName;
     private String routingName;
      private String routingName;
       private String routingName;
        private String routingName;*/
 /*
  * Delcahnnelinfo fileds end
  */
 private Map partnerMap;
 private Map routerMap;
 private Map businessProcessMap;
 private Map translationMap;
 private Map documentExtractMap;
 private Map producerMailMap;
 
 private String encodingMailBoxId;

private Map encodingMailMap;

 private int deliveryChannelId;
 private String businessProcessName;
 private String encodingMailBoxName;

  public String addPartner() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: addPartner ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddPartner");
                   
                  resultType = SUCCESS;
               }
 return resultType;
  }
   public String doAddPartner() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: doAddPartner ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddPartner");
                   
                   setFlowFlag(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString());
                   setCreatedBy(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_LOGIN_ID).toString());
                  logger.info("partnerId-->"+getPartnerId());
                   logger.info("partnerName-->"+getPartnerName());
                   logger.info("internalIdentifier-->"+getInternalIdentifier());
                   
                   logger.info("partnerIdentifier-->"+getPartnerIdentifier());
                   logger.info("applicationId-->"+getApplicationId());
                   logger.info("countryCode-->"+getCountryCode());
                   logger.info("status-->"+getStatus());
                  String resultMessage = ServiceLocator.getPartnerService().addPartner(this);
                   getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   
                  resultType = SUCCESS;
               }
 return resultType;
  }
 
           public String getPartnerList() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: getPartnerList ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                  // setFormAction("addPartner");
                   if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_PARTNER_LIST)!=null)
                   getHttpServletRequest().getSession(false).removeAttribute(AppConstants.SES_PARTNER_LIST);
                   resultType = SUCCESS;
                  
               }
 return resultType;
  }
           
             public String partnerSearch() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: partnerSearch ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                    setFlowFlag(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString());
                   setCreatedBy(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_LOGIN_ID).toString());
                   ArrayList<PartnerBean> partnerList = ServiceLocator.getPartnerService().buildPartnerQuery(this);
                   getHttpServletRequest().getSession(false).setAttribute(AppConstants.SES_PARTNER_LIST, partnerList);
                   
                  // setFormAction("addPartner");
                   resultType = SUCCESS;
                  
               }
 return resultType;
  }
             
             
             public String partnerEdit() throws Exception {
                 logger.info("Entered into the ::::PartnerAction :::: partnerEdit ");
                 setResultType(LOGIN);
                 
                if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                    setFlowFlag(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString());
                   setCreatedBy(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_LOGIN_ID).toString());
                 ServiceLocator.getPartnerService().partnerEdit(this);
                 setFormAction("doEditPartner");
                 
                 
                 
                 //  getHttpServletRequest().getSession(false).setAttribute(AppConstants.SES_PARTNER_LIST, partnerList);
                   
                  // setFormAction("addPartner");
                   resultType = SUCCESS;
                  
               }
                 
                  return resultType;
             }
             
             
                public String doEditPartner() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: doEditPartner ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddPartner");
                   
                   setFlowFlag(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString());
                   setCreatedBy(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_LOGIN_ID).toString());
                  //logger.info("partnerId-->"+getPartnerId());
                   logger.info("partnerName-->"+getPartnerName());
                   logger.info("internalIdentifier-->"+getInternalIdentifier());
                   
                   logger.info("partnerIdentifier-->"+getPartnerIdentifier());
                   logger.info("applicationId-->"+getApplicationId());
                   logger.info("countryCode-->"+getCountryCode());
                   logger.info("status-->"+getStatus());
                  String resultMessage = ServiceLocator.getPartnerService().editPartner(this);
                   getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   
                  resultType = SUCCESS;
               }
 return resultType;
  }
                
                 public String addDeliveryChannelInfo() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: addDeliveryChannelInfo ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddDeliveryChannelInfo");
                   setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                   setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                    setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                 //  if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_PARTNER_LIST)!=null)
                  // getHttpServletRequest().getSession(false).removeAttribute(AppConstants.SES_PARTNER_LIST);
                   resultType = SUCCESS;
                  
               }
 return resultType;
  }
                 
                  public String doAddDeliveryChannelInfo() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: doAddDeliveryChannelInfo ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddDeliveryChannelInfo");
                 //  if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_PARTNER_LIST)!=null)
                  // getHttpServletRequest().getSession(false).removeAttribute(AppConstants.SES_PARTNER_LIST);
                   setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                   setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                   setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                    String resultMessage = ServiceLocator.getPartnerService().addDeliveryChannelInfo(this);
                    getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   resultType = SUCCESS;
                   
               }
 return resultType;
  }
                  
                  
  public String deliveryChannelList() throws Exception {
      logger.info("Entered into the ::::PartnerAction :::: deliveryChannelList");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                     setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                      setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                   setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                  resultType = SUCCESS;  
               }
               return resultType;
  }                
           
  
  
  public String deliveryChannelSearch() throws Exception{
       logger.info("Entered into the ::::PartnerAction :::: deliveryChannelSearch");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
        setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                      setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                   setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                   
                     ArrayList<PartnerBean> deliveryChannelList = ServiceLocator.getPartnerService().buildDeliverChannelQuery(this);
                   getHttpServletRequest().getSession(false).setAttribute(AppConstants.SES_DELIVERY_CHANNEL_LIST, deliveryChannelList);
                   
                  resultType = SUCCESS;  
                     }
               return resultType;
  }
      
          
              public String deliveryChannelEdit() throws Exception {
                 logger.info("Entered into the ::::PartnerAction :::: deliveryChannelEdit ");
                 setResultType(LOGIN);
                 
                if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                     setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                      setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                   setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                    setFlowFlag(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_DEFAULT_FLOWID).toString());
                   setCreatedBy(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_LOGIN_ID).toString());
                 ServiceLocator.getPartnerService().deliveryChannelEdit(this);
                 setFormAction("doEditDeliveryChannelInfo");
    
                   resultType = SUCCESS;
                  
               }
                 
                  return resultType;
             }
              
              
                
                  public String doEditDeliveryChannelInfo() throws Exception {
            
           // System.out.println("in tp Action----");
		logger.info("Entered into the ::::PartnerAction :::: doEditDeliveryChannelInfo ");
		setResultType(LOGIN);
	       if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_USER_NAME) != null) {
                   setFormAction("doAddDeliveryChannelInfo");
                 //  if(getHttpServletRequest().getSession(false).getAttribute(AppConstants.SES_PARTNER_LIST)!=null)
                  // getHttpServletRequest().getSession(false).removeAttribute(AppConstants.SES_PARTNER_LIST);
                   setPartnerMap(DataSourceDataProvider.getInstance().getPartnerMap());
                   setRouterMap(DataSourceDataProvider.getInstance().getRouterMap());
                   setBusinessProcessMap(DataSourceDataProvider.getInstance().getRelationMap("BP"));
                   setTranslationMap(DataSourceDataProvider.getInstance().getRelationMap("TRAN"));
                   setDocumentExtractMap(DataSourceDataProvider.getInstance().getRelationMap("DEM"));
                   setProducerMailMap(DataSourceDataProvider.getInstance().getRelationMap("PMB"));
                   setEncodingMailMap(DataSourceDataProvider.getInstance().getEncodeMap());
                    String resultMessage = ServiceLocator.getPartnerService().editDeliveryChannel(this);
                    getHttpServletRequest().setAttribute(AppConstants.REQ_RESULT_MSG,resultMessage);
                   resultType = SUCCESS;
                   
               }
 return resultType;
  }
             
  @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
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
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the partnerName
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * @param partnerName the partnerName to set
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * @return the internalIdentifier
     */
    public String getInternalIdentifier() {
        return internalIdentifier;
    }

    /**
     * @param internalIdentifier the internalIdentifier to set
     */
    public void setInternalIdentifier(String internalIdentifier) {
        this.internalIdentifier = internalIdentifier;
    }

    /**
     * @return the partnerIdentifier
     */
    public String getPartnerIdentifier() {
        return partnerIdentifier;
    }

    /**
     * @param partnerIdentifier the partnerIdentifier to set
     */
    public void setPartnerIdentifier(String partnerIdentifier) {
        this.partnerIdentifier = partnerIdentifier;
    }

    /**
     * @return the applicationId
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the flowFlag
     */
    public String getFlowFlag() {
        return flowFlag;
    }

    /**
     * @param flowFlag the flowFlag to set
     */
    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
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

    /**
     * @return the routerMap
     */
    public Map getRouterMap() {
        return routerMap;
    }

    /**
     * @param routerMap the routerMap to set
     */
    public void setRouterMap(Map routerMap) {
        this.routerMap = routerMap;
    }

    /**
     * @return the businessProcessMap
     */
    public Map getBusinessProcessMap() {
        return businessProcessMap;
    }

    /**
     * @param businessProcessMap the businessProcessMap to set
     */
    public void setBusinessProcessMap(Map businessProcessMap) {
        this.businessProcessMap = businessProcessMap;
    }

    /**
     * @return the routingName
     */
    public String getRoutingName() {
        return routingName;
    }

    /**
     * @param routingName the routingName to set
     */
    public void setRoutingName(String routingName) {
        this.routingName = routingName;
    }

    /**
     * @return the sequence
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the routerId
     */
    public String getRouterId() {
        return routerId;
    }

    /**
     * @param routerId the routerId to set
     */
    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    /**
     * @return the businessProcessId
     */
    public String getBusinessProcessId() {
        return businessProcessId;
    }

    /**
     * @param businessProcessId the businessProcessId to set
     */
    public void setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
    }

    /**
     * @return the translationMapName
     */
    public String getTranslationMapName() {
        return translationMapName;
    }

    /**
     * @param translationMapName the translationMapName to set
     */
    public void setTranslationMapName(String translationMapName) {
        this.translationMapName = translationMapName;
    }

    /**
     * @return the docExtractMapName
     */
    public String getDocExtractMapName() {
        return docExtractMapName;
    }

    /**
     * @param docExtractMapName the docExtractMapName to set
     */
    public void setDocExtractMapName(String docExtractMapName) {
        this.docExtractMapName = docExtractMapName;
    }

    /**
     * @return the archiveFlag
     */
    public int getArchiveFlag() {
        return archiveFlag;
    }

    /**
     * @param archiveFlag the archiveFlag to set
     */
    public void setArchiveFlag(int archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    /**
     * @return the archiveDirectory
     */
    public String getArchiveDirectory() {
        return archiveDirectory;
    }

    /**
     * @param archiveDirectory the archiveDirectory to set
     */
    public void setArchiveDirectory(String archiveDirectory) {
        this.archiveDirectory = archiveDirectory;
    }

    /**
     * @return the outputFileName
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * @param outputFileName the outputFileName to set
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * @return the outputFormat
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    /**
     * @param outputFormat the outputFormat to set
     */
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * @return the producerMailBox
     */
    public String getProducerMailBox() {
        return producerMailBox;
    }

    /**
     * @param producerMailBox the producerMailBox to set
     */
    public void setProducerMailBox(String producerMailBox) {
        this.producerMailBox = producerMailBox;
    }

    /**
     * @return the translationMap
     */
    public Map getTranslationMap() {
        return translationMap;
    }

    /**
     * @param translationMap the translationMap to set
     */
    public void setTranslationMap(Map translationMap) {
        this.translationMap = translationMap;
    }

    /**
     * @return the documentExtractMap
     */
    public Map getDocumentExtractMap() {
        return documentExtractMap;
    }

    /**
     * @param documentExtractMap the documentExtractMap to set
     */
    public void setDocumentExtractMap(Map documentExtractMap) {
        this.documentExtractMap = documentExtractMap;
    }

   

    /**
     * @return the producerMailMap
     */
    public Map getProducerMailMap() {
        return producerMailMap;
    }

    /**
     * @param producerMailMap the producerMailMap to set
     */
    public void setProducerMailMap(Map producerMailMap) {
        this.producerMailMap = producerMailMap;
    }

    /**
     * @return the translationId
     */
    public int getTranslationId() {
        return translationId;
    }

    /**
     * @param translationId the translationId to set
     */
    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    /**
     * @return the documentExtarctId
     */
    public int getDocumentExtarctId() {
        return documentExtarctId;
    }

    /**
     * @param documentExtarctId the documentExtarctId to set
     */
    public void setDocumentExtarctId(int documentExtarctId) {
        this.documentExtarctId = documentExtarctId;
    }

    /**
     * @return the producerMailBoxId
     */
    public int getProducerMailBoxId() {
        return producerMailBoxId;
    }

    /**
     * @param producerMailBoxId the producerMailBoxId to set
     */
    public void setProducerMailBoxId(int producerMailBoxId) {
        this.producerMailBoxId = producerMailBoxId;
    }

    /**
     * @return the encodingMailBoxId
     */
    public String getEncodingMailBoxId() {
        return encodingMailBoxId;
    }

    /**
     * @param encodingMailBoxId the encodingMailBoxId to set
     */
    public void setEncodingMailBoxId(String encodingMailBoxId) {
        this.encodingMailBoxId = encodingMailBoxId;
    }

    /**
     * @return the encodingMailMap
     */
    public Map getEncodingMailMap() {
        return encodingMailMap;
    }

    /**
     * @param encodingMailMap the encodingMailMap to set
     */
    public void setEncodingMailMap(Map encodingMailMap) {
        this.encodingMailMap = encodingMailMap;
    }

    /**
     * @return the deliveryChannelId
     */
    public int getDeliveryChannelId() {
        return deliveryChannelId;
    }

    /**
     * @param deliveryChannelId the deliveryChannelId to set
     */
    public void setDeliveryChannelId(int deliveryChannelId) {
        this.deliveryChannelId = deliveryChannelId;
    }

    /**
     * @return the businessProcessName
     */
    public String getBusinessProcessName() {
        return businessProcessName;
    }

    /**
     * @param businessProcessName the businessProcessName to set
     */
    public void setBusinessProcessName(String businessProcessName) {
        this.businessProcessName = businessProcessName;
    }

    /**
     * @return the encodingMailBoxName
     */
    public String getEncodingMailBoxName() {
        return encodingMailBoxName;
    }

    /**
     * @param encodingMailBoxName the encodingMailBoxName to set
     */
    public void setEncodingMailBoxName(String encodingMailBoxName) {
        this.encodingMailBoxName = encodingMailBoxName;
    }
}
