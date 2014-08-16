package org.t4atf.mauser.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.traversal.Uniqueness.NODE_PATH;
import static org.t4atf.mauser.neo4j.MauserRelations.CITY;
import static org.t4atf.mauser.neo4j.MauserRelations.CONTINENT;
import static org.t4atf.mauser.neo4j.MauserRelations.COUNTRY;
import static org.t4atf.mauser.neo4j.MauserRelations.REGION;

import java.util.Collection;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.t4atf.mauser.transaction.TransactionOperation;

public abstract class MauserBaseEntity
{
  private final Node node;

  protected MauserBaseEntity(Node node)
  {
    this.node = node;
  }

  public long getId()
  {
    return node.getId();
  }

  @Override
  public int hashCode()
  {
    return node.getProperty("name").hashCode();
  }

  @Override
  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  protected void connectWith(MauserBaseEntity endNode, RelationshipType relationship, TransactionOperation operation)
  {
    operation.createRelationshipBetween(node, endNode.node, relationship);
  }

  public Collection<Path> traverseTo(MauserBaseEntity endNode)
  {
    GraphDatabaseService database = node.getGraphDatabase();
    try (Transaction tx = database.beginTx())
    {
      TraversalDescription bothSide = database.traversalDescription().depthFirst()
        .relationships(CITY, OUTGOING)
        .relationships(REGION, OUTGOING)
        .relationships(COUNTRY, OUTGOING)
        .relationships(CONTINENT, OUTGOING)
        .uniqueness(NODE_PATH);
      
      return IteratorUtils.toList(
        database
          .bidirectionalTraversalDescription()
          .startSide(bothSide)
          .endSide(bothSide)
          .traverse(node, endNode.node)
          .iterator());
    }
  }
}
