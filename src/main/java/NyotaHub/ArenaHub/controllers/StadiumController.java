package NyotaHub.ArenaHub.controllers;

import NyotaHub.ArenaHub.models.Stadium;
import NyotaHub.ArenaHub.services.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stadiums")
@CrossOrigin(origins = "http://localhost:5173")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @PostMapping
    public ResponseEntity<Stadium> createStadium(@RequestBody Stadium stadium) {
        Stadium created = stadiumService.createStadium(stadium);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Stadium>> getAllStadiums() {
        List<Stadium> stadiums = stadiumService.getAllStadiums();
        return ResponseEntity.ok(stadiums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stadium> getStadiumById(@PathVariable Long id) {
        return stadiumService.getStadiumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stadium> updateStadium(@PathVariable Long id, @RequestBody Stadium stadium) {
        try {
            Stadium updated = stadiumService.updateStadium(id, stadium);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable Long id) {
        stadiumService.deleteStadium(id);
        return ResponseEntity.noContent().build();
    }
}
