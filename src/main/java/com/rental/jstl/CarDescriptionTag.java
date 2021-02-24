package com.rental.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class CarDescriptionTag extends BodyTagSupport {

    private static final long serialVersionUID = -5611002958289431752L;
    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<p class=\"mb-3\">");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</p></div>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
