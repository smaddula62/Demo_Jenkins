
<%@page import="com.mss.ediscv.payments.PaymentBean"%>
<%-- 
    Document   : DocRepository
    Created on : Mar 11, 2013, 10:03:37 AM
    Author     : Nagireddy seerapu 
--%>
<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/tlds/dbgrid.tld" prefix="grd"%>
<%@ page import="com.freeware.gridtag.*"%>
<%@page import="java.sql.Connection"%>
<%@  page import="com.mss.ediscv.util.AppConstants"%>
<%@ page import="com.mss.ediscv.util.ConnectionProvider"%>
<%@ page import="java.sql.SQLException"%>


<html>
    <head>
        <title>Miracle Supply Chain Visibility portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />

 <%-- <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />  --%>
 
        <link rel="stylesheet" href='<s:url value="/includes/css/style.css"/>'
			type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/jquery-ui.css"/>'
			type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/dhtmlxcalendar.css"/>'
			type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/dhtmlxcalendar_omega.css"/>'
			type="text/css">
        
         <link rel="stylesheet" href='<s:url value="/includes/css/footerstyle.css"/>'
              type="text/css"/>
         
         <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-1.9.1.js"></s:url>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-ui.js"/>'></script>
       <script language="JavaScript"
        src='<s:url value="/includes/js/DBGrid.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/DateValidation.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/GridNavigation.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/dbgridDisplay.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
       <%--  <script language="JavaScript"
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>  --%>
        <script language="JavaScript"
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/downloadAjax.js"/>'></script>
  <script>
 /* $(function() {
    $( "#padatepicker" ).datepicker();
	 $( "#padatepickerfrom" ).datepicker();
  });*/
    var myCalendar;
		function doOnLoad() {
                        myCalendar = new dhtmlXCalendarObject(["padatepickerfrom","padatepicker"]);
                        myCalendar.setSkin('omega');
			myCalendar.setDateFormat("%m/%d/%Y");
                       // myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                       myCalendar.hideTime();
		
		}
function getDetails(val){  
     // alert("in jsp --->"+val);
    getPaymentDetails(val);
  }   
  </script>
<script type="text/javascript"> 
   $(function() {
       $('#attach_box').click(function() {
           $('#sec_box').show();
           return false;
       });        
   });
    $(function() {
       $('#detail_link').click(function() {
           $('#detail_box').show();
           return false;
       });        
   });
   
      // New function to show the left grid

  function demo(){
    $(function() {
            $('#detail_box').show();
           return false;
   });
  }
  
   function resetvalues()
    {
    document.getElementById('padatepickerfrom').value='';
    document.getElementById('padatepicker').value='';
    document.getElementById('paSenderId').value="-1";
    document.getElementById('paSenderName').value="-1";
    document.getElementById('paRecId').value="-1";
    document.getElementById('paRecName').value="-1";
    document.getElementById('sampleValue').value="1"; 
    document.getElementById('ackStatus').value="-1"; 
    document.getElementById('status').value="-1";
    document.getElementById('docType').value="-1"; 
    document.getElementById('corrattribute').value="-1"; 
    document.getElementById('corrvalue').value=""; 
    document.getElementById('corrvalue1').value="";
     document.getElementById('corrattribute1').value="-1";
    $('#detail_box').hide();
    $('#gridDiv').hide();
}


function checkCorrelation() {
     
  //   alert("hiii");
     var corrattr = document.getElementById('corrattribute').value;
     var corrval = document.getElementById('corrvalue').value;
     
       var corrattr1 = document.getElementById('corrattribute1').value;
     var corrval1 = document.getElementById('corrvalue1').value;
     
     
  
     if((corrattr!="-1")&&(corrval=="")) {
         alert("please enter Correlation Value!!!");
         return false;
     }
     if((corrattr=="-1")&&(corrval!="")) {
         alert("please select Correlation!");
         return false;
     }
     
   if((corrattr1!="-1")&&(corrval1=="")) {
         alert("please enter Correlation Value!!!");
         return false;
     }
     if((corrattr1=="-1")&&(corrval1!="")) {
         alert("please select Correlation!");
         return false;
     }
     
   
     
     var res = compareDates(document.getElementById('padatepickerfrom').value,document.getElementById('padatepicker').value);
      
    
     return res;
 }


</script>
</head>
<%
String check = null;
if(request.getAttribute("check")!=null)
  check = request.getAttribute("check").toString();

//System.out.println("check-->"+check);
    %>
