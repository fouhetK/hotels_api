package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.AdminEntity;
import fr.m2i.hotels.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class LoginApi {

    @Autowired
    private AdminService as;

    @Autowired
    private PasswordEncoder encoderPwd;

    @PostMapping( value = "/api/login" ,  consumes = "application/json" ,  produces = "application/json")
    public ResponseEntity<AdminEntity> get(@RequestBody AdminEntity a ) {

            AdminEntity admin = as.findByUsername(a.getUsername());

            if (admin == null)
                return ResponseEntity.notFound().build();
            else{
                if (encoderPwd.matches(a.getPassword(), admin.getPassword())){
                    String authPwd = Base64.getEncoder().encodeToString((a.getUsername()+":"+a.getPassword()).getBytes());
                    admin.setPassword(authPwd);

                    return ResponseEntity.ok(admin);
                }
                return ResponseEntity.badRequest().build();
            }
    }
}
