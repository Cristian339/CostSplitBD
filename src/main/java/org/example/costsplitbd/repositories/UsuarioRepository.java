package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT u.* FROM costsplit.amigo a JOIN costsplit.usuario u ON a.amigo_id = u.id WHERE a.usuario_id = ?1", nativeQuery = true)
    Set<Usuario> findAmigosByUsuarioId(Long idUsuario);
}
