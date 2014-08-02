package org.t4atf.mauser.transaction;

import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.t4atf.mauser.excpetions.NodeAlreadyExistent;

public class TransactionWrapper implements TransactionOperation
{
  private GraphDatabaseService database;

  public TransactionWrapper(GraphDatabaseService database)
  {
    this.database = database;
  }

  @Override
  public Node createIndexedNode(Map<String, Object> properties, Index<Node> index, Label... labels)
  {
    try (Transaction tx = database.beginTx())
    {
      Node node = database.createNode(labels);
      for (String k : properties.keySet())
      {
        node.setProperty(k, properties.get(k));
      }
      index.add(node, "name", properties.get("name"));

      tx.success();
      return node;
    }
  }

  @Override
  public Index<Node> createIndexFor(Label label)
  {
    try (Transaction tx = database.beginTx())
    {
      return database.index().forNodes(label.name());
    }
  }

  @Override
  public void checkIndex(Index<Node> index, String indexKey, String indexValue)
  {
    try (Transaction tx = database.beginTx())
    {
      IndexHits<Node> potentialIndexHits = index.get(indexKey, indexValue);
      if (potentialIndexHits.hasNext())
      {
        throw new NodeAlreadyExistent("Cannot create node '" + indexValue + "'. It already exists as " + potentialIndexHits.getSingle());
      }
    }
  }

  @Override
  public Node find(String name, Index<Node> index)
  {
    try (Transaction tx = database.beginTx())
    {
      IndexHits<Node> potentialHists = index.get("name", name);
      return potentialHists.getSingle();
    }
  }
}
