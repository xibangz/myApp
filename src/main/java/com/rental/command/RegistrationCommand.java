package com.rental.command;

import com.rental.Path;
import com.rental.bean.Role;
import com.rental.bean.User;
import com.rental.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.rental.dao.Fields.*;

public class RegistrationCommand extends Command {
    private static final long serialVersionUID = 8619618090340562011L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        UserService userServ = (UserService) req.getServletContext().getAttribute("userServ");

        String errorMessage = null;
        String forward = Path.ERROR_PAGE;

        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setLogin(req.getParameter(USER_LOGIN));
            user.setPassword(req.getParameter(USER_PASSWORD));
            user.setPassport(req.getParameter(USER_PASSPORT));
            user.setRoleId(USER_CLIENT_ROLE);
            userServ.insertUser(user);
            Role userRole = Role.getRole(user);
            session.setAttribute("userRole", userRole);
            session.setMaxInactiveInterval(1000);
            forward = Path.HOME_PAGE;
        } else {
            user.setPassport(req.getParameter("passport"));
            user.setBlocked(false);
            userServ.updateUserPassport(user);
            if (session.getAttribute("order") != null) {
                forward = Path.USER_ORDER_CONFIRM;
            } else {
                forward = Path.HOME_PAGE;
            }
        }
        session.setAttribute("user", user);



            /*String userLocaleName = user.getLocaleName();
            log.trace("userLocalName --> " + userLocaleName);

            if (userLocaleName != null && !userLocaleName.isEmpty()) {
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
                session.setAttribute("defaultLocale", userLocaleName);
            }
        }*/
        return forward;
    }
}
