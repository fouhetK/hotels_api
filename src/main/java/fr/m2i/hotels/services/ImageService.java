package fr.m2i.hotels.services;

import fr.m2i.hotels.entities.ImageEntity;
import fr.m2i.hotels.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@Service
public class ImageService {

    @Autowired
    private ImageRepository ir;

    private void checkImage(ImageEntity image) throws InvalidObjectException {
    }

    public ImageEntity getById(int id){
        return ir.findById(id).get();
    }

    public Iterable<ImageEntity> getAll(){
        return ir.findAll();
    }

    public Iterable<ImageEntity> getAllByHotel(int id){
        return ir.findAllByHotel_Id(id);
    }

    public void update(int id, ImageEntity image) throws InvalidObjectException {
        checkImage(image);
        ImageEntity toUpdate = this.getById(id);

        toUpdate.setDescription(image.getDescription());
        toUpdate.setPath(image.getPath());
        toUpdate.setHotel(toUpdate.getHotel());

        ir.save(toUpdate);
    }

    public void add(ImageEntity image) throws InvalidObjectException {
        checkImage(image);
        ir.save(image);
    }

    public void delete(int id){
        ImageEntity image = this.getById(id);
        ir.delete(image);
    }
}
