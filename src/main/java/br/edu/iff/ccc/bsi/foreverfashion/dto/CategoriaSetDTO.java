package br.edu.iff.ccc.bsi.foreverfashion.dto;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaSetDTO {
    private String descricao;

    public Categoria transformarParaObjeto() {
        Categoria categoria = new Categoria();
        categoria.setDescricao(this.descricao);
        return categoria;
    }
}
