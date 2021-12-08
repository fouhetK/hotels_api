package fr.m2i.hotels.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image", schema = "hotels", catalog = "")
public class ImageEntity {
    private int id;
    private String description;
    private String path;
    private HotelEntity hotel;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id == that.id && Objects.equals(description, that.description) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, path);
    }

    @OneToOne
    @JoinColumn(name = "hotel", referencedColumnName = "id", nullable = false)
    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", desc='" + description + '\'' +
                ", path='" + path + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}
