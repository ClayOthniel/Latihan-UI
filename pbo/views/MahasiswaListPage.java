package pbo.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import pbo.models.Mahasiswa;

public class MahasiswaListPage extends JPanel {
    private List<Mahasiswa> listMahasiswa;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;

    public MahasiswaListPage(JFrame parent) {
        this.setLayout(new BorderLayout());

        // 1. Inisialisasi Data Hardcode Awal
        listMahasiswa = new ArrayList<>();
        listMahasiswa.add(new Mahasiswa("12345", "Budi Santoso", "budi@gmail.com"));
        listMahasiswa.add(new Mahasiswa("67890", "Siti Aminah", "siti@yahoo.com"));
        listMahasiswa.add(new Mahasiswa("11223", "Andi Wijaya", "andi@gmail.com"));

        // 2. Panel Atas: Search Bar & Tombol Tambah
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        txtSearch = new JTextField(15);
        searchPanel.add(txtSearch);
        
        JButton btnSearch = new JButton("Filter");
        JButton btnReset = new JButton("Reset");
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);
        
        JButton btnTambah = new JButton("Tambah Mhs");
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(btnTambah);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(actionPanel, BorderLayout.EAST);

        // 3. Konfigurasi JTable
        String[] columns = {"NIM", "Nama", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mencegah edit langsung di sel tabel
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Load data pertama kali
        refreshTable(listMahasiswa);

        // 4. Panel Bawah: Tombol Aksi Row (Edit & Delete)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEdit = new JButton("Edit Terpilih");
        JButton btnDelete = new JButton("Delete Terpilih");
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        // --- Action Listeners ---
        
        // Filter Pencarian (NIM, Nama, atau Email)
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().toLowerCase().trim();
            List<Mahasiswa> filteredList = new ArrayList<>();
            for (Mahasiswa m : listMahasiswa) {
                if (m.getNim().toLowerCase().contains(keyword) || 
                    m.getNama().toLowerCase().contains(keyword) || 
                    m.getEmail().toLowerCase().contains(keyword)) {
                    filteredList.add(m);
                }
            }
            refreshTable(filteredList);
        });

        // Reset Pencarian
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(listMahasiswa);
        });

        // Buka Form Tambah
        btnTambah.addActionListener(e -> {
            FormMahasiswaDialog form = new FormMahasiswaDialog(parent, "Form Tambah Mahasiswa", null);
            if (form.isSaved()) {
                listMahasiswa.add(form.getMahasiswaData());
                refreshTable(listMahasiswa);
            }
        });

        // Buka Form Edit
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Silakan pilih baris data terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Ambil data asli berdasarkan NIM yang tertera di baris terpilih
            String selectedNim = (String) tableModel.getValueAt(selectedRow, 0);
            Mahasiswa targetMhs = null;
            for (Mahasiswa m : listMahasiswa) {
                if (m.getNim().equals(selectedNim)) {
                    targetMhs = m;
                    break;
                }
            }

            if (targetMhs != null) {
                FormMahasiswaDialog form = new FormMahasiswaDialog(parent, "Form Edit Mahasiswa", targetMhs);
                if (form.isSaved()) {
                    Mahasiswa updatedData = form.getMahasiswaData();
                    targetMhs.setNim(updatedData.getNim());
                    targetMhs.setNama(updatedData.getNama());
                    targetMhs.setEmail(updatedData.getEmail());
                    refreshTable(listMahasiswa);
                }
            }
        });

        // Aksi Hapus data dengan Konfirmasi
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Silakan pilih baris data yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String selectedNim = (String) tableModel.getValueAt(selectedRow, 0);
                listMahasiswa.removeIf(m -> m.getNim().equals(selectedNim));
                refreshTable(listMahasiswa);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            }
        });

        // Penyusunan Layout Utama Halaman
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Fungsi helper untuk merender ulang tabel
    private void refreshTable(List<Mahasiswa> dataList) {
        tableModel.setRowCount(0); // Kosongkan tabel
        for (Mahasiswa m : dataList) {
            tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getEmail()});
        }
    }
}