package bababooey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public class Panel extends JPanel {

    Table GroupA;
    Table GroupB;
    Table GroupC;
    Table GroupD;
    Table GroupE;
    Table GroupF;

    ThirdPlaceTable table;

    private Table[] tables;

    knockoutBracket bracket;

    public Panel(){
        try {
            initialise();
            this.bracket = new knockoutBracket(tables, table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){

        }
        this.setLayout(null);

        JTextField team1 = new JTextField();
        JTextField team2 = new JTextField();

        JTextField score1 = new JTextField();
        JTextField score2 = new JTextField();

        JButton button = new JButton("Update");

        team1.setBounds(600, 200, 100, 25);
        team2.setBounds(710, 200, 100, 25);

        score1.setBounds(820, 200, 25, 25);
        score2.setBounds(855, 200, 25, 25);

        button.setBounds(890, 200, 75, 25);

        add(score1);
        add(score2);
        add(team1);
        add(team2);
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean bracketUpdated = false;
                try {
                    Integer.parseInt(score2.getText());
                    Integer.parseInt(score1.getText());
                    for (Table group : tables) {
                        if (group.getTeamByName(team1.getText()) != null && group.getTeamByName(team2.getText()) != null) {
                            // Here should be an if statement to check if there are no same match in Group and Knockouts
                            if (group.findResult(group.getTeamByName(team1.getText()), group.getTeamByName(team2.getText())) != null && bracket.has(team1.getText(), team2.getText())) {
                                try {
                                    bracket.setScore(team1.getText(), team2.getText(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    // no result between teams
                                }
                                bracketUpdated = true;

                            } else {
                                try {
                                    group.setScore(team1.getText(), team2.getText(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()));
                                    bracket.update8s(tables, table);
                                    bracket.update4s();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (TeamNorFoundException e) {

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    // no result between teams
                                }
                            }
                        }
                    }
                    if (!bracketUpdated) {
                        try {
                            bracket.setScore(team1.getText(), team2.getText(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            // no result between teams
                        }

                    }
                }
                catch (Exception e){

                }

                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int space = 10;
        Stack<Table> stack = new Stack<>();
        stack.add(GroupF);
        stack.add(GroupE);
        stack.add(GroupD);
        stack.add(GroupC);
        stack.add(GroupB);
        stack.add(GroupA);
        for(int i = 0; i < 3; i++) {
            Table group = stack.pop();
            drawTable(g, space, 10, group);
            space += 240;
        }
        space = 10;
        for(int i = 0; i < 3; i++) {
            drawTable(g, space, 260+40, stack.pop());
            space += 240;
        }
        int y = 630;
        int y1 = 630;
        int y2 = 630;
        for(int i = 0; i < bracket.getRoundOf16().length; i++)
            drawKnockOuts(g, 10, y2+= 70, bracket.getRoundOf16()[i]);
        for(int i = 0; i < bracket.getQuaters().length; i++)
            drawKnockOuts(g, 220, y1+=70, bracket.getQuaters()[i]);
        for(int i = 0; i < bracket.getSemi().length; i++)
            drawKnockOuts(g, 440, y+=70, bracket.getSemi()[i]);

        drawKnockOuts(g, 660, 700, bracket.getFinal());

        drawThirdPlaceTable(g);
    }

    public void drawTable(Graphics g, int startingSpot, int y, Table group){

        Stack<Integer> sequence = new Stack<>();
        sequence.add(2);
        sequence.add(3);
        sequence.add(4);
        sequence.add(1);
        sequence.add(4);
        sequence.add(2);
        sequence.add(3);
        sequence.add(1);
        sequence.add(4);
        sequence.add(3);
        sequence.add(2);
        sequence.add(1);

        int linelower = 0;

        for(int i = startingSpot; i < startingSpot + 220; i+=20){                   // starting point x scaling. y height scaling, i+20 space scaling
            g.drawLine(y, i, y+260, i);
            if(!sequence.empty()) {
                Team one = group.startingPos[sequence.pop() - 1];
                Team two = group.startingPos[sequence.pop() - 1];
                g.drawString(one.getNAME(), y + 5, startingSpot + 15 + linelower);
                g.drawString(two.getNAME(), y + 15 + 110, startingSpot + 15 + linelower);
                try {
                    g.drawString(group.findResult(one, two)[0], y + 223, startingSpot + 15 + linelower);
                    g.drawString(group.findResult(one, two)[1], y + 243, startingSpot + 15 + linelower);
                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    // Teams Have not played yet
                }
                linelower+= 20;
            }
        }

        //drawing vertical lines
        g.drawLine(y, startingSpot, y, startingSpot+200);
        g.drawLine(y+200+20, startingSpot, y+200+20, startingSpot+120);
        g.drawLine(y+240+20, startingSpot, y+240+20, startingSpot+200);
        g.drawLine(y+220+20, startingSpot, y+220+20, startingSpot+200);
        g.drawLine(y+110+10, startingSpot, y+110+10, startingSpot+120);


        // adding final score
        g.drawString(group.winner().getNAME(), y + 5, startingSpot + 15 + linelower);
        g.drawString(String.valueOf(group.winner().getScore()), y + 243, startingSpot + 15 + linelower);

        g.drawString(group.runnerUp().getNAME(), y + 5, startingSpot + 15 + linelower + 20);
        g.drawString(String.valueOf(group.runnerUp().getScore()), y + 243, startingSpot + 15 + linelower + 20);

        g.drawString(group.third().getNAME(), y + 5, startingSpot + 15 + linelower + 40);
        g.drawString(String.valueOf(group.third().getScore()), y + 243, startingSpot + 15 + linelower + 40);

        g.drawString(group.fourth().getNAME(), y + 5, startingSpot + 15 + linelower + 60);
        g.drawString(String.valueOf(group.fourth().getScore()), y + 243, startingSpot + 15 + linelower + 60);

    }

    public void drawThirdPlaceTable(Graphics g){
        table = new ThirdPlaceTable(this);
        Stack<Team> thirds = table.getTeams();
        for(int i = 300; i < 440; i+=20){
            g.drawLine(600, i, 800, i);
            Team team;
            if(!thirds.empty()) {
                team = thirds.pop();
                g.drawString(team.getNAME(), 605, i + 15);
                g.drawString(String.valueOf(team.getScore()), 780, i+15);
            }

        }
        g.drawLine(600, 300, 600, 420);
        g.drawLine(800, 300, 800, 420);
        g.drawLine(775, 300, 775, 420);
    }

    public void drawKnockOuts(Graphics g, int x, int y, KnockoutTable knockout){
        int i = y;
            for(int line = 0; line < 3; line++) {
                g.drawLine(x, i + (line*20), x+200, i + (line*20));
                g.drawLine(x, i + (line*20), x+200, i + (line*20));
                g.drawLine(x, i + (line*20), x+200, i + (line*20));
            }
            g.drawString(knockout.startingPos[0].getNAME(), x + 15, i+15);
            g.drawString(knockout.startingPos[1].getNAME(), x + 15, i+35);
            g.drawString(String.valueOf(knockout.startingPos[0].getGoals()), x+ 187, i+15);
            g.drawString(String.valueOf(knockout.startingPos[1].getGoals()), x + 187, i+35);
            g.drawLine(x,i,x,i+(2*20));
            g.drawLine(x+200,i,x+200,i+(2*20));
            g.drawLine(x+175,i,x+175,i+(2*20));

    }

    public Table[] getTables(){
        return tables;
    }

    public void initialise() throws FileNotFoundException {
        GroupA = new Table('A', "Turkey", "Italy", "Wales", "Switzerland");
        GroupB = new Table('B', "Denmark", "Finland", "Belgium", "Russia");
        GroupC = new Table('C', "Netherlands", "Ukraine", "Austria", "NorthMacedonia");
        GroupD = new Table('D', "England", "Croatia", "Scotland", "Czech_Republic");
        GroupE = new Table('E', "Spain", "Sweden", "Poland", "Slovakia");
        GroupF = new Table('F', "Hungary", "Portugal", "France", "Germany");
        tables = new Table[]{GroupA, GroupB, GroupC, GroupD, GroupE, GroupF};
        table = new ThirdPlaceTable(this);
    }
}
