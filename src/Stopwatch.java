import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Stopwatch extends JFrame {
    private final JLabel timeLabel;
    private final Timer timer;
    private final JTextArea savedTimesArea;
    private final List<String> savedTimes = new ArrayList<>();
    private int elapsedTime;

    public Stopwatch() {
        setTitle("Stopwatch");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creating label
        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        add(timeLabel, BorderLayout.NORTH);

        // Creating button panel and buttons
        JPanel buttonPanel = new JPanel();
        JButton toggleButton = new JButton("Start");
        JButton saveButton = new JButton("Save");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(toggleButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Creating saved times display area
        savedTimesArea = new JTextArea(8, 30);
        StringBuilder areaDisplay = new StringBuilder("Saved Times:\n");

        savedTimesArea.setEditable(false);
        savedTimesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        savedTimesArea.setText(areaDisplay.toString());

        JScrollPane scrollPane = new JScrollPane(savedTimesArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.SOUTH);

        // Creating timer and functions
        timer = new Timer(1000, e -> {
            elapsedTime += 1000;
            timeLabel.setText(getFormattedTime());
        });

        toggleButton.addActionListener(e -> {
            if (toggleButton.getText().equals("Start")) {
                timer.start();
                toggleButton.setText("Stop");
            } else {
                timer.stop();
                toggleButton.setText("Start");
            }
        });

        saveButton.addActionListener(e -> {
            String currentTime = getFormattedTime();
            savedTimes.add(currentTime);

            System.out.printf("Saved %s\n", currentTime);

            // Update display
            areaDisplay.append(String.format("%d. %s\n", savedTimes.lastIndexOf(currentTime) + 1, currentTime));
            savedTimesArea.setText(areaDisplay.toString());
            savedTimesArea.setCaretPosition(savedTimesArea.getDocument().getLength());
        });

        resetButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to reset?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                timer.stop();
                elapsedTime = 0;
                timeLabel.setText(getFormattedTime());

                savedTimes.clear();
                areaDisplay.setLength(13); // Resetting to first line
                savedTimesArea.setText(areaDisplay.toString());
            }
        });
    }

    private String getFormattedTime() {
        int seconds = (elapsedTime / 1000) % 60;
        int minutes = (elapsedTime / 60000) % 60;
        int hours = (elapsedTime / 3600000) % 24;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
