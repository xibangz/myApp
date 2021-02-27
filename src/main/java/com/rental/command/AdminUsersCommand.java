package com.rental.command;

import com.rental.Path;
import com.rental.bean.User;
import com.rental.dao.Fields;
import com.rental.exception.DBException;
import com.rental.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.List;

public class AdminUsersCommand extends Command {
    private static final long serialVersionUID = -1761702055282977646L;

    UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        userServ = (UserService) req.getServletContext().getAttribute("userServ");
        ServletContext context = req.getServletContext();
        try {
            banUser(req.getParameter("banUser"),context);
            unBanUser(req.getParameter("unbanUser"), context);
            addManager(req.getParameter("addManager"), req);
        } catch (DBException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            return Path.ERROR_PAGE;
        }

        return Path.ADMIN_USERS_PAGE_REDIRECT;
    }

    private void banUser(String value, ServletContext context) throws DBException {
        if (value != null && !value.isEmpty()) {
            List<User> list = (List<User>) context.getAttribute("banned");
            User user = new User();
            user.setId(Integer.parseInt(value));
            user.setBlocked(true);
            list.add(user);
            context.setAttribute("banned", list);
            userServ.updateUserBlocked(user);
        }
    }

    private void unBanUser(String value, ServletContext context) throws DBException {
        if (value != null && !value.isEmpty()) {
            List<User> list = (List<User>) context.getAttribute("banned");
            User user = new User();
            user.setId(Integer.parseInt(value));
            user.setBlocked(false);
            deleteUser(list, user);
            context.setAttribute("banned", list);
            userServ.updateUserBlocked(user);
        }
    }

    private void deleteUser(List<User> list, User user) {
        for (int i = 0; i < list.size(); i++) {
            if (user.getId() == list.get(i).getId()) {
                list.remove(i);
            }
        }
    }

    private void addManager(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            User user = new User();
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setPassport(req.getParameter("passport"));
            user.setRoleId(Fields.USER_MANAGER_ROLE);
            userServ.insertUser(user);
        }
    }
}
