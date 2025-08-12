package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.LocationDTO;
import NyotaHub.ArenaHub.models.Location;
import NyotaHub.ArenaHub.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Create or update Location
    public LocationDTO saveLocation(LocationDTO locationDTO) {
        Location location;

        if (locationDTO.getLocationID() != null) {
            location = locationRepository.findById(locationDTO.getLocationID())
                    .orElse(new Location());
        } else {
            location = new Location();
        }

        location.setCity(locationDTO.getCity());
        location.setRegion(locationDTO.getRegion());
        location.setAddressDetails(locationDTO.getAddressDetails());

        Location savedLocation = locationRepository.save(location);
        return mapToDTO(savedLocation);
    }

    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<LocationDTO> getLocationById(Long id) {
        return locationRepository.findById(id).map(this::mapToDTO);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    private LocationDTO mapToDTO(Location location) {
        return new LocationDTO(
                location.getLocationID(),
                location.getCity(),
                location.getRegion(),
                location.getAddressDetails());
    }
}
