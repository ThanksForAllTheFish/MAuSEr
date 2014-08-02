package org.t4atf.route;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class MauserUnitTesting
{
  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
  @Rule public ExpectedException exception = ExpectedException.none();
}
