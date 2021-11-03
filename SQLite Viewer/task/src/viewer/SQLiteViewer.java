package viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Objects;

public class SQLiteViewer extends JFrame {
    protected JComboBox<String> tablesComboBox;
    protected JTextArea queryTextArea;

    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setResizable(false);
        setLocationRelativeTo(null);
        createComponents();
        setLayout(new FlowLayout());
        setVisible(true);
    }

    private void createComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JButton executeQueryButton = new JButton("Execute");

        JTextField fileNameTextField = new JTextField();
        fileNameTextField.setPreferredSize(new Dimension(570, 40));
        fileNameTextField.setName("FileNameTextField");
        panel.add(fileNameTextField, BorderLayout.WEST);

        JButton openFileButton = new JButton("Open");
        openFileButton.setName("OpenFileButton");
        openFileButton.setPreferredSize(new Dimension(90, 40));
        openFileButton.addActionListener(actionEvent -> {
            ArrayList<String> databasesName = new ArrayList<>();
            databasesName.add("firstDatabase.db");
            databasesName.add("secondDatabase.db");
            if (!databasesName.contains(fileNameTextField.getText())) {
                JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
                executeQueryButton.setEnabled(false);
                queryTextArea.setEnabled(false);
            } else {
                tablesComboBox.removeAllItems();
                SQLiteViewerJDBC connection = new SQLiteViewerJDBC(fileNameTextField.getText());
                String[] tableList = connection.getTableNames().toArray(new String[0]);
                for (String tableName : tableList
                ) {
                    tablesComboBox.addItem(tableName);
                }
                queryTextArea.setEnabled(true);
                executeQueryButton.setEnabled(true);
            }

        });

        panel.add(openFileButton, BorderLayout.EAST);
        add(panel);

        tablesComboBox = new JComboBox<>();
        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.setPreferredSize(new Dimension(650, 40));
        tablesComboBox.setEditable(true);
        tablesComboBox.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                Object selected = tablesComboBox.getSelectedItem();
                if (selected != null) {
                    String tableName = selected.toString();
                    tableName = tableName.concat(";");
                    String query = "SELECT * FROM ";
                    query = query.concat(tableName);
                    queryTextArea.setText(query);
                }
            }
        });

        add(tablesComboBox);

        JPanel executePanel = new JPanel();
        queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setPreferredSize(new Dimension(500, 150));
        queryTextArea.setEnabled(false);
        executePanel.add(queryTextArea);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setPreferredSize(new Dimension(122, 150));

        JTable table = new JTable();
        table.setPreferredSize(new Dimension(600, 500));
        table.setName("Table");

        executeQueryButton.setName("ExecuteQueryButton");
        executeQueryButton.setPreferredSize(new Dimension(120, 40));
        executeQueryButton.addActionListener(actionEvent -> {
            String correctQuery = String.format("SELECT * FROM %s;", Objects.requireNonNull(tablesComboBox.getSelectedItem()).toString());
            if (!correctQuery.equals(queryTextArea.getText())) {
                JOptionPane.showMessageDialog(new Frame(), "Invalid query");
            } else {
                executeQueryButton.setEnabled(true);
                SelectTableJDBC con = new SelectTableJDBC(fileNameTextField.getText(), queryTextArea.getText());
                SQLiteViewerTableModel tableModel = new SQLiteViewerTableModel(con.data);
                table.setModel(tableModel);
            }
        });

        executeQueryButton.setEnabled(false);
        panel1.add(executeQueryButton, BorderLayout.NORTH);

        executePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        executePanel.add(panel1);
        add(executePanel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(670, 500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane);

    }
}
