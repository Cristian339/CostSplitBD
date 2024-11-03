package org.example.costsplitbd;

import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CostSplitBdApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testFindAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for(Usuario u : usuarios) {
            System.out.println(u.getEmail());
            System.out.println(u.getApellidos());
            System.out.println(u.getNombre());
        }
    }
}
