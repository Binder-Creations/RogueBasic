package com.RogueBasic;
import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Player;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import java.util.List;

public class Driver {
  public static void main(String[] args) {
      try (Cluster cluster = Cluster.builder()
                                    .addContactPoint("127.0.0.1")
                                    .withPort(9042)
                                    .withoutJMXReporting()
                                    .build()) {
          Session session = cluster.connect("rogue_basic");
          session.execute("CREATE TABLE IF NOT EXISTS "
                  + "rogue_basic.PLAYER (id uuid PRIMARY KEY, userName text, password text, characterIds list<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)");
          MappingManager manager = new MappingManager(session);
          Mapper<Player> mapper = manager.mapper(Player.class);

          //save person
          Player p = new Player("Tester", "test123");
          mapper.save(p);
          executeNativeSelectQuery(session);

          //getting via mapper by primary key
          Player person = mapper.get(p.getId());
          System.out.println(person);

          //getting via mapper by primary key
          mapper.delete(p.getId());//delete by primary key
          executeNativeSelectQuery(session);
          session.execute("DROP TABLE rogue_basic.PLAYER");
      }
  }

  private static void executeNativeSelectQuery(Session session) {
      System.out.println("-- select query --");
      ResultSet rs = session.execute("select * from rogue_basic.Player");
      List<Row> rows = rs.all();
      System.out.println("rows: " + rows.size());
      for (Row row : rows) {
          UUID id = row.getUUID("id");
          System.out.println("Id: " + id);
          String name = row.getString("userName");
          System.out.println("name: " + name);
          String password = row.getString("password");
          System.out.println("password: " + password);
          int constitutionMetabonus = row.getInt("constitutionMetabonus");
          System.out.println("cm: " + constitutionMetabonus);
      }
  }
}
