package org.t4atf.route;

import static org.t4atf.route.fixtures.DatabasePopulator.initializeWithFakeData;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jTest extends MauserUnitTesting
{
  protected static GraphDatabaseService database;
  
  @BeforeClass
  public static void setup()
  {
    database = new TestGraphDatabaseFactory().newImpermanentDatabase();
    try (Transaction tx = database.beginTx())
    {
      createUniqueIndexFor("name");

      tx.success();
    }
    initializeWithFakeData(database);
  }

  private static void createUniqueIndexFor(String indexName)
  {
    for (MauserLabel l : MauserLabel.values())
    {
      Schema schema = database.schema();
      schema.constraintFor(l).assertPropertyIsUnique(indexName).create();
      //IndexDefinition index = schema.indexFor(l).on(indexName).create();
      //schema.awaitIndexOnline(index, 10, TimeUnit.SECONDS);
    }
  }

  @AfterClass
  public static void teardown()
  {
    database.shutdown();
  }
}
