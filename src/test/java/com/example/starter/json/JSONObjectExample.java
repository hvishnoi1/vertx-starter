package com.example.starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONObjectExample {

  @Test
  void jsonObjectCanBeMapped() {
    final JsonObject jsonObject = new JsonObject();
    jsonObject.put("id", 1);
    jsonObject.put("name", "Alice");

    String encodedJsonObject = jsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Alice\"}", encodedJsonObject);

    final JsonObject decodedJsonObject = new JsonObject(encodedJsonObject);
    assertEquals(jsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    final Map<String, Object> map = new HashMap<String, Object>();

    map.put("id", 1);
    map.put("name", "Alice");

    final JsonObject jsonObject = new JsonObject(map);
    assertEquals(map, jsonObject.getMap());
    assertEquals(1, jsonObject.getInteger("id"));
    assertEquals("Alice", jsonObject.getString("name"));
  }

  @Test
  void jsonArrayCanBeMapped() {
    final JsonArray jsonArray = new JsonArray();
    jsonArray
      .add(new JsonObject().put("id", 1))
      .add(new JsonObject().put("id", 2))
      .add(new JsonObject().put("id", 3))
      .add(new JsonObject().put("id", 4))
      .add("Random Value");

    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4},\"Random Value\"]", jsonArray.encode());
  }

  @Test
  void canMapJavaObjects(){
    final Person person = new Person(1,"Alice");
    final JsonObject jsonObject = JsonObject.mapFrom(person);

    assertEquals(person.getId(),jsonObject.getInteger("id"));
    assertEquals(person.getName(),jsonObject.getString("name"));

    final Person alice = jsonObject.mapTo(Person.class);
    assertEquals(person.getId(),alice.getId());
    assertEquals(person.getName(),alice.getName());
  }
}
