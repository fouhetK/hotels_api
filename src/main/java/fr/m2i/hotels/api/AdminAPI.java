package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.AdminEntity;
import fr.m2i.hotels.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminAPI {

    @Autowired
    private AdminService as;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @GetMapping(value = "", produces = "application/json")
    public Iterable<AdminEntity> getAll(HttpServletRequest request) {
        String search = request.getParameter("search");
        return as.getAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AdminEntity> getById(@PathVariable("id") int id) {
        try{
            AdminEntity admin = as.getById(id);
            return ResponseEntity.ok(admin);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Admin introuvable" );
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<AdminEntity> add(@RequestBody AdminEntity admin){
        try{
            admin.setPassword(pwdEncoder.encode(admin.getPassword()));
            as.add(admin);

            return ResponseEntity.ok(admin);
        } catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody AdminEntity admin){
        try{
            as.update(id, admin);
        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Admin introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            as.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin introuvable");
        }
    }
}
