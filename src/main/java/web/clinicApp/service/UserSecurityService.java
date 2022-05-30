package web.clinicApp.service;

import web.clinicApp.repository.UserRepository;
import web.clinicApp.service.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.clinicApp.entity.User;

import java.util.Set;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                Set.of(new SimpleGrantedAuthority(user.getRole())));
    }
}
