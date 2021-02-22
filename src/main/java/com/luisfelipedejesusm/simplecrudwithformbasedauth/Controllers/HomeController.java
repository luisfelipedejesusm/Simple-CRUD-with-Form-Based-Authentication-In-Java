package com.luisfelipedejesusm.simplecrudwithformbasedauth.Controllers;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Dto.UserDto;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Exceptions.UserAlreadyExistException;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String Index(){
        return "index";
    }

    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @GetMapping("/register")
    public String Register(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String RegisterUserPost(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ){
        model.addAttribute("user", userDto);

        if(bindingResult.hasErrors()){
            return "register";
        }

        try{
            User user = userService.save(userDto);
        }catch(UserAlreadyExistException e){
            bindingResult.rejectValue("email", "invalid email",e.getMessage());
            return "register";
        }
        
        return "redirect:/";
    }

}
