package API;

import API.Interfaces.IPortal;

/**
 * Classe que representa um portal.
 * Com o objectivo de ordenar os portais por tipo de equipa.
 *
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Portal_Sort_byTeamType extends Portal implements Comparable<IPortal> {
  /**
   * Construtor da classe Portal_Sort_byTeamType.
   *
   * @param name      Nome do portal
   * @param latitute  Latitude do portal
   * @param longitude Longitude do portal
   */
  public Portal_Sort_byTeamType(String name, float latitute, float longitude) {
    super(name, latitute, longitude);
  }

  /**
   * Metodo que compara dois portais por tipo de equipa.
   *
   * @param o Portal a comparar
   * @return 0 se forem iguais, 1/-1 se o portal forem de equipas diferentes
   */
  @Override
  public int compareTo(IPortal o) {
    return this.getStatus().compareTo(o.getStatus());
  }
}

