package org.example.costsplitbd.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.GastoRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los grupos.
 */
@Service
@Validated
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoRepository gastoRepository;

    /**
     * Crea un nuevo grupo.
     *
     * @param crearGrupoDTO los datos para crear el grupo
     * @param usuarioCreador el usuario que crea el grupo
     * @return los datos del grupo creado
     * @throws Exception si ocurre algún error durante la creación
     */
    @Transactional
    public GrupoDTO crearGrupo(@Valid CrearGrupoDTO crearGrupoDTO, Usuario usuarioCreador) throws Exception {
        if (usuarioRepository.findById(usuarioCreador.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Usuario creador no encontrado");
        }

        validarDatosGrupo(crearGrupoDTO);

        Grupo grupo = new Grupo();
        grupo.setNombre(crearGrupoDTO.getNombre());
        grupo.setImagenUrl(crearGrupoDTO.getImagenUrl());
        grupo.setDescripcion(crearGrupoDTO.getDescripcion());
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>());
        grupo.getUsuarios().add(usuarioCreador);

        if (crearGrupoDTO.getParticipantesIds() != null && !crearGrupoDTO.getParticipantesIds().isEmpty()) {
            for (Long participanteId : crearGrupoDTO.getParticipantesIds()) {
                usuarioRepository.findById(participanteId).ifPresent(participante ->
                        grupo.getUsuarios().add(participante)
                );
            }
        }

        Grupo grupoAlmacenado = grupoRepository.save(grupo);
        return mapGrupoToDTO(grupoAlmacenado);
    }

    /**
     * Valida los datos del grupo antes de crearlo
     *
     * @param crearGrupoDTO DTO con los datos del grupo
     * @throws Exception si los datos no son válidos
     */
    private void validarDatosGrupo(CrearGrupoDTO crearGrupoDTO) throws Exception {
        if (crearGrupoDTO.getNombre() == null || crearGrupoDTO.getNombre().isEmpty()) {
            throw new Exception("No se puede crear un grupo sin nombre");
        }

        if (crearGrupoDTO.getParticipantesIds() == null || crearGrupoDTO.getParticipantesIds().isEmpty()) {
            throw new Exception("No se puede crear un grupo sin participantes");
        }
    }

    /**
     * Añade participantes a un grupo.
     *
     * @param idGrupo el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a añadir
     * @return los datos detallados del grupo actualizado
     */
    @Transactional
    public GrupoDetalladoDTO aniadirParticipantes(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            usuarioRepository.findById(idUsuario).ifPresent(usuario ->
                    grupo.getUsuarios().add(usuario)
            );
        }

        Grupo grupoActualizado = grupoRepository.save(grupo);
        return mapGrupoToDetalladoDTO(grupoActualizado);
    }

    /**
     * Elimina participantes de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a eliminar
     */
    @Transactional
    public void eliminarParticipante(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            if (!grupo.getUsuarios().contains(usuario)) {
                throw new IllegalArgumentException("Usuario no pertenece al grupo");
            }
            grupo.getUsuarios().remove(usuario);
        }

        grupoRepository.save(grupo);
    }

    /**
     * Obtiene la lista de participantes de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de participantes del grupo
     */
    public List<UsuarioDTO> verParticipantesGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        return grupo.getUsuarios().stream()
                .map(this::mapUsuarioToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista los grupos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de grupos del usuario
     */
    public List<GrupoDTO> listarGrupos(Long idUsuario) {
        return grupoRepository.findByUsuarios_Id(idUsuario).stream()
                .map(this::mapGrupoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ver detalles de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return los datos detallados del grupo
     */
    public GrupoDetalladoDTO verGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        return mapGrupoToDetalladoDTO(grupo);
    }

    /**
     * Mapea una entidad Grupo a un GrupoDTO
     */
    private GrupoDTO mapGrupoToDTO(Grupo grupo) {
        GrupoDTO dto = new GrupoDTO();
        dto.setId(grupo.getId());
        dto.setNombre(grupo.getNombre());
        dto.setImagenUrl(grupo.getImagenUrl());
        dto.setDescripcion(grupo.getDescripcion());
        dto.setFechaCreacion(grupo.getFechaCreacion());
        return dto;
    }

    /**
     * Mapea una entidad Grupo a un GrupoDetalladoDTO
     */
    private GrupoDetalladoDTO mapGrupoToDetalladoDTO(Grupo grupo) {
        GrupoDetalladoDTO dto = new GrupoDetalladoDTO();
        dto.setId(grupo.getId());
        dto.setNombre(grupo.getNombre());
        dto.setImagenUrl(grupo.getImagenUrl());
        dto.setDescripcion(grupo.getDescripcion());
        dto.setFechaCreacion(grupo.getFechaCreacion());

        List<UsuarioDTO> usuarioDTOs = grupo.getUsuarios().stream()
                .map(this::mapUsuarioToDTO)
                .collect(Collectors.toList());

        dto.setUsuarios(usuarioDTOs);
        return dto;
    }

    /**
     * Mapea una entidad Usuario a un UsuarioDTO
     */
    private UsuarioDTO mapUsuarioToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getUrlImg()
        );
    }


}