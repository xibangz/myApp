package com.rental.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class CarDeployTag extends BodyTagSupport {
    private static final long serialVersionUID = 1200528806260618011L;

    private int productsPerPage;
    private int carsListSize;
    private int size;

    public void setCarListSize(String countProducts) {
        this.carsListSize = Integer.parseInt(countProducts);
    }

    public void setProductsPerPage(String productsPerPage) {
        this.productsPerPage = Integer.parseInt(productsPerPage);
    }

    @Override
    public int doStartTag() throws JspException {
        size = Math.min(carsListSize, productsPerPage);
        try {
            pageContext.getOut().write("<div class=\"row\"><div class=\"col-md-6\">" +
                    "<div class=\"card card-body\">" +
                    "<div class=\"media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row\">");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspTagException {
        if (size > 1) {
            size--;
            try {
                pageContext.getOut().write("</div></div></div><div class=\"col-md-6\">" +
                        "<div class=\"card card-body\">" +
                        "<div class=\"media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row\">");
            } catch (IOException e) {
                throw new JspTagException(e.getMessage());
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</div>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
