package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.ResaEntity;
import fr.m2i.hotels.repositories.ResaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.util.Calendar;

@Service
public class ResaService {

    @Autowired
    private ResaRepository hr;

    private void checkResa(ResaEntity resa) throws InvalidObjectException {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        if (resa.getDatedeb().compareTo(date) <= 0)
            throw new InvalidObjectException("Date de début doit être supérieur à celle d'aujourd'hui");
        else if (resa.getDatedeb().compareTo(resa.getDatefin()) >= 0)
            throw new InvalidObjectException("Date de fin doit être supérieur a la date de début");
        else if (resa.getClient() == null)
            throw new InvalidObjectException("Vous devez selectionner le client");
        else if (resa.getHotel() == null)
            throw new InvalidObjectException("Vous devez selectionner l'hotel");
        else if (resa.getNumChambre() == 0)
            throw new InvalidObjectException("Vous devez selectionner un numéro de chambre");
    }

    public ResaEntity getById(int id){
        return hr.findById(id).get();
    }

    public Iterable<ResaEntity> getAll(){
        return hr.findAll();
    }

    public void update(int id, ResaEntity resa) throws InvalidObjectException {
        checkResa(resa);
        ResaEntity toUpdate = this.getById(id);

        toUpdate.setClient(resa.getClient());
        toUpdate.setDatedeb(resa.getDatedeb());
        toUpdate.setDatefin(resa.getDatefin());
        toUpdate.setClient(resa.getClient());
        toUpdate.setNumChambre(resa.getNumChambre());

        hr.save(toUpdate);
    }

    public void add(ResaEntity resa) throws InvalidObjectException {
        checkResa(resa);
        hr.save(resa);
    }

    public void delete(int id){
        ResaEntity resa = this.getById(id);
        hr.delete(resa);
    }
}
