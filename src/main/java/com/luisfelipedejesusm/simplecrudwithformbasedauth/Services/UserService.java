package com.luisfelipedejesusm.simplecrudwithformbasedauth.Services;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Dto.UserDto;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Exceptions.UserAlreadyExistException;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.RoleRepository;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(UserDto userDto) throws UserAlreadyExistException {

        if(userWithSameEmailExist(userDto.getEmail())){
            throw new UserAlreadyExistException(
                    "There is an account with email address: "
                    + userDto.getEmail()
            );
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(false);
        user.setRoles(
                Arrays.asList(
                        roleRepository.findByName("ROLE_USER")
                )
        );

        User insertedUser = userRepository.save(user);
        return insertedUser;
    }

    private boolean userWithSameEmailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }
}
