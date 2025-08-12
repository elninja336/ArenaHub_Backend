package NyotaHub.ArenaHub.repository;

import NyotaHub.ArenaHub.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings for a stadium on a particular date overlapping a time range (to check conflicts)
    List<Booking> findByStadium_StadiumIDAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long stadiumID, LocalDate bookingDate, LocalTime endTime, LocalTime startTime);
}
