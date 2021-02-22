package com.luisfelipedejesusm.simplecrudwithformbasedauth.Services;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Privilege;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Role;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.RoleRepository;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return new org.springframework.security.core.userdetails.User(
                    "",
                    "",
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByName("ROLE_USER")
                    ))
            );
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities((List<Role>) user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String privilege: privileges){
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(List<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for(Role role : roles){
            collection.addAll(role.getPrivileges());
        }
        for(Privilege privilege : collection){
            privileges.add(privilege.getName());
        }
        return privileges;
    }
}
