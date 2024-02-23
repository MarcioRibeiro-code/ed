package API.Graph;

import API.Interfaces.ILocalType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.so.Collections.ListADT;

/**
 * Classe que representa um caminho com o seu peso
 * <p>
 *   Esta classe é utilizada para representar um caminho com o seu peso
 *   no grafo.
 *   Esta classe implementa a interface Comparable para que possa ser
 *   comparada com outras instâncias desta classe.
 *
 *   @author Márcio Ribeiro - 8200408
 *   @author Hugo Ribeiro - 8200441
 */
public class PathWithWeight implements Comparable<PathWithWeight> {
  @SerializedName("path")
  @Expose
  ListADT<ILocalType> path;
  @SerializedName("weight")
  @Expose
  int weight;

  /**
   * Construtor da classe PathWithWeight
   * @param path caminho
   * @param weight peso do caminho
   */
  public PathWithWeight(ListADT<ILocalType> path, int weight) {
    this.path = path;
    this.weight = weight;
  }

  /**
   * Método que nos retorna o caminho
   * @return caminho
   */
  public ListADT<ILocalType> getPath() {
    return this.path;
  }

  /**
   * Método que nos retorna o peso do caminho
   * @return peso do caminho
   */
  public int getWeight() {
    return this.weight;
  }

  /**
   * Método que devolve uma representação em String do caminho com o seu peso
   * @return representação em String do caminho com o seu peso
   */
  public String toString() {
    return "PathWithWeight{path=" + this.path + ", weight=" + this.weight + '}';
  }

  /**
   * Compara este objeto com o objeto especificado para pedido. Retorna um
   * inteiro negativo, zero ou um inteiro positivo, pois este objeto é menor
   * que, igual ou maior que o objeto especificado.
   *
   * <p>O implementador deve garantir
   * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
   * para todos os {@code x} e {@code y}. (Esse
   * implica que {@code x.compareTo(y)} deve lançar uma exceção iff
   * {@code y.compareTo(x)} lança uma exceção.)
   *
   * <p>O implementador também deve garantir que a relação seja transitiva:
   * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implica
   * {@code x.compareTo(z) > 0}.
   *
   * <p>Finalmente, o implementador deve garantir que {@code x.compareTo(y)==0}
   * implica que {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, para
   * todos {@code z}.
   *
   * <p>É fortemente recomendado, mas <i>não</i> estritamente necessário que
   * {@code (x.compareTo(y)==0) == (x.equals(y))}. De um modo geral, qualquer
   * classe que implementa a interface {@code Comparable} e viola
   * esta condição deve indicar claramente este fato. O recomendado
   * language é "Nota: esta classe tem uma ordem natural que é
   * inconsistente com iguais."
   *
   * <p>Na descrição anterior, a notação
   * {@code sgn(}<i>expression</i>{@code )} designa a matemática
   * Função <i>signum</i>, que é definida para retornar um dos {@code -1},
   * {@code 0} ou {@code 1} conforme o valor de
   * <i>expressão</i> é negativo, zero ou positivo, respectivamente.
   *
   * @param o objeto a ser comparado.
   * @return um inteiro negativo, zero ou um inteiro positivo como este objeto
   * é menor, igual ou maior que o objeto especificado.
   * @throws NullPointerException se o objeto especificado for nulo
   * @throws ClassCastException se o tipo do objeto especificado o impedir
   * de ser comparado a este objeto.
   */
  @Override
  public int compareTo(PathWithWeight o) {
    return Integer.compare(this.weight, o.weight);
  }

  /**
   * Método que devolve uma representação em Json do caminho com o seu peso
   * @return representação em Json do caminho com o seu peso
   */
  public JsonObject toJson() {
    Gson gson = new Gson();
    JsonObject jsonObject = new JsonObject();
    JsonArray pathArray = new JsonArray();
    for (ILocalType localType : path) {
      JsonElement jsonElement = gson.toJsonTree(localType);
      pathArray.add(jsonElement);
    }
    jsonObject.add("path", pathArray);
    jsonObject.addProperty("weight", weight);
    return jsonObject;
  }
}
