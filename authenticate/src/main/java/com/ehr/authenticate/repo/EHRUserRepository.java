package com.ehr.authenticate.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ehr.authenticate.entity.EHRUserEntity;

@Repository
public interface EHRUserRepository extends JpaRepository<EHRUserEntity, Long> {

	@Query("SELECT u FROM EHRUserEntity u WHERE u.username = :username OR u.email = :email")
	Optional<EHRUserEntity> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    // Alternative by email
    @Query("SELECT u.password FROM EHRUserEntity u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);
}
