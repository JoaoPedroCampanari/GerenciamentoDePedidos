package br.com.xablau.email_api.serviceEmail;


import br.com.xablau.email_api.entity.ClienteDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final JavaMailSender javaMailSender;
    private static final String FROM_EMAIL = "pedidos-api@company.com";

    public ClienteService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void enviarEmail(String email, String mensagem, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(FROM_EMAIL);
        simpleMailMessage.setText(mensagem);

        javaMailSender.send(simpleMailMessage);
    }


    public void enviarEmailClienteCriado(ClienteDto clienteDto) {
        String mensagem = gerarMensagemClienteCriado(clienteDto);
        enviarEmail(clienteDto.email(), mensagem, "Cliente cadastrado");
    }

    public void enviarEmailClienteEmailError(ClienteDto clienteDto) {
        String mensagem = gerarMensagemEmailError(clienteDto);
        enviarEmail(clienteDto.email(), mensagem, "Email já possui cadastro");
    }

    public void enviarEmailClientePedidoCriado(ClienteDto clienteDto) {
        String mensagem = gerarMensagemPedidoCriado(clienteDto);
        enviarEmail(clienteDto.email(), mensagem, "Pedido realizado com sucesso");
    }

    public void enviarEmailClientePedidoSaldoInsuficiente(ClienteDto clienteDto) {
        String mensagem = gerarMensagemPedidoSaldoInsuficiente(clienteDto);
        enviarEmail(clienteDto.email(), mensagem, "Cliente cadastrado");
    }

    public String gerarMensagemClienteCriado(ClienteDto clienteDto){
        return String.format("Olá %s, seu cadastrado foi efetuado com sucesso no email %s," +
                " seu saldo no aplicativo atualmente é: %.2f",
                clienteDto.nome(), clienteDto.email(), clienteDto.saldoAplicativo());
    }

    public String gerarMensagemEmailError(ClienteDto clienteDto){
        return String.format("Olá %s, seu email %s já possui cadastro em nosso aplicativo", clienteDto.nome(), clienteDto.email());
    }


    public String gerarMensagemPedidoCriado(ClienteDto clienteDto){
        return String.format("Olá %s, seu pedido foi criado com sucesso e se encontra em preparo!," +
                " seu saldo atual é %.2f:", clienteDto.nome(), clienteDto.saldoAplicativo());
    }

    public String gerarMensagemPedidoSaldoInsuficiente(ClienteDto clienteDto){
        return String.format("Carrísimo cliente %s, " +
                "infelizmente seu saldo %.2f é insuficiente para continuar com o pedido", clienteDto.nome(), clienteDto.saldoAplicativo());
    }



}
