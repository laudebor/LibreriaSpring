package com.example.libreria.repositorios;

import com.example.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = : ISBN")
    public Libro buscarPorISBN(@Param("ISBN") Long isbn);
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :TITULO")
    public List<Libro> buscarPorTitulo(@Param("TITULO") String titulo);
    
    @Query("SELECT l FROM Libro l JOIN Autor a WHERE a.nombre = :NOMBRE")
    public List<Libro> buscarPorAutor(@Param("NOMBRE") String nombre);
}
