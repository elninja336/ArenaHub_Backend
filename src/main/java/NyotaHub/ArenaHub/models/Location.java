package NyotaHub.ArenaHub.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationID;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String region;

    @Column(length = 1000)
    private String addressDetails;
}
