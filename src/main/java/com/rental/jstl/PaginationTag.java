package com.rental.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class PaginationTag extends BodyTagSupport {

    private static final long serialVersionUID = 5379839708781539253L;
    private int countProducts;
    private int productsPerPage;
    private int pages;

    public void setCountProducts(String countProducts){
        this.countProducts=Integer.parseInt(countProducts);
    }

    public void setProductsPerPage(String productsPerPage){
        this.productsPerPage=Integer.parseInt(productsPerPage);
    }

    @Override
    public int doStartTag() throws JspException {
        pages = countProducts%productsPerPage>0
                ? (countProducts/productsPerPage) + 1
                : countProducts/productsPerPage;
        try {
            pageContext.getOut().write("<div class=\"container\"><br><ul class=\"pagination justify-content-center\">");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspTagException {
        if (pages > 1) {
            pages--;
            try {
                pageContext.getOut().write("");
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
            pageContext.getOut().write("</ul></div>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
