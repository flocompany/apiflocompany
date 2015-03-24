package com.flocompany.util;

import java.io.IOException;

import com.flocompany.rest.model.MessageAEnvoyerDTO;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import static com.flocompany.util.RestUtil.GCM_KEY_SERVER;

public class NotificationUtil {

	
	public static void sendNotification(String regIdTerminal, MessageAEnvoyerDTO message) throws IOException {

		MessageAEnvoyerDTO m = new MessageAEnvoyerDTO("1", "Test", "Body message", "date");
		
		Message androidMessage = new Message.Builder()
		.timeToLive(5)
		.addData("id", m.getId())
		.addData("titre", m.getTitle())
		.addData("dateCreation", m.getDateCreation())
		.addData("texte", m.getBody())
		.build();
		
		
		Sender sender = new Sender(GCM_KEY_SERVER); 

		Result resultat = sender.send(androidMessage, regIdTerminal, 3);

	}
	
}
