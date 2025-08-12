package NyotaHub.ArenaHub.repository;

import NyotaHub.ArenaHub.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // Additional query methods can be added here as needed
}
