package NyotaHub.ArenaHub.controllers;

import NyotaHub.ArenaHub.models.PaymentStatus;
import NyotaHub.ArenaHub.services.PaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-statuses")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentStatusController {

    @Autowired
    private PaymentStatusService paymentStatusService;

    @PostMapping
    public ResponseEntity<PaymentStatus> createPaymentStatus(@RequestBody PaymentStatus paymentStatus) {
        PaymentStatus created = paymentStatusService.createPaymentStatus(paymentStatus);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<PaymentStatus>> getAllPaymentStatuses() {
        List<PaymentStatus> statuses = paymentStatusService.getAllPaymentStatuses();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatusById(@PathVariable Long id) {
        return paymentStatusService.getPaymentStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatus paymentStatus) {
        try {
            PaymentStatus updated = paymentStatusService.updatePaymentStatus(id, paymentStatus);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
