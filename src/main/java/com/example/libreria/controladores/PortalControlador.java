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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    
    /*METODOS DE LA ENTIDAD LIBRO*/
    
    @GetMapping("/registroLibro")
    public String registroLibro(ModelMap modelo){
         List<Autor> autores = autorServicio.listarAutores();
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.put("autores", autores);
         modelo.put("editoriales", editoriales);
        return "registroLibro.html";
    }
    
    @PostMapping("/registrarLibro")
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
        modelo.put("titulo", "Bienvenido a la librería!");
        modelo.put("descripcion", "El libro ha sido registrado con éxito");
        return "exito.html";
    }
    
    @GetMapping("/listaLibros")
    public String listarLibros(ModelMap modelo) {
         List<Libro> libros = libroServicio.listarLibros();
         modelo.put("libros", libros);
        return "listaLibros.html";
    }
    
    @GetMapping("/darBaja/{id}")
    public String darBajaLibro(ModelMap modelo, @PathVariable String id){
        try{
            libroServicio.darBaja(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaLibros";
        }
        return "redirect:/listaLibros";
    }
    
    @GetMapping("/darAlta/{id}")
    public String darAltaLibro(ModelMap modelo, @PathVariable String id){
        try{
            libroServicio.darAlta(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaLibros";
        }
        return "redirect:/listaLibros";
    }
    
    @GetMapping("/modificarLibro")
    public String modificarLibro(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.put("autores", autores);
         modelo.put("editoriales", editoriales);
        return "modificarLibro.html";
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
    public String actualizarLibro(ModelMap modelo, @RequestParam String id, Long isbn, @RequestParam String titulo, Integer anio, Integer ejemplares, Integer ejemplaresprestados, @RequestParam String idautor, @RequestParam String ideditorial){
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
        return "exito";
    }
    
    
    /*MÉTODOS DE LA ENTIDAD AUTOR*/
    
    
    @GetMapping("/registroAutor")
    public String registroAutor() {
        return "registroAutor.html";
    }
    
    @PostMapping("/registrarAutor")
    public String registrarAutor(ModelMap modelo, @RequestParam String nombre){
        try{
            autorServicio.cargar(nombre);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroAutor.html";
        }
        modelo.put("titulo", "Autores!!!");
        modelo.put("descripcion", "Autor cargado con éxito");
        return "exito.html";
    }
    
    @GetMapping("/listaAutores")
    public String listarAutores(ModelMap modelo) {
         List<Autor> autores = autorServicio.listarAutores();
         modelo.put("autores", autores);
        return "listaAutores.html";
    }
    
    
    @GetMapping("/darBajaA/{id}")
    public String darBajaAutor(ModelMap modelo, @PathVariable String id){
        try{
            autorServicio.darBaja(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaAutores";
        }
        return "redirect:/listaAutores";
    }
    
    @GetMapping("/darAltaA/{id}")
    public String darAltaAutor(ModelMap modelo, @PathVariable String id){
        try{
            autorServicio.darAlta(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaAutores";
        }
        return "redirect:/listaAutores";
    }
    
    @GetMapping("/modificarAutor")
    public String modificarAutor(){
        return "modificarAutor.html";
    }
    
    @GetMapping("/modificarA")
    public String modificarA(ModelMap modelo, @RequestParam String id){
        
        Autor autor = autorServicio.buscarPorId(id);
        modelo.addAttribute("autor", autor);
        return "modificarAutor.html";
    }
     
    @PostMapping("/actualizarA")
    public String actualizarAutor(ModelMap modelo, @RequestParam String id, @RequestParam String nombre){
        Autor autor = null;
        try{
            autor = autorServicio.buscarPorId(id);
            autorServicio.modificar(id, nombre);
        }catch(ErrorServicio ex){
            modelo.put("autor", autor);
            modelo.put("nombre", nombre);
            modelo.put("error", ex.getMessage());
            return "modificarAutor";
        }
        return "exito";
    }
    
    
    /*MÉTODOS DE LA ENTIDAD EDITORIAL*/
    
    
    @GetMapping("/registroEditorial")
    public String registroEditorial(){
        return "registroEditorial";
    }
   
    @PostMapping("/registrarEditorial")
    public String registrarEditorial(ModelMap modelo, @RequestParam String nombre){
        try{
            editorialServicio.cargar(nombre);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroAutor.html";
        }
        modelo.put("titulo", "Editoriales!!!!!");
        modelo.put("descripcion", "Editorial cargada con éxito");
        return "exito.html";
    }
    
    @GetMapping("/listaEditoriales")
    public String listarEditoriales(ModelMap modelo) {
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.put("editoriales", editoriales);
        return "listaEditoriales.html";
    }
   
    @GetMapping("/darBajaEd/{id}")
    public String darBajaEditorial(ModelMap modelo, @PathVariable String id){
        try{
            editorialServicio.darBaja(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaEditoriales";
        }
        return "redirect:/listaEditoriales";
    }
    
    @GetMapping("/darAltaEd/{id}")
    public String darAltaEditorial(ModelMap modelo, @PathVariable String id){
        try{
            editorialServicio.darAlta(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaEditoriales";
        }
        return "redirect:/listaEditoriales";
    }
   
    @GetMapping("/modificarEditorial")
    public String modificarEditorial(){
        return "modificarEditorial.html";
    }   
    
    @GetMapping("/modificarEd")
    public String modificarEd(ModelMap modelo, @RequestParam String id){
        
        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.addAttribute("editorial", editorial);
        return "modificarEditorial.html";
    }    
    
    @PostMapping("/actualizarEd")
    public String actualizarEditorial(ModelMap modelo, @RequestParam String id, @RequestParam String nombre){
        Editorial editorial = null;
        try{
            editorial = editorialServicio.buscarPorId(id);
            editorialServicio.modificar(id, nombre);
        }catch(ErrorServicio ex){
            modelo.put("editorial", editorial);
            modelo.put("nombre", nombre);
            modelo.put("error", ex.getMessage());
            return "modificarEditorial";
        }
        return "exito";
    }
    
    @RequestMapping("/accessdenied")
        public ModelAndView accessdenied() {
            return new ModelAndView("accessdenied");
        }
    
}
