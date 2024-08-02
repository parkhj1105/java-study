package reservationsystem;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Test {

	public static void main(String[] args) {



		String EMAIL_PATTERN = "^(070|080|090)+-\\d{4}+-\\d{4}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher("080-2121-4318");
        System.out.println(matcher.matches());

//		"^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
//		"^(070|080|090)+-\\d{4}+-\\d{4}$";
//      dは整数のことを指す。

        // email
        // Gmail SMTP 서버 정보 설정
        String host = "smtp.gmail.com";
        final String username = "testaka240802@gmail.com"; // 발신자 Gmail 주소
        final String password = "zdmk zhvr ncol qlwp"; // 발신자 Gmail 비밀번호

        // 수신자 이메일
        String toEmail = "testaka240802@gmail.com";

        // SMTP 서버 설정
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // 세션 생성
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // 이메일 메시지 구성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("会員登録ありがとうございました！");
            message.setText("会員登録が成功的に行われました！" + "\n" +
            				"ありがとうございます！");

            // 이메일 전송
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}