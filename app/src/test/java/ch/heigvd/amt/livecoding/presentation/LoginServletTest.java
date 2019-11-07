package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.IMatchesDAO;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServletTest {


    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    IUsersDAO usersDAO;

    @Mock
    User user;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    Utils utils;

    LoginServlet servlet;

    String[] postReqArgs = {"username", "password"};
    String[] postReqVal = {"username"};

    @BeforeEach
    public void setup() throws IOException {
        servlet = new LoginServlet();
        servlet.usersManager = usersDAO;
        servlet.utils = utils;
    }

    @Test
    void doGetReturnsLoginPage() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/login_register.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        // display login view
        verify(request, atLeastOnce()).setAttribute("login", true);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostHandlesMissingAtributes() throws ServletException, IOException, DuplicateKeyException, SQLException {

        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(false);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("login", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
    }

    @Test
    void doPostHandlesInvalidUser() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(true);
        when(request.getParameter("username")).thenReturn("toto");
        when(usersDAO.getUserByUsername("toto")).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/pages/login_register.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("login", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
        verify(request, atLeastOnce()).getParameter("username");
        verify(request, atLeastOnce()).setAttribute(eq("error"), anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostHandlesInvalidPassword() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(true);
        when(request.getParameter("username")).thenReturn("tata");
        when(usersDAO.getUserByUsername("tata")).thenReturn(user);
        when(request.getParameter("password")).thenReturn("titi");
        when(request.getSession()).thenReturn(session);
        when(user.getPassword()).thenReturn("toto");

        when(request.getRequestDispatcher("/WEB-INF/pages/login_register.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("login", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
        verify(request, atLeastOnce()).getParameter("username");
        verify(request, atLeastOnce()).getParameter("password");
        verify(request, atLeastOnce()).getSession();
        verify(user, atLeastOnce()).getPassword();
        verify(session, atLeastOnce()).setAttribute("user", null);
        verify(request, atLeastOnce()).setAttribute(eq("error"), anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostHandlesValidUserPassword() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(utils.CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)).thenReturn(true);
        when(request.getParameter("username")).thenReturn("tata");
        when(usersDAO.getUserByUsername("tata")).thenReturn(user);
        when(request.getParameter("password")).thenReturn("toto");
        when(request.getSession()).thenReturn(session);
        when(user.getPassword()).thenReturn("31f7a65e315586ac198bd798b6629ce4903d0899476d5741a9f32e2e521b6a66");
        when(request.getContextPath()).thenReturn("/Project-One");

        servlet.doPost(request, response);

        // display login view
        verify(request, atLeastOnce()).setAttribute("login", true);
        verify(utils, atLeastOnce()).CheckRequiredAttributes(request, response, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal);
        verify(request, atLeastOnce()).getParameter("username");
        verify(request, atLeastOnce()).getParameter("password");
        verify(request, atLeastOnce()).getSession();
        verify(user, atLeastOnce()).getPassword();
        verify(session, atLeastOnce()).setAttribute("user", user);
        verify(response, atLeastOnce()).sendRedirect(eq("/Project-One/index"));
    }
}
