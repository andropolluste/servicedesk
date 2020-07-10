package ee.servicedesk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ee.servicedesk.model.Ticket;

/**
 * Repository providing data access  to H2 database thru {@link CrudRepository}
 *
 * @author Andro Põlluste
 */
@Repository
public interface TicketDao extends CrudRepository<Ticket, Long> {

    /**
     * Query tickets with only active statuses
     *
     * @return collection of active Tickets
     * @see ee.servicedesk.model.TicketStatus
     */
    @Query("SELECT  t FROM Ticket t WHERE t.status IN ('NEW','IN_PROGRESS')")
    List<Ticket> findAllActive();
}
