package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import web.model.Role;
import web.repository.RoleRepository;
import web.security.RedirectRoleHandlers.LoginSuccessHandler;
import web.security.UserDetailsServiceImpl;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsServiceImpl userDetailsServiceImpl,
                                                   AuthenticationSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                            auth
                                    .requestMatchers("/css/**").permitAll();
                            List<Role> roles = roleRepository.findAll();
                            for (Role role : roles) {
                                var roleName = role.getName();
                                var redirect = role.getRedirect();
                                if (redirect != null) {
                                    auth.requestMatchers(redirect + "/**").hasAuthority(roleName);
                                }
                            }
                            auth.anyRequest().authenticated();
                        }
                )
                .formLogin(login -> login
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .userDetailsService(userDetailsServiceImpl); ;
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(RoleRepository roleRepository) {
        return new LoginSuccessHandler(roleRepository);
    }
}
