/*
 * Copyright(c) 2008-11 Tarun Chaudhry
 * TO COMPILE THIS CODE: javac JavChart.java
 * TO RUN THIS CODE: java JavChart
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * Licensed under GNU GPL v3
 * @author Tarun Chaudhry
 */
/**
 *I import every class needed. This causes my program to start 
 *quicker and allows me to have more control over the classes I use.
 *It also looks more professional if I import every class seperately.
 */
package com.tgc;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Polygon;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Insets;
import java.awt.LayoutManager;
//import javax.swing.*;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.JScrollPane;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.JTextField;
//import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;
//import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
//import java.beans.*;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.beans.PropertyEditorManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
//import java.lang.reflect.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
//import java.io.*;
import java.io.PrintWriter;
import java.io.StringWriter;
//import javax.swing.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * JavChart is an abstract class model that I will use in every Chart and graph.
 * It is designed to be a light wieght class
 */
public abstract class JavChart extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358383327520598198L;
	/**
	 * JComponent only allows for Colors as backgrounds. JavChart allows for
	 * Paints Paint encompass Colors, Textures, and Gradients
	 */
	Paint background;

	public Paint getPBackground() {
		return background;
	}

	public void setBackground(Paint bg) {
		background = bg;
	}

	/**
	 * To do the painting, JavChart calls a method calls render
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setPaint(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		render(g);
	}

	public abstract void render(Graphics g);

	/**
	 * The update method just recalculates the graph and repaints it
	 */
	public void update() {
		refresh();
		repaint();
	}

	public abstract void refresh();

	public static void main(String[] args) {
		Line list = new Line(70);
		for (int i = 0; i < 50; i++) {
			list.add(new ChartItem(i, (int) (Math.random() * i)));
		}
		Line list2 = new Line(70);
		for (int i = 0; i < 50; i++) {
			list2.add(new ChartItem(i, (int) (Math.random() * i)));
		}
		Line list3 = new Line(70);
		for (int i = 0; i < 50; i++) {
			list3.add(new ChartItem(i, (int) (Math.random() * i)));
		}
		Line list4 = new Line(70);
		for (int i = 0; i < 50; i++) {
			list4.add(new ChartItem(i, (int) (Math.random() * i)));
		}
		ArrayList<Sector> list5 = new ArrayList<Sector>();
		for (int i = 0; i < 8; i++) {
			Sector sec = new Sector(i + "st", 5 * i);
			sec.setBackground(new GradientPaint(1, 1, new Color((int) (Math
					.random() * 245 + 8), (int) (Math.random() * 245 + 8),
					(int) (Math.random() * 245 + 8)), 50, 50, new Color(
					(int) (Math.random() * 245 + 8),
					(int) (Math.random() * 245 + 8),
					(int) (Math.random() * 245 + 8)), true));
			list5.add(sec);
		}
		ArrayList<Integer> s = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			int rand = (int) (Math.random() * i + Math.random() * 1564);
			s.add(rand);
		}
		String acceptable = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		ArrayList<String> s2 = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			String s1 = (char) (Math.random() * 26 + 65) + "";
			do {
				int rand = (int) (Math.random() * (acceptable.length() - 1));
				int rand2 = (int) (Math.random() * (acceptable.length() - rand))
						+ rand;
				s1 += acceptable.substring(rand, rand2);
			} while (Math.random() < 0.3);
			s2.add(s1);
		}
		ListVisualizer panel = new ListVisualizer(s);
		// panel.setPreferredSize(new Dimension(100,1000));
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(panel);
		ListVisualizer panel2 = new ListVisualizer(s2);
		// panel2.setBackground(
		// panel.setPreferredSize(new Dimension(100,1000));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(panel2);

		/*
		 * for(int i = 0;i<list.size();i++){ System.out.print("+"+list.get(i));
		 * }
		 */
		JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
		// System.out.println("");
		JFrame f = new JFrame("JavChart Example");
		JavChartPanel cp = new JavChartPanel();
		JavChart chart = new LineChart(list);
		cp.setChart(chart);
		cp.setTitle(new JLabel("ArrayList Chart"));
		JavChartPanel cp2 = new JavChartPanel();
		list2.setPoint(new RectanglePoint(5, 5));
		// list2.setStroke();
		list3.setPoint(new OvalPoint(5, 5));
		LineChart chart2 = new LineChart(list2);
		chart2.setBackground(new GradientPaint(1, 1, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true));
		chart2.getXAxis().setMajorAxisPaint(
				new GradientPaint(1, 1, new Color(
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8)), 50, 50, new Color(
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8)), true));
		chart2.getYAxis().setMajorAxisPaint(
				new GradientPaint(1, 1, new Color(
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8)), 50, 50, new Color(
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8),
						(int) (Math.random() * 245 + 8)), true));
		chart2.getYAxis().getTitle()
				.setFont(new Font("Verdana", Font.BOLD, 20));
		chart2.getYAxis().getTitle().setTitle("# of kites");
		chart2.getYAxis()
				.getTitle()
				.setPaint(
						new GradientPaint(1, 1, new Color(
								(int) (Math.random() * 245 + 8), (int) (Math
										.random() * 245 + 8), (int) (Math
										.random() * 245 + 8)), 50, 50,
								new Color((int) (Math.random() * 245 + 8),
										(int) (Math.random() * 245 + 8),
										(int) (Math.random() * 245 + 8)), true));
		chart2.getXAxis()
				.getTitle()
				.setPaint(
						new GradientPaint(1, 1, new Color(
								(int) (Math.random() * 245 + 8), (int) (Math
										.random() * 245 + 8), (int) (Math
										.random() * 245 + 8)), 50, 50,
								new Color((int) (Math.random() * 245 + 8),
										(int) (Math.random() * 245 + 8),
										(int) (Math.random() * 245 + 8)), true));
		list2.setPaint(new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true));
		list3.setPaint(new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true));
		chart2.addLine(list3);
		// chart2.addLine(list4);
		cp2.setChart(chart2);
		cp2.setTitle(new JLabel("ArrayList Chart #2"));
		JavChartPanel cp3 = new JavChartPanel();
		JavChart chart3 = new PieChart(list5);
		chart3.setBackground(new GradientPaint(1, 1, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color((int) (Math
				.random() * 245 + 8), (int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true));
		// chart2.addLine(list3);
		// chart2.addLine(list4);
		cp3.setChart(chart3);
		cp3.setTitle(new JLabel("PieChart #3"));
		// cp.setTitleFormat(JavChartPanel.BOXED_TITLE);
		pane.addTab("Regular Chart", cp);
		pane.addTab("Colorful Chart", cp2);
		pane.addTab("Pie Chart", cp3);
		pane.add("list Visual with ints", jScrollPane);
		pane.add("list Visual with strings", jScrollPane2);
		f.setContentPane(pane);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(0, 25);
		f.setSize(d.width, d.height - 25);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// System.out.println(f);
		// StackVisual.main(args);
	}
}

/**
 * The Line class is only a ArrayList of ChartItems It calculates the mins and
 * maxs quickly while adding or removeing
 * 
 */
class Line extends ArrayList<ChartItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6730298914165448142L;
	Paint t;
	Stroke stroke;
	Number max;
	Number min;
	Number xMax;
	Number xMin;
	boolean scatter;
	// int pointSize;
	PointShape point;
	Set<PropertyChangeListener> changeListeners;

	/**
	 * Default contstructor Sets the capacity to 10
	 * 
	 */
	public Line() {
		this(10);
		scatter = false;
	}

	/**
	 * Constructor that allows you to choose a capacity
	 * 
	 */
	public Line(int capacity) {
		super(capacity);
		changeListeners = new HashSet<PropertyChangeListener>();
		t = Color.black;
		stroke = new BasicStroke(2);
		point = new TrianglePoint(10, 10);
		point.borderStroke = new BasicStroke(1);
		point.borderPaint = Color.yellow;
		point.paint = Color.blue;
		scatter = false;
	}

	/*
	 * public Line(Collection<ChartItem> c){ addAll(c); t = Color.black; stroke
	 * = new BasicStroke(2); pointSize = 2; }
	 */
	/**
	 * For adding PropertyChangeListeners that listen to when the min and max
	 * properties of this Line change
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * For removeing PropertyChangeListeners that listen to when the min and max
	 * properties of this Line change
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeListeners.remove(listener);
	}

	/**
	 * For firing PropertyChangeListeners that listen to when the min and max
	 * properties of this Line change
	 */
	private void firePropertyChangeListeners(PropertyChangeEvent e) {
		for (PropertyChangeListener p : changeListeners) {
			p.propertyChange(e);
		}
	}

	/**
	 * Retrieving the max value in this Line
	 */
	public double getMax() {
		return max.doubleValue();
	}

	/**
	 * Retrieving the min value in this Line
	 */
	public double getMin() {
		return min.doubleValue();
	}

	/**
	 * Adding a point to this line at the specified index Checks if the point
	 * added is greater than max or less than min
	 */
	public void add(int index, ChartItem element) {
		super.add(index, element);
		if (max != null && min != null) {
			double val = element.doubleValue();
			if (val > max.doubleValue()) {
				max = element.getValue();
				firePropertyChangeListeners(new PropertyChangeEvent(this,
						"max", max, element.getValue()));
			} else if (val < min.doubleValue()) {
				min = element.getValue();
				firePropertyChangeListeners(new PropertyChangeEvent(this,
						"min", min, element.getValue()));
			}
		} else if (max == null && min == null) {
			max = element.getValue();
			min = element.getValue();
			firePropertyChangeListeners(new PropertyChangeEvent(this, "max",
					max, element.getValue()));
			firePropertyChangeListeners(new PropertyChangeEvent(this, "min",
					min, element.getValue()));
		}
	}

	/**
	 * Adding a point to this line at the end of the list Checks if the point
	 * added is greater than max or less than min
	 */
	public boolean add(ChartItem o) {
		boolean b = super.add(o);
		if (b == true && max != null && min != null) {
			double val = o.doubleValue();
			if (val > max.doubleValue()) {
				max = o.getValue();
				firePropertyChangeListeners(new PropertyChangeEvent(this,
						"max", max, o.getValue()));
			} else if (val < min.doubleValue()) {
				min = o.getValue();
				firePropertyChangeListeners(new PropertyChangeEvent(this,
						"min", min, o.getValue()));
			}
		} else if (max == null && min == null) {
			max = o.getValue();
			min = o.getValue();
			firePropertyChangeListeners(new PropertyChangeEvent(this, "max",
					max, o.getValue()));
			firePropertyChangeListeners(new PropertyChangeEvent(this, "min",
					min, o.getValue()));
		}
		return b;
	}

	/**
	 * Setting a point on this line at the specified index Calls refresh which
	 * refreshes the values of min and max
	 */
	public ChartItem set(int index, ChartItem element) {
		ChartItem b = super.set(index, element);
		refresh();
		return b;
	}

	/**
	 * Iterates through all the elements Refreshes which refreshes the values of
	 * min and max
	 */
	protected void refresh() {
		Number oldMin = min;
		Number oldMax = max;
		min = get(0).getValue();
		max = min;
		// Iterator<ChartItem> iterator = this.iterator();
		// while(iterator.hasNext()){
		for (ChartItem i : this) {
			if (max.doubleValue() < i.doubleValue()) {
				max = i.getValue();
			}
			if (min.doubleValue() < i.doubleValue()) {
				min = i.getValue();
			}
		}
		firePropertyChangeListeners(new PropertyChangeEvent(this, "max", max,
				oldMin));
		firePropertyChangeListeners(new PropertyChangeEvent(this, "min", min,
				oldMax));
	}

	/**
	 * Removing a point on this line at the specified index Calls refresh which
	 * refreshes the values of min and max
	 */
	public ChartItem remove(int index) {
		ChartItem b = super.remove(index);
		refresh();
		return b;
	}

	/**
	 * Removing the specified point from the line Calls refresh which refreshes
	 * the values of min and max
	 */
	public boolean remove(ChartItem o) {
		boolean b = super.remove(o);
		refresh();
		return b;
	}

	/**
	 * Calls add successivelly to add all the elements in collection at the
	 * specified index
	 */
	public boolean addAll(int index, Collection<? extends ChartItem> collection) {
		boolean b = true;

		Iterator<? extends ChartItem> iterator = collection.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			add(i, iterator.next());
			// refresh();
			// b = false;
			// return false;
			// }
		}
		refresh();
		return b;
	}

	/**
	 * Calls addAll with the parameters size() and collection
	 */
	public boolean addAll(Collection<? extends ChartItem> collection) {
		return addAll(size(), collection);
	}

	/**
	 * Returns the value of t.
	 */
	public Paint getPaint() {
		return t;
	}

	/**
	 * Sets the value of t.
	 * 
	 * @param t
	 *            The value to assign t.
	 */
	public void setPaint(Paint t) {
		this.t = t;
	}

	/**
	 * Returns the value of stroke.
	 */
	public Stroke getStroke() {
		return stroke;
	}

	/**
	 * Sets the value of stroke.
	 * 
	 * @param stroke
	 *            The value to assign stroke.
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	/**
	 * Returns the value of pointSize.
	 */
	public PointShape getPoint() {
		return point;
	}

	/**
	 * Sets the value of point.
	 * 
	 * @param pointSize
	 *            The value to assign point.
	 */
	public void setPoint(PointShape point) {
		this.point = point;
	}

	public boolean isScatter() {
		return scatter;
	}

	public void setScatter(boolean b) {
		scatter = b;
	}
}

