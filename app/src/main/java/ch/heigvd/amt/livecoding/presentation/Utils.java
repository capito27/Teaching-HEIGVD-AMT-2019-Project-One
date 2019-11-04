package ch.heigvd.amt.livecoding.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {

    static public boolean CheckRequiredAttributes(HttpServletRequest req, HttpServletResponse res, String[] attr, String redirectUrl, String[] redirectAttr) throws ServletException, IOException {
        for (String att : attr) {
            if (req.getParameter(att) == null || req.getParameter(att).equals("")) {
                req.setAttribute("error", "missing required parameters");
                for (String errAttr : redirectAttr) {
                    req.setAttribute(errAttr, req.getParameter(errAttr));
                }
                req.getRequestDispatcher(redirectUrl).forward(req, res);
                return false;
            }
        }
        return true;
    }
}
