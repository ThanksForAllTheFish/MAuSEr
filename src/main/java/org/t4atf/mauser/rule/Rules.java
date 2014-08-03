package org.t4atf.mauser.rule;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;

import java.util.ArrayList;
import java.util.Collection;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;

public class Rules
{
  private final Collection<Rule> rules;

  private Rules(Collection<Rule> rules)
  {
    if(rules.isEmpty()) {
      Rule rule = new NoRule();
      this.rules = unmodifiableCollection(asList(rule));
    }
    else this.rules = unmodifiableCollection(rules);
  }

  public static Rules build(Collection<Path> paths)
  {
    Collection<Rule> rules = new ArrayList<>(paths.size() * 100 / 75 + 1);
    for (Path p : paths)
    {
      Collection<Object> elements = new ArrayList<>();
      for (Node n : p.nodes())
      {
        try (Transaction tx = n.getGraphDatabase().beginTx())
        {
          elements.add(n.getProperty("name"));
        }
      }
      rules.add(Rule.build(elements));
    }
    return new Rules(rules);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    for (Rule r : rules)
    {
      sb.append(r.toString()).append("\n");
    }
    return sb.toString().trim();
  }

  public Rule getBestMatch()
  {
    return rules.iterator().next();
  }
}
