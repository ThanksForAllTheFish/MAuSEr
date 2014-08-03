package org.t4atf.mauser.transaction;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

public interface TransactionOperation
{
  Node createNode(Map<String, Object> properties, Label... label);

  Node findOne(String indexKey, Label indexLabel, String indexValue);

  Relationship createRelationshipBetween(Node start, Node end, RelationshipType type);

  List<Node> findBy(String searchKey, Label label, String searchValue);
}