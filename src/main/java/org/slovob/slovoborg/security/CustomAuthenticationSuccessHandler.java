package org.slovob.slovoborg.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * This whole class has just one purpose
 * After unauthenticated user presses (dis)like he gets 401 and js redirects him to /login
 * And after he logs in, Spring's default behavior is to get him back to the page he was initially trying to open
 * But in case of pressing (dis)like his initial action was a POST request to /opinion that is basically an API endpoint
 * So there is no sense in trying to show /opinion, main page should be opened instead in this case
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        String targetUrl = determineTargetUrl(request, response);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        DefaultSavedRequest sr = (DefaultSavedRequest) new HttpSessionRequestCache().getRequest(request, response);
        if (sr == null) { // user pressed log in explicitly without trying to access something forbidden first
            return "/";
        }

        String uri = sr.getRequestURI();
        if ("/opinion".equalsIgnoreCase(uri)) {
            return "/";
        }

        return uri;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
