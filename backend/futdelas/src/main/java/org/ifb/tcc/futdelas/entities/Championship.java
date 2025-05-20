package org.ifb.tcc.futdelas.entities;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Championship {
    private String name;
    private String season;
    private Team winnerTeam;
    private List<Match> matches;
    private List<Team> teams;
    private List<Classification> classification;

    public Championship(String name, String season, Team winnerTeam, List<Match> matches, List<Team> teams, List<Classification> classification) {
        this.name = name;
        this.season = season;
        this.winnerTeam = winnerTeam;
        this.matches = matches;
        this.teams = teams;
        this.classification = classification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Classification> getClassification() {
        return classification;
    }

    public void setClassification(List<Classification> classification) {
        this.classification = classification;
    }

    
}
