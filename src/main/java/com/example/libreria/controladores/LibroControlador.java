package com.example.libreria.controladores;

import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    
//    @PostMapping("/registrar")
//    public String registrar(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idautor, @RequestParam String ideditorial){
//        try {
//            libroServicio.cargar(isbn, titulo, anio, ejemplares, idautor, ideditorial);
//        } catch (ErrorServicio ex) {
//            
//            modelo.put("error", ex.getMessage());
//            modelo.put("isbn", isbn);
//            modelo.put("titulo", titulo);
//            modelo.put("anio", anio);
//            modelo.put("ejemplares", ejemplares);
//            modelo.put("idautor", idautor);
//            modelo.put("ideditorial", ideditorial);
//            return "registroLibro.html";
//        }
//        modelo.put("titulo", "Bienvenido a la librería!");
//        modelo.put("descripcion", "El libro ha sido registrado con éxito");
//        return "exito.html";
//    }
    
}
