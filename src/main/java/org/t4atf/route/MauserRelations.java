package org.t4atf.route;

import org.neo4j.graphdb.RelationshipType;

public enum MauserRelations implements RelationshipType
{
  CITY("Airport city"), REGION("Region"), COUNTRY("Country"), CONTINENT("Continent"), FLY_IN("Airline serving given node");
  
  private final String description;
  
  MauserRelations(String description) {
    this.description = description;
  }
}
