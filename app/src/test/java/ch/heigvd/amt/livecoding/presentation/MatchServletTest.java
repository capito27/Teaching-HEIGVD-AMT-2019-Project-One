package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.IMatchesDAO;
import ch.heigvd.amt.livecoding.integration.IStadiumsDAO;
import ch.heigvd.amt.livecoding.integration.ITeamsDAO;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher dispatch;

    @Mock
    IMatchesDAO matchesDAO;

    @Mock
    IStadiumsDAO stadiumsDAO;

    @Mock
    ITeamsDAO teamsDAO;

    @Mock
    PrintWriter responseWriter;

    @Mock
    HttpSession session;
    @Mock
    User userMock;

    MatchServlet servlet;

    @BeforeEach
    public void setup() throws IOException {
        servlet = new MatchServlet();
        servlet.stadiumsManager = stadiumsDAO;
        servlet.teamsManager = teamsDAO;
        servlet.matchesManager = matchesDAO;
    }

    @Test
    void doGetReturnsMatchesPage() throws ServletException, IOException, DuplicateKeyException, SQLException {
        // verify that all attributes have been set
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(userMock);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatch);

        servlet.doGet(request, response);
        verify(response, atLeastOnce()).setContentType("text/html;charset=UTF-8");
        verify(request, atLeastOnce()).setAttribute(eq("matches"), any());
        verify(request, atLeastOnce()).setAttribute(eq("teams"), any());
        verify(request, atLeastOnce()).setAttribute(eq("stadiums"), any());
        verify(request, atLeastOnce()).setAttribute(eq("matches"), any());
        verify(request, atLeastOnce()).setAttribute(eq("matchPageNumbers"), any());
        verify(request, atLeastOnce()).setAttribute(eq("currentMatchPage"), any());
        verify(request, atLeastOnce()).setAttribute(eq("leftArrow"), any());
        verify(request, atLeastOnce()).setAttribute(eq("rightArrow"), any());
        verify(dispatch, atLeastOnce()).forward(request, response);
    }
}
