package NyotaHub.ArenaHub.repository;


import NyotaHub.ArenaHub.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPhone(String email, String phone);
}

