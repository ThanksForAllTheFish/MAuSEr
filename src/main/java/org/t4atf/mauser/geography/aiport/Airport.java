package org.t4atf.mauser.geography.aiport;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserBaseEntity;

public class Airport extends MauserBaseEntity
{
  Airport(final Node airport)
  {
    super(airport);
  }
}
