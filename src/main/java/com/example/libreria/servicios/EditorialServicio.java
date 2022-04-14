package com.example.libreria.servicios;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired 
    private EditorialRepositorio editorialRepositorio;
    
    public void cargar(String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);
        editorialRepositorio.save(editorial);
        
    }
    
    public void modificar(String id, String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("La editorial indicada no se encuentra en la base de datos");
        }
    }
    
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    public void darAlta(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("La editorial indicada no se encuentra en la base de datos");
        }
    }
    
    public void darBaja(String id) throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("La editorial indicada no se encuentra en la base de datos");
        }
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre==null||nombre.isEmpty()){
            throw new ErrorServicio("Debe indicar un nombre");
        }
    }
    
}
