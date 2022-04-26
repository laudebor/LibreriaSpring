package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    public void cargar(String nombre) throws ErrorServicio{
        validar(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);
        autorRepositorio.save(autor);
    }
    
    public void modificar(String id, String nombre) throws ErrorServicio{
        
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El autor indicado no se encuentra en la base de datos");
        }
    }
    
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }
    
    public void darBaja(String id) throws ErrorServicio{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El autor indicado no se encuentra en la base de datos");
        }
    }
    
    public void darAlta(String id) throws ErrorServicio{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El autor indicado no se encuentra en la base de datos");
        }
    }
    
    public Autor buscarPorId(String id){
        return autorRepositorio.getById(id);
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre==null||nombre.isEmpty()){
            throw new ErrorServicio("Debe indicar un nombre");
        }
    }
    
}
