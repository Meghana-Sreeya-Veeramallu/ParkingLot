package org.example.Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    // Tests for Ticket class
    @Test
    public void testTicket() {
        Ticket ticket = new Ticket();

        assertNotNull(ticket);
    }

    // Tests for isSameTicket() method
    @Test
    public void testIsSameTicketIfTicketsAreSame() {

        Ticket ticket = new Ticket();
        assertTrue(ticket.isSameTicket(ticket));
    }

    @Test
    public void testIsSameTicketIfTicketsAreDifferent() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        
        assertFalse(ticket1.isSameTicket(ticket2));
    }

}