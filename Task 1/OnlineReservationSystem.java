import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Reservation {
    int pnr;
    String name, trainNo, trainName, classType, date, from, to;

    Reservation(int pnr, String name, String trainNo, String trainName,
                String classType, String date, String from, String to) {
        this.pnr = pnr;
        this.name = name;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.date = date;
        this.from = from;
        this.to = to;
    }
}

public class OnlineReservationSystem extends JFrame implements ActionListener {

    ArrayList<Reservation> list = new ArrayList<>();
    int pnrCounter = 1000;

    HashMap<String, String> trains = new HashMap<>();

    JTextField userField, name, trainNo, trainName, from, to, pnrField, dateField;
    JPasswordField passField;

    JComboBox<String> classType;

    JButton loginBtn, reserveBtn, cancelBtn, viewBtn, backBtn;
    JButton searchBtn, confirmCancelBtn;

    JTable table;
    DefaultTableModel model;
    JTextArea detailsArea;

    OnlineReservationSystem() {

        setTitle("Online Reservation System");

        trains.put("12627", "Karnataka Express");
        trains.put("12622", "Tamil Nadu Express");
        trains.put("12637", "Pandian Express");
        trains.put("12623", "Chennai Mail");

        showLogin();
    }

    // ---------------- LOGIN ----------------
    void showLogin() {
        getContentPane().removeAll();
        setSize(350, 250);
        setLayout(null);

        addLabel("Username", 60);
        userField = new JTextField();
        userField.setBounds(120, 60, 150, 25);
        add(userField);

        addLabel("Password", 100);
        passField = new JPasswordField();
        passField.setBounds(120, 100, 150, 25);
        add(passField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 150, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // ---------------- MENU ----------------
    void showMenu() {
        getContentPane().removeAll();
        setSize(400, 300);
        setLayout(null);

        reserveBtn = new JButton("Reserve");
        reserveBtn.setBounds(120, 80, 150, 30);
        add(reserveBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(120, 120, 150, 30);
        add(cancelBtn);

        viewBtn = new JButton("View Bookings");
        viewBtn.setBounds(120, 160, 150, 30);
        add(viewBtn);

        reserveBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        viewBtn.addActionListener(this);

        repaint();
    }

    // ---------------- RESERVE ----------------
    void showReserve() {
        getContentPane().removeAll();
        setSize(400, 450);
        setLayout(null);

        name = addField("Name", 30);

        trainNo = addField("Train No", 70);

        trainName = addField("Train Name", 110);
        trainName.setEditable(false);

        trainNo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String tno = trainNo.getText();

                if (trains.containsKey(tno)) {
                    trainName.setText(trains.get(tno));
                } else {
                    trainName.setText("");
                }
            }
        });

        addLabel("Class", 150);

        String classes[] = {"Sleeper", "AC", "General"};
        classType = new JComboBox<>(classes);
        classType.setBounds(140, 150, 200, 25);
        add(classType);

        addLabel("Date (dd-MM-yyyy)", 190);
        dateField = new JTextField();
        dateField.setBounds(140, 190, 200, 25);
        add(dateField);

        from = addField("From", 230);
        to = addField("To", 270);

        reserveBtn = new JButton("Submit");
        reserveBtn.setBounds(120, 320, 100, 30);
        add(reserveBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(230, 320, 100, 30);
        add(backBtn);

        reserveBtn.addActionListener(this);
        backBtn.addActionListener(e -> showMenu());

        repaint();
    }

    // ---------------- DATE VALIDATION ----------------
    boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);

            Date d = sdf.parse(dateStr);

