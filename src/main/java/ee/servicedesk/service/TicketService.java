package ee.servicedesk.service;

import java.util.List;
import java.util.Optional;

import ee.servicedesk.model.Ticket;
import ee.servicedesk.model.TicketStatus;

/**
 * Service for providing CRUD data management of Tickets.
 *
 * @author Andro Põlluste
 */
public interface TicketService {

    /**
     * Find all tickets with active status: {@link TicketStatus#NEW} or {@link TicketStatus#IN_PROGRESS}
     *
     * @return {@code List} of active tickets or empty list
     */
    List<Ticket> findAllActive();

    /**
     * Find ticket by id.
     *
     * @param ticketId ticket.id
     * @return {@code Optional} of ticket with provided id or {@code Optional.empty()} if not found
     */
    Optional<Ticket> find(long ticketId);

    /**
     * Create ticket with provided data.<br>
     * {@code Ticket.createdDate} is set to current time.
     *
     * @param ticket Ticket to persist.
     * @return created Ticket with created {@code id} and {@code createdDate}.
     */
    Ticket create(Ticket ticket);

    /**
     * Update ticket field values in database. <br>
     * Only changeable fields are updated: id and createdDate
     * are not overwritten from provided ticket. New {@code updatedDate} is set from current time.
     *
     * @param ticket Ticket to update.
     * @return Ticket with updated values.
     */
    Ticket update(Ticket ticket);

    /**
     * Delete ticket by id.
     *
     * @param ticketId Ticket.id to be deleted
     */
    void delete(long ticketId);
}
