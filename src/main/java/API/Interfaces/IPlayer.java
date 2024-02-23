package API.Interfaces;

import API.Enums.TeamType;

/**
 * Interface que representa um jogador.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface IPlayer extends Comparable<IPlayer> {

    /**
     * Retorna a energia padrão do jogador.
     *
     * @return a energia padrão do jogador
     */
    int getDefaultEnergy();

    /**
     * Retorna o nome do jogador.
     *
     * @return o nome do jogador
     */
    String getName();

    /**
     * Define o nome do jogador.
     *
     * @param name o nome a ser definido
     */
    void setName(String name);

    /**
     * Retorna a equipa do jogador.
     *
     * @return a equipa do jogador
     */
    TeamType getTeam();

    /**
     * Define a equipe do jogador.
     *
     * @param team a equipe a ser definida
     */
    void setTeam(TeamType team);

    /**
     * Retorna o nível do jogador.
     *
     * @return o nível do jogador
     */
    int getLevel();

    /**
     * Define o nível do jogador.
     *
     * @param level o nível a ser definido
     */
    void setLevel(int level);

    /**
     * Retorna os pontos de experiência do jogador.
     *
     * @return os pontos de experiência do jogador
     */
    int getExperiencePoints();

    /**
     * Define os pontos de experiência do jogador.
     *
     * @param experiencePoints os pontos de experiência a serem definidos
     */
    void setExperiencePoints(int experiencePoints);


    /**
     * Retorna a energia atual do jogador.
     *
     * @return a energia atual do jogador
     */
    int getCurrentEnergy();

    /**
     * Define a energia atual do jogador.
     *
     * @param currentEnergy a energia atual a ser definida
     */
    void setCurrentEnergy(int currentEnergy);
    int getCapturedPortals();
    void setCapturedPortals(int capturedPortals);
}
