package pandeyDanceAcademy.pda_backend.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.service.inters.EmailSendingService;

@Service
public class EmailSendingServiceImpl implements EmailSendingService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;

	public boolean sendSimpleMail(EmailDetails details) {
		if (isValidEmailAddress(details.getRecipientEmail()) == false)
			return false;
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipientEmail());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			javaMailSender.send(mailMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendMailWithAttachment(EmailDetails details) {
		if (isValidEmailAddress(details.getRecipientEmail()) == false)
			return false;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipientEmail());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());
			if (details.getAttachment() != null) {
				FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
				mimeMessageHelper.addAttachment(file.getFilename(), file);
			}
			javaMailSender.send(mimeMessage);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}

	@Override
	public boolean sendSimpleMailWithHTMLContent(EmailDetails details) {
		if (isValidEmailAddress(details.getRecipientEmail()) == false)
			return false;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipientEmail());
			mimeMessageHelper.setText(details.getMsgBody(), true);
			mimeMessageHelper.setSubject(details.getSubject());
			if (details.getAttachment() != null) {
				FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
				mimeMessageHelper.addAttachment(file.getFilename(), file);
			}
			javaMailSender.send(mimeMessage);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean isValidEmailAddress(String email) {
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			return true;
		} catch (AddressException ex) {
			return false;
		}
	}

}
