package com.rental.command;

import com.rental.Path;
import com.rental.bean.Role;
import com.rental.bean.User;
import com.rental.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

import static com.rental.dao.Fields.*;

public class RegistrationCommand extends Command {
    private static final long serialVersionUID = 8619618090340562011L;

    private UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        userServ = (UserService) req.getServletContext().getAttribute("userServ");

        String errorMessage = null;

        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = insertUser(session, req);
            setSession(user, req.getParameter("localeName"), session);
            return Path.HOME_PAGE;
        } else {
            updateUserPassport(user, req);
            return session.getAttribute("order") != null
                    ? Path.USER_ORDER_CONFIRM
                    : Path.HOME_PAGE;
        }
    }

    private void setLocale(String locale, HttpSession session) {
        if (locale != null && !locale.isEmpty()) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", locale);
            session.setAttribute("defaultLocale", locale);
        }
    }

    private void setSession(User user, String locale, HttpSession session) {
        session.setAttribute("user", user);
        setLocale(locale, session);
        session.setAttribute("userRole", Role.getRole(user));
        session.setMaxInactiveInterval(1000);
    }

    private User insertUser(HttpSession session, HttpServletRequest req) {
        User user = new User();
        user.setLogin(req.getParameter(USER_LOGIN));
        user.setPassword(req.getParameter(USER_PASSWORD));
        user.setPassport(req.getParameter(USER_PASSPORT));
        user.setRoleId(USER_CLIENT_ROLE);
        userServ.insertUser(user);
        return user;
    }

    private void updateUserPassport(User user, HttpServletRequest req) {
        user.setPassport(req.getParameter("passport"));
        userServ.updateUserPassport(user);
    }
}