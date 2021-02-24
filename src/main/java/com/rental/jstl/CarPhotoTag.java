package com.rental.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;


public class CarPhotoTag extends BodyTagSupport {
    private static final long serialVersionUID = 1200528806260618011L;

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<div class=\"md-2 mb-3 mb-lg-0\"><img src=");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("width=\"500\" height=\"280\" alt=\"\"></div>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
