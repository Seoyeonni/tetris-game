import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	// 생성자 매개변수
	private GamePanel gamePanel = null;

	// 버튼 객체
	private JButton startButton = new JButton("START"); // 시작 버튼
	private JButton pauseButton = new JButton("PAUSE"); // 중지 버튼
	private JButton restartButton = new JButton("RESTART"); // 재시작 버튼
	private JButton resetButton = new JButton("RESET"); // 리셋 버튼
	private JButton exitButton = new JButton("EXIT"); // 종료 버튼

	public ControlPanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setLayout(null); // 배치관리자 삭제

		// 버튼 객체 추가
		startButton.setBackground(new Color(192, 192, 192));
		startButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		startButton.setSize(100, 20);
		startButton.setLocation(40, 80);
		add(startButton);

		pauseButton.setBackground(new Color(192, 192, 192));
		pauseButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		pauseButton.setSize(100, 20);
		pauseButton.setLocation(40, 110);
		add(pauseButton);

		restartButton.setBackground(new Color(192, 192, 192));
		restartButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		restartButton.setSize(100, 20);
		restartButton.setLocation(40, 140);
		add(restartButton);

		resetButton.setBackground(new Color(192, 192, 192));
		resetButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		resetButton.setSize(100, 20);
		resetButton.setLocation(40, 170);
		add(resetButton);

		exitButton.setBackground(new Color(192, 192, 192));
		exitButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		exitButton.setSize(100, 20);
		exitButton.setLocation(40, 200);
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
}