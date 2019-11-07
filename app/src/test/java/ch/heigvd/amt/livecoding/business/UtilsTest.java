package ch.heigvd.amt.livecoding.business;

import ch.heigvd.amt.livecoding.presentation.LogoutServlet;
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
public class UtilsTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher requestDispatcher;

    Utils utils;

    String[] postReqArgs = {"username", "password"};
    String[] postReqVal = {"username"};


    @BeforeEach
    public void setup() throws IOException {
        utils = new Utils();
    }

    @Test
    void doCheckRequiredAttributesAllAttributes() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getParameter("username")).thenReturn("tata");
        when(request.getParameter("password")).thenReturn("titi");


        utils.CheckRequiredAttributes(request, response, postReqArgs, "toto", postReqVal);

        verify(request, atLeastOnce()).getParameter("username");
        verify(request, atLeastOnce()).getParameter("password");
    }

    @Test
    void doCheckRequiredAttributesAllMissingAttributes() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getParameter("username")).thenReturn("tata");
        when(request.getParameter("password")).thenReturn(null);
        when(request.getParameter("username")).thenReturn("titi");
        when(request.getRequestDispatcher("toto")).thenReturn(requestDispatcher);


        utils.CheckRequiredAttributes(request, response, postReqArgs, "toto", postReqVal);

        verify(request, atLeastOnce()).getParameter("username");
        verify(request, atLeastOnce()).getParameter("password");
        verify(request, atLeastOnce()).setAttribute( eq("error"),anyString());

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}
