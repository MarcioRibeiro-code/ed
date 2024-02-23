package API;

import API.Enums.LocalType;
import API.Interfaces.ICoordinate;
import API.Interfaces.ILocalType;
import API.Interfaces.IPlayer;

/**
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Local implements ILocalType {

  protected static int nextID = 1;
  protected int ID;
  protected LocalType localType;
  protected final Coordinates coordinates;

  /**
   * Construtor para uma instância do tipo Local, onde o ID é predefinido e
   * incremental pelo IDE.
   *
   * @param latitude  Latitude de um local
   * @param longitude Longitude de um local
   */
  public Local(float latitude, float longitude) {
    this.coordinates = new Coordinates(latitude, longitude);
    this.ID = nextID++;
  }

  /**
   * Getter para um ID de um local.
   *
   * @return ID do local.
   */
  @Override
  public int getID() {
    return this.ID;
  }

  /**
   * setter para o ID do local.
   *
   * @param ID new ID
   */
  @Override
  public void setID(int ID) {
    this.ID = ID;
  }

  @Override
  public void menu(IPlayer player) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Getter para o tipo de local.
   *
   * @return tipo de local.
   */
  @Override
  public LocalType getType() {
    return this.localType;
  }

  @Override
  public ICoordinate getCoordinates() {
    return this.coordinates;
  }


  public static class Coordinates implements ICoordinate {
    protected final float latitude;
    protected final float longitude;

    public Coordinates(float latitude, float longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
    }


    /**
     * Getter para a latitude de um local.
     *
     * @return latitude do local.
     */
    @Override
    public float getLatitude() {
      return this.latitude;
    }

    /**
     * Getter para a Longitude de um Local
     *
     * @return longitude de um local.
     */
    @Override
    public float getLongitude() {
      return this.longitude;
    }
  }

}
