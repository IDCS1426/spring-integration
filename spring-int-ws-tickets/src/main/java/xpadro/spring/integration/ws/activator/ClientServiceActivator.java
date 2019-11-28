package xpadro.spring.integration.ws.activator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import xpadro.spring.integration.ws.registry.TicketRegistry;
import xpadro.spring.integration.ws.types.TicketResponse;

@Component("clientServiceActivator")
public class ClientServiceActivator {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("retryAdapter")
	private AbstractEndpoint retryAdapter;
	
	@Autowired
	private TicketRegistry registry;
	
	private Message<?> message;


	/**
	 * Receives the result from a succesful service invocation. Flow finishes here.
	 * @param msg
	 */
	public void handleServiceResult(Message<TicketResponse> msg) {
		logger.info("service invocation OK");
		TicketResponse response = msg.getPayload();
		registry.addConfirmation(response.getConfirmationId());
		retryAdapter.stop();
	}

	/**
	 * Service invocation failed. Activates trigger to start retries
	 * @param msg
	 */
	public void retryFailedInvocation(Message<?> msg) {
		logger.info("Service invocation failed. Activating retry trigger...");
		MessageHandlingException exc = (MessageHandlingException) msg.getPayload();
		this.message = exc.getFailedMessage();
		retryAdapter.start();
	}

	/**
	 * Retries the failed invocation. Sends the request message to the outbound service gateway
	 * @return
	 */
	public Message<?> retryInvocation() {
		logger.info("Retrying service invocation...");

		return message;
	}

	/**
	 * The service invocation won't succeed. Logs the failed request to the DB and finishes the flow.
	 * @param msg
	 */
	public void handleFailedInvocation(MessageHandlingException exception) {
		logger.info("Failed to succesfully invoke service. Finishing flow.");
		retryAdapter.stop();
	}
}