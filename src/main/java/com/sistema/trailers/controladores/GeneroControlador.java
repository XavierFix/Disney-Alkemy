package com.sistema.trailers.controladores;

import com.sistema.trailers.modelo.Genero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.trailers.repositorios.GeneroRepositorio;

@Controller
@RequestMapping("/index-generos")
public class GeneroControlador {

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @GetMapping("")
    public ModelAndView verPaginaDeGeneros(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        
        Page<Genero> generos = generoRepositorio.findAll(pageable);
        return new ModelAndView("admin/index-generos").addObject("generos", generos);
    }

    //CRUD de generos
    @GetMapping("/generos/nuevo")
    public ModelAndView mostrarFormularioDeNuevoGenero() {
        return new ModelAndView("admin/nuevo-genero")
                .addObject("genero", new Genero());
    }

    @PostMapping("/generos/nuevo")
    public ModelAndView registrarGenero(@Validated Genero genero, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/index-generos")
                    .addObject("genero", genero);
        }
        generoRepositorio.save(genero);
        return new ModelAndView("redirect:/index-generos")
                .addObject("genero", genero);
    }

    @GetMapping("/generos/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarGenero(@PathVariable Integer id) {
        Genero genero = generoRepositorio.getOne(id);
        return new ModelAndView("admin/editar-genero")
                .addObject("genero", genero);
    }

    @PostMapping("/generos/{id}/editar")
    public ModelAndView actualizarGenero(@PathVariable Integer id, @Validated Genero genero, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/editar-genero")
                    .addObject("genero", genero);
        }

        Genero generoDB = generoRepositorio.getOne(id);
        generoDB.setTitulo(genero.getTitulo());

        generoRepositorio.save(generoDB);
        return new ModelAndView("redirect:/index-generos");
    }

    @PostMapping("/generos/{id}/eliminar")
    public String eliminarGeneros(@PathVariable Integer id) {
        Genero genero = generoRepositorio.getOne(id);
        generoRepositorio.delete(genero);
        return "redirect:/index-generos";
    }
}
