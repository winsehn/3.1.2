package web.security.RedirectRoleHandlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.repository.RoleRepository;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RoleRepository roleRepository;

    @Autowired
    public LoginSuccessHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            Role role = roleRepository.findByName(grantedAuthority.getAuthority()).orElse(null);
            if (role != null && role.getRedirect() != null) {
                System.out.println("Redirecting to: " + role.getRedirect());
                response.sendRedirect(role.getRedirect());
                return;
            }
        }
    }
}
