package org.t4atf.mauser.geography.aiport;

import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.t4atf.mauser.transaction.TransactionOperation;

public class AirportDao
{
  private final TransactionOperation operation;
  private final Index<Node> index;

  public AirportDao(TransactionOperation transactionOperation)
  {
    this.operation = transactionOperation;
    this.index = operation.createIndexFor(AIRPORT);
  }

  public Airport create(String code, String name)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", code);
    properties.put("longName", name);
    operation.checkIndex(index, "name", code);
    Node airport = operation.createIndexedNode(properties, index, AIRPORT);
    
    return new Airport(airport);
  }

  public Airport findByCode(String code)
  {
    Node airport = operation.find(code, index);
    if(null == airport) return new NonExistentAirport(code);
    return new Airport( airport );
  }
}
