package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository  extends CrudRepository<ClientEntity, Integer> {
}
