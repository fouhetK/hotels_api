package fr.m2i.hotels.api;

import fr.m2i.hotels.entities.HotelEntity;
import fr.m2i.hotels.entities.ImageEntity;
import fr.m2i.hotels.services.HotelService;
import fr.m2i.hotels.services.ImageService;
import fr.m2i.hotels.services.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageAPI {

    @Autowired
    private ImageService is;

    @Autowired
    private HotelService hs;

    @Autowired
    private StorageServiceImpl storageService;

    @GetMapping(value = "", produces = "application/json")
    public Iterable<ImageEntity> getAll(@RequestParam(name = "hotel", required = false, defaultValue = "0") int hotel) {
        return is.getAllByHotel(hotel);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ImageEntity> getById(@PathVariable("id") int id) {
        try{
            ImageEntity image = is.getById(id);
            return ResponseEntity.ok(image);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Image introuvable" );
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<ImageEntity> add(@RequestParam(value = "image") MultipartFile file , HttpServletRequest request ) throws IOException {

        ImageEntity image = new ImageEntity();
        this.populateImage(image, file, request);

        try{
            System.out.println("add : " + image);
            is.add(image);

            return ResponseEntity.ok(image);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage(), e);
        }
    }

    @PutMapping( value = "/{id}" ,  produces = "application/json")
    public void editImage(@PathVariable int id , @RequestParam(value = "image") MultipartFile file , HttpServletRequest request ) throws IOException {

        ImageEntity imageUpdated = new ImageEntity();
        this.populateImage(imageUpdated, file, request);

        try{
            System.out.println("edit : " + imageUpdated);
            is.update(id, imageUpdated);
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
    }

    private void populateImage(ImageEntity image, MultipartFile file, HttpServletRequest request) throws IOException {
        String desc = request.getParameter("description");
        String hotelId = request.getParameter("hotel");

        HotelEntity hotel = hs.getById(Integer.parseInt(hotelId));

        String photo = null;
        if( file != null )
            photo = storageService.store(file , "src\\main\\resources\\static\\images\\uploads\\" + hotel.getId() + "\\");

        image.setDescription(desc);
        image.setPath(photo);
        image.setHotel(hotel);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            is.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image introuvable");
        }
    }
}
