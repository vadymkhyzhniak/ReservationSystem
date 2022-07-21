package springapplication.rest;

import commonapplication.models.Reservation;
import commonapplication.models.Restaurant;
import commonapplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapplication.service.ReservationService;
import springapplication.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="api/v1/reservation/",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ReservationResource {
    private final ReservationService reservationService;

    @Autowired
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * RESERVATIONS ARE RETRIEVED VIA A GET ON THE USER
     * **/

    /**
     * Add a reservation
     * Returns true if it was successful, false otherwise
     */
    @PostMapping()
    public ResponseEntity<Boolean> addReservation(@RequestBody Reservation reservation) {
        if (reservation.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reservationService.addReservation(reservation));
    }

    /**
     * Delete a reservation
     * This takes the reservationId of a reservation as String
     */
    @DeleteMapping("{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") String reservationId) {
        if(reservationService.deleteReservation(reservationId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }


    /**
     * Confirm a reservation
     * This takes the reservationId of a reservation as String
     */
    @GetMapping("{reservationId}")
    public ResponseEntity<Boolean> confirmReservation(@PathVariable("reservationId") String reservationId) {
        return ResponseEntity.ok(reservationService.confirmReservation(reservationId));
    }
}
