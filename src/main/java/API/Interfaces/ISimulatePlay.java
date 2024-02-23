package API.Interfaces;

/**
 * Interface que representa a Simulação do jogo.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface ISimulatePlay {
    /**
     * Incrementa o tempo da simulação.
     */
    void incrementTime();

    /**
     * Inicia a simulação de jogo.
     */
    void play();
}
