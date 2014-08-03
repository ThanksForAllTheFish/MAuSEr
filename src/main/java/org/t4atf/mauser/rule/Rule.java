package org.t4atf.mauser.rule;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

public class Rule
{
  private final Collection<Object> elements;

  protected Rule(Collection<Object> elements)
  {
    this.elements = unmodifiableCollection(elements);
  }

  public static Rule build(Collection<Object> elements)
  {
    return elements.isEmpty() ? new NoRule() : new Rule(elements);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    for(Object e : elements) {
      sb.append(e.toString()).append("_");
    }
    String rule = sb.substring(0, sb.length() - 1);
    return rule;
  }
}