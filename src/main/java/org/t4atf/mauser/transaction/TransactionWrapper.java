package org.t4atf.mauser.transaction;

import static org.apache.commons.collections4.IteratorUtils.toList;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class TransactionWrapper implements TransactionOperation
{
  private GraphDatabaseService database;

  public TransactionWrapper(GraphDatabaseService database)
  {
    this.database = database;
  }

  @Override
  public Node createNode(Map<String, Object> properties, Label... labels)
  {
    try (Transaction tx = database.beginTx())
    {
      Node node = database.createNode(labels);
      for (String k : properties.keySet())
      {
        node.setProperty(k, properties.get(k));
      }

      tx.success();
      return node;
    }
  }

  @Override
  public Node findOne(String indexKey, Label indexLabel, String indexValue)
  {
    try (Transaction tx = database.beginTx())
    {
      List<Node> nodes = findBy(indexKey, indexLabel, indexValue);
      if(!nodes.isEmpty()) return nodes.get(0);
      return null;
    }
  }

  @Override
  public List<Node> findBy(String searchKey, Label label, String searchValue)
  {
    return toList(database.findNodesByLabelAndProperty(label, searchKey, searchValue).iterator());
  }

  @Override
  public Relationship createRelationshipBetween(Node start, Node end, RelationshipType type)
  {
    try (Transaction tx = database.beginTx())
    {
      Relationship relationship = start.createRelationshipTo(end, type);
      tx.success();
      return relationship;
    }
  }
}
