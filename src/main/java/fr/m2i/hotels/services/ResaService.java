package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.ResaEntity;
import fr.m2i.hotels.repositories.ResaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.util.Calendar;

@Service
public class ResaService {

    @Autowired
    private ResaRepository rr;

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

        // check if there is already a reservation for the same chamber and hotel that start between the two new date
        Iterable<ResaEntity> tmp = rr.findAllByIdNotAndNumChambreAndHotel_IdAndDatedebBetween(resa.getId(), resa.getNumChambre(), resa.getHotel().getId(), resa.getDatedeb(), resa.getDatefin());
        if (tmp.iterator().hasNext())
            throw new InvalidObjectException( "Une autre réservation existe déja pour la chambre n°" + resa.getNumChambre() + " de l'hotel " + resa.getHotel().getNom() );
        // check if there is already a reservation for the same chamber and hotel that end between the two new date
        tmp = rr.findAllByIdNotAndNumChambreAndHotel_IdAndDatefinBetween(resa.getId(), resa.getNumChambre(), resa.getHotel().getId(), resa.getDatedeb(), resa.getDatefin());
        if (tmp.iterator().hasNext())
            throw new InvalidObjectException( "Une autre réservation existe déja pour la chambre n°" + resa.getNumChambre() + " de l'hotel " + resa.getHotel().getNom() );
        // check if there is already a reservation for the same chamber and hotel that suround the new date
        tmp = rr.findAllByIdNotAndNumChambreAndHotel_IdAndDatedebBeforeAndDatefinAfter(resa.getId(), resa.getNumChambre(), resa.getHotel().getId(), resa.getDatedeb(), resa.getDatedeb());
        if (tmp.iterator().hasNext())
            throw new InvalidObjectException( "Une autre réservation existe déja pour la chambre n°" + resa.getNumChambre() + " de l'hotel " + resa.getHotel().getNom() );

    }

    public ResaEntity getById(int id){
        return rr.findById(id).get();
    }

    public Iterable<ResaEntity> getAll(int client){
        if (client > 0)
            return rr.findAllByClient_Id(client);
        else
            return rr.findAll();
    }

    public void update(int id, ResaEntity resa) throws InvalidObjectException, ResponseStatusException {
        System.out.println(id);
        System.out.println(resa.getId());
        checkResa(resa);
        ResaEntity toUpdate = this.getById(id);

        toUpdate.setClient(resa.getClient());
        toUpdate.setDatedeb(resa.getDatedeb());
        toUpdate.setDatefin(resa.getDatefin());
        toUpdate.setClient(resa.getClient());
        toUpdate.setNumChambre(resa.getNumChambre());

        rr.save(toUpdate);
    }

    public void add(ResaEntity resa) throws InvalidObjectException, ResponseStatusException {
        checkResa(resa);
        rr.save(resa);
    }

    public void delete(int id){
        ResaEntity resa = this.getById(id);
        rr.delete(resa);
    }
}
