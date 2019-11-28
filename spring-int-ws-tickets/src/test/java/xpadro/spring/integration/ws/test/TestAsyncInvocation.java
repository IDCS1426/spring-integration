package xpadro.spring.integration.ws.test;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xpadro.spring.integration.ws.gateway.AsyncTicketService;
import xpadro.spring.integration.ws.registry.TicketRegistry;
import xpadro.spring.integration.ws.types.TicketRequest;
import xpadro.spring.integration.ws.utils.DateUtils;

@ContextConfiguration({"classpath:xpadro/spring/integration/ws/test/config/int-ws-async-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAsyncInvocation {
	
	@Autowired
	private TicketRegistry registry;
	
	@Autowired
	private AsyncTicketService service;
	
	@Before
	public void setUp() {
		registry.clearRegistry();
	}
	
	@Test
	public void testInvocation() throws InterruptedException, ExecutionException {
		TicketRequest request = new TicketRequest();
		request.setFilmId("aFilm");
		request.setQuantity(new BigInteger("3"));
		request.setSessionDate(DateUtils.convertDate(new Date()));
		
		service.invoke(request);
		Thread.sleep(10000);
		Assert.assertEquals(1, registry.getConfirmations().size());
		Assert.assertEquals("aFilm-5", registry.getConfirmations().get(0));
	}
}
