package com.sistema.trailers.modelo;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personaje")
    private Integer id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String historia;

    @NotNull
    private int edad;

    @NotNull
    private int peso;

    private String rutaImagen;

    @Transient
    private MultipartFile imagen;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pelicula_personaje", joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_personaje"))
    private List<Pelicula> peliculas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Personaje() {
    }

    public Personaje(Integer id, @NotBlank String nombre, @NotBlank String historia, @NotNull int edad, @NotNull int peso, @NotBlank String rutaImagen, MultipartFile imagen, List<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.historia = historia;
        this.edad = edad;
        this.peso = peso;
        this.rutaImagen = rutaImagen;
        this.imagen = imagen;
        this.peliculas = peliculas;
    }

    public Personaje(@NotBlank String nombre, @NotBlank String historia, @NotNull int edad, @NotNull int peso, @NotBlank String rutaImagen, MultipartFile imagen,  List<Pelicula> peliculas) {
        this.nombre = nombre;
        this.historia = historia;
        this.edad = edad;
        this.peso = peso;
        this.rutaImagen = rutaImagen;
        this.imagen = imagen;
        this.peliculas = peliculas;
    }

}
