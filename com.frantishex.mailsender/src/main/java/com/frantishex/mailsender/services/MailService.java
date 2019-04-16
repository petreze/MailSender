package com.frantishex.mailsender.services;

import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.frantishex.mailsender.enums.MailEnum;
import com.frantishex.mailsender.models.Mail;

@Service
@Transactional
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	private void sendEmail(String email, String subject, String message) throws MessagingException {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(message);
		javaMailSender.send(mailMessage);
	}

	public void saveEmail(String receivingEmail, String subject, String message) {
		Mail email = new Mail();

		email.setReceivingEmail(receivingEmail);
		email.setSubject(subject);
		email.setText(message);

		email.setDate(LocalDate.now());
		email.setStatus(MailEnum.NEW);

		em.persist(email);
	}

	@Scheduled(fixedRate = 15000)
	public void sendEmails(String email, String subject, String message) {
		List<Mail> resultList = em.createQuery("SELECT m FROM mail m WHERE m.status=\"NEW\" order by date", Mail.class)
				.getResultList();
		for (Mail m : resultList) {
			try {

				sendEmail(email, subject, message);
				m.setStatus(MailEnum.SEND);
			} catch (Throwable e) {

				m.setStatus(MailEnum.ERROR);
			}
		}
	}
}