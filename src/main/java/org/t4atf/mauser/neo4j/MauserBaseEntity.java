package org.t4atf.mauser.neo4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
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
}
