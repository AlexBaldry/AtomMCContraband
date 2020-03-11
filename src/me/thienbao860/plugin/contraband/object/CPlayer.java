package me.thienbao860.plugin.contraband.object;

import java.util.UUID;

public class CPlayer {

    private UUID uuid;
    private int cont;

    public CPlayer(UUID uuid, int cont) {
        this.uuid = uuid;
        this.cont = cont;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }
}
