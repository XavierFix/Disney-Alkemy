package com.sistema.trailers.servicio;

import com.sistema.trailers.modelo.Personaje;
import com.sistema.trailers.repositorios.PersonajeRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonajeServicio {
    
    @Autowired
    private PersonajeRepositorio personajeRepositorio;
    
    public List<Personaje> listAll(String palabraClave){
        if(palabraClave != null){
            return personajeRepositorio.findAll(palabraClave);
            
        }return personajeRepositorio.findAll();
    }
    
}
