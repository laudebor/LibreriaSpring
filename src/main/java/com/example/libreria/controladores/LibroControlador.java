package com.example.libreria.controladores;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.servicios.AutorServicio;
import com.example.libreria.servicios.EditorialServicio;
import com.example.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
     @Autowired
    private LibroServicio libroServicio;
     @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registro")
    public String registro(ModelMap modelo){
         List<Autor> autores = autorServicio.listarAutores();
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.put("autores", autores);
         modelo.put("editoriales", editoriales);
        return "registroLibro.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, Long isbn, @RequestParam String titulo, Integer anio, Integer ejemplares, @RequestParam String idautor, @RequestParam String ideditorial){
        try {
            libroServicio.cargar(isbn, titulo, anio, ejemplares, idautor, ideditorial);
        } catch (ErrorServicio ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("idautor", idautor);
            modelo.put("ideditorial", ideditorial);
            return "registroLibro.html";
        }
        modelo.put("mensaje", "Libro registrado con éxito");
        return "exito.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
         List<Libro> libros = libroServicio.listarLibros();
         modelo.put("libros", libros);
        return "listaLibros.html";
    }
    
    @GetMapping("/darBaja/{id}")
    public String darBaja(ModelMap modelo, @PathVariable String id){
        try{
            libroServicio.darBaja(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaLibros";
        }
        return "redirect:/libro/lista";
    }
    
    @GetMapping("/darAlta/{id}")
    public String darAlta(ModelMap modelo, @PathVariable String id){
        try{
            libroServicio.darAlta(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaLibros";
        }
        return "redirect:/libro/lista";
    }
    
    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, @RequestParam String id){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        
        try{
            Libro libro = libroServicio.buscarPorId(id);
            modelo.addAttribute("ejemplar", libro);
        }catch(ErrorServicio ex){
            modelo.addAttribute("error", ex.getMessage());
        }
        return "modificarLibro.html";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(ModelMap modelo, @RequestParam String id, Long isbn, @RequestParam String titulo, Integer anio, Integer ejemplares, Integer ejemplaresprestados, @RequestParam String idautor, @RequestParam String ideditorial){
        Libro libro = null;
        try{
            libro = libroServicio.buscarPorId(id);
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresprestados, idautor, ideditorial);
        }catch(ErrorServicio ex){
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresprestados", ejemplaresprestados);
            modelo.put("idautor", idautor);
            modelo.put("ideditorial", ideditorial);    
            modelo.put("ejemplar", libro);
            modelo.put("error", ex.getMessage());
            return "modificarLibro";
        }
        modelo.put("mensaje", "Se ha modificado el libro " + titulo);
        return "exito";
    }
    
}
