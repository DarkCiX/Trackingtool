package com.example.model;

import java.io.IOException;

//import com.google.code.gson
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		String payload = message.getPayload();
//		JSONObject jsonObject = new JSONObject(payload);
		session.sendMessage(new TextMessage(payload));
	}

}