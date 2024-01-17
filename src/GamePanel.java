import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {
	// 생성자 매개변수
	private ScorePanel scorePanel = null;

	// 게임 관련 객체
	private Map map = new Map();
	private Color[] colorArray = { new Color(0x3869e4), new Color(0x36369c), Color.YELLOW, Color.BLUE, Color.GREEN };
	private int colorIndex = 0;

	private int startMapX = 80; // 맵 왼쪽 끝(+20하면 벽 안쪽)
	private int startMapY = 200; // 맵 위쪽 끝(벽 안쪽)
	private int startBlockX = startMapX + 20;
	private int startBlockY = startMapY - 40;
	private int currentBlockX = startMapX + 20;
	private int currentBlockY = startMapY - 40;

	private int blockSize = 20; // 블럭 크기

	private boolean gameOn = false;

	private FallingThread fallingThread = null; // 블록 떨어지는 스레드 레퍼런스 생성

	public GamePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel; // 매개변수로 받은 객체 저장

		setLayout(null); // 배치관리자 삭제

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				// 이차원 배열에서의 블록 좌표
				int xIndex = (currentBlockX - startBlockX) / blockSize + 2;
				int yIndex = (currentBlockY - startBlockY) / blockSize;

				switch (keyCode) {
				case KeyEvent.VK_LEFT: { // 왼쪽 방향키
					if (map.getInfo(xIndex - 1, yIndex) == 0) { // 왼쪽이 벽이 아니면 이동
						currentBlockX -= 20;
						repaint();
					}
					break;
				}
				case KeyEvent.VK_RIGHT: { // 오른쪽 방향키
					if (map.getInfo(xIndex + 1, yIndex) == 0) { // 오른쪽이 벽이 아니면 이동
						currentBlockX += 20;
						repaint();
					}
					break;
				}
				case KeyEvent.VK_DOWN: { // 아래쪽 방향키
					if (yIndex < map.getMap().length - 2 && map.getInfo(xIndex, yIndex + 2) == 0) { // 아래아래가 벽이 아니면 이동,
																									// 인덱스 범위 고려
						currentBlockY += 20;
						repaint();
					}
					break;
				}
				case KeyEvent.VK_SPACE: { // 스페이스바
					if (yIndex < map.getMap().length - 2 && map.getInfo(xIndex, yIndex + 2) == 0) { // 아래아래가 벽이 아니면 이동,
																									// 인덱스 범위 고려
						while (yIndex < map.getMap().length - 2 && map.getInfo(xIndex, yIndex + 2) == 0) {
							currentBlockY += 20; // 아래로 이동
							yIndex = (currentBlockY - startBlockY) / blockSize;
						}
						repaint();
					}
					break;
				}
				}
			}
		});
	}
	
	@Override //배경
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon bg = new ImageIcon("tetris_background.png");
		Image img= bg.getImage();
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		drawMap(g);
		if (gameOn) // 게임 중이면
			drawBlock(g);
	}

	

	// 맵 그리기 함수
	private void drawMap(Graphics g) {
		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[y].length; x++) {
				g.setColor(colorArray[map.getMap()[y][x]]);
				g.fill3DRect(startMapX + blockSize * x, startMapY + blockSize * y, blockSize, blockSize, true);
			}
		}
	}

	// 블록 그리기 함수
	private void drawBlock(Graphics g) {
		g.setColor(colorArray[colorIndex]);
		g.fill3DRect(currentBlockX + blockSize, currentBlockY + blockSize, blockSize, blockSize, true);
	}

	// 게임 시작 함수
	public void startGame() {
		if (gameOn)
			return; // 게임이 진행 중일 때는 버튼 효과 없음
		gameOn = true; // 게임 상태 변경

		// 블록 위치 초기화
		currentBlockX = startBlockX;
		currentBlockY = startBlockY;

		nextBlock();
		repaint();
		fallingThread = new FallingThread(); // 블록 떨어지는 스레드 객체 생성
		fallingThread.start(); // 스레드 작동
	}

	// 다음 블록 설정하는 함수
	private void nextBlock() {
		brokeBlock(); // 블록 깨기

		// 블록 위치, 컬러 지정
		currentBlockX = (int) (Math.random() * 10) * blockSize + startBlockX - blockSize;
		currentBlockY = startBlockY;
		colorIndex = (int) (Math.random() * (colorArray.length - 2)) + 2;
	}

	// 블록 깨는 함수
	private void brokeBlock() {
		// 맵을 위에 한 줄 늘리고 백지로 만든 다음 블록이 생기면 게임 멈추기
		// stopGame();
		
		int cnt = 0; // 블록 깨진 횟수

		// visited 배열 초기화
		boolean[][] visited = new boolean[map.getMap().length][map.getMap()[0].length];
		for (int y = 0; y < visited.length; y++) {
			for (int x = 0; x < visited[y].length; x++) {
				visited[y][x] = false;
			}
		}

		// bfs
		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[y].length; x++) {
				// 빈칸이나 벽이 아니면, 방문하지 않은 블록이면
				if (map.getInfo(x, y) != 0 && map.getInfo(x, y) != 1 && !visited[y][x]) {
					Queue<Point> queue = new LinkedList<>(); // 큐 생성
					Vector<Point> v = new Vector<Point>(); // 벡터 생성
					Point p = new Point(x, y); // 현재 블록 좌표
					queue.add(p); // 큐에 추가
					v.add(p); // 벡터에 추가
					visited[y][x] = true; // 방문 기록
					while (!queue.isEmpty()) { // 큐가 비어있지 않을 때까지
						Point currentPoint = queue.poll();
						int currentX = currentPoint.x;
						int currentY = currentPoint.y;
						int element = map.getInfo(currentX, currentY);

						// 상
						if (currentY - 1 >= 0 && map.getInfo(currentX, currentY - 1) == element
								&& !visited[currentY - 1][currentX]) {
							Point childP = new Point(currentX, currentY - 1); // 상측 블록 좌표
							queue.add(childP); // 상측 블록 큐에 추가
							v.add(childP); // 상측 블록 벡터에 추가
							visited[currentY - 1][currentX] = true; // 방문 기록
						}
						// 하
						if (currentY + 1 < map.getMap().length && map.getInfo(currentX, currentY + 1) == element
								&& !visited[currentY + 1][currentX]) {
							Point childP = new Point(currentX, currentY + 1); // 하측 블록 좌표
							queue.add(childP); // 하측 블록 큐에 추가
							v.add(childP); // 하측 블록 벡터에 추가
							visited[currentY + 1][currentX] = true; // 방문 기록
						}
						// 좌
						if (currentX - 1 >= 0 && map.getInfo(currentX - 1, currentY) == element
								&& !visited[currentY][currentX - 1]) {
							Point childP = new Point(currentX - 1, currentY); // 좌측 블록 좌표
							queue.add(childP); // 좌측 블록 큐에 추가
							v.add(childP); // 좌측 블록 벡터에 추가
							visited[currentY][currentX - 1] = true; // 방문 기록
						}
						// 우
						if (currentX + 1 < map.getMap()[y].length && map.getInfo(currentX + 1, currentY) == element
								&& !visited[currentY][currentX + 1]) {
							Point childP = new Point(currentX + 1, currentY); // 우측 블록 좌표
							queue.add(childP); // 우측 블록 큐에 추가
							v.add(childP); // 우측 블록 벡터에 추가
							visited[currentY][currentX + 1] = true; // 방문 기록
						}
						// 좌상
						if (currentX - 1 >= 0 && currentY - 1 >= 0 && map.getInfo(currentX - 1, currentY - 1) == element
								&& !visited[currentY - 1][currentX - 1]) {
							Point childP = new Point(currentX - 1, currentY - 1); // 상측 블록 좌표
							queue.add(childP); // 상측 블록 큐에 추가
							v.add(childP); // 상측 블록 벡터에 추가
							visited[currentY - 1][currentX - 1] = true; // 방문 기록
						}
						// 좌하
						if (currentX - 1 >= 0 && currentY + 1 < map.getMap().length
								&& map.getInfo(currentX - 1, currentY + 1) == element
								&& !visited[currentY + 1][currentX - 1]) {
							Point childP = new Point(currentX - 1, currentY + 1); // 하측 블록 좌표
							queue.add(childP); // 하측 블록 큐에 추가
							v.add(childP); // 하측 블록 벡터에 추가
							visited[currentY + 1][currentX - 1] = true; // 방문 기록
						}
						// 우상
						if (currentX + 1 < map.getMap()[y].length && currentY - 1 >= 0
								&& map.getInfo(currentX + 1, currentY - 1) == element
								&& !visited[currentY - 1][currentX + 1]) {
							Point childP = new Point(currentX - 1, currentY); // 좌측 블록 좌표
							queue.add(childP); // 좌측 블록 큐에 추가
							v.add(childP); // 좌측 블록 벡터에 추가
							visited[currentY - 1][currentX + 1] = true; // 방문 기록
						}
						// 우하
						if (currentX + 1 < map.getMap()[y].length && currentY + 1 < map.getMap().length
								&& map.getInfo(currentX + 1, currentY + 1) == element
								&& !visited[currentY + 1][currentX + 1]) {
							Point childP = new Point(currentX + 1, currentY + 1); // 우측 블록 좌표
							queue.add(childP); // 우측 블록 큐에 추가
							v.add(childP); // 우측 블록 벡터에 추가
							visited[currentY + 1][currentX + 1] = true; // 방문 기록
						}
					}

					if (v.size() >= 3) { // 연속된 블록이 3개 이상이면
						cnt++;
						for (int i = 0; i < v.size(); i++) {
							Point removeP = v.get(i); // 깰 블록
							map.setInfo(removeP.x, removeP.y, 0); // 블록 깨기

							// 점수 증가
							int score = v.size(); // 깨진 블록 개수가
							if (v.size() > 3) // 3개를 초과하면
								score += (v.size() - 3) * (v.size() - 3); // 초과한 블록수의 제곱만큼 증가
							scorePanel.increaseScore(score);
						}
					}
					v.clear(); // 벡터 비우기
				}
			}
		}
		repaint();
		gravity(); // 공중에 있는 블록 아래로 내리기

		// 깨진 블록이 있으면
		if (cnt > 0) {
			try {
				Thread.sleep(500); // 잠시 멈춤
			} catch (InterruptedException e) {
			}
			brokeBlock(); // 블록 깨기 다시 
		}
	}

	// 중력 함수
	private void gravity() {
		for (int x = 0; x < map.getMap()[0].length; x++) {
			int cnt = 0; // 빈칸 개수 0개
			for (int y = map.getMap().length - 1; y >= 0; y--) { // 아래에서 위로
				if (map.getInfo(x, y) != 1) { // 벽이 아니면
					if (map.getInfo(x, y) == 0) // 빈칸이면
						cnt++; // 카운트 증가
					else { // 블록이면
						for (int i = 0; i < cnt; i++) {
							map.setInfo(x, y + cnt - i, map.getInfo(x, y - i)); // 빈칸 개수만큼 내리기
							map.setInfo(x, y - i, 0); // 위에를 빈칸으로 채우기
						}
						cnt = 0; // 빈칸 개수 초기화
					}
				}
			}
		}
		repaint();
		// 멈추기
	}

	// 게임 중지 함수
	public void pauseGame() {
		if (fallingThread != null && fallingThread.isAlive()) {
			fallingThread.suspend(); // 떨어지는 단어 스레드 일시정지
		}
	}

	// 게임 재시작 함수
	public void restartGame() {
		if (fallingThread != null && fallingThread.isAlive()) {
			fallingThread.resume();
		}
	}

	// 게임 리셋 함수
	public void resetGame() {
		gameOn = false; // 게임 상태 변경
		fallingThread.interrupt(); // 스레드 강제 종료

		// 블록 위치 초기화
		currentBlockX = startBlockX;
		currentBlockY = startBlockY;

		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[y].length; x++) {
				if (map.getMap()[y][x] != 0 && map.getMap()[y][x] != 1) // 빈 공간이나 벽이 아니면
					map.setInfo(x, y, 0);
			}
		}
		repaint();
	}
	
	// 게임 종료 함수
		private void stopGame() {
			fallingThread.interrupt(); // 스레드 강제 종료
		}

	// 블록 떨어지는 스레드
	private class FallingThread extends Thread {
		private int delay = 300; // 지연 시간

		@Override
		public void run() {
			while (true) {
				try {
					sleep(delay);

					// 이차원 배열에서의 블록 좌표
					int xIndex = (currentBlockX - startBlockX) / blockSize + 2;
					int yIndex = (currentBlockY - startBlockY) / blockSize;

					if (map.getInfo(xIndex, yIndex) != 0) { // 아래가 벽이면 스레드 종료
						if (yIndex > 0) { // 인덱스 범위 고려
							map.setInfo(xIndex, yIndex - 1, colorIndex);
							colorIndex = 0; // 블록 혼선 없애기 위함
							nextBlock(); // 다음 블록 생성
						}
					} else if (map.getInfo(xIndex, yIndex) != 0 && currentBlockY > startMapY) { // 맵이 넘쳤으면
						stopGame();
					} else
						currentBlockY += blockSize; // 블록이 한 칸씩 아래로 떨어짐

					repaint();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
