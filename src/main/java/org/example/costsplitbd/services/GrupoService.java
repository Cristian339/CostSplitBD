package org.example.costsplitbd.services;

import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.GastoRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoRepository gastoRepository;


    public GrupoDTO crearGrupo(CrearGrupoDTO crearGrupoDTO) {
        Grupo grupo = new Grupo();
        grupo.setNombre(crearGrupoDTO.getNombre());
        grupo.setImagenUrl(crearGrupoDTO.getImagenUrl());
        grupo.setDescripcion(crearGrupoDTO.getDescripcion());
        grupo.setFechaCreacion(LocalDateTime.now());
        Grupo grupoAlmacenado = grupoRepository.save(grupo);

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setId(grupoAlmacenado.getId());
        grupoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());

        return grupoDTO;
    }

    public List<Grupo> getAllGrupo() {
        return grupoRepository.findAll();
    }

    public Grupo getGrupoById(Long id) {
        return grupoRepository.findById(id).orElse(null);
    }

    public void deleteGrupoById(Long id) {
        grupoRepository.deleteById(id);
    }


    public GrupoDetalladoDTO aniadirParticipantes(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        if (grupo == null) {
            // Handle the case where the group is not found
            return null;
        }

        Set<Usuario> usuarios = grupo.getUsuarios();
        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                usuarios.add(usuario);
            }
        }
        grupo.setUsuarios(usuarios);
        Grupo grupoAlmacenado = grupoRepository.save(grupo);

        GrupoDetalladoDTO grupoDetalladoDTO = new GrupoDetalladoDTO();
        grupoDetalladoDTO.setId(grupoAlmacenado.getId());
        grupoDetalladoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDetalladoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDetalladoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDetalladoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());

        if (grupoDetalladoDTO.getUsuarios() == null) {
            grupoDetalladoDTO.setUsuarios(new ArrayList<>());
        }

        grupoAlmacenado.getUsuarios().forEach(usuario -> {
            grupoDetalladoDTO.getUsuarios().add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail()));
        });
        return grupoDetalladoDTO;
    }

    public List<UsuarioDTO> verParticipantesGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        grupo.getUsuarios().forEach(usuario -> {
            usuarioDTOS.add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail()));
        });
        return usuarioDTOS;
    }


    public void eliminarParticipante(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        if (grupo == null) {
            return;
        }
        Set<Usuario> usuarios = grupo.getUsuarios();
        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                usuarios.remove(usuario);
            }
        }
        grupo.setUsuarios(usuarios);
        Grupo grupoAlmacenado = grupoRepository.save(grupo);
        GrupoDetalladoDTO grupoDetalladoDTO = new GrupoDetalladoDTO();
        grupoDetalladoDTO.setId(grupoAlmacenado.getId());
        grupoDetalladoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDetalladoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDetalladoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDetalladoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());

        if (grupoDetalladoDTO.getUsuarios() == null) {
            grupoDetalladoDTO.setUsuarios(new ArrayList<>());
        }
        grupoAlmacenado.getUsuarios().forEach(usuario -> {
            grupoDetalladoDTO.getUsuarios().add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail()));
        });
    }


    public List<GrupoDTO> listarGrupos(Long idUsuario) {
        List<Grupo> grupos = grupoRepository.findByUsuarios_Id(idUsuario);
        List<GrupoDTO> grupoDTOS = new ArrayList<>();
        for (Grupo grupo : grupos) {
            GrupoDTO grupoDTO = new GrupoDTO();
            grupoDTO.setId(grupo.getId());
            grupoDTO.setNombre(grupo.getNombre());
            grupoDTO.setImagenUrl(grupo.getImagenUrl());
            grupoDTO.setDescripcion(grupo.getDescripcion());
            grupoDTO.setFechaCreacion(grupo.getFechaCreacion());
            grupoDTOS.add(grupoDTO);
        }
        return grupoDTOS;
    }

    public GastoDTO aniadirGasto(Long idGrupo, GastoDTO gastoDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        if (grupo == null) {
            return null;
        }

        Gasto gasto = new Gasto();
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMontoTotal(gastoDTO.getMontoTotal());
        gasto.setFecha(LocalDateTime.now());
        gasto.setTipo(Gasto.TipoGasto.DEUDA);
        gasto.setPagador(usuarioRepository.findById(gastoDTO.getIdPagador()).orElse(null));
        gasto.setGrupo(grupo);
        gastoRepository.save(gasto);
        return gastoDTO;

    }






}
