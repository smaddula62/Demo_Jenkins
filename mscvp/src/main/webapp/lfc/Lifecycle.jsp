<%-- 
    Document   : PurchaseOrder
    Created on : Mar 11, 2013, 10:59:42 AM
    Author     : Nagiredddy Seerapu
--%>

<%@page import="com.mss.ediscv.lfc.PaymentLifecycleBean"%>
<%@page import="com.mss.ediscv.lfc.InvoiceLifecycleBean"%>
<%@page import="com.mss.ediscv.lfc.LifecycleBeans"%>
<%@page import="com.mss.ediscv.lfc.PoLifecycleBean"%>
<%@page import="com.mss.ediscv.lfc.AsnLifecycleBean"%>
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
        src='<s:url value="/includes/js/modernizr-1.5.min.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/GridNavigation.js"/>'></script>
        <script language="JavaScript"
        src='<s:url value="/includes/js/common.js"/>'></script>
        
        <script language="JavaScript"
        src='<s:url value="/includes/js/LfcAjax.js"/>'></script>
          
 

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
 function getDetails(ponum,fileid,type){
     
    // alert("hiiiiii---->"+val+"------"+type);
   getlfcPODetails(ponum,fileid,type);
 } 
 function goBack()
  {
  window.history.go(-1)
  }
