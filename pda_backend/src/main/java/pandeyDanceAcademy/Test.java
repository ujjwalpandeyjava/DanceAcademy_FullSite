package pandeyDanceAcademy;

import java.time.Duration;
import java.time.Instant;

import pandeyDanceAcademy.pda_backend.service.EmailSendingServiceImpl;

public class Test {
public static void main(String[] args) {
	System.out.println(Instant.now().toString());
	System.out.println(Instant.now().plus(Duration.ofMinutes(120)));
	EmailSendingServiceImpl emailSendingServiceImpl = new EmailSendingServiceImpl();
	System.out.println(emailSendingServiceImpl.isValidEmailAddress("ujjwalpandey.aps@gmal.com"));
}
}