/*
 * interface LinePropertyChangeListener{ public void
 * linePropertyUpdated(LineEvent e); }
 */
/**
 * Represents a rectangular point for the line
 */
class RectanglePoint extends PointShape {
	public RectanglePoint(int w, int h) {
		super(w, h);
	}

	/**
	 * For drawing the point in context of Graphics2D g Draws the center of the
	 * point at the coordinates (x,y)
	 */
	public void draw(Graphics2D g, int x, int y) {
		x -= width / 2;
		y -= height / 2;
		g.setPaint(borderPaint);
		g.setStroke(borderStroke);
		g.drawRect(x, y, width, height);
		if (fill) {
			g.setPaint(paint);
			g.fillRect(x, y, width, height);
		}
	}
}

/**
 * Represents a Isoceles Triangle point for the line
 */
class TrianglePoint extends PointShape {
	public TrianglePoint(int w, int h) {
		super(w, h);
	}

	/**
	 * For drawing the point in context of Graphics2D g Draws the center of the
	 * point at the coordinates (x,y)
	 */
	public void draw(Graphics2D g, int x, int y) {
		x -= width / 2;
		y -= height / 2;
		g.setPaint(borderPaint);
		g.setStroke(borderStroke);
		Polygon p = new Polygon(new int[] { x + width / 2, x, x + width },
				new int[] { y, y + height, y + height }, 3);
		g.drawPolygon(p);
		if (fill) {
			g.setPaint(paint);
			g.fillPolygon(p);
		}
	}
}

/**
 * Represents a circular point for the line
 */
class OvalPoint extends PointShape {
	public OvalPoint(int w, int h) {
		super(w, h);
	}

	/**
	 * For drawing the point in context of Graphics2D g Draws the center of the
	 * point at the coordinates (x,y)
	 */
	public void draw(Graphics2D g, int x, int y) {
		x -= width / 2;
		y -= height / 2;
		g.setPaint(borderPaint);
		g.setStroke(borderStroke);
		g.drawOval(x, y, width, height);
		if (fill) {
			g.setPaint(paint);
			g.fillOval(x, y, width, height);
		}
	}
}

abstract class PointShape {
	Paint paint;
	int width;
	int height;
	Stroke borderStroke;
	Paint borderPaint;
	boolean fill;

	/**
	 * Constructs the point with the given height and width
	 */
	public PointShape(int w, int h) {
		width = w;
		height = h;
		fill = true;
		borderPaint = Color.blue;
		borderStroke = new BasicStroke(2);
	}

	/**
	 * Helpful for calculating points in the graph
	 */
	public Rectangle getBounds() {
		return new Rectangle(width, height);
	}

	/**
	 * For drawing the point in context of Graphics2D g Draws the center of the
	 * point at the coordinates (x,y)
	 */
	public abstract void draw(Graphics2D g, int x, int y);

	/**
	 * Constructor PointShape
	 * 
	 * 
	 * @param paint
	 * @param width
	 * @param height
	 * @param borderStroke
	 * @param borderPaint
	 * @param fill
	 * 
	 */
	public PointShape(Paint paint, int width, int height, Stroke borderStroke,
			Paint borderPaint, boolean fill) {
		this.paint = paint;
		this.width = width;
		this.height = height;
		this.borderStroke = borderStroke;
		this.borderPaint = borderPaint;
		this.fill = fill;
	}

	/**
	 * Constructor PointShape
	 * 
	 * 
	 * @param other
	 * 
	 */
	public PointShape(PointShape other) {
		if (this != other) {
			this.paint = other.paint;
			this.width = other.width;
			this.height = other.height;
			this.borderStroke = other.borderStroke;
			this.borderPaint = other.borderPaint;
			this.fill = other.fill;
		}
	}

	/**
	 * Method setPaint
	 * 
	 * 
	 * @param paint
	 * 
	 */
	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	/**
	 * Method setWidth
	 * 
	 * 
	 * @param width
	 * 
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Method setHeight
	 * 
	 * 
	 * @param height
	 * 
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Method setBorderStroke
	 * 
	 * 
	 * @param borderStroke
	 * 
	 */
	public void setBorderStroke(Stroke borderStroke) {
		this.borderStroke = borderStroke;
	}

	/**
	 * Method setBorderPaint
	 * 
	 * 
	 * @param borderPaint
	 * 
	 */
	public void setBorderPaint(Paint borderPaint) {
		this.borderPaint = borderPaint;
	}

	/**
	 * Method setFill
	 * 
	 * 
	 * @param fill
	 * 
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}

	/**
	 * Method getPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getPaint() {
		return (this.paint);
	}

	/**
	 * Method getWidth
	 * 
	 * 
	 * @return
	 * 
	 */
	public int getWidth() {
		return (this.width);
	}

	/**
	 * Method getHeight
	 * 
	 * 
	 * @return
	 * 
	 */
	public int getHeight() {
		return (this.height);
	}

	/**
	 * Method getBorderStroke
	 * 
	 * 
	 * @return
	 * 
	 */
	public Stroke getBorderStroke() {
		return (this.borderStroke);
	}

	/**
	 * Method getBorderPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getBorderPaint() {
		return (this.borderPaint);
	}

	/**
	 * Method getFill
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getFill() {
		return (this.fill);
	}

	/**
	 * Method toString
	 * 
	 * 
	 * @return String
	 * 
	 */
	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("paint = ");
		buffer.append(paint);
		buffer.append(sep);
		buffer.append("width = ");
		buffer.append(width);
		buffer.append(sep);
		buffer.append("height = ");
		buffer.append(height);
		buffer.append(sep);
		buffer.append("borderStroke = ");
		buffer.append(borderStroke);
		buffer.append(sep);
		buffer.append("borderPaint = ");
		buffer.append(borderPaint);
		buffer.append(sep);
		buffer.append("fill = ");
		buffer.append(fill);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}

}

/**
 * Pie Chart for creating pie charts
 */
class PieChart extends JavChart {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637642713744978764L;
	ArrayList<Sector> data;
	// Paint background;
	double total;
	Stroke borderStroke;
	Paint borderPaint;

	/**
	 * Default constructor Chooses random colors
	 */
	public PieChart() {
		total = 0;
		data = new ArrayList<Sector>(4);
		borderStroke = new BasicStroke(2);
		borderPaint = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		background = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
	}

	/**
	 * Constructor that sets the data to the data given to it Chooses random
	 * colors
	 */
	public PieChart(ArrayList<Sector> pie) {
		total = 0;
		data = pie;
		refresh();
		borderStroke = new BasicStroke(2);
		borderPaint = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		background = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
	}

	/**
	 * Draws the pie chart
	 */
	public void render(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		int w = getWidth();
		int h = getHeight();
		g.setPaint(background);
		g.fillRect(0, 0, w + 10, h + 10);
		g.setStroke(borderStroke);
		g.setPaint(borderPaint);
		g.drawOval(5, 5, w - 10, h - 10);
		int start = 0;
		for (Sector c : data) {
			g.setStroke(c.borderStroke);
			g.setPaint(c.borderPaint);
			int i = (int) (c.doubleValue() / total * 360.0 + 0.5);
			g.drawArc(5, 5, w - 10, h - 10, start, i);
			g.setPaint(c.background);
			g.fillArc(5, 5, w - 10, h - 10, start, i);
			start += i;
			// System.out.println("start:"+start+" i:"+i);
		}
		/*
		 * start = 0; for(Sector c : data){ int i =
		 * (int)(c.doubleValue()/total*360.0); FontMetrics fm =
		 * getFontMetrics(c.f); g.setPaint(c.foreground); int radx =
		 * (int)((w-10.0-5.0)/2.0); int rady = (int)((h-10.0-5.0)/2.0); int x =
		 * (int)(radx*(Math.cos(start+i/2))+radx); int y =
		 * (int)(rady*(Math.sin(start+i/2))+rady);
		 * System.out.println("x:"+x+" y:"+y+" radx:"+radx+" rady:"+rady);
		 * //g.translate((int)((w-10.0-5.0)/2.0),(int)((h-10.0-5.0)/2.0));
		 * if(c.words){ //g.drawString(c.getKey().toString(),x,y); }
		 * if(c.percents){
		 * 
		 * } if(c.values){
		 * 
		 * } //g.translate(-(int)((w-10.0-5.0)/2.0),-(int)((h-10.0-5.0)/2.0));
		 * start+=i; //g.fillArc(); }
		 */
	}

	/**
	 * To add a element to this chart at size()
	 */
	public void add(Sector c) {
		data.add(c);
		total += c.doubleValue();
	}

	/**
	 * To add a element to this chart at the specified index
	 */
	public void add(int i, Sector c) {
		if (i < data.size())
			total -= data.get(i).doubleValue();
		data.add(i, c);
		total += c.doubleValue();
	}

	/**
	 * To clear this chart of all elements
	 */
	public void clear() {
		total = 0;
		data.clear();
	}

