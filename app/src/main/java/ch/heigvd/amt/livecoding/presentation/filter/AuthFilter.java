package ch.heigvd.amt.livecoding.presentation.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = {"/logout", "/match", "/stadium", "/team", "/user"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        Enumeration<String > attrs = session.getAttributeNames();
        while(attrs.hasMoreElements()){
            System.out.println(attrs.nextElement());
        }

        if (session.getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
