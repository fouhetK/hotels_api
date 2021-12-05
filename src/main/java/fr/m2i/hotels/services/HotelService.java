package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.HotelEntity;
import fr.m2i.hotels.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hr;

    private void checkHotel(HotelEntity hotel) throws InvalidObjectException {
        if (hotel.getNom().length() <= 2)
            throw new InvalidObjectException("Nom invalide");
    }

    public HotelEntity getById(int id){
        return hr.findById(id).get();
    }

    public Iterable<HotelEntity> getAll(){
        return hr.findAll();
    }

    public void update(int id, HotelEntity hotel) throws InvalidObjectException {

        checkHotel(hotel);

        HotelEntity toUpdate = this.getById(id);

        toUpdate.setAdresse(hotel.getAdresse());
        toUpdate.setEmail(hotel.getEmail());
        toUpdate.setTelephone(hotel.getTelephone());
        toUpdate.setEtoiles(hotel.getEtoiles());
        toUpdate.setNom(hotel.getNom());
        toUpdate.setVille(hotel.getVille());

        hr.save(toUpdate);
    }

    public void add(HotelEntity hotel) throws InvalidObjectException {
        checkHotel(hotel);
        hr.save(hotel);
    }

    public void delete(int id){
        HotelEntity hotel = this.getById(id);
        hr.delete(hotel);
    }
}
