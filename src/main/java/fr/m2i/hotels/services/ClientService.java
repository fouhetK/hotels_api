package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.ClientEntity;
import fr.m2i.hotels.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository cr;

    private void checkClient(ClientEntity client) throws InvalidObjectException {
        if (client.getNomComplet().length() <= 2)
            throw new InvalidObjectException("Nom invalide");
    }

    public ClientEntity getById(int id){
        return cr.findById(id).get();
    }

    public Iterable<ClientEntity> getAll(){
        return cr.findAll();
    }

    public void update(int id, ClientEntity client) throws InvalidObjectException {
        checkClient(client);
        ClientEntity toUpdate = this.getById(id);

        toUpdate.setAdresse(client.getAdresse());
        toUpdate.setEmail(client.getEmail());
        toUpdate.setTelephone(client.getTelephone());
        toUpdate.setNomComplet(client.getNomComplet());

        cr.save(toUpdate);
    }

    public void add(ClientEntity client) throws InvalidObjectException {
        checkClient(client);
        cr.save(client);
    }

    public void delete(int id){
        ClientEntity client = this.getById(id);
        cr.delete(client);
    }
}
