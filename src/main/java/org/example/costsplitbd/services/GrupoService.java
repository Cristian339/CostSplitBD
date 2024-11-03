package org.example.costsplitbd.services;

import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
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
        Grupo grupo = null;

        grupo = grupoRepository.findById(idGrupo).orElse(null);
        Set<Usuario> usuarios = new HashSet<>();
        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            usuarios.add(usuario);
        }
        grupo.setUsuarios(usuarios);
        Grupo grupoAlmacenado = grupoRepository.save(grupo);


        GrupoDetalladoDTO grupoDetalladoDTO = new GrupoDetalladoDTO();
        grupoDetalladoDTO.setId(grupoAlmacenado.getId());
        grupoDetalladoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDetalladoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDetalladoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDetalladoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());
        grupoDetalladoDTO.setUsuarios(new ArrayList<>());

        grupoAlmacenado.getUsuarios().forEach(usuario -> {
            grupoDetalladoDTO.getUsuarios().add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail()));
        });
        return grupoDetalladoDTO;
    }

    public GrupoDetalladoDTO eliminarParticipantes(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = null;

        grupo = grupoRepository.findById(idGrupo).orElse(null);
        Set<Usuario> usuariosActuales = grupo.getUsuarios();

//        Set<Usuario> usuarios = new HashSet<>();
//        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
//            usuariosActuales.remove();
//
//            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
//            usuarios.add(usuario);
//        }
//
//        Set<Usuario> usuariosActuales = grupo.getUsuarios();

         /*ya vengo loco te hablo por mensaje ire a comer, en un rato dejo la idea de delete tendre otra reu en un rato.*/

/*
        grupo.setUsuarios(usuariosRestantes);*/

        Grupo grupoAlmacenado = grupoRepository.save(grupo);


        GrupoDetalladoDTO grupoDetalladoDTO = new GrupoDetalladoDTO();
        grupoDetalladoDTO.setId(grupoAlmacenado.getId());
        grupoDetalladoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDetalladoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDetalladoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDetalladoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());
        grupoDetalladoDTO.setUsuarios(new ArrayList<>());

        grupoAlmacenado.getUsuarios().forEach(usuario -> {
            grupoDetalladoDTO.getUsuarios().add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail()));
        });
        return grupoDetalladoDTO;
    }
}
