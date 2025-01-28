package test.api.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import test.api.entities.User;
import test.api.repositories.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering load user by username...");
        User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        logger.info("User authenticated");
        return new CustomUserDetails(user);
    }
}
