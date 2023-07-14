package com.telros.telrostestcase.repository;

import com.telros.telrostestcase.model.role.EnumRole;
import com.telros.telrostestcase.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(EnumRole roleName);
}
