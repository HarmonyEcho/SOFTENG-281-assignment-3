package nz.ac.auckland.se281;

public class Country {

  private String name; // name of the country
  private String continent; // name of the continent containing the country
  private int tax; // tax to travel into the country

  /**
   * Constructor method
   *
   * @param name name of the country
   * @param continent name of the continent containing the country
   * @param tax cost to travel into the country
   */
  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  /** Getter method. Returns the name of the country as a string. */
  public String getName() {
    return this.name;
  }

  /** Getter method. Returns the name of the country's continent as a string. */
  public String getContinent() {
    return this.continent;
  }

  /** Getter method. Returns the name of the country's cross-border tax as a string. */
  public int getTax() {
    return this.tax;
  }
}
