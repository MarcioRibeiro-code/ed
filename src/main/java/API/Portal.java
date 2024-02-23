package API;

import API.Abstractions.AGameSettings;
import API.Enums.LocalType;
import API.Enums.TeamType;
import API.Exceptions.IllegalArgumentException;
import API.Interfaces.IPlayer;
import API.Interfaces.IPortal;

import java.util.Scanner;

/**
 *  Classe que representa um objeto de Portal no jogo.
 *  @author Márcio Ribeiro - 8200408
 *  @author Hugo Ribeiro - 8200441
 */
public class Portal extends Local implements IPortal {
  private String name;
  private TeamType status;
  private final static int DEFAULT_ENERGY = 0;
  private AGameSettings gameSettings;

  private static final int MAX_ENERGY = 400;

  /**
   * Cria um novo portal com um nome, latitude e longitude definidos pelo user.
   *
   * @param name nome do portal
   * @param latitude latitude do portal
   * @param longitude longitude do portal
   */
  public Portal(String name, float latitude, float longitude) {
    super(latitude, longitude);
    this.gameSettings = new GameSettings(DEFAULT_ENERGY, null);
    this.name = name;
    super.localType = LocalType.PORTAL;
    this.status = TeamType.NEUTRAL;
  }



  /**
   * Permite a um jogador conquistar um portal neutro.
   *
   * @param player player a tentar conquistar o portal
   * @return true se o player conseguir conquistar o portal, caso contrário retorna false
   */
  public boolean conquerPortal(IPlayer player) {
    if (this.status != TeamType.NEUTRAL) {
      return false;
    }

    int portal_maxEnergy = this.getMAX_energy();
    int player_currentEnergy = player.getCurrentEnergy();

    if (player_currentEnergy >= (portal_maxEnergy * 0.25)) {
      player.setCurrentEnergy(player_currentEnergy - (int) (portal_maxEnergy * 0.25));
      // Energia do portal
      this.gameSettings.setEnergy(this.gameSettings.getEnergy() + (int) (portal_maxEnergy * 0.25));
      this.status = player.getTeam();
      GameSettings gameSettings = (GameSettings) this.gameSettings;
      gameSettings.getOwnerShip().setPlayer((Player) player);
      player.setExperiencePoints(player.getExperiencePoints() + GlobalSettings.captureNeutralPortal);
      System.out.println("Player " + player.getName() + " captured the portal " + this.name);
      gameSettings.ownerShip.player.setCapturedPortals(gameSettings.ownerShip.player.getCapturedPortals() + 1);
      player.getLevel();
      return true;
    }
    System.out.println("Not Enough Energy");
    return false;
  }


  /**
   * Permite a um jogador tentar conquistar um portal inimigo.
   *
   * @param player o player a tentar conquistar um portal inimigo
   * @return
   */
  public boolean conquerEnemyPortal(IPlayer player) {
    if (this.status != player.getTeam() && this.status != TeamType.NEUTRAL) {

      if (this.gameSettings.getEnergy() > player.getCurrentEnergy()) {
        throw new IllegalArgumentException("Player does not have enough energy to capture this portal");
      }


      this.gameSettings.setEnergy(this.gameSettings.getEnergy() - player.getCurrentEnergy());

      int remainingEnergy = Math.abs(this.gameSettings.getEnergy());


      if (gameSettings.getEnergy() < 0) {
        this.gameSettings.setEnergy(0);
      }

      player.setCurrentEnergy(remainingEnergy);

      GameSettings gameSettings = (GameSettings) this.gameSettings;
      gameSettings.ownerShip.player.setCapturedPortals(gameSettings.ownerShip.player.getCapturedPortals() - 1);
      gameSettings.getOwnerShip().setPlayer(null);
      this.status = TeamType.NEUTRAL;
      System.out.println("Player " + player.getName() + "de-captured the portal " + this.name);
      player.setExperiencePoints(player.getExperiencePoints() + GlobalSettings.captureEnemyPortal);

    }
    return conquerPortal(player);
  }


