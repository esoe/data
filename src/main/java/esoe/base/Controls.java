package esoe.base;

import java.sql.*;
import esoe.model.*;

public class Controls {

    public static void create(String base){
        try {
            Access.setUrl("");
            Connection con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            Statement st = con.createStatement();
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + base);
            st.close();
            System.out.println(base + " база создана.");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //удаление базы данных
    public static void drop(String base){
        try {
            Access.setUrl("");
            Connection con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            Statement st = con.createStatement();
            st.executeUpdate("DROP DATABASE " + base);
            st.close();
            System.out.println("база ... " + base + " ...  удалена");
            //Editor.ta.append("база ... " + base + " ...  удалена" + " ...\n");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //подключение к существующей базе
    public static Connection getConnection(String base) {
        Connection con = null;
        try {
            Class.forName(Access.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Access.setUrl(base);
            con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            System.out.println("... подключение к базе ... " + base);
            //Editor.ta.append("... подключение к базе ... " + base + " ...\n");
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    //таблицу из jtable добавляем в базу, прописать добавление данных таблицы
    //типы данных таблицы надо брать из модели таблицы, а не задавать принудительно.
    public static void createTable(String base, String table, ArrScheme dm) throws SQLException {
        Connection con = null;
        Statement st = null;
        String sql = "CREATE TABLE IF NOT EXISTS " +
                base +
                "." +
                table;
        String[] h = dm.header;
        sql = sql + "( " + h[0] + " int NOT NULL AUTO_INCREMENT, ";
        int len = dm.header.length; //getColumnCount();

        if (len > 1) {
            for (int i=1; i<len; i++){
                sql = sql + h[i] + " " + "varchar(50)" + ", "; //getColumnClass(int col)
                //тип данных должен определяться исходя из содержания или модели таблицы
                //длина строки должна определяться моделью таблицы
            }
        }

        sql = sql + "PRIMARY KEY (`" + h[0] +"`))";

        try {
            con = getConnection(base);
            st = con.createStatement();
            st.execute(sql);
            System.out.println("в базе данных ... " + base + " ... создана таблица ..." + table + " ...");
            //Editor.ta.append("в базе данных ... " + base + " ... создана таблица ..." + table + " ...\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    //удаляем таблицу из базы
    public static void dropTable(String base, String table) throws SQLException {
        Connection con = null;
        Statement st = null;
        String sql = "DROP TABLE " +
                base +
                "." +
                table;

        try {
            con = getConnection(base);
            st = con.createStatement();
            st.execute(sql);
            System.out.println("из базы данных " + base + " удалена таблица " + table);
            //Editor.ta.append("из базы данных " + base + " удалена таблица " + table + " ... \n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    //добавляем данные в таблицу
    public static void addData(String base, String table, ArrScheme dm){
        Connection con = null;
        Statement st = null;
        String sql;

        String headers = ""; //id, `Наименование`
        String data = "'"; //2, 'Cube'

        int col = dm.header.length;
        int i = 0;
        while (i < (col)){
            headers = headers + dm.header[i] + "";
            if (i < (col-1)){headers = headers + ", ";}
            i++;
        }

        //строка заголовков сформирована, далее идет цикл формирования строк с данными и запросов на внесение данных в базу
        //количество запросов определяется количеством в data
        int row = dm.data.length;
        i = 0;
        try {
            con = getConnection(base);

            while (i < row){
                int j = 0;
                while (j < col){
                    data = data + dm.getValueAt(i, j) + "'";
                    if (j < (col-1)){data = data + ", '";}
                    j++;
                }

                sql = "INSERT INTO " +
                        base +
                        "." +
                        table +
                        "(" +
                        headers + //сформировать строку из dm
                        ")" +
                        "VALUES (" +
                        data + //сформировать строку из dm
                        ");";
                st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println("Table " + table + " is Updated!");
                //Editor.ta.append("в таблицу " + table + " добавлена строка ... " + (i+1) + " ...\n");

                i++;
                data = "'";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {

            try {
                st.close();
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    //возвращает модель таблицы из базы данных
    public static ArrScheme getData(String base, String table){
        ArrScheme dm;
        String[] header = null;
        Object[][] data = null;
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = "select * from "+table;

        try {
            con = getConnection(base);
            st = con.createStatement();
            rs = st.executeQuery(sql);

            //получаем список заголовков таблицы
            ResultSetMetaData mdata = (ResultSetMetaData)rs.getMetaData();
            //Editor.ta.append("получаем список заголовков таблицы ..." + "\n");
            // получаем количество столбцов в таблице
            int col = mdata.getColumnCount();
            //Editor.ta.append("получаем количество столбцов в таблице ... " + col + "\n");

            header = new String[col];
            for (int i=0; i<col; i++) header[i] = mdata.getColumnName(i+1);


            //узнаем количество строк в таблице
            int row = 0;
            while (rs.next()) {
                row++;
            }
            //Editor.ta.append("узнаем количество строк в таблице ... " + row + "\n");

            data = new Object[row][col];
            rs.first();

            //получаем данные таблицы
            //Editor.ta.append("получаем данные таблицы " + table + " ...\n");
            int i = 0;
            do {
                int j = 0;
                while (j < col) {
                    data[i][j] = rs.getObject(j+1);
                    j++;
                }
                //Editor.ta.append("строка " + (i+1) + " получена ...\n");
                i++;
            }while (rs.next());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { st.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        dm = new ArrScheme(data, header);
        return dm;
    }

    //Узнаем количество строк в таблице
    public static void getDataCount(String base, String table){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = "select count(*) from "+table;

        try {
            con = getConnection(base);
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Количество записей в " + table + ": " + count);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { st.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    //получаем список таблиц в базе данных
    public static String[] getTableList(String dbName){
        Connection con = null;
        ResultSet rs = null;
        String[] list = null;

        try {
            Access.setUrl("");
            con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            DatabaseMetaData md = con.getMetaData ();
            rs = md.getTables (dbName, "", "%", null);
            int i = 0;
            int len = 0;
            while (rs.next()) {
                len++;
                //System.out.println(len);
            }

            rs.first();
            list = new String[len];

            while (i < len) {
                String name = rs.getString("TABLE_NAME");
                list[i] = name;
                i++;
                //System.out.println(name);
                //base.Editor.ta.append(name + "\n");
                rs.next();
            }
            rs.close();
            con.close();
        }

        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return list;
    }

    //получаем список баз данных на сервере
    public static String[] getBaseList(){
        Connection con = null;
        ResultSet rs = null;
        String[] list = null;

        try {
            Access.setUrl("");
            con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            DatabaseMetaData md = con.getMetaData ();
            rs = md.getCatalogs();

            int i = 0;
            int len=0;
            while (rs.next()) {
                len++;
            }

            rs.first();
            list = new String[len];

            while (i < len) {
                String name = rs.getString(1);
                list[i] = name;
                i++;
                rs.next();
            }
            rs.close();
            con.close();
        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return list;
    }

    //почемуто метод гет не уточняет название базы и таблицы, модель которых должен вернуть
    public static ArrScheme getBaseModel(){
        String[] header = {"id", "Наименование"};
        Object[][] data = null;
        ArrScheme dm;

        Connection con = null;
        ResultSet rs = null;

        System.out.println("Перечень баз данных на сервере (metadata):");

        try {
            Access.setUrl("");
            con = DriverManager.getConnection(Access.getUrl(),
                    Access.getUserName(),
                    Access.getPassword());
            rs = con.getMetaData().getCatalogs();

            //узнаем количество строк rowCount в rs
            rs.last();
            int rowCount = rs.getRow();
            System.out.println("rowCount = " + rowCount);
            rs.beforeFirst();

            //узнаем количество столбцов
            int i = 1;
            data = new Object[rowCount][2];

            while (rs.next()) {
                data[i-1][0] = Integer.toString(i);
                data[i-1][1] = rs.getString(1);
                System.out.println(data[i-1][0] + " " + data[i-1][1]);
                i++;
            }

        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        dm = new ArrScheme(data, header);
        return dm;
    }
}
