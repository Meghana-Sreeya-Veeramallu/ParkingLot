package org.example.Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    // Tests for Ticket class
    @Test
    void testTicket() {
        Ticket ticket = new Ticket();

        assertNotNull(ticket);
    }

    // Tests for isSameTicket() method
    @Test
    void testIsSameTicketIfTicketsAreSame() {

        Ticket ticket = new Ticket();
        assertTrue(ticket.isSameTicket(ticket));
    }

    @Test
    void testIsSameTicketIfTicketsAreDifferent() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        
        assertFalse(ticket1.isSameTicket(ticket2));
    }

}