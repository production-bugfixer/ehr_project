package com.ehr.authenticate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ehr.authenticate.entity.OtpRequestEntity;

public interface OtpRequestRepository extends JpaRepository<OtpRequestEntity, Long> {
}