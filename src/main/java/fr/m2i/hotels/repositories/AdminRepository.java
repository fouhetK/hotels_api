package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.AdminEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

    public AdminEntity findByUsername(String name);

}
