package NyotaHub.ArenaHub.controllers;

import NyotaHub.ArenaHub.dto.RoleDTO;
import NyotaHub.ArenaHub.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Create a new Role
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO savedRole = roleService.createRole(roleDTO);
            return ResponseEntity.ok(savedRole);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null); // Optionally return error message in body
        }
    }

    // Update an existing Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO) {
        if (!id.equals(roleDTO.getRoleID())) {
            return ResponseEntity.badRequest().build();  // ID mismatch
        }
        try {
            RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null); // Optionally return error message in body
        }
    }

    // Get all Roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Get Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Optional<RoleDTO> roleOpt = roleService.getRoleById(id);
        return roleOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
