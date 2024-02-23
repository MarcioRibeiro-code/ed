package API;

import API.Enums.TeamType;
import API.Interfaces.IPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.so.Collections.Arrays.ArrayUnorderedList;
import com.so.Collections.Arrays.SortingAndSearching;
import com.so.Collections.ListADT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Classe que gere os jogadores.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Manage_Players {


  /**
   * Método que permite introduzir um jogador.
   * @param name Nome do jogador.
   * @param team Equipa do jogador.
   */
  public static void addPlayer(String name, String team) {
    IPlayer player = new Player(name, team);
    ArrayUnorderedList<IPlayer> temp_players = (ArrayUnorderedList<IPlayer>) SimulatePlay.getPlayers();
    temp_players.addToRear(player);
  }

//Make menu that goes through all the players and prints their names and teams, and then asks for the name of the player to be removed and removes it from the list.

  /**
   * Método que vai percorrer todos os jogadores e imprime a sua informação.
   * No final, pede o nome do jogador e é devolvido o nome do jogador.
   * @param temp_players Lista de jogadores.
   * @param textShow Texto a ser mostrado.
   * @return Nome do jogador.
   */
  private static String goTroughPlayers(ListADT<IPlayer> temp_players, String textShow) {

    for (IPlayer player : temp_players) {
      System.out.println(player.toString());
    }
    System.out.println(textShow);
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();

  }

  //Make menu that allows the user to update value of a player

  /**
   * Método que permite atualizar um jogador.
   * Primeiro, é pedido o nome do jogador a atualizar.
   * Depois, é pedido o valor a atualizar.
   * Por fim, é pedido o novo valor.
   * @param temp_players Lista de jogadores.
   */

  private static void updatePlayer(ListADT<IPlayer> temp_players) {
    String name = goTroughPlayers(temp_players, "Choose the player to update");
    IPlayer player = removePlayer(name);
    if (player == null) return;

    System.out.println("Name:" + player.getName() + ".Team" + player.getTeam());
    System.out.println("Choose the value to update: 1.Name 2.Team");
    Scanner sc = new Scanner(System.in);
    int option = sc.nextInt();
    switch (option) {
      case 1:
        System.out.println("Insert the new name");
        String new_name = sc.next();
        player.setName(new_name);
        break;
      case 2:
        System.out.println("Insert the new team");
        for (TeamType team : TeamType.values()) {
          System.out.println(team.ordinal() + " " + team);
        }
        int team = sc.nextInt();
        //Only setTeam if the integer is in range
        if (team >= 0 && team < TeamType.values().length)
          player.setTeam(TeamType.values()[team]);
        break;
      default:
        System.out.println("Invalid option");
    }
    ((ArrayUnorderedList<IPlayer>) (temp_players)).addToRear(player);
  }

  /**
   * Método que permite remover um jogador.
   * Primeiro, é pedido o nome do jogador a remover.
   * Depois, é removido o jogador.
   * @param name Nome do jogador.
   * @return Jogador removido.
   */
  public static IPlayer removePlayer(String name) {
    ArrayUnorderedList<IPlayer> temp_players = (ArrayUnorderedList<IPlayer>) SimulatePlay.getPlayers();
    IPlayer[] players = new IPlayer[temp_players.size()];
    int i = 0;
    for (IPlayer player : temp_players) {
      players[i++] = player;
    }

    Iterator<IPlayer> it = temp_players.iterator();
    while (it.hasNext()) {
      IPlayer player = it.next();
      if (name.equals(player.getName())) {
        it.remove();
        return player;
      }
    }
    return null;
  }

  /**
   * Método que permite interagir com os métodos de gestão de jogadores.
   * Primeiro, é pedido a opção.
   * Depois, é executada a opção.
   * Por fim, é mostrado o menu novamente.
   */
  public static void menu() {
    int option;
    do {
      System.out.println("1.Add player 2.Remove player 3.Update player 4.List Players 5.Export Players into File 6.Import Players from File 0.Exit");
      Scanner sc = new Scanner(System.in);
      option = sc.nextInt();
      switch (option) {
        case 1:
          System.out.println("Insert the name of the player");
          String name = sc.next();
          addPlayer(name, TeamType.NEUTRAL.toString());
          break;
        case 2:
          String name1 = goTroughPlayers(SimulatePlay.getPlayers(), "Choose the player to remove");
          removePlayer(name1);
          break;
        case 3:
          updatePlayer(SimulatePlay.getPlayers());
          break;
        case 4:
          listPlayers();
          break;
        case 5:
          exportPlayers();
          break;
        case 6:
          importPlayers();
          break;
        case 0:
          break;
        default:
          System.out.println("Invalid option");
      }
    } while (option != 0);
  }

  //List all the players by level and by team and number of portals currently controlled by each player

  /**
   * Método que lista todos os jogadores por nível e por equipa e número de portais atualmente controlados por cada jogador.
   */
  public static void listPlayers() {
    ArrayUnorderedList<IPlayer> temp_players = (ArrayUnorderedList<IPlayer>) SimulatePlay.getPlayers();
    IPlayer[] players = new IPlayer[temp_players.size()];
    int i = 0;
    for (IPlayer player : temp_players) {
      players[i++] = player;
    }
    SortingAndSearching.bubbleSort(players);
    for (IPlayer player : players) {
      System.out.println(player.toString());
    }
  }


  /**
   * Método que permite exportar os jogadores para um ficheiro.
   * Primeiro, é criado um objeto JSON.
   * Depois, é criado um array de jogadores.
   * Por fim, é adicionado o array de jogadores ao objeto JSON.
   * E é escrito no ficheiro.
   */
  public static void exportPlayers() {
    Gson gson = new Gson();
    Iterator<IPlayer> it = SimulatePlay.getPlayers().iterator();
    JSONObject json = new JSONObject();
    JSONArray playersArray = new JSONArray();
    while (it.hasNext()) {
      playersArray.add(gson.toJsonTree(it.next()));
    }

    json.put("players", playersArray);
    try {
      FileReader fileReader = new FileReader("game.json");
      JSONParser jsonParser = new JSONParser();
      JSONObject existingJson = (JSONObject) jsonParser.parse(fileReader);
      fileReader.close();

      existingJson.put("players", playersArray);
      FileWriter fileWriter = new FileWriter("game.json");
      fileWriter.write(existingJson.toJSONString());
      fileWriter.close();
    } catch (IOException | org.json.simple.parser.ParseException e) {
      try {
        FileWriter fileWriter = new FileWriter("game.json");
        fileWriter.write(json.toJSONString());
        fileWriter.close();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * Método que permite importar os jogadores de um ficheiro.
   */

  public static void importPlayers() {
    if (!SimulatePlay.getPlayers().isEmpty()) {
      return;
    }
    Gson gson = new Gson();
    ArrayUnorderedList<IPlayer> players = new ArrayUnorderedList<>();
    try {
      FileReader fileReader = new FileReader("Game.json");
      JsonObject json = gson.fromJson(fileReader, JsonObject.class);
      fileReader.close();

      JsonArray playersArray = json.get("players").getAsJsonArray();
      for (JsonElement playerElement : playersArray) {
        JsonObject playerObject = playerElement.getAsJsonObject();
        Player player = gson.fromJson(playerObject, Player.class);
        players.addToRear(player);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    SimulatePlay.setPlayers(players);
  }


}
