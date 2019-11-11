package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.integration.IMatchesDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/index"})
public class LandingServlet extends HttpServlet {

    private static int matchPerPage = 5;

    @EJB
    IMatchesDAO matchesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int matchPageCount = matchesManager.getMatchCount() / matchPerPage;

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

        if (matchPageCount > 3)
            matchPageNumbers.add(Math.max(5, (Math.min(matchPageCount, currentMatchPage + 2))));

        // the right arrow is always the left number + 5, except when we're at the last 5 numbers

        int rightArrow = Math.min(matchPageCount, matchPageNumbers.get(0) + 5);

        // if the first page number is at 1, we need to decrease the right arrow value by one
        if (matchPageNumbers.get(0) == 1) {
            rightArrow--;
        }

        resp.setContentType("text/html;charset=UTF-8");
        // SQL paging enabled
        //req.setAttribute("matches", matchesManager.getMatchesFromOffset(matchPerPage * (currentMatchPage - 1), matchPerPage));
        // SQL paging disabled
        req.setAttribute("matches", matchesManager.getAllMatches().subList(matchPerPage * (currentMatchPage - 1), matchPerPage));
        req.setAttribute("matchPageNumbers", matchPageNumbers);
        req.setAttribute("currentMatchPage", currentMatchPage);
        req.setAttribute("leftArrow", leftArrow);
        req.setAttribute("rightArrow", rightArrow);
        req.getRequestDispatcher("/WEB-INF/pages/landing.jsp").forward(req, resp);
    }

}
