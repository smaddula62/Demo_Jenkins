<%-- 
    Document   : login
    Created on : Mar 11, 2013, 1:01:15 PM
    Author     : Nagireddy Seerapu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.mss.ediscv.util.AppConstants" %>
<!DOCTYPE html>
<html>
    <head>
         <title>Miracle Supply Chain Visibility portal</title>
       <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />

        <link rel="stylesheet" href='<s:url value="/includes/css/style.css"/>'
			type="text/css">
        <link rel="stylesheet" href='<s:url value="/includes/css/jquery-ui.css"/>'
			type="text/css">
       
        <link rel="stylesheet" href='<s:url value="/includes/css/footerstyle.css"/>'
              type="text/css"/>
        
         <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-1.9.1.js"></s:url>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/jquery-ui.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
    </head>
    <body>
        <div id="wrapper">
       <div id="main">
    <header>
      <div id="logo">
      <%--  <div id="logo_text">
          <!-- class="logo_colour", allows you to change the colour of the text -->
          <h1><a href="<s:url action="index"/>">Miracle Supply Chain Visiblity Portal</a></h1>
        </div>  --%>
       <s:include value="/includes/template/head.jsp"/>  
      
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
	  <li class="current"><a href="#">Login</a></li>
         <%-- <li><a href="<s:url action="../general/index"/>">Home</a></li> --%>
        </ul>
      </nav>
    </header>
    <div id="site_content">
      <div id="sidebar_container">
	  
	  
	   <div id="detail_box" style="display: none;"> 
        <div class="sidebar">
          <h3>Detail Information</h3>
          <div class="sidebar_item">
            <h4>Detail 1</h4>
            <h5>Detail 2</h5>
             <h5>Detail 2</h5>
          </div>
		  </div>
		  
		  
          <div class="sidebar_base"></div>
        </div>
       
        
      </div>
     
      <div class="content">
        <div class="content_item">
         <h3>Login Page</h3>
		<div &nbsp; style="alignment-adjust:central;" >
                    
		 <%if (request.getAttribute(AppConstants.REQ_RESULT_MSG) != null) 
                out.println(request.getAttribute(AppConstants.REQ_RESULT_MSG).toString());%>
                
           <table >
		<tbody >
		<s:form action="authdownloadUser.action?scheduleRefId=%{scheduleRefId}"  method="post" name="userLoginForm" id="userLoginForm" theme="simple">
                 <tr>
                     <td class="lableLeft">User ID </td>
				<td>
                                   <%-- <input type="text"  class="inputStyle" name="loginId" id="loginId" onkeypress="return handleEnter(this,event);" tabindex="1" /> --%>
                                   <s:textfield cssClass="passwordStyle" name="loginId" id="loginId" onkeypress="return handleEnter(this,event);" tabindex="1"/>
                                </td>
			</tr>
			<tr>
	                <td class="lableLeft">Password</td>
				<td>
                                  <%--  <input type="password" name="password" id="password" class="inputStyle" tabindex="2" /> --%>
                                    <s:password cssClass="passwordStyle" name="password" id="password" tabindex="2"/>
				</td>
				</tr>
			
                        <tr>
                            <td style="background-color: white;">
                                <s:submit value="sign In" cssClass="button"/>
                            </td>
                        </tr>
                       

                </s:form>
		</tbody>
		
	</table>
            </div>
             
		 
		 
        </div>
      </div>
	  
	  
	  
	 <div id="sec_box" style="display: none;"> 
	  <div class="content">
        <div class="content_item" >
         
	<div class="CSSTableGenerator" >	



<table  border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
  <tr align="center">
    
    <td class="tbd">Sender ID</td>
  <td class="tbd" >Sender Name</td>
	<td class="tbd"> Date </td>
	<td class="tbd"> Check Amount</td>
	<td class="tbd"> Check Number</td>
  </tr>
  <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </tr>
 <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </tr>
   <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </tr>
   <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </tr>
   <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </tr>
   <tr>
   <td><a href="#" id="detail_link" >1001</></td>
   <td class="tbd">ABCD</td>
   <td class="tbd">March 7th</td>
   <td class="tbd">$300</td>
   <td class="tbd">0300</td>
  </table>
</div> 
</div>	 
        </div>
      </div>
    </div>
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
