package org.example.costsplitbd.services;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.BalanceRepository;
import org.example.costsplitbd.repositories.GastoRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private BalanceRepository balanceRepository;

    /**
     * Crea un nuevo grupo.
     *
     * @param crearGrupoDTO los datos para crear el grupo
     * @return los datos del grupo creado
     */
    public GrupoDTO crearGrupo(@Valid CrearGrupoDTO crearGrupoDTO, Usuario usuarioCreador) throws Exception {
        if (usuarioRepository.findById(usuarioCreador.getId()).isEmpty()) {
            throw new Exception("Usuario creador no encontrado");
        }

        Grupo grupo = new Grupo();
        if (crearGrupoDTO.getNombre() == null || crearGrupoDTO.getNombre().isEmpty()) {
            throw new Exception("No se puede crear un grupo sin nombre");
        } else {
            grupo.setNombre(crearGrupoDTO.getNombre());
        }
        grupo.setImagenUrl(crearGrupoDTO.getImagenUrl());
        grupo.setDescripcion(crearGrupoDTO.getDescripcion());
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>());
        grupo.getUsuarios().add(usuarioCreador);

        if (crearGrupoDTO.getParticipantes() == null || crearGrupoDTO.getParticipantes().isEmpty()) {
            throw new Exception("No se puede crear un grupo sin participantes");
        } else {
            for (Usuario participante : crearGrupoDTO.getParticipantes()) {
                grupo.getUsuarios().add(participante);
            }
        }

        Grupo grupoAlmacenado = grupoRepository.save(grupo);

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setId(grupoAlmacenado.getId());
        grupoDTO.setNombre(grupoAlmacenado.getNombre());
        grupoDTO.setImagenUrl(grupoAlmacenado.getImagenUrl());
        grupoDTO.setDescripcion(grupoAlmacenado.getDescripcion());
        grupoDTO.setFechaCreacion(grupoAlmacenado.getFechaCreacion());

        return grupoDTO;
    }

    /**
     * Añade o elimina participantes de un grupo.
     *
     * @param idGrupo                el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a añadir o eliminar
     * @param esAgregar              indica si se están añadiendo (true) o eliminando (false) participantes
     * @return los datos detallados del grupo actualizado
     */
    private GrupoDetalladoDTO actualizarParticipantes(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO, boolean esAgregar) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        Set<Usuario> usuarios = grupo.getUsuarios();
        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                if (esAgregar) {
                    usuarios.add(usuario);
                } else {
                    usuarios.remove(usuario);
                }
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
            grupoDetalladoDTO.getUsuarios().add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getUrlImg()));
        });
        return grupoDetalladoDTO;
    }

    /**
     * Añade participantes a un grupo.
     *
     * @param idGrupo                el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a añadir
     * @return los datos detallados del grupo actualizado
     */
    public GrupoDetalladoDTO aniadirParticipantes(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        return actualizarParticipantes(idGrupo, aniadirParticipanteDTO, true);
    }

    /**
     * Obtiene la lista de participantes de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de participantes del grupo
     */
/*    public List<UsuarioDTO> verParticipantesGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        grupo.getUsuarios().forEach(usuario -> {
            usuarioDTOS.add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getUrlImg()));
        });
        return usuarioDTOS;
    }*/

    public List<UsuarioDTO> verParticipantesGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        if (grupo == null) {
            throw new RuntimeException("Grupo no encontrado");
        }
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        grupo.getUsuarios().forEach(usuario -> {
            usuarioDTOS.add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getUrlImg()));
        });
        return usuarioDTOS;
    }

    /**
     * Elimina participantes de un grupo.
     *
     * @param idGrupo                el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a eliminar
     */
