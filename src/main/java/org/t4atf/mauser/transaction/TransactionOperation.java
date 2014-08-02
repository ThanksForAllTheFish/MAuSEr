package org.t4atf.mauser.transaction;

import java.util.Map;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.Index;

public interface TransactionOperation
{
  Node createIndexedNode(Map<String, Object> properties, Index<Node> index, Label... label);

  Index<Node> createIndexFor(Label label);

  void checkIndex(Index<Node> index, String indexKey, String indexValue);

  Node find(String name, Index<Node> index);

  Relationship createRelationshipBetween(Node start, Node end, RelationshipType type);
}