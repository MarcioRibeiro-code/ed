package API;

import API.Abstractions.AGameSettings;
import API.Enums.LocalType;
import API.Enums.TeamType;
import API.Interfaces.IConnector;
import API.Interfaces.ILocalType;
import API.Interfaces.IPortal;
import com.google.gson.*;
import com.so.Collections.Arrays.SortingAndSearching;

import java.io.*;
import java.util.Scanner;

/**
 * Classe que possui métodos que permitem o utilizador, adicionar um novo local, editar um local,
 * remover um local, listar todos os locais, exportar todos os locais para um ficheiro JSON,
 * e importar todos os locais de um ficheiro JSON.
 *
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Manage_Local {

  /**
   * Método que permite ao utilizador adicionar um novo local, editar um local, remover um local,
   * listar todos os locais, exportar todos os locais para um ficheiro JSON,
   * e importar todos os locais de um ficheiro JSON.
   * O utilizador é obrigado a inserir um número que corresponde a uma das opções.
   * Caso o número inserido não corresponda a nenhuma das opções, é mostrada uma mensagem de erro.
   * Caso o número inserido corresponda a uma das opções, é executada a opção correspondente.
   * Caso o utilizador insira o número 0, o menu é fechado.
   */
  public static void menu() {
    int option;
    do {
      System.out.println("Choose an option:\n" +
          " 1.Add 2.Edit 3.Remove 4.List 5.Export 6.Import 0.Back");
      Scanner sc = new Scanner(System.in);
      option = sc.nextInt();
      switch (option) {
        case 1:
          addLocal();
          break;
        case 2:
          editLocal();
          break;
        case 3:
          removeLocal();
          break;
        case 4:
          listLocals();
          break;
        case 5:
          exportLocals();
          break;
        case 6:
          importLocals();
          break;
        case 0:
          break;
        default:
          System.out.println("Invalid option");
      }
    } while (option != 0);
  }


  /**
   * Método que permite ao utilizador adicionar um novo local.
   * O utilizador é obrigado a inserir um número que corresponde ao tipo de local que pretende adicionar.
   * Caso o número inserido não corresponda a nenhuma das opções, é mostrada uma mensagem de erro.
   * Caso o número inserido corresponda a uma das opções, é executada a opção correspondente.
   *
   */
  private static void addLocal() {

    //Longitudinal and latitudinal coordinates

    Scanner sc = new Scanner(System.in);
    System.out.println("Insert the type of the local");
    for (LocalType type : LocalType.values()) {
      System.out.println(type.ordinal() + " " + type);
    }
    int type = sc.nextInt();
    System.out.println("Longitudinal and latitudinal coordinates");
    System.out.println("Longitude");
    float longitude = sc.nextFloat();
    System.out.println("Latitude");
    float latitude = sc.nextFloat();

    //Only add local if the integer is in range
    if (type < 0 || type > LocalType.values().length) {
      System.out.println("Invalid type");
      return;
    }

    switch (LocalType.values()[type]) {
      case CONNECTOR:
        System.out.println("Insert the energy of the connector");
        int energy = sc.nextInt();
        System.out.println("Insert the cooldown timer of the connector");
        int cooldownTimer = sc.nextInt();
        SimulatePlay.getGraph().addPlace(new Connector(latitude, longitude, energy, cooldownTimer));
        break;
      case PORTAL:
        System.out.println("Insert the name of the portal");
        String name = sc.next();
        SimulatePlay.getGraph().addPlace(new Portal(name, latitude, longitude));
        break;
      default:
        System.out.println("Invalid type");
    }
  }

  /**
   * Método que permite ao utilizador editar um local.
   * O utilizador é obrigado a inserir um número que corresponde ao índice do local que pretende editar.
   * Caso o número inserido não corresponda a nenhum dos índices, é mostrada uma mensagem de erro.
   * Caso o número inserido corresponda a um dos índices, é executada a opção correspondente.
   *
   */
  private static void editLocal() {
    for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
      System.out.println(place.toString());
    }
    System.out.println("Insert the index of the local to edit");
    Scanner sc = new Scanner(System.in);
    int id = sc.nextInt();
    ILocalType place = SimulatePlay.getGraph().getVertex(id);
    if (place == null) {
      System.out.println("Invalid id");
      return;
    }

    switch (place.getType()) {
      case CONNECTOR:
        System.out.println("Insert the energy of the connector");
        int energy = sc.nextInt();
        System.out.println("Insert the cooldown timer of the connector");
        int cooldownTimer = sc.nextInt();
        ((Connector) place).getGameSettings().setEnergy(energy);
        ((Connector) place).getGameSettings().setCooldownTimer(cooldownTimer);
        break;
      case PORTAL:
        System.out.println("Insert the name of the portal");
        String name = sc.next();
        ((Portal) place).setName(name);
        break;
      default:
        System.out.println("Invalid type");
    }
  }


  /**
   * Método que permite ao utilizador remover um local.
   * O utilizador é obrigado a inserir um número que corresponde ao índice do local que pretende remover.
   * Caso o número inserido não corresponda a nenhum dos índices, é mostrada uma mensagem de erro.
   * Caso o número inserido corresponda a um dos índices, é executada a opção correspondente.
   */
  private static void removeLocal() {
    for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
      System.out.println(place.toString());
    }
    System.out.println("Insert the index of the local to remove");
    Scanner sc = new Scanner(System.in);
    int id = sc.nextInt();
    SimulatePlay.getGraph().removePlace(id);
  }

  /**
   * Método que permite ao utilizador listar os locais.
   * O utilizador é obrigado a inserir um número que corresponde à opção que pretende executar.
   * 1 - Lista todos os locais.
   * 2 - Lista os locais por tipo.
   * 3 - Lista os portais por nome.
   * 4 - Lista os portais capturados.
   *
   */
  public static void listLocals() {
    //Add a option that allows the user to list Captures Portal
    System.out.println("Choose an option: 1.List all 2.List by type 3.List Portal by name 4.List Captured Portals");
    Scanner sc = new Scanner(System.in);
    int option = sc.nextInt();
    switch (option) {
      case 1:
        for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
          System.out.println(place.toString());
        }
        break;
      case 2:
        System.out.println("Insert the type of the local");
        for (LocalType type : LocalType.values()) {
          System.out.println(type.ordinal() + " " + type);
        }
        int type = sc.nextInt();
        if (type < 0 || type > LocalType.values().length) {
          System.out.println("Invalid type");
          return;
        }
        for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
          if (place.getType() == LocalType.values()[type]) {
            System.out.println(place.toString());
          }
        }
        break;
      case 3:
        System.out.println("Insert the name of the local");
        String name = sc.next();
        for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
          if (place instanceof IPortal && ((IPortal) place).getName().equals(name)) {
            System.out.println(place.toString());
          }
        }
        break;

      case 4:
        Portal_Sort_byTeamType[] capturedPortals = new Portal_Sort_byTeamType[SimulatePlay.getGraph().getPlaces().size()];
        int i = 0;
        for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
          if (place instanceof IPortal && ((IPortal) place).getStatus() != TeamType.NEUTRAL) {
            capturedPortals[i++] = (Portal_Sort_byTeamType) place;
          }
        }
        SortingAndSearching.insertionSort(capturedPortals);
        for (Portal_Sort_byTeamType place : capturedPortals) {
          if (place != null) {
            System.out.println(place);
          }
        }

        break;

      default:
        System.out.println("Invalid option");
    }
  }

  /**
   * Método que permite ao utilizador exportar os locais para um ficheiro JSON.
   * Caso não existam locais, é mostrada uma mensagem de erro.
   * Caso existam locais, é criado um ficheiro JSON com o nome "game.json" e é adicionado um array de locais.
   * Cada local é adicionado ao array de locais.
   *
   */
  public static void exportLocals() {

    if (SimulatePlay.getGraph().isEmpty()) {
      System.out.println("There are no locals to export");
      return;
    }

    Gson gson = new Gson();

    String json = "{\"locals\":[";
    for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
      json += gson.toJson(place) + ",";
    }
    json = json.substring(0, json.length() - 1) + "]}";

    try {
      JsonParser parser = new JsonParser();
      JsonObject playersJson = null;

      File file = new File("game.json");
      if (file.exists()) {
        JsonElement jsonElement = parser.parse(new FileReader("game.json"));
        playersJson = jsonElement.getAsJsonObject();
      } else {
        playersJson = new JsonObject();
      }

      JsonElement localElement = parser.parse(json);
      playersJson.add("locals", localElement.getAsJsonObject().get("locals"));

      FileWriter fileWriter = new FileWriter("game.json");
      fileWriter.write(playersJson.toString());
      fileWriter.close();
      System.out.println("Locals exported");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Método que permite ao utilizador importar os locais de um ficheiro JSON.
   * Caso já existam locais, é mostrada uma mensagem de erro.
   * Caso não existam locais, é lido o ficheiro JSON com o nome "game.json" e é adicionado um array de locais.
   * Cada local é adicionado ao array de locais contido no SimulatePlay.
   */
  protected static void importLocals() {

    if (!SimulatePlay.getGraph().isEmpty()) {
      System.out.println("There are already locals in the program");
      return;
    }

    Gson gson = new GsonBuilder()
        .registerTypeAdapter(AGameSettings.class, new GameSettingsDeserializer())
        .create();

    try {
      JsonParser parser = new JsonParser();
      JsonObject playersJson = parser.parse(new FileReader("game.json")).getAsJsonObject();

      JsonArray locals = playersJson.getAsJsonArray("locals");
      for (JsonElement route : locals) {
        JsonObject routeObject = route.getAsJsonObject();
        String type = routeObject.get("localType").getAsString();

        switch (type) {
          case "PORTAL":
            IPortal portal = gson.fromJson(routeObject, Portal.class);
            SimulatePlay.getGraph().addPlace(portal);
            break;
          case "CONNECTOR":
            IConnector connector = gson.fromJson(routeObject, Connector.class);
            SimulatePlay.getGraph().addPlace(connector);
            break;
          default:
            System.out.println("Invalid type");
        }
      }
      System.out.println("Locals imported " + SimulatePlay.getGraph().getPlaces().size());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


}
