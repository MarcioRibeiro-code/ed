package API;

import API.Interfaces.IGlobalSettings;

/**
 * Classe que contém as configurações globais do jogo
 * <p>
 *   GlobalSettings é uma classe que contém as configurações globais do jogo
 *   GlobalSettings contém as configurações de captura de portais, recarga de energia e recarga de energia em conectores
 *   GlobalSettings contém também as configurações de x e y
 *   GlobalSettings é uma classe que implementa a interface IGlobalSettings
 *
 *   @author Márcio Ribeiro - 8200408
 *  @author Hugo Ribeiro - 8200441
 */
public class GlobalSettings implements IGlobalSettings {

  static final double x = 0.02;
  static final double y = 1.5;

  static final int captureNeutralPortal = 100;
  static final int captureEnemyPortal = 50;
  static final int loadEnergyIntoPortal = 30;

static final int reloadEnergyInConnector = 15;

//static final ConnectorSettings connectorSettings;

}
