package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.ClientEntity;
import fr.m2i.hotels.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/client")
public class ClientAPI {

    @Autowired
    private ClientService cs;

    @GetMapping(value = "", produces = "application/json")
    public Iterable<ClientEntity> getAll() {
        return cs.getAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClientEntity> getById(@PathVariable("id") int id) {
        try{
            ClientEntity client = cs.getById(id);
            return ResponseEntity.ok(client);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Client introuvable" );
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<ClientEntity> add(@RequestBody ClientEntity client){
        try{
            cs.add(client);

            return ResponseEntity.ok(client);
        } catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody ClientEntity client){
        try{
            cs.update(id, client);
        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Client introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            cs.delete(id);
        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Client introuvable" );

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Le client a des réservations en cours");
        }
    }
}
