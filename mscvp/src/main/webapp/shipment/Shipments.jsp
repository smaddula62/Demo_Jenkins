<%-- 
    Document   : Shipments
    Created on : Mar 11, 2013, 11:04:41 AM
    Author     : Nagireddy seerapu
--%>
<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@page import="com.mss.ediscv.shipment.ShipmentBean"%>

<%@ taglib uri="/WEB-INF/tlds/dbgrid.tld" prefix="grd"%>
<%@ page import="com.freeware.gridtag.*"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException"%>
<%@ page import="com.mss.ediscv.util.AppConstants" %>
<%@ page import="com.mss.ediscv.util.ConnectionProvider" %>
<%@ page import="com.mss.ediscv.util.ServiceLocatorException" %>
<%@ page import="org.apache.log4j.Logger"%>

<html>
    <head>
        <title>Miracle Supply Chain Visibility Portal</title>
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
        src='<s:url value="/includes/js/dbgridDisplay.js"/>'></script>
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
        src='<s:url value="/includes/js/common.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/downloadAjax.js"/>'></script>
          <script>
        
  /*$(function() {
    $( "#shpdatepicker" ).datepicker();
	 $( "#shpdatepickerfrom" ).datepicker();
  });
         */
            var myCalendar;
		function doOnLoad() {
                        myCalendar = new dhtmlXCalendarObject(["shpdatepickerfrom","shpdatepicker"]);
                        myCalendar.setSkin('omega');
			myCalendar.setDateFormat("%m/%d/%Y");
                        myCalendar.hideTime();
		
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
$('.check_link').click (function ()
{
var thisCheck = $(this);
if (thischeck.is (':checked'))
{
  $('#check_box').show();
}
});
function getDetails(val,ponum,fileid){
    getAsnDetails(val,ponum,fileid);
  }
//life cycle fuctionality for asn

  
function resetvalues()
{
    document.getElementById('buId').value="-1";
    document.getElementById('shpdatepickerfrom').value="";
    document.getElementById('shpdatepicker').value="";
    document.getElementById('senderId').value="-1";
    document.getElementById('senderName').value="-1";
   // document.getElementById('asnNum').value="";
    document.getElementById('recName').value="-1";
   // document.getElementById('bolNum').value="";
    //document.getElementById('poNum').value="";   
     document.getElementById('sampleValue').value="1"; 
    document.getElementById('ackStatus').value="-1"; 
    // document.shipmentForm.ackStatus.value="-1"; 
     document.getElementById('status').value="-1";
     document.getElementById('docType').value="-1"; 
     
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
     
    
     
     var res = Formvalidation(document.getElementById('shpdatepickerfrom').value,document.getElementById('shpdatepicker').value);
      
    
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
   <%--<body onload="doOnLoad();initDate('shpdatepickerfrom','shpdatepicker','<%=check %>');setStyle('mainShipment','shipmentCurrId');">  --%>
   <body onload="doOnLoad();setStyle('mainShipment','shipmentCurrId');"> 
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
       <%--  <div class="sidebar">
          <h3>Detail Information</h3>
          <div class="sidebar_item">
            <h4>Detail 1</h4>
            <h5>Detail 2</h5>
             <h5>Detail 2</h5>
          </div>
		  </div> --%>
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
         <h3>Search By Shipments</h3>
		<div &nbsp; style="alignment-adjust:central;" >
                    <%String contextPath = request.getContextPath();
                    %>
           <table >
		<tbody >
                    <s:form action="../shipment/shipmentSearch.action" method="post" name="shipmentForm" id="shipmentForm" theme="simple">
		 <tr>
                     <td class="lableLeft">Date From </td>
				<td><%-- <input type="text" id="datepickerfrom" /> --%>
                                  <%--  <input type="text" name="datepickerfrom" id="datepickerfrom" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="datepickerfrom" value="%{datepickerfrom}" id="shpdatepickerfrom" tabindex="1" onkeypress="return enterManufacturingDateShipment();"/>
                                  <a href="javascript:copyValuTo('shpdatepickerfrom','shpdatepicker');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
		        <td class="lableLeft">Date To </td>
				<td><%-- <input type="text" id="datepicker" /> --%>
                                  <%--  <input type="text" name="datepicker" id="datepicker" class="inputStyle" tabindex="2" />  --%>
                                  <s:textfield cssClass="inputStyle" name="datepicker" value="%{datepicker}" id="shpdatepicker" tabindex="2" onkeypress="return enterManufacturingDateShipment();"/>
				</td>
				
			</tr>
			<tr>
				<td class="lableLeft">Sender Id</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="senderId" id="senderId" class="inputStyle" tabindex="2" /> --%>
                                 <s:select headerKey="-1" headerValue="Select Type" list="senderIdList" name="senderId" id="senderId" value = "%{senderId}" tabindex="3" cssStyle="width : 150px"/>
				</td>
				<td class="lableLeft">Sender Name</td>
				<td><%-- <input type="text"> --%>
                                  <%--  <input type="text" name="senderName" id="senderName" class="inputStyle" tabindex="2" /> --%>
                               <s:select headerKey="-1" headerValue="Select Type" list="senderNameList" name="senderName" id="senderName" value = "%{senderName}" tabindex="4" cssStyle="width : 150px"/>
				</td>
				</tr>
			<tr>
				<td class="lableLeft">Receiver ID</td>
				<td><%-- <input type="text"> --%>
                                    <%--<input type="text" name="buId" id="buId" class="inputStyle" tabindex="2" /> --%>
                               <s:select headerKey="-1" headerValue="Select Type" list="receiverIdList" name="buId" id="buId" value = "%{buId}" tabindex="5" cssStyle="width : 150px"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="recName" id="recName" class="inputStyle" tabindex="2" /> --%>
                                 <s:select headerKey="-1" headerValue="Select Type" list="receiverNameList" name="recName" id="recName" value = "%{recName}" tabindex="6" cssStyle="width : 150px"/>
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
			<%--<tr>
				<td class="lableLeft">PO #
                                </td>
				<td>
                                   <s:textfield cssClass="inputStyle" name="poNum" id="poNum" tabindex="9"/>
				</td>
			</tr>--%>
                      <tr>
                        <td style="background-color: white;">
                      <%--   <strong>  <s:submit value="Search" cssClass="button" onclick="return compareDates(document.getElementById('shpdatepickerfrom').value,document.getElementById('shpdatepicker').value)" tabindex="10"/></strong> --%>
                     
                      <strong>  <s:submit value="Search" cssClass="button" onclick="return checkCorrelation();" tabindex="10"/></strong>
               <%--         </td>
                        <td style="background-color: white;"> --%>
                        <%-- <s:reset value="Reset" cssClass="button" tabindex="11"/> --%>
                        <strong><input type="button" value="Reset" class="button" tabindex="11" onclick="return resetvalues();"/></strong>
                        </td>
                        <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                        </tr>
		</tbody>
		
	</table> 
           
                    
                                         
            </div>
             
		 
		 
        </div>
           <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
      </div>
	  
	  <s:if test="#session.shipmentSearchList != null">
 <div class="content" id="gridDiv">
        <div class="content_item">
<%--<table  border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr align="center">
        <td>     --%>

        <%!String cssValue = "whiteStripe";
            int resultsetTotal;%>

            <table align="left" width="710px"
            border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td style="background-color: white;">

            <table align="left" id="results" width="690px"
            border="0" cellpadding="0" cellspacing="0" class="CSSTableGenerator">
            <%
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_SHIPMENT_LIST);
             
            if(list.size()!=0){
             
             ShipmentBean shipmentBean;
             %>
             <input type="hidden" name="sec_shipment_list" id="sec_shipment_list" value="<%=list.size()%>"/> 
             <tr>
               <td>InstanceId</td>
               <td >ASN #</td>
               <td >Partner</td>  
               
               <%-- <td>Ship Date</td> --%>
               <td>DateTime</td>
               <%--<td>ISA #</td> --%>
              <%-- <td>GS #</td>  --%>
               <%-- <td>ST #</td> --%>
               <td>Direction</td>
               <td>Status</td>
               <td>Ack Status</td>
               <td>Reprocess</td>
                <%--<td >PO #</td> --%>
                    <%
            // System.out.println("Session=========================="+session.getAttribute(AppConstants.SES_ROLE_ID).equals("100"));
             //System.out.println("Session=========================="+session.getAttribute(AppConstants.SES_ROLE_ID).equals("104"));
             if(session.getAttribute(AppConstants.SES_ROLE_ID).equals("100")||session.getAttribute(AppConstants.SES_ROLE_ID).equals("104")){
          %>
                 <td>#</td>
                
             <%}%>    
               
            </tr>
            <tr>
             
             <%
            for (int i = 0; i < list.size(); i++) {
            shipmentBean = (ShipmentBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
           <tr>
               <%-- <td><a href="#" onclick="return demo();" > --%>
               <td>
                    <%
            out.println(shipmentBean.getFile_id());
            %>
            <input type="hidden" name="Instance<%=i%>" id="Instance<%=i%>" value="<%=shipmentBean.getFile_id()%>"/> 
                </td>
                <td>  <a href="javascript:getDetails('<%=shipmentBean.getAsnNo()%>','<%=shipmentBean.getPoNo()%>','<%=shipmentBean.getFile_id()%>');">
                    <%
                    out.println(shipmentBean.getAsnNo());
                    %>
                    <input type="hidden" name="text<%=i%>" id="text<%=i%>" value="<%=shipmentBean.getAsnNo()%>"/> 
                    </a>
                </td>
               <td>
                    <%
            out.println(shipmentBean.getPname());
            %>
                </td>
               
                
                <%-- <td>
                    <%
            out.println(shipmentBean.getShipmentDate());
            %>
                </td>  --%>
                <td>
                    <%
          //  out.println(shipmentBean.getDate_time_rec());
           out.println(shipmentBean.getDate_time_rec().toString().substring(0, shipmentBean.getDate_time_rec().toString().lastIndexOf(":")));
            %>
                </td>
             
               <%-- <td>
                    <%
            out.println(shipmentBean.getIsa());
            %>
                </td>  --%>
              <%--  <td>
                    <%
            out.println(shipmentBean.getGsCtrl());
            %>
                </td>  --%>
              <%--  <td>
                    <%
            out.println(shipmentBean.getStCtrl());
            %>
                </td>  --%>
                <td>
                    <%
            out.println(shipmentBean.getDirection().toUpperCase());
            %>
                </td>
                <td>
                    <%
                     if(shipmentBean.getStatus().equalsIgnoreCase("ERROR")){
                        out.println("<font color='red'>"+shipmentBean.getStatus().toUpperCase()+"</font>");
                       }else if(shipmentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+shipmentBean.getStatus().toUpperCase()+"</font>");
                       }else {
                       out.println("<font color='orange'>"+shipmentBean.getStatus().toUpperCase()+"</font>");
                       }
            %>
                </td>
               <td>
                    <%
            //out.println(shipmentBean.getAckStatus());
                    if(shipmentBean.getAckStatus().equalsIgnoreCase("REJECTED")){       
                         out.println("<font color='red'>"+shipmentBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(shipmentBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+shipmentBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+shipmentBean.getAckStatus().toUpperCase()+"</font>");
                       }
            %>
                </td>
                        <td>
                    <%
            //out.println(purchaseOrderBean.getReProcessStatus());
                   if(shipmentBean.getReProcessStatus()!=null){
                       out.println(shipmentBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
            
            %>
                </td>
                     </td>
                <% 
             if(session.getAttribute(AppConstants.SES_ROLE_ID).equals("100")||session.getAttribute(AppConstants.SES_ROLE_ID).equals("104")){      
          %>
            <td> &nbsp; &nbsp; 
                <input type = "checkbox" name ="check_List" id = "check_List" value="<%= shipmentBean.getAsnNo()%>"/>&nbsp; &nbsp;  
            </td> 
            <%
             }
              %>
           </tr>
            <%
            }
            }
            else
            {
                 %>
                 <tr><td>
                 <%
            // String contextPath = request.getContextPath();
           // out.println("<img  border='0' align='top'  src='"+contextPath+"/includes/images/alert.gif'/><b> No Records Found to Display!</b>");
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
        </div>
<%-- Process butttons  start --%>
              <%
            // out.println(session.getAttribute(AppConstants.SES_ROLE_ID));
             
             if((session.getAttribute(AppConstants.SES_ROLE_ID).equals("100")||session.getAttribute(AppConstants.SES_ROLE_ID).equals("104")) && list.size()!= 0) {
          %>
          <table align="right">
                <tr>
             <td style="background-color: white;">
                <strong><input type="button" value="ReTransmit" class="button" onmouseover="Tip('Click here to ReTransmit.')" onmouseout="UnTip()" onclick="return getProcesAsn(this,document.getElementById('sec_shipment_list').value);" id="pre"/></strong>
             </td>
                <td style="background-color: white;">
                 <strong><input type="button" value="ReSubmit" class="button" onmouseover="Tip('Click here to Resubmit.')" onmouseout="UnTip()" onclick="return getProcesAsn(this,document.getElementById('sec_shipment_list').value);" id="post"/></strong>
               </td>

                     <td style="background-color: white;">
        <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('shipment','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/></strong>
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
		</div> --%>
        </div> 
  </div>
        <footer> 
         <p><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></p>
	  </footer>
    </body>
</html>