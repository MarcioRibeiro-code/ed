package Player;

import API.Enums.TeamType;
import API.Exceptions.IllegalArgumentException;
import API.Interfaces.IPlayer;
import API.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private static final String TEST_NAME = "John Doe";
    private static final String TEST_TEAM_GIANTS = "GIANTS";
    private static final String TEST_TEAM_SPARKS = "SPARKS";
    private static final int TEST_LEVEL = 1;
    private static final int TEST_EXPERIENCE_POINTS = 1000;
    private static final int TEST_CURRENT_ENERGY = 80;

    @Test
    public void testCreatePlayerWithGiantsTeam() {
        IPlayer player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        assertEquals(TeamType.GIANTS, player.getTeam());
        assertEquals(TEST_NAME, player.getName());
    }

    @Test
    public void testCreatePlayerWithSparksTeam() {
        IPlayer player = new Player(TEST_NAME, TEST_TEAM_SPARKS);
        assertEquals(TEST_NAME, player.getName());
        assertEquals(TeamType.SPARKS, player.getTeam());
    }

    @Test
    public void testGetAndSetName() {
        IPlayer player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        String newName = "Jane Doe";
        player.setName(newName);
        assertEquals(newName, player.getName());
    }

    @Test
    public void testGetAndSetTeam() {
        Player player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        player.setTeam(TeamType.SPARKS);
        assertEquals(TeamType.SPARKS, player.getTeam());
    }

    @Test
    public void testGetLevel() {
        Player player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        player.setExperiencePoints(TEST_EXPERIENCE_POINTS);
        assertEquals(TEST_LEVEL, player.getLevel());
    }

    @Test
    public void testGetAndSetExperiencePoints() {
        Player player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        player.setExperiencePoints(TEST_EXPERIENCE_POINTS);
        assertEquals(TEST_EXPERIENCE_POINTS, player.getExperiencePoints());
    }

    @Test
    public void testGetAndSetCurrentEnergy() {
        Player player = new Player(TEST_NAME, TEST_TEAM_GIANTS);
        player.setCurrentEnergy(TEST_CURRENT_ENERGY);
        assertEquals(TEST_CURRENT_ENERGY, player.getCurrentEnergy());
    }
}
