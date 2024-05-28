package nz.ac.auckland.se281;

public class Country {

  private String name; // name of the country
  private String continent; // name of the continent containing the country
  private int tax; // tax to travel into the country

  /**
   * Constructor method.
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

  /** Taken from https://softeng281.digitaledu.ac.nz/topics/data-structures/. */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * Taken from https://softeng281.digitaledu.ac.nz/topics/data-structures/.
   *
   * @param obj an object to compare this Country to
   * @return whether obj is equal to this Country
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
