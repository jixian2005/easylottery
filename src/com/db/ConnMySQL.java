package com.db;
import com.model.Forecast;

import java.sql.*;

public class ConnMySQL {

    private final String dbDriver = "com.mysql.jdbc.Driver";// 设置连接MySQL数据库的驱动
    // 连接MySQL数据库的路径(注意：这里的数据库db_lottery.sql必须已经被导入MYSQL了）
    private static final String URL = "jdbc:mysql://localhost:3306/db_lottery";
    private static final String USERNAME = "root";// 设置连接MySQL数据库的用户名
    private static final String PASSWORD = "jixian0721";// 设置连接MySQL数据库的密码
    private static Connection con = null;// 设置初始化连接MySQL数据库的对象
    public ConnMySQL() {// 连接MySQL数据库的构造方法
        try {
            Class.forName(dbDriver);// 加载MySQL数据库的驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库加载失败");
        }
    }
    public static boolean creatConnection() {// 建立MySQL数据库的连接
        try {
            // 根据连接MySQL数据库的路径、用户名、密码连接MySQL数据库
            con = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void closeConnection() {// 关闭MySQL数据库的连接
        if (con != null) {// 判断Connection对象是否为空
            try {
                con.close();// 关闭MySQL数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                con = null;// 重置Connection对象为空
            }
        }
    }

    public ResultSet showAll(String sql) {// 显示所有抽号信息
        Statement statement = null;// 声明用于执行SQL语句的接口
        if (con == null) {// Connection对象为空
            creatConnection();// 建立MySQL数据库的连接
        }
        try {
            statement = con.createStatement();// 创建执行SQL语句的Statement对象
            ResultSet rs = statement.executeQuery(sql);// 执行查询语句获得结果集
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getABC(String abc, int number) {// 获得统计数据
        // 获得number(0~9)在历届抽号号码中第abc(a~g)位出现的总次数,例如想获得第1位的情况，即统计a这一列的数据内容
        String sql = "select count(" + abc + ") from tb_history where " + abc + "=" + number;
        Statement statement = null;// 声明用于执行SQL语句的接口
        int i = 0;// 初始化“”
        if (con == null) {// Connection对象为空
            creatConnection();// 建立MySQL数据库的连接
        }
        try {
            statement = con.createStatement();// 创建执行SQL语句的Statement对象
            ResultSet rs = statement.executeQuery(sql);// 执行查询语句获得结果集
            while (rs.next()) {// 遍历结果集
                i = rs.getInt(1);// 获得“抽号批次”
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return i;
    }
    public static void closeStatement(Statement stat) {// 关闭用于执行SQL语句的Statement对象
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库语句异常");
                e.printStackTrace();
            }
        }
    }

    public int selectNumber(String sql) {// 查询批次
        Statement statement = null;// 声明用于执行SQL语句的接口
        int i = 10001;// 初始化“抽号批次”
        if (con == null) {// Connection对象为空
            creatConnection();// 建立MySQL数据库的连接
        }
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                i = rs.getInt(1);// 替换“抽号批次”
            }
        } catch (SQLException e) {
            System.out.println("历史抽号号码添加失败！");
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return i;
    }

    public Boolean addForecast(Forecast fr) {// 添加机选号码
        if (con == null) {// Connection对象为空
            creatConnection();// 建立MySQL数据库的连接
        }
        try {
            PreparedStatement statement = con.prepareStatement(
                    "insert into tb_forecast (number,a,b,c,d,e,f,g,forecasttime)"
                            + "values(?,?,?,?,?,?,?,?,?)"
            ); // 定义插入数据库的预处理语句（括号里有9个“?”）
            statement.setInt(1, fr.getNumber()); // 设置预处理语句的参数值
            statement.setInt(2, fr.getA());
            statement.setInt(3, fr.getB());
            statement.setInt(4, fr.getC());
            statement.setInt(5, fr.getD());
            statement.setInt(6, fr.getE());
            statement.setInt(7, fr.getF());
            statement.setInt(8, fr.getG());
            statement.setString(9, fr.getForecasttime());
            statement.executeUpdate(); // 执行预处理语句
            return true;
        } catch (SQLException e) {
            System.out.println("机选号码添加失败！");
            e.printStackTrace();
            return false;
        }
    }


}
