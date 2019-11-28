package xpadro.spring.integration.ws.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("ticketRegistry")
public class TicketRegistry {

	private List<String> confirmations = new ArrayList<>();
	
	public void clearRegistry() {
		confirmations.clear();
	}
	
	public void addConfirmation(String confirmation) {
		confirmations.add(confirmation);
	}
	
	public List<String> getConfirmations() {
		return Collections.unmodifiableList(this.confirmations);
	}
}
