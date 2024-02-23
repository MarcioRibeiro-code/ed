package API.Interfaces;

import API.Player;

/**
 * Interface que representa um connector.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface IConnector extends ILocalType {

    /**
     * Carrega a energia de um player.
     *
     * @param player player cuja energia irá ser carregada
     */
    void reload_energy(IPlayer player);

    /**
     * Retorna o cooldowntimer de um connector.
     *
     * @return o valor do cooldowntimer
     */

}
