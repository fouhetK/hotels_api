package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.AdminEntity;
import fr.m2i.hotels.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository ar;

    private void checkAdmin(AdminEntity admin) throws InvalidObjectException {
        if (admin.getUsername().length() <= 2)
            throw new InvalidObjectException("Username invalide");
    }

    public AdminEntity getById(int id){
        return ar.findById(id).get();
    }

    public Iterable<AdminEntity> getAll(){
        return ar.findAll();
    }

    public AdminEntity findByUsername(String username){
        return ar.findByUsername(username);
    }

    public void update(int id, AdminEntity admin) throws InvalidObjectException {
        checkAdmin(admin);
        AdminEntity toUpdate = this.getById(id);

        toUpdate.setUsername(admin.getUsername());
        toUpdate.setRole(admin.getRole());
        toUpdate.setPassword(toUpdate.getPassword());

        ar.save(toUpdate);
    }

    public void add(AdminEntity admin) throws InvalidObjectException {
        checkAdmin(admin);
        ar.save(admin);
    }

    public void delete(int id){
        AdminEntity admin = this.getById(id);
        ar.delete(admin);
    }
}
