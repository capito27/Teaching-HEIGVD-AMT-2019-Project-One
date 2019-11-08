package ch.heigvd.amt.livecoding.business;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class Utils {

    public boolean CheckRequiredAttributes(HttpServletRequest req, HttpServletResponse res, String[] attr, String redirectUrl, String[] redirectAttr) throws ServletException, IOException {
        for (String att : attr) {
            String param = req.getParameter(att);
            if (param == null || param.equals("")) {
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
