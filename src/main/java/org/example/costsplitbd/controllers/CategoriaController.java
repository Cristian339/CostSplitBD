package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.CategoriaDTO;
import org.example.costsplitbd.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las categorías.
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Obtiene todas las categorías disponibles.
     */
    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    /**
     * Crea una nueva categoría.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crearCategoria(categoriaDTO);
    }
}