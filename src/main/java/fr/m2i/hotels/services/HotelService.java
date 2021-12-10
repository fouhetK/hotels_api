package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.HotelEntity;
import fr.m2i.hotels.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.regex.Pattern;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hr;

    private void checkHotel(HotelEntity hotel) throws InvalidObjectException {
        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String regexTel = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";

        if (hotel.getNom().length() < 2){
            throw new InvalidObjectException("Nom de l'hotel invalide");
        }
        else if (hotel.getEtoiles() <= 0 || hotel.getEtoiles() >= 6) {
            throw new InvalidObjectException("Nombre d'étoiles invalide");
        } else if (!Pattern.compile(regexEmail).matcher(hotel.getEmail()).matches()) {
            throw new InvalidObjectException("Email de l'hotel invalide");
        } else if (!Pattern.compile(regexTel).matcher(hotel.getTelephone()).matches()) {
            throw new InvalidObjectException("Téléphone de l'hotel invalide");
        }
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
