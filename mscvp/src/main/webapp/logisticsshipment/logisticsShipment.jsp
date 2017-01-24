<%-- 
    Document   : logisticsShipment
    Created on : Jun 27, 2013, 10:04:33 AM
    Author     : miracle
--%>


<%@page import="com.mss.ediscv.logisticsshipment.LtShipmentBean"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>




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
                        myCalendar = new dhtmlXCalendarObject(["datepickerfrom","datepickerTo"]);
                        myCalendar.setSkin('omega');
			myCalendar.setDateFormat("%m/%d/%Y %H:%i");
                       // myCalendar.hideTime();
		
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

/*function getDetails(val,ponum){
    getLogisticsShipmentDetails(val,ponum);
  }*/
  
  function getDetails(val,ponum,id){
      
  
  //alert("This is in js id"+id);    
   
   getLogisticsShipmentDetails(val,ponum,id);
 }
function resetvalues()
{
    document.getElementById('buId').value="";
    document.getElementById('datepickerfrom').value="";
    document.getElementById('datepickerTo').value="";
    document.getElementById('senderId').value="";
    document.getElementById('senderName').value="";
   // document.getElementById('asnNum').value="";
    document.getElementById('recName').value="";
   // document.getElementById('bolNum').value="";
    //document.getElementById('poNum').value="";   
     document.getElementById('sampleValue').value="1"; 
      document.getElementById('ackStatus').value="-1"; 
     document.getElementById('status').value="-1";
     document.getElementById('docType').value="-1"; 
     document.getElementById('corrattribute').value = "-1";
     document.getElementById('corrattribute1').value = "-1";
     document.getElementById('corrvalue').value = "";
     document.getElementById('corrvalue1').value = "";
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
     
    
     
     var res = Formvalidation(document.getElementById('datepickerfrom').value,document.getElementById('datepickerTo').value);
      
    
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
   <body onload="doOnLoad();setStyle('logisticsShip','mainShipment');"> 
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
       <div id="wrapper">
        <div id="main">
    <header>
      <div id="logo">
        
         <s:include value="/includes/template/head.jsp"/>    
      
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
	
        <s:include value="/includes/template/logisticsMenu.jsp"/>
         
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
         <h3>Search By Lt Shipments</h3>
		<div &nbsp; style="alignment-adjust:central;" >
                    <%String contextPath = request.getContextPath();
                    %>
           <table >
		<tbody >
                    <s:form action="../logisticsshipment/ltShipmentSearch.action" method="post" name="ltshipmentForm" id="ltshipmentForm" theme="simple">
		 <tr>
                     <td class="lableLeft">Date From </td>
				<td><%-- <input type="text" id="datepickerfrom" /> --%>
                                  <%--  <input type="text" name="datepickerfrom" id="datepickerfrom" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="datepickerfrom" value="%{datepickerfrom}" id="datepickerfrom" tabindex="1" onkeypress="return enterDateInvoice();"/>
                                  <a href="javascript:copyValuTo('datepickerfrom','datepickerTo');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
		        <td class="lableLeft">Date To </td>
				<td><%-- <input type="text" id="datepicker" /> --%>
                                  <%--  <input type="text" name="datepicker" id="datepicker" class="inputStyle" tabindex="2" />  --%>
                                  <s:textfield cssClass="inputStyle" name="datepickerTo" value="%{datepickerTo}" id="datepickerTo" tabindex="2" onkeypress="return enterDateInvoice();"/>
				</td>
				
			</tr>
			<tr>
				<td class="lableLeft">Sender Id</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="senderId" id="senderId" class="inputStyle" tabindex="2" /> --%>
                                   <s:textfield cssClass="inputStyle" name="senderId" id="senderId" value = "%{senderId}" tabindex="3"/>
				</td>
				<td class="lableLeft">Sender Name</td>
				<td><%-- <input type="text"> --%>
                                  <%--  <input type="text" name="senderName" id="senderName" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="senderName" id="senderName" value = "%{senderName}" tabindex="4"/>
				</td>
				</tr>
			<tr>
				<td class="lableLeft">Receiver ID</td>
				<td><%-- <input type="text"> --%>
                                    <%--<input type="text" name="buId" id="buId" class="inputStyle" tabindex="2" /> --%>
                                    <s:textfield cssClass="inputStyle" name="buId" id="buId" value = "%{buId}" tabindex="5"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="recName" id="recName" class="inputStyle" tabindex="2" /> --%>
                                   <s:textfield cssClass="inputStyle" name="recName" id="recName" value = "%{recName}" tabindex="6"/>
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
                        <%--    <tr>
                               <td class="lableLeft">Correlation </td>
                               <td> 
                                   	<s:select headerKey="-1" headerValue="Select Attribute" list="correlationList" name="corrattribute2" id="corrattribute2" value="%{corrattribute2}" tabindex="7" cssStyle="width : 150px"/>
                                    
                                </td>
                                <td class="lableLeft">Value </td>
                               <td> 
                                    <s:textfield cssClass="inputStyle" name="corrvalue2" id="corrvalue2" value="%{corrvalue2}" tabindex="8"/>
                                </td>
                                
                            </tr> --%>
                            <%-- New search --%>
                          
		    <tr>
				<td class="lableLeft">Document Type</td>
                              <td class="lableLeft">
                                <s:select headerKey="-1" headerValue="Select Type" list="docTypeList" name="docType" id="docType" value="%{docType}" tabindex="9" cssStyle="width : 150px"/>
                               </td> 
                               <td class="lableLeft">Status</td>
                              <td class="lableLeft">
                                 <s:select headerKey="-1" headerValue="Select Type" list="{'Success','Error','Warning'}" name="status" id="status" value="%{status}" tabindex="10" cssStyle="width : 150px"/> 
                              </td>
                             <%--   <td>  <s:select cssClass="inputStyle" headerKey="-1" headerValue="Select   Type" list="{'850', '860', '855', '856','810','820'}" name="docType" id="docType" tabindex="9" cssStyle="width : 150px"/></td>   --%>
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
           
                    </s:form>
                                         
            </div>
             
		 
		 
        </div>
           <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
      </div>
	  
	  <s:if test="#session.ltShipmentList != null">
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
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_LTSHIPMENT_LIST);
             
            if(list.size()!=0){
             
             LtShipmentBean ltShipmentBean;
             %>
             <tr>
               <td>InstanceId</td>
               <td >Shipment #</td>
               <td >Partner</td>  
               <td >Carrier Status</td>  
               <%-- <td>Ship Date</td> --%>
               <td>DateTime</td>
               <%--<td>ISA #</td> --%>
              <%-- <td>GS #</td>  --%>
               <%-- <td>ST #</td> --%>
               <td>Direction</td>
               <td>Status</td>
             
                <%--<td >PO #</td> --%>
                
                
                 
               
            </tr>
            <tr>
             
             <%
            for (int i = 0; i < list.size(); i++) {
            ltShipmentBean = (LtShipmentBean) list.get(i);

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
            out.println(ltShipmentBean.getInstanceId());
            %>
                </td>
                <%--<td>  <a href="javascript:getDetails('<%=ltShipmentBean.getAsnNum()%>','<%=ltShipmentBean.getPoNum()%>');">
                    <%
                    out.println(ltShipmentBean.getAsnNum());
                    %>
                   </a> 
                </td>--%>
                <td>  <a href="javascript:getDetails('<%=ltShipmentBean.getAsnNum()%>','<%=ltShipmentBean.getPoNum()%>','<%=ltShipmentBean.getId()%>');">
                    <%
                    out.println(ltShipmentBean.getAsnNum());
                    %>
                   </a> 
                </td>
                
               <td>
                    <%
            out.println(ltShipmentBean.getPartner());
            %>
                </td>
                <td>
                    <%
                    if(ltShipmentBean.getCarrierStatus()!=null){
                         if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("AA")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_pick_up appointment");
                       }
                         else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("AB")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Delivery appointment");
                       }
                          else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("X3")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Arrived at Pick_up Location");
                       }
                           else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("AF")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Departed from pick_up Location");
                       }
                            else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("X1")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Arrived at Delivery Location");
                       }
                             else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("D1")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Completed Unloading Delivery Location");
                       }
                              else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("CD")){
                        out.println(ltShipmentBean.getCarrierStatus()+"_Carrier Departed Delivery Location");
                       }
                                                 }
            %>
                </td>
               
                
                <%-- <td>
                    <%
            out.println(shipmentBean.getShipmentDate());
            %>
                  <%
              if(logisticsDocBean.getStatus().equalsIgnoreCase("ERROR")){       
                         out.println("<font color='red'>"+logisticsDocBean.getStatus()+"</font>");
                     }else if(logisticsDocBean.getStatus().equalsIgnoreCase("SUCCESS")){
                         out.println("<font color='green'>"+logisticsDocBean.getStatus()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+logisticsDocBean.getStatus()+"</font>");
                       }
                          if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("AA")){
            out.println("AA(Pick_up APPOINTMENT)");
                       }
            else if(ltShipmentBean.getCarrierStatus().equalsIgnoreCase("AB")){
                         out.println("<font color='green'>"+ltShipmentBean.getStatus()+"</font>");
                     } 
           %>
                </td>  --%>
                <td>
                    <%
          //  out.println(shipmentBean.getDate_time_rec());
           out.println(ltShipmentBean.getDateTime().toString().substring(0, ltShipmentBean.getDateTime().toString().lastIndexOf(":")));
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
            out.println(ltShipmentBean.getDirection().toUpperCase());
            %>
                </td>
                <td>
                    <%
                     if(ltShipmentBean.getStatus().equalsIgnoreCase("ERROR")){
                        out.println("<font color='red'>"+ltShipmentBean.getStatus().toUpperCase()+"</font>");
                       }else if(ltShipmentBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+ltShipmentBean.getStatus().toUpperCase()+"</font>");
                       }else {
                       out.println("<font color='orange'>"+ltShipmentBean.getStatus().toUpperCase()+"</font>");
                       }
            %>
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
            if(list.size()!=0){
                %>
          <table align="right">
                <tr>
                     <td style="background-color: white;">
        <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('ltShipment','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/></strong>
        </td>
                </tr>
            </table> 
          <%}%>
            <%-- process buttons end--%>
                </div>
               

</s:if>
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
