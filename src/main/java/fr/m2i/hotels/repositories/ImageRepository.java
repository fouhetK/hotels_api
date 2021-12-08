package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.ImageEntity;
import fr.m2i.hotels.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ImageRepository extends CrudRepository<ImageEntity, Integer> {

    Iterable<ImageEntity> findAllByHotel_Id(int id);
}
