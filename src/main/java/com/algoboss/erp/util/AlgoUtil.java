/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.algoboss.erp.entity.DevEntityPropertyValue;
import com.algoboss.erp.face.BaseBean;
import com.algoboss.erp.face.GenericBean;

/**
 *
 * @author Agnaldo
 */
public class AlgoUtil {
	public static String normalizerName(String name) {
		return Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9]+", "").toLowerCase();
	}

	public static Number toNumber(Object propertyValue) {
		if (propertyValue == null) {
			return null;
		}
		Number obj = null;
		try {
			try {
				obj = Double.valueOf(String.valueOf(propertyValue));
			} catch (Exception e) {
				DecimalFormat df = new DecimalFormat();
				DecimalFormatSymbols dfs = new DecimalFormatSymbols();
				dfs.setDecimalSeparator(BaseBean.getBundle("decimalSeparator", "msg").charAt(0));
				dfs.setGroupingSeparator(BaseBean.getBundle("thousandsSeparator", "msg").charAt(0));
				df.setDecimalFormatSymbols(dfs);
				obj = df.parse(String.valueOf(propertyValue));
				// TODO: handle exception
			}
		} catch (Exception ex) {
			Logger.getLogger(DevEntityPropertyValue.class.getName()).log(Level.SEVERE, null, ex);
		}
		return obj;
	}

	public static String extractName(String normalizedName) {
		return AlgoUtil.normalizerName(normalizedName.substring(normalizedName.indexOf("_") + 1));
	}
	
	public static String escapeURL(String url) {
		try {
			return URLEncoder.encode(String.valueOf(url), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}   
	
	public static String sendEmail(final List<String[]> destinatarios, final String emailRemet, final String nomeRemet, final String assunto, final String corpo, final List<File> files) throws Exception {
		String error = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.algoboss.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("webmaster@algoboss.com", "algoboss1");
				}
			};

			Session session = Session.getInstance(props, auth);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailRemet, nomeRemet));
			for (String[] dest : destinatarios) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(dest[0], dest[1]));
			}

			message.setSubject(assunto);
			// cuida do anexo da mensagem
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpMsg = new MimeBodyPart();
			mbpMsg.setDisposition(Part.INLINE);
			mbpMsg.setText(corpo);
			mp.addBodyPart(mbpMsg);
			if (files != null) {
				for (File file : files) {
					DataSource fds = new FileDataSource(file);
					MimeBodyPart mbp = new MimeBodyPart();
					mbp.setDisposition(Part.ATTACHMENT);
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(fds.getName());
					mp.addBodyPart(mbp);
				}
			}

			message.setContent(mp);

			// message.setContent(corpo, "text/plain");

			Transport.send(message);
			System.out.println("EMAIL ENVIADO!");
		} catch (Throwable ex) {
			Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
			error = ex.getMessage();
		}
		return error;
	}

}
