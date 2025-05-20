package org.ifb.tcc.futdelas.entities;

import jakarta.persistence.Entity;

@Entity
public class Team {
    private String name;
    private String shieldUrl;

    public Team(String name, String shieldUrl) {
        this.name = name;
        this.shieldUrl = shieldUrl;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShieldUrl() {
        return shieldUrl;
    }
    public void setShieldUrl(String shieldUrl) {
        this.shieldUrl = shieldUrl;
    }
}
