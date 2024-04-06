package pandeyDanceAcademy.pda_backend.service.inter;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;

public interface EmailSendingServiceInter {
	// Send a simple text email to the desired recipient
	boolean sendSimpleMail(EmailDetails details);

	boolean sendSimpleMailWithHTMLContent(EmailDetails details);

	// Send an email along with an attachment to the desired recipient.
	boolean sendMailWithAttachment(EmailDetails details);

}
