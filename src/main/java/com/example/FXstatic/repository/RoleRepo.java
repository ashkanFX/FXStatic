package com.example.FXstatic.repository;

import com.example.FXstatic.models.AppRole;
import com.example.FXstatic.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

}
