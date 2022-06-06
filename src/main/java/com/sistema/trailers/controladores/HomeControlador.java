package com.sistema.trailers.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.trailers.modelo.Pelicula;
import com.sistema.trailers.modelo.Personaje;
import com.sistema.trailers.repositorios.PeliculaRepositorio;
import com.sistema.trailers.repositorios.PersonajeRepositorio;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Controller
@RequestMapping("")
public class HomeControlador {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private PersonajeRepositorio personajeRepositorio;
    
    String palabraClave = "";

    @GetMapping("/")
    public ModelAndView verPaginaDeInicio() {
        List<Pelicula> peliculas = peliculaRepositorio.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending())).toList();
        return new ModelAndView("index")
                .addObject("ultimasPeliculas", peliculas);
    }

    @GetMapping("/peliculas")
    public ModelAndView listarPeliculas(@PageableDefault(sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable, @Param("palabraClave") String palabraClave) {
      
        List<Pelicula> peliculas = peliculaRepositorio.findAll(palabraClave);
        return new ModelAndView("peliculas")
                .addObject("peliculas", peliculas).addObject("palabraClave", palabraClave);
                
    }

    @GetMapping("/peliculas/{id}")
    public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepositorio.getOne(id);
        Optional<Personaje> personaje = personajeRepositorio.findById(id);
        return new ModelAndView("pelicula").addObject("pelicula", pelicula).addObject("personaje", personaje);
    }

    @GetMapping("/personajes")
    public ModelAndView listarPersonajes(@PageableDefault(sort = "edad", direction = Sort.Direction.DESC) Pageable pageable, @Param("palabraClave") String palabraClave) {
        List<Personaje> personajes = personajeRepositorio.findAll(palabraClave);
        return new ModelAndView("personajes")
                .addObject("personajes", personajes);
    }

    @GetMapping("/personajes/{id}")
    public ModelAndView mostrarDetallesDePersonaje(@PathVariable Integer id) {
        Personaje personaje = personajeRepositorio.getOne(id);
        return new ModelAndView("personaje").addObject("personaje", personaje);
    }

}
