package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private HashMap<String, Country> countryMap =
      new HashMap<>(); // Key: name of country, Value: Country object

  private HashMap<Country, ArrayList<Country>> adjMap =
      new HashMap<>(); // Key: Country object, Value: Arraylist of connected countries

  /** Constructor method */
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
      String[] details = countryList.get(i).split(",", 3); // continent, country,  taxes

      countryMap.put(details[1], new Country(details[1], details[0], Integer.parseInt(details[2])));
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

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
