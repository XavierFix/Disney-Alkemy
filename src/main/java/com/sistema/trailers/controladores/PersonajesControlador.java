package com.sistema.trailers.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.trailers.modelo.Pelicula;
import com.sistema.trailers.modelo.Personaje;
import com.sistema.trailers.repositorios.PeliculaRepositorio;
import com.sistema.trailers.repositorios.PersonajeRepositorio;
import com.sistema.trailers.servicio.AlmacenServicioImpl;

@Controller
@RequestMapping("/index-personajes")
public class PersonajesControlador {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("")
    public ModelAndView verPaginaDePersonajes(@PageableDefault(sort = "nombre", size = 5) Pageable pageable) {
        Page<Personaje> personajes = personajeRepositorio.findAll(pageable);
        return new ModelAndView("admin/index-personajes").addObject("personajes", personajes);
    }

    //CRUD de personajes
    @GetMapping("/personajes/nuevo")
    public ModelAndView mostrarFormularioDeNuevoPersonaje() {
        List<Pelicula> peliculas = peliculaRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/nuevo-personaje")
                .addObject("personaje", new Personaje())
                .addObject("peliculas", peliculas);
    }

    @PostMapping("/personajes/nuevo")
    public ModelAndView registrarPersonaje(@Validated Personaje personaje, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || personaje.getImagen().isEmpty()) {
            if (personaje.getImagen().isEmpty()) {
                bindingResult.rejectValue("imagen", "MultipartNotEmpty");
            }

            List<Pelicula> peliculas = peliculaRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/nuevo-personaje")
                    .addObject("personaje", personaje)
                    .addObject("peliculas", peliculas);
        }

        String rutaImagen = servicio.almacenarArchivo(personaje.getImagen());
        personaje.setRutaImagen(rutaImagen);

        personajeRepositorio.save(personaje);
        return new ModelAndView("redirect:/index-personajes");
    }

    @GetMapping("/personajes/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarPersonaje(@PathVariable Integer id) {
        Personaje personaje = personajeRepositorio.getOne(id);
        List<Pelicula> peliculas = peliculaRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/editar-personaje")
                .addObject("personaje", personaje)
                .addObject("peliculas", peliculas);
    }

    @PostMapping("/personajes/{id}/editar")
    public ModelAndView actualizarPersonaje(@PathVariable Integer id, @Validated Personaje personaje, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/editar-personaje")
                    .addObject("personaje", personaje);
        }

        Personaje personajeDB = personajeRepositorio.getOne(id);
        personajeDB.setNombre(personaje.getNombre());
        personajeDB.setEdad(personaje.getEdad());
        personajeDB.setPeso(personaje.getPeso());
        personajeDB.setHistoria(personaje.getHistoria());
         personajeDB.setPeliculas(personaje.getPeliculas());

        if (!personaje.getImagen().isEmpty()) {
            servicio.eliminarArchivo(personajeDB.getRutaImagen());
            String rutaImagen = servicio.almacenarArchivo(personaje.getImagen());
            personajeDB.setRutaImagen(rutaImagen);
        }

        personajeRepositorio.save(personajeDB);
        return new ModelAndView("redirect:/index-personajes");
    }

    @PostMapping("/personajes/{id}/eliminar")
    public String eliminarPersonajes(@PathVariable Integer id) {
        Personaje personaje = personajeRepositorio.getOne(id);
        personajeRepositorio.delete(personaje);
        servicio.eliminarArchivo(personaje.getRutaImagen());

        return "redirect:/admin";
    }
}
