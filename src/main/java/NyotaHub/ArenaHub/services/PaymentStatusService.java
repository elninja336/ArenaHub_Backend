package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.models.PaymentStatus;
import NyotaHub.ArenaHub.repository.PaymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentStatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public PaymentStatus createPaymentStatus(PaymentStatus paymentStatus) {
        return paymentStatusRepository.save(paymentStatus);
    }

    public List<PaymentStatus> getAllPaymentStatuses() {
        return paymentStatusRepository.findAll();
    }

    public Optional<PaymentStatus> getPaymentStatusById(Long id) {
        return paymentStatusRepository.findById(id);
    }

    public PaymentStatus updatePaymentStatus(Long id, PaymentStatus updatedStatus) {
        return paymentStatusRepository.findById(id).map(existingStatus -> {
            existingStatus.setStatusName(updatedStatus.getStatusName());
            existingStatus.setDescription(updatedStatus.getDescription());
            return paymentStatusRepository.save(existingStatus);
        }).orElseThrow(() -> new IllegalArgumentException("PaymentStatus not found with id: " + id));
    }

    public void deletePaymentStatus(Long id) {
        paymentStatusRepository.deleteById(id);
    }
}
