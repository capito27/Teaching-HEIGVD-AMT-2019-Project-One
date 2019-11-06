package ch.heigvd.amt.livecoding.presentation.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/logout", "/match", "/stadium", "/team", "/user", "/index", "/login")));

    private static final Set<String> RESTRICTED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/logout", "/match", "/stadium", "/team", "/user")));

    private static final Set<String> ADMIN_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/stadium", "/team")));

    // Filter method inspired from https://stackoverflow.com/questions/31318397/webfilter-exclude-url-pattern
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        // if the path of the request is not the current project, we ignore it
        if (!path.startsWith("/Project-One")) {
            chain.doFilter(request, response);
            return;
        }

        // if the path is not in the allowed lists, redirect to root
        if (!ALLOWED_PATHS.contains(path) && !Pattern.matches("\\/assets\\/.*", path)) {
            response.sendRedirect(request.getContextPath() + "/index");
            return;
        }

        // if the path is in the restricted list, and the user is not logged, redirect to login
        if (RESTRICTED_PATHS.contains(path) && (session == null || session.getAttribute("user") == null)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
