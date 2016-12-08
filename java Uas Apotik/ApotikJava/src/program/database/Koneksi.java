package program.database;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class Koneksi {
    public static String SESSION_USER = "";
    public static final String VERSION = "1.0.0";
    public static String QUERY_RUMUS_STOCK = "((SELECT IFNULL(SUM(Stock_Sekarang) - SUM(Stock_Awal),0) FROM pembelian_detail pb WHERE pb.ID_Produk = p.ID_Produk) - (SELECT IFNULL(SUM(Jumlah),0) FROM penjualan_detail pj WHERE pj.ID_Produk = p.ID_Produk) - (SELECT IFNULL(SUM(Jumlah),0) FROM retur rr WHERE rr.ID_Produk = p.ID_Produk)) as `Stock`";
    public static final String DB_HOST = "jdbc:mysql://localhost:3306/apotikjava";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";
    public static final String AUTHOR = "PROGRAM INVENTORI APOTIK\n" +
            "Denis Kusbowo (535110016)\n" +
            "James Eklie (535110019)\n" +
            "Resky Gamadya (535140019)";

    public Connection connection = null;
    public Statement statement = null;

    public void runQuery(String sql) {
        try {
            PreparedStatement s = connection.prepareStatement(sql);
            s.execute();
        } catch (SQLException e) {

        }
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found !");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(this.DB_HOST,
                    this.DB_USER, this.DB_PASS);

        } catch (SQLException e) {
            // System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            statement = connection.createStatement();
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        DefaultTableModel dtm = new CustomTableModel(data, columnNames);

        return dtm;

    }

    public static DefaultTableModel buildTableModelCustom(Object[][] dataa,
                                                          Object[] cols) throws SQLException {

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = cols.length;
        for (int column = 0; column < columnCount; column++) {
            columnNames.add((String) cols[column]);
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        for (int i = 0; i < dataa.length; i++) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < dataa[i].length; columnIndex++) {
                vector.add(dataa[i][columnIndex]);
            }
            data.add(vector);
        }

        DefaultTableModel dtm = new CustomTableModel(data, columnNames);

        return dtm;

    }

    public static void writeCSVfile(quick.dbtable.DBTable dbt, String FileOutput) throws IOException,
            ClassNotFoundException, SQLException {
        Writer writer = null;

        int nRow = dbt.getRowCount();
        int nCol = dbt.getColumnCount();

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(FileOutput)));

            // write the header information
            StringBuffer bufferHeader = new StringBuffer();

            for (int j = 0; j < nCol; j++) {
                if (j == 0) {
                    bufferHeader.append("No. ,");
                }

                bufferHeader.append(dbt.getColumn(j).getColumnName());
                if (j != nCol)
                    bufferHeader.append(",");
            }
            writer.write(bufferHeader.toString() + "\r\n");

            // write row information
            for (int i = 0; i < nRow; i++) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(i + 1 + ",");
                for (int j = 0; j < nCol; j++) {
                    buffer.append(dbt.getValueAt(i, j).toString().replace(",", " "));
                    if (j != nCol)
                        buffer.append(",");
                }
                writer.write(buffer.toString() + "\r\n");
            }
        } finally {
            writer.close();
        }
    }
}

@SuppressWarnings("serial")
class CustomTableModel extends DefaultTableModel {
    public CustomTableModel(Vector<Vector<Object>> data,
                            Vector<String> columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}