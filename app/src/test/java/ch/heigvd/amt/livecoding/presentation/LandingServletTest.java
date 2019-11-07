package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.IMatchesDAO;
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
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LandingServletTest {


    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    IMatchesDAO matchesDAO;

    @Mock
    RequestDispatcher requestDispatcher;

    LandingServlet servlet;

    @BeforeEach
    public void setup() throws IOException {
        servlet = new LandingServlet();
        servlet.matchesManager = matchesDAO;
    }

    @Test
    void doGetHandlesInvalidParam() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/landing.jsp")).thenReturn(requestDispatcher);
        when(matchesDAO.getMatchCount()).thenReturn(100);
        when(request.getParameter("matchListPage")).thenReturn("toto");
        servlet.doGet(request, response);
        verify(matchesDAO, atLeastOnce()).getMatchCount();
        verify(matchesDAO, atLeastOnce()).getMatchesFromOffset(anyInt(), anyInt());
        verify(requestDispatcher,atLeastOnce()).forward(request,response);
    }

    @Test
    void doGetHandlesvalidParam() throws ServletException, IOException, DuplicateKeyException, SQLException {
        when(request.getRequestDispatcher("/WEB-INF/pages/landing.jsp")).thenReturn(requestDispatcher);
        when(matchesDAO.getMatchCount()).thenReturn(100);
        when(request.getParameter("matchListPage")).thenReturn("5");
        servlet.doGet(request, response);
        verify(matchesDAO, atLeastOnce()).getMatchCount();
        verify(matchesDAO, atLeastOnce()).getMatchesFromOffset(20, 5);
        verify(requestDispatcher,atLeastOnce()).forward(request,response);
    }
}
