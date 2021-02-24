package com.rental.controller;

import com.rental.bean.CarTotal;
import com.rental.bean.OrderStatus;
import com.rental.bean.Role;
import com.rental.command.Command;
import com.rental.command.CommandContainer;
import com.rental.service.CarTotalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = -9116344949613325144L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command =
                CommandContainer.getCommand(req.getParameter("command"));
        String forward = command.execute(req, resp);
        if (forward != null) {
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command =
                CommandContainer.getCommand(req.getParameter("command"));
        String forward = command.execute(req, resp);
        if (forward != null) {
            resp.sendRedirect(forward);
        }
    }
}