<%--<body onload="doOnLoad();initDate('padatepickerfrom','padatepicker','<%=check %>');setStyle('mainFinance','paymentCurrId');"> --%>
<body onload="doOnLoad();setStyle('mainFinance','paymentCurrId');">
     <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
    <div id="wrapper">
  <div id="main">
    <header>
      <div id="logo">
  
          <s:include value="/includes/template/head.jsp"/>          
 
     </div>
      <nav>
       <ul class="sf-menu" id="nav">
	  <%--<li><a href="<s:url action="../doc/orderToCash"/>">Document Repository</a></li>
          <li><a href="<s:url action="../po/purchaseOrder"/>">Purchase Orders</a></li>
          <li><a href="<s:url action="../shipment/shipmentAction"/>">Shipments</a></li>
          <li><a href="<s:url action="../inv/invoiceAction"/>">Invoices</a></li>
	   <li class="current"><a href="#">Payments</a></li>
           <%
            // out.println(session.getAttribute(AppConstants.SES_ROLE_ID));
             
             if(session.getAttribute(AppConstants.SES_ROLE_ID).equals("101")){
          %>
          <li><a href="<s:url action="../tp/addTP"/>">Trading Partner</a></li>
          <%}%>  
          <s:include value="/includes/template/paymentMenu.jsp"/>--%>
         <%-- <s:include value="/includes/template/mainMenu.jsp"/>  --%>
         <s:include value="/includes/template/orderToCashMenu.jsp"/>
        </ul> 
      </nav>
    </header>
    <div id="site_content">
      <div id="sidebar_container">
	  
	  
	   <div id="detail_box" style="display: none;"> 
        <div class="sidebar">
          <h3>Detail Information</h3>
          <div class="sidebar_item">
              
               <div id="loadingImage" align="center"><img  src="../includes/images/ajax-loader.gif" /></div>

            <h5 id="detailInformation"></h5>
          </div>
		  </div>
		  
		  
          <div class="sidebar_base"></div>
        </div>
       
        
      </div>
     
      <div class="content" >
        <div class="content_item" id="searchdiv">
         <h3>Search By Payments</h3>
		<div &nbsp; style="alignment-adjust:central;" >
		<%String contextPath = request.getContextPath();
                    %>
           <table >
		<tbody >
		<s:form action="../payment/paymentSearch.action" method="post" name="paymentForm" id="paymentForm" theme="simple">
			<tr>
                            <td class="lableLeft">Date From </td>
				<td>
                                  <%--  <input type="text" id="datepickerfrom" /> --%>
                                    <s:textfield cssClass="inputStyle" name="paDateFrom" id="padatepickerfrom" value="%{paDateFrom}" tabindex="1" onkeypress="return enterManufacturingDatePayment();"/>
                                    <a href="javascript:copyValuTo('padatepickerfrom','padatepicker');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
		
			<td class="lableLeft">Date To </td>
				<td>
                                   <%-- <input type="text" id="datepicker" /> --%>
                                    <s:textfield cssClass="inputStyle" name="paDateTo" value="%{paDateTo}" id="padatepicker" tabindex="2" onkeypress="return enterManufacturingDatePayment();"/>
				</td>
				
				
			</tr>
                        <tr>	
				<td class="lableLeft">Sender Id</td>
				<td>
                                    <%-- <input type="text"> --%>
                                 <s:select headerKey="-1" headerValue="Select Type" list="senderIdList" name="paSenderId" id="paSenderId" value = "%{paSenderId}" tabindex="3" cssStyle="width : 150px"/>
                                    
				</td>
                                 <td class="lableLeft">Sender Name</td>
				<td>
                                   <%-- <input type="text"> --%>
                               <s:select headerKey="-1" headerValue="Select Type" list="senderNameList" name="paSenderName" id="paSenderName" value = "%{paSenderName}" tabindex="4" cssStyle="width : 150px"/>
				</td>
                                
				</tr>
			<tr>
                               <td class="lableLeft">Receiver Id</td>
				<td>
                                    <%-- <input type="text"> --%>
                              <s:select headerKey="-1" headerValue="Select Type" list="receiverIdList" name="paRecId" id="paRecId" value = "%{paRecId}" tabindex="5" cssStyle="width : 150px"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td>
                                    <%--<input type="text"> --%>
                           <s:select headerKey="-1" headerValue="Select Type" list="receiverNameList" name="paRecName" id="paRecName" value = "%{paRecName}" tabindex="6" cssStyle="width : 150px"/>
				</td>
				</tr>
				
				<%--<tr>
				<td class="lableLeft">Cheque #</td>
				<td>
                                 
                                    <s:textfield cssClass="inputStyle" name="paChequeNo" id="paChequeNo" value = "%{paChequeNo}" tabindex="7"/>
				</td>
                                
                                <td class="lableLeft">Cheque Amount</td>
				<td>
                                   
                                    <s:textfield cssClass="inputStyle" name="paChequeAmt" id="paChequeAmt" value = "%{paChequeAmt}" tabindex="8"/>
				</td>
				
			</tr> --%>
                                   <tr>
                               <td class="lableLeft">Correlation </td>
                               <td> 
                                   	<s:select headerKey="-1" headerValue="Select Attribute" list="correlationList" name="corrattribute" id="corrattribute" value="%{corrattribute}" tabindex="7" cssStyle="width : 150px"/>
                                    
                                </td>
                                <td class="lableLeft">Value </td>
                               <td> 
                                    <s:textfield cssClass="inputStyle" name="corrvalue" id="corrvalue" value="%{corrvalue}" tabindex="8"/>
                                </td>
                                
                            </tr>
                                 <tr>
                               <td class="lableLeft">Correlation </td>
                               <td> 
                                   	<s:select headerKey="-1" headerValue="Select Attribute" list="correlationList" name="corrattribute1" id="corrattribute1" value="%{corrattribute1}" tabindex="7" cssStyle="width : 150px"/>
                                    
                                </td>
                                <td class="lableLeft">Value </td>
                               <td> 
                                    <s:textfield cssClass="inputStyle" name="corrvalue1" id="corrvalue1" value="%{corrvalue1}" tabindex="8"/>
                                </td>
                                
                            </tr>
                            <tr>
                                    <td class="lableLeft">Document Type</td>
                              <td class="lableLeft">
                                  <s:select headerKey="-1" headerValue="Select Type" list="docTypeList" name="docType" id="docType" value="%{docType}" tabindex="9" cssStyle="width : 150px"/>
                               </td> 
                               <td class="lableLeft">Status</td>
                                <td class="lableLeft">
                                 <s:select headerKey="-1" headerValue="Select Type" list="{'Success','Error','Warning'}" name="status" id="status" value="%{status}" tabindex="10" cssStyle="width : 150px"/> 
                              </td>
                                
                                </tr>
                                <tr>
                                <td class="lableLeft">Ack Status 
                                </td>
				<td>
                                  <%-- <s:textfield cssClass="inputStyle" name="ackStatus" id="ackStatus" value = "%{ackStatus}" tabindex="9"/>  --%>
                                   <s:select headerKey="-1" headerValue="Select Type" list="{'Overdue','Accepted','Rejected'}" name="ackStatus" id="ackStatus" value="%{ackStatus}" tabindex="9" cssStyle="width : 150px"/> 
				</td>
			        </tr>
                      <tr>
                        <td style="background-color: white;">
                            
                            <strong>  <s:submit value="Search" cssClass="button" onclick="return checkCorrelation();" tabindex="9"/></strong>
                     <%--   </td>
                        <td style="background-color: white;"> --%>
                          <%--   <s:reset value="Reset" cssClass="button" tabindex="10"/> --%>
                           <strong><input type="button" value="Reset" class="button" tabindex="10" onclick="return resetvalues();"/></strong>
                        </td>
                        <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                        </tr>
		</tbody>
                 
	
	</table> 
	
            </div>	 
        </div>
           <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
      </div>
	  
	  
	<s:if test="#session.paymentSearchList != null">
 <div class="content" id="gridDiv">
        <div class="content_item">
