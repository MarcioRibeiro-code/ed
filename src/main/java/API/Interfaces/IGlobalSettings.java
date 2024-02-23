package API.Interfaces;

import API.Local;

/**
 * Interface que representa configurações globais do jogo.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface IGlobalSettings {


  /**
   * Calcula o nível a partir de pontos de experiência, x e y.
   *
   * @param experiencePoints pontos de experiência
   * @param x valor x
   * @param y valor y
   * @return o nível calculado
   */
  static int calculateLevel(double experiencePoints, double x, double y) {
    return (int) Math.pow(experiencePoints / x, 1 / y);
  }

  /**
   * Calcula pontos de experiência a partir de um nível, x e y.
   *
   * @param level nível
   * @param x valor x
   * @param y valor y
   * @return os pontos de experiência calculados
   */
  static int calculateExperiencePoints(int level, double x, double y) {
    return (int) Math.pow(level / x, y);
  }

  /**
   * Calcula o peso da ligação a partir de coordenadas de início e fim.
   *
   * @param start coordenada de início
   * @param end coordenada de fim
   * @return o peso da ligação calculado
   */
  static int calculateEdgeWeight(ICoordinate start, ICoordinate end) {
    double x = Math.pow((end.getLongitude() - start.getLongitude()), 2);
    double y = Math.pow(end.getLatitude() - start.getLatitude(), 2);
    return (int) Math.sqrt(x + y);
  }

}
