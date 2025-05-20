package org.ifb.tcc.futdelas.entities;

public class Scoreboard {
    private int homeTeamScore;
    private int visitantTeamScore;

    public Scoreboard(int homeTeamScore, int visitantTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.visitantTeamScore = visitantTeamScore;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitantTeamScore() {
        return visitantTeamScore;
    }

    public void setVisitantTeamScore(int visitantTeamScore) {
        this.visitantTeamScore = visitantTeamScore;
    }
}
