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

  private List<Movie> findByMovies() {
    List<Movie> movies = movieRepository.findAll();
    if (movies.size() == 0) {
      return new ArrayList<>();
    }
    return movies;
  }

  @Override
  public List<MovieDTO> listMovie() {
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
          int index = intervalosDTO.indexOf(intervalo);
          if (intervalosDTO.contains(intervalo)) {
            if (!intervalosDTO.get(index).getFollowingWin().equals(intervalosDTO.get(index).getPreviousWin())) {
              continue;
            }
            intervalosDTO.get(index).setFollowingWin(movie.getYear());
            intervalosDTO.get(index).setInterval(this.calculateInterval(intervalosDTO.get(index)));
          } else {
            IntervalAwardsDTO novoIntervalo = new IntervalAwardsDTO();
            novoIntervalo.setPreviousWin(movie.getYear());
            novoIntervalo.setFollowingWin(movie.getYear());
            novoIntervalo.setInterval(0);
            novoIntervalo.setProducer(produtor);
            intervalosDTO.add(novoIntervalo);
          }
        }
      }
      Collections.sort(intervalosDTO);
      for (IntervalAwardsDTO intervalo : intervalosDTO) {
        if (intervalo.getInterval() != 0) {
          return intervalo;
        }
      }
      return intervalosDTO.get(0);
    } catch (Exception e) {
      return new IntervalAwardsDTO();
    }
  }

  @Override
  public void clearTable() {
    movieRepository.clearTable();
  }

  private List<MovieDTO> uploadCsvFile() {
    List<MovieDTO> filmes = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile.getPath())));
      String linha = null;
      int numeroLinha = 1;
      while ((linha = reader.readLine()) != null) {
        if (numeroLinha == 1) {
          numeroLinha++;
          continue;
        }
        String[] filmeCampos = linha.split(csvResource);
        filmes.add(new MovieDTO(filmeCampos[0], filmeCampos[1], filmeCampos[2], filmeCampos[3],
            filmeCampos.length == 5 ? "yes" : ""));
      }
      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return filmes;
  }

  private List<String> getProducers(Movie movie) {
    List<String> nomes = new ArrayList<>();
    if (!movie.getProducer().toLowerCase().contains(" and ".toLowerCase())) {
      nomes.add(movie.getProducer());
      return nomes;
    }
    String[] produtoresSepAnd = movie.getProducer().split(" and ");
    nomes.add(produtoresSepAnd[1]);
    if (!produtoresSepAnd[0].toLowerCase().contains(", ".toLowerCase())) {
      nomes.add(produtoresSepAnd[0]);
      return nomes;
    }
    String[] produtoresSepVirgula = produtoresSepAnd[0].split(", ");
    for (String nome : produtoresSepVirgula) {
      nomes.add(nome);
    }
    return nomes;
  }

  private int calculateInterval(IntervalAwardsDTO intervalAwardsDTO) {
    return Integer.parseInt(intervalAwardsDTO.getFollowingWin()) - Integer
        .parseInt(intervalAwardsDTO.getPreviousWin());
  }

}
