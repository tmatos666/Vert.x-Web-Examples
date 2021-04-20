package io.vertx.blog.first;

import io.vertx.core.json.JsonObject;

public class Whisky {

  private int id;

  private String name;

  private String origin;

  public Whisky(String name, String origin) {
    this.name = name;
    this.origin = origin;
  }
  
  public Whisky(int id, String name, String origin) {
    this.name = name;
    this.origin = origin;
    this.id = id;
  }
  
  

  public JsonObject toJson() {
    JsonObject json = new JsonObject()
        .put("name", name)
        .put("origin", origin);
        json.put("_id", id);
    return json;
  }

  public String getName() {
    return name;
  }

  public String getOrigin() {
    return origin;
  }

  public int getId() {
    return id;
  }

  public Whisky setName(String name) {
    this.name = name;
    return this;
  }

  public Whisky setOrigin(String origin) {
    this.origin = origin;
    return this;
  }

  public Whisky setId(int id) {
    this.id = id;
    return this;
  }
}