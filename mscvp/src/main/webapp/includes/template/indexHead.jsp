<%-- 
    Document   : indexHead
    Created on : Jul 3, 2013, 10:51:04 AM
    Author     : miracle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
    <head>
        <title>Miracle Supply Chain Visibility portal</title>
        <meta name="description" content="website description" />
        <meta name="keywords" content="website keywords, website keywords" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="http://74.218.204.46/ediscv/favicon.ico" type="image/x-icon" /> 
            <script>
            function switchFlow() {
            var flowId = document.getElementById("userFlowMap").value;

            location.href = "../switchFlow/switchFlow.action?flowId="+flowId;
             }
            </script>
    </head>
    <body>
        <div id="logo_text">
            
            <h1><a href="#"><font color="white">Miracle Supply Chain Visiblity Portal</font></a>
            
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <s:if test="#session.loginId != null">
               
                <font color="white" style="font-size: 60%">Switch to Flow :</font> 
                <s:select headerKey="-1" list="#session.userFlowMap" name="userFlowMap" id="userFlowMap" value="#session.userDefaultFlowID" tabindex="9" cssStyle="width : 120px" onchange="switchFlow();"/>    
                </s:if> 
              
            </h1>
           
            
            <h2>
                <s:if test="#session.loginId != null">
                <font color="white">Welcome &nbsp;<s:property value="#session.userName" /></font>
                </s:if>
                <s:else>
                   <font color="white">Welcome &nbsp;Guest   &nbsp;</font>
                </s:else>
            </h2>
            <h2>
             
             <s:if test="%{#session.loginId != null && #session.mscvpRole != null}">
             <font color="white"><s:property value="#session.mscvpRole"/></font>
             </s:if>
            </h2>
            
            <h2>
                <s:if test="#session.loginId != null">
                <a href="<s:url value="general/logout"/>"> 
                    <font color="white">Logout</font>
              </a>  
                </s:if>
                <s:else>
                    <a href="<s:url action="general/login"/>"><font color="white">LogIn</font></a>
                </s:else>
            </h2>
           
        </div>
    </body>
</html>