  /**
   * Permite a um jogador adicionar energia a um portal que a sua equipa ja tenha conquistado.
   *
   * @param player player a querer adicionar energia ao portal
   * @param energy energy a ser adicionada ao portal pelo player
   * @return true se conseguir adicionar energy ao portal
   */
  public boolean addEnergy(IPlayer player, int energy) {

    if (this.status != player.getTeam()) {
      throw new IllegalArgumentException("Portal does not belong to the player's team: " + player.getTeam());
    }

    int player_currentEnergy = player.getCurrentEnergy();
    int portal_maxEnergy = this.getMAX_energy();
    int portal_currentEnergy = this.gameSettings.getEnergy();
    if (player_currentEnergy < energy) {
      throw new IllegalArgumentException("Energy is more than the player has");
    }

    int energyToAdd = Math.min(energy, portal_maxEnergy - portal_currentEnergy);
    this.gameSettings.setEnergy(this.gameSettings.getEnergy() + energyToAdd);
    player.setCurrentEnergy(player_currentEnergy - energyToAdd);
    player.setExperiencePoints(player.getExperiencePoints() + GlobalSettings.loadEnergyIntoPortal);
    player.getLevel();
    System.out.println("Player " + player.getName() + " added " + energyToAdd + " energy to the portal " + this.name);
    return true;
  }

  /**
   * Retorna o maximo de energia de um portal.
   *
   * @return MAX_ENERGY do portal
   */
  public int getMAX_energy() {
    return Portal.MAX_ENERGY;
  }

  /**
   * Retorna o nome do portal.
   *
   * @return name do portal
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Define o nome do portal
   *
   * @param name o novo nome do portal.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Retorna o tipo de local
   *
   * @return LocalType do local
   */
  @Override
  public LocalType getType() {
    return super.localType;
  }

  /**
   * Menu para um jogador fazer iterações com as funções existentes dentro de um portal.
   *
   * @param player player a ser usado para trabalhar com as respetivas interações
   */
  @Override
  public void menu(IPlayer player) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("1 - Capture Portal");
    System.out.println("2 - Add Energy");
    System.out.println("3 - Exit");
    int option = scanner.nextInt();

    switch (option) {
      case 1:
        if (this.status == TeamType.NEUTRAL) {
          this.conquerPortal(player);
        } else {
          this.conquerEnemyPortal(player);
        }
        break;
      case 2:
        System.out.println("How much energy do you want to add?");
        int energy = scanner.nextInt();
        this.addEnergy(player, energy);
        break;
      case 3:
        break;
      default:
        System.out.println("Invalid Option");
        break;
    }
  }

  /**
   * Retorna o estado do portal.
   *
   * @return status do portal
   */
  @Override
  public TeamType getStatus() {
    return this.status;
  }

  /**
   * Retorna o quem conquistou(no caso de ter sido conquistado) o portal
   *
   * @return ownerShip do portal
   */
  @Override
  public Player getPlayer() {
    GameSettings gameSettings1 = (GameSettings) this.gameSettings;
    return gameSettings1.ownerShip.getPlayer();
  }


  /**
   * Inner class que representa um objeto de GameSettings de um Portal no jogo.
   */
  public class GameSettings extends AGameSettings {

    private OwnerShip ownerShip;

    /**
     * Retorna quem conquistou o portal, no caso de ter sido conquistado.
     *
     * @return ownerShip do portal
     */
    public OwnerShip getOwnerShip() {
      return ownerShip;
    }

    /**
     * Construtor de um GameSettings, com energy e player atribuidos automaticamente quando é instanciado um portal
     *
     * @param energy energia atual do portal
     * @param player player que conquistou o portal
     */
    public GameSettings(int energy, Player player) {
      super(energy);
      this.ownerShip = new OwnerShip(player);
    }

    /**
     * Inner class que representa um objeto de OwnerShip de um portal no jogo.
     */
    private class OwnerShip {
      private Player player;

      /**
       * Construtor de um OwnerShip
       * @param player player a ser atribuido ao ownership
       */
      public OwnerShip(Player player) {
        this.player = player;
      }

      /**
       * Retorna o player do ownership.
       * @return player conquistou portal.
       */
      public Player getPlayer() {
        return player;
      }

      /**
       * Define o player do OwnerShip.
       *
       * @param player player que irá ser novo ownership do portal
       */
      public void setPlayer(Player player) {
        this.player = player;
      }

      /**
       * Retorna o respetivo OwnerShip do portal
       *
       * @return player que conquistou o portal
       */
      @Override
      public String toString() {
        return "OwnerShip{" +
            "player=" + player +
            '}';
      }
    }

    @Override
    public String toString() {
      return "{" +
          super.toString() + "," +
          "ownerShip=" + ownerShip.toString() +
          '}';
    }

  }

  @Override
  public String toString() {
    return "Portal{" +
        "name='" + name + '\'' +
        ", status='" + status + '\'' +
        ", OwnPlayer=" + getPlayer() +
        ", gameSettings=" + gameSettings +
        ", latitude=" + super.coordinates.getLatitude() +
        ", longitude=" + super.coordinates.getLongitude() +
        ", ID=" + ID +
        ", localType=" + localType +
        '}';
  }
}
