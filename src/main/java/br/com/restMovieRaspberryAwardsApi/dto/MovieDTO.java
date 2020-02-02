package br.com.restMovieRaspberryAwardsApi.dto;

import java.io.Serializable;

import br.com.restMovieRaspberryAwardsApi.enumeration.ChampionType;
import br.com.restMovieRaspberryAwardsApi.model.Movie;

public class MovieDTO implements Serializable {

  private static final long serialVersionUID = -3879461964268667743L;

  private String id;
  private String year;
  private String title;
  private String recordCompany;
  private String producer;
  private String champion;

  public MovieDTO(String year, String title, String recordCompany, String producer, String champion) {
    super();
    this.year = year;
    this.title = title;
    this.recordCompany = recordCompany;
    this.producer = (producer.replace('\"', ' ')).trim();
    this.champion = champion;
  }

  public MovieDTO(Movie movie) {
    super();
    if (null != movie.getId()) {
      this.id = movie.getId().toString();
    }
    if (!movie.getYear().equals("")) {
      this.year = movie.getYear();
    }
    if (!movie.getTitle().equals("")) {
      this.title = movie.getTitle();
    }
    if (!movie.getRecordCompany().equals("")) {
      this.recordCompany = movie.getRecordCompany();
    }
    if (!movie.getProducer().equals("")) {
      this.producer = movie.getProducer();
    }
    if (movie.getChampion() != null) {
      this.champion = ChampionType.YES.getDescription();
    } else {
      this.champion = ChampionType.NO.getDescription();
    }
  }

  public String toString() {
    return "Movie{year='" + year + "', title=" + title + ", recordCompany='" + recordCompany + ", producer='" + producer
        + ", champion='" + champion + "'''}";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getRecordCompany() {
    return recordCompany;
  }

  public void setRecordCompany(String recordCompany) {
    this.recordCompany = recordCompany;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public String getChampion() {
    return champion;
  }

  public void setChampion(String champion) {
    this.champion = champion;
  }

}
