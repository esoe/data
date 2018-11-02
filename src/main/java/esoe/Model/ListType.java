package esoe.Model;

import javax.swing.table.AbstractTableModel;

public class ListType extends AbstractTableModel implements Wildcard{
    //возвращаем количество столбцов в модели
    public int getColumnCount();

    //возвращаем количество строк в модели
    public int getRowCount();

    //устанавливаем название столбца в модели
    public void setHeader(int col, String header);

    //возвращаем данные ячейки
    public Object getValueAt(int row, int col);

    //разрешает редактировать данные в таблице - jtable
    public boolean isCellEditable(int row, int col);

    //изменение данных в ячейке модели таблицы
    public void setValueAt(Object val, int row, int col);

    //добавляет в модель пустую строку
    public Object[][] addString();

    //добавляем строку c данными в модель данных, возвращает новую модель данных
    public Object[][] addRow(String[] s);

    //добавляем столбец к модели
    public Wildcard addColumn(String s);

    //возвращаем класс к которому относятся данные столбца
    //необходимо при формировании запроса на создание таблицы в базе данных
    public Class getColumnClass(int col);

}