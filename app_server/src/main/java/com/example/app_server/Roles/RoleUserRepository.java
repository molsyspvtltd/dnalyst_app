package com.example.app_server.Roles;

import com.example.app_server.security.EncryptionUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, String> {
    Optional<RoleUser> findByEmail(String email);


    List<RoleUser> findByRole(Role role);
    boolean existsByEmail(String email);

    default List<RoleUser> findAllAdmins() {
        return findByRole(Role.ADMIN);
    }
    @Query("SELECT MAX(u.id) FROM RoleUser u WHERE u.id LIKE :prefix")
    String findLastIdByPrefix(@Param("prefix") String prefix);
    List<RoleUser> findBySubAdmin(RoleUser subAdmin);


    default List<RoleUser> findAllSubAdmins() {
        return findByRole(Role.SUB_ADMIN);
    }

    List<RoleUser> findByManagedBy(RoleUser subAdmin);

    default List<RoleUser> findAllStaff() {
        return findByRole(Role.STAFF);
    }
}
