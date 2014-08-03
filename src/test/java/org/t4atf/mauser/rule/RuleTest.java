package org.t4atf.mauser.rule;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RuleTest
{
  @Test
  public void buildNonEmptyRule()
  {
    List<Object> elements = new ArrayList<>();
    elements.add("NEW");
    Rule rule = Rule.build(elements);
    
    assertThat(rule.toString(), equalTo("NEW"));
  }

  @Test
  public void buildEmptyRule()
  {
    Rule rule = Rule.build(new ArrayList<Object>());
    
    assertThat(rule.toString(), equalTo("No rule found"));
  }
}