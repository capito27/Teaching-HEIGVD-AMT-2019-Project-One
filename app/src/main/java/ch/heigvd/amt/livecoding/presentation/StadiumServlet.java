package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.services.dao.StadiumsManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/stadium")
public class StadiumServlet extends HttpServlet {

    @EJB
    private StadiumsManagerLocal stadiumsManager;

    private static String[] postReqArgs = {"action", "name", "location", "viewers"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("stadiums", stadiumsManager.getAllStadiums());
        req.getRequestDispatcher("/WEB-INF/pages/stadiums.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("action").equals("post")) {
            if(Utils.CheckRequiredAttributes(req, resp, postReqArgs, "/WEB-INF/pages/stadiums.jsp", postReqArgs)) {
                Stadium toCreateTeam = Stadium.builder()
                        .location(req.getParameter("location"))
                        .name(req.getParameter("name"))
                        .viewerPlaces(Integer.parseInt(req.getParameter("viewers")))
                        .build();
                Stadium teamCreated = stadiumsManager.createStadium(toCreateTeam);
                if (teamCreated != null) {
                    System.out.println("Creation Stadium");
                    this.doGet(req, resp);
                } else {
                    req.setAttribute("error", "Error in creation");
                    req.getRequestDispatcher("/WEB-INF/pages/stadiums.jsp").forward(req, resp);
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
        if(stadiumsManager.deleteStadium(Integer.parseInt(req.getParameter("stadium")))) {
            // TODO : Put confirmation
            this.doGet(req, resp);
        } else {
            req.setAttribute("error", "Error in deleting");
            req.getRequestDispatcher("/WEB-INF/pages/stadiums.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(stadiumsManager.updateStadium(Integer.parseInt(req.getParameter("id")), req.getParameter("name"), req.getParameter("location"), Integer.parseInt(req.getParameter("viewers")))) {
            // TODO : Put confirmation
            this.doGet(req, resp);
        } else {
            req.setAttribute("error", "Error in updating");
            req.getRequestDispatcher("/WEB-INF/pages/stadiums.jsp").forward(req, resp);
        }
    }
}
