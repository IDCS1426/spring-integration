package xpadro.spring.integration.ws.gateway;

import org.springframework.integration.annotation.Gateway;

import xpadro.spring.integration.ws.types.TicketRequest;
import xpadro.spring.integration.ws.types.TicketResponse;

public interface TicketService {

	/**
	 * Entry to the messaging system. All invocations to this method will be intercepted and sent to the SI "system entry" gateway
	 * 
	 * @param request
	 */
	@Gateway
	public TicketResponse invoke(TicketRequest request);
}
