package NyotaHub.ArenaHub.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodID;

    @Column(nullable = false, unique = true)
    private String methodName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;
}
