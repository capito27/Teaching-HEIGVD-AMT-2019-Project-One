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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    IUsersDAO usersManager;

    static String[] postReqArgs = {"username", "password"};
    static String[] postReqVal = {"username"};
    Utils utils = new Utils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("login", true);
        req.getRequestDispatcher("/WEB-INF/pages/login_register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // login success, redirect to index
        // Check if that user is in the DB
        // TODO : Control form entries
        req.setAttribute("login", true);
        if (utils.CheckRequiredAttributes(req, resp, postReqArgs, "/WEB-INF/pages/login_register.jsp", postReqVal)) {
            User user = usersManager.getUserByUsername(req.getParameter("username"));
            if (user != null) {
                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                byte[] encodedhash = digest.digest(
                        req.getParameter("password").getBytes(StandardCharsets.UTF_8));
                HttpSession httpSession = req.getSession();
                String passInDB = bytesToHex(encodedhash);
                if (user.getPassword().equals(passInDB)) {
                    // auth successful
                    httpSession.setAttribute("user", user);
                } else {
                    // render errors
                    httpSession.setAttribute("user", null);
                    req.setAttribute("error", "Bad username or password !");
                    req.getRequestDispatcher("/WEB-INF/pages/login_register.jsp").forward(req, resp);
                    return;
                }
            } else {
                // render errors
                req.setAttribute("error", "Bad username or password !");
                req.getRequestDispatcher("/WEB-INF/pages/login_register.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/index");
        }
    }

    // Source : https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
