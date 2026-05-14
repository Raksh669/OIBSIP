import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OnlineExamFinal extends JFrame implements ActionListener {

    JTextField nameField, emailField, userField;
    JPasswordField passField;
    JButton loginBtn;

    String name = "", email = "", password = "1234";

    JLabel qLabel, timerLabel, userLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup bg;
    JButton nextBtn, prevBtn, submitBtn, logoutBtn, profileBtn;

    List<Integer> order = new ArrayList<>();
    int current = 0, time = 60;
    int[] userAnswers;
    boolean submitted = false;

    javax.swing.Timer timer;

    // 10 QUESTIONS
    String[] questions = {
            "Which keyword is used to create object?",
            "Which is not primitive type?",
            "Which loop runs at least once?",
            "Which keyword is used for method overriding?",
            "Which symbol is used for comments?",
            "Which is used for input in Java?",
            "Which is not access modifier?",
            "Which package is for GUI?",
            "Which method is entry point?",
            "Java supports?"
    };

    String[][] options = {
            {"new", "create", "object", "make"},
            {"int", "float", "String", "boolean"},
            {"for", "while", "do-while", "switch"},
            {"override", "super", "@Override", "this"},
            {"//", "/* */", "#", "All"},
            {"Scanner", "input", "cin", "read"},
            {"public", "private", "protected", "static"},
            {"java.io", "java.util", "javax.swing", "java.net"},
            {"main()", "start()", "run()", "init()"},
            {"OOP", "Procedure", "None", "Both"}
    };

    int[] answers = {0, 2, 2, 2, 3, 0, 3, 2, 0, 3};

    public OnlineExamFinal() {
        setTitle("Online Examination System");
        setSize(600, 450);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showLogin();
        setVisible(true);
    }

    void showLogin() {
        getContentPane().removeAll();
        getContentPane().setBackground(new Color(20, 30, 40));

        JLabel n = new JLabel("Name:");
        n.setForeground(Color.WHITE);
        n.setBounds(150, 60, 100, 30);
        add(n);

        nameField = new JTextField();
        nameField.setBounds(250, 60, 150, 30);
        add(nameField);

        JLabel e = new JLabel("Email:");
        e.setForeground(Color.WHITE);
        e.setBounds(150, 100, 100, 30);
        add(e);

        emailField = new JTextField();
        emailField.setBounds(250, 100, 150, 30);
        add(emailField);

        JLabel u = new JLabel("Username:");
        u.setForeground(Color.WHITE);
        u.setBounds(150, 140, 100, 30);
        add(u);

        userField = new JTextField();
        userField.setBounds(250, 140, 150, 30);
        add(userField);

        JLabel p = new JLabel("Password:");
        p.setForeground(Color.WHITE);
        p.setBounds(150, 180, 100, 30);
        add(p);

        passField = new JPasswordField();
        passField.setBounds(250, 180, 150, 30);
        add(passField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(220, 240, 100, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        repaint();
    }

    void startExam() {
        getContentPane().removeAll();
        getContentPane().setBackground(new Color(10, 25, 45));

        userAnswers = new int[questions.length];
        Arrays.fill(userAnswers, -1);

        order.clear();
        for (int i = 0; i < questions.length; i++) order.add(i);
        Collections.shuffle(order);

        userLabel = new JLabel("Welcome, " + name);
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(20, 10, 200, 30);
        add(userLabel);

        timerLabel = new JLabel("Time: " + time);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(450, 10, 100, 30);
        add(timerLabel);

        qLabel = new JLabel();
        qLabel.setForeground(Color.WHITE);
        qLabel.setBounds(50, 50, 500, 30);
        add(qLabel);

        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();

        JRadioButton[] opts = {opt1, opt2, opt3, opt4};
        for (int i = 0; i < 4; i++) {
            opts[i].setBounds(50, 100 + i * 30, 400, 30);
            opts[i].setBackground(new Color(10, 25, 45));
            opts[i].setForeground(Color.WHITE);
            add(opts[i]);
        }

        bg = new ButtonGroup();
        bg.add(opt1); bg.add(opt2); bg.add(opt3); bg.add(opt4);

        prevBtn = new JButton("Previous");
        prevBtn.setBackground(Color.ORANGE);

        nextBtn = new JButton("Next");
        nextBtn.setBackground(Color.GREEN);

        submitBtn = new JButton("Submit");
        submitBtn.setBackground(Color.BLUE);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.RED);

        profileBtn = new JButton("Profile");
        profileBtn.setBackground(Color.PINK); // ✅ Pink color
        profileBtn.setForeground(Color.WHITE); // ✅ White text

        JButton[] btns = {prevBtn, nextBtn, submitBtn, logoutBtn, profileBtn};
        int x = 30;
        for (JButton b : btns) {
            b.setBounds(x, 300, 100, 30);
            b.setForeground(Color.WHITE);
            b.addActionListener(this);
            add(b);
            x += 110;
        }

        loadQuestion();

        timer = new javax.swing.Timer(1000, e -> {
            time--;
            timerLabel.setText("Time: " + time);

            if (time <= 10) {
                timerLabel.setForeground(Color.RED);
            }

            if (time == 0) {
                userAnswers[current] = getSelected();
                submitExam();
            }
        });
        timer.start();

        repaint();
    }

    void loadQuestion() {
        int i = order.get(current);
        qLabel.setText("Q" + (current + 1) + ": " + questions[i]);

        opt1.setText(options[i][0]);
        opt2.setText(options[i][1]);
        opt3.setText(options[i][2]);
        opt4.setText(options[i][3]);

        bg.clearSelection();

        if (userAnswers[current] != -1) {
            switch (userAnswers[current]) {
                case 0 -> opt1.setSelected(true);
                case 1 -> opt2.setSelected(true);
                case 2 -> opt3.setSelected(true);
                case 3 -> opt4.setSelected(true);
            }
        }

        prevBtn.setEnabled(current != 0);
    }

    int getSelected() {
        if (opt1.isSelected()) return 0;
        if (opt2.isSelected()) return 1;
        if (opt3.isSelected()) return 2;
        if (opt4.isSelected()) return 3;
        return -1;
    }

    void submitExam() {
        if (submitted) return;
        submitted = true;
        timer.stop();

        int correct = 0;
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] == answers[order.get(i)]) correct++;
        }

        int wrong = questions.length - correct;
        int percent = (correct * 100) / questions.length;

        JOptionPane.showMessageDialog(this,
                "Score: " + correct + "/" + questions.length +
                        "\nCorrect: " + correct +
                        "\nWrong: " + wrong +
                        "\nPercentage: " + percent + "%");

        saveResult(correct);
    }

    void saveResult(int score) {
        try {
            FileWriter fw = new FileWriter("result.txt", true);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            fw.write("Name: " + name +
                    "\nEmail: " + email +
                    "\nScore: " + score + "/" + questions.length +
                    "\nDate: " + date + "\n\n");

            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showProfile() {
        JTextField nameF = new JTextField(name);
        JTextField emailF = new JTextField(email);
        JPasswordField passF = new JPasswordField(password);

        Object[] fields = {"Name:", nameF, "Email:", emailF, "Password:", passF};

        int option = JOptionPane.showConfirmDialog(this, fields, "Profile", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            name = nameF.getText();
            email = emailF.getText();
            password = new String(passF.getPassword());
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginBtn) {
            name = nameField.getText();
            email = emailField.getText();

            if (userField.getText().equals("admin") &&
                    new String(passField.getPassword()).equals(password)) {
                startExam();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        }

        else if (e.getSource() == nextBtn && !submitted) {
            userAnswers[current] = getSelected();

            if (current == questions.length - 1) {
                JOptionPane.showMessageDialog(this, "This is the last question");
            } else {
                current++;
                loadQuestion();
            }
        }

        else if (e.getSource() == prevBtn && !submitted) {
            userAnswers[current] = getSelected();
            if (current > 0) {
                current--;
                loadQuestion();
            }
        }

        else if (e.getSource() == submitBtn) {
            userAnswers[current] = getSelected();
            submitExam();
        }

        else if (e.getSource() == profileBtn) {
            showProfile();
        }

        else if (e.getSource() == logoutBtn) {
            if (timer != null) timer.stop();
            showLogin();
        }
    }

    public static void main(String[] args) {
        new OnlineExamFinal();
    }
}