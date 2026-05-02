package ModuloRecursosInnovatech.Recursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ModuloRecursosInnovatech.Recursos.model.Categoria;
import ModuloRecursosInnovatech.Recursos.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategoria();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias); 
    }


    @GetMapping("/{id}")

    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.getCategoriaById(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(categoria); 
    }

   
    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        
        categoria.setId(null); 
        Categoria createdCategoria = categoriaService.saveCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        // Verificamos si existe antes de intentar actualizar
        if (categoriaService.getCategoriaById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        categoria.setId(id);
        Categoria updatedCategoria = categoriaService.saveCategoria(categoria);
        return ResponseEntity.ok(updatedCategoria); 
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCateogoria(@PathVariable Integer id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();  
    }
}
