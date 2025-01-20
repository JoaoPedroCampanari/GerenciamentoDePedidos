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

    public void enviarEmailClienteEmailError(ClienteDto clienteDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Email já possui cadastro");
        simpleMailMessage.setTo(clienteDto.getEmail());
        simpleMailMessage.setFrom("pedidos-api@company.com");
        simpleMailMessage.setText(this.gerarMensagemEmailError(clienteDto));

        javaMailSender.send(simpleMailMessage);
    }

    public void enviarEmailClientePedidoCriado(ClienteDto clienteDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Pedido criado com sucesso");
        simpleMailMessage.setTo(clienteDto.getEmail());
        simpleMailMessage.setFrom("pedido-api@company.com");
        simpleMailMessage.setText(this.gerarMensagemPedidoCriado(clienteDto));

        javaMailSender.send(simpleMailMessage);
    }

    public void enviarEmailClientePedidoSaldoInsuficiente(ClienteDto clienteDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Saldo insuficiente");
        simpleMailMessage.setTo(clienteDto.getEmail());
        simpleMailMessage.setFrom("pedido-api@company.com");
        simpleMailMessage.setText(this.gerarMensagemPedidoSaldoInsuficiente(clienteDto));

        javaMailSender.send(simpleMailMessage);
    }

    public String gerarMensagem(ClienteDto clienteDto){
        return String.format("Olá %s, seu cadastrado foi efetuado com sucesso no email %s," +
                " seu saldo no aplicativo atualmente é: %.2f",
                clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getSaldoAplicativo());
    }

    public String gerarMensagemEmailError(ClienteDto clienteDto){
        return String.format("Olá %s, seu email %s já possui cadastro em nosso aplicativo", clienteDto.getNome(), clienteDto.getEmail());
    }


    public String gerarMensagemPedidoCriado(ClienteDto clienteDto){
        return String.format("Olá %s, seu pedido foi criado com sucesso e se encontra em preparo!," +
                " seu saldo atual é %.2f:", clienteDto.getNome(), clienteDto.getSaldoAplicativo());
    }



    public String gerarMensagemPedidoSaldoInsuficiente(ClienteDto clienteDto){
        return String.format("Carrísimo cliente %s, " +
                "infelizmente seu saldo %.2f é insuficiente para continuar com o pedido", clienteDto.getNome(), clienteDto.getSaldoAplicativo());
    }


}
