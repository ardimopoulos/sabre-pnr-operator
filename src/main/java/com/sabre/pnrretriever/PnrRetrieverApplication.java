package com.sabre.pnrretriever;

import com.sabre.pnrretriever.config.properties.HeaderProperties;
import com.sabre.pnrretriever.handlers.SessionCreateHandler;
import com.sabre.pnrretriever.rest.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PnrRetrieverApplication {

	@Autowired
	private SessionCreateHandler sessionCreateHandler;

	@Autowired
	HeaderProperties headerProperties;

	@Autowired
	ApiResponse apiResponse;

	public static void main(String[] args) {
		SpringApplication.run(PnrRetrieverApplication.class, args);
	}


}
