package org.t4atf.mauser.geography.aiport;

import static org.t4atf.mauser.neo4j.MauserRelations.CITY;

import java.util.Collection;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
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

  public Collection<Path> flyTo(Airport landing)
  {
    return super.traverseTo(landing);
  }
}
