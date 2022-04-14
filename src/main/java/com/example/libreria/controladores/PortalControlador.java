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
    
    @GetMapping("/registroLibro")
    public String registroLibro(ModelMap modelo){
         List<Autor> autores = autorServicio.listarAutores();
         List<Editorial> editoriales = editorialServicio.listarEditoriales();
         modelo.put("autores", autores);
         modelo.put("editoriales", editoriales);
        return "registroLibro.html";
    }
    
    @GetMapping("/registroAutor")
     public String registroAutor() {
        return "registroAutor.html";
    }
     
     @GetMapping("/listaLibros")
     public String listarLibros(ModelMap modelo) {
         List<Libro> libros = libroServicio.listarLibros();
         modelo.put("libros", libros);
        return "listaLibros.html";
    }
    
    @GetMapping("/registroEditorial")
    public String registroEditorial(){
        return "registroEditorial";
    }
    
    @PostMapping("/registrarLibro")
    public String registrar(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idautor, @RequestParam String ideditorial){
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
    
    
    @RequestMapping("/accessdenied")
        public ModelAndView accessdenied() {
            return new ModelAndView("accessdenied");
        }
    
}
