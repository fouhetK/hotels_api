package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.ResaEntity;
import fr.m2i.hotels.services.ResaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/resa")
public class ResaAPI {

    @Autowired
    private ResaService rs;

    @GetMapping(value = "", produces = "application/json")
    public Iterable<ResaEntity> getAll(@RequestParam(name = "client", required = false, defaultValue = "0") int client) {
        return rs.getAll(client);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResaEntity> getById(@PathVariable("id") int id) {
        try{
            ResaEntity resa = rs.getById(id);
            return ResponseEntity.ok(resa);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Réservation introuvable" );
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<ResaEntity> add(@RequestBody ResaEntity resa){
        try{
            rs.add(resa);

            return ResponseEntity.ok(resa);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody ResaEntity resa){
        try{
            rs.update(id, resa);
        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Réservation introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            rs.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable");
        }
    }
}
