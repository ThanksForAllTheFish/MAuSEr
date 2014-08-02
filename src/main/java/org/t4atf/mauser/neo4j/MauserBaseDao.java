package org.t4atf.mauser.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.t4atf.mauser.transaction.TransactionOperation;

public abstract class MauserBaseDao<TYPE extends MauserBaseEntity>
{
  protected final TransactionOperation operation;
  protected final Index<Node> index;
  private final Label label;

  protected MauserBaseDao(TransactionOperation transactionOperation, Label label)
  {
    this.operation = transactionOperation;
    this.label = label;
    this.index = operation.createIndexFor(label);
  }

  public TYPE create(String code, String name)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", code);
    properties.put("longName", name);
    operation.checkIndex(index, "name", code);
    Node node = operation.createIndexedNode(properties, index, label);
    
    return createEntity(node);
  }
  
  public TYPE findByName(String name)
  {
    Node node = operation.find(name, index);
    if(null == node) return createNonExistentEntity(name);
    return createEntity( node );
  }

  protected abstract TYPE createNonExistentEntity(String code);
  protected abstract TYPE createEntity(Node node);
}
