package com.rental.command;

import com.rental.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends Command {

    private static final long serialVersionUID = 298634316653905762L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("errorMessage", "No such Command!!!");
        return Path.ERROR_PAGE;
    }
}
