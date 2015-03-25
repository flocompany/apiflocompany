package com.flocompany.util;

import java.io.IOException;
import java.util.List;

import com.flocompany.rest.model.MessageAEnvoyerDTO;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import static com.flocompany.util.RestUtil.GCM_KEY_SERVER;

public class NotificationUtil {

	
	public static int send(List<String> regIdDevices, MessageAEnvoyerDTO message) throws IOException {

		
		Message androidMessage = new Message.Builder()
		.timeToLive(5)
		.addData("id", message.getId())
		.addData("titre", message.getTitle())
		.addData("dateCreation", message.getDateCreation())
		.addData("texte", message.getBody())
		.build();
		
		
		Sender sender = new Sender(GCM_KEY_SERVER); 

		MulticastResult resultat = sender.send(androidMessage, regIdDevices, 3);
		
		int result = resultat.getTotal();
		return result;
	}
	
}
