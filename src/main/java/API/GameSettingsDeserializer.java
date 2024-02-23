package API;

import API.Abstractions.AGameSettings;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author Márcio Ribeiro - 8200408
 * @author Hugo Ribeiro - 8200441
 */
public class GameSettingsDeserializer implements JsonDeserializer<AGameSettings> {


  /**
   * Gson invoca este método de retorno de chamada durante a desserialização quando encontra um campo do
   * tipo especificado.
   * <p>Na implementação deste método de retorno de chamada, você deve considerar invocar
   * Método {@link JsonDeserializationContext#deserialize(JsonElement, Type)} para criar objetos
   * para qualquer campo não trivial do objeto retornado. No entanto, você nunca deve invocá-lo no
   * o mesmo tipo passando {@code json}, pois isso causará um loop infinito (o Gson chamará seu
   * método de retorno de chamada novamente).
   *
   * @param json Os dados Json sendo desserializados
   * @param typeOfT O tipo do objeto para desserializar
   * @param contexto
   * @return um objeto desserializado do tipo especificado typeOfT que é uma subclasse de {@code T}
   * @throws JsonParseException se json não estiver no formato esperado de {@code typeofT}
   */
  @Override
  public AGameSettings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement type = jsonObject.get("ownerShip");


    if (type != null) {
      return context.deserialize(json, Portal.GameSettings.class);
    } else {
      return context.deserialize(json, Connector.GameSettings.class);
    }
  }
}
