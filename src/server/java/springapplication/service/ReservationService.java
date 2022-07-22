package springapplication.service;

import commonapplication.models.Reservation;
import commonapplication.models.Restaurant;
import commonapplication.models.User;
import commonapplication.persistancemanagement.Parser;
import commonapplication.persistancemanagement.Saver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    /**
     * Adds a reservation. It is added to the internal lists in the saver as well
     */
    public Boolean addReservation(Reservation reservation) {
        return Saver.addReservation(reservation);
    }

    /**
     * Deletes a reservation from our internal representation
     */
    public boolean deleteReservation(String reservationId) {
        Restaurant restaurant = Parser.getRestaurantById(Parser.getRestaurantIdFromReservationId(reservationId));
        if(restaurant == null){
            return false;
        }

        Reservation reservation = Parser.getReservationById(reservationId, restaurant);
        if(reservation == null){
            return false;
        }

        return Saver.removeReservation(reservation);
    }

    /**
     * Confirms a reservation
     *
     * @param reservationId
     * @return
     */
    public boolean confirmReservation(String reservationId) {
        Restaurant restaurant = Parser.getRestaurantById(Parser.getRestaurantIdFromReservationId(reservationId));
        if(restaurant == null){
            return false;
        }

        Reservation reservation = Parser.getReservationById(reservationId, restaurant);
        if(reservation == null){
            return false;
        }

        return Saver.confirmReservation(reservation);
    }
}
