import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame {
    private final JLabel timeLabel;
    private final Timer timer;
    private int elapsedTime;

    public Stopwatch() {
        setTitle("Stopwatch");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creating label
        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        add(timeLabel, BorderLayout.CENTER);

        // Creating button panel and buttons
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(1000, e -> {
            elapsedTime += 1000;
            updateTimeLabel();
        });

        startButton.addActionListener(e -> timer.start());
        stopButton.addActionListener(e -> timer.stop());
        resetButton.addActionListener(e -> {
            timer.stop();
            elapsedTime = 0;
            updateTimeLabel();
        });
    }

    private void updateTimeLabel() {
        int seconds = (elapsedTime / 1000) % 60;
        int minutes = (elapsedTime / 60000) % 60;
        int hours = (elapsedTime / 3600000) % 24;

        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
