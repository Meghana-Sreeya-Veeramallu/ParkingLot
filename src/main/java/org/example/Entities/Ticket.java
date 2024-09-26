package org.example.Entities;

import java.util.Objects;
import java.util.UUID;

public class Ticket {
    private final String ticketId;

    public Ticket() {
        this.ticketId = UUID.randomUUID().toString();
    }

    public boolean isSameTicket(Ticket ticket) {
        return this.ticketId.equals(ticket.ticketId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Ticket ticket = (Ticket) obj;
        return Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(ticketId);
        return result;
    }
}
