package com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
