package API;

import API.Graph.PathWithWeight;
import API.Interfaces.IConcreteGraph;
import API.Interfaces.IGlobalSettings;
import API.Interfaces.ILocalType;
import API.Interfaces.IPortal;
import com.so.Collections.Arrays.ArrayList;
import com.so.Collections.Arrays.ArrayOrderedList;
import com.so.Collections.Arrays.ArrayUnorderedList;
import com.so.Collections.Arrays.SortingAndSearching;
import com.so.Collections.Graphs.WGraph;
import com.so.Collections.ListADT;
import com.so.Collections.Lists.DoubleUnorderedList;
import com.so.Collections.Lists.LinkedList;
import com.so.Collections.Map.HashMap;
import com.so.Collections.Queues.LinkedQueue;
import com.so.Collections.Queues.QueueADT;

import java.util.Iterator;


/**
 * ConcreteGraph é uma classe que extende WGraph e implementa IConcreteGraph.
 * É usada para armazenar o grafo com pesos do jogo.
 * Também armazena as rotas do grafo em um hashmap.
 * A chave do hashmap é o vértice de partida.
 * O valor do hashmap é uma lista de vértices de chegada.
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class ConcreteGraph extends WGraph<ILocalType> implements IConcreteGraph {


  private final HashMap<Integer, ArrayUnorderedList<Integer>> routes;

  /**
   * Construtor da classe.
   */
  public ConcreteGraph() {
    super();
    routes = new HashMap<>();
  }

  /**
   * Adiciona uma rota "edge" entre dois vértices e inseri-la no hashmap routes.
   *
   * @param start vertice de partida
   * @param end   vertice de chegada
   * @return true se a rota foi adicionada com sucesso, false caso contrário
   */
  @Override
  public boolean addRoute(int start, int end) {
    if (start == end || super.isEmpty()) return false;

    // Dont allow to Portals to be connected to themselves
    if (getVertex(start) instanceof IPortal && getVertex(end) instanceof IPortal) {
      return false;
    }

    int startIndex = super.getIndex(getVertex(start));
    int endIndex = super.getIndex(getVertex(end));
    if ((startIndex != -1 && endIndex != -1) && super.adjMatrix[startIndex][endIndex] == Integer.MAX_VALUE) {
      super.addEdge(startIndex, endIndex, IGlobalSettings.calculateEdgeWeight(getVertex(start).getCoordinates(), getVertex(end).getCoordinates()));
      if (!routes.isEmpty() && routes.containsKey(start)) {
        routes.get(start).addToRear(end);
      } else {
        ArrayUnorderedList<Integer> list = new ArrayUnorderedList<>();
        list.addToRear(end);
        routes.put(start, list);
      }
      return true;
    }

    return false;
  }


  /**
   * Remove uma rota "edge" entre dois vértices e remove-a do hashmap routes.
   * Primeiro valor da rota é a chave do hashmap.
   * Segundo valor da rota é o valor a ser removido da lista.
   *
   * @param start vertice de partida
   * @param end   vertice de chegada
   * @return true se a rota foi removida com sucesso, false caso contrário
   */
  @Override
  public boolean removeRoute(int start, int end) {
    if (start == end || super.isEmpty()) return false;

    int startIndex = super.getIndex(getVertex(start));
    int endIndex = super.getIndex(getVertex(end));

    if (startIndex == -1 || endIndex == -1) return false;

    QueueADT<Integer> indices = new LinkedQueue<>() {{
      enqueue(startIndex);
      enqueue(endIndex);
    }};
    QueueADT<Integer> keys = new LinkedQueue<>() {{
      enqueue(start);
      enqueue(end);
    }};

    while (!indices.isEmpty()) {
      int index = indices.dequeue();
      int key = keys.dequeue();

      if (super.adjMatrix[index][index == startIndex ? endIndex : startIndex] != Integer.MAX_VALUE) {
        if (routes.containsKey(key)) {
          ArrayList<Integer> list = routes.get(key);
          list.remove(key == start ? end : start);
          if (list.isEmpty()) {
            routes.remove(key);
          }
          super.removeEdge(index, index == startIndex ? endIndex : startIndex);
        }
      }
    }
    return true;
  }


  /**
   * Baseado no ID do ILocalType, retorna o índice do vértice no grafo.
   *
   * @param id o ID do ILocalType
   * @return Object que representa ILocalType
   */
  public ILocalType getVertex(int id) {
    VerticesIterator iterator = new VerticesIterator(vertices);
    while (iterator.hasNext()) {
      ILocalType vertex = (ILocalType) iterator.next();
      if (vertex.getID() == id) return vertex;
    }
    return null;
  }

  /**
   * Método que permite introduzir um vértice (ILocalType) no grafo.
   * @param place O lugar a ser adicionado.
   */
  @Override
  public void addPlace(ILocalType place) {
    super.addVertex(place);
  }

  /**
   * Remove um vértice do grafo.
   *
   * @param id ID do vértice a ser removido
   */
  @Override
  public void removePlace(int id) {
    ILocalType vertex = getVertex(id);
    if (vertex != null) {
      super.removeVertex(vertex);
    }
  }

  /**
   * Método que permite obter o caminho mais curto entre dois vértices.
   *<p>
   *   O caminho é representado por uma lista de ILocalType.
   *   O primeiro elemento da lista é o vértice de partida e o último é o vértice de chegada.
   *   Os elementos intermédios são os vértices que compõem o caminho.
   *
   * @param start O ponto de início.
   * @param end O ponto final.
   * @return Uma lista de ILocalType que representa o caminho mais curto entre os dois pontos.
   */
  @Override
  public ArrayList<ILocalType> djistkra(ILocalType start, ILocalType end) {
    return super.djisktra(start, end);
  }


  /**
   * Método que permite obter todos os caminhos possíveis entre dois vértices, tendo que passar por um conjunto de vértices.
   * @param toPass Conjunto de vértices que têm que ser passados.
   * @param start O ponto de início.
   * @param end O ponto final.
   * @return Uma lista de caminhos possíveis.
   */
  public ArrayList<PathWithWeight> findPaths(ArrayList<ILocalType> toPass, ILocalType start, ILocalType end) {
    ArrayOrderedList<PathWithWeight> validPaths = new ArrayOrderedList();
    Iterator var6 = this.findAllPathsWithWeight(start, end).iterator();

    while (var6.hasNext()) {
      PathWithWeight pathWithWeight = (PathWithWeight) var6.next();
      ArrayUnorderedList<ILocalType> path = (ArrayUnorderedList) pathWithWeight.getPath();
      boolean isValid = true;
      Iterator var10 = toPass.iterator();

      while (var10.hasNext()) {
        ILocalType typeToPass = (ILocalType) var10.next();
        if (!path.contains(typeToPass)) {
          isValid = false;
          break;
        }
      }

      if (isValid) {
        validPaths.add(pathWithWeight);
      }
    }

    return validPaths;
  }

  /**
   * Método que permite obter todos os caminhos possíveis entre dois vértices, junto com o seu peso.
   * @param startVertex O ponto de início.
   * @param endVertex O ponto final.
   * @return Uma lista de caminhos possíveis.
   */
  private ListADT<PathWithWeight> findAllPathsWithWeight(ILocalType startVertex, ILocalType endVertex) {
    int startIndex = this.getIndex(startVertex);
    int endIndex = this.getIndex(endVertex);
    boolean[] isVisited = new boolean[this.numVertices];
    ArrayList<ILocalType> currentPath = new ArrayUnorderedList();
    ListADT<PathWithWeight> allPaths = new ArrayUnorderedList();
    this.findAllPathsWithWeight(startIndex, endIndex, isVisited, currentPath, allPaths, 0);

    PathWithWeight[] paths = new PathWithWeight[allPaths.size()];
    int i = 0;
    for (int size = allPaths.size(); i < size; ++i) {
      paths[i] = allPaths.removeFirst();
    }
    SortingAndSearching.mergeSort(paths, 0, paths.length - 1);
    for (PathWithWeight path : paths)
      ((ArrayUnorderedList) allPaths).addToRear(path);

    return allPaths;
  }

  /**
   * Método que permite obter o ILocalType de um vértice, dado o seu índice.
   *
   * @param pos Indice do vértice
   * @return O ILocalType do vértice
   */
  ILocalType getVertex_Pos(int pos) {
    int i = 0;

    for (VerticesIterator it = new VerticesIterator(this.vertices); it.hasNext(); ++i) {
      ILocalType localType = (ILocalType) it.next();
      if (pos == i) {
        return localType;
      }
    }

    return null;
  }

  /**
   * Helper method para encontrar todos os caminhos possíveis entre dois vértices, junto com o seu peso.
   * @param currentIndex Current index
   * @param endIndex End index
   * @param isVisited Array de vértices visitados
   * @param currentPath Caminho atual
   * @param allPaths Lista de todos os caminhos
   * @param weight Peso do caminho
   */
  private void findAllPathsWithWeight(int currentIndex, int endIndex, boolean[] isVisited, ListADT<ILocalType> currentPath, ListADT<PathWithWeight> allPaths, int weight) {
    // System.out.println("INDEX" + currentIndex + " VERTEX" + this.getVertex_Pos(currentIndex));
    ((ArrayUnorderedList) currentPath).addToRear(this.getVertex_Pos(currentIndex));
    isVisited[currentIndex] = true;
    if (currentIndex == endIndex) {
      ArrayUnorderedList<ILocalType> copyPath = new ArrayUnorderedList();
      Iterator<ILocalType> it = currentPath.iterator();

      while (it.hasNext()) {
        copyPath.addToRear((ILocalType) it.next());
      }

      ((ArrayUnorderedList) allPaths).addToRear(new PathWithWeight(copyPath, weight));
    } else {
      for (int i = 0; i < this.numVertices; ++i) {
        if (this.adjMatrix[currentIndex][i] != Integer.MAX_VALUE && !isVisited[i]) {
          this.findAllPathsWithWeight(i, endIndex, isVisited, currentPath, allPaths, weight + this.adjMatrix[currentIndex][i]);
        }
      }
    }

    currentPath.removeLast();
    isVisited[currentIndex] = false;
  }

  /**
   * Método que permite obter o índice de um vértice, dado o seu ILocalType.
   *
   * @param <T> Tipo genérico do vértice
   */
  private static class VerticesIterator<T> implements Iterator<T> {
    private final T[] vertices;
    private int currentIndex = 0;

    /**
     * Construtor do iterador de vértices.
     *
     * @param vertices Array de vértices
     */
    public VerticesIterator(T[] vertices) {
      this.vertices = vertices;
    }

    /**
     * Método que permite verificar se existe um próximo vértice.
     *
     * @return True se existir um próximo vértice, false caso contrário.
     */
    @Override
    public boolean hasNext() {
      return currentIndex < vertices.length && vertices[currentIndex] != null;
    }

    /**
     * Método que permite obter o próximo vértice.
     * @return O próximo vértice.
     */
    @Override
    public T next() {
      return vertices[currentIndex++];
    }
  }

  /**
   * Método que permite obter o HashMap de rotas.
   * @return O HashMap de rotas.
   */
  public HashMap<Integer, ArrayUnorderedList<Integer>> getRoutes() {
    return routes;
  }

  /**
   * Método que permite obter a lista de vértices.
   * @return A lista de vértices.
   */
  public ArrayList<ILocalType> getPlaces() {
    ArrayUnorderedList<ILocalType> places = new ArrayUnorderedList<>();

    VerticesIterator iterator = new VerticesIterator(vertices);
    while (iterator.hasNext()) {
      ILocalType vertex = (ILocalType) iterator.next();
      places.addToRear(vertex);
    }
    return places;
  }

  /**
   * Método que permite obter todas as rotas possíveis entre todos os vertices.
   * @return Lista de todas as rotas possíveis entre todos os vértices.
   */
  public ListADT<PathWithWeight> findAllPaths() {
    DoubleUnorderedList<PathWithWeight> allPathsForAllPlaces = new DoubleUnorderedList<>();
    for (int i = 0; i < this.numVertices; i++) {
      for (int j = 0; j < this.numVertices; j++) {
        if (i != j) {
          allPathsForAllPlaces.addToRear(this.findAllPathsWithWeight(this.getVertex_Pos(i), this.getVertex_Pos(j)).first());
        }
      }
    }
    return allPathsForAllPlaces;
  }

  /**
   * Método que permite o utilizador visualizar os locais que pode visitar a partir de um determinado local.
   * @param vertex Vértice a partir do qual se quer visualizar os locais que se pode visitar.
   * @return Lista de locais que se pode visitar a partir do vértice.
   */
  public ListADT<ILocalType> displayPlaces(ILocalType vertex) {
    LinkedList<ILocalType> places = new LinkedList<>();
    int index = this.getIndex(vertex);
    System.out.println("Places to visit from " + vertex.getID() + ":");
    for (int i = 0; i < this.numVertices; i++) {
      if (this.adjMatrix[index][i] != Integer.MAX_VALUE) {
        System.out.println(this.getVertex_Pos(i));
        places.add(this.getVertex_Pos(i));
      }
    }
    return places;
  }

}


