package br.com.restMovieRaspberryAwardsApi.model;

import java.io.Serializable;

import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Component
@Table(name = "filme")
public class Movie implements Serializable {

  private static final long serialVersionUID = 6020617195244815009L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "year", nullable = false)
  private String year;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "recordCompany", nullable = false)
  private String recordCompany;

  @Column(name = "producer", nullable = false)
  private String producer;

  @Column(name = "champion", nullable = true)
  private Boolean champion;

  public Movie() {

  }

  public Movie(Long id, String year, String title, String recordCompany, String producer, Boolean champion) {
    super();
    this.id = id;
    this.year = year;
    this.title = title;
    this.recordCompany = recordCompany;
    this.producer = producer;
    this.champion = champion;
  }

  public Movie(MovieDTO movieDTO) {
    if (!"".equals(movieDTO.getYear())) {
      this.year = movieDTO.getYear();
    }
    if (!"".equals(movieDTO.getTitle())) {
      this.title = movieDTO.getTitle();
    }
    if (!"".equals(movieDTO.getRecordCompany())) {
      this.recordCompany = movieDTO.getRecordCompany();
    }
    if (!"".equals(movieDTO.getProducer())) {
      this.producer = movieDTO.getProducer();
    }
    this.champion = !"".equals(movieDTO.getChampion());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Boolean getChampion() {
    return champion;
  }

  public void setChampion(Boolean champion) {
    this.champion = champion;
  }

  public String toString() {
    return "Movie{year='" + year + "', title=" + title + ", recordCompany='" + recordCompany + ", producer='" + producer
        + ", champion='" + champion + "'''}";
  }

}
