package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.IStadiumsDAO;
import ch.heigvd.amt.livecoding.integration.ITeamsDAO;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/stadiumDetails")
public class StadiumDetailsServlet extends HttpServlet {

    @EJB
    IStadiumsDAO stadiumsManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long stadiumID = Integer.parseInt(req.getParameter("id"));
        Stadium toSend = stadiumsManager.getStadium(stadiumID);
        req.setAttribute("stadium", toSend);
        req.getRequestDispatcher("WEB-INF/pages/stadiumDetails.jsp").forward(req,resp);
        super.doGet(req, resp);
    }
}
