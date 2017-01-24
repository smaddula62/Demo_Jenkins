<%-- 
    Document   : PurchaseOrder
    Created on : Mar 11, 2013, 10:59:42 AM
    Author     : Nagiredddy Seerapu
--%>

<%@page import="com.mss.ediscv.po.PurchaseOrderBean"%>
<%@page import="com.mss.ediscv.util.AppConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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

          <script>
  $(function() {
    $( "#podatepicker" ).datepicker();
	 $( "#podatepickerfrom" ).datepicker();
  });

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

  function demo(){
    
    $(function() {
       
           $('#detail_box').show();
           return false;
               
   });
  
  }
  
function resetvalues()
{
    document.getElementById('poNumber').value="";
    document.getElementById('podatepickerfrom').value="";
    document.getElementById('podatepicker').value="";
    document.getElementById('poSenderId').value="";
    document.getElementById('poSenderName').value="";
    document.getElementById('poRecId').value="";
    document.getElementById('poRecName').value="";
   // alert("reset");
    
}
</script>
    </head>
    <body>
       <div id="main">
    <header>
      <div id="logo">
         <s:include value="/includes/template/head.jsp"/>    
      
     </div>
      <nav>
        <ul class="sf-menu" id="nav">
          <li><a href="<s:url action="../doc/orderToCash"/>">Document Repository</a></li>
          <li class="current"><a href="#">Purchase Orders</a></li>
          <li><a href="<s:url action="../shipment/shipmentAction"/>">Shipments</a></li>
          <li><a href="<s:url action="../inv/invoiceAction"/>">Invoices</a></li>
          <li><a href="<s:url action="../payment/paymentAction"/>">Payments</a></li>
         <%--  <li><a href="<s:url action="../general/logout"/>">Logout</a></li> --%>
        </ul>
      </nav>
    </header>
    <div id="site_content">
      <div id="sidebar_container">
	  
	  
	   <div id="detail_box" style="display: none;"> 
        <div class="sidebar">
          <h3>Detail Information</h3>
          <div class="sidebar_item">
		   <h5>Delivary Status :&nbsp; &nbsp; Delivered</h5>
            <h5>PO Value :&nbsp; &nbsp; 100$</h5>
            <h5>Routings :&nbsp; &nbsp; Route1</h5>
             <h5>Invoice # :&nbsp; &nbsp; 234</h5>
			 <h5>Cash Payments :&nbsp; &nbsp; 6</h5>
          </div>
		  </div>
		  
		  
          <div class="sidebar_base"></div>
        </div>
       
        
      </div>
     
      <div class="content">
        <div class="content_item">
         <h3>Search By PO</h3>
		<div &nbsp; style="alignment-adjust:central;" >
		
               <table >
		<tbody >
		<s:form action="../po/poSearch" method="post" name="purchaseForm" id="purchaseForm" theme="simple">
			<tr>
                            <td>Date From </td>
				<td>
                                   <%-- <input type="text" id="datepickerfrom" /> --%>
                                   <s:textfield cssClass="inputStyle" name="poDateFrom" id="podatepickerfrom" tabindex="1" onkeypress="enterDate();"/>
				</td>
				
			<td>Date To </td>
				<td>
                                    <%--<input type="text" id="datepicker" /> --%>
                                    <s:textfield cssClass="inputStyle" name="poDateTo" id="podatepicker" tabindex="2" onkeypress="enterDate();"/>
				</td>
				
			</tr>
                        <tr>
			<td>Sender Id</td>
				<td>
                                    
                                    <s:textfield cssClass="inputStyle" name="poSenderId" id="poSenderId" tabindex="3" />
				</td>
				<td>Sender Name</td>
				<td>
                                   
                                    <s:textfield cssClass="inputStyle" name="poSenderName" id="poSenderName" tabindex="4"/>
				</td>
				</tr>
			<tr>
				<td>Receiver Id</td>
				<td>
                                    
                                    <s:textfield cssClass="inputStyle" name="poRecId" id="poRecId" tabindex="5"/>
				</td>
				<td>Receiver Name</td>
				<td>
                                    
                                    <s:textfield cssClass="inputStyle" name="poRecName" id="poRecName" tabindex="6"/>
				</td>
				</tr>
			
				
				<tr>
				<td>PO # <font color="red" style="bold"> * </font>
                                </td>
				<td>
                                   <s:textfield cssClass="inputStyle" name="poNumber" id="poNumber" tabindex="7"/>
				</td>
				
		</div>		
  				</td></tr>
         </tbody>
                    </table>
                        <table>
                        <tr>
                        <td style="background-color: white;">
                        <strong>  <s:submit value="Search" cssClass="button" onclick="return Formvalidation(document.getElementById('poNumber').value,document.getElementById('podatepickerfrom').value,document.getElementById('podatepicker').value);" tabindex="8"/></strong>
                        </td>
                        <td style="background-color: white;">
                        <%-- <strong> <s:reset value="Reset" cssClass="button" tabindex="9"/></strong> --%>
                        <strong><input type="button" value="Reset" class="button" tabindex="9" onclick="return resetvalues();"/></strong>
                        </td>
                        </tr>
                        </table>
        
         </s:form>
	
	
            </div>
        </div>
      </div>
         <s:if test="#session.poSearchList != null"> 
	  <%--- GRid start --%>
          <div class="content">
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
             <tr>
               <td >PO #</td>
                <td >SO #</td>
                <td >SAP IDOC</td>
                <td>PO Date</td>
                <td>Ship Date</td>
                <td>PO Status</td>
                <td>ISA Control# </td>
                <td>Total Cost</td>
             <%--    <td> </td> --%>
            
            </tr>
            <tr>
             
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
               <td><a href="#" onclick="return demo();" >
                    <%
                    out.println(purchaseOrderBean.getPo());
                    %>
                    </a>
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getSo());
            %>
                </td>
                <td>
                   <%
            out.println(purchaseOrderBean.getSapIdoc());
            %>
                    
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getPoDate());
            %>
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getShipDate());
            %>
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getPoStatus());
            %>
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getIsaControl());
            %>
                </td>
                <td>
                    <%
            out.println(purchaseOrderBean.getItemQty());
            %>
            </td>
            <%--    <td> &nbsp; &nbsp; <input type = "checkbox" id = "check_link" />&nbsp; &nbsp;  </td> --%>
            
           </tr>
            <%
            }
            }
            else
            {
                 %>
                 <tr><td>
                 <%
            out.println("<img  border='0' align='top'  src='/ediscv/includes/images/alert.gif'/><b> No Records Found to Display!</b>");
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
         <%--    <table >
		<tr>
		  <td class="button"><img src="images/spacer.gif" width="30" height="1"><strong>Original</strong></td>
		   <td class="button"><img src="images/spacer.gif" width="30" height="1"><strong>Processed</strong></td>
		    <td class="button"><img src="images/spacer.gif" width="30" height="1"><strong>Acknowledge</strong></td>
			
			 <td class="button"><img src="images/spacer.gif" width="30" height="1"><strong>Resend</strong></td>
			  <td class="button"><img src="images/spacer.gif" width="30" height="1"><strong>Reprocess</strong></td>
		</tr>
		</table> --%>
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
    <footer>
    
      <p>Copyright @ MSS</p>
    </footer>
  </div>  
    </body>
</html>
