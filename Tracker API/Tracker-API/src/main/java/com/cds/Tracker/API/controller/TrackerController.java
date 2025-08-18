package com.cds.Tracker.API.controller;


import com.cds.Tracker.API.entity.Tracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TrackerController
{
   private Map<Long , Tracker> mref = new HashMap<>();

   @PostMapping
   public ResponseEntity<List<Tracker>> createNewTracker(@RequestBody List<Tracker> track)
   {
      for(Tracker cnt : track )
      {
         mref.put(cnt.getId(), cnt);
      }
       return ResponseEntity.status(HttpStatus.CREATED).body(track);
   }

   @GetMapping
   public ResponseEntity<List<Tracker>> getAllTracker()
   {
      return ResponseEntity.ok(new ArrayList<>( mref.values()));
   }
   @PatchMapping("/{id}/status")
   public ResponseEntity<Tracker> updateStatus(@PathVariable long id,@RequestBody String status )
   {
     Tracker traker  = mref.get(id);
     if(traker == null)
     {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
     }
     traker.setStatus(status);
     mref.put(id,traker);
     return ResponseEntity.ok(traker);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Tracker> deleteById(@PathVariable Long id)
   {
      Tracker tk = mref.remove(id);
      if(tk==null)
      {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      return ResponseEntity.noContent().build();
   }


}
