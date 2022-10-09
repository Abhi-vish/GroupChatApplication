import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class UserOne implements ActionListener, Runnable {
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    BufferedReader reader;
    BufferedWriter writer;

    String name = "manan";
    UserOne() {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(31,44,52));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon((i2));
        JLabel back = new JLabel(i3);
        back.setBounds(2,10,50,50);
        p1.add(back);


        back.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/idea.png"));
        Image i5 = i4.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon((i5));
        JLabel profile = new JLabel(i6);
        profile.setBounds(50,15,40,40);
        p1.add(profile);



        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(23,23,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon((i8));
        JLabel Video = new JLabel(i9);
        Video.setBounds(300,20,23,23);
        p1.add(Video);



        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(23,23,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon((i11));
        JLabel phone = new JLabel(i12);
        phone.setBounds(350,20,23,23);
        p1.add(phone);



        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon((i14));
        JLabel threeDot = new JLabel(i15);
        threeDot.setBounds(400,20,10,25);
        p1.add(threeDot);

        JLabel name = new JLabel("Mini Project");
        name.setBounds(110,25,100,13);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,17));
        p1.add(name);

        JLabel status = new JLabel("You, Aman, Abhishek");
        status.setBounds(110,43,160,13);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.ITALIC,12));
        p1.add(status);


        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        a1.setBackground(Color.BLACK);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setBackground(new Color(31,44,52));
        text.setForeground(Color.WHITE);
        text.setCaretColor(Color.WHITE);
//        text.setMargin(new Insets(50, 50, 10, 10));
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        text.setBorder(null);
        f.add(text);


        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(31,44,52));
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.setBorder(null);
        f.add(send);


        f.setSize(450,700);
        f.setLocation(20,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.BLACK);
        f.setVisible(true);


        try {
            Socket socket = new Socket("localhost",2003);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = "<html><p style=\"font-size:10px;color:#14716E;font-family:Arial;\">"+name+"</p><p style=\"color:white;font-weight:bold;\">"+text.getText()+"</p></html>";
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(Color.BLACK);
            right.setForeground(Color.WHITE);
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+ out +"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(31,44,52));
        output.setBorder(new EmptyBorder(0, 15, 15, 50));
        output.setOpaque(true);
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setBackground(Color.BLACK);
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);


        return panel;
    }

    public void run() {
        try {
            String msg = "";
            while(true) {
                msg = reader.readLine();
                if(msg.contains(name)) {
                    continue;
                }
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.BLACK);
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                a1.add(vertical, BorderLayout.PAGE_START);
                f.repaint();
                f.invalidate();
                f.validate();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        UserOne One = new UserOne();
        Thread t1 = new Thread(One);
        t1.start();
    }
}
