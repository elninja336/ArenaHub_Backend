package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.models.PaymentMethod;
import NyotaHub.ArenaHub.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id);
    }

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod updatedMethod) {
        return paymentMethodRepository.findById(id).map(existingMethod -> {
            existingMethod.setMethodName(updatedMethod.getMethodName());
            existingMethod.setDescription(updatedMethod.getDescription());
            existingMethod.setIsActive(updatedMethod.getIsActive());
            return paymentMethodRepository.save(existingMethod);
        }).orElseThrow(() -> new IllegalArgumentException("PaymentMethod not found with id: " + id));
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
