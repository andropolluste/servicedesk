package ee.servicedesk.model;

import lombok.Getter;

/**
 * Ticket priority levels with numeric priority value.
 */
@Getter
public enum TicketPriority {

    MINOR(10),
    SMALL(20),
    MEDIUM(30),
    CRITICAL(40),
    IMPEDIMENT(50);

    /**
     * Value to sort priorities by. Keep two-place number to allow smoother
     * adding of new values in future if required.
     */
    private final int sortValue;

    TicketPriority(int sortValue) {
        this.sortValue = sortValue;
    }
}