            if (d.before(new Date())) {
                JOptionPane.showMessageDialog(this, "Past date not allowed");
                return false;
            }

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Date Format");
            return false;
        }
    }

    // ---------------- TABLE ----------------
    void showTable() {
        getContentPane().removeAll();
        setSize(700, 400);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("PNR");
        model.addColumn("Name");
        model.addColumn("Train No");
        model.addColumn("Train Name");
        model.addColumn("From");
        model.addColumn("To");

        for (Reservation r : list) {
            model.addRow(new Object[]{
                    r.pnr,
                    r.name,
                    r.trainNo,
                    r.trainName,
                    r.from,
                    r.to
            });
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton back = new JButton("Back");
        add(back, BorderLayout.SOUTH);

        back.addActionListener(e -> showMenu());

        repaint();
    }

    // ---------------- CANCEL ----------------
    void showCancel() {
        getContentPane().removeAll();
        setSize(400, 350);
        setLayout(null);

        addLabel("Enter PNR", 30);

        pnrField = new JTextField();
        pnrField.setBounds(140, 30, 150, 25);
        add(pnrField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(50, 70, 100, 30);
        add(searchBtn);

        confirmCancelBtn = new JButton("Cancel Ticket");
        confirmCancelBtn.setBounds(200, 70, 130, 30);
        add(confirmCancelBtn);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 120, 280, 120);
        add(detailsArea);

        backBtn = new JButton("Back");
        backBtn.setBounds(140, 260, 100, 30);
        add(backBtn);

        searchBtn.addActionListener(this);
        confirmCancelBtn.addActionListener(this);
        backBtn.addActionListener(e -> showMenu());

        repaint();
    }

    // ---------------- HELPERS ----------------
    JTextField addField(String label, int y) {

        addLabel(label, y);

        JTextField t = new JTextField();
        t.setBounds(140, y, 200, 25);

        add(t);

        return t;
    }

    void addLabel(String text, int y) {

        JLabel l = new JLabel(text);
        l.setBounds(30, y, 100, 25);

        add(l);
    }

    // ---------------- ACTION ----------------
    public void actionPerformed(ActionEvent e) {

        // LOGIN
        if (e.getSource() == loginBtn) {

            if (userField.getText().equals("Rakshana") &&
                    new String(passField.getPassword()).equals("admin")) {

                showMenu();

            } else {

                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        }

        // OPEN RESERVE PAGE
        else if (e.getSource() == reserveBtn &&
                reserveBtn.getText().equals("Reserve")) {

            showReserve();
        }

        // SUBMIT RESERVATION
        else if (e.getSource() == reserveBtn) {

            if (!isValidDate(dateField.getText()))
                return;

            Reservation r = new Reservation(
                    ++pnrCounter,
                    name.getText(),
                    trainNo.getText(),
                    trainName.getText(),
                    classType.getSelectedItem().toString(),
                    dateField.getText(),
                    from.getText(),
                    to.getText()
            );

            list.add(r);

            JOptionPane.showMessageDialog(this,
                    "Booked Successfully!\nPNR: " + r.pnr);

            showMenu();
        }

        // VIEW BOOKINGS
        else if (e.getSource() == viewBtn) {

            showTable();
        }

        // OPEN CANCEL PAGE
        else if (e.getSource() == cancelBtn) {

            showCancel();
        }

        // SEARCH PNR
        else if (e.getSource() == searchBtn) {

            try {

                int pnr = Integer.parseInt(pnrField.getText());

                for (Reservation r : list) {

                    if (r.pnr == pnr) {

                        detailsArea.setText(
                                "Name : " + r.name +
                                "\nTrain No : " + r.trainNo +
                                "\nTrain Name : " + r.trainName +
                                "\nClass : " + r.classType +
                                "\nFrom : " + r.from +
                                "\nTo : " + r.to
                        );

                        return;
                    }
                }

                detailsArea.setText("PNR Not Found");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Invalid PNR");
            }
        }

        // CANCEL TICKET
        else if (e.getSource() == confirmCancelBtn) {

            try {

                int pnr = Integer.parseInt(pnrField.getText());

                Iterator<Reservation> it = list.iterator();

                while (it.hasNext()) {

                    Reservation r = it.next();

                    if (r.pnr == pnr) {

                        it.remove();

                        JOptionPane.showMessageDialog(this,
                                "Ticket Cancelled");

                        showMenu();
                        return;
                    }
                }

                JOptionPane.showMessageDialog(this,
                        "PNR Not Found");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Invalid PNR");
            }
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        new OnlineReservationSystem();
    }
}