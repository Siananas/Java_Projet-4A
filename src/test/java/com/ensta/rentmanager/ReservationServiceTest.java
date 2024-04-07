package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationDao reservationDao;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void findReservationById_should_return_reservation() throws DaoException, ServiceException {
        // Arrange
        Reservation expectedReservation = new Reservation(1, 1, LocalDate.now(), LocalDate.now().plusDays(3));
        when(reservationDao.findReservationById(1)).thenReturn(expectedReservation);

        // Act
        Reservation actualReservation = reservationService.findReservationById(1);

        // Assert
        assertEquals(expectedReservation, actualReservation);
    }

    @Test
    void findByClientId_should_return_list_of_reservations() throws DaoException, ServiceException {
        // Arrange
        List<Reservation> expectedReservations = Arrays.asList(new Reservation(1, 1, LocalDate.now(), LocalDate.now().plusDays(3)));
        when(reservationDao.findResaByClientId(1)).thenReturn(expectedReservations);

        // Act
        List<Reservation> actualReservations = reservationService.findByClientId(1);

        // Assert
        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void findAll_should_return_all_reservations() throws DaoException, ServiceException {
        // Arrange
        List<Reservation> expectedReservations = Arrays.asList(
                new Reservation(1, 1, LocalDate.now(), LocalDate.now().plusDays(3)),
                new Reservation(2, 2, LocalDate.now(), LocalDate.now().plusDays(2))
        );
        when(reservationDao.findAll()).thenReturn(expectedReservations);

        // Act
        List<Reservation> actualReservations = reservationService.findAll();

        // Assert
        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void delete_should_succeed_when_reservation_exists() throws DaoException, ServiceException {
        // Arrange
        Reservation reservationToDelete = new Reservation(1, 1, LocalDate.now(), LocalDate.now().plusDays(3));
        when(reservationDao.findReservationById(1)).thenReturn(reservationToDelete);
        when(reservationDao.delete(reservationToDelete)).thenReturn(1);

        // Act
        boolean result = reservationService.delete(1);

        // Assert
        assertTrue(result);
    }

    @Test
    void updateReservation_should_fail_when_dao_exception_occurs() throws DaoException {
        // Arrange
        Reservation reservationToUpdate = new Reservation(1, 1, LocalDate.now(), LocalDate.now().plusDays(3));
        doThrow(DaoException.class).when(reservationDao).updateReservation(reservationToUpdate);

        // Act & Assert
        assertThrows(ServiceException.class, () -> reservationService.updateReservation(reservationToUpdate));
    }
}
