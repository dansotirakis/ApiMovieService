package br.com.restMovieRaspberryAwardsApi.dto;

import java.io.Serializable;

public class IntervalAwardsDTO implements Serializable, Comparable<IntervalAwardsDTO> {

  private static final long serialVersionUID = -8195197903314034973L;

  private String producer;
  private Integer interval;
  private String previousWin;
  private String followingWin;

  public IntervalAwardsDTO() {
    super();
  }

  public static int compareIntervalos(IntervalAwardsDTO a, IntervalAwardsDTO b) {
    return a.interval.compareTo(b.interval);
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public Integer getInterval() {
    return interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public String getPreviousWin() {
    return previousWin;
  }

  public void setPreviousWin(String previousWin) {
    this.previousWin = previousWin;
  }

  public String getFollowingWin() {
    return followingWin;
  }

  public void setFollowingWin(String followingWin) {
    this.followingWin = followingWin;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((producer == null) ? 0 : producer.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    IntervalAwardsDTO other = (IntervalAwardsDTO) obj;
    if (producer == null) {
      return other.producer == null;
    } else {
      return producer.equals(other.producer);
    }
  }

  @Override
  public String toString() {
    return "producer : " + producer + "\t" + "interval : " + interval + "\t" + "previousWin : " + previousWin + "\t"
        + "followingWin : " + followingWin;
  }

  @Override
  public int compareTo(IntervalAwardsDTO o) {
    return getInterval().compareTo(o.getInterval());
  }

}
