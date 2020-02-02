package br.com.restMovieRaspberryAwardsApi.dto;

import java.io.Serializable;

public class IntervalDTO implements Serializable {

  private static final long serialVersionUID = -7137962307504317088L;
  private IntervalAwardsDTO min;
  private IntervalAwardsDTO max;

  public IntervalDTO() {
    super();
  }

  public IntervalDTO(IntervalAwardsDTO min, IntervalAwardsDTO max) {
    super();
    this.min = min;
    this.max = max;
  }

  public IntervalAwardsDTO getMin() {
    return min;
  }

  public void setMin(IntervalAwardsDTO min) {
    this.min = min;
  }

  public IntervalAwardsDTO getMax() {
    return max;
  }

  public void setMax(IntervalAwardsDTO max) {
    this.max = max;
  }

}
