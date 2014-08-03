package org.t4atf.mauser.rule;

import java.util.ArrayList;

public class NoRule extends Rule
{
  protected NoRule() {
    super(new ArrayList<Object>());
  }

  @Override
  public String toString()
  {
    return "No rule found";
  }
}
