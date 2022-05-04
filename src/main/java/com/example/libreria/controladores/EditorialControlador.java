package com.example.libreria.controladores;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registro")
    public String registro() {
        return "registroEditorial";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.cargar(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroEditorial.html";
        }

        modelo.put("mensaje", "Editorial registrada con éxito");
        return "exito.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("editoriales", editoriales);
        return "listaEditoriales.html";
    }

    @GetMapping("/darBaja/{id}")
    public String darBaja(ModelMap modelo, @PathVariable String id) {
        try {
            editorialServicio.darBaja(id);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listaEditoriales";
        }
        return "redirect:/editorial/lista";
    }

    @GetMapping("/darAlta/{id}")
    public String darAlta(ModelMap modelo, @PathVariable String id) {
        try {
            editorialServicio.darAlta(id);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listaEditoriales";
        }
        return "redirect:/editorial/lista";
    }

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, @RequestParam String id) {

        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.addAttribute("editorial", editorial);
        return "modificarEditorial.html";
    }

    @PostMapping("/actualizar")
    public String actualizar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) {
        Editorial editorial = null;
        try {
            editorial = editorialServicio.buscarPorId(id);
            editorialServicio.modificar(id, nombre);
        } catch (ErrorServicio ex) {
            modelo.put("editorial", editorial);
            modelo.put("nombre", nombre);
            modelo.put("error", ex.getMessage());
            return "modificarEditorial";
        }
        modelo.put("mensaje", "Se ha modificado la editorial " + nombre);
        return "exito";
    }
}
