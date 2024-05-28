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

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {

    // repeat until the user inputs a valid country
    Country country = null;
    while (country == null) {

      // prompts and recieves user input
      MessageCli.INSERT_COUNTRY.printMessage();
      String countryName = Utils.scanner.nextLine();

      // print the country's info, or if country is invalid print an error message
      try {
        country = this.getCountryByName(countryName);
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), Integer.toString(country.getTax()));
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    // get the starting country
    Country originCountry = null;
    while (originCountry == null) {
      MessageCli.INSERT_SOURCE.printMessage();
      String countryName = Utils.scanner.nextLine();
      try {
        originCountry = this.getCountryByName(countryName);
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
      }
    }

    // get the ending country
    Country destinationCountry = null;
    while (destinationCountry == null) {
      MessageCli.INSERT_SOURCE.printMessage();
      String countryName = Utils.scanner.nextLine();
      try {
        destinationCountry = this.getCountryByName(countryName);
      } catch (InvalidCountryException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
