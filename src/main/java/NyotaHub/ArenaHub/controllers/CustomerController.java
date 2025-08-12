package NyotaHub.ArenaHub.controllers;

import NyotaHub.ArenaHub.dto.CustomerDTO;
import NyotaHub.ArenaHub.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/check-or-create")
    public ResponseEntity<CustomerDTO> checkOrCreateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO result = customerService.findOrCreateCustomer(customerDTO);
        return ResponseEntity.ok(result);
    }
}
