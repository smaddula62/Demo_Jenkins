/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : TextColumn.java
 * CREATED: Jul 22, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package com.freeware.gridtag;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;

/**
 * This class provides functionality to display string columns.
 * @author Prasad P. Khandekar
 * @version 1.0
 * @since 1.0
 */
public final class TextColumn extends AColumnTag
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5928642885957457925L;
	private int    mintMaxLength;
    private String mstrDataFormat;

    public TextColumn()
    {
        super();
        this.mintMaxLength = -1;
    }

/*------------------------------------------------------------------------------
 * Getters 
 *----------------------------------------------------------------------------*/
    public String getDataFormat()
    {
        return this.mstrDataFormat;
    }

    public int getMaxLength()
    {
        return this.mintMaxLength;
    }

/*------------------------------------------------------------------------------
 * Setters 
 *----------------------------------------------------------------------------*/
    public void setDataFormat(String pstrDataFormat)
    {
        this.mstrDataFormat = pstrDataFormat;
    }

    public void setMaxLength(int pintMaxLen)
    {
        this.mintMaxLength = pintMaxLen;
    }

/*------------------------------------------------------------------------------
 * Overridden methods
 * @see javax.servlet.jsp.tagext.Tag
 *----------------------------------------------------------------------------*/
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        DBGrid objTmp = null;

        try
        {
            objTmp = (DBGrid) getParent();
            objTmp.addColumn(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: Column is not a child of DBGrid", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        if (!(this.getParent() instanceof DBGrid))
            throw new JspException("Error: Column tag needs to be a child of DBGrid!");

        // This tag does not have body contents.
        return SKIP_BODY;
    }

/*------------------------------------------------------------------------------
 * Methods 
 *----------------------------------------------------------------------------*/
    /* (non-Javadoc)
     * @see com.freeware.gridtag.IColumnTag#renderDetail()
     */
    public void renderDetail(Object pobjValue,int rowNum,int currColNum) throws JspException
    {
        String       strVal = null;
        StringBuffer objBuf = null;

        try
        {
            objBuf = new StringBuffer();
            objBuf.append("<td");
            if (this.mintWidth > 0)
                objBuf.append(" width=\"" + this.mintWidth + "%\"");
            if (this.mintHeight > 0)
                objBuf.append(" height=\"" + this.mintHeight + "\"");
            /*if (this.mstrCssClass != null)
                objBuf.append(" class=\"" + this.mstrCssClass + "\"");
            else
                objBuf.append(" class=\"gridRowEven\"");*/
            if (this.mstrHAlign != null)
                objBuf.append(" align=\"" + this.mstrHAlign.toLowerCase() + "\"");
            if (this.mstrVAlign != null)
                objBuf.append(" valign=\"" + this.mstrVAlign.toLowerCase() + "\"");

            if (this.mstrBgColor != null)
                objBuf.append(" bgcolor=\"" + this.mstrBgColor + "\"");
            if (this.mstrForeColor != null)
                objBuf.append(" color=\"" + this.mstrForeColor + "\"");
            if(this.mstrHideField){
            	objBuf.append(" id=\"hf"+rowNum+"\" style=\"display:none\"");
            }else{
            	objBuf.append(" id=\""+rowNum+"\"");
            }
            
            objBuf.append(">");
            strVal = formatField(pobjValue, this.mstrDataFormat);
                if (strVal != null && this.mintMaxLength > 0){
                    if (strVal.length() > this.mintMaxLength){
                        strVal = strVal.substring(0, this.mintMaxLength) + "...";
                    }
                }
            objBuf.append(strVal);
            objBuf.append("</td>");
            
            // Write created HTML to output stream.
            this.pageContext.getOut().print(objBuf.toString());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: Anchorcolumn must be a child of DBGrid!", CCEx);
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: IOException while writing to client!", IOEx);
        }
        catch (Exception ex)
        {
            throw new JspException("Error: Exception while writing to client!", ex);
        }
        finally
        {
            if (objBuf != null) objBuf = null;
        }
    }

    private String formatField(Object pobjVal, String pstrFmt) throws ClassCastException
    {
        String strRet = null;
        Format objFmt = null;

        try
        {
            if (pobjVal instanceof java.sql.Date || pobjVal instanceof java.util.Date)
            {
                objFmt = new SimpleDateFormat(pstrFmt);
                strRet = objFmt.format(pobjVal);
            }
            else if (pobjVal instanceof Number)
            {
                objFmt = new DecimalFormat(pstrFmt);
                strRet = objFmt.format(pobjVal);
            }
            else
                strRet = pobjVal.toString();
        }
        catch (NullPointerException NPExIgnore)
        {
        }
        catch (IllegalArgumentException IArgExIgnore)
        {
        }
        finally
        {
            if (objFmt != null) objFmt = null;
        }
        if (strRet == null) strRet = DBGrid.DEFAULT_NULLTEXT;
        return strRet;
    }

    private TextColumn getCopy()
    {
        TextColumn objRet = null;

        objRet = new TextColumn();
        super.copyAttributesTo(objRet);
        objRet.setDataFormat(this.mstrDataFormat);
        objRet.setId(this.getId());
        objRet.setPageContext(this.pageContext);
        objRet.setParent(this.getParent());
        return objRet;
    }
}
