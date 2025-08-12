package NyotaHub.ArenaHub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingDTO {

    private Long bookingID;

    @NotNull(message = "Customer ID is required")
    private Long customerID;

    @NotNull(message = "Stadium ID is required")
    private Long stadiumID;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date cannot be in the past")
    private LocalDate bookingDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotBlank(message = "Status is required")
    private String status;
}
