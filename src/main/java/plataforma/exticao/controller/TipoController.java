package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.service.TipoService;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public List<Tipo> getAll() {
        return tipoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getById(@PathVariable Long id) {
        return tipoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public Tipo create(@RequestBody Tipo tipo) {
        return tipoService.save(tipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> update(@PathVariable Long id, @RequestBody Tipo tipo) {
        try {
            Tipo updated = tipoService.update(id, tipo);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tipoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
