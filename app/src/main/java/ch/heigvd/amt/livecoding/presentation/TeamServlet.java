package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.business.Utils;
import ch.heigvd.amt.livecoding.model.Team;
import ch.heigvd.amt.livecoding.integration.ITeamsDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/team")
public class TeamServlet extends HttpServlet {

    @EJB
    ITeamsDAO teamsManager;

    private static String[] postReqArgs = {"action", "name", "country"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("teams", teamsManager.getAllTeams());
        req.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("action").equals("post")) {
            if(new Utils().CheckRequiredAttributes(req, resp, postReqArgs, "/WEB-INF/pages/teams.jsp", postReqArgs)) {
                Team toCreateTeam = Team.builder()
                        .country(req.getParameter("country"))
                        .name(req.getParameter("name"))
                        .build();
                Team teamCreated = teamsManager.createTeam(toCreateTeam);
                if (teamCreated != null) {
                    System.out.println("Creation team");
                    req.setAttribute("confirmation", "Team creation successful");
                    this.doGet(req, resp);
                } else {
                    req.setAttribute("error", "Error in creation");
                    req.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(req, resp);
                }
            }
        } else {
            if(req.getParameter("action").equals("delete")) {
                this.doDelete(req, resp);
            } else if(req.getParameter("action").equals("update")) {
                this.doPut(req, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(teamsManager.deleteTeam(Integer.parseInt(req.getParameter("team")))) {
            // TODO : Put confirmation
            req.setAttribute("confirmation", "Team deleted successful");
            this.doGet(req, resp);
        } else {
            req.setAttribute("error", "Error in deleting");
            req.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(teamsManager.updateTeam(Integer.parseInt(req.getParameter("id")), req.getParameter("name"), req.getParameter("country"))) {
            // TODO : Put confirmation
            req.setAttribute("confirmation", "Team updated successful");
            this.doGet(req, resp);
        } else {
            req.setAttribute("error", "Error in updating");
            req.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(req, resp);
        }
    }
}
