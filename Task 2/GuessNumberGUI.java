import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GuessNumberGUI extends JFrame {

    private int numberToGuess;
    private int attempts;
    private final int maxAttempts = 10;
    private int score = 0;
    private int round = 1;

    private JTextField inputField;
    private JLabel messageLabel, attemptsLabel, scoreLabel, roundLabel;
    private JButton guessButton, restartButton;

    public GuessNumberGUI() {
        setTitle("🎯 Guess The Number Game");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🌤 Sky Blue Background
        Color bgColor = new Color(135, 206, 235);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title (WHITE)
        JLabel title = new JLabel("Guess The Number");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        // Round (DARK BLUE)
        roundLabel = new JLabel("Round: 1");
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundLabel.setForeground(new Color(0, 0, 139));

        // Message (WHITE → for Too High/Low also)
        messageLabel = new JLabel("Enter a number between 1 and 100");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(bgColor);

        // "Your Guess" (DARK BLUE)
        JLabel inputLabel = new JLabel("Your Guess: ");
        inputLabel.setForeground(new Color(0, 0, 139));

        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.BOLD, 14));

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        // ✅ Guess Button (Green + White text)
        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(0, 170, 0));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);

        // ❌ Restart Button (Red + White text)
        restartButton = new JButton("Restart");
        restartButton.setBackground(new Color(220, 0, 0));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);

        buttonPanel.add(guessButton);
        buttonPanel.add(restartButton);

        // Attempts & Score (DARK BLUE)
        attemptsLabel = new JLabel("Attempts Left: 10");
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptsLabel.setForeground(new Color(0, 0, 139));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(new Color(0, 0, 139));

        // Layout
        panel.add(Box.createVerticalStrut(15));
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(roundLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(messageLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(attemptsLabel);
        panel.add(scoreLabel);

        add(panel);

        generateNumber();

        // Actions
        guessButton.addActionListener(e -> checkGuess());
        restartButton.addActionListener(e -> resetGameManual());
        inputField.addActionListener(e -> checkGuess());

        setVisible(true);
    }

    private void generateNumber() {
        Random rand = new Random();
        numberToGuess = rand.nextInt(100) + 1;
        attempts = 0;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(inputField.getText());
            attempts++;

            if (guess == numberToGuess) {

                score += (maxAttempts - attempts + 1) * 10;
                scoreLabel.setText("Score: " + score);

                messageLabel.setText("🎉 Correct Answer!");
                messageLabel.setForeground(Color.WHITE);

                JOptionPane.showMessageDialog(this,
                        "🎯 Correct Answer!\nNumber: " + numberToGuess,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                round++;
                roundLabel.setText("Round: " + round);

                Timer timer = new Timer(1000, e -> resetGameAuto());
                timer.setRepeats(false);
                timer.start();

            } else if (guess < numberToGuess) {
                messageLabel.setText("🔽 Too Low!");
                messageLabel.setForeground(Color.WHITE);
            } else {
                messageLabel.setText("🔼 Too High!");
                messageLabel.setForeground(Color.WHITE);
            }

            int remaining = maxAttempts - attempts;
            attemptsLabel.setText("Attempts Left: " + remaining);

            if (remaining == 0 && guess != numberToGuess) {
                messageLabel.setText("❌ Game Over! Number was " + numberToGuess);
                messageLabel.setForeground(Color.WHITE);

                JOptionPane.showMessageDialog(this,
                        "Game Over!\nCorrect Number: " + numberToGuess,
                        "Oops!",
                        JOptionPane.ERROR_MESSAGE);

                guessButton.setEnabled(false);
            }

            inputField.setText("");

        } catch (Exception e) {
            messageLabel.setText("⚠ Enter valid number!");
            messageLabel.setForeground(Color.WHITE);
        }
    }

    private void resetGameAuto() {
        generateNumber();
        attemptsLabel.setText("Attempts Left: 10");
        messageLabel.setText("Enter a number between 1 and 100");
        messageLabel.setForeground(Color.WHITE);
        inputField.setText("");
        guessButton.setEnabled(true);
        inputField.requestFocus();
    }

    private void resetGameManual() {
        score = 0;
        round = 1;
        scoreLabel.setText("Score: 0");
        roundLabel.setText("Round: 1");
        resetGameAuto();
    }

    public static void main(String[] args) {
        new GuessNumberGUI();
    }
}