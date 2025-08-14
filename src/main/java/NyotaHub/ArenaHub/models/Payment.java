package NyotaHub.ArenaHub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Booking booking;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PaymentStatus status;

    private LocalDateTime paymentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "methodID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PaymentMethod method;
}
