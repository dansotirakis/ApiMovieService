package br.com.restMovieRaspberryAwardsApi.service.interfaces;

import java.util.List;

import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;
import br.com.restMovieRaspberryAwardsApi.dto.IntervalDTO;

public interface IFilmeService {

  List<MovieDTO> listar();

  void save(List<MovieDTO> filmes);

  IntervalDTO findByIntervalAwards();

  void insertRecords();

  void clearTable();

}
