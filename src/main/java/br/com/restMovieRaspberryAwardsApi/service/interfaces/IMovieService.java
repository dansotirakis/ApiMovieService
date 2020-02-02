package br.com.restMovieRaspberryAwardsApi.service.interfaces;

import java.util.List;

import br.com.restMovieRaspberryAwardsApi.dto.IntervalDTO;
import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;

public interface IMovieService {

  List<MovieDTO> listMovies();

  void save(List<MovieDTO> filmes);

  IntervalDTO findByIntervalAwards();

  void insertRecords();

  void clearTable();

}
