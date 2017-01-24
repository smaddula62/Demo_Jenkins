
<%@page import="com.mss.ediscv.inv.InvoiceBean"%>
<%-- 
    Document   : Invoices
    Created on : Mar 11, 2013, 11:04:10 AM
    Author     : miracle
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
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/dbgridDisplay.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
       <%--  <script language="JavaScript"
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>  --%>
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/downloadAjax.js"/>'></script>
        <script>      
  /*$(function() {
    $( "#invdatepicker" ).datepicker();
	 $( "#invdatepickerfrom" ).datepicker();
  });*/
    var myCalendar;
		function doOnLoad() {
                        myCalendar = new dhtmlXCalendarObject(["invdatepickerfrom","invdatepicker"]);
                        myCalendar.setSkin('omega');
			myCalendar.setDateFormat("%m/%d/%Y %H:%i");
		
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
function resetvalues()
{
    document.getElementById('invdatepickerfrom').value='';

    document.getElementById('invdatepicker').value='';

    document.getElementById('invSenderId').value="-1";

    document.getElementById('invSenderName').value="-1";

    document.getElementById('invBusId').value="-1";

    document.getElementById('invRecName').value="-1";
 document.getElementById('corrattribute').value="-1"; 
    document.getElementById('corrvalue').value="";
  //  document.getElementById('invNum').value='';

//document.getElementById('invPoNum').value='';
 document.getElementById('sampleValue').value="1";  
 document.getElementById('ackStatus').value="-1"; 
     document.getElementById('status').value="-1";
 document.getElementById('docType').value="-1"; 
    $('#detail_box').hide();
    $('#gridDiv').hide();
    
}

function checkCorrelation() {
     
  //   alert("hiii");
     var corrattr = document.getElementById('corrattribute').value;
     var corrval = document.getElementById('corrvalue').value;
     if((corrattr!="-1")&&(corrval=="")) {
         alert("please enter Correlation Value!!!");
         return false;
     }
     if((corrattr=="-1")&&(corrval!="")) {
         alert("please select Correlation!");
         return false;
     }
 var res = compareDates(document.getElementById('invdatepickerfrom').value,document.getElementById('invdatepicker').value);
      
    
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
    <%--<body onload="doOnLoad();initDateTime('invdatepickerfrom','invdatepicker','<%=check %>');setStyle('mainFinance','invCurrId');">  --%>
    <body onload="doOnLoad();setStyle('mainFinance','invCurrId');">
         <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
        <div id="wrapper">
         <div id="main">
    <header>
      <div id="logo">
      <s:include value="/includes/template/head.jsp"/>    
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
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
     
      <div class="content">
        <div class="content_item" id="searchdiv">
         <h3>Search By Invoices</h3>
		<div &nbsp; style="alignment-adjust:central;" >
		<%String contextPath = request.getContextPath();
                    %>
           <table >
		<tbody >
		   <s:form action="../inv/invoiceSearch.action" method="post" name="invoiceForm" id="invoiceForm" theme="simple">
			<tr>
                            <td class="lableLeft">Date From </td>
				<td><%-- <input type="text" id="datepickerfrom" /> --%>
                                  <%--  <input type="text" name="datepickerfrom" id="datepickerfrom" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="invdatepickerfrom" id="invdatepickerfrom" value="%{invdatepickerfrom}" tabindex="1"  onkeypress="return enterManufacturingDateInvoice();"/>
                                  <a href="javascript:copyValuTo('invdatepickerfrom','invdatepicker');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
		        <td class="lableLeft">Date To </td>
				<td><%-- <input type="text" id="datepicker" /> --%>
                                  <%--  <input type="text" name="datepicker" id="datepicker" class="inputStyle" tabindex="2" />  --%>
                                  <s:textfield cssClass="inputStyle" name="invdatepicker"  value="%{invdatepicker}" id="invdatepicker" tabindex="2" onkeypress="return enterManufacturingDateInvoice();"/>
				</td>
				
			</tr>
			<tr>
				<td class="lableLeft">Sender Id</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="senderId" id="senderId" class="inputStyle" tabindex="2" /> --%>
                                <s:select headerKey="-1" headerValue="Select Type" list="senderIdList" name="invSenderId" id="invSenderId" value = "%{invSenderId}" tabindex="3" cssStyle="width : 150px"/>
				</td>
                                <td class="lableLeft">Sender Name</td>
				<td><%-- <input type="text"> --%>
                                  <%--  <input type="text" name="senderName" id="senderName" class="inputStyle" tabindex="2" /> --%>
                               <s:select headerKey="-1" headerValue="Select Type" list="senderNameList" name="invSenderName" id="invSenderName" value = "%{invSenderName}" tabindex="4" cssStyle="width : 150px"/>
				</td>
				
				</tr>
			<tr>
				<td class="lableLeft">Receiver Id</td>
				<td><%-- <input type="text"> --%>
                                    <%--<input type="text" name="buId" id="buId" class="inputStyle" tabindex="2" /> --%>
                                <s:select headerKey="-1" headerValue="Select Type" list="receiverIdList" name="invBusId" id="invBusId" value = "%{invBusId}" tabindex="5" cssStyle="width : 150px"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="recName" id="recName" class="inputStyle" tabindex="2" /> --%>
                               <s:select headerKey="-1" headerValue="Select Type" list="receiverNameList" name="invRecName" id="invRecName" value = "%{invRecName}" tabindex="6" cssStyle="width : 150px"/>
				</td>
				</tr>
                              
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
                                <s:submit value="Search" cssClass="button" onclick="return checkCorrelation();" tabindex="9"/>
                         <%--   </td>
                            <td style="background-color: white;"> --%>
                           <%--  <s:reset value="Reset" cssClass="button"/> --%>
                           <strong><input type="button" value="Reset" class="button" tabindex="10" onclick="return resetvalues();"/></strong>
                            </td>
                            
                            <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                        </tr>
                               </tbody>
                                
                    </table>
                        
	 
            </div>
	 <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
        </div>
      </div>
	  	  
   <s:if test="#session.invoiceList != null"> 
	  <%--- GRid start --%>
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
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_INV_LIST);
             
            if(list.size()!=0){
             InvoiceBean invoiceBean;
             %>
              <input type="hidden" name="sec_invoice_list" id="sec_invoice_list" value="<%=list.size()%>"/>
             <tr>
                 <td>InstanceId</td> 
               <td >Invoice #</td>
               <td >Partner</td>
                <td >PO #</td>
                <td >Item Qty</td>
                <td>Invoice Amount</td>
                <td>DateTime</td>
                <td>Status</td>
                <td>Ack Status</td>
                <td>Reprocess</td>
                <td>#</td>
            </tr>
            <tr>
             
             <%
            for (int i = 0; i < list.size(); i++) {
            invoiceBean = (InvoiceBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
            
              <%--  <td><a href="#" onclick="return demo();" > --%>
                   
               <td>
                    <%
            out.println(invoiceBean.getFileId());
            %>
             <input type="hidden" name="Instance<%=i%>" id="Instance<%=i%>" value="<%=invoiceBean.getFileId()%>"/>
                </td>
                
                   <td>  <a href="javascript:getInvDetails('<%=invoiceBean.getInvNumber()%>','<%=invoiceBean.getPoNumber()%>','<%=invoiceBean.getFileId()%>');"  >
                    <%
                    out.println(invoiceBean.getInvNumber());
                    %>
                    <input type="hidden" name="text<%=i%>" id="text<%=i%>" value="<%=invoiceBean.getInvNumber()%>"/>
                    </a>
                </td>
                <td>
                    <%
            out.println(invoiceBean.getPname());
            %>
                </td>
                <td>
                    <%
            out.println(invoiceBean.getPoNumber());
            %>
                </td>
                <td>
                   <%
            out.println(invoiceBean.getItemQty());
            %>
                    
                </td>
                <td>
                    <%
                    if(invoiceBean.getInvAmount()!=null){
                    out.println("$"+invoiceBean.getInvAmount());
                       }
                       else{
                 out.println(invoiceBean.getInvAmount());
                       }
            %>
                   
           </td>
           <td>
                    <%
            out.println(invoiceBean.getDate_time_rec().toString().substring(0, invoiceBean.getDate_time_rec().toString().lastIndexOf(":")));
            %>
                   
           </td>
            <td>
                    <%
                   // out.println(invoiceBean.getStatus());
                    if(invoiceBean.getStatus().equalsIgnoreCase("ERROR")){
                        out.println("<font color='red'>"+invoiceBean.getStatus().toUpperCase()+"</font>");
                       }else if(invoiceBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+invoiceBean.getStatus().toUpperCase()+"</font>");
                       }else {
                       out.println("<font color='orange'>"+invoiceBean.getStatus().toUpperCase()+"</font>");
                       }
            %>
                </td>
           <td>
                    <%
            //out.println(invoiceBean.getAckStatus());
         if(invoiceBean.getAckStatus().equalsIgnoreCase("REJECT")){       
                         out.println("<font color='red'>"+invoiceBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(invoiceBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+invoiceBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+invoiceBean.getAckStatus().toUpperCase()+"</font>");
                       }
            %>
                   
           </td>
                      <td>
                    <%
            //out.println(purchaseOrderBean.getReProcessStatus());
                   if(invoiceBean.getReProcessStatus()!=null){
                       out.println(invoiceBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
            
            %>
                </td> 
                 <td> &nbsp; &nbsp; 
                <input type = "checkbox" name ="check_List" id = "check_List" value="<%=invoiceBean.getInvNumber()%>"/>&nbsp; &nbsp;  
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
             //String contextPath = request.getContextPath();
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
            <tr >
             <td align="right" colspan="28" style="background-color: white;">
            <div align="right" id="pageNavPosition">hello</div>
             </td>
            </tr>   
            <% } %>
</table>
 </div>
  
<%-- Grid End --%>
           <%-- Process butttons  start --%>
            <%
            if(list.size()!=0){
                %>
          <table align="right">
                <tr>
                      <td style="background-color: white;">
                <strong><input type="button" value="ReTransmit" class="button" onmouseover="Tip('Click here to ReTransmit.')" onmouseout="UnTip()" onclick="return getProcesInv(this,document.getElementById('sec_invoice_list').value);" id="pre"/></strong>
             </td>
                <td style="background-color: white;">
                 <strong><input type="button" value="ReSubmit" class="button" onmouseover="Tip('Click here to Resubmit.')" onmouseout="UnTip()" onclick="return getProcesInv(this,document.getElementById('sec_invoice_list').value);" id="post"/></strong>
               </td>
                     <td style="background-color: white;">
        <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('invoice','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/></strong>
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
        </div>  
             </div>
   <%-- <div id="footer"> --%>
           <footer> 
         <p><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></p>
	  </footer>
		<%--</div> --%>
      
  
       
    </body>
</html>
