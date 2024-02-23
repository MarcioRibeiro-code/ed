package API;

import API.Enums.TeamType;
import API.Interfaces.IGlobalSettings;
import API.Interfaces.IPlayer;

/**
 * Classe que representa um jogador
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Player implements IPlayer {

  private String name;
  private TeamType team;
  private int level;
  private int experiencePoints;
  private int currentEnergy;
  private int DEFAULT_ENERGY = 100;
  public int currentLocation = -1;
  private int capturedPortals = 0;


  /**
   * Construtor da classe Player
   * @param name Nome do jogador
   * @param team Equipa do jogador
   */
  public Player(String name, String team) {
    this.name = name;
    this.team = TeamType.valueOf(team);
    this.level = 0;
    this.experiencePoints = 0;
    this.currentEnergy = DEFAULT_ENERGY;
  }

  /**
   * Método que permite calcular a energia por defeito
   * @return Energia por defeito
   */
  private int calculateDefaultEnergy() {
    return DEFAULT_ENERGY + (int) (DEFAULT_ENERGY * 0.05 * this.level);
  }

  /**
   * Método que permite obter a energia por defeito
   * @return Energia por defeito
   */
  public int getDefaultEnergy() {
    return calculateDefaultEnergy();
  }

  /**
   * Método que permite obter o nome do jogador
   * @return Nome do jogador
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método que permite definir o nome do jogador
   * @param name o nome a ser definido
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Método que permite obter a equipa do jogador
   * @return Equipa do jogador
   */
  public TeamType getTeam() {
    return this.team;
  }

  /**
   * Método que permite definir a equipa do jogador
   * @param team a equipa a ser definida
   */
  public void setTeam(TeamType team) {
    this.team = team;
  }

  /**
   * Método Helper que permite obter o nível do jogador, calculando-o
   * Com base nos pontos de experiência
   * @return Nível do jogador
   */
  private int checkLevel() {

    if (this.experiencePoints >= IGlobalSettings.calculateExperiencePoints(
        this.level + 1, GlobalSettings.x, GlobalSettings.y)) {
      this.level++;

    }
    return this.level;
  }

  /**
   * Método que permite obter o nível do jogador
   * @return Nível do jogador
   */
  public int getLevel() {
    return checkLevel();
  }

  /**
   * Método que permite definir o nível do jogador
   * @param level o nível a ser definido
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Método que permite obter os pontos de experiência do jogador
   * @return Pontos de experiência do jogador
   */
  public int getExperiencePoints() {
    return experiencePoints;
  }

  /**
   * Método que permite definir os pontos de experiência do jogador
   * @param experiencePoints os pontos de experiência a serem definidos
   */
  public void setExperiencePoints(int experiencePoints) {
    this.experiencePoints = experiencePoints;
  }

  /**
   * Método que permite obter a energia atual do jogador
   * @return Energia atual do jogador
   */
  public int getCurrentEnergy() {
    return currentEnergy;
  }

  /**
   * Método que permite definir a energia atual do jogador
   * @param currentEnergy a energia atual a ser definida
   */
  public void setCurrentEnergy(int currentEnergy) {
    this.currentEnergy = currentEnergy;
  }

  /**
   * Método que permite obter o numero de portais conquistados pelo jogador
   * @return numero de portais conquistados pelo jogador
   */
  public int getCapturedPortals() {
    return capturedPortals;
  }

  /**
   * Método que permite definir o numero de portais conquistados pelo jogador
   * @param capturedPortals o numero de portais a ser definido
   */
  public void setCapturedPortals(int capturedPortals) {
    this.capturedPortals = capturedPortals;
  }


  /**
   * Método que permite comparar dois objetos do tipo Player
   * @param player Objeto a ser comparado
   * @return 0 se forem iguais, 1 se o objeto for maior e -1 se o objeto for menor
   */
  @Override
  public int compareTo(IPlayer player) {
    if (!team.equals(player.getTeam())) {
      return team.compareTo(player.getTeam());
    }
    if (level != player.getLevel()) {
      return Integer.compare(level, player.getLevel());
    }
    return Integer.compare(capturedPortals, player.getCapturedPortals());
  }

  /**
   * Método que permite obter a representação em String de um objeto do tipo Player
   * @return Representação em String de um objeto do tipo Player
   */
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        ", team=" + team +
        ", level=" + level +
        ", experiencePoints=" + experiencePoints +
        ", currentEnergy=" + currentEnergy +
        ", MaxEnergy=" + DEFAULT_ENERGY +
        ", currentLocation=" + currentLocation +
        ", capturedPortals=" + capturedPortals +
        '}';
  }
}
