package com.rental.command;

import com.rental.Path;
import com.rental.bean.Role;
import com.rental.bean.User;
import com.rental.exception.DBException;
import com.rental.service.UserService;

import static com.rental.dao.Fields.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

public class LoginCommand extends Command {
    private static final long serialVersionUID = 2800137775546049666L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        UserService userServ = (UserService) req.getServletContext().getAttribute("userServ");
        String login = req.getParameter(USER_LOGIN);
        String password = req.getParameter(USER_PASSWORD);
        String errorMess = "errorMessage";
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            req.setAttribute(errorMess, "Login/password cannot be empty");
            return Path.ERROR_PAGE;
        }
        User user;
        try {
            user = userServ.findUserByLogin(login);
        } catch (DBException e) {
            session.setAttribute(errorMess,e.getMessage());
            return Path.ERROR_PAGE;
        }
        if (user == null || !password.equals(user.getPassword())) {
            req.setAttribute(errorMess, "Cannot find user with such login/password");
            return Path.ERROR_PAGE;
        } else {
            setUser(user, session);
            setLocale(req.getParameter("localeName"), session);
        }

        return Path.HOME_PAGE;
    }

    private void setLocale(String locale, HttpSession session) {
        if (locale != null && !locale.isEmpty()) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", locale);
            session.setAttribute("defaultLocale", locale);
        }
    }

    private void setUser(User user, HttpSession session) {
        session.setAttribute("user", user);
        session.setAttribute("userRole", Role.getRole(user));
        session.setMaxInactiveInterval(1000);
    }
}
