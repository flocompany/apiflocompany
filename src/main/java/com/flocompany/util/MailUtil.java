package com.flocompany.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

public class MailUtil {
	
	
	
	private List<String> to = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private List<String> cci = new ArrayList<String>();
	private String from;
	private String subject;
	private String message;
	
	
	public MailUtil(final List<String> to, final List<String> cc, final List<String> cci, final String from, final String subject, final String message) throws MessagingException, UnsupportedEncodingException {
	 setTo(to);
     setFrom(from);
	 setSubject(subject);
	 setMessage(message);
	}
	
	
	/** Envoie plusieurs mails
	 * @param mails
	 * @throws NamingException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public void envoieMails() throws NamingException, MessagingException, UnsupportedEncodingException {
		 Properties props = new Properties();
	     Session session = Session.getDefaultInstance(props, null);

	     try {
	         Message msg = new MimeMessage(session);
	         try {
				msg.setFrom(new InternetAddress(from, "Administrator"));
				for(String mailto : getTo()){
					msg.addRecipient(Message.RecipientType.TO,
			                  new InternetAddress(mailto, mailto));
				}
			} catch (UnsupportedEncodingException e) {
				throw e;
			}
	         msg.setSubject(getSubject());
	         msg.setText(getMessage());
	         Transport.send(msg);

	     } catch (AddressException e) {
	         System.out.println("bad adress");
	         throw e;
	     } catch (MessagingException e) {
	         System.out.println("bad message");
	         throw e;
	     }

	}


	public String getFrom() {
		return this.from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public List<String> getTo() {
		return this.to;
	}

	public void setTo(final List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return this.cc;
	}

	public void setCc(final List<String> cc) {
		this.cc = cc;
	}

	public List<String> getCci() {
		return this.cci;
	}

	public void setCci(final List<String> cci) {
		this.cci = cci;
	}
	
}