	/**
	 * To remove the element at the specified index on this chart
	 */
	public Sector removeSector(int i) {
		Sector c = data.remove(i);
		total -= c.doubleValue();
		return c;
	}

	/**
	 * To set the element at the specified index on this chart
	 */
	public Sector set(int i, Sector c) {
		Sector d = data.set(i, c);
		total -= d.doubleValue();
		total += c.doubleValue();
		return d;
	}

	/**
	 * To get the element at the specified index on this chart
	 */
	public Sector get(int i) {
		return data.get(i);
	}

	/**
	 * To recalculate the total for this chart
	 */
	public void refresh() {
		total = 0;
		for (Sector c : data) {
			total += c.doubleValue();
		}
	}
}

/**
 * For storing info about a sector in pie chart
 */
class Sector extends ChartItem {
	Stroke borderStroke;
	Paint borderPaint;
	Paint background;
	Font f;
	Paint foreground;

	public Sector(String key, double value) {
		super(key, value);
		borderStroke = new BasicStroke(2);
		borderPaint = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		background = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		foreground = Color.white;// new GradientPaint(1,1,new
									// Color((int)(Math.random()*245+8),(int)(Math.random()*245+8),(int)(Math.random()*245+8)),50,50,new
									// Color((int)(Math.random()*245+8),(int)(Math.random()*245+8),(int)(Math.random()*245+8)),true);
		f = new Font("Arial", Font.BOLD, 13);
	}

	/**
	 * Constructor Sector
	 * 
	 * 
	 * @param borderStroke
	 * @param borderPaint
	 * @param background
	 * @param f
	 * @param foreground
	 * @param words
	 * @param percents
	 * @param values
	 * 
	 */
	public Sector(Object key, Number value, Stroke borderStroke,
			Paint borderPaint, Paint background, Font f, Paint foreground) {
		super(key, value);
		this.borderStroke = borderStroke;
		this.borderPaint = borderPaint;
		this.background = background;
		this.f = f;
		this.foreground = foreground;

	}

	/**
	 * Constructor Sector
	 * 
	 * 
	 * @param other
	 * 
	 */
	public Sector(Sector other) {
		super(other.getKey(), other.getValue());
		if (this != other) {

			this.borderStroke = other.borderStroke;
			this.borderPaint = other.borderPaint;
			this.background = other.background;
			this.f = other.f;
			this.foreground = other.foreground;

		}
	}

	/**
	 * Method setBorderStroke
	 * 
	 * 
	 * @param borderStroke
	 * 
	 */
	public void setBorderStroke(Stroke borderStroke) {
		this.borderStroke = borderStroke;
	}

	/**
	 * Method setBorderPaint
	 * 
	 * 
	 * @param borderPaint
	 * 
	 */
	public void setBorderPaint(Paint borderPaint) {
		this.borderPaint = borderPaint;
	}

	/**
	 * Method setBackground
	 * 
	 * 
	 * @param background
	 * 
	 */
	public void setBackground(Paint background) {
		this.background = background;
	}

	/**
	 * Method setF
	 * 
	 * 
	 * @param f
	 * 
	 */
	public void setFont(Font f) {
		this.f = f;
	}

	/**
	 * Method setForeground
	 * 
	 * 
	 * @param foreground
	 * 
	 */
	public void setForeground(Paint foreground) {
		this.foreground = foreground;
	}

	/**
	 * Method getBorderStroke
	 * 
	 * 
	 * @return
	 * 
	 */
	public Stroke getBorderStroke() {
		return (this.borderStroke);
	}

	/**
	 * Method getBorderPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getBorderPaint() {
		return (this.borderPaint);
	}

	/**
	 * Method getBackground
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getBackground() {
		return (this.background);
	}

	/**
	 * Method getF
	 * 
	 * 
	 * @return
	 * 
	 */
	public Font getFont() {
		return (this.f);
	}

	/**
	 * Method getForeground
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getForeground() {
		return (this.foreground);
	}

	/**
	 * Method toString
	 * 
	 * 
	 * @return
	 * 
	 */
	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("borderStroke = ");
		buffer.append(borderStroke);
		buffer.append(sep);
		buffer.append("borderPaint = ");
		buffer.append(borderPaint);
		buffer.append(sep);
		buffer.append("background = ");
		buffer.append(background);
		buffer.append(sep);
		buffer.append("f = ");
		buffer.append(f);
		buffer.append(sep);
		buffer.append("foreground = ");
		buffer.append(foreground);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}
	/*
	 * public double doubleValue(){ return value; }
	 */
}

/**
 * The component for displaying a line chart
 */
