package com.rental.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class CarPriceTag extends BodyTagSupport {
    private static final long serialVersionUID = 1759009273979417012L;

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<div class=\"mt-3 mt-lg-0 ml-lg-3 text-center\"><h3 class=\"mb-0 font-weight-semibold\">");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</h3>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
