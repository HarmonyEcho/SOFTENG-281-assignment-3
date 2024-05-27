package nz.ac.auckland.se281;

public class Country {

  private String name; // name of the country
  private String continent; // name of the continent containing the country
  private int tax; // cost to travel into the country

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
}
