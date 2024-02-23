package API.Abstractions;

/**
 * Classe abstracta que representa as configurações comuns a todos os locais.
 * <p>
 *   Possui a energia do local.
 *
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public abstract class AGameSettings {
  private int energy;

  /**
   * Construtor de GameSettings
   * <p>
   *   A energia é o valor máximo de energia que o jogador pode recarregar no local no caso dos connectors e o valor de energia currente no caso dos portais
   *   A energia a ser introduzida esta definida nas classes connector e portal.
   *
   * @param energy Energia do local
   */
  public AGameSettings(int energy) {
    this.energy = energy;
  }

  /**
   * Metodo que retorna a energia do local
   * @return energia do local
   */
  public int getEnergy() {
    return energy;
  }

  /**
   * Metodo que permite alterar a energia do local
   * @param energy nova energia do local
   */
  public void setEnergy(int energy) {
    this.energy = energy;
  }

  /**
   * Metodo que retorna a string da informação contida na classe AGameSettings
   * @return string com a informação contida na classe AGameSettings
   */
  @Override
  public String toString() {
    return
        "energy=" + energy;
  }
}