<%!String cssValue = "whiteStripe";
            int resultsetTotal;%>

            <table align="left" width="710px"
            border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td style="background-color: white;">

            <table align="left" id="results" width="690px"
            border="0" cellpadding="0" cellspacing="0" class="CSSTableGenerator">
            <%
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_PAYMENT_LIST);
             
            if(list.size()!=0){
             
             PaymentBean paymentBean;
             %>
               <input type="hidden" name="sec_payment_list" id="sec_payment_list" value="<%=list.size()%>"/>
             <tr>
                
                <td>Partner</td>
                <td >InstanceId</td>
                <%-- <td >Check #</td>  --%>
                <td>PO #</td>
                <td>Inv #</td>
                <td >Date&nbsp;Time</td>
               <%-- <td>Trans Type</td> --%>
                <td>Cheque #</td>
                <td>Cheque Amount</td>
                <td>Status</td>
                <td>ACK Status</td>
                <td>Reprocess</td>
                <td>#</td>
             </tr>
            <tr>
             
             <%
            for (int i = 0; i < list.size(); i++) {
            paymentBean = (PaymentBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
           <tr>
               
                 <td>
                    <%
            out.println(paymentBean.getReceiverName());
            %>
                </td>
                <td><a href="javascript:getDetails(<%=paymentBean.getFileId()%>);" >
                    <%
                    out.println(paymentBean.getFileId()); 
                    %>
                     <input type="hidden" name="Instance<%=i%>" id="Instance<%=i%>" value="<%=paymentBean.getFileId()%>"/>
                    </a>
                </td>
                 <td>
                    <%
            out.println(paymentBean.getPonumber());
            %>
                </td>
                 <td>
                    <%
            out.println(paymentBean.getInvNumber());
            %>
           
                </td>
                   <td>
                   <%
             out.println(paymentBean.getDate_time_rec().toString().substring(0, paymentBean.getDate_time_rec().toString().lastIndexOf(":")));
            %>
                    
                </td>
               <%--  <td>
                   <%
            out.println(paymentBean.getTransType());
            %>
                    
                </td> --%>
               <td>
                    <%
                      if(paymentBean.getCheckNumber()!=null && !paymentBean.getCheckNumber().equals("")) {
                        out.println(paymentBean.getCheckNumber());
                         }else{
                            out.println("--");
                        }
            
            %>
             <input type="hidden" name="text<%=i%>" id="text<%=i%>" value="<%=paymentBean.getCheckNumber()%>"/>
            
                     </td> 
               
                  
                 <td>
                    <%
                          
                    if(paymentBean.getCheckAmount()!=null){
                    out.println("$"+paymentBean.getCheckAmount());
                       }
                       else{
                 out.println(paymentBean.getCheckAmount());
                       }
                 %>
                </td>
                 <td>
                    <%
                     if(paymentBean.getStatus().equalsIgnoreCase("ERROR")){
                        out.println("<font color='red'>"+paymentBean.getStatus().toUpperCase()+"</font>");
                       }else if(paymentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+paymentBean.getStatus().toUpperCase()+"</font>");
                       }else {
                       out.println("<font color='orange'>"+paymentBean.getStatus().toUpperCase()+"</font>");
                       }
            %>
                </td>
           <td>
                    <%
            //out.println(invoiceBean.getAckStatus());
            if(paymentBean.getAckStatus().equalsIgnoreCase("REJECT")){       
                         out.println("<font color='red'>"+paymentBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(paymentBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+paymentBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+paymentBean.getAckStatus().toUpperCase()+"</font>");
                       }
            %>
                   
           </td>
                    <td>
                    <%
            //out.println(purchaseOrderBean.getReProcessStatus());
                   if(paymentBean.getReProcessStatus()!=null){
                       out.println(paymentBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
            
            %>
                </td> 
                <td> &nbsp; &nbsp; 
                <input type = "checkbox" name ="check_List" id = "check_List" value="<%=paymentBean.getCheckNumber()%>"/>&nbsp; &nbsp;  
            </td>
           </tr>
            <%
            }
            }
            else
            {
                 %>
                 <tr><td>
                 <%
             
            //out.println("<img  border='0' align='top'  src='"+contextPath+"/includes/images/alert.gif'/><b> No Records Found to Display!</b>");
                 out.println("<img  border='0' align='top'  src='"+contextPath+"/includes/images/alert.gif'/><b>No records found for the given search criteria. Please try a different search criteria!</b>");
            }

            %>
                     </td>
           </tr>
            </table>
                </td>
            </tr>
             <%
            if(list.size()!=0){
                %>
            <tr>
                <td align="right" colspan="28" style="background-color: white;">
            <div align="right" id="pageNavPosition"></div>
                </td>
            </tr>
                <%}%>
            </table>
            </td>
            </tr>
</table>
        </div>
             <%-- Process butttons  start --%>
              <%
            if(list.size()!=0){
                %>
          <table align="right">
                <tr>
               <td style="background-color: white;">
                <strong><input type="button" value="ReTransmit" class="button" onmouseover="Tip('Click here to ReTransmit.')" onmouseout="UnTip()" onclick="return getProcesPayment(this,document.getElementById('sec_payment_list').value);" id="pre"/></strong>
             </td>
                <td style="background-color: white;">
                 <strong><input type="button" value="ReSubmit" class="button" onmouseover="Tip('Click here to Resubmit.')" onmouseout="UnTip()" onclick="return getProcesPayment(this,document.getElementById('sec_payment_list').value);" id="post"/></strong>
               </td>
                     <td style="background-color: white;">
        <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('payment','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/></strong>
        </td>
                </tr>
            </table> 
          <%}%>
            <%-- process buttons end--%> 
                </div>
               


</s:if> 
                </s:form>  
    </div>
        <script type="text/javascript">
        var pager = new Pager('results', 10); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    	</script>
     <%--  <div id="footer">
         <h2><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></h2>
		</div>  --%>
        </div>
  </div>
       <footer> 
         <p><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></p>
	  </footer>
</body>
</html>

