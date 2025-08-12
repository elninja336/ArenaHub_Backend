package NyotaHub.ArenaHub.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusID;

    @Column(nullable = false, unique = true)
    private String statusName;

    @Column(length = 500)
    private String description;
}
