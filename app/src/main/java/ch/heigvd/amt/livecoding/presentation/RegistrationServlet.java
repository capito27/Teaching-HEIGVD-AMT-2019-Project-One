package ch.heigvd.amt.livecoding.presentation;

import ch.heigvd.amt.livecoding.model.User;
import ch.heigvd.amt.livecoding.integration.IUsersDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {

    @EJB
    private IUsersDAO usersManager;

    private static String[] postReqArgs = {"username", "firstname", "lastname", "email", "password"};
    private static String[] postReqVal = {"username", "firstname", "lastname", "email"};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // register success, render index page
        req.setAttribute("register", true);
        if(Utils.CheckRequiredAttributes(req,resp,postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)) {
            User user = User.builder()
                    .username(req.getParameter("username"))
                    .password(req.getParameter("password"))
                    .lastname(req.getParameter("lastname"))
                    .firstname(req.getParameter("firstname"))
                    .email(req.getParameter("email"))
                    .build();
            User userCreated = usersManager.createUser(user);
            if (userCreated != null) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("user", userCreated);
                resp.sendRedirect("/Project-One/index");
                return;
            } else {
                req.getRequestDispatcher("/WEB-INF/pages/login_register.jsp").forward(req, resp);
            }
        }
    }
}
