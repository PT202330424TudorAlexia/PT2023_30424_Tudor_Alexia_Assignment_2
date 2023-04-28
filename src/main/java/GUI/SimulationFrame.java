package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;

public class SimulationFrame implements ActionListener {

    SimulationManager simulationManager1;
    JFrame frame;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextField textField5;
    JTextField textField6;
    JTextField textField7;
    JTextField textField8;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JLabel label6;
    JTextArea textArea1;

    JCheckBox boxqueue;
    JCheckBox boxtime;

    JButton[] functionButtons = new JButton[3];
    JButton generateButton, clearButton, stopButton;
    JPanel panel;

    Font font = new Font("Arial", Font.BOLD, 15);

    public SimulationFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 600);
        frame.setLayout(null);

        textField1 = new JTextField();
        textField1.setBounds(230, 20, 200, 50);
        textField1.setFont(font);
        textField1.setEditable(true);

        textField2 = new JTextField();
        textField2.setBounds(230, 120, 200, 50);
        textField2.setFont(font);
        textField2.setEditable(true);

        textField3 = new JTextField();
        textField3.setBounds(230, 220, 200, 50);
        textField3.setFont(font);
        textField3.setEditable(true);

        textField4 = new JTextField();
        textField4.setBounds(230, 320, 80, 50);
        textField4.setFont(font);
        textField4.setEditable(true);

        textField5 = new JTextField();
        textField5.setBounds(350, 320, 80, 50);
        textField5.setFont(font);
        textField5.setEditable(true);

        textField6 = new JTextField();
        textField6.setBounds(230, 420, 80, 50);
        textField6.setFont(font);
        textField6.setEditable(true);

        textField7 = new JTextField();
        textField7.setBounds(350, 420, 80, 50);
        textField7.setFont(font);
        textField7.setEditable(true);

        textField8 = new JTextField();
        textField8.setBounds(1150, 10, 50, 30);
        textField8.setFont(font);
        textField8.setEditable(true);

        boxqueue = new JCheckBox("Shortest Queue");
        boxqueue.setBounds(460, 140, 120, 120);
        boxtime = new JCheckBox("Shortest Time");
        boxtime.setBounds(460, 240, 120, 120);

        textArea1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setAutoscrolls(true);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        scrollPane.setBounds(600, 60, 500, 460);
        textArea1.setBounds(610, 70, 480, 430);


        textArea1.setFocusable(false);
        textArea1.setVisible(true);
        textArea1.setFont(font);

        label1 = new JLabel();
        label1.setBounds(20, 20, 150, 50);
        label1.setFont(font);
        label1.setText("Number of Clients:");

        label2 = new JLabel();
        label2.setBounds(20, 120, 150, 50);
        label2.setFont(font);
        label2.setText("Number of Queues:");

        label3 = new JLabel();
        label3.setBounds(20, 220, 150, 50);
        label3.setFont(font);
        label3.setText("Simulation interval:");

        label4 = new JLabel();
        label4.setBounds(20, 300, 210, 70);
        label4.setFont(font);
        label4.setText("Min and max arrival time:");

        label5 = new JLabel();
        label5.setBounds(20, 400, 210, 70);
        label5.setFont(font);
        label5.setText("Min and max service time:");

        label6 = new JLabel();
        label6.setBounds(600, 10, 150, 50);
        label6.setFont(font);
        label6.setText("Log of Events");

        generateButton = new JButton("Generate");
        clearButton = new JButton("Clear");
        stopButton = new JButton("Stop");

        functionButtons[0] = generateButton;
        functionButtons[1] = clearButton;
        functionButtons[2] = stopButton;

        for (int i = 0; i < 3; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(font);
            functionButtons[i].setFocusable(false);

        }

        generateButton.setBounds(30, 500, 110, 50);
        clearButton.setBounds(180, 500, 110, 50);
        stopButton.setBounds(330, 500, 110, 50);

        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);
        frame.add(label5);
        frame.add(label6);
        frame.add(textField1);
        frame.add(textField2);
        frame.add(textField3);
        frame.add(textField4);
        frame.add(textField5);
        frame.add(textField6);
        frame.add(textField7);
        frame.add(textField8);
        frame.add(scrollPane);

        frame.add(boxqueue);
        frame.add(boxtime);
        frame.add(generateButton);
        frame.add(clearButton);
        frame.add(stopButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int timeLimit = 0, minArrivalTime=0, maxArrivalTime=0, maxProcessingTime=0, minProcessingTime=0, numberOfServers=0, numberOfClients=0;
        SelectionPolicy selectionPolicy = null;
        if (e.getSource() == generateButton) {
            timeLimit = Integer.parseInt(textField3.getText());
            minArrivalTime = Integer.parseInt(textField4.getText());
            maxArrivalTime = Integer.parseInt(textField5.getText());
            minProcessingTime = Integer.parseInt(textField6.getText());
            maxProcessingTime = Integer.parseInt(textField7.getText());
            numberOfClients = Integer.parseInt(textField1.getText());
            numberOfServers = Integer.parseInt(textField2.getText());

            if (boxqueue.isSelected()) {
                selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            } else if (boxtime.isSelected())
                selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            simulationManager1=new SimulationManager(this,timeLimit, minArrivalTime, maxArrivalTime, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, selectionPolicy);
            simulationManager1.startSimulation(this,timeLimit, minArrivalTime, maxArrivalTime, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, selectionPolicy);

        }
        if(e.getSource()==clearButton){
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
            textField7.setText("");
        }
        if(e.getSource()==stopButton){

        }

    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public void setTextArea1(JTextArea textArea1) {
        this.textArea1 = textArea1;
    }

    public void updateTextArea(String text){
        textArea1.setText(text);
    }

    public static void main(String[] args) {
        SimulationFrame frame1=new SimulationFrame();
    }

}
