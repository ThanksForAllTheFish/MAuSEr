package org.t4atf.mauser.geography.city;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserBaseEntity;

public class City extends MauserBaseEntity
{
  City(Node node)
  {
    super(node);
  }
}
