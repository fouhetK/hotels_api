package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.HotelEntity;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository  extends CrudRepository<HotelEntity, Integer> {
}
