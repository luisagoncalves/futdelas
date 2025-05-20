package org.ifb.tcc.futdelas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class Match {
    private LocalDateTime dateHour;
    private Team homeTeam;
    private Team visitantTeam;
    private Scoreboard scoreboard;
    private Championship championship;

    public Match(LocalDateTime dateHour, Team homeTeam, Team visitantTeam, Scoreboard scoreboard, Championship championship) {
        this.dateHour = dateHour;
        this.homeTeam = homeTeam;
        this.visitantTeam = visitantTeam;
        this.scoreboard = scoreboard;
        this.championship = championship;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }
    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }
    public Team getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    public Team getVisitantTeam() {
        return visitantTeam;
    }
    public void setVisitantTeam(Team visitantTeam) {
        this.visitantTeam = visitantTeam;
    }
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
    public Championship getChampionship() {
        return championship;
    }
    public void setChampionship(Championship championship) {
        this.championship = championship;
    }
    
}
