package com.sistema.trailers.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.trailers.modelo.Personaje;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PersonajeRepositorio extends JpaRepository<Personaje, Integer>{

    @Query("SELECT p FROM Personaje p WHERE p.nombre LIKE %?1% OR p.edad LIKE %?1%")
    public List<Personaje> findAll(String palabraClave);
    
}
