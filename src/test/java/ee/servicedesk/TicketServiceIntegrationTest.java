package ee.servicedesk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ee.servicedesk.dao.TicketDao;
import ee.servicedesk.model.Ticket;
import ee.servicedesk.model.TicketPriority;
import ee.servicedesk.service.TicketService;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TicketServiceIntegrationTest {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private TicketDao ticketDao;

	/**
	 * Assert clean DAO before each test
	 */
	@Before
	public void cleanDAO() {
		ticketDao.deleteAll();
	}

	@Test
	public void given_properTicket_expect_addedTicketWithIdAndCreatedDate() {

		Ticket ticket = createTestTicket();
		Ticket savedTicket = ticketService.create(ticket);

		assertThat("ID must be set on persist", savedTicket.getId(), notNullValue());
		assertThat("Created date must be set on persist", savedTicket.getCreatedTime(), notNullValue());
		assertThat(ticket.getTitle(), equalTo(savedTicket.getTitle()));
		assertThat(ticket.getDescription(), equalTo(savedTicket.getDescription()));
		assertThat(ticket.getEmail(), equalTo(savedTicket.getEmail()));
		assertThat(ticket.getPriority(), equalTo(savedTicket.getPriority()));
		assertThat(ticket.getStatus(), equalTo(savedTicket.getStatus()));
	}

	@Test
	public void given_existingTicket_expect_updatedOnlyUpdatableFields() {

		Ticket savedTicket = ticketService.create(createTestTicket());

		Ticket ticket = createTestTicket();
		ticket.setId(savedTicket.getId());
		ticket.setCreatedTime(null);
		ticket.setTitle("Changed title");
		ticket.setDescription("Changed description");
		ticket.setEmail("Changed.email@mail.ee");

		Ticket updatedTicket = ticketService.update(ticket);

		assertThat("Update date must be set on persist", updatedTicket.getUpdatedTime(), notNullValue());
		assertThat("Created date must not be changed on update", updatedTicket.getCreatedTime(), equalTo(savedTicket.getCreatedTime()));
		assertThat(ticket.getTitle(), equalTo(updatedTicket.getTitle()));
		assertThat(ticket.getDescription(), equalTo(updatedTicket.getDescription()));
		assertThat(ticket.getEmail(), equalTo(updatedTicket.getEmail()));
		assertThat(ticket.getPriority(), equalTo(updatedTicket.getPriority()));
		assertThat(ticket.getStatus(), equalTo(updatedTicket.getStatus()));
	}

	private static Ticket createTestTicket() {
		Ticket ticket = new Ticket();
		ticket.setTitle("Test title");
		ticket.setDescription("Description");
		ticket.setEmail("a@b.com");
		ticket.setPriority(TicketPriority.MINOR);
		return ticket;
	}
}
