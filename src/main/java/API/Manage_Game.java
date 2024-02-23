package API;

import API.Graph.PathWithWeight;
import API.Interfaces.ILocalType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.so.Collections.Arrays.ArrayList;
import com.so.Collections.Arrays.ArrayUnorderedList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe que possui métodos que permitem o utilizador, obter o caminho mais curto entre dois locais,
 * o caminho mais curto entre dois locais tendo que passar por um local,
 * e exportar todos os caminhos possíveis entre dois locais.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Manage_Game {

  /**
   * Método que permite ao utilizador obter o caminho mais curto entre dois locais.
   * O utilizador é obrigado a inserir o ID de dois locais.
   * Caso o ID de um dos locais não exista, é mostrada uma mensagem de erro.
   * Caso não exista caminho entre os dois locais, é mostrada uma mensagem de erro.
   * Caso exista caminho entre os dois locais, é mostrado o caminho mais curto entre os dois locais.
   */
  public static void menu(){
    int option;
    do {
      System.out.println("Choose an option:\n" +
          " 1.Shortest Path 2.Shortest Path having to pass through\n" +
          " 3.Export all Possible Paths 0.Back");
      Scanner sc = new Scanner(System.in);
      option = sc.nextInt();
      switch (option) {
        case 1:
          ShortestPath();
          break;
        case 2:
          ShortestPath_HavingToPassTrough();
          break;
        case 3:
          exportPaths();
          break;
        case 0:
          break;
        default:
          System.out.println("Invalid option");
      }
    } while (option != 0);
  }


  /**
   * Método que permite ao utilizador obter o caminho mais curto entre dois locais.
   * O utilizador é obrigado a inserir o ID de dois locais.
   * Caso o ID de um dos locais não exista, é mostrada uma mensagem de erro.
   * Caso não exista caminho entre os dois locais, é mostrada uma mensagem de erro.
   * Caso exista caminho entre os dois locais, é mostrado o caminho mais curto entre os dois locais.
   */
  private static void ShortestPath() {
    for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
      System.out.println(place);
    }


    Scanner sc = new Scanner(System.in);
    System.out.println("Insira o ID do primeiro local");
    int id1 = sc.nextInt();
    System.out.println("Insira o ID do segundo local");
    int id2 = sc.nextInt();

    if (SimulatePlay.getGraph().getVertex(id1) == null || SimulatePlay.getGraph().getVertex(id2) == null) {
      System.out.println("Um dos locais não existe");
      return;
    }

    ArrayList<ILocalType> path = SimulatePlay.getGraph().djistkra(SimulatePlay.getGraph().getVertex(id1), SimulatePlay.getGraph().getVertex(id2));
    if (path.isEmpty()) {
      System.out.println("Não existe caminho entre os dois locais");
      return;
    }

    System.out.println("Caminho mais curto: " + path.toString());
  }

  /**
   * Método que permite ao utilizador obter o caminho mais curto entre dois locais tendo que passar por alguns locais.
   * O utilizador é obrigado a inserir o ID de dois locais, e os IDs dos locais que tem que passar pelo caminho.
   * Caso o ID de um dos locais não exista, é mostrada uma mensagem de erro.
   * Caso não exista caminho entre os dois locais, é mostrada uma mensagem de erro.
   * Caso exista caminho entre os dois locais, é mostrado o caminho mais curto entre os dois locais.
   */
  private static void ShortestPath_HavingToPassTrough() {
    for (ILocalType place : SimulatePlay.getGraph().getPlaces()) {
      System.out.println(place);
    }
    Scanner sc = new Scanner(System.in);
    System.out.println("Insira o ID do primeiro local");
    int id1 = sc.nextInt();
    System.out.println("Insira o ID do segundo local");
    int id2 = sc.nextInt();

    int option = 0;
    ArrayUnorderedList<ILocalType> places = new ArrayUnorderedList<>();
    do {
      System.out.println("Insira os Ids dos locais que tem que passar pelo caminho (-1 para terminar)");
      option = sc.nextInt();
      if (SimulatePlay.getGraph().getVertex(option) == null) {
        System.out.println("Local não existe");
        return;
      } else {
        if (option != -1) {
          places.addToRear(SimulatePlay.getGraph().getVertex(option));
        }
        break;
      }
    } while (option != -1);

    PathWithWeight path = SimulatePlay.getGraph().findPaths(places, SimulatePlay.getGraph().getVertex(id1), SimulatePlay.getGraph().getVertex(id2)).first();
    if (path == null) {
      System.out.println("Não existe caminho entre os dois locais");
      return;
    }
    System.out.println(path);
  }

  /**
   * Método que permite ao utilizador exportar todos os caminhos possíveis entre os locais.
   * É criado um ficheiro JSON com todos os caminhos possíveis entre os locais, junto com o peso de cada caminho.
   */
  private static void exportPaths() {
    JsonArray array = new JsonArray();
    for (PathWithWeight path : SimulatePlay.getGraph().findAllPaths()) {
      array.add(path.toJson());
    }
    JsonObject object = new JsonObject();
    object.add("PossiblePaths", array);
    try {
      FileWriter file = new FileWriter("ShortestPath.json");
      file.write(object.toString());
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
