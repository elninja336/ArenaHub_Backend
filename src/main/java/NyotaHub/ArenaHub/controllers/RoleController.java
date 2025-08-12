package NyotaHub.ArenaHub.controllers;

import NyotaHub.ArenaHub.dto.RoleDTO;
import NyotaHub.ArenaHub.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Create or update a role
    @PostMapping
    public ResponseEntity<RoleDTO> createOrUpdateRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO savedRole = roleService.saveRole(roleDTO);
        return ResponseEntity.ok(savedRole);
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Get role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
