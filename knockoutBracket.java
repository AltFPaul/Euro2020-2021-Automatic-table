package bababooey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class knockoutBracket {

    KnockoutTable[] RoundOf16;
    KnockoutTable[] Quaters;
    KnockoutTable[] Semi;
    KnockoutTable Final;


    public knockoutBracket(Table[] tables, ThirdPlaceTable table){
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;

        RoundOf16 = new KnockoutTable[8];
        Quaters = new KnockoutTable[4];
        Semi= new KnockoutTable[2];

        KnockoutTable Match1 = new KnockoutTable('8', tables[B].winner(), get4Thirds(thrids(table), tables)[0]);
        RoundOf16[0] = Match1;
        KnockoutTable Match2 = new KnockoutTable('8', tables[A].winner(), tables[C].runnerUp());
        RoundOf16[1] = Match2;
        KnockoutTable Match3 = new KnockoutTable('8', tables[F].winner(), get4Thirds(thrids(table), tables)[3]);
        RoundOf16[2] = Match3;
        KnockoutTable Match4 = new KnockoutTable('8', tables[D].runnerUp(), tables[E].runnerUp());
        RoundOf16[3] = Match4;
        KnockoutTable Match5 = new KnockoutTable('8', tables[E].winner(), get4Thirds(thrids(table), tables)[2]);
        RoundOf16[4] = Match5;
        KnockoutTable Match6 = new KnockoutTable('8', tables[F].runnerUp(), tables[D].winner());
        RoundOf16[5] = Match6;
        KnockoutTable Match7 = new KnockoutTable('8', tables[C].winner(), get4Thirds(thrids(table), tables)[1]);
        RoundOf16[6] = Match7;
        KnockoutTable Match8 = new KnockoutTable('8', tables[A].runnerUp(), tables[B].runnerUp());
        RoundOf16[7] = Match8;

        KnockoutTable Quat1 = new KnockoutTable('4', Match1.Winner(), Match2.Winner());
        Quaters[0] = Quat1;
        KnockoutTable Quat2 = new KnockoutTable('4', Match3.Winner(), Match4.Winner());
        Quaters[1] = Quat2;
        KnockoutTable Quat3 = new KnockoutTable('4', Match5.Winner(), Match6.Winner());
        Quaters[2] = Quat3;
        KnockoutTable Quat4 = new KnockoutTable('4', Match7.Winner(), Match8.Winner());
        Quaters[3] = Quat4;

        KnockoutTable Semi1 = new KnockoutTable('2', Quat1.Winner(), Quat2.Winner());
        Semi[0] = Semi1;
        KnockoutTable Semi2 = new KnockoutTable('2', Quat3.Winner(), Quat4.Winner());
        Semi[1] = Semi2;

        Final = new KnockoutTable('1', Semi1.Winner(), Semi2.Winner());

    }

    public void update8s(Table[] tables, ThirdPlaceTable table){

        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;

        KnockoutTable Match1 = new KnockoutTable('8', tables[B].winner(), get4Thirds(thrids(table), tables)[0]);
        RoundOf16[0] = Match1;
        KnockoutTable Match2 = new KnockoutTable('8', tables[A].winner(), tables[C].runnerUp());
        RoundOf16[1] = Match2;
        KnockoutTable Match3 = new KnockoutTable('8', tables[F].winner(), get4Thirds(thrids(table), tables)[3]);
        RoundOf16[2] = Match3;
        KnockoutTable Match4 = new KnockoutTable('8', tables[D].runnerUp(), tables[E].runnerUp());
        RoundOf16[3] = Match4;
        KnockoutTable Match5 = new KnockoutTable('8', tables[E].winner(), get4Thirds(thrids(table), tables)[2]);
        RoundOf16[4] = Match5;
        KnockoutTable Match6 = new KnockoutTable('8', tables[F].runnerUp(), tables[D].winner());
        RoundOf16[5] = Match6;
        KnockoutTable Match7 = new KnockoutTable('8', tables[C].winner(), get4Thirds(thrids(table), tables)[1]);
        RoundOf16[6] = Match7;
        KnockoutTable Match8 = new KnockoutTable('8', tables[A].runnerUp(), tables[B].runnerUp());
        RoundOf16[7] = Match8;


    }

    public void update4s(){
        KnockoutTable Quat1 = new KnockoutTable('4', RoundOf16[0].Winner(), RoundOf16[1].Winner());
        Quaters[0] = Quat1;
        KnockoutTable Quat2 = new KnockoutTable('4', RoundOf16[2].Winner(), RoundOf16[3].Winner());
        Quaters[1] = Quat2;
        KnockoutTable Quat3 = new KnockoutTable('4', RoundOf16[4].Winner(), RoundOf16[5].Winner());
        Quaters[2] = Quat3;
        KnockoutTable Quat4 = new KnockoutTable('4', RoundOf16[6].Winner(), RoundOf16[7].Winner());
        Quaters[3] = Quat4;

        KnockoutTable Semi1 = new KnockoutTable('2', Quat1.Winner(), Quat2.Winner());
        Semi[0] = Semi1;
        KnockoutTable Semi2 = new KnockoutTable('2', Quat3.Winner(), Quat4.Winner());
        Semi[1] = Semi2;

        Final = new KnockoutTable('1', Semi1.Winner(), Semi2.Winner());
    }

    public void updateSemi(){
        KnockoutTable Semi1 = new KnockoutTable('2', Quaters[0].Winner(), Quaters[1].Winner());
        Semi[0] = Semi1;
        KnockoutTable Semi2 = new KnockoutTable('2', Quaters[2].Winner(), Quaters[3].Winner());
        Semi[1] = Semi2;

        Final = new KnockoutTable('1', Semi1.Winner(), Semi2.Winner());

    }

    public void updateFinal(){
        Final = new KnockoutTable('1', Semi[0].Winner(), Semi[1].Winner());
    }



    public void update() throws FileNotFoundException {
        for(KnockoutTable knockoutTable: RoundOf16){
            knockoutTable.reset();
        }
        Scanner read = Table.read();
        while (read.hasNextLine()) {
            String line = read.nextLine();
            if (line.charAt(6) == '8' || line.charAt(6) == '4' || line.charAt(6) == '2' || line.charAt(6) == '1') {
                int index = 8;
                String team1 = "";
                String team2 = "";
                String goals1 = "";
                String goals2 = "";
                while (line.charAt(index) != ' ') {
                    team1 += line.charAt(index);
                    index++;
                }
                index += 4;
                while (line.charAt(index) != ' ') {
                    team2 += line.charAt(index);
                    index++;
                }
                index += 8;
                while (line.charAt(index) != ' ') {
                    goals1 += line.charAt(index);
                    index++;
                }
                index += 3;
                while (index < line.length()) {
                    goals2 += line.charAt(index);
                    index++;
                }
                int goal1 = Integer.parseInt(goals1);
                int goal2 = Integer.parseInt(goals2);

                for (KnockoutTable knockout : RoundOf16) {
                    if (knockout.has(team1, team2)) {
                        if (knockout.isTeam1(team1))
                            knockout.setGoal(goal1, goal2);
                        else
                            knockout.setGoal(goal2, goal1);
                        if (goal1 > goal2)
                            knockout.setWinner(team1, team2);
                        else
                            knockout.setWinner(team2, team1);
                    }
                }
                for (KnockoutTable knockout : Quaters) {
                    if (knockout.has(team1, team2)) {
                        if (knockout.isTeam1(team1))
                            knockout.setGoal(goal1, goal2);
                        else
                            knockout.setGoal(goal2, goal1);
                        if (goal1 > goal2)
                            knockout.setWinner(team1, team2);
                        else
                            knockout.setWinner(team2, team1);
                    }
                }
                for (KnockoutTable knockout : Semi) {
                    if (knockout.has(team1, team2)) {
                        if (knockout.isTeam1(team1))
                            knockout.setGoal(goal1, goal2);
                        else
                            knockout.setGoal(goal2, goal1);
                        if (goal1 > goal2)
                            knockout.setWinner(team1, team2);
                        else
                            knockout.setWinner(team2, team1);
                    }
                }
                if (Final.has(team1, team2)) {
                    if (Final.isTeam1(team1))
                        Final.setGoal(goal1, goal2);
                    else
                        Final.setGoal(goal2, goal1);
                    if (goal1 > goal2)
                        Final.setWinner(team1, team2);
                    else
                        Final.setWinner(team2, team1);
                }

                if (line.charAt(6) == '8')
                    update4s();
                if (line.charAt(6) == '4')
                    updateSemi();
                if (line.charAt(6) == '2')
                    updateFinal();
            }
        }
    }

    public char[] thrids(ThirdPlaceTable thirdPlaces){
        char[] thirds = thirdPlaces.GroupIDs();
        Arrays.sort(thirds);
        System.out.println(thirds);
        if(Arrays.equals(new char[]{'A', 'B', 'C', 'D'}, thirds)) return new char[]{'A', 'D', 'B', 'C'};
        if(Arrays.equals(new char[]{'A', 'B', 'C', 'E'}, thirds)) return new char[]{'A', 'E', 'B', 'C'};
        if(Arrays.equals(new char[]{'A', 'B', 'C', 'F'}, thirds)) return new char[]{'A', 'F', 'B', 'C'};
        if(Arrays.equals(new char[]{'A', 'B', 'D', 'E'}, thirds)) return new char[]{'D', 'E', 'A', 'B'};
        if(Arrays.equals(new char[]{'A', 'B', 'D', 'F'}, thirds)) return new char[]{'D', 'F', 'A', 'B'};
        if(Arrays.equals(new char[]{'A', 'B', 'E', 'F'}, thirds)) return new char[]{'E', 'F', 'B', 'A'};
        if(Arrays.equals(new char[]{'A', 'C', 'D', 'E'}, thirds)) return new char[]{'E', 'D', 'C', 'A'};
        if(Arrays.equals(new char[]{'A', 'C', 'D', 'F'}, thirds)) return new char[]{'F', 'D', 'C', 'A'};
        if(Arrays.equals(new char[]{'A', 'C', 'E', 'F'}, thirds)) return new char[]{'E', 'F', 'C', 'A'};
        if(Arrays.equals(new char[]{'A', 'D', 'E', 'F'}, thirds)) return new char[]{'E', 'F', 'D', 'A'};
        if(Arrays.equals(new char[]{'B', 'C', 'D', 'E'}, thirds)) return new char[]{'E', 'D', 'B', 'C'};
        if(Arrays.equals(new char[]{'B', 'C', 'D', 'F'}, thirds)) return new char[]{'F', 'D', 'C', 'B'};
        if(Arrays.equals(new char[]{'B', 'C', 'E', 'F'}, thirds)) return new char[]{'F', 'E', 'C', 'B'};
        if(Arrays.equals(new char[]{'B', 'D', 'E', 'F'}, thirds)) return new char[]{'F', 'E', 'D', 'B'};
        if(Arrays.equals(new char[]{'C', 'D', 'E', 'F'}, thirds)) return new char[]{'F', 'E', 'D', 'C'};
        return null;

    }

    public Team[] get4Thirds(char[] IDs, Table[] tables){
        Team[] teams = new Team[4];
        for (Table table : tables) {
            if (table.getGroupID() == IDs[0]) teams[0] = table.third();
            if (table.getGroupID() == IDs[1]) teams[1] = table.third();
            if (table.getGroupID() == IDs[2]) teams[2] = table.third();
            if (table.getGroupID() == IDs[3]) teams[3] = table.third();
        }
        return teams;
    }

    public KnockoutTable[] getRoundOf16(){
        return RoundOf16;
    }

    public KnockoutTable[] getQuaters() {
        return Quaters;
    }

    public KnockoutTable[] getSemi() {
        return Semi;
    }

    public KnockoutTable getFinal() {
        return Final;
    }

    public void setScore(String team1, String team2, int goals1, int goals2) throws IOException {
        for(KnockoutTable knockout: RoundOf16){
            if(knockout.has(team1, team2))
                knockout.setScore(team1, team2, goals1, goals2);
        }
        for(KnockoutTable knockout: Quaters){
            if(knockout.has(team1, team2))
                knockout.setScore(team1, team2, goals1, goals2);
        }
        for(KnockoutTable knockout: Semi){
            if(knockout.has(team1, team2))
                knockout.setScore(team1, team2, goals1, goals2);
        }
        if(Final.has(team1, team2)) Final.setScore(team1, team2, goals1, goals2);

        update();
    }

    public boolean has(String team1, String team2){
        for(KnockoutTable knockout: RoundOf16){
            if(knockout.has(team1, team2))
                return true;
        }
        for(KnockoutTable knockout: Quaters){
            if(knockout.has(team1, team2))
                return true;
        }
        for(KnockoutTable knockout: Semi){
            if(knockout.has(team1, team2))
                return true;
        }
        if(Final.has(team1, team2)) return true;;
        return false;
    }
}
