package com.example.libreria.controladores;

import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @PostMapping("/registrar")
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
        return "exitoAutor.html";
    }
    
}
