package Connector;

import API.Abstractions.AGameSettings;
import API.Connector;
import API.Enums.LocalType;
import API.Interfaces.IConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ConnectorTest {

    /**
     * Teste para verificar se ao instanciar um Connector com apenas latitude e longitude, o seu coolDownTimer será 5.
     */
    @Test
    public void ConnectorTestT01() {
        Connector connector = new Connector(1, 2);
        assertEquals(5, connector.getGameSettings().getCooldownTimer());
        assertEquals(LocalType.CONNECTOR, connector.getType());
        assertEquals(1, connector.getID());
        assertEquals(1, connector.getCoordinates().getLatitude());
        assertEquals(2, connector.getCoordinates().getLongitude());
    }

    /**
     * Verificar se o valor de coolDownTimer, corresponde ao valor que o user passou ao instanciar.
     */
    @Test
    public void ConnectorTestT02() {
        Connector connector = new Connector(4, 4, 200, 10);
        String expectedForToString = "Connector{gameSettings=GameSettings{energy=200, cooldownTimer=10}, latitude=4.0, longitude=4.0, ID=2, localType=CONNECTOR}";
        assertEquals(10, connector.getGameSettings().getCooldownTimer());
        assertEquals(LocalType.CONNECTOR, connector.getType());
        assertEquals(expectedForToString, connector.toString());
        assertEquals(2, connector.getID());
        assertEquals(4, connector.getCoordinates().getLatitude());
        assertEquals(4, connector.getCoordinates().getLongitude());
    }



}
