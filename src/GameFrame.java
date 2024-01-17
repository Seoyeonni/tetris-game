import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class GameFrame extends JFrame {

	public GameFrame() {
		setTitle("Tetris"); // 프레임 타이틀 설정
		setSize(600, 700); // 프레임 크기 설정
		setResizable(false); // 프레임 크기 변경 불가 설정
		setLocationRelativeTo(null); // 화면 중앙에 프레임 생성
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 윈도우를 닫으면 프로그램 종료

		ScorePanel scorePanel = new ScorePanel(); // 점수판 패널 객체 생성
		GamePanel gamePanel = new GamePanel(scorePanel); // 게임 패널 객체 생성
		ControlPanel controlPanel = new ControlPanel(gamePanel); // 컨트롤 패널 객체 생성

		JSplitPane hPane = new JSplitPane(); // 스플릿팬 객체 생성
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 좌우로 배치
		hPane.setDividerLocation(400); // 분할바 위치 설정
		hPane.setEnabled(false); // 분할바 이동 불가 설정
		hPane.setLeftComponent(gamePanel); // 왼쪽에 게임 패널 부착

		JSplitPane vPane = new JSplitPane(); // 스플릿팬 객체 생성
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // 상하로 배치
		vPane.setDividerLocation(200); // 분할바 위치 설정
		vPane.setTopComponent(scorePanel); // 위쪽에 점수판 패널 부착
		vPane.setBottomComponent(controlPanel); // 아래쪽에 컨트롤 패널 부착
		hPane.setRightComponent(vPane); // 오른쪽에 스플릿팬 부착

		add(hPane); // 생성된 스플릿팬 부착
		
		setVisible(true); // 화면에 프레임 출력
	}
}
