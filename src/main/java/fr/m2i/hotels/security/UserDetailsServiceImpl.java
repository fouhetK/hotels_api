package fr.m2i.hotels.security;

import fr.m2i.hotels.entities.AdminEntity;
import fr.m2i.hotels.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AdminRepository userRepositories;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        AdminEntity user = userRepositories.findByUsername(name);

        if (user == null)
            throw new UsernameNotFoundException("User "+ name +" not found");
        else
            return new UserDetailsImpl(user);
    }
}
