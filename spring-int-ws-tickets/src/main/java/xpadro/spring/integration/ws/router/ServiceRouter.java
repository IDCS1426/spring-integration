package xpadro.spring.integration.ws.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;

@Component("serviceRouter")
public class ServiceRouter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private int maxRetries = 3;
	private int currentRetries;
	
	public String handleServiceError(Message<?> msg) {
		logger.info("Handling service failure");
		
		if (maxRetries > 0) {
			currentRetries++;
			if (currentRetries > maxRetries) {
				logger.info("Max retries [{}] reached", maxRetries);
				return "failedChannel"; 
			}
		}

		logger.info("Retry number {} of {}", currentRetries, maxRetries);
		return "retryChannel";
	}

}
