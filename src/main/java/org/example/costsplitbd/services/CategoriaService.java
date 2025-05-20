package org.example.costsplitbd.services;

import jakarta.validation.Valid;
import org.example.costsplitbd.dto.CategoriaDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Categoria;
import org.example.costsplitbd.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con las categorías de gastos.
 */
@Service
@Validated
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Lista todas las categorías disponibles.
     *
     * @return lista de categorías
     */
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::mapCategoriaToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva categoría.
     *
     * @param categoriaDTO datos de la categoría
     * @return la categoría creada
     */
    @Transactional
    public CategoriaDTO crearCategoria(@Valid CategoriaDTO categoriaDTO) {
        // Validar que el nombre no está vacío
        if (categoriaDTO.getNombre() == null || categoriaDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }

        // Verificar si ya existe una categoría con ese nombre
        if (categoriaRepository.findByNombreIgnoreCase(categoriaDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setIcono(categoriaDTO.getIcono());
        categoria.setColor(categoriaDTO.getColor());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaSaved = categoriaRepository.save(categoria);
        return mapCategoriaToDTO(categoriaSaved);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id identificador de la categoría
     * @return la categoría encontrada
     */
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return mapCategoriaToDTO(categoria);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id identificador de la categoría
     * @param categoriaDTO datos actualizados
     * @return la categoría actualizada
     */
    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, @Valid CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (categoriaDTO.getNombre() != null && !categoriaDTO.getNombre().trim().isEmpty()) {
            // Verificar que no exista otra categoría con el mismo nombre (excepto esta misma)
            categoriaRepository.findByNombreIgnoreCase(categoriaDTO.getNombre())
                    .ifPresent(existente -> {
                        if (!existente.getId().equals(id)) {
                            throw new IllegalArgumentException("Ya existe otra categoría con ese nombre");
                        }
                    });
            categoria.setNombre(categoriaDTO.getNombre());
        }

        if (categoriaDTO.getIcono() != null) {
            categoria.setIcono(categoriaDTO.getIcono());
        }

        if (categoriaDTO.getColor() != null) {
            categoria.setColor(categoriaDTO.getColor());
        }

        if (categoriaDTO.getDescripcion() != null) {
            categoria.setDescripcion(categoriaDTO.getDescripcion());
        }

        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return mapCategoriaToDTO(categoriaActualizada);
    }

    /**
     * Elimina una categoría.
     *
     * @param id identificador de la categoría
     */
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    /**
     * Mapea una entidad Categoria a un CategoriaDTO.
     *
     * @param categoria la entidad a mapear
     * @return el DTO resultante
     */
    private CategoriaDTO mapCategoriaToDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setIcono(categoria.getIcono());
        dto.setColor(categoria.getColor());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}