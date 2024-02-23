package Program;

import API.*;
import API.Interfaces.IConnector;
import API.Interfaces.ILocalType;
import API.Interfaces.IPlayer;
import API.Interfaces.IPortal;
import com.so.Collections.Arrays.ArrayUnorderedList;

import java.util.Scanner;

/**
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class Main {
  public static void main(String[] args) {

    /*
     *
     * MENU PARA SER USADO
     * */

    //Menu para Manage_Game,Manage_Players,Manage_Local,Manage_Routes
    int option;
    Scanner sc = new Scanner(System.in);
    do {
      System.out.println("Choose an option:\n" +
          " 1.Manage_Game 2.Manage_Players 3.Manage_Local 4.Manage_Routes 5.Simulate Play 6.Load File  7.Export File 0.STOP");
      option = sc.nextInt();
      switch (option) {
        case 1:
          Manage_Game.menu();
          break;
        case 2:
          Manage_Players.menu();
          break;
        case 3:
          Manage_Local.menu();
          break;
        case 4:
          Manage_Routes.menu();
          break;
        case 5:
          SimulatePlay jogar = new SimulatePlay();
          jogar.play();
          break;
        case 6:
          SimulatePlay loadFile = new SimulatePlay();
          loadFile.loadFile();
          break;
        case 7:
          SimulatePlay saveFile = new SimulatePlay();
          saveFile.saveFile();
          break;
        case 0:
          break;
        default:
          System.out.println("Invalid option");
          break;
      }
    } while (option != 0);


  }
}
