package NyotaHub.ArenaHub.repository;

import NyotaHub.ArenaHub.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    // Add custom query methods if needed
}
