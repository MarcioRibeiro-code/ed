package API.Interfaces;

import API.Enums.TeamType;
import API.Player;

/**
 * Interface que representa um portal.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface IPortal extends ILocalType {

    /**
     * Obtém o status atual de um portal.
     *
     * @return o tipo de equipe a qual o portal pertence.
     */
    TeamType getStatus();

    /**
     * Obtém o jogador que controla o portal.
     *
     * @return o jogador que controla o portal.
     */
    Player getPlayer();

    /**
     * Obtém o nome do portal.
     *
     * @return o nome do portal.
     */
    String getName();

    /**
     * Define o nome do portal.
     *
     * @param name o novo nome do portal.
     */
    void setName(String name);
}
