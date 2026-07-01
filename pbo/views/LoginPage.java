package pbo.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pbo.views.components.TextBox;

public class LoginPage extends JPanel {

    public LoginPage(JFrame parent) {
        // Menggunakan GridBagLayout agar form login berada di tengah halaman
        this.setLayout(new GridBagLayout());

        JPanel loginForm = new JPanel();
        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
        loginForm.setPreferredSize(new Dimension(250, 200));

        TextBox txtUsername = new TextBox("Username");
        TextBox txtPassword = new TextBox("Password");
        
        JLabel lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(CENTER_ALIGNMENT);

        JButton btnLogin = new JButton("Login");
        btnLogin.setAlignmentX(CENTER_ALIGNMENT);


        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            // Hardcode login
            if (username.equals("Clay") && password.equals("123")) {
                lblError.setText(" ");
                // Pindah ke Halaman Tabel Mahasiswa
                CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
                cl.show(parent.getContentPane(), "MahasiswaListPage");
            } else {
                lblError.setText("Username atau Password salah!");
            }
        });

        loginForm.add(txtUsername);
        loginForm.add(Box.createRigidArea(new Dimension(0, 10)));
        loginForm.add(txtPassword);
        loginForm.add(Box.createRigidArea(new Dimension(0, 10)));
        loginForm.add(lblError);
        loginForm.add(Box.createRigidArea(new Dimension(0, 10)));
        loginForm.add(btnLogin);

        this.add(loginForm);
    }
}