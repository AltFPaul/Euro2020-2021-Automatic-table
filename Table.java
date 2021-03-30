package bababooey;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Table {

    private Table instance = this;

    private final char GroupID;

    private final Team TEAM1;
    private final Team TEAM2;
    private final Team TEAM3;
    private final Team TEAM4;

    private Team[] teams = new Team[4];

    public Team[] startingPos = new Team[4];


    public Table(char ID, String team1, String team2, String team3, String team4){
        this.GroupID = ID;
        this.TEAM1 = new Team(team1, this.GroupID);
        this.TEAM2 = new Team(team2, this.GroupID);
        this.TEAM3 = new Team(team3, this.GroupID);
        this.TEAM4 = new Team(team4, this.GroupID);

        teams[0] = TEAM1;
        teams[1] = TEAM2;
        teams[2] = TEAM3;
        teams[3] = TEAM4;

        startingPos[0] = TEAM1;
        startingPos[1] = TEAM2;
        startingPos[2] = TEAM3;
        startingPos[3] = TEAM4;
    }

    public void setScore(String team1, String team2, int goals1, int goals2) throws IOException, TeamNorFoundException {
        BufferedWriter writer = writer();
        String[] score = null;
        try {
            score = findResult(getTeamByName(team1), getTeamByName(team2));
        }
        catch (Exception e){

        }
        if(score == null)
            writer.write("Group " + GroupID + " " + team1 + " vs " + team2 + " score: " + goals1 + " - " + goals2 + "\n");
        else{
            System.out.println("Result exists");
            int i = 0;
            Scanner read = read();
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

        writer.close();
        reset();
        updateScore();
    }

    public String[] findResult(Team one, Team two) throws FileNotFoundException {
        Scanner read = read();
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

    public void updateScore() throws FileNotFoundException, TeamNorFoundException {
        Scanner read = read();
        while(read.hasNextLine()){
            String line = read.nextLine();
            if(line.charAt(6) == GroupID){
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
                int goal1 = Integer.parseInt(goals1);
                int goal2 = Integer.parseInt(goals2);

                Team Nation1 = null;
                Team Nation2 = null;

                for(int teamIndex = 0; teamIndex < teams.length; teamIndex++){
                    Team team = teams[teamIndex];
                    if(team.getNAME().equals(team1)){
                        Nation1 = team;
                        teams[teamIndex] = Nation1;
                    }
                    else if(team.getNAME().equals(team2)){
                        Nation2 = team;
                        teams[teamIndex] = Nation2;
                    }
                }

                if(Nation1 == null || Nation2 == null)
                    throw new TeamNorFoundException();

                Nation1.setGoals(goal1);
                Nation1.setGoalDiff(goal1 - goal2);
                Nation2.setGoals(goal2);
                Nation2.setGoalDiff(goal2 - goal1);

                if(goal1 > goal2) {
                    Nation1.setScore(3);
                    Nation1.setWins();
                }
                else if(goal1 < goal2) {
                    Nation2.setScore(3);
                    Nation2.setWins();
                }
                else{
                    Nation1.setScore(1);
                    Nation2.setScore(1);
                }
            }
        }
        Arrays.sort(teams);
        read.close();
    }

    public void reset(){
        for(Team team: teams){
            team.reset();
        }
    }

    public Team winner(){
        return teams[0];
    }

    public Team runnerUp(){
        return teams[1];
    }

    public Team third(){
        return teams[2];
    }

    public Team fourth(){return teams[3];}

    public char getGroupID(){return this.GroupID;}

    public Team getTeamByName(String name){
        for(Team team: startingPos){
            if(team.getNAME().equals(name)) return team;
        }
        return null;
    }


    public static BufferedWriter writer() throws IOException {
        FileWriter file = new FileWriter(, true);
        BufferedWriter writer = new BufferedWriter(file);
        return writer;
    }

    public static Scanner read() throws FileNotFoundException {
        File file = new File();
        Scanner read = new Scanner(file);
        return read;
    }



}