class LineChart extends JavChart implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2117874893315259919L;
	ArrayList<Line> data;
	// Axes series = new Axes();
	Axis xAxis = new Axis();
	Axis yAxis = new Axis();

	// Paint background;
	/**
	 * The default constructor
	 */
	public LineChart() {
		xAxis.setMin(0);
		background = Color.white;
		this.data = new ArrayList<Line>();
	}

	/**
	 * Constructs a line chart with the specified line
	 */
	public LineChart(Line data) {
		background = Color.white;
		this.data = new ArrayList<Line>();
		addLine(data);
	}

	// public LineChart(ArrayList<ArrayList<ChartItem>> data){
	// this.data = data;
	// }
	/**
	 * Draws the line graph
	 */
	public void render(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		int w = getWidth();
		int h = getHeight();
		g.setPaint(background);
		g.fillRect(0, 0, w + 10, h + 10);
		/*
		 * if(yAxis.max == null || yAxis.min == null){ yAxis.max = new
		 * Double(50); yAxis.min = new Double(0); }
		 */
		double yMin = yAxis.min.doubleValue();
		double yMax = yAxis.max.doubleValue();
		double xMax = xAxis.max.doubleValue();
		double xMin = xAxis.min.doubleValue();
		int xSpace = xAxis.getXSpace();
		int ySpace = yAxis.getYSpace();
		// System.out.println(xSpace);
		int wN = w - ySpace;
		// System.out.println("wN:"+wN+"ySpace:"+ySpace+"w:"+w);
		int hN = h - xSpace;
		if (yAxis.paintAxis) {
			// w-=25;
			// g.drawString(
			// paintYAxis(g);
			// g.translate(25,0);
			g.setPaint(yAxis.title.paint);
			double yD = ((double) hN) / (yMax - yMin);
			int yTitleFontHeight = getFontMetrics(yAxis.title.font).getHeight();
			int yM = (int) (hN - yTitleFontHeight * yAxis.title.title.length()) / 2;
			g.setFont(yAxis.title.font);
			// System.out.println("lem:"+yAxis.title.title.length());
			for (int i = 0; i < yAxis.title.title.length(); i++)
				g.drawString("" + yAxis.title.title.charAt(i), 10, yM
						+ yTitleFontHeight * i);
			g.setStroke(yAxis.majorAxisStroke);
			g.setPaint(yAxis.majorAxisPaint);
			g.drawLine(ySpace, 0, ySpace, h - xSpace);
			// g.drawString(yMin+"",40,h-10);

			double yMin2 = 0;// (yMin*yD);
			double yMajor = (yAxis.majorAxis * yD);
			// int yMax2 = yMax*yD;
			int len = yAxis.majorSize;
			g.setFont(yAxis.majorFont);
			FontMetrics fm = getFontMetrics(yAxis.majorFont);
			String s = yMax + "";
			g.drawString(s, ySpace - fm.stringWidth(s), fm.getHeight());
			if (yAxis.major) {
				while (yMin2 < hN) {
					// yMin2+=yMajor;
					g.drawLine(ySpace - 2, (int) yMin2, ySpace + len,
							(int) yMin2);

					// System.out.println(yMin2+" <= "+hN+":"+(s));
					if (yAxis.majorWords) {
						s = (int) yMax - ((int) (yMin2 / yD)) + "";
						g.drawString(s, ySpace - fm.stringWidth(s), (int) yMin2);
					}
					yMin2 += yMajor;
				}
			}
			if (yAxis.minor) {
				// g.setFont(
				len = yAxis.minorSize;
				g.setPaint(yAxis.minorAxisPaint);
				g.setStroke(yAxis.minorAxisStroke);
				yMin2 = 0;// (yMin*yD);
				double yMinor = (yAxis.minorAxis * yD);
				while (yMin2 < hN) {
					yMin2 += yMinor;
					g.drawLine(ySpace - 2, (int) yMin2, ySpace + len,
							(int) yMin2);

					// System.out.println(yMin2+" <= "+hN+":"+(s));
					if (yAxis.minorWords) {
						s = (int) yMax - ((int) (yMin2 / yD)) + "";
						g.drawString(s, ySpace - fm.stringWidth(s), (int) yMin2);
					}
				}
			}
		}
		if (xAxis.paintAxis) {
			g.setPaint(xAxis.title.paint);
			g.setFont(xAxis.title.font);

			int xM = wN / 2 - 5 * xAxis.title.title.length();
			g.drawString(xAxis.title.title, xM,
					h - getFontMetrics(xAxis.title.font).getMaxDescent());
			g.setStroke(xAxis.majorAxisStroke);
			g.setPaint(xAxis.majorAxisPaint);
			g.drawLine(ySpace, h - xSpace, w, h - xSpace);

			double xD = ((double) wN) / (xMax - xMin);
			// g.drawString(xMax+"",w-30,h-40);
			// g.drawString(xMin+"",70,h-40);
			double xMin2 = 0;// (xMin*xD);
			// System.out.println(xD+" "+xMin);
			double xMajor = (xAxis.majorAxis * xD);
			int len = xAxis.majorSize;
			// System.out.println(xD);
			// xD=70;
			if (xAxis.major) {
				g.setFont(xAxis.majorFont);
				while (xMin2 < wN) {

					// System.out.println(xMin2);
					g.drawLine((int) xMin2 + ySpace, hN, (int) xMin2 + ySpace,
							hN - len);
					if (xAxis.majorWords)
						g.drawString(((int) (xMin2 / xD)) + "", (int) xMin2
								+ ySpace, h
								- getFontMetrics(xAxis.title.font).getHeight()
								- 5);
					xMin2 += xMajor;
				}
			}
			if (xAxis.minor) {
				len = xAxis.minorSize;
				xMin2 = 0;// (xMin*xD);
				// System.out.println(xD+" "+xMin);
				double xMinor = (xAxis.minorAxis * xD);

				g.setPaint(xAxis.minorAxisPaint);
				g.setStroke(xAxis.minorAxisStroke);
				// g.setFont(xAxis.xFont);
				while (xMin2 < wN) {

					// System.out.println(xMin2);
					g.drawLine((int) xMin2 + ySpace, hN, (int) xMin2 + ySpace,
							hN - len);
					if (xAxis.minorWords)
						g.drawString(((int) (xMin2 / xD)) + "", (int) xMin2
								+ ySpace, h
								- getFontMetrics(xAxis.title.font).getHeight()
								- 5);
					xMin2 += xMinor;
				}
			}
			// h-=25;
			// paintXAxis(g);
			// g.translate(0,25);
		}
		w -= ySpace;
		// h-=50;

		g.translate(ySpace, xSpace);
		h -= xSpace;
		// g.drawRect(0,0,w,h);
		// int major = series.yMajor();
		double hD = ((double) h) / (yMax - yMin);
		double wD = ((double) w) / (xMax - xMin);
		for (int i = 0; i < data.size(); i++) {
			Line chart = data.get(i);
			PointShape p = chart.getPoint();
			double x = 0, y = 0, x1 = 0, y1 = 0;

			for (int j = 0; j < chart.size() - 1; j++) {

				x = j * wD;
				y = h - chart.get(j).doubleValue() * hD - xSpace * 1.05;
				// System.out.println("Y:"+y+" chart:"+
				// chart.get(j).doubleValue()+" Xspace:"+xSpace*1.05);
				p.draw(g, (int) x, (int) y);
				// g.fillOval((int)x,(int)y,chart.getPointSize(),chart.getPointSize());
				x1 = x + wD;
				y1 = h - chart.get(j + 1).doubleValue() * hD - xSpace * 1.05;
				g.setPaint(chart.getPaint());
				g.setStroke(chart.getStroke());
				if (!chart.isScatter())
					g.drawLine((int) x, (int) y, (int) x1, (int) y1);
			}
			p.draw(g, (int) x1, (int) y1);
			// g.fillOval((int)x1,(int)y1,chart.getPointSize(),chart.getPointSize());
		}
	}

	/**
	 * To detect any property changes in any lines
	 */
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("max")) {
			if (yAxis.getMax().doubleValue() < ((Number) e.getNewValue())
					.doubleValue())
				setMax(((Number) e.getNewValue()).doubleValue() * 1.25);
		} else if (e.getPropertyName().equals("min")) {
			if (yAxis.getMin().doubleValue() > ((Number) e.getNewValue())
					.doubleValue())
				setMin(((Number) e.getNewValue()).doubleValue() * 0.75);
		}
		repaint();
		/*
		 * if(yAxis.getMax().doubleValue() < c.doubleValue())
		 * setMax(c.doubleValue()); if(yAxis.getMin().doubleValue() >
		 * c.doubleValue()) setMin(c.doubleValue());
		 */
	}

	/**
	 * To refresh the chart
	 */
	public void refresh() {
		double min = data.get(0).getMin();
		double max = data.get(0).getMax();
		for (Line l : data) {
			if (min > l.getMin()) {
				min = l.getMin();
			}
			if (max > l.getMax()) {
				max = l.getMax();
			}
		}
		setMax(max);
		setMin(min);
	}

	/**
	 * To set the minimum and calculate the major and minor axis of the graph
	 */
	public void setMin(double min) {
		yAxis.setMin(min * 0.75);
		double max = yAxis.getMax().doubleValue();
		double d = max - min;
		double c = 5.0;
		for (double i = 4; i < 8; i += 0.5) {
			if (d % i == 0) {
				c = i;
				i = 10;
			}
		}
		c = 4.0;
		yAxis.majorAxis = (int) ((d) / c);
		d = yAxis.majorAxis;
		for (double i = 2; i < 7; i += 0.5) {
			if (d % i == 0) {
				c = i;
				i = 10;
			}
		}
		yAxis.minorAxis = d / c;
	}

	/**
	 * To set the maximum and calculate the major and minor axis of the graph
	 */
	public void setMax(double min) {
		yAxis.setMax(min * 1.25);
		double max = yAxis.getMax().doubleValue();
		double d = max - min;
		double c = 5.0;
		for (double i = 4; i < 8; i += 0.5) {
			if (d % i == 0) {
				c = i;
				i = 10;
			}
		}
		c = 4.0;
		yAxis.majorAxis = (int) ((d) / c);
		d = yAxis.majorAxis;
		for (double i = 2; i < 7; i += 0.5) {
			if (d % i == 0) {
				c = i;
				i = 10;
			}
		}
		yAxis.minorAxis = d / c;
	}

	/**
	 * To add the specified element to line located at the specified index
	 */
	public void update(int i, ChartItem c) {
		data.get(i).add(c);
		update();
	}

	/**
	 * To add the specified element to line located at the size() - 1
	 */
	public void update(ChartItem c) {
		// data.get(data.size()-1).add(c);
		update(data.size() - 1, c);
	}

	/**
	 * To add the specified element to line graph
	 */
	public void addLine(Line data) {
		this.data.add(data);
		xAxis.setMax(data.size());
		data.addPropertyChangeListener(this);
		if (yAxis.getMax().doubleValue() < data.getMax())
			setMax(data.getMax());
		if (yAxis.getMin().doubleValue() > data.getMin())
			setMin(data.getMin());
		repaint();
		// update();
	}

	/**
	 * To remove the element at the specified index from line graph
	 */
	public Line removeLine(int i) {
		Line b = this.data.remove(i);
		b.removePropertyChangeListener(this);
		update();
		return b;
	}

	/**
	 * To get the element at the specified index to line graph
	 */
	public Line getLine(int i) {
		return data.get(i);
	}

	/**
	 * To set the specified element at the specified index to line graph
	 */
	public void setLine(int i, Line data) {
		this.data.set(i, data);
		data.addPropertyChangeListener(this);
		update();
	}

	/**
	 * Returns the value of data.
	 */
	public ArrayList<Line> getData() {
		return data;
	}

	/**
	 * Sets the value of data.
	 * 
	 * @param data
	 *            The value to assign data.
	 */
	public void setData(ArrayList<Line> data) {
		this.data = data;
	}

	/**
	 * Constructor LineChart
	 * 
	 * 
	 * @param data
	 * @param xAxis
	 * @param yAxis
	 * @param background
	 * 
	 */
	public LineChart(ArrayList<Line> data, Axis xAxis, Axis yAxis,
			Paint background) {
		this.data = data;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.background = background;
	}

	/**
	 * Constructor LineChart
	 * 
	 * 
	 * @param other
	 * 
	 */
	public LineChart(LineChart other) {
		if (this != other) {
			this.data = other.data;
			this.xAxis = other.xAxis;
			this.yAxis = other.yAxis;
			this.background = other.background;
		}
	}

	/**
	 * Method setXAxis
	 * 
	 * 
	 * @param xAxis
	 * 
	 */
	public void setXAxis(Axis xAxis) {
		this.xAxis = xAxis;
	}

	/**
	 * Method setYAxis
	 * 
	 * 
	 * @param yAxis
	 * 
	 */
	public void setYAxis(Axis yAxis) {
		this.yAxis = yAxis;
	}

	/**
	 * Method setBackground
	 * 
	 * 
	 * @param background
	 * 
	 * 
	 *            public void setBackground(Paint background) { this.background
	 *            = background; }
	 */

	/**
	 * Method getXAxis
	 * 
	 * 
	 * @return
	 * 
	 */
	public Axis getXAxis() {
		return (this.xAxis);
	}

	/**
	 * Method getYAxis
	 * 
	 * 
	 * @return
	 * 
	 */
	public Axis getYAxis() {
		return (this.yAxis);
	}

	/**
	 * Method getBackground
	 * 
	 * 
	 * @return
	 * 
	 * 
	 *         public Paint getPBackground() { return (this.background); }
	 */

	/**
	 * Method toString
	 * 
	 * 
	 * @return
	 * 
	 */
	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("data = ");
		buffer.append(data);
		buffer.append(sep);
		buffer.append("xAxis = ");
		buffer.append(xAxis);
		buffer.append(sep);
		buffer.append("yAxis = ");
		buffer.append(yAxis);
		buffer.append(sep);
		buffer.append("background = ");
		buffer.append(background);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}
}

/**
 * Stores a point on the chart I made this with a key and a value to make it
 * more useful to other charts such as Pie Chart
 */
class ChartItem {
	private Object key;
	private Number value;

	public ChartItem(Object key, Number value) {
		this.key = key;
		this.value = value;
	}

	public ChartItem(Number val) {
		value = val;
	}

	/**
	 * Returns the value of value.
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * Sets the value of value.
	 * 
	 * @param value
	 *            The value to assign value.
	 */
	public void setValue(Number value) {
		this.value = value;
	}

	/**
	 * Returns the value of key.
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * Sets the value of key.
	 * 
	 * @param key
	 *            The value to assign key.
	 */
	public void setKey(Object key) {
		this.key = key;
	}

	public String stringValue() {
		return value.toString();
	}

	public double doubleValue() {
		return value.doubleValue();
	}

	public int intValue() {
		return value.intValue();
	}

	public short shortValue() {
		return value.shortValue();
	}

	public long longValue() {
		return value.longValue();
	}

	public byte byteValue() {
		return value.byteValue();
	}

	public float floatValue() {
		return value.floatValue();
	}

	public String toString() {
		return this.getClass() + "[key=" + key + ",value=" + value + "]";
	}
}

/**
 * Useful class for displaying charts
 */
class JavChartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8316183117292261272L;
	private JLabel title;
	// private int titleFormat;
	private JavChart chart;
	private Paint background;

	/*
	 * public final static int BOXED_TITLE = 2; public final static int NO_TITLE
	 * = 0; public final static int REGULAR_TITLE = 1; public final static int
	 * BOLD_TITLE = 4;
	 */
	public JavChartPanel() {
		super();
		// title = new JLabel("");
		// add(title, BorderLayout.NORTH);
		// title.setHorizontalTextPosition(SwingConstants.CENTER);
		setLayout(new BorderLayout());
		// titleFormat = NO_TITLE;
	}

	/**
	 * Returns the value of chart.
	 */
	public JavChart getChart() {
		return chart;
	}

	/**
	 * Sets the value of chart.
	 * 
	 * @param chart
	 *            The value to assign chart.
	 */
	public void setChart(JavChart chart) {
		// if(chart != null)
		// remove(this.chart);
		this.chart = chart;
		add(chart, BorderLayout.CENTER);
	}

	/**
	 * Returns the value of title.
	 */
	public JLabel getTitle() {
		return title;
	}

	/**
	 * Sets the value of title.
	 * 
	 * @param title
	 *            The value to assign title.
	 */
	public void setTitle(JLabel title) {
		if (this.title != null)
			remove(this.title);
		this.title = title;
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Returns the value of titleFormat.
	 * 
	 * public int getTitleFormat(){ return titleFormat; }
	 * 
	 * /** Sets the value of titleFormat.
	 * 
	 * @param titleFormat
	 *            The value to assign titleFormat.
	 * 
	 *            public void setTitleFormat(int titleFormat){ this.titleFormat
	 *            = titleFormat; }
	 */
}

/**
 * Stores the title, paint, and the font associeated with it
 */
class Title {
	String title = "Years";
	Font font = new Font("Arail", Font.BOLD, 32);
	Paint paint = Color.black;

	/**
	 * Constructs a default title
	 */
	public Title() {
		title = "Years";
		font = new Font("Arail", Font.BOLD, 32);
		paint = Color.black;
	}

	/**
	 * Constructor Title
	 * 
	 * 
	 * @param title
	 * @param font
	 * @param paint
	 * 
	 */
	public Title(String title, Font font, Paint paint) {
		this.title = title;
		this.font = font;
		this.paint = paint;
	}

	/**
	 * Constructor Title
	 * 
	 * 
	 * @param other
	 * 
	 */
	public Title(Title other) {
		if (this != other) {
			this.title = other.title;
			this.font = other.font;
			this.paint = other.paint;
		}
	}

