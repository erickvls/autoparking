package br.com.autoparking.service.impl;

import br.com.autoparking.service.EmailService;
import br.com.autoparking.service.exception.EnviarEmailException;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;

    private static final String RECUPERACAO_SUBJECT = "Recuperação de senha";
    private static final String EMAIL_FROM = "Suporte Autoparking";

    @Override
    public void sendMail(String toAddresses) {
        try{
            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(toAddresses.split("[,;]"));
                message.setFrom("SERVICO", EMAIL_FROM);
                message.setSubject(RECUPERACAO_SUBJECT);
                message.setText("Mensagem com senha resetada:", false);
            };
            mailSender.send(preparator);
            logger.info("Email enviado com sucesso para: {} com assunto: {}", toAddresses, RECUPERACAO_SUBJECT);
        }
        catch(MailException ex){
            throw new EnviarEmailException("Não foi possível enviar o email devido a: {}", ex);
        }
    }
}
