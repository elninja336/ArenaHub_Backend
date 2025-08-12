package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.PaymentDTO;
import NyotaHub.ArenaHub.models.*;
import NyotaHub.ArenaHub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = mapToEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return mapToDTO(payment);
    }

    public PaymentDTO updatePayment(Long paymentID, PaymentDTO paymentDTO) {
        Payment updatedPayment = paymentRepository.findById(paymentID).map(existingPayment -> {
            existingPayment.setAmount(paymentDTO.getAmount());
            existingPayment.setPaymentDate(paymentDTO.getPaymentDate());

            Booking booking = bookingRepository.findById(paymentDTO.getBookingID())
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            PaymentStatus status = paymentStatusRepository.findById(paymentDTO.getStatusID())
                    .orElseThrow(() -> new IllegalArgumentException("Payment Status not found"));
            PaymentMethod method = paymentMethodRepository.findById(paymentDTO.getMethodID())
                    .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

            existingPayment.setBooking(booking);
            existingPayment.setStatus(status);
            existingPayment.setMethod(method);
            return paymentRepository.save(existingPayment);
        }).orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + paymentID));

        return mapToDTO(updatedPayment);
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaymentDTO> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(this::mapToDTO);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentID(),
                payment.getBooking().getBookingID(),
                payment.getAmount(),
                payment.getStatus().getStatusID(),
                payment.getPaymentDate(),
                payment.getMethod().getMethodID()
        );
    }

    private Payment mapToEntity(PaymentDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingID())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        PaymentStatus status = paymentStatusRepository.findById(dto.getStatusID())
                .orElseThrow(() -> new IllegalArgumentException("Payment Status not found"));
        PaymentMethod method = paymentMethodRepository.findById(dto.getMethodID())
                .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(dto.getAmount());
        payment.setStatus(status);
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setMethod(method);
        return payment;
    }
}
