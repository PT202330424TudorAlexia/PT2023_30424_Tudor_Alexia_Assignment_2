package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationFrame implements ActionListener {

    JFrame frame;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextField textField5;
    JTextArea textArea1;

    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JLabel label6;

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
        textField4.setBounds(230, 320, 200, 50);
        textField4.setFont(font);
        textField4.setEditable(true);

        textField5 = new JTextField();
        textField5.setBounds(230, 420, 200, 50);
        textField5.setFont(font);
        textField5.setEditable(true);

        textArea1 = new JTextArea();
      //  textArea1.setBounds(470, 60, 740, 480);
       // textArea1.setFont(font);
        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(470, 60, 740, 480);


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
        label6.setBounds(480, 10, 150, 50);
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
        frame.add(textArea1);
        frame.add(scrollPane);
        frame.add(generateButton);
        frame.add(clearButton);
        frame.add(stopButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {


        }
    }


    public static void main(String[] args) {
        SimulationFrame graphic = new SimulationFrame();
    }

}
