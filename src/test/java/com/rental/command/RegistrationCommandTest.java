package com.rental.command;

import com.rental.Path;
import com.rental.bean.Order;
import com.rental.bean.User;
import com.rental.exception.DBException;
import com.rental.service.UserService;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;

public class RegistrationCommandTest extends Mockito {
    private final RegistrationCommand registrationCommand = new RegistrationCommand();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final ServletContext context = mock(ServletContext.class);
    private final UserService userServ = mock(UserService.class);
    private final User user = mock(User.class);
    private final Order order = mock(Order.class);

    @Test
    public void testUserLoginExists() throws DBException, IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(null);
        when(req.getParameter("login")).thenReturn("login");
        when(userServ.findUserByLogin(anyString())).thenReturn(new User());
        assertEquals(Path.ERROR_PAGE, registrationCommand.execute(req, resp));
    }

    @Test
    public void testInvalidPassportUserNotNull() throws IOException, ServletException {
        mock();
        when(req.getParameter("passport")).thenReturn("pass");
        assertEquals(Path.ERROR_PAGE, registrationCommand.execute(req, resp));
    }

    @Test
    public void testInvalidLoginPassword() throws IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(null);
        when(req.getParameter("login")).thenReturn("login");
        when(req.getParameter("password")).thenReturn("11ыйы");
        assertEquals(Path.ERROR_PAGE, registrationCommand.execute(req, resp));
    }

    @Test
    public void testValidLoginPassword() throws IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(null);
        when(req.getParameter("login")).thenReturn("login");
        when(req.getParameter("password")).thenReturn("we123w");
        assertEquals(Path.HOME_PAGE, registrationCommand.execute(req, resp));
    }

    @Test
    public void testValidPassportUserNotNull() throws IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("passport")).thenReturn("MM123456");
        assertEquals(Path.HOME_PAGE, registrationCommand.execute(req, resp));
    }

    @Test
    public void testValidPassportUserNotNullAndOrderNotNull() throws IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("order")).thenReturn(order);
        when(req.getParameter("passport")).thenReturn("MM123456");
        assertEquals(Path.USER_ORDER_CONFIRM, registrationCommand.execute(req, resp));
    }

    @Test
    public void testValidPassportUserNotNullAndOrderNull() throws IOException, ServletException {
        mock();
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("order")).thenReturn(null);
        when(req.getParameter("passport")).thenReturn("MM123456");
        assertEquals(Path.HOME_PAGE, registrationCommand.execute(req, resp));
    }

    public void mock() {
        when(req.getSession()).thenReturn(session);
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute("userServ")).thenReturn(userServ);
    }
}