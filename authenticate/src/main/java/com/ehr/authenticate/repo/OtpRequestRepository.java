package com.ehr.authenticate.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehr.authenticate.entity.OtpRequestEntity;
@Repository
public interface OtpRequestRepository extends JpaRepository<OtpRequestEntity, Long> {
	Optional<OtpRequestEntity> findByRequestId(String requestId);
}