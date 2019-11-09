package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.ITeamsDAO;
import ch.heigvd.amt.livecoding.model.Team;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/teamDetails")
public class TeamDetailsServlet extends HttpServlet {

    @EJB
    ITeamsDAO teamsManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long teamID = Integer.parseInt(req.getParameter("id"));
        Team toSend = teamsManager.getTeam(teamID);
        req.setAttribute("team", toSend);
        req.getRequestDispatcher("WEB-INF/pages/teamDetails.jsp").forward(req,resp);
        super.doGet(req, resp);
    }
}
