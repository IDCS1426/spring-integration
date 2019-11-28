package xpadro.spring.integration.ws.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xpadro.spring.integration.ws.gateway.TicketService;
import xpadro.spring.integration.ws.types.TicketRequest;
import xpadro.spring.integration.ws.types.TicketResponse;
import xpadro.spring.integration.ws.utils.DateUtils;

@ContextConfiguration({"classpath:xpadro/spring/integration/ws/test/config/int-ws-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInvocation {
	
	@Autowired
	private TicketService service;
	
	@Test
	public void testInvocation() throws InterruptedException, ExecutionException {
		TicketRequest request = new TicketRequest();
		request.setFilmId("aFilm");
		request.setQuantity(new BigInteger("3"));
		request.setSessionDate(DateUtils.convertDate(new Date()));
		
		TicketResponse response = service.invoke(request);
		
		assertNotNull(response);
		assertEquals("aFilm", response.getFilmId());
		assertEquals(new BigInteger("5"), response.getQuantity());
	}
}
