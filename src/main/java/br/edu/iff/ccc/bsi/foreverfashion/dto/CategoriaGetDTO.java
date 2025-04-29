package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaGetDTO {
    private Long id;
    private String descricao;

    public CategoriaGetDTO transformaParaCategoriaDTO(Categoria categoria) {
        CategoriaGetDTO categoriaGetDTO = new CategoriaGetDTO();
        categoriaGetDTO.setId(categoria.getId_categoria());
        categoriaGetDTO.setDescricao(categoria.getDescricao());
        return categoriaGetDTO;
    }

    public List<CategoriaGetDTO> transformaParaCategoriaDTO(List<Categoria> categorias) {
        List<CategoriaGetDTO> categoriasGetDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasGetDTO.add(transformaParaCategoriaDTO(categoria));
        }
        return categoriasGetDTO;
    }   
}
