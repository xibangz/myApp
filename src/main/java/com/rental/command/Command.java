package com.rental.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 8067695266110250487L;

    public abstract String execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException;
}