	/**
	 * Method setTitle
	 * 
	 * 
	 * @param title
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method setFont
	 * 
	 * 
	 * @param font
	 * 
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Method setPaint
	 * 
	 * 
	 * @param paint
	 * 
	 */
	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	/**
	 * Method getTitle
	 * 
	 * 
	 * @return
	 * 
	 */
	public String getTitle() {
		return (this.title);
	}

	/**
	 * Method getFont
	 * 
	 * 
	 * @return
	 * 
	 */
	public Font getFont() {
		return (this.font);
	}

	/**
	 * Method getPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getPaint() {
		return (this.paint);
	}

	/**
	 * Method toString
	 * 
	 * 
	 * @return
	 * 
	 */
	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("title = ");
		buffer.append(title);
		buffer.append(sep);
		buffer.append("font = ");
		buffer.append(font);
		buffer.append(sep);
		buffer.append("paint = ");
		buffer.append(paint);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}
}

/*
 * Class Axis stores information about the axisYou need to create 2 Axises for
 * getting a X Axis and a Y Axis in the ChartYou need to implement the painting
 * of the axis
 */
class Axis {
	/*
	 * Stores the
	 */
	Number max = new Double(1);
	Number min = new Double(0);
	Title title = new Title();
	double majorAxis = 10;
	double minorAxis = 2;
	// int axisFormat;
	boolean major = true;
	boolean minor = true;
	Font majorFont = new Font("Arail", Font.BOLD, 12);
	Font minorFont = new Font("Arail", Font.BOLD, 12);
	boolean majorWords = true;
	boolean minorWords = false;
	int majorSize = 1500;
	int minorSize = 4;
	boolean paintAxis = true;
	Stroke minorAxisStroke = new BasicStroke(1);
	Paint minorAxisPaint = Color.black;
	Stroke majorAxisStroke = new BasicStroke(1);
	Paint majorAxisPaint = Color.black;
	public static final int NO_MINOR = 1;
	public static final int MAJOR_NOTCHES = 2;
	public static final int MINOR_NOTCHES = 4;
	public static final int MAJOR_LINES = 8;
	public static final int MINOR_LINES = 16;
	public static final int NO_MAJOR = 32;
	public static final int MINOR_WORDS = 64;
	public static final int MAJOR_WORDS = 128;

	public Axis() {

	}

	/**
	 * Calclates that space needed for this axis if it were the x-axis
	 */
	@SuppressWarnings("deprecation")
	public int getXSpace() {
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(majorFont);
		FontMetrics fm2 = Toolkit.getDefaultToolkit()
				.getFontMetrics(title.font);
		int size = 0;
		size += fm2.getHeight();
		size += fm.getHeight();
		return size;
	}

	/**
	 * Calclates that space needed for this axis if it were the y-axis
	 */
	@SuppressWarnings("deprecation")
	public int getYSpace() {
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(majorFont);
		FontMetrics fm2 = Toolkit.getDefaultToolkit()
				.getFontMetrics(title.font);
		int size = 0;
		size += fm2.charWidth('m');

		size += fm.charWidth('m')
				* ((int) (Math.log(max.doubleValue()) / Math.log(10)) + 1);
		// System.out.println("Size"+((int)Math.log(yMax.doubleValue())+1));
		// System.out.println(size);
		// size+=5;
		return size;
	}

	public Number getMin() {
		return min;
	}

	public Number getMax() {
		return max;
	}

	public void setMin(Number newNum) {
		min = newNum;
	}

	public void setMax(Number newNum) {
		max = newNum;
	}

	/*
	 * @return String toString for printing and debugging
	 */

	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("max = ");
		buffer.append(max);
		buffer.append(sep);
		buffer.append("min = ");
		buffer.append(min);
		buffer.append(sep);
		buffer.append("title = ");
		buffer.append(title);
		buffer.append(sep);
		buffer.append("majorAxis = ");
		buffer.append(majorAxis);
		buffer.append(sep);
		buffer.append("minorAxis = ");
		buffer.append(minorAxis);
		buffer.append(sep);
		// buffer.append("axisFormat = ");
		// buffer.append(axisFormat);
		buffer.append(sep);
		buffer.append("major = ");
		buffer.append(major);
		buffer.append(sep);
		buffer.append("minor = ");
		buffer.append(minor);
		buffer.append(sep);
		buffer.append("majorFont = ");
		buffer.append(majorFont);
		buffer.append(sep);
		buffer.append("minorFont = ");
		buffer.append(minorFont);
		buffer.append(sep);
		buffer.append("majorWords = ");
		buffer.append(majorWords);
		buffer.append(sep);
		buffer.append("minorWords = ");
		buffer.append(minorWords);
		buffer.append(sep);
		buffer.append("majorSize = ");
		buffer.append(majorSize);
		buffer.append(sep);
		buffer.append("minorSize = ");
		buffer.append(minorSize);
		buffer.append(sep);
		buffer.append("paintAxis = ");
		buffer.append(paintAxis);
		buffer.append(sep);
		buffer.append("minorAxisStroke = ");
		buffer.append(minorAxisStroke);
		buffer.append(sep);
		buffer.append("minorAxisPaint = ");
		buffer.append(minorAxisPaint);
		buffer.append(sep);
		buffer.append("majorAxisStroke = ");
		buffer.append(majorAxisStroke);
		buffer.append(sep);
		buffer.append("majorAxisPaint = ");
		buffer.append(majorAxisPaint);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Constructor Axis
	 * 
	 * 
	 * @param max
	 * @param min
	 * @param title
	 * @param majorAxis
	 * @param minorAxis
	 * @param axisFormat
	 * @param major
	 * @param minor
	 * @param majorFont
	 * @param minorFont
	 * @param majorWords
	 * @param minorWords
	 * @param majorSize
	 * @param minorSize
	 * @param paintAxis
	 * @param minorAxisStroke
	 * @param minorAxisPaint
	 * @param majorAxisStroke
	 * @param majorAxisPaint
	 * 
	 */
	public Axis(Number max, Number min, Title title, double majorAxis,
			double minorAxis, /* int axisFormat, */boolean major, boolean minor,
			Font majorFont, Font minorFont, boolean majorWords,
			boolean minorWords, int majorSize, int minorSize,
			boolean paintAxis, Stroke minorAxisStroke, Paint minorAxisPaint,
			Stroke majorAxisStroke, Paint majorAxisPaint) {
		this.max = max;
		this.min = min;
		this.title = title;
		this.majorAxis = majorAxis;
		this.minorAxis = minorAxis;
		// this.axisFormat = axisFormat;
		this.major = major;
		this.minor = minor;
		this.majorFont = majorFont;
		this.minorFont = minorFont;
		this.majorWords = majorWords;
		this.minorWords = minorWords;
		this.majorSize = majorSize;
		this.minorSize = minorSize;
		this.paintAxis = paintAxis;
		this.minorAxisStroke = minorAxisStroke;
		this.minorAxisPaint = minorAxisPaint;
		this.majorAxisStroke = majorAxisStroke;
		this.majorAxisPaint = majorAxisPaint;

	}

	/**
	 * Constructor Axis
	 * 
	 * 
	 * @param other
	 * 
	 */
	public Axis(Axis other) {
		if (this != other) {
			this.max = other.max;
			this.min = other.min;
			this.title = other.title;
			this.majorAxis = other.majorAxis;
			this.minorAxis = other.minorAxis;
			// this.axisFormat = other.axisFormat;
			this.major = other.major;
			this.minor = other.minor;
			this.majorFont = other.majorFont;
			this.minorFont = other.minorFont;
			this.majorWords = other.majorWords;
			this.minorWords = other.minorWords;
			this.majorSize = other.majorSize;
			this.minorSize = other.minorSize;
			this.paintAxis = other.paintAxis;
			this.minorAxisStroke = other.minorAxisStroke;
			this.minorAxisPaint = other.minorAxisPaint;
			this.majorAxisStroke = other.majorAxisStroke;
			this.majorAxisPaint = other.majorAxisPaint;

		}
	}

	/**
	 * Method setTitle
	 * 
	 * 
	 * @param title
	 * 
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * Method setMajorAxis
	 * 
	 * 
	 * @param majorAxis
	 * 
	 */
	public void setMajorAxis(double majorAxis) {
		this.majorAxis = majorAxis;
	}

	/**
	 * Method setMinorAxis
	 * 
	 * 
	 * @param minorAxis
	 * 
	 */
	public void setMinorAxis(double minorAxis) {
		this.minorAxis = minorAxis;
	}

	/**
	 * Method setAxisFormat
	 * 
	 * 
	 * @param axisFormat
	 * 
	 */
	/*
	 * public void setAxisFormat(int axisFormat) { this.axisFormat = axisFormat;
	 * }
	 */

	/**
	 * Method setMajor
	 * 
	 * 
	 * @param major
	 * 
	 */
	public void setMajor(boolean major) {
		this.major = major;
	}

	/**
	 * Method setMinor
	 * 
	 * 
	 * @param minor
	 * 
	 */
	public void setMinor(boolean minor) {
		this.minor = minor;
	}

	/**
	 * Method setMajorFont
	 * 
	 * 
	 * @param majorFont
	 * 
	 */
	public void setMajorFont(Font majorFont) {
		this.majorFont = majorFont;
	}

	/**
	 * Method setMinorFont
	 * 
	 * 
	 * @param minorFont
	 * 
	 */
	public void setMinorFont(Font minorFont) {
		this.minorFont = minorFont;
	}

	/**
	 * Method setMajorWords
	 * 
	 * 
	 * @param majorWords
	 * 
	 */
	public void setMajorWords(boolean majorWords) {
		this.majorWords = majorWords;
	}

	/**
	 * Method setMinorWords
	 * 
	 * 
	 * @param minorWords
	 * 
	 */
	public void setMinorWords(boolean minorWords) {
		this.minorWords = minorWords;
	}

	/**
	 * Method setMajorSize
	 * 
	 * 
	 * @param majorSize
	 * 
	 */
	public void setMajorSize(int majorSize) {
		this.majorSize = majorSize;
	}

	/**
	 * Method setMinorSize
	 * 
	 * 
	 * @param minorSize
	 * 
	 */
	public void setMinorSize(int minorSize) {
		this.minorSize = minorSize;
	}

	/**
	 * Method setPaintAxis
	 * 
	 * 
	 * @param paintAxis
	 * 
	 */
	public void setPaintAxis(boolean paintAxis) {
		this.paintAxis = paintAxis;
	}

	/**
	 * Method setMinorAxisStroke
	 * 
	 * 
	 * @param minorAxisStroke
	 * 
	 */
	public void setMinorAxisStroke(Stroke minorAxisStroke) {
		this.minorAxisStroke = minorAxisStroke;
	}

	/**
	 * Method setMinorAxisPaint
	 * 
	 * 
	 * @param minorAxisPaint
	 * 
	 */
	public void setMinorAxisPaint(Paint minorAxisPaint) {
		this.minorAxisPaint = minorAxisPaint;
	}

	/**
	 * Method setMajorAxisStroke
	 * 
	 * 
	 * @param majorAxisStroke
	 * 
	 */
	public void setMajorAxisStroke(Stroke majorAxisStroke) {
		this.majorAxisStroke = majorAxisStroke;
	}

	/**
	 * Method setMajorAxisPaint
	 * 
	 * 
	 * @param majorAxisPaint
	 * 
	 */
	public void setMajorAxisPaint(Paint majorAxisPaint) {
		this.majorAxisPaint = majorAxisPaint;
	}

