package com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
