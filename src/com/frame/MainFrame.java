package com.frame;

import com.db.ConnMySQL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;

public class MainFrame extends JFrame{
private JPanel jcontentPane;// 内容面板
private JButton firstPageButton;// 首页
private JButton latePageButton;// 尾页
private JButton nextPageButton;// 下一页
private JButton lastPageButton;// 上一页
private JTable table;// 表格模型
private int maxPageNumber;// 表格的总页数
private int maxrows = 0;// 初始化最大行数为0
private int currentPageNumber = 1;// 初始化表格的当前页数为1
private double pageSize = 20;// 每页表格可容纳20条数据
private DefaultTableModel defaultModel;// 表格模型的实例对象
private JButton btnChoose;

public static void main(String[] args) {
	// TODO Auto-generated method stub
EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				// 实例化主窗体
				MainFrame frame = new MainFrame();
				frame.setVisible(true);// 使主窗体可见
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});	
}

public MainFrame() {// 主窗体的构造方法
	setForeground(Color.BLACK);// 设置前景色为黑色
	setTitle("随机按位抽号系统");// 主窗体的标题
	setResizable(false);// 主窗体不能改变大小
	// 主窗体的标题图标
	setIconImage(
Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imgs/log.png"))
	    );
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 对登录窗体发起“close”时，退出应用程序
	setBounds(100, 30, 1100, 600);// 登录窗体的位置及宽高
	jcontentPane = new JPanel();// 实例化内容面板
	jcontentPane.setLayout(new BorderLayout(0, 0));// 设置内容面板的布局为边界布局
	setContentPane(jcontentPane);// 把内容面板放入主窗体中
	BackgroundPanel contentPane = new BackgroundPanel();// 创建自定义背景面板
	// 设置背景面板的图片
contentPane.setImage(getToolkit().getImage(getClass().getResource("/imgs/main.png")));
	jcontentPane.add(contentPane, BorderLayout.CENTER);// 添加背景面板到内容面板
	// 添加随机选号按钮
	btnChoose = new JButton("随机选号");
	btnChoose.setIcon(
			new ImageIcon(MainFrame.class.getResource("/img_btn/15.png"))
	);
	btnChoose.setBounds(6, 230, 184, 40);
	btnChoose.setBorderPainted(false);
	contentPane.add(btnChoose);

	// 为随机选号按钮添加动作事件监听
	btnChoose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_btnChoose_actionPerformed(e);
		}
	});
	//添加按钮
	
			// “查看历史”按钮
			JButton btnHis = new JButton("");
			// 设置“查看历史”按钮的图标
			btnHis.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/09.png"))
				);
			btnHis.setBounds(6, 150, 184, 40);// “查看历史”按钮的位置及宽高
	btnHis.setBorderPainted(false);
			contentPane.add(btnHis);// 将“查看历史”按钮添加到自定义背景面板中
					
			// “查看号码统计”按钮
			JButton btnTrend = new JButton("");
			// 设置“查看号码统计”按钮的图标
			btnTrend.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/14.png"))
				);
			btnTrend.setBounds(6, 190, 184, 40);// “查看号码统计”按钮的位置及宽高
	btnTrend.setBorderPainted(false);
			contentPane.add(btnTrend);// 将“查看号码统计”按钮添加到自定义背景面板中
					
			JButton btnExit = new JButton("");// “退出系统”按钮
			// 设置“退出系统”按钮的图标
			btnExit.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/08.png"))
				);
			btnExit.setBounds(6, 270, 184, 40);// “退出系统”按钮的位置及宽高
	btnExit.setBorderPainted(false);
	contentPane.add(btnExit);// 将“退出系统”按钮添加到自定义背景面板中	

	//添加表格模型与分页按钮
			JScrollPane scrollPane = new JScrollPane(); // 滚动面板
			scrollPane.setBackground(new Color(0, 51, 204)); // 滚动面板背景色
			scrollPane.setBounds(217, 74, 848, 351); // 滚动面板在主窗体中的位置及宽高
			contentPane.add(scrollPane); // 将滚动面板添加到自定义背景面板中
			table = new JTable(); // 表格模型
			scrollPane.setViewportView(table); // 向滚动面板中添加表格
			firstPageButton = new JButton("首   页"); // “首页”按钮
			// 设置“首页”按钮的图标
			firstPageButton.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/7_08.png")));
			firstPageButton.setBounds(416, 439, 84, 27); // “首页”按钮的位置及宽高
			contentPane.add(firstPageButton); // 将“首页”按钮添加到自定义背景面板中
			latePageButton = new JButton("上一页"); // “上一页”按钮
			// 设置“上一页”按钮的图标
			latePageButton.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/7_10.png")));
			latePageButton.setBounds(550, 439, 84, 27); // “上一页”按钮的位置及宽高
			contentPane.add(latePageButton); // 将“上一页”按钮添加到自定义背景面板中
			nextPageButton = new JButton("下一页"); // “下一页”按钮
			// 设置“下一页”按钮的图标
			nextPageButton.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/7_09.png")));
			nextPageButton.setBounds(686, 439, 84, 27); // “下一页”按钮的位置及宽高
			contentPane.add(nextPageButton); // 将“下一页”按钮添加到自定义背景面板中
			lastPageButton = new JButton("尾   页"); // “尾页”按钮
			// 设置“尾页”按钮的图标
			lastPageButton.setIcon(
				new ImageIcon(MainFrame.class.getResource("/img_btn/7_11.png")));
			lastPageButton.setBounds(819, 439, 84, 27); // “尾页”按钮的位置及宽高
			contentPane.add(lastPageButton); // 将“尾页”按钮添加到自定义背景面板中
			//添加结束

	firstPageButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_firstPageButton_actionPerformed(e);// 为“首页”按钮添加动作事件的监听
		}
	});
	lastPageButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_lastPageButton_actionPerformed(e);// 为“尾页”按钮添加动作事件的监听
		}
	});
	latePageButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_latePageButton_actionPerformed(e);// 为“上一页”按钮添加动作事件的监听
		}
	});
	nextPageButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_nextPageButton_actionPerformed(e);// 为“下一页”按钮添加动作事件的监听
		}
	});
	btnTrend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			do_btnTrend_actionPerformed(e);
		}
	});



	selecttable();

}

	public void selecttable() {// 分页显示抽号号码的方法

		defaultModel = (DefaultTableModel) table.getModel();// 获得表格模型
		defaultModel.setRowCount(0);// 清空表格模型中的数据
		// 定义表头
		defaultModel.setColumnIdentifiers(new Object[]
				{ "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" });
		String sql = "select count(id) from tb_history";// 定义SQL语句
		ConnMySQL con = new ConnMySQL();// 连接数据库
		ResultSet rs = con.showAll(sql);// 执行SQL语句后获得的结果集
		try {
			if (rs.next())// 因为上面的执行结果是有且只有一个，所以我们用if语句来遍历集合
			{
				maxrows = rs.getInt(1);// 为最大行数赋值
			}
			con.closeConnection();// 关闭链接
		} catch (SQLException eq) {
			eq.printStackTrace();
		}
		if (maxrows != 0) {// 判断如果有数据执行下面的方法
			// 按照抽号批次降序排列获得表tb_history中数据的SQL语句
			sql = "select * from tb_history order by number desc";
			rs = con.showAll(sql);// 执行SQL语句后获得的结果集
			try {
				// 为表格中每一行的单元格赋值
				while (rs.next()) {
					defaultModel.addRow(new Object[] { rs.getInt(2), rs.getInt(3),
							rs.getInt(4), rs.getInt(5),rs.getInt(6), rs.getInt(7),
							rs.getInt(8), rs.getInt(9), rs.getString(10) });
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 计算总页数
			maxPageNumber = (int)
					(maxrows % pageSize == 0 ? maxrows / pageSize : maxrows / pageSize + 1);
			DefaultTableModel newModel = new DefaultTableModel();// 创建新的表格模型
			// 定义表头
			Object[] obj=new Object[] { "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" };
			newModel.setColumnIdentifiers(obj);
			for (int i = 0; i<pageSize; i++) {
				// 根据页面大小来获得数据
				newModel.addRow((Vector)defaultModel.getDataVector().elementAt(i));
			}
			table.getTableHeader().setReorderingAllowed(false);
			table.setModel(newModel);// 设置表格模型
			firstPageButton.setEnabled(false);// 禁用“首页”按钮
			latePageButton.setEnabled(false);// 禁用“上一页”按钮
			nextPageButton.setEnabled(true);// 启用“下一页”按钮
			lastPageButton.setEnabled(true);// 启用“尾页”按钮
		} else {
			firstPageButton.setEnabled(false);// 禁用“首页”按钮
			latePageButton.setEnabled(false);// 禁用“上一页”按钮
			nextPageButton.setEnabled(false);// 禁用“下一页”按钮
			lastPageButton.setEnabled(false);// 禁用“尾页”按钮
		}
	}
	protected void do_btnChoose_actionPerformed(ActionEvent e) {
		ForecastAddframe forecastAddframe = new ForecastAddframe();
		forecastAddframe.setVisible(true);
	}
	// “首页”按钮添加动作事件的监听
	protected void do_firstPageButton_actionPerformed(ActionEvent e) {
		currentPageNumber = 1;// 将当前页码设置成1
		Vector dataVector = defaultModel.getDataVector();// 获得原表格模型中的数据
		DefaultTableModel newModel = new DefaultTableModel();// 创建新的表格模型
		// 定义表头
		newModel.setColumnIdentifiers(new Object[]
				{ "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" });
		for (int i = 0; i<pageSize; i++) {
			newModel.addRow((Vector) dataVector.elementAt(i));// 根据页面大小来获得数据
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(newModel);// 设置表格模型
		firstPageButton.setEnabled(false);// 禁用“首页”按钮
		latePageButton.setEnabled(false);// 禁用“上一页”按钮
		nextPageButton.setEnabled(true);// 启用“下一页”按钮
		lastPageButton.setEnabled(true);// 启用“尾页”按钮
	}
	// 新增的号码统计事件处理方法
	protected void do_btnTrend_actionPerformed(ActionEvent e) {
		SparBuoy sparBuoy = new SparBuoy();
		sparBuoy.setVisible(true);
	}
	// “尾页”按钮添加动作事件的监听
	protected void do_lastPageButton_actionPerformed(ActionEvent e) {
		currentPageNumber = maxPageNumber;// 将当前页面设置为末页
		Vector dataVector = defaultModel.getDataVector();// 获得原表格模型中的数据
		DefaultTableModel newModel = new DefaultTableModel();// 创建新的表格模型
		// 定义表头
		newModel.setColumnIdentifiers(new Object[]
				{ "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" });
		int lastPageSize = (int)
				(defaultModel.getRowCount() - pageSize * (maxPageNumber - 1));
		if (lastPageSize == maxrows) {
			for (int i = 0; i<pageSize; i++) {
				// 根据页面大小来获得数据
				newModel.addRow(
						(Vector) dataVector.elementAt((int) (pageSize * (maxPageNumber - 1) + i))
				);
			}
		} else {
			for (int i = 0; i<lastPageSize; i++) {
				// 根据页面大小来获得数据
				newModel.addRow(
						(Vector) dataVector.elementAt((int) (pageSize * (maxPageNumber - 1) + i))
				);
			}
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(newModel);// 设置表格模型
		firstPageButton.setEnabled(true);// 启用“首页”按钮
		latePageButton.setEnabled(true);// 启用“上一页”按钮
		nextPageButton.setEnabled(false);// 禁用“下一页”按钮
		lastPageButton.setEnabled(false);// 禁用“尾页”按钮
	}
	// “上一页”按钮添加动作事件的监听
	protected void do_latePageButton_actionPerformed(ActionEvent e) {
		currentPageNumber--;// 将当前页面减一
		Vector dataVector = defaultModel.getDataVector();// 获得原表格模型中的数据
		DefaultTableModel newModel = new DefaultTableModel();// 创建新的表格模型
		// 定义表头
		newModel.setColumnIdentifiers(new Object[]
				{ "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" });
		for (int i = 0; i<pageSize; i++) {
			// 根据页面大小来获得数据
			newModel.addRow(
					(Vector) dataVector.elementAt((int) (pageSize * (currentPageNumber - 1) + i))
			);
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(newModel);// 设置表格模型
		if (currentPageNumber == 1) {
			firstPageButton.setEnabled(false);// 禁用“首页”按钮
			latePageButton.setEnabled(false);// 禁用“上一页”按钮
		}
		nextPageButton.setEnabled(true);// 启用“下一页”按钮
		lastPageButton.setEnabled(true);// 启用“尾页”按钮
	}
	// “下一页”按钮添加动作事件的监听
	protected void do_nextPageButton_actionPerformed(ActionEvent e) {
		currentPageNumber++;// 将当前页面加一
		Vector dataVector = defaultModel.getDataVector();// 获得原表格模型中的数据
		DefaultTableModel newModel = new DefaultTableModel();// 创建新的表格模型
		// 定义表头
		newModel.setColumnIdentifiers(new Object[]
				{ "抽号批次", "第1位", "第2位", "第3位", "第4位", "第5位", "第6位", "第7位", "抽号时间" });
		if (currentPageNumber == maxPageNumber) {
			int lastPageSize = (int)
					(defaultModel.getRowCount() - pageSize * (maxPageNumber - 1));
			for (int i = 0; i<lastPageSize; i++) {
				// 根据页面大小来获得数据
				newModel.addRow(
						(Vector) dataVector.elementAt((int) (pageSize * (maxPageNumber - 1) + i))
				);
			}
			nextPageButton.setEnabled(false);// 禁用“下一页”按钮
			lastPageButton.setEnabled(false);// 禁用“尾页”按钮
		} else {
			for (int i = 0; i<pageSize; i++) {
				// 根据页面大小来获得数据
				newModel.addRow(
						(Vector) dataVector.elementAt((int) (pageSize * (currentPageNumber - 1) + i))
				);
			}
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(newModel);// 设置表格模型
		firstPageButton.setEnabled(true);// 启用“首页”按钮
		latePageButton.setEnabled(true);// 启用“上一页”按钮
	}



}



