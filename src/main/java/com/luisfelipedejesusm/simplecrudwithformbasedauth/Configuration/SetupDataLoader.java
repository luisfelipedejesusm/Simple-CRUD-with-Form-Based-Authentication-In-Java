package com.luisfelipedejesusm.simplecrudwithformbasedauth.Configuration;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Privilege;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Role;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.PrivilegeRepository;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.RoleRepository;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege
        );
        Role admin = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role user = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        User adminUser = createAdminUserIfNotExist(Arrays.asList(admin));

        alreadySetup = true;
    }

    @Transactional
    private User createAdminUserIfNotExist(List<Role> roles) {
        User user = userRepository.findByEmail("admin@admin.com");
        if(user == null){
            user = new User();
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setEmail("admin@admin.com");
            user.setRoles(roles);
            user.setEnabled(true);
            userRepository.save(user);
        }
        return user;
    }

    @Transactional
    private Role createRoleIfNotFound(String roleName, List<Privilege> rolePrivileges) {
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName(roleName);
            role.setPrivileges(rolePrivileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String privilegeName) {
        Privilege privilege = privilegeRepository.findByName(privilegeName);
        if(privilege == null){
            privilege = new Privilege();
            privilege.setName(privilegeName);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
}
