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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Uno {

	public static Deck deck;
	public static final int COLOR_GREEN = 1;
	public static final int COLOR_YELLOW = 2;
	public static final int COLOR_RED = 3;
	public static final int COLOR_BLUE = 4;
	public static final int COLOR_WILD = 5;
	public static final int COLOR_WILD4 = 6;
	public static final int ACTION_D2 = 14;
	public static final int ACTION_SKIP = 13;
	public static final int ACTION_REV = 12;
	public static final int NUM_CARD_HAND = 7;
	public static ArrayList<Player> players;
	public static int nextTurn;
	public static Card prevCard;
	public static boolean forward;
	public static Scanner scan;

	public Uno(int playNum) {
		nextTurn = 0;
		forward = true;
		makeDeck();
		makePlayers(playNum);
		prevCard = deck.drawCard();
		while (!(prevCard instanceof NumberCard)) {
			deck.addCard(prevCard);
			prevCard = deck.drawCard();
		}
	}

	public void makePlayers(int p) {
		players = new ArrayList<Player>(p);
		Player c = null;
		c = new Player(0);
		for (int t = 0; t < NUM_CARD_HAND; t++) {
			c.addCard(deck.drawCard());
		}
		players.add(c);
		for (int i = 1; i < p; i++) {
			c = new MedPlayer(i);
			for (int t = 0; t < NUM_CARD_HAND; t++) {
				c.addCard(deck.drawCard());
			}
			players.add(c);
		}
	}

	public Player getCurrentPlayer() {
		return players.get(nextTurn);
	}

	public Card getPrevCard() {
		return prevCard;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			Toolkit tools = Toolkit.getDefaultToolkit();
			Menu l = new Menu(tools.getScreenSize());
		} else if (args[0].equals("adf")) {
			UnoPlayer up = new UnoPlayer();
		} else {
			System.out
					.println("\n\nWelcome to UNO\n\nBY: Tarun Chaudhry\n\n\n ");
			System.out.println("How many players? ");
			scan = new Scanner(System.in);
			Uno u = new Uno(scan.nextInt());
			Player p = null;
			ArrayList<Card> cards = null;
			do {
				try {
					do {

						System.out.println("\n\n\n\n\nCurrent Card:"
								+ u.getPrevCard());
						// System.out.println("Deck Size:"+u.deck.cards.size());
						for (Player e : u.players) {
							System.out.println("Player " + e.getId()
									+ " SCORE:" + e.getScore() + " HAND:"
									+ e.getHandSize());
						}
						p = u.getCurrentPlayer();
						System.out.print("\nPlayer " + p.getId()
								+ " Turn SCORE:" + p.getScore() + " HAND:"
								+ p.getHandSize()
								+ " - Press ENTER to proceed... ");
						scan.nextLine();
						System.out.println("Player " + p.getId() + "'s Hand:");
						cards = p.getHand();
						for (int i = 1; i < cards.size() + 1; i++) {
							System.out.println("Card " + i + ": "
									+ cards.get(i - 1));
						}
						System.out.println("Choice "
								+ ((int) ((int) cards.size() + 1)) + ": Draw");
						System.out
								.println("Type the number of the card you would like to play and hit ENTER:");
					} while (!u.playCard(cards.get(scan.nextInt() - 1)));
				} catch (Exception e) {
					if (u.checkValid(u.pickUp())) {
						cards = p.getHand();
						System.out
								.println("Play: "
										+ cards.get(cards.size() - 1)
										+ "?\n1. YES\n2. NO\n\nWould you like to play the card?");
						if (scan.nextInt() == 1) {
							if (!u.playCard(cards.get(cards.size() - 1))) {
								System.out.println("Invalid Card");
								u.skip();
							}
						} else {
							u.skip();
						}
					} else {
						u.skip();
					}
				}
			} while (!u.won());
			scan.close();
			p = u.getWinningPlayer();
			for (Player e : u.players) {
				System.out.println("Player " + e.getId() + " SCORE:"
						+ e.getScore() + " HAND:" + e.getHandSize());
			}
			System.out.println("\n\nPlayer " + p.getId() + " won!");
		}
	}

	public void skip() {
		nextPlayer();
		computerPlay();
	}

	public Card pickUp() {
		System.out.println("Player " + nextTurn + " picked up a card.");
		Card c = deck.drawCard();
		players.get(nextTurn).addCard(c);
		return c;
	}

	public Player getWinningPlayer() {
		for (Player p : players) {
			if (p.getHandSize() == 0) {
				return p;
			}
		}
		return null;
	}

	public boolean won() {
		for (Player p : players) {
			if (p.getHandSize() == 0) {
				return true;
			}
		}
		return false;
	}

	public static int getScore(Card c) {
		if (c instanceof NumberCard) {
			return (((NumberCard) c).getValue()) + 2;
		} else if (c instanceof ActionCard) {
			return (((ActionCard) c).getAction());
		} else if (c.getColor() == COLOR_WILD) {
			return (2);
		} else if (c.getColor() == COLOR_WILD4) {
			return (1);
		}
		return 1;
	}

	public void addScore(Card c) {
		players.get(nextTurn).addScore(getScore(c));
	}

	public boolean playCard(Card c) {
		if (!checkValid(c)) {
			return false;
		}
		System.out.println("Player " + nextTurn + " played " + c);
		players.get(nextTurn).subtractCard(c);
		if (players.get(nextTurn).getHandSize() == 0) {
			return true;
		}
		prevCard = c;
		addScore(c);
		doCardAction(c);
		nextPlayer();
		computerPlay();
		return true;
	}

	public boolean nextPlayer() {
		if (forward) {
			nextTurn++;
		} else {
			nextTurn--;
		}
		if (nextTurn >= players.size()) {
			nextTurn = 0;
		} else if (nextTurn < 0) {
			nextTurn = players.size() - 1;
		}
		// System.out.println("Deck Size:"+deck.cards.size());
		return true;
	}

	public boolean computerPlay() {
		if (players.get(nextTurn) instanceof ComputerPlayer) {
			Card c = ((ComputerPlayer) (players.get(nextTurn))).playCard();
			if (c == null) {
				if (playCard(pickUp())) {
				} else {
					skip();
				}
			} else if (playCard(c)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void draw(int c) {
		nextPlayer();
		for (int i = 0; i < c; i++) {
			players.get(nextTurn).addCard(deck.drawCard());
		}
	}

	public void doCardAction(Card c) {
		if (c.getColor() == COLOR_WILD) {
			prevCard = new Card(players.get(nextTurn).askColor());
		} else if (c.getColor() == COLOR_WILD4) {
			prevCard = new Card(players.get(nextTurn).askColor());
			draw(4);
		} else if (c instanceof ActionCard) {
			if (((ActionCard) c).getAction() == ACTION_D2) {
				draw(2);
			} else if (((ActionCard) c).getAction() == ACTION_SKIP) {
				nextPlayer();
			} else if (((ActionCard) c).getAction() == ACTION_REV) {
				forward = !forward;
			}
		}
	}

	public static boolean checkValid(Card c) {
		return c.getColor() == COLOR_WILD
				|| c.getColor() == COLOR_WILD4
				|| c.getColor() == prevCard.getColor()
				|| (c instanceof NumberCard && prevCard instanceof NumberCard && ((NumberCard) c)
						.getValue() == ((NumberCard) prevCard).getValue())
				|| (c instanceof ActionCard && prevCard instanceof ActionCard && ((ActionCard) c)
						.getAction() == ((ActionCard) prevCard).getAction());
	}

	public static void makeDeck() {
		deck = new Deck();
		System.out.println("Made another deck");
		addCardsToDeck(COLOR_GREEN);
		addCardsToDeck(COLOR_RED);
		addCardsToDeck(COLOR_YELLOW);
		addCardsToDeck(COLOR_BLUE);
		for (int n = 0; n < 4; n++) {
			deck.addCard(new Card(COLOR_WILD4));
			deck.addCard(new Card(COLOR_WILD));
		}
		deck.shuffle();
	}

	public static void addCardsToDeck(int c) {
		for (int i = 0; i < 2; i++) {
			for (int n = 0; n < 10; n++) {
				deck.addCard(new NumberCard(c, n));
			}
			deck.addCard(new ActionCard(c, ACTION_D2));
			deck.addCard(new ActionCard(c, ACTION_SKIP));
			deck.addCard(new ActionCard(c, ACTION_REV));
		}
	}
}

class Card {

	int color;

	public Card(int c) {
		color = c;
	}

	public int getColor() {
		return color;
	}

	public boolean equals(Object o) {
		if (o instanceof Card) {
			Card c = (Card) o;
			if (color == c.getColor()) {
				return true;
			}
		}
		return false;
	}

	public String getColorString() {
		switch (color) {
		case Uno.COLOR_GREEN:
			return "Green";
		case Uno.COLOR_RED:
			return "Red";
		case Uno.COLOR_BLUE:
			return "Blue";
		case Uno.COLOR_YELLOW:
			return "Yellow";
		case Uno.COLOR_WILD:
			return "Wild";
		case Uno.COLOR_WILD4:
			return "Wild Draw 4";
		}
		return "Color";
	}

	public String toString() {
		return getColorString() + " Card";
	}
}

class NumberCard extends Card {

	int value;

	public NumberCard(int c, int v) {
		super(c);
		value = v;
	}

	public int getValue() {
		return value;
	}

	public boolean equals(Object o) {
		if (o instanceof NumberCard) {
			NumberCard c = (NumberCard) o;
			if (color == c.getColor() && value == c.getValue()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return getColorString() + " " + value + " NumberCard";
	}
}

class ActionCard extends Card {

	int action;

	public ActionCard(int c, int a) {
		super(c);
		action = a;
	}

	public int getAction() {
		return action;
	}

	public boolean equals(Object o) {
		if (o instanceof ActionCard) {
			ActionCard c = (ActionCard) o;
			if (color == c.getColor() && action == c.getAction()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String value = "";
		switch (action) {
		case Uno.ACTION_D2:
			value = "Draw 2";
			break;
		case Uno.ACTION_SKIP:
			value = "Skip";
			break;
		case Uno.ACTION_REV:
			value = "Reverse";
			break;
		}
		return getColorString() + " " + value + " ActionCard";
	}
}

class Deck {

	ArrayList<Card> pile;
	ArrayList<Card> cards;

	public Deck(ArrayList<Card> c) {
		cards = c;
	}

	public Deck() {
		cards = new ArrayList<Card>(108);
	}

	public void addCard(Card c) {
		cards.add(c);
	}

	public Card drawCard() {
		if (cards.size() == 0) {
			Uno.makeDeck();
		}
		return cards.remove(0);
	}

	public Card peek() {
		return cards.get(0);
	}

	public void split() {
		split((int) ((Math.random() * (cards.size() - 10)) + 10));
	}

	public void split(int c) {
		ArrayList<Card> temp = (ArrayList) cards.subList(0, c);
		cards.removeAll(temp);
		cards.addAll(temp);
	}

	public void played(Card c) {
		pile.add(c);
	}

	public boolean canHave(Card c) {
		return cards.contains(c);
	}

	public void shuffle() {
		ArrayList<Card> c = new ArrayList<Card>(108);
		while (cards.size() > 0) {
			c.add(cards.remove((int) (cards.size() * Math.random())));
		}
		cards = c;
	}
}

class Player {

	int id;
	ArrayList<Card> hand;
	int score;

	public Player(int i) {
		id = i;
		score = 0;
		hand = new ArrayList<Card>(10);
	}

	public int getId() {
		return id;
	}

	public void addCard(Card c) {
		hand.add(c);
	}

	public void subtractCard(Card c) {
		for (int i = 0; i < hand.size(); i++) {
			if (c.equals(hand.get(i))) {
				hand.remove(i);
				i = hand.size();
			}
		}
	}

	public int getHandSize() {
		return hand.size();
	}

	public boolean hasInHand(Card c) {
		return hand.contains(c);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int i) {
		score += i;
	}

	public int askColor() {
		System.out
				.println("What Color?\n\n1. GREEN\n2. YELLOW\n3. RED\n4. BLUE\nType the number of the color you want to play:");
		// Scanner scan = new Scanner(System.in);
		int i = Uno.scan.nextInt();
		// scan.close();
		return i;
	}
}

abstract class ComputerPlayer extends Player {

	public ComputerPlayer(int id) {
		super(id);
	}

	public java.util.List enumeratePossiblities(ArrayList<Card> c) {
		ArrayList<Card> f = new ArrayList(5);
		for (Card d : c) {
			if (Uno.checkValid(d)) {
				f.add(d);
			}
		}
		return f;
	}

	public java.util.List findHighestProbability(ArrayList<Card> c) {
		ArrayList<Card> f = new ArrayList(2);
		int highest = 0;
		int temp;
		for (Card d : c) {
			temp = Uno.getScore(d);
			if (temp > highest) {
				highest = temp;
			}
		}
		for (Card d : c) {
			if (highest == Uno.getScore(d)) {
				f.add(d);
			}
		}
		return f;
	}

	public int askColor() {
		int[] colors = new int[4];
		for (Card c : hand) {
			if (c.getColor() < colors.length) {
				colors[c.getColor()]++;
			}
		}
		int highest = 0;
		int temp = 1;
		for (int i = 0; i < colors.length; i++) {
			if (highest > colors[i]) {
				highest = colors[i];
				temp = i;
			}
		}
		return temp;
	}

	abstract Card playCard();
}

class EasyPlayer extends ComputerPlayer {

	public EasyPlayer(int id) {
		super(id);
	}

	public Card playCard() {
		ArrayList<Card> possible = (ArrayList<Card>) enumeratePossiblities(hand);
		if (possible.size() == 0) {
			return null;
		}
		ArrayList<Card> highest = (ArrayList<Card>) findHighestProbability(possible);
		// if(highest.size() == 1){
		return highest.get((int) (Math.random() * (double) highest.size()));
		// }
		// return highest.get(0);
	}
}

class MedPlayer extends ComputerPlayer {

	int size;

	public MedPlayer(int id) {
		super(id);
		size = 0;
	}

	public void addCard(Card c) {
		super.addCard(c);
		size += 1;
	}

	public void subtractCard(Card c) {
		// System.out.println("Deck Size:"+Uno.deck.cards.size());
		boolean removed = false;
		for (int i = 0; i < hand.size(); i++) {
			if (c.equals(hand.get(i))) {
				removed = true;
				// System.out.println("Removed from hand "+c);
				hand.remove(i);
				i = hand.size();
			}
		}
		if (!removed) {
			for (int i = 0; i < Uno.deck.cards.size(); i++) {
				if (c.equals(Uno.deck.cards.get(i))) {
					// System.out.println("Removed from deck "+c);
					Uno.deck.cards.remove(i);
					i = Uno.deck.cards.size();
				}
			}
		}
		// System.out.println("Deck Size:"+Uno.deck.cards.size());
		size -= 1;
	}

	public int getHandSize() {
		return size;
	}

	public Card playCard() {
		ArrayList<Card> possible = (ArrayList<Card>) enumeratePossiblities(hand);
		if (possible.size() == 0) {
			possible.addAll(Uno.deck.cards);
			possible = (ArrayList<Card>) enumeratePossiblities(possible);
		}
		ArrayList<Card> highest = (ArrayList<Card>) findHighestProbability(possible);
		return highest.get((int) (Math.random() * (double) highest.size()));
	}
}

class HardPlayer extends ComputerPlayer {

	int size;

	public HardPlayer(int id) {
		super(id);
		size = 0;
	}

	public void addCard(Card c) {
		super.addCard(c);
		size += 1;
	}

	public void subtractCard(Card c) {
		// System.out.println("Deck Size:"+Uno.deck.cards.size());
		boolean removed = false;
		for (int i = 0; i < hand.size(); i++) {
			if (c.equals(hand.get(i))) {
				removed = true;
				// System.out.println("Removed from hand "+c);
				hand.remove(i);
				i = hand.size();
			}
		}
		if (!removed) {
			for (int i = 0; i < Uno.deck.cards.size(); i++) {
				if (c.equals(Uno.deck.cards.get(i))) {
					// System.out.println("Removed from deck "+c);
					Uno.deck.cards.remove(i);
					i = Uno.deck.cards.size();
				}
			}
		}
		// System.out.println("Deck Size:"+Uno.deck.cards.size());
		size -= 1;
	}

	public int getHandSize() {
		return size;
	}

	public Card playCard() {
		ArrayList<Card> possible = new ArrayList<Card>(75);
		possible.addAll(Uno.deck.cards);
		possible.addAll(hand);
		possible = (ArrayList<Card>) enumeratePossiblities(possible);
		if (possible.size() == 0) {
			return null;
		}
		possible = (ArrayList<Card>) findHighestProbability(possible);
		return possible.get((int) (Math.random() * possible.size()));
	}
}

class UnoPlayer extends JFrame {

	public UnoPlayer() {
		this.setTitle("UNO by Tarun Chaudhry");
		JPanel c = new JPanel();
		Uno u = new Uno(4);
		GridLayout ly = new GridLayout(1, 7, 5, 10);
		c.setLayout(ly);
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		c.add(new CardPanel(u.deck.drawCard()));
		this.setSize(600, 200);
		this.setVisible(true);
		getContentPane().add(c);
	}
}

class CardPanel extends JPanel {

	Card c;
	String s;
	int mwidth;
	Color col;
	BasicStroke bs = new BasicStroke(5);

	public CardPanel() {
		this.setBackground(this.getParent().getBackground());
	}

	public CardPanel(Card d) {
		super();
		setCard(d);
	}

	public Color decipherColor(int color) {
		switch (color) {
		case Uno.COLOR_WILD4:
			return Color.black;
		case Uno.COLOR_WILD:
			return Color.black;
		case Uno.COLOR_GREEN:
			return Color.green;
		case Uno.COLOR_BLUE:
			return Color.blue;
		case Uno.COLOR_YELLOW:
			return Color.yellow;
		case Uno.COLOR_RED:
			return Color.red;
		}
		return Color.gray;
	}

	public String decipherMessage(Card d) {
		if (d instanceof NumberCard) {
			return ((Integer) ((NumberCard) d).getValue()).toString();
		} else if (d instanceof ActionCard) {
			switch (((ActionCard) d).getAction()) {
			case Uno.ACTION_D2:
				return "Draw 2";
			case Uno.ACTION_SKIP:
				return "Skip";
			case Uno.ACTION_REV:
				return "Reverse";
			}
		} else if (d.getColor() == Uno.COLOR_WILD) {
			return "Wild";
		} else if (d.getColor() == Uno.COLOR_WILD4) {
			return "Wild Draw 4";
		}
		return "NOPE";
	}

	public Card getCard() {
		return c;
	}

	public void setCard(Card d) {
		c = d;
		col = this.decipherColor(c.getColor());
		// this.setBackground(col);
		s = this.decipherMessage(c);
		mwidth = this.getFontMetrics(this.getFont()).stringWidth(s);
		repaint();
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setColor(col);
		// g.
		g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 30, 30);
		g.setStroke(bs);
		g.setColor(Color.white);
		g.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
		g.fillRoundRect(5, 5, mwidth + 10, 20, 10, 10);
		g.fillRoundRect(getWidth() - 5 - mwidth - 10, getHeight() - 25,
				mwidth + 10, 20, 10, 10);
		g.setColor(Color.black);
		g.drawString(s, 10, 20);
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
		offset = 3;
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}
}

class Menu extends JFrame implements ActionListener, ChangeListener {

	TButton start = new TButton("Start");
	TButton start1 = new TButton("Start");
	TButton close = new TButton("X");
	TButton icon = new TButton("-");
	JSpinner numPlayers = new JSpinner(new SpinnerNumberModel(2, 2, 9, 1));
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
			"Welcome to Uno, the game!!!                                                                                          Created by Tarun Chaudhry!!!                                                                                         Enjoy the game!!!                 ");
	ArrayList<JTextField> ai = new ArrayList<JTextField>();

	public Menu(Dimension d) {
		this.setSize(400, 210);
		this.setLocation(d.width / 2 - 200, d.height / 2 - 100);
		this.setTitle("Uno");
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
		sp.setBounds(25, 25, 100, 25);
		mp.setBounds(150, 25, 100, 25);
		mp.setBackground(Color.green);
		sp.setBackground(Color.green);
		top.add(numPlayers);
		numPlayers.setBackground(Color.GREEN);
		numPlayers.setBounds(275, 25, 35, 25);
		numPlayers.addChangeListener(this);
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
		// b2.add(pl11);
		b2.add(start1);
		b2.add(j11);
		// b2.add(pl2);
		b2.add(j2);
		// b1.add(pl1);
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
		j1.setBounds(10, 25, 165, 25);
		j2.setBounds(10, 50, 165, 25);
		start.setBounds(250, 105, 100, 25);
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
		card.next(bottom);
		dragListener drag = new dragListener(this);
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
			// start(Checkers.EASY);
		} else if (com == start1) {
			start(0);
		}
	}

	protected void start(int lvl) {
		this.dispose();
		System.gc();
		// new Checkers(lvl);
	}

	public void stateChanged(ChangeEvent e) {
		changeNumberPlayers(((Integer) numPlayers.getValue()).intValue());
	}
	
	public void changeNumberPlayers(int i) {
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