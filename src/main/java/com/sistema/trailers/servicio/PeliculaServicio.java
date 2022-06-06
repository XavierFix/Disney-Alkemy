package com.sistema.trailers.servicio;

import com.sistema.trailers.modelo.Pelicula;
import com.sistema.trailers.modelo.Personaje;
import com.sistema.trailers.repositorios.PeliculaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServicio {
    
    @Autowired
    private PeliculaRepositorio peliculaRepositorio;
    
    public List<Pelicula> listAll(String palabraClave){
        if(palabraClave != null){
            return peliculaRepositorio.findAll(palabraClave);
            
        }return peliculaRepositorio.findAll();
    }
    
}
