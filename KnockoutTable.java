package bababooey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class KnockoutTable {

    private final char GroupID;

    private Team TEAM1;
    private Team TEAM2;

    private Team[] teams;

    public Team[] startingPos;

    public KnockoutTable(char id, Team team1, Team team2)  {
        this.GroupID = id;
        this.TEAM1 = new Team(team1.getNAME(), id);
        this.TEAM2 = new Team(team2.getNAME(), id);

        this.teams = new Team[]{TEAM1, TEAM2};

        this.startingPos = new Team[]{TEAM1, TEAM2};
    }

    public void setScore(String team1, String team2, int goals1, int goals2) throws IOException {
        BufferedWriter writer = Table.writer();
        String[] score = null;
        try {
            score = findResult(TEAM1, TEAM2);
        }
        catch (Exception e){

        }
        if(score == null) {
            writer.write("Group " + GroupID + " " + team1 + " vs " + team2 + " score: " + goals1 + " - " + goals2 + "\n");
        }
        else{
            System.out.println("Result exists");
            int i = 0;
            Scanner read = Table.read();
            String Team1;
            String Team2;
            while(read.hasNextLine()) {
                Team1 = "";
                Team2 = "";
                String line = read.nextLine();
                if (line.charAt(6) == GroupID) {
                    int index = 8;
                    while (line.charAt(index) != ' ') {
                        Team1 += line.charAt(index);
                        index++;
                    }
                    index += 4;
                    while (line.charAt(index) != ' ') {
                        Team2 += line.charAt(index);
                        index++;
                    }
                }
                if((Team1.equals(team1) && Team2.equals(team2)) || (Team1.equals(team2) && Team2.equals(team1)))
                    break;
                i++;
            }
            read.close();
            File file = new File();
            List<String> lines = Files.readAllLines(file.toPath());
            lines.set(i, "Group " + GroupID + " " + team1 + " vs " + team2 + " score: " + goals1 + " - " + goals2);
            Files.write(file.toPath(), lines);
        }
        reset();
        writer.close();
    }

    public String[] findResult(Team one, Team two) throws FileNotFoundException {
        Scanner read = Table.read();
        while(read.hasNextLine()) {
            String line = read.nextLine();
            if (line.charAt(6) == GroupID) {
                int index = 8;
                String team1 = "";
                String team2 = "";
                String goals1 = "";
                String goals2 = "";
                while(line.charAt(index) != ' '){
                    team1 += line.charAt(index);
                    index++;
                }
                index+=4;
                while(line.charAt(index) != ' '){
                    team2 += line.charAt(index);
                    index++;
                }
                index+=8;
                while(line.charAt(index) != ' '){
                    goals1 += line.charAt(index);
                    index++;
                }
                index+=3;
                while(index < line.length()){
                    goals2 += line.charAt(index);
                    index++;
                }
                if(team1.equals(one.getNAME()) && team2.equals(two.getNAME())){
                    return new String[]{goals1, goals2};
                }
                if(team2.equals(one.getNAME()) && team1.equals(two.getNAME()))
                    return new String[]{goals2, goals1};
            }
        }
        return null;
    }

    public Team Winner(){
        return teams[0];
    }

    public boolean has(String one, String two){
        return (one.equals(TEAM1.getNAME()) && two.equals(TEAM2.getNAME()) || (one.equals(TEAM2.getNAME()) && two.equals(TEAM1.getNAME())));
    }

    public void reset(){
        for(Team team: teams){
            team.reset();
        }
    }

    public void setWinner(String winner, String loser){
        Team currentLoser = teams[1];

        if(has(winner, loser)){
            if(winner.equals(currentLoser.getNAME())){
                teams[1] = teams[0];
                teams[0] = currentLoser;
            }
        }
    }

    public void setGoal(int goal1, int goal2){
        TEAM1.setGoals(goal1);
        TEAM2.setGoals(goal2);
    }

    public boolean isTeam1(String one){
        return one.equals(TEAM1.getNAME());
    }

}
