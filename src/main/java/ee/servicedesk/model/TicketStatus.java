package ee.servicedesk.model;

/**
 * Lists suitable statuses for tickets.
 *
 * @author Andro Põlluste
 */
public enum TicketStatus {

    /** Ticket is created but not being actively dealt with */
    NEW,
    /** Ticket is being actively dealt with */
    IN_PROGRESS,
    /** Ticket has been closed */
    CLOSED

}
