package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.CustomerDTO;
import NyotaHub.ArenaHub.models.Customer;
import NyotaHub.ArenaHub.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Find or create a customer by email and phone, returning DTO
    public CustomerDTO findOrCreateCustomer(CustomerDTO customerDTO) {
        Optional<Customer> customerOpt = customerRepository.findByEmailAndPhone(customerDTO.getEmail(), customerDTO.getPhone());

        Customer customer = customerOpt.orElseGet(() -> {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(customerDTO.getEmail());
            newCustomer.setPhone(customerDTO.getPhone());
            return customerRepository.save(newCustomer);
        });

        return mapToDTO(customer);
    }

    // Manual mapping entity -> DTO
    private CustomerDTO mapToDTO(Customer customer) {
        return new CustomerDTO(customer.getCustomerID(), customer.getEmail(), customer.getPhone());
    }

    // (Optional) Manual mapping DTO -> entity for updates if needed
}
