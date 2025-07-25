package plataforma.exticao.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.service.DenunciaService;

import java.util.List;



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
        Denuncia nova = denunciaService.registrarMultipart(
                titulo,
                descricao,
                especieId,
                denunciadoPorId,
                latitude,
                longitude,
                imagemFile
        );
        return ResponseEntity.ok(nova);
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
        return denunciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}