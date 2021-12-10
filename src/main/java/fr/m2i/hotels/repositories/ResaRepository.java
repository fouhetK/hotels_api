package fr.m2i.hotels.repositories;

import fr.m2i.hotels.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ResaRepository  extends CrudRepository<ResaEntity, Integer> {

    public Iterable<ResaEntity> findAllByClient_Id(int id);

    public Iterable<ResaEntity> findAllByIdNotAndNumChambreAndHotel_IdAndDatedebBetween(int id, int numChambre, int hotel, Date datedeb, Date datefin);
    public Iterable<ResaEntity> findAllByIdNotAndNumChambreAndHotel_IdAndDatefinBetween(int id, int numChambre, int hotel, Date datedeb, Date datefin);
    public Iterable<ResaEntity> findAllByIdNotAndNumChambreAndHotel_IdAndDatedebBeforeAndDatefinAfter(int id, int numChambre, int hotel, Date date1, Date date2);

    public Iterable<ResaEntity> findAllByIdNotAndClient_IdAndDatedebBetween(int id, int id_client, Date datedeb, Date datefin);
    public Iterable<ResaEntity> findAllByIdNotAndClient_IdAndDatefinBetween(int id, int id_client, Date datedeb, Date datefin);
}
