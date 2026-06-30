package pbo.views.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextBox extends JPanel {
    private JTextField textField;

    public TextBox(String label) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(jLabel);

        textField = new JTextField();
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(textField);
        
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(String text) {
        this.textField.setText(text);
    }
    
    public JTextField getTextField() {
        return this.textField;
    }
}