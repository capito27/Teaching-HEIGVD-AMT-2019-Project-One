package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.business.Utils;
import ch.heigvd.amt.livecoding.integration.IUsersDAO;
import ch.heigvd.amt.livecoding.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ejb.DuplicateKeyException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    IUsersDAO usersDAO;

    @Mock
    RequestDispatcher requestDispatcher;

    RegistrationServlet servlet;

    @Mock
    User user;

    @Mock
    Utils utils;

    private static String[] postReqArgs = {"username", "firstname", "lastname", "email", "password"};
    private static String[] postReqVal = {"username", "firstname", "lastname", "email"};

    @BeforeEach
    public void setup() throws IOException {
        servlet = new RegistrationServlet();
        servlet.usersManager = usersDAO;
        servlet.utils = utils;
    }

    @Test
    void doPostHandlesMissingParam() throws ServletException, IOException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(false);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("register", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
    }

    @Test
    void doPostHandlesFailedRegistration() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(true);
        when(request.getParameter(anyString())).thenReturn("toto");
        when(usersDAO.createUser(any())).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/pages/login_register.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("register", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
        verify(request, times(5)).getParameter(anyString());
        verify(usersDAO, times(1)).createUser(any());
        verify(request, atLeastOnce()).setAttribute(eq("error"), anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostHandlesSuccessRegistration() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(true);
        when(request.getParameter(anyString())).thenReturn("toto");
        when(usersDAO.createUser(any())).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("register", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
        verify(request, times(5)).getParameter(anyString());
        verify(usersDAO, times(1)).createUser(any());
        verify(session, atLeastOnce()).setAttribute("user", user);
        verify(response, atLeastOnce()).sendRedirect("/Project-One/index");
    }
}
