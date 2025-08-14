package NyotaHub.ArenaHub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDTO {

    private Long roleID;

    @NotBlank(message = "name must not be blank")
    private String name;

    private String description;

    private Boolean isActive;
}
