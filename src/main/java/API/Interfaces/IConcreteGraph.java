package API.Interfaces;

import API.Graph.PathWithWeight;
import com.so.Collections.Arrays.ArrayList;

/**
 * Representa as operações para manipulação de um grafo.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public interface IConcreteGraph {

  /**
   * Adiciona uma rota.
   *
   * @param start O ponto de início da rota.
   * @param end   O ponto de destino da rota.
   * @return
   */
  boolean addRoute(int start, int end);

  /**
   * Remove uma rota.
   *
   * @param start O ponto de início da rota.
   * @param end   O ponto de destino da rota.
   * @return
   */
  boolean removeRoute(int start, int end);

  /**
   * Remove um lugar pelo ID.
   * @param id O ID do lugar a ser removido.
   */
  void removePlace(int id);


  /**
   * Adiciona um lugar ao grafo.
   * @param place O lugar a ser adicionado.
   */
  void addPlace(ILocalType place);

  /**
   * Executa o algoritmo de Dijkstra a partir do ponto de início até o ponto final.
   * @param start O ponto de início.
   * @param end O ponto final.
   * @return Uma lista de lugares que representam o caminho mais curto entre o ponto de início e o ponto final.
   */
  ArrayList<ILocalType> djistkra(ILocalType start, ILocalType end);



}
