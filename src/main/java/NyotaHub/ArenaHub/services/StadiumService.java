package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.models.Location;
import NyotaHub.ArenaHub.models.Stadium;
import NyotaHub.ArenaHub.repository.LocationRepository;
import NyotaHub.ArenaHub.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StadiumService {

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private LocationRepository locationRepository;

    public Stadium createStadium(Stadium stadium) {
        // Validate location exists
        if (stadium.getLocation() == null || stadium.getLocation().getLocationID() == null) {
            throw new IllegalArgumentException("Location must be provided.");
        }

        Optional<Location> location = locationRepository.findById(stadium.getLocation().getLocationID());
        if (location.isEmpty()) {
            throw new IllegalArgumentException("Provided Location does not exist.");
        }
        stadium.setLocation(location.get());
        return stadiumRepository.save(stadium);
    }

    public List<Stadium> getAllStadiums() {
        return stadiumRepository.findAll();
    }

    public Optional<Stadium> getStadiumById(Long id) {
        return stadiumRepository.findById(id);
    }

    public void deleteStadium(Long id) {
        stadiumRepository.deleteById(id);
    }

    public Stadium updateStadium(Long id, Stadium updatedStadium) {
        return stadiumRepository.findById(id).map(existing -> {

            existing.setName(updatedStadium.getName());
            existing.setPlayerCapacity(updatedStadium.getPlayerCapacity());
            existing.setPrice(updatedStadium.getPrice());

            if (updatedStadium.getLocation() != null && updatedStadium.getLocation().getLocationID() != null) {
                var locOpt = locationRepository.findById(updatedStadium.getLocation().getLocationID());
                if (locOpt.isPresent()) {
                    existing.setLocation(locOpt.get());
                } else {
                    throw new IllegalArgumentException("Provided Location does not exist.");
                }
            }

            return stadiumRepository.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Stadium not found with id: " + id));
    }
}
