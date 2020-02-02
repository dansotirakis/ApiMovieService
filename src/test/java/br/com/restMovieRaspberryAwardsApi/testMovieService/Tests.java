
package br.com.restMovieRaspberryAwardsApi.testMovieService;
import java.util.ArrayList;
import java.util.List;

import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;
import br.com.restMovieRaspberryAwardsApi.service.MovieService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;

import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class Tests {

  @LocalServerPort
  private int port = 8080;
  private String uri;

  @PostConstruct
  @Before
  public void init() {
    final MovieService movieService = mock(MovieService.class);
    movieService.clearTable();
    List<MovieDTO> movieDTO = new ArrayList<>();
    movieDTO.add(new MovieDTO("2014", "TestTitle1", "TestRecord1", "TestProducer1", "yes"));
    movieDTO.add(new MovieDTO("2015", "TestTitle2", "TestRecord1", "TestProducer2", ""));
    movieDTO.add(new MovieDTO("2016", "TestTitle3", "TestRecord2", "TestProducer3", ""));
    movieDTO.add(new MovieDTO("2017", "TestTitle4", "TestRecord1", "TestProducer1", "yes"));
    movieDTO.add(new MovieDTO("2018", "TestTitle5", "TestRecord2", "TestProducer4", "yes"));
    movieDTO.add(new MovieDTO("2019", "TestTitle6", "TestRecord3", "TestProducer5", "yes"));
    movieService.save(movieDTO);
    uri = "http://localhost:" + port;
  }

  @Test
  public void listMovies() {
    RestAssured.
        given().
        when().
        get(uri + "/movies").
        then().
        assertThat().
        statusCode(200);
  }

  @Test
  public void IntervalAwords() {
    RestAssured.
        given().
        when().
        get(uri + "/interval-awards").
        then().
        assertThat().
        statusCode(200);
  }

  @Test
  public void higherPremiumReturnValue() {
    RestAssured.
        given().
        expect().
        statusCode(200).
        body("max.producer", Matchers.equalTo("Matthew Vaughn")).
        body("max.interval", Matchers.equalTo(13)).
        body("max.previousWin", Matchers.equalTo("2002")).
        body("max.followingWin", Matchers.equalTo("2015")).
        when().
        get(uri + "/interval-awards");
  }

  @Test
  public void twoPrizesFastestReturn() {
    RestAssured.
        given().
        expect().
        statusCode(200).
        body("min.producer", Matchers.equalTo("Joel Silver")).
        body("min.interval", Matchers.equalTo(1)).
        body("min.previousWin", Matchers.equalTo("1990")).
        body("min.followingWin", Matchers.equalTo("1991")).
        when().
        get(uri + "/interval-awards");
  }

}
