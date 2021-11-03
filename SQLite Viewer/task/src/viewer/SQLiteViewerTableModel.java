package viewer;

import javax.swing.table.AbstractTableModel;

class SQLiteViewerTableModel extends AbstractTableModel {
    String[] columns = {"contact_id", "first_name", "last_name", "email", "phone"};
    Object[][] data;

    protected SQLiteViewerTableModel(Object[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {

        return data.length;
    }

    @Override
    public int getColumnCount() {

        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {

        return columns[columnIndex];
    }

}
