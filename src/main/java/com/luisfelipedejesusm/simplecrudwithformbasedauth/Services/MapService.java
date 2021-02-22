package com.luisfelipedejesusm.simplecrudwithformbasedauth.Services;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Dto.UserDto;
import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    public UserDto convertToUserDto(User user){
        UserDto userDto = new UserDto(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
        return userDto;
    }
}
