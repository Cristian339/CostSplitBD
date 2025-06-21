package org.example.costsplitbd.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Genera un token JWT para el usuario
     */
    public String generateToken(Usuario usuario){
        TokenDataDTO tokenDataDTO = TokenDataDTO
                .builder()
                .email(usuario.getEmail())
                .rol("USER") // Si tienes roles implementados, usar usuario.getRol().name()
                .fecha_creacion(System.currentTimeMillis())
                .fecha_expiracion(System.currentTimeMillis() + 1000 * 60 * 60 * 24) // 24 horas
                .build();

        return Jwts
                .builder()
                .claim("tokenDataDTO", tokenDataDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractDatosToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenDataDTO extractTokenData(String token){
        Claims claims = extractDatosToken(token);
        Map<String, Object> mapa = (LinkedHashMap<String,Object>) claims.get("tokenDataDTO");
        return TokenDataDTO.builder()
                .email((String) mapa.get("email"))
                .fecha_creacion((Long) mapa.get("fecha_creacion"))
                .fecha_expiracion((Long) mapa.get("fecha_expiracion"))
                .rol((String) mapa.get("rol"))
                .build();
    }

    /**
     * Verifica si el token ha expirado
     */
    public boolean isExpired(String token){
        return new Date(extractTokenData(token).getFechaExpiracion()).before(new Date());
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Usuario extraerUsuarioToken(String token){
        String tokenSinCabecera = token.substring(7);
        TokenDataDTO tokenDataDTO = extractTokenData(tokenSinCabecera);
        // Ajusta esto según tu servicio
        return usuarioService.getUsuarioByEmail(tokenDataDTO.getEmail());
    }
}