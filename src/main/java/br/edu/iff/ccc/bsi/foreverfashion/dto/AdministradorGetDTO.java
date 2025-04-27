package br.edu.iff.ccc.bsi.foreverfashion.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AdministradorGetDTO {
    private Long id_pessoa;
    private String descricao;
    private String nome;
    private String email;
    private String telefone;
    private Long id_cargo;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String numero;
    private String usuario;
 
    public AdministradorGetDTO transformaParaAdministradorDTO (Administrador administrador) {
        AdministradorGetDTO administradorGetDTO = new AdministradorGetDTO();

        administradorGetDTO.setId_pessoa(administrador.getId_pessoa());
        administradorGetDTO.setDescricao(administrador.getDescricao());
        administradorGetDTO.setNome(administrador.getNome());
        administradorGetDTO.setEmail(administrador.getEmail());
        administradorGetDTO.setTelefone(administrador.getTelefone());
        administradorGetDTO.setId_cargo(administrador.getCargo().getId_cargo());
        administradorGetDTO.setRua(administrador.getEndereco().getRua());
        administradorGetDTO.setBairro(administrador.getEndereco().getBairro());
        administradorGetDTO.setCidade(administrador.getEndereco().getCidade());
        administradorGetDTO.setEstado(administrador.getEndereco().getEstado());
        administradorGetDTO.setCep(administrador.getEndereco().getCep());
        administradorGetDTO.setComplemento(administrador.getEndereco().getComplemento());
        administradorGetDTO.setNumero(administrador.getEndereco().getNumero());
        administradorGetDTO.setUsuario(administrador.getUsuario().getUsuario());

        return administradorGetDTO;
    }

    public List<AdministradorGetDTO> transformaParaAdministradorDTO (List<Administrador> administradores) {
        List<AdministradorGetDTO> administradoresGetDTO = new ArrayList<>();

        for(Administrador administrador : administradores){
            administradoresGetDTO.add(transformaParaAdministradorDTO(administrador));
        }

        return administradoresGetDTO;
    }
}
