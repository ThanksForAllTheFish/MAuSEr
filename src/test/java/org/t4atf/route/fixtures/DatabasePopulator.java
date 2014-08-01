package org.t4atf.route.fixtures;

import static org.t4atf.route.MauserLabel.AIRLINE;
import static org.t4atf.route.MauserLabel.AIRPORT;
import static org.t4atf.route.MauserLabel.CITY;
import static org.t4atf.route.MauserLabel.CONTINENT;
import static org.t4atf.route.MauserLabel.COUNTRY;
import static org.t4atf.route.MauserLabel.REGION;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.t4atf.route.MauserLabel;
import org.t4atf.route.MauserRelations;

public class DatabasePopulator
{
  private static GraphDatabaseService database;
  private static ExecutionEngine engine;

  public static void initializeWithFakeData(GraphDatabaseService graphDatabaseService)
  {
    database = graphDatabaseService;

    engine = new ExecutionEngine(database);

    try (Transaction tx = database.beginTx())
    {
      Node continent = database.createNode(CONTINENT);
      continent.setProperty("name", "Europa");
      createContinentalGeography(continent, "Italia", "Lombardia", "Milano", "MXP", "Malpensa");
      createContinentalGeography(continent, "Italia", "Lazio", "Roma", "CIA", "Ciampino");
      createContinentalGeography(continent, "England", "London", "London", "LTN", "Luton");
      createContinentalGeography(continent, "Espana", "Catalunya", "Barcelona", "BCN", "El Prat");
      Node rome = database.findNodesByLabelAndProperty(CITY, "name", "Roma").iterator().next();
      Node italy = database.findNodesByLabelAndProperty(COUNTRY, "name", "Italia").iterator().next();
      Node luton = database.findNodesByLabelAndProperty(AIRPORT, "name", "LTN").iterator().next();

      Node ryanair = createAirline("FR");
      createAirlineCompanyConnections(ryanair, rome, luton);

      Node easyjet = createAirline("U2");
      createAirlineCompanyConnections(easyjet, italy, luton);

      Node alitalia = createAirline("AZ");
      createAirlineCompanyConnections(alitalia, italy);

      tx.success();
    }
  }

  private static Node createAirline(String airlineCode)
  {
    Node airline = createNode(airlineCode, AIRLINE);
    return airline;
  }

  private static void createAirlineCompanyConnections(Node airline, Node... connections)
  {
    for (Node c : connections)
    {
      createAirlineConnection(airline, c);
    }
  }

  private static void createAirlineConnection(Node airline, Node connection)
  {
    airline.createRelationshipTo(connection, MauserRelations.FLY_IN);
  }

  private static void createContinentalGeography(Node continent, String countryName, String regionName,
      String cityName, String airportCode, String airportName)
  {
    Node country = createNode(countryName, COUNTRY);
    createCountryGeography(continent, country, regionName, cityName, airportCode, airportName);
  }

  private static Node createNode(String name, MauserLabel label)
  {
    String queryString = "MERGE (n:" + label + "{name: {name}}) RETURN n";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put( "name", name );
    ResourceIterator<Node> it = engine.execute( queryString, parameters ).columnAs( "n" );
    Node node = it.next();
    database.index().forNodes(label.name()).add(node, "name", name);
    return node;
  }

  private static void createCountryGeography(Node continent, Node country, String regionName, String cityName,
      String airportCode, String airportName)
  {
    Node region = createNode(regionName, REGION);
    createRegionGeography(continent, country, region, cityName, airportCode, airportName);
  }

  private static void createRegionGeography(Node continent, Node country, Node region, String cityName,
      String airportCode, String airportName)
  {
    Node city = createNode(cityName, CITY);
    createRegionGeography(continent, country, region, city, airportCode, airportName);
  }

  private static void createRegionGeography(Node continent, Node country, Node region, Node city, String airportCode,
      String airportName)
  {
    Node airport = createNode(airportCode, AIRPORT);
    airport.setProperty("longName", airportName);
    airport.createRelationshipTo(city, MauserRelations.CITY);
    city.createRelationshipTo(region, MauserRelations.REGION);
    region.createRelationshipTo(country, MauserRelations.COUNTRY);
    country.createRelationshipTo(continent, MauserRelations.CONTINENT);
  }

}
