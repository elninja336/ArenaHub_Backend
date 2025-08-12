package NyotaHub.ArenaHub.services;

import NyotaHub.ArenaHub.dto.BookingDTO;
import NyotaHub.ArenaHub.models.Booking;
import NyotaHub.ArenaHub.models.Customer;
import NyotaHub.ArenaHub.models.Stadium;
import NyotaHub.ArenaHub.repository.BookingRepository;
import NyotaHub.ArenaHub.repository.CustomerRepository;
import NyotaHub.ArenaHub.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    // Create new booking
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        validateBookingTimes(bookingDTO.getStartTime(), bookingDTO.getEndTime());

        Customer customer = customerRepository.findById(bookingDTO.getCustomerID())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Stadium stadium = stadiumRepository.findById(bookingDTO.getStadiumID())
                .orElseThrow(() -> new IllegalArgumentException("Stadium not found"));

        // Check if requested time slot is available (no overlapping confirmed bookings)
        if (isTimeSlotBooked(stadium.getStadiumID(), bookingDTO.getBookingDate(), bookingDTO.getStartTime(), bookingDTO.getEndTime())) {
            throw new IllegalArgumentException("Booking time conflicts with an existing booking");
        }

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setStadium(stadium);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setStatus(bookingDTO.getStatus());

        booking = bookingRepository.save(booking);
        return mapToDTO(booking);
    }

    // Update existing booking by BookingID
    public BookingDTO updateBooking(Long bookingID, BookingDTO bookingDTO) {
        validateBookingTimes(bookingDTO.getStartTime(), bookingDTO.getEndTime());

        Booking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Customer customer = customerRepository.findById(bookingDTO.getCustomerID())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Stadium stadium = stadiumRepository.findById(bookingDTO.getStadiumID())
                .orElseThrow(() -> new IllegalArgumentException("Stadium not found"));

        // Check for time conflicts excluding current booking
        List<Booking> conflicts = bookingRepository.findByStadium_StadiumIDAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                stadium.getStadiumID(), bookingDTO.getBookingDate(), bookingDTO.getEndTime(), bookingDTO.getStartTime());

        boolean conflictExists = conflicts.stream()
                .anyMatch(b -> !b.getBookingID().equals(bookingID));

        if (conflictExists) {
            throw new IllegalArgumentException("Booking time conflicts with an existing booking");
        }

        booking.setCustomer(customer);
        booking.setStadium(stadium);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setStatus(bookingDTO.getStatus());

        booking = bookingRepository.save(booking);
        return mapToDTO(booking);
    }

    // Check if a stadium is booked during a time slot
    private boolean isTimeSlotBooked(Long stadiumID, java.time.LocalDate bookingDate, java.time.LocalTime startTime, java.time.LocalTime endTime) {
        List<Booking> overlappingBookings = bookingRepository.findByStadium_StadiumIDAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                stadiumID,
                bookingDate,
                endTime,
                startTime);
        return !overlappingBookings.isEmpty();
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookingDTO> getBookingById(Long id) {
        return bookingRepository.findById(id).map(this::mapToDTO);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO mapToDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingID(),
                booking.getCustomer().getCustomerID(),
                booking.getStadium().getStadiumID(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }

    private void validateBookingTimes(java.time.LocalTime startTime, java.time.LocalTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}
