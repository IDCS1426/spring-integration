package xpadro.spring.integration.ws.interceptor;


import javax.xml.transform.dom.DOMSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.w3c.dom.Node;

public class MyInterceptor implements ClientInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		WebServiceMessage message = messageContext.getRequest();
		DOMSource source = (DOMSource) message.getPayloadSource();

		Node quantityNode = source.getNode().getAttributes().getNamedItem("quantity");
		String oldValue = quantityNode.getNodeValue();
		quantityNode.setNodeValue("5");

		logger.info("Before endpoint invocation. Changed quantity old value {} for {}", oldValue, 5);
		
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		logger.info("endpoint invocation succeed");
		
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		logger.info("endpoint returned a fault");
		
		return true;
	}
}
