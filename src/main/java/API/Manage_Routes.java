package API;

import com.google.gson.*;
import com.so.Collections.Arrays.ArrayUnorderedList;
import com.so.Collections.Map.HashMap;

import java.io.*;
import java.util.Scanner;

/**
 * Classe que permite ao utilizador gerir as rotas
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Manage_Routes {
  /**
   * Menu que permite ao utilizador gerir as rotas
   * O utilizador pode criar uma rota, remover uma rota ou exportar as rotas
   * O utilizador pode voltar ao menu anterior
   * Caso o utilizador insira uma opção inválida, é mostrada uma mensagem de erro
   */
  public static void menu() {
    int opcao = 0;
    Scanner sc = new Scanner(System.in);
    do {
      System.out.println("1 - Criar Rota");
      System.out.println("2 - Remover Rota");
      System.out.println("3 - Exportar Rotas");
      System.out.println("0 - Voltar");
      opcao = sc.nextInt();
      switch (opcao) {
        case 1:
          createRoute();
          break;
        case 2:
          removeRoute();
          break;
        case 3:
          exportRoutes();
          break;
        case 0:
          break;
        default:
          System.out.println("Opção inválida");
      }
    } while (opcao != 0);
  }


  /**
   * Função que permite ao utilizador criar uma rota
   * O utilizador é obrigado a inserir o ID de dois locais
   * Caso o ID de um dos locais não exista, é mostrada uma mensagem de erro
   * Caso a rota seja criada com sucesso, é mostrada uma mensagem de sucesso
   */
  public static void createRoute() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Insira o ID do primeiro local");
    int id1 = sc.nextInt();
    System.out.println("Insira o ID do segundo local");
    int id2 = sc.nextInt();
    if (SimulatePlay.getGraph().addRoute(id1, id2)) {
      System.out.println("Rota criada com sucesso");
    } else {
      System.out.println("Não foi possível criar a rota");
    }
  }

  /**
   * Função que permite ao utilizador remover uma rota
   * O utilizador é obrigado a inserir o ID de dois locais
   * Caso o ID de um dos locais não exista, é mostrada uma mensagem de erro
   */
  public static void removeRoute() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Insira o ID do primeiro local");
    int id1 = sc.nextInt();
    System.out.println("Insira o ID do segundo local");
    int id2 = sc.nextInt();
    if (SimulatePlay.getGraph().removeRoute(id1, id2)) {
      System.out.println("Rota removida com sucesso");
    } else {
      System.out.println("Não foi possível remover a rota");
    }
  }

  /**
   * Função que permite ao utilizador exportar as rotas para um ficheiro
   * O ficheiro é guardado no formato JSON
   */
  public static void exportRoutes() {
    Gson gson = new Gson();
    HashMap<Integer, ArrayUnorderedList<Integer>> routes = SimulatePlay.getGraph().getRoutes();
    String json = "{\"routes\":[";

    for (Integer from : routes.keySet()) {
      ArrayUnorderedList<Integer> toNodes = routes.get(from);
      for (Integer to : toNodes) {
        json += "{\"from\":" + from + ",\"to\":" + to + "},";
      }
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

      JsonElement routesElement = parser.parse(json);
      playersJson.add("routes", routesElement.getAsJsonObject().get("routes"));

      FileWriter fileWriter = new FileWriter("game.json");
      fileWriter.write(playersJson.toString());
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Função que permite ao utilizador importar as rotas de um ficheiro
   * Caso o grafo esteja vazio, é mostrada uma mensagem de erro
   * Caso o ficheiro não exista, é mostrada uma mensagem de erro
   */
  public static void importRoutes() {
    if (SimulatePlay.getGraph().isEmpty()) {
      System.out.println("Não existem locais para criar rotas");
      System.out.println("Por favor crie/importe locais primeiro");
      return;
    }


    try {
      JsonParser parser = new JsonParser();
      JsonObject playersJson = parser.parse(new FileReader("game.json")).getAsJsonObject();

      JsonArray routes = playersJson.getAsJsonArray("routes");
      for (JsonElement route : routes) {
        JsonObject routeObject = route.getAsJsonObject();
        int from = routeObject.get("from").getAsInt();
        int to = routeObject.get("to").getAsInt();
        SimulatePlay.getGraph().addRoute(from, to);
      }

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


}
