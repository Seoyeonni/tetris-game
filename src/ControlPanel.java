import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	// 생성자 매개변수
	private GamePanel gamePanel = null;

	// 버튼 객체
	private JButton startButton; // 시작 버튼
	private JButton pauseButton ; // 중지 버튼
	private JButton restartButton; // 재시작 버튼
	private JButton resetButton; // 리셋 버튼
	private JButton exitButton ; // 종료 버튼

	public ControlPanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setLayout(null); // 배치관리자 삭제
		
		//버튼 이미지
		ImageIcon start_img= new ImageIcon("start_button.png");
		ImageIcon stop_img= new ImageIcon("stop_button.png");
		ImageIcon restart_img= new ImageIcon("restart_button.png");
		ImageIcon reset_img= new ImageIcon("reset_button.png");
		ImageIcon exit_img= new ImageIcon("exit_button.png");
		ImageIcon roll_start= new ImageIcon("clicked_start.png");
		ImageIcon roll_stop= new ImageIcon("clicked_stop.png");
		ImageIcon roll_restart= new ImageIcon("clicked_restart.png");
		ImageIcon roll_reset= new ImageIcon("clicked_reset.png");
		ImageIcon roll_exit= new ImageIcon("clicked_exit.png");
		
		
		startButton= new JButton(start_img);
		pauseButton= new JButton(stop_img);
		restartButton= new JButton(restart_img);
		resetButton= new JButton(reset_img);
		exitButton= new JButton(exit_img);
		

		// 버튼 객체 추가
		startButton.setBounds(50, 30, start_img.getIconWidth(), start_img.getIconHeight());
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setRolloverIcon(roll_start);
		add(startButton);

		pauseButton.setBounds(50, 110, start_img.getIconWidth(), start_img.getIconHeight());
		pauseButton.setBorderPainted(false);
		pauseButton.setContentAreaFilled(false);
		pauseButton.setRolloverIcon(roll_stop);
		add(pauseButton);

		restartButton.setBounds(50, 190, start_img.getIconWidth(), start_img.getIconHeight());
		restartButton.setBorderPainted(false);
		restartButton.setContentAreaFilled(false);
		restartButton.setRolloverIcon(roll_restart);
		add(restartButton);

		resetButton.setBounds(50, 270, start_img.getIconWidth(), start_img.getIconHeight());
		resetButton.setBorderPainted(false);
		resetButton.setContentAreaFilled(false);
		resetButton.setRolloverIcon(roll_reset);
		add(resetButton);

		exitButton.setBounds(50, 350, start_img.getIconWidth(), start_img.getIconHeight());
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setRolloverIcon(roll_exit);
		add(exitButton);

		// 시작 버튼 리스너
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.startGame();
				gamePanel.requestFocus(); // 포커스 설정
			}
		});

		// 중지 버튼 리스너
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.pauseGame();
			}
		});

		// 재시작 버튼 리스너
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.restartGame();
				gamePanel.requestFocus(); // 포커스 설정
			}
		});

		// 리셋 버튼 리스너
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.resetGame();
			}
		});

		// 종료 버튼 리스너
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	@Override //배경
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("control_background.png");
		Image img= bg.getImage();
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}
