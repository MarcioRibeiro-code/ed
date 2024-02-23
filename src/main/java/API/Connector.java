package API;

import API.Abstractions.AGameSettings;
import API.Enums.LocalType;
import API.Interfaces.IConnector;
import API.Interfaces.IPlayer;
import com.so.Collections.Map.HashMap;

import java.util.Scanner;

/**
 * Classe que representa um connector no jogo
 * <p>
 *   Um connector é um local que permite aos jogadores recarregarem a sua energia
 *   Um connector tem um Cooldown Timer que impede os jogadores de recarregarem a sua energia demasiadas vezes
 *   Um connector tem um limite de energia que os jogadores podem recarregar
 *
 *   @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Connector extends Local implements IConnector {
  private static final int DEFAULT_SIZE = 20;
  private static final int DEFAULT_COOLDOWN_TIMER = 5;

  private static final int maxEnergy = 300;
  private static final int minEnergy = 100;
  private static final int DEFAULT_ENERGY = (int) (Math.random() * (maxEnergy - minEnergy + 1)) + minEnergy;
  private final AGameSettings gameSettings;

  private HashMap<IPlayer, Integer> players = new HashMap<>(DEFAULT_SIZE);


  /**
   * Metodo que permite ao jogador recarregar a sua energia
   * <p>
   *   O jogador só pode recarregar a sua energia se o cooldown timer tiver passado
   *   O jogador só pode recarregar a sua energia até ao máximo de energia do connector
   *   O jogador tem pontos de experiencia por recarregar a sua energia
   *   É guardado o tempo em que o jogador recarregou a sua energia
   *   É mostrado no ecrã que o jogador recarregou a sua energia, com a quantidade de energia recarregada
   *
   * @param player player que vai recarregar a sua energia
   */
  public void reload_energy(IPlayer player) {
    int time = SimulatePlay.getTime();
    if (players.isEmpty() || (players.containsKey(player) && ((time - players.get(player)) < getGameSettings().getCooldownTimer()))) {
      return;
    }
    int energyToBeReloaded = Math.min(this.gameSettings.getEnergy(), player.getDefaultEnergy() - player.getCurrentEnergy());
    player.setCurrentEnergy(player.getCurrentEnergy() + energyToBeReloaded);
    player.setExperiencePoints(player.getExperiencePoints() + GlobalSettings.reloadEnergyInConnector);
    player.getLevel();
    players.put(player, time);
    System.out.println("Player " + player.getName() + " reloaded with " + energyToBeReloaded + " energy");
  }


  /**
   * Construtor de um Connector, sem cooldown timer.
   * É predefinido o valor de cooldownTimer para 5 e a energia de um connector para um valor aleatório entre (100 - 300).
   * Ao instanciar um connector, fica guardado no LocalType que é um CONNECTOR
   *
   * @param latitude  Latitude do Connector
   * @param longitude Longitude do Connector
   */
  public Connector(float latitude, float longitude) {
    super(latitude, longitude);
    super.localType = LocalType.CONNECTOR;
    this.gameSettings = new GameSettings(DEFAULT_COOLDOWN_TIMER, DEFAULT_ENERGY);
  }

  /**
   * Construtor de um Connector, com cooldown timer.
   * O Valor do cooldownTimer e energy do connector é definido pelo utilizador.
   *
   * @param latitude      Latitude do Connector
   * @param longitude     Longitude do Connector
   * @param energy        Energia do Connector
   * @param cooldownTimer Tempo de espera para o jogador poder carregar no Connector novamente
   */
  public Connector(float latitude, float longitude, int energy, int cooldownTimer) {
    super(latitude, longitude);
    this.gameSettings = new GameSettings(cooldownTimer, energy);
    super.localType = LocalType.CONNECTOR;
  }

  /**
   * Metodo que retorna a classe GameSettings do connector
   * <p>
   *   GameSettings é uma classe que contem as configurações do jogo
   *   GameSettings contem o cooldown timer e a energia do connector
   *
   * @return GameSettings do connector
   */
  public GameSettings getGameSettings() {
    return (GameSettings) gameSettings;
  }

  /**
   * Inner class que representa as configurações do Connector
   * <p>
   *   GameSettings é uma classe que contem as configurações do jogo
   *   GameSettings contem o cooldown timer e a energia do connector
   */
  public class GameSettings extends AGameSettings {
    private int cooldownTimer;

    /**
     * Construtor de GameSettings
     * <p>
     *   O cooldown timer é o tempo que o jogador tem de esperar para poder recarregar a sua energia novamente
     *   O cooldown timer está definido no ficheiro de configuração (GameSettings)
     *   A energia é o valor máximo de energia que o jogador pode recarregar no connector
     *   A energia está definida no ficheiro de configuração (GameSettings)
     *
     * @param cooldownTimer Tempo de espera para o jogador poder carregar no Connector novamente
     * @param energy        Energia do Connector
     */
    public GameSettings(int cooldownTimer, int energy) {
      super(energy);
      this.cooldownTimer = cooldownTimer;
    }

    /**
     * Metodo que retorna a energia do connector
     * @return energia do connector
     */
    @Override
    public int getEnergy() {
      return super.getEnergy();
    }

    /**
     * Metodo que retorna o cooldown timer do connector
     * @return cooldown timer do connector
     */
    public int getCooldownTimer() {
      return this.cooldownTimer;
    }

    /**
     * Metodo que permite alterar a energia do connector
     * @param energy nova energia do connector
     */
    public void setEnergy(int energy) {
      super.setEnergy(energy);
    }

    /**
     * Metodo que permite alterar o cooldown timer do connector
     * @param cooldownTimer novo cooldown timer do connector
     */
    public void setCooldownTimer(int cooldownTimer) {
      this.cooldownTimer = cooldownTimer;
    }

    /**
     * Metodo que retorna uma string com as informações do GameSettings
     * @return String com as informações do GameSettings
     */
    @Override
    public String toString() {
      return "GameSettings{" +
          super.toString() + ", " +
          "cooldownTimer=" + cooldownTimer +
          '}';
    }

  }

  /**
   * Metodo que retorna uma string com as informações do Connector
   * @return String com as informações do Connector
   */
  @Override
  public String toString() {
    return "Connector{" +
        "gameSettings=" + gameSettings +
        ", latitude=" + super.coordinates.getLatitude() +
        ", longitude=" + super.coordinates.getLongitude() +
        ", ID=" + ID +
        ", localType=" + localType +
        '}';
  }

  /**
   * Metodo que permite um jogador realizar uma ação no Connector
   * <p>
   *   O jogador pode recarregar a sua energia no Connector
   *   O jogador pode voltar atrás no menu principal
   * @param player jogador que vai realizar a ação
   */
  @Override
  public void menu(IPlayer player) {
    System.out.println("1 - Reload Energy");
    System.out.println("2 - Back");
    Scanner scanner = new Scanner(System.in);
    int option = scanner.nextInt();
    switch (option) {
      case 1:
        reload_energy(player);
        break;
      case 2:
        break;
      default:
        System.out.println("Invalid option");
        break;
    }
  }
}
