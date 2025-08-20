package com.cfs.MovieCatalog.controller;


import com.cfs.MovieCatalog.enity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
//MovieCatalog API
//Requirements:
//        -----------------------------------------------------
//GET /movies – list all movies<<Done>>
//POST /movies – add a movie <<Done>>
//GET /movies/{id} – fetch movie details
//PUT /movies/{id} – edit movie details
//DELETE /movies/{id} – delete a movie
//📦 Fields:
//id, title, genre, releaseYear, rating
@RestController
@RequestMapping("/movies")
public class MovieCatalogController
{
    Map<Long, Movie> movieDb =  new HashMap<Long, Movie>();


 @GetMapping
  public ResponseEntity<List<Movie>> getAllMovie()
 {
     return ResponseEntity.ok( new ArrayList<>(movieDb.values()));
 }
 @PostMapping("/{id}")
 public ResponseEntity<Movie> createMovie(@PathVariable long id,@RequestBody Movie movie )
 {
     movieDb.put(id,movie);
     return ResponseEntity.status(HttpStatus.CREATED).body(movie);
 }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getByMovieId(@PathVariable long id)
    {
        Movie movie = movieDb.get(id);
        if(movie==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieDtl(@PathVariable long id,@RequestBody Movie movie )
    {
        Movie movdtl = movieDb.get(id);
        if(movdtl == null)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        movdtl.setTitle(movie.getTitle());
        movdtl.setGenre(movie.getGenre());
        movdtl.setRating(movie.getRating());
        movdtl.setReleaseYear(movie.getReleaseYear());
        movieDb.put(id,movdtl);
        return ResponseEntity.status(HttpStatus.CREATED).body(movdtl);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<List<Movie>> deleteMoviebyID(@PathVariable long id)
    {
        movieDb.remove(id);
        return ResponseEntity.ok( new ArrayList<>( movieDb.values()));
    }


}