/*    public void eliminarParticipante(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        actualizarParticipantes(idGrupo, aniadirParticipanteDTO, false);
    }*/

    public void eliminarParticipante(Long idGrupo, AniadirParticipanteDTO aniadirParticipanteDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        for (Long idUsuario : aniadirParticipanteDTO.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            if (!grupo.getUsuarios().contains(usuario)) {
                throw new RuntimeException("Usuario no pertenece al grupo");
            }
            grupo.getUsuarios().remove(usuario);
        }
        grupoRepository.save(grupo);
    }
    /**
     * Lista los grupos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de grupos del usuario
     */
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

    /**
     * Añade un gasto a un grupo.
     *
     * @param gastoDTO los datos del gasto a añadir
     * @return los datos del gasto añadido
     */
    public GastoDTO aniadirGasto(GastoDTO gastoDTO) throws Exception {
        if (gastoDTO.getDescripcion() == null || gastoDTO.getDescripcion().isEmpty()) {
            throw new Exception("La descripción no puede estar vacía");
        }

        if (gastoDTO.getMontoTotal() == null || gastoDTO.getMontoTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("El monto total no puede ser negativo");
        }

        if (gastoDTO.getFecha() == null) {
            throw new Exception("La fecha es inválida");
        }

        Grupo grupo = grupoRepository.findById(gastoDTO.getIdGrupo()).orElse(null);
        if (grupo == null) {
            throw new Exception("El grupo no existe");
        }

        Usuario pagador = usuarioRepository.findById(gastoDTO.getIdPagador()).orElse(null);
        if (pagador == null) {
            throw new Exception("El pagador no existe");
        }

        if (gastoDTO.getTipo() == null || gastoDTO.getTipo().isEmpty()) {
            throw new Exception("El tipo de gasto es inválido");
        }

        int nroParticipantes = grupo.getUsuarios().size();

        Gasto gasto = new Gasto();
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMontoTotal(gastoDTO.getMontoTotal());
        gasto.setFecha(LocalDateTime.now());
        gasto.setPagador(pagador);
        gasto.setGrupo(grupo);
        gasto.setTipo(gastoDTO.getTipo());
        gastoRepository.save(gasto);

        BigDecimal montoPorParticipante = gasto.getMontoTotal().divide(BigDecimal.valueOf(nroParticipantes), 2, RoundingMode.HALF_UP);

        for (Usuario usuario : grupo.getUsuarios()) {
            Balance balance = balanceRepository.findBalanceByGrupoIdAndUsuarioId(grupo.getId(), usuario.getId()).orElse(new Balance());
            balance.setGrupo(grupo);
            balance.setUsuario(usuario);

            if (balance.getImporte() == null) {
                balance.setImporte(BigDecimal.ZERO);
            }

            if (usuario.equals(gasto.getPagador())) {
                balance.setImporte(balance.getImporte().add(gasto.getMontoTotal().subtract(montoPorParticipante)));
            } else {
                balance.setImporte(balance.getImporte().subtract(montoPorParticipante));
            }

            balanceRepository.save(balance);
        }

        return gastoDTO;
    }

    /**
     * Obtiene la lista de balances de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de balances del grupo
     */
    public List<BalanceDTO> verBalances(Long idGrupo) {
        List<Balance> balances = balanceRepository.findBalanceByGrupoId(idGrupo);
        List<BalanceDTO> balanceDTOs = new ArrayList<>();

        for (Balance balance : balances) {
            BalanceDTO balanceDTO = new BalanceDTO();
            balanceDTO.setUsuarioId(balance.getUsuario().getId());
            balanceDTO.setUsuarioNombre(balance.getUsuario().getNombre());
            balanceDTO.setImporte(balance.getImporte());
            balanceDTOs.add(balanceDTO);
        }

        return balanceDTOs;
    }

    /**
     * Obtiene la lista de gastos de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de gastos del grupo
     */
    public List<GastoDTO> verGastos(Long idGrupo) {
        List<Gasto> gastos = gastoRepository.findGastoByGrupoId(idGrupo);
        List<GastoDTO> gastoDTOS = new ArrayList<>();

        for (Gasto gasto : gastos) {
            GastoDTO gastoDTO = new GastoDTO();
            gastoDTO.setIdGrupo(gasto.getGrupo().getId());
            gastoDTO.setDescripcion(gasto.getDescripcion());
            gastoDTO.setMontoTotal(gasto.getMontoTotal());
            gastoDTO.setFecha(gasto.getFecha());
            gastoDTO.setIdPagador(gasto.getPagador().getId());
            gastoDTO.setTipo(gasto.getTipo());
            gastoDTOS.add(gastoDTO);
        }

        return gastoDTOS;
    }

    public GrupoDetalladoDTO verGrupo(Long idGrupo) {
        Optional<Grupo> grupoOptional = grupoRepository.findById(idGrupo);
        if (grupoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            GrupoDetalladoDTO grupoDetalladoDTO = new GrupoDetalladoDTO();
            grupoDetalladoDTO.setId(grupo.getId());
            grupoDetalladoDTO.setNombre(grupo.getNombre());
            grupoDetalladoDTO.setDescripcion(grupo.getDescripcion());
            grupoDetalladoDTO.setImagenUrl(grupo.getImagenUrl());
            grupoDetalladoDTO.setFechaCreacion(grupo.getFechaCreacion());

            List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
            for (Usuario usuario : grupo.getUsuarios()) {
                usuarioDTOList.add(new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getUrlImg()));
            }
            grupoDetalladoDTO.setUsuarios(usuarioDTOList);

            return grupoDetalladoDTO;
        } else {
            throw new RuntimeException("Grupo no encontrado");
        }
    }
}