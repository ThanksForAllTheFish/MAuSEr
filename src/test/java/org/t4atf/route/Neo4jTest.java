package org.t4atf.route;

import static org.t4atf.route.fixtures.DatabasePopulator.initializeWithFakeData;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jTest
{
  protected static GraphDatabaseService database;

  @BeforeClass
  public static void setup()
  {
    database = new TestGraphDatabaseFactory().newImpermanentDatabase();
    try (Transaction tx = database.beginTx())
    {
      for(MauserLabel l : MauserLabel.values())
        database.schema().constraintFor(l).assertPropertyIsUnique("name").create();
      tx.success();
    }
    initializeWithFakeData(database);
  }

  @AfterClass
  public static void teardown()
  {
    database.shutdown();
  }
}
