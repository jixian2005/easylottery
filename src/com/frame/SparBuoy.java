package com.frame;

import com.allPanel.Apanel;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

import java.awt.Toolkit;
import javax.swing.JDialog;

public class SparBuoy extends JDialog {
    JTabbedPane tp = new JTabbedPane();// 创建选项卡面板
	public SparBuoy() {// 号码统计对话框的构造方法
        setTitle("号码统计");// 设置号码统计对话框的标题
        setResizable(false);// 不可改变号码统计对话框的大小
        setIconImage(Toolkit.getDefaultToolkit().getImage(SparBuoy.class.getResource("/imgs/log.png")));// 设置号码统计对话框的窗体图标
        // 把显示第一位~第七位抽号号码的走势面板添加到选项卡面板中
        tp.add("第一位",new Apanel("a"));
        tp.add("第二位",new Apanel("b"));
        tp.add("第三位",new Apanel("c"));
        tp.add("第四位",new Apanel("d"));
        tp.add("第五位",new Apanel("e"));
        tp.add("第六位",new Apanel("f"));
        tp.add("第七位",new Apanel("g"));
        this.getContentPane().add(tp);// 把选项卡面板添加到号码统计对话框的内容面板中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 这是号码统计对话框的关闭方式
        this.setBounds(450, 100, 563, 593);// 设置号码统计对话框的位置和宽高
    }
}
