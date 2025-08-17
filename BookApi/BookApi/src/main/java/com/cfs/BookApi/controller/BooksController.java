package com.cfs.BookApi.controller;

import com.cfs.BookApi.entity.Books;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Books")
public class BooksController
{
    private Map<Long, Books> booksetDB =  new HashMap<>();


    @GetMapping
    public ResponseEntity<List<Books>>  fetchAllBook()
    {
        return ResponseEntity.ok(new ArrayList<>(booksetDB.values())) ;
    }

    @PostMapping
    public ResponseEntity<List<Books>> getBooks(@RequestBody List<Books> book)
    {
        for(Books cnt : book)
        {
            booksetDB.put(cnt.getId(), cnt);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> fetchByid(@PathVariable Long id )
    {
      Books books   = booksetDB.get(id);
      if(books==null)
      {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateByid(@PathVariable Long id,@RequestBody Books book )
    {
        Books books = booksetDB.get(id);
        if(books==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        booksetDB.put(id,book);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Books> updatePrice(@PathVariable Long id ,@RequestBody Double newPrice)
    {
        Books book = booksetDB.get(id);
        if(book== null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        book.setPrice(newPrice);
        booksetDB.put(id,book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Books> deleteByid(@PathVariable Long id)
    {
         Books book = booksetDB.remove(id);
         if(book==null)
         {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
