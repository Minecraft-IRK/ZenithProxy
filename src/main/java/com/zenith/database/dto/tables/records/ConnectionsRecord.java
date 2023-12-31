/*
 * This file is generated by jOOQ.
 */
package com.zenith.database.dto.tables.records;


import com.zenith.database.dto.enums.Connectiontype;
import com.zenith.database.dto.tables.Connections;
import org.jooq.impl.TableRecordImpl;

import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ConnectionsRecord extends TableRecordImpl<ConnectionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.connections.time</code>.
     */
    public ConnectionsRecord setTime(OffsetDateTime value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.connections.time</code>.
     */
    public OffsetDateTime getTime() {
        return (OffsetDateTime) get(0);
    }

    /**
     * Setter for <code>public.connections.connection</code>.
     */
    public ConnectionsRecord setConnection(Connectiontype value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.connections.connection</code>.
     */
    public Connectiontype getConnection() {
        return (Connectiontype) get(1);
    }

    /**
     * Setter for <code>public.connections.player_name</code>.
     */
    public ConnectionsRecord setPlayerName(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.connections.player_name</code>.
     */
    public String getPlayerName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.connections.player_uuid</code>.
     */
    public ConnectionsRecord setPlayerUuid(UUID value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.connections.player_uuid</code>.
     */
    public UUID getPlayerUuid() {
        return (UUID) get(3);
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConnectionsRecord
     */
    public ConnectionsRecord() {
        super(Connections.CONNECTIONS);
    }

    /**
     * Create a detached, initialised ConnectionsRecord
     */
    public ConnectionsRecord(OffsetDateTime time, Connectiontype connection, String playerName, UUID playerUuid) {
        super(Connections.CONNECTIONS);

        setTime(time);
        setConnection(connection);
        setPlayerName(playerName);
        setPlayerUuid(playerUuid);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised ConnectionsRecord
     */
    public ConnectionsRecord(com.zenith.database.dto.tables.pojos.Connections value) {
        super(Connections.CONNECTIONS);

        if (value != null) {
            setTime(value.getTime());
            setConnection(value.getConnection());
            setPlayerName(value.getPlayerName());
            setPlayerUuid(value.getPlayerUuid());
            resetChangedOnNotNull();
        }
    }
}
