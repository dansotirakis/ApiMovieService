package br.com.restMovieRaspberryAwardsApi.repositories.interfaces;

import br.com.restMovieRaspberryAwardsApi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Modifying
  @Query(value = "TRUNCATE TABLE filme", nativeQuery = true)
  void clearTable();

}
