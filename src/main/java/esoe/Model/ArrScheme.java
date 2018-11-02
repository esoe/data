package esoe.Model;
import javax.swing.table.AbstractTableModel;

public class ArrScheme extends AbstractTableModel implements Wildcard {
    public String[] header;
    public Object[][] data;

    public ArrScheme() {
       String[] h = { "id" };
        Object[][] d = {
                { "1" },
        };

        this.header = h;
        this.data = d;
    }

    public ArrScheme(Object[][] d, String[] h) {
        this.header = h;
        this.data = d;
    }

    //возвращаем количество столбцов в модели
    public int getColumnCount() {
        return this.header.length;
    }

    //возвращаем количество строк в модели
    public int getRowCount() {
        return data.length;
    }

    //устанавливаем название столбца в модели
    public void setHeader(int col, String header){
        this.header[col] = header;
    }

    //возвращаем данные ячейки
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    //разрешает редактировать данные в таблице - jtable
    public boolean isCellEditable(int row, int col) {
        boolean isEditable = true;
        /**
         if (col == 0) {
         isEditable = false; // Make the ID column non-editable
         }
         */
        return isEditable;
    }

    //изменение данных в ячейке модели таблицы
    public void setValueAt(Object val, int row, int col) {
        this.data[row][col] = val;
        fireTableDataChanged();
    }

    //добавляет в модель пустую строку
    public Object[][] addString(){
        Object[][] newData = new Object[this.data.length+1][this.header.length];

        int i = 0;
        while (i < this.data.length) {
            int j = 0;
            while (j < this.header.length) {
                newData[i][j] = this.data[i][j];
                j++;
            }
            i++;
        }
        return newData;
    }

    //добавляем строку c данными в модель данных, возвращает новую модель данных
    public Object[][] addRow(String[] s) {
        int row = this.getRowCount();
        int col = this.getColumnCount();

        Object[][] d = new Object[row+1][col];

        int i = 0;
        while (i < this.data.length) {
            int j = 0;
            while (j < this.data[0].length) {
                d[i][j] = this.data[i][j];
                j++;
            }
            i++;
        }

        int q = 0;
        while (q < this.data[0].length) {
            d[this.data.length][q] = s[q];
            q++;
        }
        return d;
    }

    //добавляем столбец к модели
    /**
     * добавляем поле к заголовку
     * добавляем столбец к данным - пустой
     */
    public ArrScheme addColumn(String s) {
        String[] newHeader = new String[this.header.length + 1];

        int i = 0;
        while (i < this.header.length) {
            newHeader[i] = this.header[i];
            i++;
        }
        newHeader[this.header.length] = s;

        Object[][]newData = new Object[this.data.length][this.header.length + 1];
        i = 0;
        while (i < this.data.length) {
            int j = 0;
            while (j < this.header.length) {
                newData[i][j] = this.data[i][j];
                j++;
            }
            i++;
        }
        ArrScheme newModel = new ArrScheme(newData, newHeader);
        return newModel;
    }


    //возвращаем класс к которому относятся данные столбца, пока не применяю
    //необходимо при формировании запроса на создание таблицы в базе данных
    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }


}
