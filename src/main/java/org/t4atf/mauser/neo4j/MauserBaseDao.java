package org.t4atf.mauser.neo4j;

import java.util.Map;

import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.excpetions.NodeAlreadyExistent;
import org.t4atf.mauser.transaction.TransactionOperation;

public abstract class MauserBaseDao<TYPE extends MauserBaseEntity>
{
  protected final TransactionOperation operation;
  private final Label label;

  protected MauserBaseDao(TransactionOperation transactionOperation, Label label)
  {
    this.operation = transactionOperation;
    this.label = label;
  }

  public TYPE findOne(String name)
  {
    Node node = operation.findOne("name", label, name);
    if (null == node) return createNonExistentEntity(name);
    return createEntity(node);
  }

  protected TYPE create(Map<String, Object> properties)
  {
    try
    {
      Node node = operation.createNode(properties, label);
      return createEntity(node);
    }
    catch (ConstraintViolationException e)
    {
      throw new NodeAlreadyExistent(e.getMessage());
    }
  }

  protected abstract TYPE createNonExistentEntity(String name);

  protected abstract TYPE createEntity(Node node);
}
