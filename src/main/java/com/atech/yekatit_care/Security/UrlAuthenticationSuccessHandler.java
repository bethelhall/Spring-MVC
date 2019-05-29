package com.atech.yekatit_care.Security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class UrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isDoctor = false;
        boolean isAdmin = false;
        boolean isReceptionist = false;
        boolean isLabTechnician = false;

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        System.out.println(authorities);
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("DOCTOR")) {
                isDoctor = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("RECEPTIONIST")) {
                isReceptionist = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("LABORATORY_TECHNICIAN")) {
                isLabTechnician = true;
                break;
            }

        }

        if (isDoctor) {
            return "/doctor/home";
        } else if (isAdmin) {
            return "/admin/home";
        } else if (isReceptionist) {
            return "/receptionist/home";
        } else if (isLabTechnician) {
            return "/laboratory/home";
        } else {
            throw new IllegalStateException();
        }
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }


}
