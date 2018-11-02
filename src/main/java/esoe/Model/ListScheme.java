package esoe.Model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ListScheme extends AbstractTableModel implements Wildcard{
    public ArrayList<String> header;
    public ArrayList<Object> data;
    //data может быть строкой, тогда объект ListScheme будет возвращать заголовок и одну строку.
    //Список же обектов ListScheme будет представлять таблицу.
    //возможно проще в arrSheme расширить функционал, переводя периодически данные в arrayList,
    //когда это удобно.
    //в tableModel можно передавать либо массив, либо вектор, всеравно понадобится преобразование данных.


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
