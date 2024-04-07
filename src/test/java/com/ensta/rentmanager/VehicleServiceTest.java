package com.ensta.rentmanager;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    VehicleDao vehicleDao;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void count_should_return_correct_number() throws DaoException, ServiceException {
        when(vehicleDao.countVehicles()).thenReturn(5);
        assertEquals(5, vehicleService.count());
    }

    @Test
    void create_should_fail_when_constructeur_is_empty() {
        Vehicle vehicleWithEmptyConstructeur = new Vehicle("","ModelX", 5);
        assertThrows(IllegalArgumentException.class, () -> vehicleService.create(vehicleWithEmptyConstructeur));
    }

    @Test
    void create_should_fail_when_modele_is_empty() {
        Vehicle vehicleWithEmptyModele = new Vehicle("Tesla", "", 5);
        assertThrows(IllegalArgumentException.class, () -> vehicleService.create(vehicleWithEmptyModele));
    }

    @Test
    void create_should_fail_when_nb_places_is_invalid() {
        Vehicle vehicleWithInvalidSeats = new Vehicle("Tesla", "ModelX", 1);
        assertThrows(IllegalArgumentException.class, () -> vehicleService.create(vehicleWithInvalidSeats));
    }

    @Test
    void findById_should_fail_when_no_vehicle_found() throws DaoException {
        when(vehicleDao.findById(100)).thenThrow(DaoException.class);
        assertThrows(IllegalArgumentException.class, () -> vehicleService.findById(100));
    }

    @Test
    void delete_should_return_false_when_no_rows_affected() throws DaoException {
        Vehicle vehicle = new Vehicle("Tesla", "ModelX", 5);
        when(vehicleDao.findById(1)).thenReturn(vehicle);
        when(vehicleDao.delete(vehicle)).thenReturn(0);
        assertFalse(vehicleService.delete(1));
    }

    @Test
    void findAll_should_return_list_of_vehicles() throws DaoException, ServiceException {
        Vehicle vehicle1 = new Vehicle("Tesla", "ModelX", 5);
        Vehicle vehicle2 = new Vehicle("Tesla", "ModelS", 4);
        when(vehicleDao.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));
        assertEquals(2, vehicleService.findAll().size());
    }

    @Test
    void updateVehicle_should_fail_when_dao_exception_occurs() throws DaoException {
        Vehicle vehicleToUpdate = new Vehicle("Tesla", "Model3", 5);
        doThrow(DaoException.class).when(vehicleDao).updateVehicle(vehicleToUpdate);
        assertThrows(ServiceException.class, () -> vehicleService.updateVehicle(vehicleToUpdate));
    }
}
