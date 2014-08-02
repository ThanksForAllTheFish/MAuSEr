package org.t4atf.mauser.geography.country;

import static org.t4atf.mauser.neo4j.MauserRelations.CONTINENT;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.continent.Continent;
import org.t4atf.mauser.neo4j.MauserBaseEntity;
import org.t4atf.mauser.transaction.TransactionOperation;

public class Country extends MauserBaseEntity
{
  public Country(Node node)
  {
    super(node);
  }

  public void placeIn(Continent continent, TransactionOperation operation)
  {
    connectWith(continent, CONTINENT, operation);
  }
}
