package ModuloRecursosInnovatech.Recursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloRecursosInnovatech.Recursos.model.Categoria;
import ModuloRecursosInnovatech.Recursos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategoria() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }



    
}
