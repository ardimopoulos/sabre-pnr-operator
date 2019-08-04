package com.sabre.pnr_operator;

import com.sabre.pnr_operator.handlers.SabreCommandHandler;
import com.sabre.pnr_operator.handlers.SessionCloseHandler;
import com.sabre.pnr_operator.handlers.SessionCreateHandler;
import com.sabre.pnr_operator.responses.Response;
import com.sabre.pnr_operator.rest.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private SessionCreateHandler sessionCreateHandler;
    private SabreCommandHandler sabreCommandHandler;
    private SessionCloseHandler sessionCloseHandler;
    private ApiResponse apiResponse;

    private Response sessionCloseResponse;
    private Response dxStatusResponse;
    private Response dxTransmitResponse;

    public ScheduledTask(SessionCreateHandler sessionCreateHandler, SabreCommandHandler sabreCommandHandler,
                         SessionCloseHandler sessionCloseHandler, ApiResponse apiResponse) {

        this.sessionCreateHandler = sessionCreateHandler;
        this.sabreCommandHandler = sabreCommandHandler;
        this.sessionCloseHandler = sessionCloseHandler;
        this.apiResponse = apiResponse;
    }

    @Scheduled(cron = "${execute.time}")
    void runTask() {
        Response sessionCreateResponse = sessionCreateHandler.processRequest();

        if (sessionCreateResponse.isSuccess()) {
            sabreCommandHandler.setCommand("DX STATUS");
            dxStatusResponse = sabreCommandHandler.processRequest();

            sabreCommandHandler.setCommand("DX TRANSMIT");
            dxTransmitResponse = sabreCommandHandler.processRequest();

            sessionCloseResponse = sessionCloseHandler.processRequest();
        }

        apiResponse.getScheduledTask().put("session-create", sessionCreateResponse);
        apiResponse.getScheduledTask().put("dx-status", dxStatusResponse);
        apiResponse.getScheduledTask().put("dx-transmit", dxTransmitResponse);
        apiResponse.getScheduledTask().put("session-close", sessionCloseResponse);
    }

}
