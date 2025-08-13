package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.service.DenunciaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping(value = "/registrar", consumes = {"multipart/form-data"})
    public ResponseEntity<Denuncia> registrarDenuncia(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam(required = false) Long especieId,
            @RequestParam String denunciadoPorId,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(value = "imagem", required = false) MultipartFile imagemFile
    ) {
        Denuncia novaDenuncia = denunciaService.registrarMultipart(
                titulo,
                descricao,
                especieId,
                denunciadoPorId,
                latitude,
                longitude,
                imagemFile
        );
        return ResponseEntity.ok(novaDenuncia);
    }

    @GetMapping
    public ResponseEntity<List<Denuncia>> listarTodas() {
        return ResponseEntity.ok(denunciaService.listarTodas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Denuncia>> listarPendentes() {
        return ResponseEntity.ok(denunciaService.listarPendentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> buscarPorId(@PathVariable Long id) {
        Optional<Denuncia> denunciaOpt = denunciaService.buscarPorId(id);
        return denunciaOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Denuncia> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusAprovacao novoStatus,
            Authentication authentication
    ) {
        String usuarioId = authentication.getName(); // Assumindo que o username é o ID do usuário
        try {
            Optional<Denuncia> denunciaAtualizada = denunciaService.atualizarStatus(id, novoStatus, usuarioId);
            return denunciaAtualizada
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDenuncia(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String usuarioId = authentication.getName();
        boolean deletado = denunciaService.deletar(id, usuarioId);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
