package com.sistema.trailers.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.trailers.modelo.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer>{

    @Query("SELECT p FROM Pelicula p WHERE p.titulo LIKE %?1%")
    public List<Pelicula> findAll(String palabraClave);
}
