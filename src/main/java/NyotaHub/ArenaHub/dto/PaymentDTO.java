package NyotaHub.ArenaHub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {

    private Long paymentID;

    @NotNull(message = "Booking ID is required")
    private Long bookingID;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment status ID is required")
    private Long statusID;

    @NotNull(message = "Payment date is required")
    private LocalDateTime paymentDate;

    @NotNull(message = "Payment method ID is required")
    private Long methodID;
}
