package org.t4atf.mauser.geography.region;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.country.Country;
import org.t4atf.mauser.neo4j.MauserBaseEntity;
import org.t4atf.mauser.neo4j.MauserRelations;
import org.t4atf.mauser.transaction.TransactionOperation;

public class Region extends MauserBaseEntity
{
  public Region(Node node)
  {
    super(node);
  }

  public void placeIn(Country country, TransactionOperation operation)
  {
    connectWith(country, MauserRelations.COUNTRY, operation);
  }
}
