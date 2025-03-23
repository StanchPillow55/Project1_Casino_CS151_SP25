// Source code is decompiled from a .class file using FernFlower decompiler.
package casino;

public class Matchup {
   private String sport;
   private String team1;
   private double oddsTeam1;
   private String team2;
   private double oddsTeam2;

   public Matchup(String sport, String team1, double oddsTeam1, String team2, double oddsTeam2) {
      this.sport = sport;
      this.team1 = team1;
      this.oddsTeam1 = oddsTeam1;
      this.team2 = team2;
      this.oddsTeam2 = oddsTeam2;
   }

   public String getSport() {
      return this.sport;
   }

   public String getTeam1() {
      return this.team1;
   }

   public double getOddsTeam1() {
      return this.oddsTeam1;
   }

   public String getTeam2() {
      return this.team2;
   }

   public double getOddsTeam2() {
      return this.oddsTeam2;
   }

   public double getProbabilityTeam1() {
      double p1 = 1.0 / this.oddsTeam1;
      double p2 = 1.0 / this.oddsTeam2;
      return p1 / (p1 + p2);
   }

   public double getProbabilityTeam2() {
      return 1.0 - this.getProbabilityTeam1();
   }
}
