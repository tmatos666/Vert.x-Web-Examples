package io.vertx.blog.first;

import java.util.Objects;

public class Whisky implements Comparable<Whisky>{

  private int id;

  private String name;

  private String origin;

  public Whisky(int id, String name, String origin) {
    this.name = name;
    this.origin = origin;
    this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.origin);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Whisky other = (Whisky) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.origin, other.origin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Whisky{" + "id=" + id + ", name=" + name + ", origin=" + origin + '}';
    }

    @Override
    public int compareTo(Whisky o) {
        return this.getId() - o.getId();
    }
   
}