package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.service.EstatisticaService;

import java.util.Map;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    // Número total de denúncias
    @GetMapping("/denuncias/total")
    public ResponseEntity<Long> totalDenuncias() {
        return ResponseEntity.ok(estatisticaService.contarDenuncias());
    }

    // Número de denúncias por status
    @GetMapping("/denuncias/por-status")
    public ResponseEntity<Map<String, Long>> denunciasPorStatus() {
        return ResponseEntity.ok(estatisticaService.contarDenunciasPorStatus());
    }

    // Número total de seres
    @GetMapping("/seres/total")
    public ResponseEntity<Long> totalSeres() {
        return ResponseEntity.ok(estatisticaService.contarSeres());
    }

    // Número de seres por status de conservação
    @GetMapping("/seres/por-status")
    public ResponseEntity<Map<String, Long>> seresPorStatusConservacao() {
        return ResponseEntity.ok(estatisticaService.contarSeresPorStatus());
    }

    // Número total de usuários
    @GetMapping("/usuarios/total")
    public ResponseEntity<Long> totalUsuarios() {
        return ResponseEntity.ok(estatisticaService.contarUsuarios());
    }
}
