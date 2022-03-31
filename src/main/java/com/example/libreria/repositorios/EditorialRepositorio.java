package com.example.libreria.repositorios;

import com.example.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :NOMBRE")
    public List<Editorial> buscarPorNombre(@Param("NOMBRE") String nombre);
    
}