	/**
	 * Method getTitle
	 * 
	 * 
	 * @return
	 * 
	 */
	public Title getTitle() {
		return (this.title);
	}

	/**
	 * Method getMajorAxis
	 * 
	 * 
	 * @return
	 * 
	 */
	public double getMajorAxis() {
		return (this.majorAxis);
	}

	/**
	 * Method getMinorAxis
	 * 
	 * 
	 * @return
	 * 
	 */
	public double getMinorAxis() {
		return (this.minorAxis);
	}

	/**
	 * Method getAxisFormat
	 * 
	 * 
	 * @return
	 * 
	 */
	/*
	 * public int getAxisFormat() { return (this.axisFormat); }
	 */

	/**
	 * Method getMajor
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getMajor() {
		return (this.major);
	}

	/**
	 * Method getMinor
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getMinor() {
		return (this.minor);
	}

	/**
	 * Method getMajorFont
	 * 
	 * 
	 * @return
	 * 
	 */
	public Font getMajorFont() {
		return (this.majorFont);
	}

	/**
	 * Method getMinorFont
	 * 
	 * 
	 * @return
	 * 
	 */
	public Font getMinorFont() {
		return (this.minorFont);
	}

	/**
	 * Method getMajorWords
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getMajorWords() {
		return (this.majorWords);
	}

	/**
	 * Method getMinorWords
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getMinorWords() {
		return (this.minorWords);
	}

	/**
	 * Method getMajorSize
	 * 
	 * 
	 * @return
	 * 
	 */
	public int getMajorSize() {
		return (this.majorSize);
	}

	/**
	 * Method getMinorSize
	 * 
	 * 
	 * @return
	 * 
	 */
	public int getMinorSize() {
		return (this.minorSize);
	}

	/**
	 * Method getPaintAxis
	 * 
	 * 
	 * @return
	 * 
	 */
	public boolean getPaintAxis() {
		return (this.paintAxis);
	}

	/**
	 * Method getMinorAxisStroke
	 * 
	 * 
	 * @return
	 * 
	 */
	public Stroke getMinorAxisStroke() {
		return (this.minorAxisStroke);
	}

	/**
	 * Method getMinorAxisPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getMinorAxisPaint() {
		return (this.minorAxisPaint);
	}

	/**
	 * Method getMajorAxisStroke
	 * 
	 * 
	 * @return
	 * 
	 */
	public Stroke getMajorAxisStroke() {
		return (this.majorAxisStroke);
	}

