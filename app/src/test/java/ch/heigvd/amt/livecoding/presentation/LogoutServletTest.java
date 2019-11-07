package ch.heigvd.amt.livecoding.presentation;

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
public class LogoutServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher requestDispatcher;

    LogoutServlet servlet;


    @BeforeEach
    public void setup() throws IOException {
        servlet = new LogoutServlet();
    }


    @Test
    void doGetReturnsLoginPageAndLogsOut() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("toto");

        servlet.doGet(request, response);

        verify(request, atLeastOnce()).getSession();
        verify(session,atLeastOnce()).invalidate();
        verify(response, atLeastOnce()).sendRedirect("toto/index");
    }

}
