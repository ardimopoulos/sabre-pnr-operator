package com.sabre.pnrretriever;

import com.sabre.pnrretriever.config.properties.HeaderProperties;
import com.sabre.pnrretriever.handlers.SessionCreateHandler;
import com.sabre.pnrretriever.headers.message_header.Action;
import com.sabre.pnrretriever.responses.Response;
import com.sabre.pnrretriever.rest.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PnrRetrieverApplication implements CommandLineRunner {

	@Autowired
	private SessionCreateHandler sessionCreateHandler;

	@Autowired
	HeaderProperties headerProperties;

	@Autowired
	ApiResponse apiResponse;

	public static void main(String[] args) {
		SpringApplication.run(PnrRetrieverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        System.out.println(Action.SESSION_CREATE.getValue());
		Response sessionCreateResponse = sessionCreateHandler.processRequest();
		System.out.println(sessionCreateResponse);

		apiResponse.getApiResponse().put("session-create", sessionCreateResponse);
		apiResponse.getApiResponse().put("sabre-command", sessionCreateResponse);
		apiResponse.getApiResponse().put("session-close", sessionCreateResponse);
//		apiResponse.setSessionCreateResponse(sessionCreateResponse);
	}

}
