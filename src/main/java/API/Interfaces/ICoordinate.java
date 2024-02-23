package API.Interfaces;

/**
 * Interface que representa as coordenadas geográficas de um local.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface ICoordinate {
  /**
   * Retorna a latitude.
   *
   * @return a latitude
   */
  float getLatitude();

  /**
   * Retorna a longitude.
   *
   * @return a longitude
   */
  float getLongitude();
}
