package bababooey;

import java.util.Arrays;
import java.util.Stack;

public class ThirdPlaceTable {

    private final Team[] teams = new Team[6];

    public ThirdPlaceTable(Panel panel){
        int i = 0;
        for(Table table: panel.getTables()){
            teams[i++] = table.third();
        }
        Arrays.sort(teams);
    }

    public Stack<Team> getTeams(){
        Stack<Team> teamsStack = new Stack<>();
        for(int i = 5; i >= 0; i--)
            teamsStack.add(teams[i]);
        return teamsStack;
    }

    public char[] GroupIDs(){
        char[] IDs = new char[4];
        for(int i = 0; i < 4; i++){
            IDs[i] = teams[i].getGroupID();
        }
        return IDs;
    }

}
