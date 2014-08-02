package org.t4atf.transaction;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.route.MauserLabel.AIRPORT;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.t4atf.route.Neo4jTest;
import org.t4atf.route.excpetions.NodeAlreadyExistent;

public class TransactionWrapperTest extends Neo4jTest
{
  private final Label testLabel = AIRPORT;
  private final TransactionOperation operation = new TransactionWrapper(database);
  private Index<Node> index;

  @Before
  public void init()
  {
    try (Transaction tx = database.beginTx())
    {
      index = database.index().forNodes(testLabel.name());
    }
  }

  @Test
  public void indexIsCreatedProperly()
  {
    Index<Node> operationIndex = operation.createIndexFor(testLabel);

    assertThat(operationIndex, equalTo(index));
  }

  @Test
  public void createBrandNewIndexedNode()
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", "NEW");
    Node node = operation.createIndexedNode(properties, index, testLabel);

    assertThat(node.getId(), greaterThan(0L));
    try (Transaction tx = database.beginTx())
    {
      assertThat(node, equalTo(index.get("name", "NEW").getSingle()));
    }
  }
  
  @Test
  public void cannotCreateANodeTwice()
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", "MXP");
    
    exception.expect(ConstraintViolationException.class);
    operation.createIndexedNode(properties, index, testLabel);
  }
  
  @Test
  public void nodeExistsInIndex()
  {
    exception.expect(NodeAlreadyExistent.class);
    operation.checkIndex(index, "name", "MXP");
  }
  
  @Test
  public void nodeDoesNotExistInIndex()
  {
    operation.checkIndex(index, "name", "XXX");
  }
  
  @Test
  public void nodeFound()
  {
    Node node = operation.find("MXP", index);
    
    assertThat(node, notNullValue());
  }
  
  @Test
  public void nodeNotFound()
  {
    Node node = operation.find("XXX", index);
    
    assertThat(node, nullValue());
  }
}