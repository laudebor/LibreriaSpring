package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.errores.ErrorServicio;
import com.example.libreria.repositorios.AutorRepositorio;
import com.example.libreria.repositorios.EditorialRepositorio;
import com.example.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(propagation = Propagation.NESTED)
    public void cargar(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        if (respuestaAutor.isPresent()) {
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
            if (respuestaEditorial.isPresent()) {
                Libro libro = new Libro();
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setEjemplares(ejemplares);
                libro.setEjemplaresPrestados(0);
                libro.setEjemplaresRestantes(ejemplares);
                libro.setAlta(Boolean.TRUE);
                Autor autor = respuestaAutor.get();
                libro.setAutor(autor);
                Editorial editorial = respuestaEditorial.get();
                libro.setEditorial(editorial);
                libroRepositorio.save(libro);
            } else {
                
                throw new ErrorServicio("La editorial indicada no se encuentra en la base de datos");
            }
        } else {
            throw new ErrorServicio("El autor ingresado no se encuentra en la base de datos");
        }        
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresprestados, String idAutor, String idEditorial) throws ErrorServicio{
        
        validar2(isbn, titulo, anio, ejemplares,ejemplaresprestados);
        
        Optional<Libro> respuestaLibro = libroRepositorio.findById(id);
        
        if(respuestaLibro.isPresent()){
            Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
            if(respuestaAutor.isPresent()){
                Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
                if(respuestaEditorial.isPresent()){
                    Libro libro = respuestaLibro.get();
                    Autor autor = respuestaAutor.get();
                    Editorial editorial = respuestaEditorial.get();
                    libro.setIsbn(isbn);
                    libro.setTitulo(titulo);
                    libro.setAnio(anio);
                    libro.setEjemplares(ejemplares);
                    libro.setEjemplaresPrestados(ejemplaresprestados);
                    libro.setEjemplaresRestantes(ejemplares-ejemplaresprestados);
                    libro.setAutor(autor);
                    libro.setEditorial(editorial);
                    libroRepositorio.save(libro);
                }else{
                    throw new ErrorServicio("La editorial indicada no se encuentra en la base de datos");
                }
            }else{
                throw new ErrorServicio("El autor indicado no se encuentra en la base de datos");
            }    
        }else{
            throw new ErrorServicio("El libro indicado no se encuentra en la base de datos");
        }
    }
    
    public List<Libro> listarLibros(){
        return libroRepositorio.findAll();
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void darBaja(String id) throws ErrorServicio{
        
        Optional<Libro> respuestaLibro = libroRepositorio.findById(id);
        
        if(respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("El libro indicado no se encuentra en la base de datos");
        }   
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void darAlta(String id) throws ErrorServicio{
        
        Optional<Libro> respuestaLibro = libroRepositorio.findById(id);
        
        if(respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("El libro indicado no se encuentra en la base de datos");
        }
        
    }
    
    @Transactional(readOnly=true)
    public List<Libro> mostrar(){
        return libroRepositorio.findAll();
    }
    
    public Libro buscarPorId(String id) throws ErrorServicio{
        
        return libroRepositorio.getById(id);
        
//            Optional<Libro> respuesta = libroRepositorio.findById(id);
//        
//            if(respuesta.isPresent()){
//                return respuesta.get();
//            }else{
//                throw new ErrorServicio("No se encontro el libro");
//            }
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void borrarPorId(String id) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            libroRepositorio.delete(respuesta.get());
        }else{
            throw new ErrorServicio("El libro indicado no se encuentra en la base de datos");
        }
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("Debe indicar el ISBN");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Debe indicar el título");
        }
        if (anio == null) {
            throw new ErrorServicio("Debe indicar el año");
        }
        if(ejemplares==null){
            throw new ErrorServicio("Debe indicar el número de ejemplares");
        }
    }
     
    public void validar2(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresprestados) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("Debe indicar el ISBN");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Debe indicar el título");
        }
        if (anio == null) {
            throw new ErrorServicio("Debe indicar el año");
        }
        if(ejemplares==null){
            throw new ErrorServicio("Debe indicar el número de ejemplares");
        }
        if(ejemplaresprestados==null){
            throw new ErrorServicio("Debe indicar el número de ejemplares prestados");
        }
        
    }

}
