package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CargoGetDTO {
    private Long id;
    private String descricao;

    public CargoGetDTO transformaParaCargoDTO(Cargo cargo) {
        CargoGetDTO cargoGetDTO = new CargoGetDTO();
        cargoGetDTO.setId(cargo.getId_cargo());
        cargoGetDTO.setDescricao(cargo.getDescricao());
        return cargoGetDTO;
    }

    public List<CargoGetDTO> transformaParaCargoDTO(List<Cargo> cargos) {
        List<CargoGetDTO> cargosGetDTO = new ArrayList<>();
        for (Cargo cargo : cargos) {
            cargosGetDTO.add(transformaParaCargoDTO(cargo));
        }
        return cargosGetDTO;
    }
}
