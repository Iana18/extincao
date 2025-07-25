package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.AuthenticationDTO;
import plataforma.exticao.dtos.RegisterDTO;
import plataforma.exticao.model.UserRole;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.seguranca.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@CrossOrigin("*")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (userRepository.findByLogin(dto.login()).isPresent()) {
            return ResponseEntity.badRequest().body("Login já existe");
        }

        // Verifica se já existe algum usuário com role ADMIN
        boolean adminExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getRole() == UserRole.ADMIN);

        // Se já existe um admin, força o novo usuário a ser USER
        UserRole role = adminExists ? UserRole.USER : UserRole.ADMIN;

        Usuario usuario = new Usuario(dto.login(), passwordEncoder.encode(dto.password()), role);
        userRepository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }


    //@CrossOrigin("*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
            authenticationManager.authenticate(authToken);

            Usuario usuario = userRepository.findByLogin(dto.login()).get();
            String token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }

}




