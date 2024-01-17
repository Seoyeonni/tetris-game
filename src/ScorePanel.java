import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ScorePanel extends JPanel {

	// 점수 관련 객체
	private int score = 0; // 점수
	private JLabel textLabel = new JLabel("SCORE"); // 점수 레이블 객체 생성
	private JLabel scoreLabel = new JLabel(Integer.toString(score)); // scoreLabel 객체 생성

	public ScorePanel() {
		setLayout(null); // 배치관리자 삭제

		// 점수 관련 객체 추가
		textLabel.setForeground(new Color(0, 0, 0));
		textLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setSize(50, 20);
		textLabel.setLocation(50, 50);
		add(textLabel);

		scoreLabel.setForeground(new Color(0, 0, 0));
		scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		scoreLabel.setSize(50, 20);
		scoreLabel.setLocation(110, 50);
		add(scoreLabel);
	}

	// 점수 설정 함수
	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText(Integer.toString(score)); // scoreLabel 갱신
	}

	// 점수 증가 함수
	public void increaseScore(int score) {
		this.score += score;
		scoreLabel.setText(Integer.toString(this.score)); // scoreLabel 갱신
	}

	// 점수 감소 함수
	public void decreaseScore(int score) {
		this.score -= score;
		scoreLabel.setText(Integer.toString(this.score)); // scoreLabel 갱신
	}
}