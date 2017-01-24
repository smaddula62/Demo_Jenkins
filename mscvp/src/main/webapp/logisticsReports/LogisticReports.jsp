<%-- 
    Document   : DocRepository
    Created on : Mar 11, 2013, 10:03:37 AM
    Author     : Nagireddy seerapu 
--%>
<%@page import="com.mss.ediscv.logisticreports.LogisticReportsBean"%>
<%@page import="com.mss.ediscv.logisticsdoc.LogisticsDocBean"%>
<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> --%>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/tlds/dbgrid.tld" prefix="grd"%>
<%@ page import="com.freeware.gridtag.*"%>
<%@page import="java.sql.Connection"%>
<%@  page import="com.mss.ediscv.util.AppConstants"%>
<%@ page import="com.mss.ediscv.util.ConnectionProvider"%>
<%@ page import="java.sql.SQLException"%>

<!DOCTYPE html>
<html class=" js canvas canvastext geolocation crosswindowmessaging no-websqldatabase indexeddb hashchange historymanagement draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms no-csstransforms3d csstransitions  video audio localstorage sessionstorage webworkers applicationcache svg smil svgclippaths   fontface">
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
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
  
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        
         <script language="JavaScript"
        src='<s:url value="/includes/js/dbgridDisplay.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
         <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/downloadAjax.js"/>'></script>
        <%--   <script language="JavaScript"
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>  --%>
  <script>
                 var myCalendar;
		function doOnLoad() {
                        myCalendar = new dhtmlXCalendarObject(["docdatepickerfrom","docdatepicker"]);
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
   
      // New function to show the left grid

  function demo(){ 
    $(function() {
       
           $('#detail_box').show();
           return false;          
   });
  
  }
  
   function getDetails(val,ponum){
  //  alert("hiiii");    
   
   getLogisticsDocDetails(val,ponum);
 }
    function checkDates()
{
    
     var docdatepickerfrom = document.getElementById('docdatepickerfrom').value;
     var docdatepicker = document.getElementById('docdatepicker').value;
     if(docdatepickerfrom == "" && docdatepicker == ""){
                 alert("Please enter Date From and Date To");
                return false;
      }
      var res = compareDates(docdatepickerfrom,docdatepicker);
     
     return res;
}
  function resetvalues()
{
    document.getElementById('docdatepickerfrom').value="";
    document.getElementById('docdatepicker').value="";
    document.getElementById('docSenderId').value="";
    document.getElementById('docSenderName').value="";
    document.getElementById('docBusId').value="";
    document.getElementById('docRecName').value="";
    //document.getElementById('docIsa').value="";
    document.getElementById('corrattribute').value="-1"; 
    document.getElementById('corrvalue').value=""; 
    document.getElementById('docType').value="-1"; 
     document.getElementById('corrattribute1').value="-1"; 
    document.getElementById('corrvalue1').value=""; 
     document.getElementById('corrattribute2').value="-1"; 
    document.getElementById('corrvalue2').value=""; 
    
    document.getElementById('status').value="-1"; 
    document.getElementById('ackStatus').value="-1"; 

    $('#detail_box').hide();
    $('#gridDiv').hide();
    
}
 /* $(document).ready(function() {
      $('ul.sf-menu').sooperfish();
    });*/
</script>
  

  
  
</head>
<%
String check = null;
if(request.getAttribute("check")!=null)
  check = request.getAttribute("check").toString();

//System.out.println("check-->"+check);
    %>
<body onload="doOnLoad();setStyle('logisticCurrId','logisticreportsId');">
    <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
    <div id="wrapper">
  <div id="main">
    <header>
      <div id="logo">
           <s:include value="/includes/template/head.jsp"/>       

<gcse:search></gcse:search>
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
         <h3>Search Logistic Reports</h3>

		<div &nbsp; style="alignment-adjust:central;" >
                   <%String contextPath = request.getContextPath();
                    %>
               <table >
		<tbody >
		  <s:form action="../logisticsReports/logisticreportsSearch.action" method="post" name="documentForm" id="documentForm" theme="simple">
                      
                       <tr>
                           <td class="lableLeft">Date From </td>
				<td><%-- <input type="text" id="datepickerfrom" /> --%>
                                  <%--  <input type="text" name="datepickerfrom" id="datepickerfrom" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="docdatepickerfrom" id="docdatepickerfrom"  value="%{docdatepickerfrom}" tabindex="1"  onkeypress="return enterDateRepository();"/>
                                  <a href="javascript:copyValuTo('docdatepickerfrom','docdatepicker');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
		        <td class="lableLeft">Date To </td>
				<td><%-- <input type="text" id="datepicker" /> --%>
                                  <%--  <input type="text" name="datepicker" id="datepicker" class="inputStyle" tabindex="2" />  --%>
                                  <s:textfield cssClass="inputStyle" name="docdatepicker"  value="%{docdatepicker}" id="docdatepicker" tabindex="2"  onkeypress="return enterDateRepository();"/>
				</td>
				
			</tr>
			<tr>
				<td class="lableLeft">Sender Id</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="senderId" id="senderId" class="inputStyle" tabindex="2" /> --%>
                                   <s:textfield cssClass="inputStyle" name="docSenderId" id="docSenderId" value="%{docSenderId}" tabindex="3"/>
				</td>
                                <td class="lableLeft">Sender Name</td>
				<td><%-- <input type="text"> --%>
                                  <%--  <input type="text" name="senderName" id="senderName" class="inputStyle" tabindex="2" /> --%>
                                  <s:textfield cssClass="inputStyle" name="docSenderName" id="docSenderName" value="%{docSenderName}" tabindex="4"/>
				</td>
                        </tr>
			<tr>
				<td class="lableLeft">Receiver Id</td>
				<td><%-- <input type="text"> --%>
                                    <%--<input type="text" name="buId" id="buId" class="inputStyle" tabindex="2" /> --%>
                                    <s:textfield cssClass="inputStyle" name="docBusId" id="docBusId" value="%{docBusId}" tabindex="5"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td><%-- <input type="text"> --%>
                                   <%-- <input type="text" name="recName" id="recName" class="inputStyle" tabindex="2" /> --%>
                                   <s:textfield cssClass="inputStyle" name="docRecName" id="docRecName" value="%{docRecName}" tabindex="6"/>
				</td>
				</tr>
             

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
                  
                     <tr>  <%-- return compareDates(document.getElementById('docdatepickerfrom').value,document.getElementById('docdatepicker').value); --%>
                         <td style="background-color: white;" colspan="2">
                                <s:submit value="Search" cssClass="button" tabindex="12"/>
                           <%-- </td>
                            <td style="background-color: white;">--%>
                               <%--  <s:reset value="Reset" cssClass="button"/> --%>
                               <strong><input type="button" value="Reset" class="button" tabindex="13" onclick="return resetvalues();"/></strong>
                            </td>
                            <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                        </tr>
             </tbody>
             
          </s:form>
				
		
	</table> 
	 
            </div>
                    <%--  out.print("contextPath-->"+contextPath); --%>
    
               
        </div>
            <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
      </div>
	  
	  
	 <s:if test="#session.logdocumentList != null"> 
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
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_LOG_DOC_LIST);
             
            if(list.size()!=0){
             LogisticReportsBean logisticsReportBean;
             %>
             <tr>

               <td >FileFormat</td> 
               <td >InstanceId</td>
               <td >Partner</td>
               
               <td >DateTime</td>
               <%-- <td >ISA #</td>  --%>
                
                <%-- <td >DOC_ORIGIN</td> --%>
                <td >TransType</td>
                <td >Direction</td>
                
                <td >Status</td>
               <%-- <td >ACK_STATUS</td>  --%>
                <td >Reprocess</td>
                <td >FileName </td>
            </tr>
            <tr>
             
             <%
            for (int i = 0; i < list.size(); i++) {
            logisticsReportBean = (LogisticReportsBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
              <td>
                   <%
            out.println(logisticsReportBean.getFile_type());
            %>
                    
                </td>
                <td><%--<a href="javascript:getDetails('<%=logisticsReportBean.getFile_id()%>','<%=logisticsReportBean.getPoNumber()%>');"> --%>
                    <%
                    out.println(logisticsReportBean.getFile_id());
                    %>
                   <%-- </a> --%>
            </td>
           <td>
                   <%
                   
                       out.println(logisticsReportBean.getPname());
                       
                   
           
           %>
                 
          </td>
            
                
          <td>
                    <%
            out.println(logisticsReportBean.getDate_time_rec().toString().substring(0, logisticsReportBean.getDate_time_rec().toString().lastIndexOf(":")));
            %>
                   
           </td>   
                <td>
                   <%
            out.println(logisticsReportBean.getTransaction_type());
            %>
                    
                </td>
               <td>
                   <%
           out.println(logisticsReportBean.getDirection());
           %>
                 
          </td>  
          
           
           <td>
           <%
              if(logisticsReportBean.getStatus().equalsIgnoreCase("ERROR")){       
                         out.println("<font color='red'>"+logisticsReportBean.getStatus()+"</font>");
                     }else if(logisticsReportBean.getStatus().equalsIgnoreCase("SUCCESS")){
                         out.println("<font color='green'>"+logisticsReportBean.getStatus()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+logisticsReportBean.getStatus()+"</font>");
                       }
                       
           %>
                 
          </td>
       <%--   <td>
                  <%
                  
                    //out.println(logisticsDocBean.getAckStatus());
                    if(logisticsDocBean.getAckStatus().equalsIgnoreCase("REJECTED")){       
                         out.println("<font color='red'>"+logisticsDocBean.getAckStatus()+"</font>");
                     }else if(logisticsDocBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+logisticsDocBean.getAckStatus()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+logisticsDocBean.getAckStatus()+"</font>");
                       }
                                      
                    %>
                </td> --%>
            <td>
                   <%
           //out.println(logisticsDocBean.getReProcessStatus());
           if(logisticsReportBean.getReProcessStatus()!=null){
                       out.println(logisticsReportBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
           %>
                 
          </td>
            <td style="word-break:break-all;">

                   <%
           //out.println(logisticsDocBean.getReProcessStatus());
           if(logisticsReportBean.getFile_name()!=null){
                       out.println(logisticsReportBean.getFile_name().toUpperCase());
                       
                   }else {
                        out.println("");
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
            <tr >
                <td align="right" colspan="28" style="background-color: white;">
                    <div align="right" id="pageNavPosition">hello</div>
             </td>
            </tr> 
            <% }%>
</table>

 </div>
        <%-- Process butttons  start --%>
             <%
            if(list.size()!=0){
                %>
      <table align="right">
                <tr>
                     <td style="background-color: white;">
        <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('logisticsReport','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/></strong>
        </td>
                </tr>
            </table> 
      <%}%>
            <%-- process buttons end--%>
<%-- Grid End --%>
           
          </div>
       </s:if> 
    
          
       </div> 
        <script type="text/javascript">
        var pager = new Pager('results', 10); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    	</script>
                    </div>
               </div>
          </div> 
                <%--    <div id="footer">  --%>
             <footer> 
         <p><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></p>
	  </footer>   
    <%--   	</div> --%>
         
    </body>
</html>
