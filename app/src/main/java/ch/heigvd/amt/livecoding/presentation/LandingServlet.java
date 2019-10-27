package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.services.dao.MatchesManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/index")
public class LandingServlet extends HttpServlet {

    @EJB
    private MatchesManagerLocal matchesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("matches", matchesManager.findAllMatches());
        req.getRequestDispatcher("/WEB-INF/pages/landing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
