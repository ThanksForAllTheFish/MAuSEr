package org.t4atf.mauser.geography.city;

import static org.t4atf.mauser.neo4j.MauserRelations.REGION;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.region.Region;
import org.t4atf.mauser.neo4j.MauserBaseEntity;
import org.t4atf.mauser.transaction.TransactionOperation;

public class City extends MauserBaseEntity
{
  public City(Node node)
  {
    super(node);
  }

  public void placeIn(Region region, TransactionOperation operation)
  {
    connectWith(region, REGION, operation);
  }
}
