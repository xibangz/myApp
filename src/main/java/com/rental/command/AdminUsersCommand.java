package com.rental.command;

import com.rental.Path;
import com.rental.bean.User;
import com.rental.dao.Fields;
import com.rental.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminUsersCommand extends Command {
    private static final long serialVersionUID = -1761702055282977646L;

    UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        userServ = (UserService) req.getServletContext().getAttribute("userServ");

        banUser(req.getParameter("banUser"));
        unBanUser(req.getParameter("unbanUser"));
        addManager(req.getParameter("addManager"), req);

        return Path.ADMIN_USERS_PAGE_REDIRECT;
    }

    private void banUser(String value) {
        if (value != null && !value.isEmpty()) {
            User user = new User();
            user.setId(Integer.parseInt(value));
            user.setBlocked(true);
            userServ.updateUserBlocked(user);
        }
    }

    private void unBanUser(String value) {
        if (value != null && !value.isEmpty()) {
            User user = new User();
            user.setId(Integer.parseInt(value));
            user.setBlocked(false);
            userServ.updateUserBlocked(user);
        }
    }

    private void addManager(String value, HttpServletRequest req) {
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
