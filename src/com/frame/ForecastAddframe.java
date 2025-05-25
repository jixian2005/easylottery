package com.frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;
import javax.swing.*;

import com.dao.ForecastDao;
import com.dao.HistoryDao;
import com.dao.LotteryValidate;

public class ForecastAddframe extends JDialog implements Runnable, ActionListener {
    private JPanel jpl;// 内容面板
    JButton bt1 = new JButton();// 显示随机选号第1位的按钮
    JButton bt2 = new JButton();// 显示随机选号第2位的按钮
    JButton bt3 = new JButton();// 显示随机选号第3位的按钮
    JButton bt4 = new JButton();// 显示随机选号第4位的按钮
    JButton bt5 = new JButton();// 显示随机选号第5位的按钮
    JButton bt6 = new JButton();// 显示随机选号第6位的按钮
    JButton bt7 = new JButton();// 显示随机选号第7位的按钮
    JButton but = new JButton("");// “随机选号”按钮
    private final JTextField sevenTextField = new JTextField();// “选号号码”文本框
    private final JLabel label = new JLabel("第");// “第”标签
    private final JLabel label_1 = new JLabel("批");// “批选号”标签
    private final JTextField numberTextField = new JTextField();// “选号批次”文本框
    private final JButton btnNewButton = new JButton("");// “确定”按钮
    private final JButton btnNewButton_1 = new JButton("");// “关闭”按钮
    private boolean bol = false; // 控制动画是否运行
    private int index = 0;       // 控制各个号码生成的时间点
    private int i = 0;           // 当前随机数
    private int a, b, c, d, e, f, g;

