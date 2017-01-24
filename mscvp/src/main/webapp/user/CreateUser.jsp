<%-- 
    Document   : PurchaseOrder
    Created on : Mar 11, 2013, 10:59:42 AM
    Author     : Nagiredddy Seerapu
--%>

<%@page import="com.mss.ediscv.tp.TpBean"%>
<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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
         
         <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-1.9.1.js"></s:url>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-ui.js"/>'></script>
         <%-- <script language="JavaScript"
        src='<s:url value="/includes/js/DateValidation.js"/>'></script> --%>
        <script language="JavaScript"
        src='<s:url value="/includes/js/GridNavigation.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/generalValidations.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/dhtmlxcalendar.js"/>'></script>
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

  function getDetails(val){  
    getPoDetails(val);
  }
  function getList()
  {
     // alert('hello');
     // tradingPartnerForm.action =  "../tp/getTpList.action";
      //tradingPartnerForm.submit();
  }
function checkUserValues() {
    
    
    var fname = document.getElementById("fname").value;
    var lname = document.getElementById("lname").value;
    var email = document.getElementById("email").value;
    var ophno = document.getElementById("ophno").value;
    var status = document.getElementById("status").value;
    var role = document.getElementById("role").value;
    var deptId = document.getElementById("deptId").value;
    
    //if(((fname!=null)&&(fname!=""))&&((lname!=null)&&(lname!=""))&&((email!=null)&&(email!=""))&&((ophno!=null)&&(ophno!="")))
    if((fname==null)||(fname=="")) {
      alert("Please enter First Name!");
            return false;
    }
    if((lname==null)||(lname=="")) {
      alert("Please enter Last Name!");
            return false;
    }
     if((email==null)||(email=="")) {
      alert("Please enter Email!");
            return false;
    }
     if((ophno==null)||(ophno=="")) {
      alert("Please enter Office Number!");
            return false;
    }
     if((status==null)||(status=="-1")) {
      alert("Please select status!");
            return false;
    }
     if((role==null)||(role=="-1")) {
      alert("Please select role!");
            return false;
    }
     if((deptId==null)||(deptId=="-1")) {
      alert("Please select Department!");
            return false;
    }
    else
        {
          return true;
        }
}

function resetvalues()
{
    document.getElementById('fname').value="";
    document.getElementById('lname').value="";
    document.getElementById('email').value="";
    document.getElementById('ophno').value="";
      document.getElementById('status').value="-1";
        document.getElementById('role').value="-1";
          document.getElementById('deptId').value="-1";
    //$('#detail_box').hide();
    //$('#gridDiv').hide();
   
}

