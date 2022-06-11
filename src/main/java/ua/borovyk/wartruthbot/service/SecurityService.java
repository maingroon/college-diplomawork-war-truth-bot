package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    AuthenticationManager authenticationManager;

    UserDetailsService userDetailsService;

    public Optional<String> findLoggedInUsername() {
        var userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return Optional.ofNullable(((UserDetails) userDetails).getUsername());
        }
        return Optional.empty();
    }

    public void autoLogin(String username, String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

}
