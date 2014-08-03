package org.t4atf.mauser.transaction;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;
import static org.t4atf.mauser.neo4j.MauserLabel.CITY;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.t4atf.mauser.neo4j.MauserRelations;
import org.t4atf.mauser.neo4j.Neo4jTest;
import org.t4atf.mauser.transaction.TransactionOperation;
import org.t4atf.mauser.transaction.TransactionWrapper;

public class TransactionWrapperIT extends Neo4jTest
{
  private final Label testLabel = AIRPORT;
  private final TransactionOperation operation = new TransactionWrapper(database);

  @Test
  public void createBrandNewIndexedNode()
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", "NEW");
    Node node = operation.createNode(properties, testLabel);

    assertThat(node.getId(), greaterThan(0L));
    try (Transaction tx = database.beginTx())
    {
      assertThat(node, equalTo(database.findNodesByLabelAndProperty(testLabel, "name", "NEW").iterator().next()));
    }
  }

  @Test
  public void cannotCreateANodeTwice()
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", "MXP");

    exception.expect(ConstraintViolationException.class);
    operation.createNode(properties, testLabel);
  }

  @Test
  public void nodeFound()
  {
    Node node = operation.findOne("name", testLabel, "MXP");

    assertThat(node, notNullValue());
  }

  @Test
  public void nodeNotFound()
  {
    Node node = operation.findOne("name", testLabel, "XXX");

    assertThat(node, nullValue());
  }

  @Test
  public void createRelation() throws Exception
  {
    Node start = buildStartNode();
    Node end = buildEndNode();
    Relationship relationship = operation.createRelationshipBetween(start, end, MauserRelations.CITY);

    verifyRelationshipHasBeenCorrectlySaved(start, end, relationship);
  }

  private void verifyRelationshipHasBeenCorrectlySaved(Node start, Node end, Relationship relationship)
  {
    Relationship savedRelationship;
    try (Transaction tx = database.beginTx())
    {
      savedRelationship = database.getRelationshipById(relationship.getId());
      assertThat(asList(savedRelationship.getNodes()), containsInAnyOrder(start, end));
    }
  }

  private Node buildEndNode()
  {
    return operation.findOne("name", CITY, "London");
  }

  private Node buildStartNode()
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", "MAD");
    properties.put("longName", "Barajas");
    Node start = operation.createNode(properties);
    return start;
  }
}
