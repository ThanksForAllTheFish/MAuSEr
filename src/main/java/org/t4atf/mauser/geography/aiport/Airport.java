package org.t4atf.mauser.geography.aiport;

import static org.t4atf.mauser.neo4j.MauserRelations.CITY;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.city.City;
import org.t4atf.mauser.neo4j.MauserBaseEntity;
import org.t4atf.mauser.transaction.TransactionOperation;

public class Airport extends MauserBaseEntity
{
  public Airport(final Node airport)
  {
    super(airport);
  }

  protected void placeIn(City city, TransactionOperation operation)
  {
    super.connectWith(city, CITY, operation);
  }
}
