package ee.servicedesk.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ee.servicedesk.dao.TicketDao;
import ee.servicedesk.model.Ticket;

/**
 * Implementation class of {@link TicketService}.
 *
 * @author Andro Põlluste
 */
@Service
@Log4j2
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public List<Ticket> findAllActive() {
        return ticketDao.findAllActive();
    }

    @Override
    public Optional<Ticket> find(long ticketId) {
        return ticketDao.findById(ticketId);
    }

    @Override
    public Ticket create(Ticket ticket) {
        Assert.isNull (ticket.getId(), "Ticket id must not exist on creating new ticket!");
        ticket.setCreatedTime(LocalDateTime.now());
        Ticket savedTicket = ticketDao.save(ticket);
        log.info("Created ticket [{}]", savedTicket);
        return savedTicket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        Assert.notNull (ticket.getId(), "Ticket id must be provided on update!");
        Ticket existingTicket = ticketDao.findById(ticket.getId())
                .orElseThrow(() -> new InvalidParameterException("Ticket not found with id " + ticket.getId()));
        Ticket updatedTicket = populateChanges(ticket, existingTicket);
        Ticket savedTicket = ticketDao.save(updatedTicket);
        log.info("Updated ticket from [{}] to [{}]", ticket, savedTicket);
        return savedTicket;
    }

    @Override
    public void delete(long ticketId) {
        ticketDao.deleteById(ticketId);
        log.info("Deleted ticket with id [{}]", ticketId);
    }

    //////////////////////
    // PRIVATE METHODS
    //////////////////////

    /**
     * Overwrite only changeable fields and forbid overwriting data related to ticket creation like ID and createdTime.
     *  Sets updatedTime value to current time.
     *
     * @param ticket Ticket with data to update
     * @param existingTicket Ticket to update data to
     * @return existing ticket with updated data
     */
    private static Ticket populateChanges(Ticket ticket, Ticket existingTicket) {
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setEmail(ticket.getEmail());
        existingTicket.setPriority(ticket.getPriority());
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setUpdatedTime(LocalDateTime.now());
        return existingTicket;
    }

}