	/**
	 * Method getMajorAxisPaint
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getMajorAxisPaint() {
		return (this.majorAxisPaint);
	}
}

/*
 * class Axes{ Number yMax=new Double(0.0); Number yMin=new Double(0.0); double
 * xMin=0; double xMax=50; Title xTitle=new Title(); Title yTitle=new Title();
 * double yMajorAxis=10; double yMinorAxis=2; double xMajorAxis=10; double
 * xMinorAxis=2; int yAxisFormat; int xAxisFormat; boolean xMajor=true; boolean
 * xMinor=true; boolean yMajor=true; boolean yMinor=true; Font xFont=new
 * Font("Arail",Font.BOLD,12); Font yFont=new Font("Arail",Font.BOLD,12);
 * boolean xMajorWords=true; boolean xMinorWords=false; boolean
 * yMajorWords=true; boolean yMinorWords=false; int xMajorSize=1500; int
 * xMinorSize=4; int yMajorSize=1500; int yMinorSize=4; //int notchSize; //int
 * lineSize; boolean paintXAxis=true; boolean paintYAxis=true; Stroke
 * xMinorAxisStroke=new BasicStroke(1); Paint xMinorAxisPaint=Color.black;
 * Stroke xMajorAxisStroke=new BasicStroke(1); Paint
 * xMajorAxisPaint=Color.black; Stroke yMinorAxisStroke=new BasicStroke(1);
 * Paint yMinorAxisPaint=Color.black; Stroke yMajorAxisStroke=new
 * BasicStroke(1); Paint yMajorAxisPaint=Color.black; public static final int
 * NO_MINOR = 1; public static final int MAJOR_NOTCHES = 2; public static final
 * int MINOR_NOTCHES = 4; public static final int MAJOR_LINES = 8; public static
 * final int MINOR_LINES = 16; public static final int NO_MAJOR = 32; public
 * static final int MINOR_WORDS = 64; public static final int MAJOR_WORDS = 128;
 * public double yMax(){ return 70.0; } public double yMin(){ return 0.0; }
 * public Number getYMin(){ return yMin; } public Number getYMax(){ return yMax;
 * } public void setYMin(Number newNum){ yMin = newNum; } public void
 * setYMax(Number newNum){ yMax = newNum; } public int getXSpace(){ FontMetrics
 * fm = Toolkit.getDefaultToolkit().getFontMetrics(xFont); FontMetrics fm2 =
 * Toolkit.getDefaultToolkit().getFontMetrics(xTitle.font); int size = 0;
 * size+=fm2.getHeight(); size+=fm.getHeight(); return size; } public int
 * getYSpace(){ FontMetrics fm =
 * Toolkit.getDefaultToolkit().getFontMetrics(yFont); FontMetrics fm2 =
 * Toolkit.getDefaultToolkit().getFontMetrics(yTitle.font); int size = 0;
 * size+=fm2.charWidth('M');
 * 
 * size+=fm.charWidth('M')*((int)(Math.log(yMax.doubleValue())/Math.log(10))+1);
 * //System.out.println("Size"+((int)Math.log(yMax.doubleValue())+1));
 * //System.out.println(size); //size+=5; return size; } }
 */
/*
 * class CollectionVisual { public static void main(String args[]) { JFrame
 * frame = new JFrame("List Visual Sample");
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * 
 * ArrayList<Integer> s = new ArrayList<Integer>(); for(int i =0; i<10; i++){
 * int rand=(int)(Math.random() * i + Math.random() * 1564); //if(rand > 0)
 * s.add(rand); //else if(!s.empty()) // s.pop(); } ListVisualizer panel = new
 * ListVisualizer(s); //panel.setPreferredSize(new Dimension(100,1000));
 * JScrollPane jScrollPane = new JScrollPane();
 * jScrollPane.setViewportView(panel);
 * 
 * frame.add(jScrollPane, BorderLayout.CENTER); //DnDJPanel c=new DnDJPanel();
 * //c.add(panel); //JButton b = new JButton("drag me");
 * //b.setTransferHandler(new TransferHandler()); //b.setBounds(0,0,80,20);
 * //c.add(b); //frame.setContentPane(panel); frame.setSize(400, 450);
 * frame.setVisible(true); } }
 */
class ListVisualizer extends JPanel implements MouseListener,
		MouseMotionListener {
	boolean isEditable;
	Font font;
	Paint boxForeground;
	Paint background;
	Paint textForeground;
	Stroke stroke;
	private MouseEvent firstMouseEvent = null;
	java.util.List c;
	GridLayout grid;

	public ListVisualizer(java.util.List c) {
		addMouseMotionListener(this);
		addMouseListener(this);
		// stack = (s);
		isEditable = false;
		// isBoxed = true;
		font = new Font("Arial", Font.BOLD, 12);
		background = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		textForeground = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		;
		boxForeground = new GradientPaint(1, 1, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), 50, 50, new Color(
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8),
				(int) (Math.random() * 245 + 8)), true);
		;
		stroke = new BasicStroke(8);
		grid = new GridLayout(c.size(), 1, 10, 10);
		this.c = c;
		setAutoscrolls(true);
		// setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		make();

	}

	public void paint(Graphics gr) {
		Graphics2D g = ((Graphics2D) (gr));
		g.setPaint(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		paintChildren(gr);
		paintComponent(gr);

	}

	public void paintComponent(Graphics gr) {
		Graphics2D g = ((Graphics2D) (gr));
		if (child != null) {
			g.setPaint(Color.white);
			g.fillRect(1, findIndex() * (child.getHeight() + grid.getVgap()),
					this.getWidth(), 5);
			// System.out.println("Paint this");
		}
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	Component child; // A component inside the JPanel.
	int xAdjustment = 0, yAdjustment = 0; // Used for correlating the Panel
											// coordinate space with the

	// child coordinate space. See the TransferHandler code.
	/**
	 * Adjustment must take place at the time of the mouse press, because this
	 * is the only time we can guarantee that the mouse is inside the boundaries
	 * of our container.
	 */
	public void mousePressed(MouseEvent e) {
		Point componentLocation;

		child = getComponentAt(e.getPoint());

		// Don't bother to drag if there is nothing there.
		if (getComponentAt(e.getPoint()) != this) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				((CollectionItem) child).ison = true;
				// child.requestFocus();
				componentLocation = child.getLocation();
				firstMouseEvent = e;
				xAdjustment = componentLocation.x - e.getX();
				yAdjustment = componentLocation.y - e.getY();
			} else if (SwingUtilities.isRightMouseButton(e)) {

				JPopupMenu menu = new JPopupMenu();
				Method[] methods = getMethods(((CollectionItem) child)
						.getObject());
				Class oldDcl = null;
				for (int i = 0; i < methods.length; i++) {
					Class dcl = methods[i].getDeclaringClass();
					if (dcl != Object.class) {
						if (i > 0 && dcl != oldDcl)
							menu.addSeparator();
						menu.add(new MethodItem(methods[i],
								((CollectionItem) child).getObject()));
					}
					oldDcl = dcl;
				}
				menu.show(this, e.getX(), e.getY());
				child = null;
			}
		} else {
			firstMouseEvent = null;
			child = null;
		}
		e.consume();

	}

	public void make() {
		removeAll();
		setLayout(grid);
		setPreferredSize(new Dimension(100, 50 * c.size()));
		Iterator i = c.iterator();
		while (i.hasNext()) {
			CollectionItem ci = new CollectionItem(i.next(), boxForeground,
					textForeground, font, stroke);
			add(ci);
		}
		// repaint(0,newy-getComponent(0).getHeight(),getWidth(),newy+getComponent(0).getHeight());
		child = null;

		// System.out.println("done with making:"+this);
	}

	int newx = 0, newy = 0;
	int realx = 0, realy = 0;
	// Arbitrarily define a 5-pixel shift as the official beginning of a drag.
	final static int DX = 5;
	final static int DY = 5;

	public int findIndex() {
		// int y = this.getHeight()/child.getHeight();
		// System.out.println("Newy: "+newy+" child.getHieght():"+child.getHeight());
		return (int) ((double) (newy + child.getHeight() / 2)
				/ (double) (child.getHeight() + grid.getVgap()) + 0.5);
	}

	public void mouseDragged(MouseEvent e) {
		// Move the component. Otherwise, prior to recognizing the drag, the
		// component
		// won't follow the mouse.
		if (child != null) {
			repaint(0, newy - child.getHeight() / 2, getWidth(),
					newy + child.getHeight() / 2);
			newx = e.getX() + xAdjustment;
			newy = e.getY() + yAdjustment;
			realx = newx;
			realy = newy;
			if ((newx + child.getWidth()) > this.getWidth()) {
				newx = this.getWidth() - child.getWidth();
			}
			if ((newy + child.getHeight()) > this.getHeight()) {
				newy = this.getHeight() - child.getHeight();
			}
			if (newx < 0)
				newx = 0;
			if (newy < 0)
				newy = 0;
			child.setLocation(newx, newy);

			// getVerticalScrollBar().
		}
		if (firstMouseEvent != null) {
			e.consume();
			if (!SwingUtilities.isLeftMouseButton(e))
				return;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (child != null) {
			c.remove(((CollectionItem) child).getObject());// ((CollectionItem)child).ison
															// = false;
			int index = findIndex();
			if (index > c.size())
				index = c.size();
			if (index < 0)
				index = 0;
			c.add(index, ((CollectionItem) child).getObject());
			// System.out.println("FindIndex: "+findIndex());
			make();
			validate();
			repaint();
			// paint(getGraphics());
			// child.repaint();
			// child.setLocation(newx,findY());
		}
		// System.out.println("MouseReleased - Child:"+child);
		/*
		 * for(Component c:getComponents() ){ ((CollectionItem)c).ison = false;
		 * }
		 */

	}

	public void mouseMoved(MouseEvent e) {
		/*
		 * if (getComponentAt(e.getPoint()) != this) {
		 * ((CollectionItem)getComponentAt(e.getPoint())).ison = true; }
		 */
	}

	private Method[] getMethods(Object occupant) {
		Class cl = occupant.getClass();
		Method[] methods = cl.getMethods();

		Arrays.sort(methods, new Comparator<Method>() {
			public int compare(Method m1, Method m2) {
				int d1 = depth(m1.getDeclaringClass());
				int d2 = depth(m2.getDeclaringClass());
				if (d1 != d2)
					return d2 - d1;
				int d = m1.getName().compareTo(m2.getName());
				if (d != 0)
					return d;
				d1 = m1.getParameterTypes().length;
				d2 = m2.getParameterTypes().length;
				return d1 - d2;
			}

			private int depth(Class cl) {
				if (cl == null)
					return 0;
				else
					return 1 + depth(cl.getSuperclass());
			}
		});
		return methods;
	}

	/**
	 * Constructor ListVisualizer
	 * 
	 * 
	 * @param isEditable
	 * @param font
	 * @param boxForeground
	 * @param background
	 * @param textForeground
	 * @param stroke
	 * @param firstMouseEvent
	 * @param c
	 * @param grid
	 * @param child
	 * @param xAdjustment
	 * @param yAdjustment
	 * @param newx
	 * @param newy
	 * @param realx
	 * @param realy
	 * 
	 */
	public ListVisualizer(boolean isEditable, Font font, Paint boxForeground,
			Paint background, Paint textForeground, Stroke stroke,
			java.util.List c) {
		this.isEditable = isEditable;
		this.font = font;
		this.boxForeground = boxForeground;
		this.background = background;
		this.textForeground = textForeground;
		this.stroke = stroke;
		this.firstMouseEvent = firstMouseEvent;
		this.c = c;

	}

	/**
	 * Constructor ListVisualizer
	 * 
	 * 
	 * @param other
	 * 
	 */
	public ListVisualizer(ListVisualizer other) {
		if (this != other) {
			this.isEditable = other.isEditable;
			this.font = other.font;
			this.boxForeground = other.boxForeground;
			this.background = other.background;
			this.textForeground = other.textForeground;
			this.stroke = other.stroke;
			this.firstMouseEvent = other.firstMouseEvent;
			this.c = other.c;
			this.grid = other.grid;
			this.child = other.child;
			this.xAdjustment = other.xAdjustment;
			this.yAdjustment = other.yAdjustment;
			this.newx = other.newx;
			this.newy = other.newy;
			this.realx = other.realx;
			this.realy = other.realy;

		}
	}

	/**
	 * Method setIsEditable
	 * 
	 * 
	 * @param isEditable
	 * 
	 */
	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	/**
	 * Method setFont
	 * 
	 * 
	 * @param font
	 * 
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Method setBoxForeground
	 * 
	 * 
	 * @param boxForeground
	 * 
	 */
	public void setBoxForeground(Paint boxForeground) {
		this.boxForeground = boxForeground;
	}

	/**
	 * Method setBackground
	 * 
	 * 
	 * @param background
	 * 
	 */
	public void setBackground(Paint background) {
		this.background = background;
	}

	/**
	 * Method setTextForeground
	 * 
	 * 
	 * @param textForeground
	 * 
	 */
	public void setTextForeground(Paint textForeground) {
		this.textForeground = textForeground;
	}

	/**
	 * Method setStroke
	 * 
	 * 
	 * @param stroke
	 * 
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	/**
	 * Method setC
	 * 
	 * 
	 * @param c
	 * 
	 */
	public void setList(java.util.List c) {
		this.c = c;
		make();
	}

	/**
	 * Method getFont
	 * 
	 * 
	 * @return
	 * 
	 */
	public Font getFont() {
		return (this.font);
	}

	/**
	 * Method getBoxForeground
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getBoxForeground() {
		return (this.boxForeground);
	}

	/**
	 * Method getBackground
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getPBackground() {
		return (this.background);
	}

	/**
	 * Method getTextForeground
	 * 
	 * 
	 * @return
	 * 
	 */
	public Paint getTextForeground() {
		return (this.textForeground);
	}

	/**
	 * Method getStroke
	 * 
	 * 
	 * @return
	 * 
	 */
	public Stroke getStroke() {
		return (this.stroke);
	}

	/**
	 * Method getC
	 * 
	 * 
	 * @return
	 * 
	 */
	public java.util.List getList() {
		return (this.c);
	}

	/**
	 * Method toString
	 * 
	 * 
	 * @return
	 * 
	 */
	public String toString() {

		String sep = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder(this.getClass().getName());
		buffer.append("[");
		buffer.append(sep);
		buffer.append("isEditable = ");
		buffer.append(isEditable);
		buffer.append(sep);
		buffer.append("font = ");
		buffer.append(font);
		buffer.append(sep);
		buffer.append("boxForeground = ");
		buffer.append(boxForeground);
		buffer.append(sep);
		buffer.append("background = ");
		buffer.append(background);
		buffer.append(sep);
		buffer.append("textForeground = ");
		buffer.append(textForeground);
		buffer.append(sep);
		buffer.append("stroke = ");
		buffer.append(stroke);
		buffer.append(sep);
		buffer.append("firstMouseEvent = ");
		buffer.append(firstMouseEvent);
		buffer.append(sep);
		buffer.append("c = ");
		buffer.append(c);
		buffer.append(sep);
		buffer.append("grid = ");
		buffer.append(grid);
		buffer.append(sep);
		buffer.append("child = ");
		buffer.append(child);
		buffer.append(sep);
		buffer.append("xAdjustment = ");
		buffer.append(xAdjustment);
		buffer.append(sep);
		buffer.append("yAdjustment = ");
		buffer.append(yAdjustment);
		buffer.append(sep);
		buffer.append("newx = ");
		buffer.append(newx);
		buffer.append(sep);
		buffer.append("newy = ");
		buffer.append(newy);
		buffer.append(sep);
		buffer.append("realx = ");
		buffer.append(realx);
		buffer.append(sep);
		buffer.append("realy = ");
		buffer.append(realy);
		buffer.append(sep);
		buffer.append("DX = ");
		buffer.append(DX);
		buffer.append(sep);
		buffer.append("DY = ");
		buffer.append(DY);
		buffer.append(sep);

		buffer.append("]");
		return buffer.toString();
	}

	class CollectionItem extends AbstractButton {
		boolean ison;
		Object obj;
		// Object occupant;
		// ActionEvent evt;
		Stroke s;
		Paint backGround;// =new
							// GradientPaint(0,0,Color.orange,25,50,Color.yellow,true);
		Paint foreGround;// =new
							// GradientPaint(0,0,Color.green,25,50,Color.black,true);
		Font f;// =new Font("Arail",Font.BOLD,12);
		// FontMetrics fm=/*Toolkit.getDefaultToolkit().*/getFontMetrics(f);
		int offset = 0, sw;

		public CollectionItem(Object o, Paint bg, Paint fg, Font font, Stroke bs) {
			obj = o;
			// if(obj == null)
			// System.out.println("awdf");
			// occupant=o;
			foreGround = fg;
			backGround = bg;
			f = font;
			s = bs;
			sw = getFontMetrics(f).stringWidth(obj.toString()) / 2;
			// evt=
			// this.addMouseListener(this);
			setVisible(true);
		}

		public void paint(Graphics gr) {
			Graphics2D g = (Graphics2D) gr;
			g.setPaint(backGround);
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			g.setPaint(foreGround);
			if (ison) {
				g.setStroke(s);
				g.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
			}
			g.setFont(f);
			g.drawString(obj.toString(), this.getWidth() / 2 - sw + offset,
					getHeight() / 2 + 5 + offset);
		}

		/*
		 * public void setLocation(int x, int y){ super.setLocation(x,y);
		 * System.out.println("setLocation: x = "+x+" y = "+y); }
		 */
		public void setObject(Object o) {
			obj = o;
			// sw=getFontMetrics(f).stringWidth(obj.toString())/2;
			// evt=new ActionEvent(this,ActionEvent.ACTION_PERFORMED,obj);
			repaint();
		}

		public void setFont(Font fo) {
			f = fo;
			// fm=/*Toolkit.getDefaultToolkit().*/getFontMetrics(f);
			sw = getFontMetrics(f).stringWidth(obj.toString()) / 2;
		}

		public Font getFont() {
			return f;
		}

		public String getText() {
			if (obj != null)
				return obj.toString();
			return null;
		}

		public Object getObject() {
			return obj;
		}

		/*
		 * public void mouseEntered(MouseEvent e) { ison=true;
		 * fireStateChanged(); repaint(); } public void doClick(){
		 * this.fireActionPerformed(new
		 * ActionEvent(this,ActionEvent.ACTION_PERFORMED,obj.toString())); }
		 * public void mouseExited(MouseEvent e) { ison=false; repaint(); }
		 * public void mouseReleased(MouseEvent e) { offset=0;
		 * this.fireActionPerformed(new
		 * ActionEvent(this,ActionEvent.ACTION_PERFORMED,obj.toString()));
		 * //if(i!=null){ // drag.drag=false; //FindAndPlace(e); //} repaint();
		 * }
		 * 
		 * public void mousePressed(MouseEvent e) { offset=5;
		 * //if(addx!=-1&&addy!=-1){ // drag.drag=true; // drag.x=e.getX(); //
		 * drag.y=e.getY(); //drag.i=i; // drag.c=this; //drag //
		 * drag.addx=getX()-e.getX(); // drag.addy=getY()-e.getY(); // //}
		 * //occupant = occupant;
		 * 
		 * repaint();
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * public void mouseClicked(MouseEvent e) {}
		 */
		public Dimension getMinimumSize() {
			return new Dimension(
					getFontMetrics(f).stringWidth(obj.toString()) / 2 + 5, 25);
		}

		public Dimension getPreferredSize() {
			return new Dimension(
					getFontMetrics(f).stringWidth(obj.toString()) / 2 + 20, 50);
		}

	}

	private class MethodItem extends MCItem implements ActionListener {
		public MethodItem(Method m, Object occupant) {
			setText(getDisplayString(m.getReturnType(), m.getName(),
					m.getParameterTypes()));
			this.m = m;
			this.occupant = occupant;
			addActionListener(this);
			// setIcon(displayMap.getIcon(m.getDeclaringClass(), 16, 16));
		}

		public void actionPerformed(ActionEvent event) {
			Class[] types = m.getParameterTypes();
			Object[] values = new Object[types.length];
			for (int i = 0; i < types.length; i++) {
				values[i] = makeDefaultValue(types[i]);
			}
			if (types.length > 0) {
				PropertySheet sheet = new PropertySheet(types, values);
				JOptionPane.showMessageDialog(this, sheet, "???",
						JOptionPane.QUESTION_MESSAGE);
				values = sheet.getValues();
			}
			try {

				Object result = m.invoke(occupant, values);
				this.repaint();
				if (m.getReturnType() != void.class && result != null) {
					String resultString = result.toString();
					Object resultObject;
					final int MAX_LENGTH = 50;
					final int MAX_HEIGHT = 10;
					if (resultString.length() < MAX_LENGTH)
						resultObject = resultString;
					else {
						int rows = Math.min(MAX_HEIGHT,
								1 + resultString.length() / MAX_LENGTH);
						JTextArea pane = new JTextArea(rows, MAX_LENGTH);
						pane.setText(resultString);
						pane.setLineWrap(true);
						resultObject = new JScrollPane(pane);
					}
					JOptionPane.showMessageDialog(new JFrame(), resultObject,
							"???", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (InvocationTargetException ex) {
				new GUIExceptionHandler().handle(ex.getCause());
			} catch (Exception ex) {
				new GUIExceptionHandler().handle(ex);
			}
		}

		private Object occupant;
		private Method m;
	}

	private class MCItem extends JMenuItem {
		public String getDisplayString(Class retType, String name,
				Class[] paramTypes) {
			StringBuffer b = new StringBuffer();
			b.append("<html>");
			if (retType != null)
				appendTypeName(b, retType.getName());
			b.append(" <font color='blue'>");
			appendTypeName(b, name);
			b.append("</font>( ");
			for (int i = 0; i < paramTypes.length; i++) {
				if (i > 0)
					b.append(", ");
				appendTypeName(b, paramTypes[i].getName());
			}
			b.append(" )</html>");
			return b.toString();
		}

		public void appendTypeName(StringBuffer b, String name) {
			int i = name.lastIndexOf('.');
			if (i >= 0) {
				String prefix = name.substring(0, i + 1);
				if (!prefix.equals("java.lang")) {
					b.append("<font color='gray'>");
					b.append(prefix);
					b.append("</font>");
				}
				b.append(name.substring(i + 1));
			} else
				b.append(name);
		}

		public Object makeDefaultValue(Class type) {
			if (type == int.class)
				return new Integer(0);
			else if (type == boolean.class)
				return Boolean.FALSE;
			else if (type == double.class)
				return new Double(0);
			else if (type == String.class)
				return "";
			else if (type == Color.class)
				return Color.BLACK;
			/*
			 * else if (type == Integer.class) return new Integer(0); else if
			 * (type == Boolean.class) return Boolean.FALSE; else if (type ==
			 * Double.class) return new Double(0);
			 */
			else {
				try {
					return type.newInstance();
				} catch (Exception ex) {
					return null;
				}
			}
		}
	}
}

class ColorEditor extends PropertyEditorSupport {
	public ColorEditor() {
		combo = new JComboBox(colorIcons);
	}

	public Object getValue() {
		ColorIcon value = (ColorIcon) combo.getSelectedItem();
		return value.getColor();
	}

	public boolean supportsCustomEditor() {
		return true;
	}

	public Component getCustomEditor() {
		combo.setSelectedItem(0);
		return combo;
	}

	private interface ColorIcon extends Icon {
		Color getColor();

		int WIDTH = 120;
		int HEIGHT = 20;
	}

	private static class SolidColorIcon implements ColorIcon {
		private Color color;

		public Color getColor() {
			return color;
		}

		public SolidColorIcon(Color c) {
			color = c;
		}

		public int getIconWidth() {
			return WIDTH;
		}

		public int getIconHeight() {
			return HEIGHT;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Rectangle r = new Rectangle(x, y, WIDTH - 1, HEIGHT - 1);
			Graphics2D g2 = (Graphics2D) g;
			Color oldColor = g2.getColor();
			g2.setColor(color);
			g2.fill(r);
			g2.setColor(Color.BLACK);
			g2.draw(r);
			g2.setColor(oldColor);
		}
	}

	private static class RandomColorIcon implements ColorIcon {
		public Color getColor() {
			return new Color((int) (Math.random() * 256 * 256 * 256));
		}

		public int getIconWidth() {
			return WIDTH;
		}

		public int getIconHeight() {
			return HEIGHT;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Rectangle r = new Rectangle(x, y, WIDTH - 1, HEIGHT - 1);
			Graphics2D g2 = (Graphics2D) g;
			Color oldColor = g2.getColor();
			Rectangle r1 = new Rectangle(x, y, WIDTH / 4, HEIGHT - 1);
			for (int i = 0; i < 4; i++) {
				g2.setColor(getColor());
				g2.fill(r1);
				r1.translate(WIDTH / 4, 0);
			}
			g2.setColor(Color.BLACK);
			g2.draw(r);
			g2.setColor(oldColor);
		}
	}

	private JComboBox combo;
	private static Color[] colorValues = { Color.BLACK, Color.BLUE, Color.CYAN,
			Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
			Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE,
			Color.YELLOW };
	private static ColorIcon[] colorIcons;
	static {
		colorIcons = new ColorIcon[colorValues.length + 1];
		colorIcons[0] = new RandomColorIcon();
		for (int i = 0; i < colorValues.length; i++)
			colorIcons[i + 1] = new SolidColorIcon(colorValues[i]);
	}
}

class PropertySheet extends JPanel {
	/**
	 * Constructs a property sheet that shows the editable properties of a given
	 * object.
	 * 
	 * @param object
	 *            the object whose properties are being edited
	 */
	public PropertySheet(Class[] types, Object[] values) {
		this.values = values;
		editors = new PropertyEditor[types.length];
		setLayout(new FormLayout());
		for (int i = 0; i < values.length; i++) {
			JLabel label = new JLabel(types[i].getName());
			add(label);

			editors[i] = getEditor(types[i]);
			if (editors[i] != null) {
				editors[i].setValue(values[i]);
				add(getEditorComponent(editors[i]));
			} else
				add(new JLabel("?"));
		}
	}

	/**
	 * Gets the property editor for a given property, and wires it so that it
	 * updates the given object.
	 * 
	 * @param bean
	 *            the object whose properties are being edited
	 * @param descriptor
	 *            the descriptor of the property to be edited
	 * @return a property editor that edits the property with the given
	 *         descriptor and updates the given object
	 */
	public PropertyEditor getEditor(Class type) {
		PropertyEditor editor;
		editor = defaultEditors.get(type);
		if (editor != null)
			return editor;
		editor = PropertyEditorManager.findEditor(type);
		return editor;
	}

	/**
	 * Wraps a property editor into a component.
	 * 
	 * @param editor
	 *            the editor to wrap
	 * @return a button (if there is a custom editor), combo box (if the editor
	 *         has tags), or text field (otherwise)
	 */
	public Component getEditorComponent(final PropertyEditor editor) {
		String[] tags = editor.getTags();
		String text = editor.getAsText();
		if (editor.supportsCustomEditor()) {
			return editor.getCustomEditor();
		} else if (tags != null) {
			// make a combo box that shows all tags
			final JComboBox comboBox = new JComboBox(tags);
			comboBox.setSelectedItem(text);
			comboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED)
						editor.setAsText((String) comboBox.getSelectedItem());
				}
			});
			return comboBox;
		} else {
			final JTextField textField = new JTextField(text, 10);
			textField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					try {
						editor.setAsText(textField.getText());
					} catch (IllegalArgumentException exception) {
					}
				}

				public void removeUpdate(DocumentEvent e) {
					try {
						editor.setAsText(textField.getText());
					} catch (IllegalArgumentException exception) {
					}
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});
			return textField;
		}
	}

	public Object[] getValues() {
		for (int i = 0; i < editors.length; i++)
			if (editors[i] != null)
				values[i] = editors[i].getValue();
		return values;
	}

	private PropertyEditor[] editors;
	private Object[] values;

	private static Map<Class, PropertyEditor> defaultEditors;

	// workaround for Web Start bug
	public static class StringEditor extends PropertyEditorSupport {
		public String getAsText() {
			return (String) getValue();
		}

		public void setAsText(String s) {
			setValue(s);
		}
	}

	static {
		defaultEditors = new HashMap<Class, PropertyEditor>();
		defaultEditors.put(String.class, new StringEditor());
		// defaultEditors.put(Integer.class, new NumberEditor());
		// defaultEditors.put(Double.class, new StringEditor());
		// defaultEditors.put(Short.class, new StringEditor());
		// defaultEditors.put(Location.class, new LocationEditor());
		defaultEditors.put(Color.class, new ColorEditor());
	}
}

