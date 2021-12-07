package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ResaRepository  extends CrudRepository<ResaEntity, Integer> {

    public Iterable<ResaEntity> findAllByClient_Id(int id);

    public Iterable<ResaEntity> findAllByNumChambreAndHotel_IdAndDatedebBetween(int numChambre, int hotel, Date datedeb, Date datefin);
    public Iterable<ResaEntity> findAllByNumChambreAndHotel_IdAndDatefinBetween(int numChambre, int hotel, Date datedeb, Date datefin);

    public Iterable<ResaEntity> findAllByClient_IdAndDatedebBetween(int id, Date datedeb, Date datefin);
    public Iterable<ResaEntity> findAllByClient_IdAndDatefinBetween(int id, Date datedeb, Date datefin);
}
