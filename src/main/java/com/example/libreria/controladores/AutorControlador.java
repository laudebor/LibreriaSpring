package com.example.libreria.controladores;

import com.example.libreria.entidades.Autor;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
  @GetMapping("/registro")
    public String registro() {
        return "registroAutor.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre){
        try{
            autorServicio.cargar(nombre);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroAutor.html";
        }
        modelo.put("mensaje", "Autor registrado con éxito");
        return "exito.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
         List<Autor> autores = autorServicio.listarAutores();
         modelo.put("autores", autores);
        return "listaAutores.html";
    }
    
    
    @GetMapping("/darBaja/{id}")
    public String darBaja(ModelMap modelo, @PathVariable String id){
        try{
            autorServicio.darBaja(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaAutores";
        }
        return "redirect:/autor/lista";
    }
    
    @GetMapping("/darAlta/{id}")
    public String darAlta(ModelMap modelo, @PathVariable String id){
        try{
            autorServicio.darAlta(id);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            return "listaAutores";
        }
        return "redirect:/autor/lista";
    }

    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, @RequestParam String id){
        
        Autor autor = autorServicio.buscarPorId(id);
        modelo.addAttribute("autor", autor);
        return "modificarAutor.html";
    }
     
    @PostMapping("/actualizar")
    public String actualizar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre){
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
        modelo.put("mensaje", "Se ha modificado el autor " + nombre);
        return "exito";
    }
    
}
