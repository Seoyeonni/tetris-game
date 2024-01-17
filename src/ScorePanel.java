import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ScorePanel extends JPanel {

	// 점수 관련 객체
	private int score = 0; // 점수
	private JLabel textLabel = new JLabel(); // 점수 레이블 객체 생성
	private JLabel scoreLabel = new JLabel(Integer.toString(score)); // scoreLabel 객체 생성

	public ScorePanel() {
		setLayout(null); // 배치관리자 삭제
		
		ImageIcon scoreImg= new ImageIcon("score_text.png"); //점수 이미지 생성
		textLabel.setIcon(scoreImg);

		// 점수 관련 객체 추가
		textLabel.setBounds(35, 55, scoreImg.getIconWidth(), scoreImg.getIconHeight());
		add(textLabel);

		scoreLabel.setForeground(Color.YELLOW);
		scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
		//scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setSize(100, 20);
		scoreLabel.setLocation(70, 100);
		add(scoreLabel);
	}
	
	@Override //배경
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("score_background.png");
		Image img= bg.getImage();
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
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