    public ForecastAddframe() {// 随机选号对话框的构造方法
        setModal(true);// 使随机选号对话框总在最前
        setTitle("随机选号");// 设置随机选号对话框的标题
        // 设置随机选号对话框的标题图标
        setIconImage(Toolkit.getDefaultToolkit().getImage(ForecastAddframe.class.getResource("/imgs/log.png")));
        setResizable(false);// 随机选号对话框不可改变大小
        HistoryDao his = new HistoryDao();// 实例化操作选号信息
        numberTextField.setText(his.selectNumber() + "");// 设置“选号批次”文本框中的内容

        //下面是选号批次的文本框，现在还没有内容，数据要再从数据库里取。
        numberTextField.setEditable(false);// 设置“选号批次”文本框不可编辑
        numberTextField.setBounds(331, 88, 64, 24);// 设置“选号批次”文本框的位置和宽高
        numberTextField.setColumns(10);// 设置“选号批次”文本框的宽度
        sevenTextField.setBounds(300, 220, 140, 27);// 设置“选号号码”文本框的位置和宽高
        sevenTextField.setColumns(10);// 设置“选号号码”文本框的宽度

        jpl = new JPanel();// 内容面板
        jpl.setLayout(new BorderLayout(0, 0));// 设置内容面板的布局为边界布局
        setContentPane(jpl);// 把内容面板置于随机选号对话框中
        BackgroundPanel pl = new BackgroundPanel();// 自定义背景面板
        pl.setImage(getToolkit().getImage(getClass().getResource("/imgs/001.png")));// 设置背景面板的图片
        jpl.add(pl, BorderLayout.CENTER);// 添加背景面板到内容面板
        // 设置“随机选号”按钮的图标
        but.setIcon(new ImageIcon(ForecastAddframe.class.getResource("/img_btn/111.png")));
        but.setBounds(200, 220, 62, 28);// 设置“随机选号”按钮的位置以及宽高
        pl.add(but);// 把“随机选号”按钮置于自定义背景面板中
        but.addActionListener(this);// 为“随机选号”按钮添加动作事件的监听
        this.getContentPane().add(pl);// 将随机选号对话框中的控件置于自定义背景面板中
        /*
         * 设置显示随机选号第1位~第7位的按钮的位置以及宽高， 并把显示随机选号第1位~第7位的按钮置于自定义背景面板中
         */
        bt1.setBounds(74, 128, 84, 63);
        pl.add(bt1);
        bt2.setBounds(159, 128, 84, 63);
        pl.add(bt2);
        bt3.setBounds(244, 128, 84, 63);
        pl.add(bt3);
        bt4.setBounds(329, 128, 84, 63);
        pl.add(bt4);
        bt5.setBounds(414, 128, 84, 63);
        pl.add(bt5);
        bt6.setBounds(499, 128, 84, 63);
        pl.add(bt6);
        bt7.setBounds(584, 128, 84, 63);
        pl.add(bt7);
        pl.add(sevenTextField);// 把“选号号码”文本框置于自定义背景面板中
        pl.add(numberTextField);// 把“选号批次”文本框置于自定义背景面板中
        label.setBounds(299, 90, 22, 18);// 设置“第”标签的位置以及宽高
        pl.add(label);// 把“第”标签置于自定义背景面板中
        label_1.setBounds(415, 90, 22, 18);// 设置“批”标签的位置以及宽高
        pl.add(label_1);// 把“批”标签置于自定义背景面板中

        JLabel label_10 = new JLabel("");//选号确认后显示的标签
        label_10.setBounds(480, 250, 62, 15);
        pl.add(label_10);

        // 设置“确定”按钮的图标
        btnNewButton.setIcon(new ImageIcon(ForecastAddframe.class.getResource("/img_btn/a02.png")));
        btnNewButton.setBounds(480, 220, 62, 28);// 设置“确定”按钮的位置和宽高
        btnNewButton.setEnabled(false);//选号前“确认”按钮不可用
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_btnNewButton_actionPerformed(e);// 为“确定”按钮添加动作事件的监听
                label_10.setText("选号成功！");// 设置“确定状态”标签内容
            }
        });
        pl.add(btnNewButton);// 把“确定”按钮置于自定义背景面板中
        // 设置“关闭”按钮的图标
        btnNewButton_1.setIcon(new ImageIcon(ForecastAddframe.class.getResource("/img_btn/a07.png")));
        btnNewButton_1.setBounds(330, 300, 62, 28);// 设置“关闭”按钮的位置和宽高
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_btnNewButton_1_actionPerformed(e);// 为“关闭”按钮添加动作事件的监听
            }
        });
        pl.add(btnNewButton_1);// 把“关闭”按钮置于自定义背景面板中
        this.setBounds(350, 100, 750, 420);// 随机选号对话框的位置和宽高
    }
    protected void do_but_actionPerformed(ActionEvent e2) {
        bt1.setText(""); // 设置显示随机选号第 1 位的按钮内容为空
        bt2.setText(""); // 设置显示随机选号第 2 位的按钮内容为空
        bt3.setText(""); // 设置显示随机选号第 3 位的按钮内容为空
        bt4.setText(""); // 设置显示随机选号第 4 位的按钮内容为空
        bt5.setText(""); // 设置显示随机选号第 5 位的按钮内容为空
        bt6.setText(""); // 设置显示随机选号第 6 位的按钮内容为空
        bt7.setText(""); // 设置显示随机选号第 7 位的按钮内容为空
        sevenTextField.setText(""); // 设置“选号号码”文本框内容为空
        bol = true;
        index = 0; // 为奖号变换时间赋值
        but.setEnabled(false); // 设置“随机选号”按钮不可用
        btnNewButton.setEnabled(true); // 设置“确认”按钮可用
        Thread th1 = new Thread(this); // 在随机选号对话框中创建线程
        th1.start(); // 启动线程，调用 thread.start() 时则表示开启线程，执行 run 方法
    }




    private void do_btnNewButton_1_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    private void do_btnNewButton_actionPerformed(ActionEvent e) {
        //		LotteryValidate validate = new LotteryValidate();// 实例化验证信息
        // “选号号码”本框输入的“选号号码”的格式正确
        ForecastDao fr = new ForecastDao();// 实例化操作随机选号信息
        Boolean b;
        b = fr.addForecastDao(
                Integer.parseInt(numberTextField.getText()), sevenTextField.getText()
        );// 获得添加随机选号的返回值
        if (b) {// 添加随机选号成功
            HistoryDao his = new HistoryDao();// 实例化操作抽号信息
            numberTextField.setText(his.selectNumber() + "");// 设置“抽号批次”文本框中的内容
        }


    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 实例化主窗体
                    ForecastAddframe frame = new ForecastAddframe();
                    frame.setVisible(true);// 使主窗体可见
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void run() {
        String s = "";// 存储随机生成的选号
        Random ram = new Random();// 随机数对象
        while (bol) {
            try {
                if (i>= 10) {// i表示奖号，所以i不能大于10
                    i = 0;
                }
                if (index< (500 - ram.nextInt(20))) {// 控制停止时间500毫秒减去20以内的随机数
                    a = i;// 获得随机选号第1位的数字
                    bt1.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第1位的摇奖结果
                }
                if (index< (700 - ram.nextInt(20))) {// 控制停止时间1000毫秒减去20以内的随机数
                    b = i;// 获得随机选号第2位的数字
                    bt2.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第2位的摇奖结果
                }
                if (index< (900 - ram.nextInt(20))) {// 控制停止时间1500毫秒减去20以内的随机数
                    c = i;// 获得随机选号第3位的数字
                    bt3.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第3位的摇奖结果
                }
                if (index< (1100 - ram.nextInt(20))) {// 控制停止时间2000毫秒减去20以内的随机数
                    d = i;// 获得随机选号第4位的数字
                    bt4.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第4位的摇奖结果
                }
                if (index< (1300 - ram.nextInt(20))) {// 控制停止时间3000毫秒减去20以内的随机数
                    e = i;// 获得随机选号第5位的数字
                    bt5.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第5位的摇奖结果
                }
                if (index< (1500 - ram.nextInt(20))) {// 控制停止时间4000毫秒减去20以内的随机数
                    f = i;// 获得随机选号第6位的数字
                    bt6.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第6位的摇奖结果
                }
                if (index< (1700 - ram.nextInt(20))) {// 控制停止时间5000毫秒减去20以内的随机数
                    g = i;// 获得随机选号第7位的数字
                    bt7.setIcon(
                            new ImageIcon(ForecastAddframe.class.getResource("/imgs/" + i + ".png"))
                    );// 通过循环变换图片以达到随机选号第7位的摇奖结果
                }
                switch (index) {// 以奖号变换时间为参数的多分支语句
                    case 500:// 500毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的空内容
                        sevenTextField.setText(s + a);// 把第1位的值添加到“选号号码”文本框中
                        break;
                    case 700:// 1000毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的第1位奖号
                        sevenTextField.setText(s + b);// 把第2位的值添加到“选号号码”文本框中
                        break;
                    case 900:// 1500毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的前2位奖号
                        sevenTextField.setText(s + c);// 把第3位的值添加到“选号号码”文本框中
                        break;
                    case 1100:// 2000毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的前3位奖号
                        sevenTextField.setText(s + d);// 把第4位的值添加到“选号号码”文本框中
                        break;
                    case 1300:// 3000毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的前4位奖号
                        sevenTextField.setText(s + e);// 把第5位的值添加到“选号号码”文本框中
                        break;
                    case 1500:// 4000毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的前5位奖号
                        sevenTextField.setText(s + f);// 把第6位的值添加到“选号号码”文本框中
                        break;
                    case 1700:// 5000毫秒时
                        s = sevenTextField.getText();// 获取“选号号码”文本框中的前6位奖号
                        sevenTextField.setText(s + g);// 把第7位的值添加到“选号号码”文本框中
                        bol = false;//
                        but.setEnabled(true);// 设置“随机选号”按钮可用
                        break;
                }
                i++;// i = i + 1
                Thread.sleep(0);// 线程不休眠
                index++;// index = index + 1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == but) {
            do_but_actionPerformed(e); // 调用 do_but_actionPerformed 方法
        }
    }

}