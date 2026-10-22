
package com.fixmycampus.repository;

import com.fixmycampus.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {
}
