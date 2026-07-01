package pbo.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pbo.models.Mahasiswa;
import pbo.views.components.TextBox;

public class FormMahasiswaDialog extends JDialog {
    private TextBox txtNim, txtNama, txtEmail;
    private JLabel lblError;
    private JButton btnSave;
    private boolean saved = false;
    private Mahasiswa mahasiswaData;


    public FormMahasiswaDialog(JFrame parent, String title, Mahasiswa mhs) {
        super(parent, title, true);
        this.setSize(300, 300);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        txtNim = new TextBox("NIM");
        txtNama = new TextBox("Nama");
        txtEmail = new TextBox("Email");

        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(LEFT_ALIGNMENT);

        formPanel.add(txtNim);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtNama);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtEmail);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(lblError);

        if (mhs != null) {
            txtNim.setText(mhs.getNim());
            txtNama.setText(mhs.getNama());
            txtEmail.setText(mhs.getEmail());
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton(mhs == null ? "Add" : "Update");
        JButton btnCancel = new JButton("Batal");
        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);

        // Validasi dan simpan data
        btnSave.addActionListener(e -> {
            if (txtNim.getText().trim().isEmpty() || 
                txtNama.getText().trim().isEmpty() || 
                txtEmail.getText().trim().isEmpty()) {
                lblError.setText("Semua field wajib diisi!");
            } else {
                mahasiswaData = new Mahasiswa(txtNim.getText(), txtNama.getText(), txtEmail.getText());
                saved = true;
                this.dispose();
            }
        });

        btnCancel.addActionListener(e -> this.dispose());

        this.add(formPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public boolean isSaved() { return saved; }
    public Mahasiswa getMahasiswaData() { return mahasiswaData; }
}