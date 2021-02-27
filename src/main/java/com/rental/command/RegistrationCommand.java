package com.rental.command;

import com.rental.Path;
import com.rental.bean.Role;
import com.rental.bean.User;
import com.rental.exception.DBException;
import com.rental.service.UserService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.rental.dao.Fields.*;

public class RegistrationCommand extends Command {
    private static final long serialVersionUID = 8619618090340562011L;

    private UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession();
        userServ = (UserService) req.getServletContext().getAttribute("userServ");
        User user = (User) session.getAttribute("user");

        String errorMessage = "errorMessage";
        String passport;
        String err=null;
        try {
            if (user == null) {
                if (userServ.findUserByLogin(req.getParameter(USER_LOGIN)) != null) {
                    session.setAttribute(errorMessage, err);
                    return Path.ERROR_PAGE;
                }
                if (!validateLoginPassword(req)) {
                    session.setAttribute(errorMessage, err);
                    return Path.ERROR_PAGE;
                }
                passport = req.getParameter(USER_PASSPORT);
                if (passport != null && !passport.isEmpty() && !validatePassport(passport)) {
                    session.setAttribute(errorMessage, err);
                    return Path.ERROR_PAGE;

                }
                user = insertUser(req);
                setSession(user, req.getParameter("localeName"), session);
                return Path.HOME_PAGE;
            } else {
                passport = req.getParameter(USER_PASSPORT);
                if (passport == null || passport.isEmpty() || !validatePassport(passport)) {
                    session.setAttribute(errorMessage, err);
                    return Path.ERROR_PAGE;
                }
                updateUserPassport(user, req);
                session.setAttribute("user", user);
                return session.getAttribute("order") != null
                        ? Path.USER_ORDER_CONFIRM
                        : Path.HOME_PAGE;
            }
        } catch (DBException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            return Path.ERROR_PAGE;
        }
    }

    private boolean validateLoginPassword(HttpServletRequest req) {
        String login = req.getParameter(USER_LOGIN);
        String password = req.getParameter(USER_PASSWORD);
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return Pattern.matches("\\w{5,45}", login)
                && Pattern.matches("\\w{5,45}", password);
    }


    private boolean validatePassport(String passport) {
        return Pattern.matches("([A-Z]{2}[0-9]{6})", passport);
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

    private User insertUser(HttpServletRequest req) throws DBException {
        User user = new User();
        String passport = req.getParameter(USER_PASSPORT);
        user.setLogin(req.getParameter(USER_LOGIN));
        user.setPassword(req.getParameter(USER_PASSWORD));
        if (passport != null && !passport.isEmpty()) {
            user.setPassport(req.getParameter(USER_PASSPORT));
        }
        user.setRoleId(USER_CLIENT_ROLE);
        userServ.insertUser(user);
        return user;
    }

    private void updateUserPassport(User user, HttpServletRequest req) throws DBException {
        user.setPassport(req.getParameter("passport"));
        userServ.updateUserPassport(user);
    }
}