function getContact(detail)
{
   // alert("in contact"+detail);
    
     $(function() {
       
           $('#detail_box').show();
           return false;
               
   });
   var details = detail;
    var detailsDIV=document.getElementById("detailInformation");
	detailsDIV.innerHTML="";
	detailsDIV.innerHTML=details;
   
   
}
function getUserList(){
   // var tpid = document.getElementById("tpid").value;
   // var tpname = document.getElementById("tpname").value;
    location.href = "../user/backToSearchList";
     return true;
}
function init() {
    if(document.getElementById("userPageId").value != '0' ) {
    document.getElementById("email").readOnly = true;
}
}
</script>

 
    </head>
    <body onload="setStyle('mainTp','createUser');init();">
        <div id="wrapper">
       <div id="main">
    <header>
      <div id="logo">
         <s:include value="/includes/template/head.jsp"/>    
      
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
       
         <s:include value="/includes/template/msscvpAdminMenu.jsp"/>
        </ul> 
      </nav>
    </header>
    <div id="site_content">
      <div id="sidebar_container">
	  
	  
	   <div id="detail_box" style="display: none;"> 
        <div class="sidebar">
          <h3>Detail Information</h3>
          <div class="sidebar_item">
              
              
                <h5 id="detailInformation"></h5>
                
          </div>
		  </div>
		  
		  
          <div class="sidebar_base"></div>
        </div>
       
        
      </div>
     <s:hidden name="userPageId" value="%{userPageId}" id="userPageId"/>
      <div class="content" id="gridDiv">
        <div class="content_item">
            <%
           // out.println("pageId-->"+request.getAttribute("tppageId").toString());
            if(request.getAttribute("userPageId").toString().equals("0"))         {                       
            %>
         <h3>User Creation</h3>    
         <%}else{%>
         <h3>Update User</h3> 
         <%}%>
         <%
         if(request.getAttribute("resultMessage")!=null)
          {
             String reqponseString = request.getAttribute("resultMessage").toString();
                 out.println(reqponseString);
          }
                 %>
		<div style="alignment-adjust:central;" >
                    <%String contextPath = request.getContextPath();
                    %>
               <table >
		<tbody >
                    <%-- ../user/createUser.action--%>
                    <s:form action="%{currentAction}" method="post" name="userForm" id="userForm" theme="simple">
                     <s:hidden name="id" value="%{id}"/>
                        <tr>
                            <td class="lableLeft">First&nbsp;Name <font color="red">*</font></td>
                            <td>
                               <%-- <input type="text" class="inputStyle" name="loginId" id="loginId" onkeypress="return handleEnter(this,event);" tabindex="1" /> --%>
                               <s:textfield cssClass="inputStyle" name="fname" id="fname" tabindex="1" maxLength="50" value="%{fname}"/>
                            </td>
                            
                            <td class="lableLeft">Last&nbsp;Name<font color="red">*</font></td>
				<td>
                                
                                    <s:textfield cssClass="inputStyle" name="lname" id="lname" tabindex="2" maxLength="50" value="%{lname}"/>
				</td>
                            
			</tr>
                        
                        <tr>
                            <td class="lableLeft">Email <font color="red">*</font></td>
                            <td>
                              
                               <s:textfield cssClass="inputStyle" name="email" id="email" tabindex="3" maxLength="100" value="%{email}" onblur="return validateEmail();"/>
                            </td>
                            
                            <td class="lableLeft">Office&nbsp;Phone<font color="red">*</font></td>
				<td>
                                
                                    <s:textfield cssClass="inputStyle" name="ophno" id="ophno" onchange="return formatPhone(this);" onblur="return validatenumber(this);" tabindex="4" maxLength="14" value="%{ophno}"/>
				</td>
                            
			</tr>
                           <tr>
                            <td class="lableLeft">Status <font color="red">*</font></td>
                            <td>
                              
                                <s:select cssClass="inputStyle" list="#@java.util.LinkedHashMap@{'A':'Active','I':'Inactive','T':'Terminated'}" headerKey="-1" headerValue="Select Status" name="status" id="status" tabindex="5" cssStyle="width : 150px" value="%{status}"/>
                            </td>
                            
                            <td class="lableLeft">Role<font color="red">*</font></td>
				<td>
                                <s:select cssClass="inputStyle" list="userRolesMap" headerKey="-1" headerValue="Select Role" name="role" id="role" tabindex="6" cssStyle="width : 150px" value="%{role}"/>
                                   <%--  <s:select cssClass="inputStyle" list="#@java.util.LinkedHashMap@{'100':'Admin','101':'Compliance','102':'Logistics','103':'Credit','104':'Developer'}" headerKey="-1" headerValue="Select Role" name="role" id="role" tabindex="6" cssStyle="width : 150px" value="%{role}"/>  --%>
				</td>
                            
			</tr>
                        <tr>
                           <td class="lableLeft">Department <font color="red">*</font></td>
                            <td>
                              
                                <s:select cssClass="inputStyle" list=" #@java.util.LinkedHashMap@{'1':'Management','2':'Operations','3':'Developement'}" headerKey="-1" headerValue="Select Department" name="deptId" id="deptId" tabindex="7" cssStyle="width : 150px" value="%{deptId}"/>
                            </td> 
                        </tr>
			
                        <tr>
                            <td style="background-color: white;" colspan="3">
                                <%
                               // out.println("pageId-->"+request.getAttribute("tppageId").toString());
                                if(request.getAttribute("userPageId").toString().equals("0"))         {                       
                                %>
                                <s:submit value="Create" cssClass="button" onclick="return checkUserValues();" tabindex="8"/>
                                <strong><input type="button" value="Reset" class="button" tabindex="9" onclick="return resetvalues();"/></strong>
                            <%}else {%>
                            <s:submit value="Update" cssClass="button" onclick="return checkUserValues();" tabindex="8"/> 
                                    
                                    <strong><input type="button" value="BackToList" class="button" onclick="return getUserList();" tabindex="9"/></strong>
                           <%}%>
                            </td>
                        </tr>
                       

                </s:form>
                </tbody>
                    </table>
            </div>
        </div>
      </div>

               
                
    
          
       </div> 
      
      
       <div id="footer">
         <h2><font color="white">? 2013 Miracle Software Systems, Inc. All rights reserved</font></h2>
		</div>
        </div>        
   
  </div>  
    
   
    </body>
</html>
