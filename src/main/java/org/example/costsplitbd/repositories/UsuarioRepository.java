package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repositorio para gestionar las operaciones de la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra todos los amigos de un usuario por el ID del usuario.
     *
     * @param idUsuario el ID del usuario
     * @return un conjunto de amigos del usuario
     */
    @Query(value = "SELECT u.* FROM costsplit.amigo a JOIN costsplit.usuario u ON a.amigo_id = u.id WHERE a.usuario_id = ?1", nativeQuery = true)
    Set<Usuario> findAmigosByUsuarioId(Long idUsuario);

    // Buscar usuario por email (para login)
    Optional<Usuario> findByEmail(String email);
}


