package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.HotelEntity;
import fr.m2i.hotels.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hotel")
public class HotelAPI {

    @Autowired
    private HotelService hs;

    @GetMapping(value = "", produces = "application/json")
    public Iterable<HotelEntity> getAll(HttpServletRequest request) {
        String search = request.getParameter("search");
        return hs.getAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<HotelEntity> getById(@PathVariable("id") int id) {
        try{
            HotelEntity hotel = hs.getById(id);
            return ResponseEntity.ok(hotel);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Patient introuvable" );
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<HotelEntity> add(@RequestBody HotelEntity hotel){
        try{
            hs.add(hotel);

            return ResponseEntity.ok(hotel);
        } catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody HotelEntity hotel){
        try{
            hs.update(id, hotel);
        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Admin introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            hs.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin introuvable");
        }
    }
}
