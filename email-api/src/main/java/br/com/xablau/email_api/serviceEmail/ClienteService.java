package br.com.xablau.email_api.serviceEmail;

import br.com.xablau.dtos.ClienteDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final JavaMailSender javaMailSender;

    public ClienteService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmailClienteCriado(ClienteDto clienteDto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Cliente cadastrado");
        simpleMailMessage.setTo(clienteDto.getEmail());
        simpleMailMessage.setFrom("pedidos-api@company.com");
        simpleMailMessage.setText(this.gerarMensagem(clienteDto));

        javaMailSender.send(simpleMailMessage);
    }

    public String gerarMensagem(ClienteDto clienteDto){
        return String.format("Olá %s, seu cadastrado foi efetuado com sucesso no email %s, seu saldo no aplicativo atualmente é: %.2f", clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getSaldoAplicativo());
    }
}
