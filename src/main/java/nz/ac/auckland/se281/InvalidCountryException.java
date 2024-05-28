package nz.ac.auckland.se281;

public class InvalidCountryException extends Exception {

  private String countryName; // name of the invalid country

  /**
   * Constructor method.
   *
   * @param countryName a string containing the name of the country
   */
  public InvalidCountryException(String countryName) {
    super(MessageCli.INVALID_COUNTRY.getMessage(countryName));
    this.countryName = countryName;
  }

  /** Getter method. Returns the name of the country. */
  public String getCountryName() {
    return this.countryName;
  }
}
