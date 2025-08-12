package NyotaHub.ArenaHub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDTO {

    private Long locationID; // Present in responses

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Region is mandatory")
    private String region;

    private String addressDetails;
}
