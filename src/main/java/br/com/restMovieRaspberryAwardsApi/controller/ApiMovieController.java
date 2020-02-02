package br.com.restMovieRaspberryAwardsApi.controller;

import java.util.List;

import br.com.restMovieRaspberryAwardsApi.dto.IntervalDTO;
import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;
import br.com.restMovieRaspberryAwardsApi.model.Movie;
import br.com.restMovieRaspberryAwardsApi.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "RestMovieRaspberryAwardsApi")
public class ApiMovieController {

  @Autowired
  private MovieService movieService;

  public ApiMovieController(MovieService movieService) {
    this.movieService = movieService;
    this.movieService.insertRecords();
  }

  @ApiOperation(value = "Request list films")
  @RequestMapping(value = "/movies", method = RequestMethod.GET)
  public ResponseEntity<List<MovieDTO>> list() {
    List<MovieDTO> filmes = movieService.listar();
    return ResponseEntity.status(HttpStatus.OK).body(filmes);
  }

  @ApiOperation(value = "Request film selected champions")
  @RequestMapping(value = "/interval-awards", method = RequestMethod.GET)
  public ResponseEntity<IntervalDTO> intervalAwards() {
    IntervalDTO intervaloPremiosDTO = movieService.findByIntervalAwards();
    return ResponseEntity.status(HttpStatus.OK).body(intervaloPremiosDTO);
  }

}
