<%-- 
    Document   : PurchaseOrder
    Created on : Mar 11, 2013, 10:59:42 AM
    Author     : Nagiredddy Seerapu
--%>

<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page buffer="50kb" autoFlush="true" %>
<%@page import="com.mss.ediscv.po.PurchaseOrderBean"%>
<%@page import="com.mss.ediscv.util.AppConstants"%>


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
         
      <%--   <link rel="stylesheet" href='<s:url value="/includes/css/tooltip.css"/>'
              type="text/css"/>  --%>
         
         <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-1.9.1.js"></s:url>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-ui.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/DateValidation.js"/>'></script> 
        <script language="JavaScript"
        src='<s:url value="/includes/js/GridNavigation.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/dbgridDisplay.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/downloadAjax.js"/>'></script>
        
        <%-- <script language="JavaScript"
        src='<s:url value="/includes/js/tooltip.js"/>'></script> --%>
        
        <script>
        
 /* $(function() {
    $( "#podatepicker" ).datepicker();
	 $( "#podatepickerfrom" ).datepicker();
  });
*/
var myCalendar;
		function doOnLoad() {
                        myCalendar = new dhtmlXCalendarObject(["podatepickerfrom","podatepicker"]);
                        myCalendar.setSkin('omega');
			myCalendar.setDateFormat("%m/%d/%Y");
                        //myCalendar.setDateFormat("%m/%d/%Y %H:%i");
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
      // New function to show the left grid

  function getDetails(val,val1){  
    //  alert("in jsp --->"+val+"-------"+val1);
    getPoDetails(val,val1);
  }
   function getLifeCycle(checkList) {
    var res = false;
    var i = 0;
    var po_Num = "";
    for(var j=0; j<checkList;j++) {
            if(checkList==1)
                {
                    res = document.purchaseForm.check_List.checked;
                    
                }else
                    {
                        res = document.purchaseForm.check_List[j].checked;
                    }
             
             if(res == true) {
                 i = i + 1;
                 if(i==1){
                   po_Num =   po_Num + document.getElementById("text"+j).value;  
                 }
             }
            }
     if(i != 1) {
         alert("Please select one checkbox to get LifeCycle of PO !!");
         return false;    
    }
 else {
     //alert(po_Num);
     //window.location = "../lfc/lifeCycle.action?poNumber="+po_Num; 
     //purchaseForm.action = "../lfc/lifeCycle.action?poNumber="+po_Num;
     location.href = "../lfc/lifecycle.action?poNumber="+po_Num;
     return true;
 }
}
</script>

 <script >  
 function resetvalues()
{
    //document.getElementById('poNumber').value="";
    document.getElementById('podatepickerfrom').value="";
    document.getElementById('podatepicker').value="";
    document.getElementById('poSenderId').value="-1";
    document.getElementById('poSenderName').value="-1";
    document.getElementById('poRecId').value="-1";
    document.getElementById('poRecName').value="-1";  
     document.getElementById('sampleValue').value="1";
     //document.getElementById('ackStatus').value="-1";
      document.purchaseForm.ackStatus.value="-1"; 
    // document.getElementById('gsNumber').value="";
     document.getElementById('status').value="-1";
     
     document.getElementById('corrattribute').value="-1"; 
    document.getElementById('corrvalue').value=""; 
    document.getElementById('docType').value="-1"; 
     document.getElementById('corrattribute1').value="-1"; 
    document.getElementById('corrvalue1').value=""; 
     document.getElementById('corrattribute2').value="-1"; 
    document.getElementById('corrvalue2').value=""; 
  //  document.getElementById('docType').value="-1"; 
     
     $('#detail_box').hide();
    $('#gridDiv').hide();
}
function enterDate()
{
    alert("Please select from the Calender !");
    document.getElementById('podatepickerfrom').value='';
}


function checkCorrelation() {
     
  //   alert("hiii");
     var corrattr = document.getElementById('corrattribute').value;
     var corrval = document.getElementById('corrvalue').value;
     
       var corrattr1 = document.getElementById('corrattribute1').value;
     var corrval1 = document.getElementById('corrvalue1').value;
     
       var corrattr2 = document.getElementById('corrattribute2').value;
     var corrval2 = document.getElementById('corrvalue2').value;
     
  
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
     
     if((corrattr2!="-1")&&(corrval2=="")) {
         alert("please enter Correlation Value!!!");
         return false;
     }
     if((corrattr2=="-1")&&(corrval2!="")) {
         alert("please select Correlation!");
         return false;
     }
     
     var res = Formvalidation(document.getElementById('podatepickerfrom').value,document.getElementById('podatepicker').value);
      
    
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
  <%-- <body onload="doOnLoad();initDate('podatepickerfrom','podatepicker','<%=check %>');setStyle('mainOm','omCurrId');">  --%>
  <body onload="doOnLoad();setStyle('mainOm','omCurrId');">
       
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
         <h3>Search By PO</h3>
		<div &nbsp; style="alignment-adjust:central;" >
                    <%String contextPath = request.getContextPath();
                     
                    %>
               <table >
		<tbody >
		<s:form action="../po/poSearch" method="post" name="purchaseForm" id="purchaseForm" theme="simple">
			<tr>
                            <td class="lableLeft">Date From </td>
				<td>
                                   <%-- <input type="text" id="datepickerfrom" /> --%>
                                   <s:textfield cssClass="inputStyle" name="poDateFrom" value="%{poDateFrom}" id="podatepickerfrom" tabindex="1"  onkeypress="return enterManufacturingDatePurchase();" />
                                   <a href="javascript:copyValuTo('podatepickerfrom','podatepicker');"><img border="0" src="<%= contextPath%>/includes/images/lm/arrow.gif" width="7"
												height="9"></a>
				</td>
				
			<td class="lableLeft">Date To </td>
				<td>
                                    <%--<input type="text" id="datepicker" /> --%>
                                    <s:textfield cssClass="inputStyle" name="poDateTo"  value="%{poDateTo}" id="podatepicker" tabindex="2" onkeypress="return enterManufacturingDatePurchase();"/>
				</td>
				
			</tr>
                        <tr>
			<td class="lableLeft">Sender Id</td>
				<td>
                                    
                                <s:select headerKey="-1" headerValue="Select Type" list="senderIdList" name="poSenderId" id="poSenderId" value = "%{poSenderId}" tabindex="3" cssStyle="width : 150px" />
				</td>
				<td class="lableLeft">Sender Name</td>
				<td>
                                   
                                   <s:select headerKey="-1" headerValue="Select Type" list="senderNameList" name="poSenderName" id="poSenderName" value = "%{poSenderName}" tabindex="4" cssStyle="width : 150px"/>
				</td>
				</tr>
			<tr>
				<td class="lableLeft">Receiver Id</td>
				<td>
                                    
                               <s:select headerKey="-1" headerValue="Select Type" list="receiverIdList" name="poRecId" id="poRecId" value = "%{poRecId}" tabindex="5" cssStyle="width : 150px"/>
				</td>
				<td class="lableLeft">Receiver Name</td>
				<td>
                                    
                                <s:select headerKey="-1" headerValue="Select Type" list="receiverNameList" name="poRecName" id="poRecName" value = "%{poRecName}" tabindex="6" cssStyle="width : 150px"/>
				</td>
				</tr>
			
                                <%-- New search functionality based on correlation --%>
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
                                   	<s:select headerKey="-1" headerValue="Select Attribute" list="correlationList" name="corrattribute1" id="corrattribute1" value="%{corrattribute1}" tabindex="9" cssStyle="width : 150px"/>
                                    
                                </td>
                                <td class="lableLeft">Value </td>
                               <td> 
                                    <s:textfield cssClass="inputStyle" name="corrvalue1" id="corrvalue1" value="%{corrvalue1}" tabindex="10"/>
                                </td>
                                
                            </tr>
                            <tr>
                               <td class="lableLeft">Correlation </td>
                               <td> 
                                   	<s:select headerKey="-1" headerValue="Select Attribute" list="correlationList" name="corrattribute2" id="corrattribute2" value="%{corrattribute2}" tabindex="11" cssStyle="width : 150px"/>
                                    
                                </td>
                                <td class="lableLeft">Value </td>
                               <td> 
                                    <s:textfield cssClass="inputStyle" name="corrvalue2" id="corrvalue2" value="%{corrvalue2}" tabindex="12"/>
                                </td>
                                
                            </tr>
                                
                               
                            <%-- end 0f new search --%>
                              <tr>
                                    <td class="lableLeft">Document Type</td>
                              <td class="lableLeft">
                                  <s:select headerKey="-1" headerValue="Select Type" list="docTypeList" name="docType" id="docType" value="%{docType}" tabindex="13" cssStyle="width : 150px"/>
                               </td> 
                               <td class="lableLeft">Status</td>
                                <td class="lableLeft">
                                 <s:select headerKey="-1" headerValue="Select Type" list="{'Success','Error','Warning'}" name="status" id="status" value="%{status}" tabindex="14" cssStyle="width : 150px"/> 
                              </td>
                                
                                </tr>
                                <tr>
                                <td class="lableLeft">Ack Status 
                                </td>
				<td>
                                  <s:select headerKey="-1" headerValue="Select Type" list="{'Overdue','Accepted','Rejected'}" name="ackStatus" id="ackStatus" value="%{ackStatus}" tabindex="15" cssStyle="width : 150px"/> 
				</td>
			        </tr>  
        
        <tr>
                        <td style="background-color: white;">
                           
                        <strong>  <s:submit value="Search" cssClass="button" tabindex="16" onclick="return checkCorrelation();"/></strong>
                      <%--  </td>
                        <td style="background-color: white;"> --%>
                        <strong><input type="button" value="Reset" class="button" tabindex="17" onclick="return resetvalues();"/></strong>
                        </td>
                        <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                        </tr>
         </tbody>
                    </table>
                        
        
        <%--  </s:form>   --%>
	
	
            </div>
        </div>
        <a><img src='../includes/images/dtp/cal_plus.gif' alt="nag"width="13" height="9" border="0" onclick="javascript:hideSearch()" id="fsCollImg"/></a>  
      </div>
         <s:if test="#session.poSearchList != null"> 
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
             java.util.List list = (java.util.List) session.getAttribute(AppConstants.SES_PO_LIST);
             
            if(list.size()!=0){
             PurchaseOrderBean purchaseOrderBean;
             %>
              <input type="hidden" name="sec_po_list" id="sec_po_list" value="<%=list.size()%>"/> 
             
             
             <tr>
               <td >InstanceId</td>
               <td >PO #</td>
                <td>DateTime</td> 
               <%--   <td >SO #</td> --%>
                 <%--  <td >SAP IDOC</td>
                <td>Item Qty</td> --%>
                <td>Partner</td>
               <%--<td>ISA Control # </td>  --%>
               <%--<td>GS_Control #</td>  --%>
               <%-- <td>PO Status</td>  --%>
               <td>Direction</td>
               
               <td>Status</td> 
               <td>Ack&nbsp;Status</td>  
               <td>Reprocess</td> 
               
               <%
            // System.out.println("Session=========================="+session.getAttribute(AppConstants.SES_ROLE_ID).equals("100"));
             //System.out.println("Session=========================="+session.getAttribute(AppConstants.SES_ROLE_ID).equals("104"));
             if(session.getAttribute(AppConstants.SES_ROLE_ID).equals("100")||session.getAttribute(AppConstants.SES_ROLE_ID).equals("104")){
          %>
               <td>#</td>
            <%}%>
            </tr>
           
             
             <%
            for (int i = 0; i < list.size(); i++) {
            purchaseOrderBean = (PurchaseOrderBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
           <tr>
               <td>
                    <%
                    out.println(purchaseOrderBean.getFileId());
                    %>
                    <input type="hidden" name="Instance<%=i%>" id="Instance<%=i%>" value="<%=purchaseOrderBean.getFileId()%>"/>   
                    
                </td>
               <td><a href="javascript:getDetails('<%=purchaseOrderBean.getPo()%>','<%=purchaseOrderBean.getFileId()%>');"  >
                  <%
                    out.println(purchaseOrderBean.getPo());
                    %> 
                 
                <%--  <input type="hidden" name="text<%=i%>" id="text<%=i%>" value="<%=purchaseOrderBean.getPo()%>"/>  --%>
                  <input type="hidden" name="text<%=i%>" id="text<%=i%>" value="<%=purchaseOrderBean.getPo()%>"/>
                    </a>
                </td>
              <td>
                          <%
            out.println(purchaseOrderBean.getDate_time_rec().toString().substring(0, purchaseOrderBean.getDate_time_rec().toString().lastIndexOf(":")));
            %>
                 </td>  
                <td>
                    <%
            out.println(purchaseOrderBean.getPname());
            %>
                </td>
              <%--    <td>
                   <%
            out.println(purchaseOrderBean.getSapIdoc());
            %>
                    
                </td>--%>
              
             
               
               <%-- <td>
                    <%
            out.println(purchaseOrderBean.getIsaControl());
            %>
                </td>  --%>
                
               <%--  <td>
                    <%
            out.println(purchaseOrderBean.getGsControlNumber());
            %>
                </td>  --%>
                
             <%--<td>
                  <%
            out.println(purchaseOrderBean.getPoStatus());
            %>
                </td> --%>
                
                 <td>
                    <%
            out.println(purchaseOrderBean.getDirection().toUpperCase());
            %>
                </td>
              <td>
                    <%
                    
                    if(purchaseOrderBean.getStatus().equalsIgnoreCase("ERROR")){  
                        out.println("<font color='red'>"+purchaseOrderBean.getStatus().toUpperCase()+"</font>");
                       }else if(purchaseOrderBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+purchaseOrderBean.getStatus().toUpperCase()+"</font>");
                       }else {
                        out.println("<font color='orange'>"+purchaseOrderBean.getStatus().toUpperCase()+"</font>");
                       }
            %>
                </td>  
                <td>
                  <%
                  
                    //out.println(poLifeCycleBean.getAckStatus());
                    if(purchaseOrderBean.getAckStatus().equalsIgnoreCase("REJECTED")){       
                         out.println("<font color='red'>"+purchaseOrderBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(purchaseOrderBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+purchaseOrderBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+purchaseOrderBean.getAckStatus().toUpperCase()+"</font>");
                       }
                                      
                    %>
                </td>  
                
                <td>
                    <%
            //out.println(purchaseOrderBean.getReProcessStatus());
                   if(purchaseOrderBean.getReProcessStatus()!=null){
                       out.println(purchaseOrderBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
            
            %>
                </td>
                <% 
             if(session.getAttribute(AppConstants.SES_ROLE_ID).equals("100")||session.getAttribute(AppConstants.SES_ROLE_ID).equals("104")){      
          %>
            <td> &nbsp; &nbsp; 
                <input type = "checkbox" name ="check_List" id = "check_List" value="<%= purchaseOrderBean.getPo()%>"/>&nbsp; &nbsp;  
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
                <div align="right" id="pageNavPosition"></div>
               </td>
             </tr>
             <% } %>
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
                    <strong><input type="button" value="ReTransmit" class="button" onmouseover="Tip('Click here to ReTransmit.')" onmouseout="UnTip()" onclick="return getProces(this,document.getElementById('sec_po_list').value);" id="pre"/></strong>
                </td>
                <td style="background-color: white;">
                        <strong><input type="button" value="ReSubmit" class="button" onmouseover="Tip('Click here to Resubmit.')" onmouseout="UnTip()" onclick="return getProces(this,document.getElementById('sec_po_list').value);" id="post"/></strong>
               </td>
                 <td style="background-color: white;">
                       <strong><input type="button" value="LifeCycle" class="button" onmouseover="Tip('Click here to generate Life Cycle.')" onmouseout="UnTip()" onclick="return getLifeCycle(document.getElementById('sec_po_list').value);"/></strong>
                 </td> 
                 <td style="background-color: white;">
                <strong><input type="button" value="Generate Excel" class="button" onclick="return gridDownload('po','xls');" onmouseover="Tip('Click here to generate an excel Report.')" onmouseout="UnTip()" id="excel"/> </strong>  
               
                </td>
            </tr>
            </table> 
            <%}%>
            <%-- process buttons end--%>
<%-- Grid End --%>
           
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
