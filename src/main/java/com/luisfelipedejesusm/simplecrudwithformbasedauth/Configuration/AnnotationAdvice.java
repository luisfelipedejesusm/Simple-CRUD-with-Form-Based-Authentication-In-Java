package com.luisfelipedejesusm.simplecrudwithformbasedauth.Configuration;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories.UserRepository;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Services.UserDetailServiceImpl;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class AnnotationAdvice {

    @Autowired
    private UserRepository userRepository;

    private String principalUsername;

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof UserDetails){
            return userRepository.findByEmail(((UserDetails) user).getUsername());
        }
        return null;
    }
}
