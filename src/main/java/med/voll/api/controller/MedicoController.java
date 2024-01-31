package med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @PostMapping
    public void cadastrar(@RequestBody String msg){
        System.out.println(msg);
    }


//    Nome
//    E-mail
//    Telefone
//    CRM
//    Especialidade (Ortopedia, Cardiologia, Ginecologia ou Dermatologia)
//    Endereço completo (logradouro, número, complemento, bairro, cidade, UF e CEP)

}
