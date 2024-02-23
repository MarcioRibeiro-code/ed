package Portal;

import API.Enums.LocalType;
import API.Enums.TeamType;
import API.Exceptions.IllegalArgumentException;
import API.Interfaces.IPortal;
import API.Player;
import API.Portal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortalTest {

    @Test
    public void testConquerPortal() {
        // Given
        Player player = new Player("John", "SPARKS");
        Portal portal = new Portal("Portal 1", 0.0f, 0.0f);

        // When
        boolean result = portal.conquerPortal(player);

        // Then
        assertTrue(result);
        assertEquals(0, player.getCurrentEnergy());
        assertEquals(TeamType.SPARKS, portal.getStatus());
    }


    @Test
    public void testConquerEnemyPortal() {
        Player player = new Player("Hugo", "GIANTS");
        Portal portal = new Portal("Portal 1", 0.0f, 0.0f);

        boolean result = portal.conquerEnemyPortal(player);

        assertTrue(result);
        assertEquals(0, player.getCurrentEnergy());
        assertEquals(TeamType.GIANTS, portal.getStatus());
    }

    @Test
    public void addEnergyMoreThanThePlayerCan() {
        Player player = new Player("Hugo", "GIANTS");
        Portal portal = new Portal("Portal 1", 0.0f, 0.0f);

        portal.conquerPortal(player);

        assertThrows(IllegalArgumentException.class, () -> portal.addEnergy(player, 120));
    }

    @Test
    public void addEnergyPlayerCanGive() {
        Player player = new Player("Hugo", "GIANTS");
        Portal portal = new Portal("Portal 1", 0.0f, 0.0f);

        //First player need control of portal to add energy
        portal.conquerPortal(player);

        //Like he conquer portal he lost all energy, so he will need reload energy to add energy
        assertEquals(0, player.getCurrentEnergy());
        //assertTrue(portal.addEnergy(player, 100));
    }
}
