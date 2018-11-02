package esoe.model;

import javax.swing.table.TableModel;

public interface Wildcard extends TableModel {

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

    /**
     * методы зависят от того, с какими данными работает модель.
     * при работе с arrayList, конструкторы должны быть переопределены
     * потому их лучше не указывать в интерфейсе.
     *
    //добавляет в модель пустую строку
    public Object[][] addString();

    //добавляем строку c данными в модель данных, возвращает новую модель данных
    public Object[][] addRow(String[] s);

    //добавляем столбец к модели
    public Wildcard addColumn(String s);

    //возвращаем класс к которому относятся данные столбца
    //необходимо при формировании запроса на создание таблицы в базе данных
    public Class getColumnClass(int col);
     */

}
