<%-- 
    Document   : retailer
    Created on : May 6, 2013, 7:46:13 AM
    Author     : miracle1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Miracle Supply Chain Visibility portal</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
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
    </head>
    <body>
    <div id="wrapper">
  <div id="main">
    <header>
      <div id="logo">
       
                <s:include value="/includes/template/head.jsp"/>         
     </div>
     
     <!-- nav start -->
      <nav>
         <ul class="sf-menu" id="nav">
	  Menu..
         <%--  <s:include value="/includes/template/orderToCashMenu.jsp"/>  --%>
        </ul>  
      </nav> 
        
        <!-- nav end -->
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
            <!-- heading for the content -->
         <h3>Retailer</h3>
          <!-- end of the heading tag -->
           <!-- content div start -->
          <div &nbsp; style="alignment-adjust:central;" >
		<%-- <img alt="Error Header" src="<s:url value="/includes/images/errorHeader_847x150.jpg"/>" width="700" height="120" border="0">--%>
                 <table >
		<tbody >
		 <tr>
                     <td valign="top"> 
		 <img alt="Error Header" src="<s:url value="/includes/images/under_construction.jpg"/>" width="700" height="250" border="0">
                      </td>
                 </tr>
                 
                
                </tbody>
              </table>

              
            </div>
             <!-- end of the conetnt -->
	</div>
      </div>
	  
	  
	  
	 <div id="sec_box" style="display: none;"> 
	  <div class="content">
        <div class="content_item">
         
		
<div class="CSSTableGenerator" >


<table >
  <tr align="center">
    <td >ISA #</td>
    <td >Document Type</td>
    <td >File Format</td>
    <td>Direction</td>
    <td>Date</td>
    <td>Status</td>
  </tr>
  <tr align="center">
    <td><a href="#" id="detail_link" >1001</a></td>
    <td>850</td>
    <td>EDI</td>
<td>Inbound</a></td>
<td>Feb 27 2013</td>
<td>Shipped</td>
 </tr>
  <tr align="center">
    <td><a href="#" id="detail_link" >1001</a></td>
    <td>850</td>
    <td>EDI</td>
<td>Inbound</a></td>
<td>Feb 27 2013</td>
<td>Shipped</td>
 </tr>
 
  <tr align="center">
    <td><a href="#" id="detail_link" >1001</a></td>
    <td>850</td>
    <td>EDI</td>
<td>Inbound</a></td>
<td>Feb 27 2013</td>
<td>Shipped</td>
 </tr>
  <tr align="center">
    <td><a href="#" id="detail_link" >1001</a></td>
    <td>850</td>
    <td>EDI</td>
<td>Inbound</a></td>
<td>Feb 27 2013</td>
<td>Shipped</td>
 </tr>
  <tr align="center">
    <td><a href="#" id="detail_link" >1001</a></td>
    <td>850</td>
    <td>EDI</td>
<td>Inbound</a></td>
<td>Feb 27 2013</td>
<td>Shipped</td>
 </tr>
</table>
</div>
</div>
	 
        </div>
      </div>
    </div>
     <div id="footer">
         <h2><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></h2>
		</div>
        </div>
  </div>
</body>

</html>
