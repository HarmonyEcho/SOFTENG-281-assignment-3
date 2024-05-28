package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** This class is the main entry point. */
public class MapEngine {

  private HashMap<String, Country> countryMap =
      new HashMap<>(); // Key: name of country, Value: Country object

  private HashMap<Country, ArrayList<Country>> adjMap =
      new HashMap<>(); // Key: Country object, Value: Arraylist of connected countries

  /** Constructor method. */
  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /**
   * invoked one time only when constracting the MapEngine class.
   *
   * <p>DO NOT INVOKE MORE THAN ONCE - will break.
   *
   * <p>DOES NOT perform any error handling of the csv files - if the csv files are broken, this
   * will break.
   */
  private void loadMap() {
    List<String> countryList = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // loop through each element of countryList
    for (int i = 0; i < countryList.size(); i++) {
      String[] details = countryList.get(i).split(",", 3); // country, continent,  taxes

      countryMap.put(details[0], new Country(details[0], details[1], Integer.parseInt(details[2])));
    }

    // loop through element of adjacencies
    for (int i = 0; i < adjacencies.size(); i++) {
      String[] adj = adjacencies.get(i).split(",", 0);

      // loop through all the adjacent countries, adding them to the ArrayList
      ArrayList<Country> adjCountries = new ArrayList<>();
      for (int j = 1; j < adj.length; j++) {
        adjCountries.add(countryMap.get(adj[j]));
      }
      adjMap.put(countryMap.get(adj[0]), adjCountries); // should not need putIfAbsent
    }
  }

  /**
   * Returns the Country object corresponding to the name String given. This method will capitalise
   * the first letter of each word. If no country matching the given name is found, an
   * InvalidCountryException will be thrown and null will be returned.
   *
   * @param countryName the name of the country to find.
   * @return the corresponding Country object, if any is found.
   * @throws InvalidCountryException thrown when no country matching the name given is found.
   */
  private Country getCountryByName(String countryName) throws InvalidCountryException {

    // set every letter to lowercase, then capitalise the first letter of each word
    countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
    Country country = countryMap.get(countryName);

    // if no matching country is found, throw an exception
    if (country == null) {
      throw new InvalidCountryException(countryName);
    }
    return country;
  }

  /**
   * Prompts the user to enter the name of a country using the selected message, and will warn the
   * user until a valid country name is input.
   *
   * @param message a value of MessageCli to use to prompt the user. Only messages that do not
   *     require arguments can be used.
   * @return a Country object corresponding to the user input.
   */
  private Country inputCountry(MessageCli message) {

    // prompt the user to input a country's name
    message.printMessage();

    // repeat until the user inputs a valid country name
    Country country = null;
    while (country == null) {
      String countryName = Utils.scanner.nextLine();

      // get the country object, or if country name is invalid print an error message
      try {
        country = this.getCountryByName(countryName);
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
      }
    }
    return country;
  }

  /**
   * Finds the shortest route between two countries, by number of countries, and returns an array
   * containing the list of countries on the route in order. Uses breadth-first search.
   *
   * @param sourceCountry Country to start the route from.
   * @param destinationCountry Country to finish in.
   * @return Arraylist of the countries in the shortest route between the source and end countries,
   *     in order, including the source and end countries. If no valid route is found, returns null.
   */
  private ArrayList<Country> findRoute(Country sourceCountry, Country destinationCountry) {
    HashMap<Country, Country> paths =
        new HashMap<>(); // value is the country where the key was traversed from.
    List<Country> visited = new ArrayList<>(); // list of countries that have already been 'visited'
    Queue<Country> queue = new LinkedList<>(); // queue of countries to visit

    // add the source country to the queue of countries to visit and the list of countries visited
    queue.add(sourceCountry);
    visited.add(sourceCountry);
    while (!queue.isEmpty()) {

      // pop the front country in the queue
      Country country = queue.poll();

      // loop through each country adjacent to the popped country
      for (int i = 0; i < adjMap.get(country).size(); i++) {
        Country visiting = adjMap.get(country).get(i);

        // if the destination country has been reached
        if (visiting.equals(destinationCountry)) {
          // initialise arraylist of countries on the route
          ArrayList<Country> route = new ArrayList<>();
          paths.put(visiting, country);
          country = destinationCountry;

          // loop back through the path until the source country is reached.
          while (country != sourceCountry) {
            route.add(country); // add country to the route
            country = paths.get(country); // set country to the next country in the path
          }
          if (!sourceCountry.equals(destinationCountry)) {
            route.add(sourceCountry);
          }

          // flip the order of elements from (destination -> source) to (source -> destination)
          java.util.Collections.reverse(route);
          return route;

          // else if the country visiting has not already been visited
        } else if (!visited.contains(visiting)) {
          paths.put(visiting, country);
          visited.add(visiting);
          queue.add(visiting);
        }
      }
    }
    return null; // no valid route found
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    Country country = inputCountry(MessageCli.INSERT_COUNTRY);
    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), Integer.toString(country.getTax()));
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    // get the starting country
    Country sourceCountry = inputCountry(MessageCli.INSERT_SOURCE);

    // get the ending country
    Country destinationCountry = inputCountry(MessageCli.INSERT_DESTINATION);

    // if the source and destination countries are the same, print a message and return
    if (sourceCountry.equals(destinationCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    ArrayList<Country> route = findRoute(sourceCountry, destinationCountry);
    ArrayList<String> countryNameList = new ArrayList<>();
    ArrayList<String> continentNameList = new ArrayList<>();
    int totalTaxes = 0;

    // loop through every country in the route
    for (int i = 0; i < route.size(); i++) {
      Country country = route.get(i);

      // add the country name to the list
      countryNameList.add(country.getName());

      // if the continent is not already on the list, add it to the list
      String continent = country.getContinent();
      if (!continentNameList.contains(continent)) {
        continentNameList.add(continent);
      }

      // add tax to the total, except for the source country
      if (i != 0) {
        totalTaxes += country.getTax();
      }
    }

    MessageCli.ROUTE_INFO.printMessage(countryNameList.toString());
    MessageCli.CONTINENT_INFO.printMessage(continentNameList.toString());
    MessageCli.TAX_INFO.printMessage(Integer.toString(totalTaxes));
  }
}
