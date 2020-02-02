package br.com.restMovieRaspberryAwardsApi.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.restMovieRaspberryAwardsApi.dto.IntervalAwardsDTO;
import br.com.restMovieRaspberryAwardsApi.dto.IntervalDTO;
import br.com.restMovieRaspberryAwardsApi.dto.MovieDTO;
import br.com.restMovieRaspberryAwardsApi.model.Movie;
import br.com.restMovieRaspberryAwardsApi.repositories.interfaces.MovieRepository;
import br.com.restMovieRaspberryAwardsApi.service.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService implements IMovieService {

  private final URL csvFile = this.getClass().getResource("/movielist.csv");

  @Autowired
  private MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    super();
    this.movieRepository = movieRepository;
  }

  @Override
  public void insertRecords() {
    List<MovieDTO> movies = uploadCsvFile();
    this.save(movies);
  }

  @Override
  public List<MovieDTO> listMovies() {
    List<MovieDTO> movieDTO = new ArrayList<>();
    try {
      List<Movie> movies = this.findByMovies();
      for (Movie movie : movies) {
        movieDTO.add(new MovieDTO(movie));
      }
      return movieDTO;
    } catch (Exception e) {
      return movieDTO;
    }
  }

  private List<Movie> findByMovies() {
    List<Movie> movies = movieRepository.findAll();
    if (movies.size() == 0) {
      return new ArrayList<>();
    }
    return movies;
  }

  @Override
  public void save(List<MovieDTO> filmesDTO) {
    if (filmesDTO.isEmpty()) {
      return;
    }
    List<Movie> movies = new ArrayList<>();
    for (MovieDTO movieDTO : filmesDTO) {
      movies.add(new Movie(movieDTO));
    }
    movieRepository.saveAll(movies);
  }

  @Override
  public IntervalDTO findByIntervalAwards() {
    IntervalDTO intervalDTO = new IntervalDTO();
    intervalDTO.setMin(this.searchProducerTwoPrizesFaster());
    intervalDTO.setMax(this.searchLongestRangeProducerAwards());
    return intervalDTO;
  }

  private IntervalAwardsDTO searchLongestRangeProducerAwards() {
    List<IntervalAwardsDTO> intervalsDTO = new ArrayList<>();
    try {
      List<Movie> movies = this.findByMovies();
      if (movies.size() == 0) {
        return new IntervalAwardsDTO();
      }
      for (Movie movie : movies) {
        if (!movie.getChampion()) {
          continue;
        }
        IntervalAwardsDTO intervalo = new IntervalAwardsDTO();
        for (String produtor : this.getProducers(movie)) {
          intervalo.setProducer(produtor);
          if (intervalsDTO.contains(intervalo)) {
            int index = intervalsDTO.indexOf(intervalo);
            intervalsDTO.get(index).setFollowingWin(movie.getYear());
            intervalsDTO.get(index).setInterval(this.calculateInterval(intervalsDTO.get(index)));
          } else {
            IntervalAwardsDTO novoIntervalo = new IntervalAwardsDTO();
            novoIntervalo.setPreviousWin(movie.getYear());
            novoIntervalo.setFollowingWin(movie.getYear());
            novoIntervalo.setInterval(0);
            novoIntervalo.setProducer(produtor);
            intervalsDTO.add(novoIntervalo);
          }
        }
      }
      Collections.sort(intervalsDTO);
      return intervalsDTO.get(intervalsDTO.size() - 1);
    } catch (Exception e) {
      return new IntervalAwardsDTO();
    }
  }

  private IntervalAwardsDTO searchProducerTwoPrizesFaster() {
    List<IntervalAwardsDTO> intervalsDTO = new ArrayList<>();
    try {
      List<Movie> movies = this.findByMovies();
      if (movies.size() == 0) {
        return new IntervalAwardsDTO();
      }
      for (Movie movie : movies) {
        if (!movie.getChampion()) {
          continue;
        }
        IntervalAwardsDTO intervalo = new IntervalAwardsDTO();
        for (String produtor : this.getProducers(movie)) {
          intervalo.setProducer(produtor);
          int index = intervalsDTO.indexOf(intervalo);
          if (intervalsDTO.contains(intervalo)) {
            if (!intervalsDTO.get(index).getFollowingWin().equals(intervalsDTO.get(index).getPreviousWin())) {
              continue;
            }
            intervalsDTO.get(index).setFollowingWin(movie.getYear());
            intervalsDTO.get(index).setInterval(this.calculateInterval(intervalsDTO.get(index)));
          } else {
            IntervalAwardsDTO novoIntervalo = new IntervalAwardsDTO();
            novoIntervalo.setPreviousWin(movie.getYear());
            novoIntervalo.setFollowingWin(movie.getYear());
            novoIntervalo.setInterval(0);
            novoIntervalo.setProducer(produtor);
            intervalsDTO.add(novoIntervalo);
          }
        }
      }
      Collections.sort(intervalsDTO);
      for (IntervalAwardsDTO intervals : intervalsDTO) {
        if (intervals.getInterval() != 0) {
          return intervals;
        }
      }
      return intervalsDTO.get(0);
    } catch (Exception e) {
      return new IntervalAwardsDTO();
    }
  }

  @Override
  public void clearTable() {
    movieRepository.clearTable();
  }

  private List<MovieDTO> uploadCsvFile() {
    List<MovieDTO> movies = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile.getPath())));
      String linha;
      int numberLine = 1;
      while ((linha = reader.readLine()) != null) {
        if (numberLine == 1) {
          numberLine++;
          continue;
        }
        final String csvResource = ";";
        String[] movieFields = linha.split(csvResource);
        movies.add(new MovieDTO(movieFields[0], movieFields[1], movieFields[2], movieFields[3],
            movieFields.length == 5 ? "yes" : ""));
      }
      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return movies;
  }

  private List<String> getProducers(Movie movie) {
    List<String> names = new ArrayList<>();
    if (!movie.getProducer().toLowerCase().contains(" and ".toLowerCase())) {
      names.add(movie.getProducer());
      return names;
    }
    String[] producersSepAnd = movie.getProducer().split(" and ");
    names.add(producersSepAnd[1]);
    if (!producersSepAnd[0].toLowerCase().contains(", ".toLowerCase())) {
      names.add(producersSepAnd[0]);
      return names;
    }
    String[] produtocersSepVirgula = producersSepAnd[0].split(", ");
    names.addAll(Arrays.asList(produtocersSepVirgula));
    return names;
  }

  private int calculateInterval(IntervalAwardsDTO intervalAwardsDTO) {
    return Integer.parseInt(intervalAwardsDTO.getFollowingWin()) - Integer
        .parseInt(intervalAwardsDTO.getPreviousWin());
  }

}
