package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.ClientEntity;
import fr.m2i.hotels.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.regex.Pattern;

@Service
public class ClientService {

    @Autowired
    private ClientRepository cr;

    private void checkClient(ClientEntity client) throws InvalidObjectException {
        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String regexTel = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";

        if (client.getNomComplet().length() < 2){
            throw new InvalidObjectException("Nom complet du client invalide");
        } else if (!Pattern.compile(regexEmail).matcher(client.getEmail()).matches()) {
            throw new InvalidObjectException("Email du client invalide");
        } else if (!Pattern.compile(regexTel).matcher(client.getTelephone()).matches()) {
            throw new InvalidObjectException("Téléphone du client invalide");
        }
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
