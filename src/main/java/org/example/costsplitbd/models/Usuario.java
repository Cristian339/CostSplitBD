package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa un usuario en la aplicación de división de costos.
 * Esta entidad se utiliza para gestionar la información de los usuarios.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario", schema = "costsplit")
public class Usuario implements UserDetails {
    /**
     * El identificador único para el usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El nombre del usuario.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Los apellidos del usuario.
     */
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    /**
     * El correo electrónico del usuario.
     */
    @Column(name = "mail", nullable = false)
    private String email;

    /**
     * La contraseña del usuario.
     */
    @Column(name = "pass", nullable = false)
    private String contrasenia;

    /**
     * La fecha de creación del usuario.
     */
    @Column(name = "fecha_creacion", columnDefinition = "date default current_date")
    private LocalDate fechaCreacion;

    /**
     * Indica si el usuario es administrador.
     */
    @Column(name = "es_admin", nullable = false)
    private boolean esAdmin;

    /**
     * La URL de la imagen del grupo.
     */
    @Column(name = "url_img", nullable = true)
    private String urlImg;

    /**
     * Los amigos del usuario.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "amigo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private Set<Usuario> amigos = new HashSet<>();

    // Implementación de métodos de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (esAdmin) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}