package br.com.restMovieRaspberryAwardsApi.enumeration;

public enum ChampionType {

  YES("yes"), NO("");

  private String description;

  ChampionType(String desc) {
    this.description = desc;
  }

  public String getDescription() {
    return this.description;
  }

}
