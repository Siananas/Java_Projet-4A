package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientDao clientDao;
    @InjectMocks
    private ClientService clientService;

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
        // When
        when(this.clientService.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void create_user_should_fail_when_user_with_empty_firstname() {
        Client clientWithEmptyName = new Client(1, "Le Pottier", "", "Evie@epfedu.fr", LocalDate.of(2002, 9, 29));
        // When
        try {
            clientService.create(clientWithEmptyName);
            fail("Aucune exception n'a été levée pour un nom de client vide");
        } catch (ServiceException e) {
            // Then
            assertEquals("Le prénom du client ne peut pas être vide et doit compter au minimum 3 caractères.", e.getMessage());
        }
    }

    @Test
    void create_should_fail_when_name_is_too_short() {
        Client shortNameClient = new Client(0, "Yu", "Moriarty", "ym@epf.fr", LocalDate.of(2001, 4, 15));
        ServiceException thrown = assertThrows(ServiceException.class, () -> clientService.create(shortNameClient));
        assertEquals("Le nom du client ne peut pas être vide et doit compter au minimum 3 caractères.", thrown.getMessage());
    }

    @Test
    void create_should_fail_when_firstname_is_too_short() {
        Client shortFirstnameClient = new Client(0, "Yusuke", "M", "yusuke@epf.fr", LocalDate.of(2000, 12, 25));
        ServiceException thrown = assertThrows(ServiceException.class, () -> clientService.create(shortFirstnameClient));
        assertEquals("Le prénom du client ne peut pas être vide et doit compter au minimum 3 caractères.", thrown.getMessage());
    }

    @Test
    void findById_should_fail_when_no_client_found() throws DaoException {
        when(clientDao.findById(100)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findById(100));
    }

    @Test
    void delete_should_fail_when_client_does_not_exist() throws DaoException {
        doThrow(DaoException.class).when(clientDao).findById(anyInt());
        assertThrows(ServiceException.class, () -> clientService.delete(999));
    }

}