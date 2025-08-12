package NyotaHub.ArenaHub.repository;

import NyotaHub.ArenaHub.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    // Custom queries if needed in the future
}
