package epf;

import static org.junit.Assert.assertTrue;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.ui.cli.InterfaceMethodes;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void UiClientCreate() {

    }


    /*public void createTestCombiningScenarios() {
        // Scenario 1: Nom and Prenom are not empty
        Client validClient = new Client("john", "doe");
        when(clientDao.create(any(Client.class))).thenReturn(1L);

        long createdValidClientId = clientService.create(validClient);

        assertThat(createdValidClientId).isNotEqualTo(0);
        assertThat(validClient.getNom()).isEqualTo("JOHN");
        verify(clientDao, times(1)).create(validClient);

        // Reset interactions with mocked clientDao for the next scenario
        reset(clientDao);

        // Scenario 2: Nom is empty
        Client clientWithEmptyNom = new Client("", "doe");

        long createdIdWithEmptyNom = clientService.create(clientWithEmptyNom);

        assertThat(createdIdWithEmptyNom).isEqualTo(0);
        verify(clientDao, never()).create(clientWithEmptyNom);

        // Scenario 3: Prenom is empty
        Client clientWithEmptyPrenom = new Client("john", "");

        long createdIdWithEmptyPrenom = clientService.create(clientWithEmptyPrenom);

        assertThat(createdIdWithEmptyPrenom).isEqualTo(0);
        verify(clientDao, never()).create(clientWithEmptyPrenom);
    }*/
}
