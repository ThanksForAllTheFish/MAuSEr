package org.t4atf.mauser.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.t4atf.mauser.transaction.TransactionOperation;

public abstract class MauserUnitTesting
{
  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
  @Rule public ExpectedException exception = ExpectedException.none();
  
  protected final TransactionOperation transactionOperation = context.mock(TransactionOperation.class);
  
  protected Map<String, Object> buildExpectedProperties(final String name, String longName)
  {
    final Map<String, Object> expectedProperties = new HashMap<>();
    expectedProperties.put("name", name);
    expectedProperties.put("longName", longName);
    return expectedProperties;
  }

  protected abstract Label getLocalizedLabel();
}
