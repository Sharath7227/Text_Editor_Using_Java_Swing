import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleTextEditor extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu;
    JMenuItem newFile, openFile, saveFile, exit;
    JMenuItem cut, copy, paste;
    JMenuItem fontSize, fontColor, fontStyle;

    public SimpleTextEditor() {
        setTitle("Simple Text Editor");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        // Edit Menu
        editMenu = new JMenu("Edit");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        // Format Menu
        formatMenu = new JMenu("Format");
        fontSize = new JMenuItem("Font Size");
        fontColor = new JMenuItem("Font Color");
        fontStyle = new JMenuItem("Font Style");

        fontSize.addActionListener(this);
        fontColor.addActionListener(this);
        fontStyle.addActionListener(this);

        formatMenu.add(fontSize);
        formatMenu.add(fontColor);
        formatMenu.add(fontStyle);

        // Add Menus to MenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // File Menu Actions
        if (e.getSource() == newFile) {
            textArea.setText(""); // Clear text area for new file
        } else if (e.getSource() == openFile) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    FileReader reader = new FileReader(fileChooser.getSelectedFile());
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == saveFile) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
                    textArea.write(writer);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == exit) {
            System.exit(0); // Exit the application
        }

        else if (e.getSource() == cut) {
            textArea.cut();
        } else if (e.getSource() == copy) {
            textArea.copy();
        } else if (e.getSource() == paste) {
            textArea.paste();
        }

        // Format Menu Actions
        else if (e.getSource() == fontSize) {
            // Prompt user to enter font size up to 120
            String input = JOptionPane.showInputDialog(this, "Enter Font Size (Max: 120):");
            try {
                int size = Integer.parseInt(input);
                if (size > 0 && size <= 120) {
                    textArea.setFont(new Font(textArea.getFont().getName(), textArea.getFont().getStyle(), size));
                } else {
                    JOptionPane.showMessageDialog(this, "Font size must be between 1 and 120.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
            }
        } else if (e.getSource() == fontColor) {
            Color color = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
            if (color != null) {
                textArea.setForeground(color);
            }
        } else if (e.getSource() == fontStyle) {
            String[] styles = {"Plain", "Bold", "Italic"};
            int style = JOptionPane.showOptionDialog(this, "Choose Font Style", "Font Style",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, styles, styles[0]);

            if (style >= 0) {
                switch (style) {
                    case 0:
                        textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, textArea.getFont().getSize()));
                        break;
                    case 1:
                        textArea.setFont(new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize()));
                        break;
                    case 2:
                        textArea.setFont(new Font(textArea.getFont().getName(), Font.ITALIC, textArea.getFont().getSize()));
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleTextEditor editor = new SimpleTextEditor();
        editor.setVisible(true);
    }
}
