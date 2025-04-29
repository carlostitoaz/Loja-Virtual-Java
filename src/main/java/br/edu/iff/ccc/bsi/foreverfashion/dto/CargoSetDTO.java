package br.edu.iff.ccc.bsi.foreverfashion.dto;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CargoSetDTO {
    private String descricao;

    public Cargo transformaParaObjeto() {
        Cargo cargo = new Cargo();
        cargo.setDescricao(this.descricao);
        return cargo;
    }
}
