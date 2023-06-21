package domain.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import domain.api.ControlInterface;
import domain.api.NotificationInterface;
import domain.api.SubmissionInterface;

public class UserInterface extends Thread implements NotificationInterface {
    private ControlInterface controlInterface;
    private SubmissionInterface submissionInterface;

    private JFrame frame = new JFrame("Process Scheduling Simulator");
    private JPanel basePanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JPanel jobPanel = new JPanel();
    private JButton startButton = new JButton("Start");
    private JButton suspendButton = new JButton("Suspend");
    private JButton resumeButton = new JButton("Resume");
    private JButton stopButton = new JButton("Stop");
    private JButton submitJobButton = new JButton("Submit Job");
    private JTextField jobTextField = new JTextField(20);
    private JTextArea reportTextArea = new JTextArea(200, 30);
    private JScrollPane reportScrollPane = new JScrollPane(reportTextArea);

    public UserInterface() {
        startButton.setEnabled(true);
        suspendButton.setEnabled(false);
        resumeButton.setEnabled(false);
        stopButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlInterface.startSimulation();

                startButton.setEnabled(false);
                suspendButton.setEnabled(true);
                stopButton.setEnabled(true);
            }
        });

        suspendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlInterface.suspendSimulation();

                suspendButton.setEnabled(false);
                resumeButton.setEnabled(true);
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlInterface.resumeSimulation();

                suspendButton.setEnabled(true);
                resumeButton.setEnabled(false);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlInterface.stopSimulation();

                startButton.setEnabled(true);
                suspendButton.setEnabled(false);
                resumeButton.setEnabled(false);
            }
        });

        submitJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitJob();
            }
        });

        jobTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitJob();
            }
        });

        reportTextArea.setEditable(false);
        reportScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        buttonsPanel.add(startButton);
        buttonsPanel.add(suspendButton);
        buttonsPanel.add(resumeButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        jobPanel.setLayout(new BoxLayout(jobPanel, BoxLayout.X_AXIS));
        jobPanel.add(jobTextField);
        jobPanel.add(submitJobButton);

        basePanel.add(buttonsPanel);
        basePanel.add(jobPanel);
        basePanel.add(reportScrollPane);

        basePanel.setBounds(10, 10, 380, 380);
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

        frame.add(basePanel);

        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setControlInterface(ControlInterface controlInterface) {
        this.controlInterface = controlInterface;
    }

    public void setSubmissionInterface(SubmissionInterface submissionInterface) {
        this.submissionInterface = submissionInterface;
    }

    @Override
    public void display(String info) {
        reportTextArea.append(info + "\n");
    }

    private void submitJob() {
        var submitted = submissionInterface.submitJob(jobTextField.getText());
        if (submitted) {
            jobTextField.setText("");
        }
    }

}
