package NyotaHub.ArenaHub.repository;

import NyotaHub.ArenaHub.models.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    // Add custom queries if needed
}
