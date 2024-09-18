//package com.example.demo.services;
//
//import com.example.demo.controllers.EmailHandlerController;
//import com.example.demo.data.EmailHandlerVO;
//import com.example.demo.exceptions.RequiredObjectIsNullException;
//import com.example.demo.model.EmailHandler;
//import com.example.demo.repositories.EmailHandlerRepository;
//import com.example.demo.repositories.mapper.DozerMapper;
//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.PagedModel;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Service
//public class EmailHandlerServices {
//
//    private Logger logger = Logger.getLogger(EmailHandlerServices.class.getName());
//
//    @Autowired
//    EmailHandlerRepository repository;
//
//    @Autowired
//    PagedResourcesAssembler<EmailHandlerVO> assembler;
//
//    @Value("${sendgrid.api.key}")
//    private String sendGridApiKey;
//
//    public EmailHandlerVO create(EmailHandlerVO emailVO) {
//        if (emailVO == null) throw new RequiredObjectIsNullException();
//
//        logger.info("Creating an email entry and sending email!");
//
//        int emailCode = generateRandomCode();
//
//        String emailContent = "Your verification code is: " + emailCode;
//        sendEmailWithSendGrid(emailVO.getEmailUser(), emailContent);
//
//        Timestamp sendDate = new Timestamp(System.currentTimeMillis());
//
//        Timestamp returnDate = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));
//
//        EmailHandler emailHandler = DozerMapper.parseObject(emailVO, EmailHandler.class);
//        emailHandler.setEmailCode(emailCode);
//        emailHandler.setSendDate(sendDate);
//        emailHandler.setReturnDate(returnDate);
//        emailHandler.setEmailUser(emailHandler.getEmailUser());
//        emailHandler.setCpfDep(emailHandler.getCpfDep());
//
//        emailHandler = repository.save(emailHandler);
//
//        EmailHandlerVO vo = DozerMapper.parseObject(emailHandler, EmailHandlerVO.class);
//        vo.add(linkTo(methodOn(EmailHandlerController.class).findById(emailHandler.getEmailCode())).withSelfRel());
//
//        return vo;
//    }
//
//    private void sendEmailWithSendGrid(String toEmail, String content) {
//        Email from = new Email("pip.personal.identifier@gmail.com");
//        String subject = "Seu código de verificação!";
//        Email to = new Email(toEmail);
//        Content emailContent = new Content("text/plain", content);
//        Mail mail = new Mail(from, subject, to, emailContent);
//
//        SendGrid sg = new SendGrid(sendGridApiKey);
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            logger.info("Email sent. Status Code: " + response.getStatusCode());
//        } catch (IOException ex) {
//            logger.severe("Error sending email: " + ex.getMessage());
//        }
//    }
//
//    private int generateRandomCode() {
//        return (int) (Math.random() * 900000) + 100000;
//    }
//
//    public PagedModel<EntityModel<EmailHandlerVO>> findAll(Pageable pageable) {
//        logger.info("Finding all emails!");
//
//        var emailPage = repository.findAll(pageable);
//
//        var emailVosPage = emailPage.map(p -> {
//            EmailHandlerVO vo = DozerMapper.parseObject(p, EmailHandlerVO.class);
//            vo.add(linkTo(methodOn(EmailHandlerController.class).findById(vo.getKey())).withSelfRel());
//            return vo;
//        });
//
//        Link link = linkTo(methodOn(EmailHandlerController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString())).withSelfRel();
//
//        return assembler.toModel(emailVosPage, link);
//    }
//
//    public EmailHandlerVO findById(Integer id) {
//        logger.info("Finding an email!");
//
//        EmailHandler entity = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
//
//        EmailHandlerVO vo = DozerMapper.parseObject(entity, EmailHandlerVO.class);
//
//        vo.add(linkTo(methodOn(EmailHandlerController.class).findById(id)).withSelfRel());
//
//        return vo;
//    }
//
//    public void deleteByEmail(String emailUser) {
//        logger.info("Deleting all EmailHandler entries for email: " + emailUser);
//        boolean exists = repository.existsByEmailUser(emailUser);
//        if (!exists) {
//            logger.info("No records found for this email: " + emailUser);
//            throw new ResourceNotFoundException("No records found for this email: " + emailUser);
//        }
//        repository.deleteByEmailUser(emailUser);
//    }
//
//
//    public boolean verifyEmailCode(String email, int code) {
//        logger.info("Verifying an email code for: " + email);
//
//        EmailHandler entity = repository.findByEmailUserAndEmailCode(email, code)
//                .orElseThrow(() -> new ResourceNotFoundException("No records found for this email and code!"));
//
//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        if (entity.getReturnDate() != null && currentTime.before(entity.getReturnDate())) {
//            deleteByEmail(entity.getEmailUser());
//            return true;
//        } else {
//            throw new ResourceNotFoundException("Code expired or not valid!");
//        }
//    }
//}