</script>


    </head>
    <body>
        <div id="wrapper">
       <div id="main">
           <%String contextPath = request.getContextPath();
          %>
    <header>
      <div id="logo">
         <s:include value="/includes/template/head.jsp"/>    
      
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
          <li class="current"><a href="#">Life Cycle</a></li>
         <%-- <li ><a href="<s:url action="../po/purchaseOrder"/>">Purchase Orders</a></li> --%>
         <li><a href="#"> Order Management<span class="sf-arrow"></span></a>
         <ul>
               <li ><a href="<s:url action="../po/purchaseOrder"/>">Purchase Orders</a></li>
            </ul>
          </li>
          
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
	  <%--- GRid start --%>
          <div class="content">
        <div class="content_item">
             <div align="right"><h3 align="left">Life Cycle</h3><input type="button" value="Go back" class="button" onclick="goBack()"></input></div>
            <%!String cssValue = "whiteStripe";
            int resultsetTotal;%>

          <table align="left" width="710px"
            border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td style="background-color: white;">
              
           <table align="left" id="results" width="690px"
            border="0" cellpadding="0" cellspacing="0" class="CSSTableGenerator">
           
                <%
                //LifecycleBeans lifecycleBeans = (LifecycleBeans) 
               // PoLifecycleBean poLifeCycleBean = (PoLifecycleBean) session.getAttribute(AppConstants.SES_LifecycleBeansList);
               /* AsnLifecycleBean asnLifecycleBean = (AsnLifecycleBean) lifecycleBeans.getAsnLifecycleBean();
                InvoiceLifecycleBean invoiceLifecycleBean = (InvoiceLifecycleBean) lifecycleBeans.getInvoiceLifecycleBean();
                PaymentLifecycleBean paymentLifecycleBean = (PaymentLifecycleBean) lifecycleBeans.getPaymentLifecycleBean();*/
                
                java.util.List list = (java.util.List) session.getAttribute(AppConstants.LFC_SES_PO_LIST);
              //   out.println("NAG-----PO LIST size--->"+list.size()+"\n");
                  
              if(list.size()!=0){
             PoLifecycleBean poLifeCycleBean;
             %>
            
             <tr>
               <td>#</td>
               <td >PO #</td>
               <td>InstanceId</td>
               <td >Type</td>
               <td >Direction</td>
               <td >DateTime</td>  
               <%--<td >SENDER_ID</td>
               <td >RECEIVER_ID</td>   --%>
               <td >Status</td>
              <td>Ack Status</td> 
              <td>Reprocess</td> 
            </tr>

            <%
            for (int i = 0; i < list.size(); i++) {
            poLifeCycleBean = (PoLifecycleBean) list.get(i);

            if (i % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
           
            <%-- Po Start--%>
           <tr>
               <td>
                  <%
                    out.println(poLifeCycleBean.getTranType());
                    %>
                </td>
                 
            <td><a href="javascript:getDetails('<%=poLifeCycleBean.getPoNumber()%>','<%=poLifeCycleBean.getFileId()%>','PO');">
                  <%
                    out.println(poLifeCycleBean.getPoNumber());
                    %></a>
                    
                </td>
                <td>
                  <%
                    out.println(poLifeCycleBean.getFileId());
                    %>
                </td>
               <td>
                  <%
                    out.println(poLifeCycleBean.getFileType());
                    %>
                </td>
             
              
            
               <td>
                  <%
                    out.println(poLifeCycleBean.getDirection().toUpperCase());
                    %>
                </td>
               <td>
                  <%
                    out.println(poLifeCycleBean.getDatetimeRec().toString().substring(0,poLifeCycleBean.getDatetimeRec().toString().lastIndexOf(":")));
                    %>
                </td>  
                <%--<td>
                  <%
                    out.println(poLifeCycleBean.getSenderId());
                    %>
                </td>
                <td>
                  <%
                    out.println(poLifeCycleBean.getRecId());
                    %>
                </td>  --%>
                
                 <td>
                  <%
                  if(poLifeCycleBean.getStatus().equalsIgnoreCase("ERROR")){  
                    out.println("<font color='red'>"+poLifeCycleBean.getStatus().toUpperCase()+"</font>");
                     }else if(poLifeCycleBean.getStatus().equalsIgnoreCase("SUCCESS")){
                         out.println("<font color='green'>"+poLifeCycleBean.getStatus().toUpperCase()+"</font>");
                       }else {
                        out.println("<font color='orange'>"+poLifeCycleBean.getStatus().toUpperCase()+"</font>");
                       }
                                       
                    %>
                </td>
                 <td>
                  <%
                  
                    //out.println(poLifeCycleBean.getAckStatus());
                    if(poLifeCycleBean.getAckStatus().equalsIgnoreCase("REJECTED")){       
                         out.println("<font color='red'>"+poLifeCycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(poLifeCycleBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+poLifeCycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+poLifeCycleBean.getAckStatus().toUpperCase()+"</font>");
                       }
                                      
                    %>
                </td> 
                <td>
                  <%
                   // out.println(poLifeCycleBean.getReProcessStatus());
                    if(poLifeCycleBean.getReProcessStatus()!=null){
                       out.println(poLifeCycleBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
                    %>
                </td>
                
           </tr>
             <%} //PO FOR loop END
             %>
             
             <%-- Po End--%>
            <%-- ASN start --%>
            <%
                 java.util.List asnlist = (java.util.List) session.getAttribute(AppConstants.LFC_SES_ASN_LIST);
                 // out.println("NAG-----ASN LIST size--->"+list.size()+"\n");
                 if(asnlist.size()!=0){
                 AsnLifecycleBean asnLifecycleBean;
                 %>
            
                 <%
            for (int j = 0; j < asnlist.size(); j++) {
            asnLifecycleBean = (AsnLifecycleBean) asnlist.get(j);

            if (j % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
            <%-- Asn Start--%>
            <tr>
                 <td>
                  <%
                    out.println(asnLifecycleBean.getTranType());
                    %>
                </td>
                 
            <td><a href="javascript:getDetails('<%=asnLifecycleBean.getPoNumber()%>','<%=asnLifecycleBean.getFileId()%>','ASN');">
                  <%
                    out.println(asnLifecycleBean.getPoNumber());
                    %>
                </a>
                </td>
                <td>
                  <%
                    out.println(asnLifecycleBean.getFileId());
                    %>
                </td>
               <td>
                  <%
                    out.println(asnLifecycleBean.getFileType());
                    %>
                </td>
               <td>
                  <%
                    out.println(asnLifecycleBean.getDirection().toUpperCase());
                    %>
                </td>
               
                <td>
                  <%
                    out.println(asnLifecycleBean.getDatetimeRec().toString().substring(0,asnLifecycleBean.getDatetimeRec().toString().lastIndexOf(":")));
                    %>
                </td> 
               <%-- <td>
                  <%
                    out.println(asnLifecycleBean.getSenderId());
                    %>
                </td>
                <td>
                  <%
                    out.println(asnLifecycleBean.getRecId());
                    %>
                </td> --%>
                
                 <td>
                  <%
                  if(asnLifecycleBean.getStatus().equalsIgnoreCase("ERROR")){  
                    out.println("<font color='red'>"+asnLifecycleBean.getStatus().toUpperCase()+"</font>");
                    }else if(asnLifecycleBean.getStatus().equalsIgnoreCase("SUCCESS")){
                           out.println("<font color='green'>"+asnLifecycleBean.getStatus().toUpperCase()+"</font>");                
                                       }else {
                        out.println("<font color='orange'>"+asnLifecycleBean.getStatus().toUpperCase()+"</font>");                
                                       }
                    %>
                </td>
                <td>
                  <%
                   // out.println(asnLifecycleBean.getAckStatus());
                     if(asnLifecycleBean.getAckStatus().equalsIgnoreCase("REJECT")){       
                         out.println("<font color='red'>"+asnLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(asnLifecycleBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+asnLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+asnLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                       }
                    %>
                </td>  
                
                 <td>
                  <%
                   // out.println(asnLifecycleBean.getReProcessStatus());
                   if(asnLifecycleBean.getReProcessStatus()!=null){
                       out.println(asnLifecycleBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
                    %>
                </td>
           </tr>
           
            <%-- ASn end--%>
            
            <%
               }// ASN for loop end
                   
                       }// ASN if end
            %>
            <%-- INVOICE START --%>
            <%
                 java.util.List invoicelist = (java.util.List) session.getAttribute(AppConstants.LFC_SES_INVOICE_LIST);
               //  out.println("NAG-----INVOICE LIST size--->"+invoicelist.size()+"\n");
                 if(invoicelist.size()!=0){
                 InvoiceLifecycleBean invoiceLifecycleBean;
                 %>
             <%
            for (int k = 0; k < invoicelist.size(); k++) {
            invoiceLifecycleBean = (InvoiceLifecycleBean) invoicelist.get(k);

            if (k % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
           <tr>
               <td>
                  <%
                    out.println(invoiceLifecycleBean.getTranType());
                    %>
                </td>
                
                
            <td><a href="javascript:getDetails('<%=invoiceLifecycleBean.getPoNumber()%>','<%=invoiceLifecycleBean.getFileId()%>','INV');">
                  <%
                    out.println(invoiceLifecycleBean.getPoNumber());
                    %></a>
                </td>
                <td>
                  <%
                    out.println(invoiceLifecycleBean.getFileId());
                    %>
                </td>
               <td>
                  <%
                    out.println(invoiceLifecycleBean.getFileType());
                    %>
                </td>
             
              
            
               <td>
                  <%
                    out.println(invoiceLifecycleBean.getDirection().toUpperCase());
                    %>
                </td>
             
                <td>
                  <%
                    out.println(invoiceLifecycleBean.getDatetimeRec().toString().substring(0,invoiceLifecycleBean.getDatetimeRec().toString().lastIndexOf(":")));
                    %>
                </td> 
              <%--  <td>
                  <%
                    out.println(invoiceLifecycleBean.getSenderId());
                    %>
                </td>
                <td>
                  <%
                    out.println(invoiceLifecycleBean.getRecId());
                    %>
                </td> --%>
                
                 <td>
                  <%
                  if(invoiceLifecycleBean.getStatus().equalsIgnoreCase("ERROR")){  
                    out.println("<font color='red'>"+invoiceLifecycleBean.getStatus().toUpperCase()+"</font>");
                                       }else if(invoiceLifecycleBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+invoiceLifecycleBean.getStatus().toUpperCase()+"</font>");
                                       }else {
                        out.println("<font color='orange'>"+invoiceLifecycleBean.getStatus().toUpperCase()+"</font>");
                                       }
                    %>
                </td>
                <td>
                  <%
                   // out.println(invoiceLifecycleBean.getAckStatus());
                     if(invoiceLifecycleBean.getAckStatus().equalsIgnoreCase("REJECT")){       
                         out.println("<font color='red'>"+invoiceLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(invoiceLifecycleBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+invoiceLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+invoiceLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                       }
                    %>
                </td> 
                
                 <td>
                  <%
                   // out.println(invoiceLifecycleBean.getReProcessStatus());
                     if(invoiceLifecycleBean.getReProcessStatus()!=null){
                       out.println(invoiceLifecycleBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
                    %>
                </td>
           </tr>
            <%-- INVOICE END --%>
            
            <%
               }// INVOICE FOR
                             } //INVOICE IF
            %>
            
            <%-- payment start --%>
            <%
                 java.util.List paymentlist = (java.util.List) session.getAttribute(AppConstants.LFC_SES_PAYMENT_LIST);
                 //out.println("NAG-----INVOICE LIST size--->"+paymentlist.size()+"\n");
                 if(paymentlist.size()!=0){
                 PaymentLifecycleBean paymentLifecycleBean;
                 %>
             <%
            for (int l = 0; l < paymentlist.size(); l++) {
            paymentLifecycleBean = (PaymentLifecycleBean) paymentlist.get(l);

            if (l % 2 == 0) {
            cssValue = "whiteStripe";
            } else {
            cssValue = "grayEditSelection";
            }
           %>
            
            <%-- Payment Start--%>
           <tr>
                <td>
                  <%
                    out.println(paymentLifecycleBean.getTranType());
                    %>
                </td>
               
           <td><a href="javascript:getDetails('<%=paymentLifecycleBean.getPoNumber()%>','<%=paymentLifecycleBean.getFileId()%>','PAYMENT');">
                  <%
                    out.println(paymentLifecycleBean.getPoNumber());
                    %></a>
                </td>
                 <td>
                  <%
                    out.println(paymentLifecycleBean.getFileId());
                    %>
                </td>
               <td>
                  <%
                    out.println(paymentLifecycleBean.getFileType());
                    %>
                </td>
               <td>
                  <%
                    out.println(paymentLifecycleBean.getDirection().toUpperCase());
                    %>
                </td>
           
                <td>
                  <%
                    out.println(paymentLifecycleBean.getDatetimeRec().toString().substring(0,paymentLifecycleBean.getDatetimeRec().toString().lastIndexOf(":")));
                    %>
                </td>
               <%-- <td>
                  <%
                    out.println(paymentLifecycleBean.getSenderId());
                    %>
                </td>
                <td>
                  <%
                    out.println(paymentLifecycleBean.getRecId());
                    %>
                </td>  --%>
                
                 <td>
                  <%
                  if(paymentLifecycleBean.getStatus().equalsIgnoreCase("ERROR")){
                    out.println("<font color='red'>"+paymentLifecycleBean.getStatus().toUpperCase()+"</font>");
                     }else if(paymentLifecycleBean.getStatus().equalsIgnoreCase("SUCCESS")){
                        out.println("<font color='green'>"+paymentLifecycleBean.getStatus().toUpperCase()+"</font>");
                                       }else {
                        out.println("<font color='orange'>"+paymentLifecycleBean.getStatus().toUpperCase()+"</font>");
                                       }
                    %>
                </td>
                 <td>
                  <%
                  //out.println("hiiiii");
                     
                    //out.println(paymentLifecycleBean.getAckStatus());
                            if(paymentLifecycleBean.getAckStatus().equalsIgnoreCase("REJECT")){       
                         out.println("<font color='red'>"+paymentLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else if(paymentLifecycleBean.getAckStatus().equalsIgnoreCase("ACCEPTED")){
                         out.println("<font color='green'>"+paymentLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                     }else {
                          out.println("<font color='orange'>"+paymentLifecycleBean.getAckStatus().toUpperCase()+"</font>");
                       }            
                    %>
                </td>  
                
                 <td>
                  <%
                      if(paymentLifecycleBean.getReProcessStatus()!=null){
                       out.println(paymentLifecycleBean.getReProcessStatus().toUpperCase());
                       
                   }else {
                        out.println("-");
                   }
                    %>
                </td>
                
           </tr>
             <%-- Payment End--%>
            
            <%-- Payment END --%>
            <%
                       }//PAYMENT END FOR loop
                       }//PAYMENT ENF IF
            } // PO if
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
            <tr >
                
               <td align="right" colspan="28" style="background-color: white;">
                <div align="right" id="pageNavPosition"></div>
               </td>
            </tr>           
</table>  
            
          
 </div>
            <%-- Process butttons  start --%>
           
            <%-- process buttons end--%>
<%-- Grid End --%>
           
          </div>
       
          
       </div> 
       <script type="text/javascript">
        var pager = new Pager('results', 10); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    	</script>
      <%--  <div id="footer">
         <h2><font color="white">&#169 2013 Miracle Software Systems, Inc. All rights reserved</font></h2>
		</div>--%>
        </div>
        </div>
         <footer> 
         <p><font color="white">&#169 2015 Miracle Software Systems, Inc. All rights reserved</font></p>
	  </footer>
    </body>
</html>
