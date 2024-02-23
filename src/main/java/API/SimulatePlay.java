package API;

import API.Enums.TeamType;
import API.Interfaces.ILocalType;
import API.Interfaces.IPlayer;
import API.Interfaces.IPortal;
import API.Interfaces.ISimulatePlay;
import com.so.Collections.Arrays.ArrayUnorderedList;
import com.so.Collections.ListADT;
import com.so.Collections.Lists.LinkedList;
import com.so.Collections.Map.HashMap;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Class that simulates the game.
 *
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class SimulatePlay implements ISimulatePlay {
  private static ListADT<IPlayer> players = new ArrayUnorderedList<>();
  private static ConcreteGraph graph = new ConcreteGraph();
  private static HashMap<IPlayer, ILocalType> playerLocation = new HashMap<>();
  private static int time = 0;

  /**
   * Método que devolve o grafo.
   * @return Grafo.
   */
  public static ConcreteGraph getGraph() {
    return graph;
  }


  /**
   * Método que devolve a lista de jogadores.
   * @return Lista de jogadores.
   */
  public static ListADT<IPlayer> getPlayers() {
    return players;
  }

  /**
   * Método que permite definir a lista de jogadores.
   * @param players Lista de jogadores.
   */
  public static void setPlayers(ListADT<IPlayer> players) {
    SimulatePlay.players = players;
  }

  public static int getTime() {
    return time;
  }

  public void incrementTime() {
    time++;
  }


  /**
   * Método que permite os varios jogadores jogarem.
   */
  public void play() {

    if (players.isEmpty()) {
      System.out.println("No players");
      return;
    }
    if (graph.isEmpty()) {
      System.out.println("No places");
      return;
    }

    if(graph.getRoutes().isEmpty()){
      System.out.println("No routes");
      return;
    }

    if (!players.isEmpty() && playerLocation.isEmpty()) {
      generateRandomLocations();
    }

    Iterator<IPlayer> playerIterator = players.iterator();
    int option;
    do {
      System.out.println("Choose an option:\n" +
          " 1.Play 0.STOP");
      Scanner sc = new Scanner(System.in);
      option = sc.nextInt();
      if (option == 0) {
        break;
      }
      IPlayer currentPlayer = cyclePlayers(playerIterator);
      if (currentPlayer.getTeam() == TeamType.NEUTRAL) {
        currentPlayer = cyclePlayers(playerIterator);
      }

      ILocalType currentPlace = (ILocalType) playerLocation.get(currentPlayer);

      System.out.println("Current Player: " + currentPlayer.getName());
      System.out.println("Current Place: " + currentPlace.getID());

      //Make a menu with options move to a place, or stay in the current place and do something
      System.out.println("Choose an option:\n" +
          " 1.Move 2.Stay");
      option = sc.nextInt();
      switch (option) {
        case 1:
          LinkedList<ILocalType> places_move = (LinkedList<ILocalType>) graph.displayPlaces(currentPlace);
          System.out.println("ID of the place you want to move to:");
          int id = sc.nextInt();


          ILocalType newPlace = graph.getVertex(id);
          if (newPlace == null) {
            System.out.println("Invalid ID");
            break;
          } else if (!places_move.contains(newPlace)) {
            System.out.println("You can't move to that place");
            break;
          }
          System.out.println("Moving to " + newPlace.getID());
          playerLocation.put(currentPlayer, newPlace);
          incrementTime();
          break;
        case 2:
          currentPlace.menu(currentPlayer);
          incrementTime();
          break;
        default:
          System.out.println("Invalid option");
          break;
      }


    } while (true);

  }

  /**
   * Método que gera as localizações aleatórias dos jogadores.
   * Tendo em conta que os jogadores não podem estar em portais.
   */
  public static void generateRandomLocations() {
    if (!playerLocation.isEmpty()) {
      return;
    }
    for (IPlayer player : players) {
      //Generate a random int between 0 and the number of vertices
      int random = (int) (Math.random() * graph.size());

      ILocalType place = ((ConcreteGraph) graph).getVertex_Pos(random);
      while (place instanceof IPortal) {
        random = (int) (Math.random() * graph.size());
        place = ((ConcreteGraph) graph).getVertex_Pos(random);
      }

      playerLocation.put(player, place);
    }
  }

  /**
   * Método que permite iterar os jogadores.
   * @param playerIterator Iterator dos jogadores
   * @return Jogador atual
   */
  private IPlayer cyclePlayers(Iterator<IPlayer> playerIterator) {
    if (!playerIterator.hasNext()) {
      playerIterator = players.iterator();
    }
    return playerIterator.next();
  }

  /**
   * Método que permite carregar a informaçao contida nos ficheiros para as estruturas de dados.
   */
  public void loadFile() {
    Manage_Local.importLocals();
    Manage_Players.importPlayers();
    Manage_Routes.importRoutes();
  }

  /**
   * Método que permite guardar os dados das estruturas de dados para os ficheiros.
   */
  public void saveFile() {
    Manage_Local.exportLocals();
    Manage_Players.exportPlayers();
    Manage_Routes.exportRoutes();
  }

}