class GUIExceptionHandler {
	public void handle(Throwable e) {
		e.printStackTrace();

		JTextArea area = new JTextArea(10, 40);
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		area.setText(writer.toString());
		area.setCaretPosition(0);
		String copyOption = "Copy to Clipboard";
		JOptionPane pane = new JOptionPane(new JScrollPane(area),
				JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION, null,
				new String[] { copyOption, "Cancel" });
		pane.createDialog(new JFrame(), e.toString()).setVisible(true);
		if (copyOption.equals(pane.getValue())) {
			area.setSelectionStart(0);
			area.setSelectionEnd(area.getText().length());
			area.copy(); // copy to clipboard
		}
	}
}

class FormLayout implements LayoutManager {
	public Dimension preferredLayoutSize(Container parent) {
		Component[] components = parent.getComponents();
		left = 0;
		right = 0;
		height = 0;
		for (int i = 0; i < components.length; i += 2) {
			Component cleft = components[i];
			Component cright = components[i + 1];

			Dimension dleft = cleft.getPreferredSize();
			Dimension dright = cright.getPreferredSize();
			left = Math.max(left, dleft.width);
			right = Math.max(right, dright.width);
			height = height + Math.max(dleft.height, dright.height);
		}
		return new Dimension(left + GAP + right, height);
	}

	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	public void layoutContainer(Container parent) {
		preferredLayoutSize(parent); // sets left, right

		Component[] components = parent.getComponents();

		Insets insets = parent.getInsets();
		int xcenter = insets.left + left;
		int y = insets.top;

		for (int i = 0; i < components.length; i += 2) {
			Component cleft = components[i];
			Component cright = components[i + 1];

			Dimension dleft = cleft.getPreferredSize();
			Dimension dright = cright.getPreferredSize();

			int height = Math.max(dleft.height, dright.height);

			cleft.setBounds(xcenter - dleft.width, y + (height - dleft.height)
					/ 2, dleft.width, dleft.height);

			cright.setBounds(xcenter + GAP, y + (height - dright.height) / 2,
					dright.width, dright.height);
			y += height;
		}
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	private int left;
	private int right;
	private int height;
	private static final int GAP = 6;
}