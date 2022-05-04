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
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/exito")
    public String exito(){
        return "exito.html";
    }
    
    @RequestMapping("/accessdenied")
        public ModelAndView accessdenied() {
            return new ModelAndView("accessdenied");
        }
    
}
