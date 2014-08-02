package org.t4atf.mauser.neo4j;

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
  protected final Index<Node> index = context.mock(Index.class);
  
  public void init()
  {
    context.checking(new Expectations()
    {
      {
        allowing(transactionOperation).createIndexFor(getLocalizedLabel());
        will(returnValue(index));
      }

    });
  }

  protected abstract Label getLocalizedLabel();
}
