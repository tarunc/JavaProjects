import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class Checkers extends JFrame {
	public static final int EASY = 1, MED = 2, HARD = 3;
	int level = EASY;

	public Checkers(int lvl) {
		level = lvl;
		setContentPane(new Board(lvl));
		setTitle("Checkers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocation(100, 100);
		setVisible(true);
	}

	public static void main(String[] args) {
		// new Checkers(Checkers.EASY);
		Toolkit tools = Toolkit.getDefaultToolkit();
		Menu l = new Menu(tools.getScreenSize());
	}
}

class Board extends JPanel implements MouseMotionListener {
	Cell[][] cells = new Cell[8][8];
	Paint gp = new GradientPaint(0, 0, Color.cyan, 25, 50, Color.blue.darker(),
			true);
	Paint gp2 = new GradientPaint(0, 0, Color.black.brighter(), 0, 50,
			Color.green, true);
	Paint gp3 = new GradientPaint(0, 0, Color.yellow, 0, 50,
			Color.orange.darker(), true);
	BasicStroke bs1 = new BasicStroke(1);
	BasicStroke bs2 = new BasicStroke(3);
	BasicStroke bs5 = new BasicStroke(5);
	int width, height, rded = 0, bded = 0, mx, my, turn = 0, lvl;
	DragEvent drag = new DragEvent();
	Image img;

	public Board(int lev) {
		lvl = lev;
		setLayout(new GridLayout(8, 8, 2, 2));
		img = Toolkit.getDefaultToolkit().getImage("king");
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
					if (i < 2) {
						cells[i][j] = new Cell(true, gp3, bs2, 1, i, j, img);
					} else if (i > 5) {
						cells[i][j] = new Cell(true, gp3, bs2, 2, i, j, img);
					} else {
						cells[i][j] = new Cell(true, gp3, bs2, i, j, img);
					}
				} else {
					cells[i][j] = new Cell(false, gp2, bs2, i, j, img);
				}
				this.add(cells[i][j]);
				cells[i][j].addMouseMotionListener(this);
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (drag.drag) {
			drag.x = e.getX() + drag.addx - cells[0][0].getWidth() / 2;
			drag.y = e.getY() + drag.addy - cells[0][0].getHeight() / 2;
			repaint();
		}
	}

	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		width = this.getWidth();
		height = this.getHeight();
		g.setPaint(gp);
		g.fillRect(0, 0, width, height);
		paintChildren(g);
		if (drag.drag) {
			if (drag.p.p == 1) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}
			g.fillOval(drag.x, drag.y, cells[0][0].getWidth(),
					cells[0][0].getHeight());
			if (drag.p.king) {
				g.drawImage(img, drag.x + 10, drag.y,
						cells[0][0].getWidth() - 20,
						cells[0][0].getHeight() - 20, this);
			}
		}
	}

	public void FindAndPlace(MouseEvent e) {
		int x = -5, y = -5;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].ison) {
					x = j;
					y = i;
					i = cells.length;
					break;
				}
			}
		}
		if (y == drag.i && x == drag.j) {
			cells[drag.i][drag.j].peice.p = drag.p.p;
			cells[drag.i][drag.j].peice.king = drag.p.king;
		} else {
			if (moved(y, x)) {
				// System.out.println(""+lvl);
				if (lvl > 0) {
					computerMove();
				}
			}
		}
		this.repaint();
	}

	public void computerMove() {
		calcMoveable();
		if (lvl == Checkers.EASY) {
			genEasy();
		} else if (lvl == Checkers.MED) {
			genMed();
		} else if (lvl == Checkers.HARD) {
			genHard();
		} else {
			// throw new Exception("Wrong");
		}
	}

	public void calcMoveable() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].peice.p != 0) {
					cells[i][j].peice.moveable = isMoveable(i, j);
					System.out.println("moved " + "i=" + i + " j=" + j
							+ " move=" + cells[i][j].peice.moveable);
				}
			}
		}
	}

	public boolean setDragMove(int i, int j, int x, int y) {
		drag.p.p = cells[i][j].peice.p;
		drag.p.king = cells[i][j].peice.king;
		cells[i][j].peice.p = 0;
		cells[i][j].peice.king = false;
		drag.i = i;
		drag.j = j;
		if (moved(x, y)) {
			return true;
		} else {
			cells[i][j].peice.p = drag.p.p;
			cells[i][j].peice.king = drag.p.king;
			return false;
		}
	}

	public void genEasy() {
		ArrayList<Data> data = new ArrayList<Data>();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].peice.p == 2) {
					// System.out.println(i+" "+j+" "+cells[i][j].peice.moveable);
					data.add(new Data(i, j, cells[i][j].peice.moveable));
				}
			}
		}
		// int highest=(((Integer)(data.get(0))).move),index;
		Data highest;
		for (int i = 1; i < data.size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				// System.out.println(data.get(i).move+" "+data.get(j).move);
				if ((((Data) data.get(j)).move) < (((Data) (data.get(i))).move)) {
					highest = (((Data) (data.get(j))));
					data.set(j, data.get(i));
					data.set(i, highest);
				}
				/*
				 * if((((Data)data.get(j)).i)>(((Data)(data.get(i))).i)){
				 * highest=(((Data)(data.get(j)))); data.set(j,data.get(i));
				 * data.set(i,highest); }
				 */
			}
		}
		int x = -1, y = -1;
		for (int i = 0; i < data.size(); i++) {
			highest = data.get(i);
			System.out.println("i=" + highest.i + " j=" + highest.j + " move="
					+ highest.move);
		}
		for (int i = 0; i < data.size(); i++) {
			highest = data.get(i);
			System.out.println("current i=" + highest.i + " j=" + highest.j
					+ " move=" + highest.move);
			switch (highest.move) {
			// case
			// 0:System.out.println("Can't make a move");System.exit(0);break;
			case 1:
				x = 1;
				y = 1;
				break;
			case 2:
				x = -1;
				y = 1;
				break;
			case 3:
				x = -1;
				y = -1;
				break;
			case 4:
				x = 1;
				y = -1;
				break;
			case 5:
				x = -2;
				y = -2;
				break;
			case 6:
				x = -2;
				y = 2;
				break;
			case 7:
				x = 2;
				y = -2;
				break;
			case 8:
				x = 2;
				y = 2;
				break;
			}
			if (setDragMove(highest.i, highest.j, highest.i + x, highest.j + y)) {
				return;
			} else {
				System.out.println("no");
			}
		}
		System.out.println("cant make a move");
	}

	public int isMoveable(int i, int j) {
		int is = 0;
		try {
			if (cells[i + 1][j + 1].peice.p == 0) {
				is = 1;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (cells[i - 1][j + 1].peice.p == 0) {
				is = 2;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (cells[i - 1][j - 1].peice.p == 0) {
				is = 3;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (cells[i + 1][j - 1].peice.p == 0) {
				is = 4;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// if(is<5){
		try {
			System.out.print("5 " + i + " " + j + " "
					+ cells[i - 1][j - 1].peice.p + " " + cells[i][j].peice.p
					+ " " + cells[i - 1][j - 1].peice.p != 0 + " "
					+ cells[i - 2][j - 2].peice.p);
			if (cells[i - 1][j - 1].peice.p != cells[i][j].peice.p
					&& cells[i - 1][j - 1].peice.p != 0
					&& 0 == cells[i - 2][j - 2].peice.p) {
				is = 5;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			System.out.print("6 " + i + " " + j + " "
					+ cells[i - 1][j + 1].peice.p + " " + cells[i][j].peice.p
					+ " " + cells[i - 1][j + 1].peice.p != 0 + " "
					+ cells[i - 2][j + 2].peice.p);
			if (cells[i - 1][j + 1].peice.p != cells[i][j].peice.p
					&& cells[i - 1][j + 1].peice.p != 0
					&& 0 == cells[i - 2][j + 2].peice.p) {
				is = 6;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			System.out.print("7 " + i + " " + j + " "
					+ cells[i + 1][j - 1].peice.p + " " + cells[i][j].peice.p
					+ " " + cells[i + 1][j - 1].peice.p != 0 + " "
					+ cells[i + 2][j - 2].peice.p);
			if (cells[i + 1][j - 1].peice.p != cells[i][j].peice.p
					&& cells[i + 1][j - 1].peice.p != 0
					&& 0 == cells[i + 2][j - 2].peice.p) {
				is = 7;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			System.out.print("8 " + i + " " + j + " "
					+ cells[i + 1][j + 1].peice.p + " " + cells[i][j].peice.p
					+ " " + cells[i + 1][j + 1].peice.p != 0 + " "
					+ cells[i + 2][j + 2].peice.p);
			if (cells[i + 1][j + 1].peice.p != cells[i][j].peice.p
					&& cells[i + 1][j + 1].peice.p != 0
					&& 0 == cells[i + 2][j + 2].peice.p) {
				is = 8;
				System.out.println(is);
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		System.out.println("final" + is);
		return is;
	}

	public void genHard() {

	}

	public void genMed() {

	}

	/*
	 * public void generateAllPossibe(){ for(int i=0;i<cells.length;i++){
	 * for(int j=0;j<cells.length;j++){ if(cells[i][j].peice.p!=0){
	 * cells[i][j].peice.moveable=0<isAround(); } } } } public void
	 * generateEasyMove(){ for(int i=0;i<1000;i++){
	 * if(setDragMove((int)(Math.random
	 * ()*cells.length),(int)(Math.random()*cells.length))){ return; } }
	 * System.exit(0); }
	 */
	public boolean moved(int y, int x) {
		boolean is = false;
		try {
			if ((turn % 2 == drag.p.p - 1)) {
				int qq = isAround(y, x);
				// System.out.println(qq);
				if (cells[y][x].peice.p <= 0
						&& qq > 0
						&& ((drag.i <= y && drag.p.p == 1)
								|| (drag.i >= y && drag.p.p == 2) || (drag.p.king))
						&& cells[y][x].color) {
					switch (qq) {
					case 5:
						cells[y - 1][x - 1].peice.p = 0;
						cells[y - 1][x - 1].peice.king = false;
						break;
					case 6:
						cells[y - 1][x + 1].peice.p = 0;
						cells[y - 1][x + 1].peice.king = false;
						break;
					case 7:
						cells[y + 1][x - 1].peice.p = 0;
						cells[y + 1][x - 1].peice.king = false;
						break;
					case 8:
						cells[y + 1][x + 1].peice.p = 0;
						cells[y + 1][x + 1].peice.king = false;
						break;
					}
					addKilled(qq);
					cells[y][x].peice.p = drag.p.p;
					cells[y][x].peice.king = drag.p.king;
					turn++;
					// cells[y][x].repaint();
					endMove(y, x);
					is = true;
				} else {
					cells[drag.i][drag.j].peice.p = drag.p.p;
					cells[drag.i][drag.j].peice.king = drag.p.king;
				}
			}
		} catch (Exception evt) {
			System.out.println("caught\n" + evt);
			cells[drag.i][drag.j].peice.p = drag.p.p;
			cells[drag.i][drag.j].peice.king = drag.p.king;
		}
		this.repaint();
		return is;
	}

	public void addKilled(int qq) {
		if (qq >= 5) {
			if (drag.p.p == 1) {
				rded++;
			} else if (drag.p.p == 2) {
				bded++;
			}
		}
		if (rded == 8) {
			System.exit(0);
		} else if (bded == 8) {
			System.exit(0);
		}
	}

	public void endMove(int i, int j) {
		// System.out.println(cells[i][j].peice.p+" "+i+" "+j);
		if (cells[i][j].peice.p == 1 && i == 7) {
			cells[i][j].peice.king = true;
		} else if (cells[i][j].peice.p == 2 && i == 0) {
			cells[i][j].peice.king = true;
		}
	}

	public int isAround(int i, int j) {
		int is = 0;
		try {
			if (i - 1 == drag.i && j - 1 == drag.j) {
				is = 1;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (i - 1 == drag.i && j + 1 == drag.j) {
				is = 2;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (i + 1 == drag.i && j - 1 == drag.j) {
				is = 3;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			if (i + 1 == drag.i && j + 1 == drag.j) {
				is = 4;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// if(is<5){
		try {
			if (cells[i - 1][j - 1].peice.p != drag.p.p
					&& cells[i - 1][j - 1].peice.p != 0
					&& cells[drag.i][drag.j] == cells[i - 2][j - 2]) {
				is = 5;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			if (cells[i - 1][j + 1].peice.p != drag.p.p
					&& cells[i - 1][j + 1].peice.p != 0
					&& cells[drag.i][drag.j] == cells[i - 2][j + 2]) {
				is = 6;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			if (cells[i + 1][j - 1].peice.p != drag.p.p
					&& cells[i + 1][j - 1].peice.p != 0
					&& cells[drag.i][drag.j] == cells[i + 2][j - 2]) {
				is = 7;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		// if(is<5){
		try {
			if (cells[i + 1][j + 1].peice.p != drag.p.p
					&& cells[i + 1][j + 1].peice.p != 0
					&& cells[drag.i][drag.j] == cells[i + 2][j + 2]) {
				is = 8;
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		// }
		return is;
	}

	class Cell extends JComponent {
		Peice peice;
		int i, j;
		boolean color = true, ison = false;
		Paint p;
		BasicStroke bs;
		Image img;

		public Cell(boolean b, Paint gp, BasicStroke ba, int c, int x, int y,
				Image cc) {
			color = b;
			i = x;
			j = y;
			p = gp;
			peice = new Peice(c);
			bs = ba;
			img = cc;
			this.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					ison = true;
					repaint();
				}

				public void mouseExited(MouseEvent e) {
					ison = false;
					repaint();
				}

				public void mouseReleased(MouseEvent e) {
					if (peice.p != 0) {
						drag.drag = false;
						FindAndPlace(e);
					}
				}

				public void mousePressed(MouseEvent e) {
					if (peice.p != 0 && ((turn % 2) == (peice.p - 1))) {
						drag.drag = true;
						drag.x = e.getX();
						drag.y = e.getY();
						drag.addx = getX();
						drag.addy = getY();
						drag.p.p = peice.p;
						drag.p.king = peice.king;
						drag.i = i;
						drag.j = j;
						peice.p = -1;
						peice.king = false;
					}
				}
			});
		}

		public Cell(boolean b, Paint gp, BasicStroke ba, int x, int y, Image cc) {
			this(b, gp, ba, 0, x, y, cc);
		}

		public void paintComponent(Graphics gr) {
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D) gr;
			g.setPaint(p);
			g.fillRect(0, 0, getWidth(), getHeight());
			if (peice.p == 1) {
				g.setColor(Color.red);
				g.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
			} else if (peice.p == 2) {
				g.setColor(Color.black);
				g.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
			}
			// System.out.println(peice.p);
			if (peice.p != 0 && peice.king) {
				g.setColor(Color.cyan);
				g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
				g.drawImage(img, 10, 10, getWidth() - 20, getHeight() - 20,
						this);
			}
			if (ison) {
				g.setColor(Color.cyan);
				g.setStroke(bs);
				g.drawRect(0, 0, getWidth(), getHeight());
			}
		}

		public void setBackPaint(Paint c) {
			p = c;
		}
	}
}

class Data {
	int i, j, move;

	public Data(int x, int y, int moved) {
		i = x;
		j = y;
		move = moved;
	}
}

class Peice {
	int p = 0, moveable = 0;
	boolean king = false;

	public Peice(int c) {
		p = c;
	}
}

class DragEvent {
	Peice p = new Peice(0);
	int x, y, addx, addy, i, j;
	boolean drag = false;
}

class Menu extends JFrame implements ActionListener {
	JTextField pl1 = new JTextField();
	JTextField pl11 = new JTextField();
	JTextField pl2 = new JTextField();
	TButton start = new TButton("Start");
	TButton start1 = new TButton("Start");
	TButton close = new TButton("X");
	TButton icon = new TButton("-");
	JRadioButton sp = new JRadioButton("Single Player", false);
	JRadioButton mp = new JRadioButton("Multiplayer", true);
	CardLayout card = new CardLayout();
	JPanel bottom = new JPanel(card);
	SText dis = new SText(
			Color.green.darker(),
			Color.cyan,
			new Font("Arial", Font.BOLD, 12),
			3,
			50,
			"Welcome to Checkers, the game!!!                                                                                          Created by Tarun Chaudhry, with special thanks to Vishal!!!                                                                                         Enjoy the game!!!                 ");

	public Menu(Dimension d) {
		this.setSize(400, 210);
		this.setLocation(d.width / 2 - 200, d.height / 2 - 100);
		this.setTitle("Checkers");
		this.setResizable(false);
		this.setUndecorated(true);
		JPanel pane = new JPanel();
		JPanel top = new JPanel();
		JRadioButton easy = new JRadioButton("Beginner", false);
		JRadioButton med = new JRadioButton("Getting there", true);
		JRadioButton hard = new JRadioButton("Impossible", false);
		JLabel comp = new JLabel("What level should the computer play at?");
		JLabel j1 = new JLabel("Player 1, enter your name:");
		JLabel j11 = new JLabel("Player 1, enter your name:");
		JLabel j2 = new JLabel("Player 2, enter your name:");
		JSeparator sep = new JSeparator();
		top.setBackground(Color.green);
		top.setLayout(null);
		top.add(mp);
		top.add(sp);
		top.add(icon);
		top.add(dis);
		top.add(sep);
		top.add(close);
		sp.setBounds(50, 25, 125, 25);
		mp.setBounds(225, 25, 125, 25);
		mp.setBackground(Color.green);
		sp.setBackground(Color.green);
		easy.setBackground(Color.green);
		med.setBackground(Color.green);
		hard.setBackground(Color.green);
		pane.setBackground(Color.green);
		pane.setLayout(null);
		JPanel b1 = new JPanel(null);
		JPanel b2 = new JPanel(null);
		b1.add(comp);
		b1.add(easy);
		b1.add(med);
		b1.add(hard);
		b2.add(pl11);
		b2.add(start1);
		b2.add(j11);
		b2.add(pl2);
		b2.add(j2);
		b1.add(pl1);
		b1.add(start);
		b1.add(j1);
		pane.add(top);
		b1.setBackground(Color.green);
		b2.setBackground(Color.green);
		bottom.add(b1, "0");
		bottom.add(b2, "1");
		bottom.setBackground(Color.green);
		pane.add(bottom);
		bottom.setBounds(0, 50, 400, 160);
		this.setContentPane(pane);
		top.setBounds(0, 0, 400, 50);
		close.setBounds(380, 5, 10, 10);
		icon.setBounds(360, 5, 10, 10);
		sep.setBounds(0, 21, 400, 4);
		dis.setBounds(0, 0, 355, 20);
		comp.setBounds(25, 50, 350, 25);
		easy.setBounds(25, 75, 100, 25);
		med.setBounds(125, 75, 110, 25);
		hard.setBounds(260, 75, 100, 25);
		pl1.setBounds(175, 25, 200, 20);
		pl2.setBounds(175, 50, 200, 20);
		j1.setBounds(10, 25, 165, 25);
		j2.setBounds(10, 50, 165, 25);
		start.setBounds(250, 105, 100, 25);
		pl11.setBounds(175, 25, 200, 20);
		start1.setBounds(250, 105, 100, 25);
		j11.setBounds(10, 25, 165, 25);
		this.setVisible(true);
		dis.start();
		ButtonGroup bg = new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(easy);
		bg2.add(med);
		bg2.add(hard);
		bg.add(sp);
		bg.add(mp);
		close.addActionListener(this);
		icon.addActionListener(this);
		start1.addActionListener(this);
		start.addActionListener(this);
		mp.addActionListener(this);
		sp.addActionListener(this);
		pl1.addActionListener(this);
		pl11.addActionListener(this);
		pl2.addActionListener(this);
		card.next(bottom);
		dragListener drag = new dragListener(this);
		pl11.requestFocus();
	}

	public void actionPerformed(ActionEvent e) {
		Object com = e.getSource();
		if (com == close) {
			System.exit(0);
		} else if (com == icon) {
			this.setExtendedState(JFrame.ICONIFIED);
		} else if (com == sp) {
			card.next(bottom);
		} else if (com == mp) {
			card.previous(bottom);
		} else if (com == start) {
			start(Checkers.EASY);
		} else if (com == pl2) {

		} else if (com == pl1) {

		} else if (com == pl11) {
			pl2.requestFocus();
		} else if (com == start1) {
			start(0);
		}
	}

	protected void start(int lvl) {
		this.dispose();
		System.gc();
		new Checkers(lvl);
	}
}

class dragListener {
	JFrame o;
	int xScr, yScr, xPosn, yPosn;
	boolean drag;

	public dragListener(JFrame or) {
		o = or;
		setMouseListening();
		setMouseMotionListening();
	}

	private void setMouseListening() {
		o.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = o.getLocationOnScreen();
				xScr = p.x;
				yScr = p.y;
				xPosn = e.getX();
				yPosn = e.getY();
				if (yPosn > 0 && yPosn < 25) {
					drag = true;
					// o.setCursor(JFrame.MOVE_CURSOR );
				} else {
					drag = false;
					// o.setCursor(JFrame.DEFAULT_CURSOR );
				}
			}
		});
	}

	private void setMouseMotionListening() {
		o.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (drag /* && !o.isIconified() */) {
					xScr += (e.getX() - xPosn);
					yScr += (e.getY() - yPosn);
					o.setLocation(xScr, yScr);
				}
			}
		});
	}
}

class SText extends JPanel implements ActionListener {
	javax.swing.Timer timer;
	String message = "Tarun Rules!";
	int messagePosition = -1, messageHeight, messageWidth, charWidth, times;

	public SText(Color back, Color fore, Font fn, int cw, int time, String mesag) {
		this.setBackground(back);
		this.setForeground(fore);
		message = mesag;
		this.setFont(fn);
		FontMetrics fm = this.getFontMetrics(fn);
		messageWidth = fm.stringWidth(message);
		messageHeight = fm.getAscent();
		charWidth = cw;
		times = time;
		Dimension dddd = new Dimension(600, 75);
		setMinimumSize(dddd);
		setPreferredSize(dddd);
	}

	public void change(String m) {
		message = m;
		FontMetrics fm = this.getFontMetrics(this.getFont());
		messageWidth = fm.stringWidth(message);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(message, getWidth() - messagePosition, getHeight() / 2
				+ messageHeight / 2);
	}

	public void start() {
		if (timer == null) {
			timer = new javax.swing.Timer(times, this);
			timer.start();
		} else {
			timer.restart();
		}
	}

	public void stop() {
		timer.stop();
	}

	public void actionPerformed(ActionEvent evt) {
		messagePosition += charWidth;
		if (getSize().width - messagePosition + messageWidth < 0) {
			messagePosition = 0;
		}
		repaint();
	}
}

class TButton extends AbstractButton implements MouseListener {
	boolean ison;
	String text;
	ActionEvent evt;
	BasicStroke bs = new BasicStroke(5);
	GradientPaint gp = new GradientPaint(0, 0, Color.orange, 25, 50,
			Color.yellow, true);
	GradientPaint gp1 = new GradientPaint(0, 0, Color.green, 25, 50,
			Color.black, true);
	Font f = new Font("Arail", Font.BOLD, 12);
	FontMetrics fm = /* Toolkit.getDefaultToolkit(). */getFontMetrics(f);
	int offset = 0, sw;

	public TButton(String tex) {
		text = tex;
		sw = fm.stringWidth(text) / 2;
		evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
		this.addMouseListener(this);
		setVisible(true);
	}

	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setPaint(gp);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		g.setPaint(gp1);
		if (ison) {
			g.setStroke(bs);
			g.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
		}
		g.setFont(f);
		g.drawString(text, this.getWidth() / 2 - sw + offset, getHeight() / 2
				+ 5 + offset);
	}

	public void setText(String tex) {
		text = tex;
		sw = fm.stringWidth(text) / 2;
		evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
		repaint();
	}

	public void setFont(Font fo) {
		f = fo;
		fm = /* Toolkit.getDefaultToolkit(). */getFontMetrics(f);
		sw = fm.stringWidth(text) / 2;
	}

	public Font getFont() {
		return f;
	}

	public String getText() {
		return text;
	}

	public void mouseEntered(MouseEvent e) {
		ison = true;
		fireStateChanged();
		repaint();
	}

	public void doClick() {
		this.fireActionPerformed(evt);
	}

	public void mouseExited(MouseEvent e) {
		ison = false;
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		offset = 0;
		this.fireActionPerformed(evt);
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		offset = 5;
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}
}