import java.awt.Color;

public class Map {

	private Color[] colorArray = { Color.WHITE, Color.GRAY, Color.YELLOW, Color.BLUE, Color.GREEN };

	// for문으로 변경하기
	private int[][] map = null;

	public Map() {
		map = new int[21][12];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				if (x == 0 || x == map[y].length - 1)
					map[y][x] = 1; // 기둥벽
				else if (y == map.length - 1)
					map[y][x] = 1; // 바닥벽
				else
					map[y][x] = 0; // 빈칸
			}
		}
	}

	public int[][] getMap() {
		return map;
	}

	public int getInfo(int x, int y) {
		return map[y][x];
	}

	public void setInfo(int x, int y, int colorIndex) {
		map[y][x] = colorIndex;
	}
}