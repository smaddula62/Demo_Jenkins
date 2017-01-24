
function gridDownload(sheetType,dwdType) {
 
    window.location="../download/gridDownload.action?downloadType="+dwdType+"&sheetType="+sheetType;
}

function gridDashboardDownload(sheetType,dwdType,inbound,outbound) {
    /*alert(inbound);
    alert(outbound);*/
    if(inbound==''&&outbound=='')
    {
         alert("No INBOUND and OUTBOUND data to generate");        
    }
    
    
    else
    {
         window.location="../download/gridDownload.action?downloadType="+dwdType+"&sheetType="+sheetType+"&inbound="+inbound+"&outbound="+outbound;
    }
}

/*function gridDownloadReports(sheetType,dwdType) {
 
    window.location="../download/gridDownloadReports.action?downloadType="+dwdType+"&sheetType="+sheetType;
}*/