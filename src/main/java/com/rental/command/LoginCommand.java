package com.rental.command;

import com.rental.Path;
import com.rental.bean.Role;
import com.rental.bean.User;
import com.rental.service.UserService;

import static com.rental.dao.Fields.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {
    private static final long serialVersionUID = 2800137775546049666L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        ServletContext context = req.getServletContext();
        UserService userServ = (UserService) context.getAttribute("userServ");

        String login = req.getParameter(USER_LOGIN);
        String password = req.getParameter(USER_PASSWORD);

        String errorMessage = null;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            req.setAttribute("errorMessage", errorMessage);
            return Path.ERROR_PAGE;
        }

        User user = userServ.findUserByLogin(login);
        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            req.setAttribute("errorMessage", errorMessage);
            return Path.ERROR_PAGE;
        } else {
            Role userRole = Role.getRole(user);
            /*if (userRole == Role.ADMIN)
                forward = Path.COMMAND_LIST_ORDERS;

            if (userRole == Role.CLIENT)
                forward = Path.COMMAND_LIST_MENU;*/

            session.setAttribute("user", user);
            session.setAttribute("userRole", userRole);
            session.setMaxInactiveInterval(1000);

            /*String userLocaleName = user.getLocaleName();
            log.trace("userLocalName --> " + userLocaleName);

            if (userLocaleName != null && !userLocaleName.isEmpty()) {
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
                session.setAttribute("defaultLocale", userLocaleName);
            }
        }*/

            return Path.HOME_PAGE;
        }
    }
}
