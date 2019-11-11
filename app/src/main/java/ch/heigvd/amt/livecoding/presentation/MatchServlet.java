package ch.heigvd.amt.livecoding.presentation;


import ch.heigvd.amt.livecoding.business.Utils;
import ch.heigvd.amt.livecoding.model.User;
import ch.heigvd.amt.livecoding.integration.IMatchesDAO;
import ch.heigvd.amt.livecoding.integration.IStadiumsDAO;
import ch.heigvd.amt.livecoding.integration.ITeamsDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/match")
public class MatchServlet extends HttpServlet {

    private static int matchPerPage = 5;

    @EJB
    IMatchesDAO matchesManager;

    @EJB
    ITeamsDAO teamsManager;

    @EJB
    IStadiumsDAO stadiumsManager;

    private static String[] postReqArgs = {"action", "score1", "score2", "team1", "team2", "stadium"};

    Utils utils = new Utils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        int matchPageCount = (int) Math.ceil((double) matchesManager.getMatchCountFromUser(user.getId()) / matchPerPage);
        System.out.println(matchPageCount);
        // try to parse an integer from the parameter, if we can't assume it's 1
        int currentMatchPage = 1;
        try {
            currentMatchPage = Integer.parseInt(req.getParameter("matchListPage"));
        } catch (Exception e) { /* ignored */ }

        if (currentMatchPage > matchPageCount)
            currentMatchPage = matchPageCount;


        ArrayList<Integer> matchPageNumbers = new ArrayList<>();
        // the left arrow is always the greater between 1 and page_nbr - 3, so that we can return either to the start of the listing, or go back 3 spots

        int leftArrow = Math.max(1, currentMatchPage - 3);

        // the first page number is always either 2, or the last page number minus 4 or the current page number minus 2
        matchPageNumbers.add(Math.max(1, (Math.min(matchPageCount - 4, currentMatchPage - 2))));

        if (matchPageCount > 1)
            matchPageNumbers.add(Math.max(2, (Math.min(matchPageCount - 3, currentMatchPage - 1))));

        if (matchPageCount > 2)
            matchPageNumbers.add(Math.max(3, (Math.min(matchPageCount - 2, currentMatchPage))));

        if (matchPageCount > 3)
            matchPageNumbers.add(Math.max(4, (Math.min(matchPageCount - 1, currentMatchPage + 1))));

        if (matchPageCount > 4)
            matchPageNumbers.add(Math.max(5, (Math.min(matchPageCount, currentMatchPage + 2))));

        // the right arrow is always the left number + 5, except when we're at the last 5 numbers

        int rightArrow = Math.min(matchPageCount, matchPageNumbers.get(0) + 5);

        // if the first page number is at 1,
        if (leftArrow == 1) {

        }

        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("matches", matchesManager.getMatchesFromUserAndOffset(user.getId(), matchPerPage * (currentMatchPage - 1), matchPerPage));
        req.setAttribute("teams", teamsManager.getAllTeams());
        req.setAttribute("stadiums", stadiumsManager.getAllStadiums());
        req.setAttribute("matchPageNumbers", matchPageNumbers);
        req.setAttribute("currentMatchPage", currentMatchPage);
        req.setAttribute("leftArrow", leftArrow);
        req.setAttribute("rightArrow", rightArrow);
        req.getRequestDispatcher("/WEB-INF/pages/matches.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("action"));
        if(req.getParameter("action").equals("post")) {
            if (utils.CheckRequiredAttributes(req, resp, postReqArgs, "/WEB-INF/pages/matches.jsp", postReqArgs)) {
                User user = (User) req.getSession().getAttribute("user");
                if (matchesManager.createMatch(Integer.parseInt(req.getParameter("score1")),
                        Integer.parseInt(req.getParameter("score2")),
                        Integer.parseInt(req.getParameter("team1")),
                        Integer.parseInt(req.getParameter("team2")),
                        Integer.parseInt(req.getParameter("stadium")),
                        (int) user.getId()) != null) {
                    req.setAttribute("confirmation", "Match creation successful");
                    this.doGet(req, resp);
                } else {
                    req.setAttribute("error", "Error in creation");
                    req.getRequestDispatcher("/WEB-INF/pages/matches.jsp").forward(req, resp);
                }
            }
        } else {
            if(req.getParameter("action").equals("update")){
                this.doPut(req, resp);
            } else if(req.getParameter("action").equals("delete")){
                this.doDelete(req, resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (matchesManager.deleteMatch(Integer.parseInt(req.getParameter("match")))) {
            req.setAttribute("confirmation", "Match delete successful");
            this.doGet(req, resp);
        } else {
            req.setAttribute("error", "Error in creation");
            req.getRequestDispatcher("/WEB-INF/pages/matches.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (utils.CheckRequiredAttributes(req, resp, postReqArgs, "/WEB-INF/pages/matches.jsp", postReqArgs)) {
            User user = (User) req.getSession().getAttribute("user");
            if (matchesManager.updateMatch(Integer.parseInt(req.getParameter("id")),
                    Integer.parseInt(req.getParameter("score1")),
                    Integer.parseInt(req.getParameter("score2")),
                    (long) Integer.parseInt(req.getParameter("team1")),
                    (long) Integer.parseInt(req.getParameter("team2")),
                    (long) Integer.parseInt(req.getParameter("stadium")),
                    user.getId())) {
                req.setAttribute("confirmation", "Update of Match successful");
                this.doGet(req, resp);
            } else {
                req.setAttribute("error", "Error in creation");
                req.getRequestDispatcher("/WEB-INF/pages/matches.jsp").forward(req, resp);
            }
        }
    }
}
