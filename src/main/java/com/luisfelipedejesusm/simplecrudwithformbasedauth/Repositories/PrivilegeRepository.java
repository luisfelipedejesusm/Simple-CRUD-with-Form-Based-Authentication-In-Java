package com.luisfelipedejesusm.simplecrudwithformbasedauth.Repositories;

import com.luisfelipedejesusm.simplecrudwithformbasedauth.Models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String read_privilege);
}
