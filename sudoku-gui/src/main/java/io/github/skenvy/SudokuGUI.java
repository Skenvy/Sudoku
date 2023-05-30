package io.github.skenvy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SudokuGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1269307511176122701L;
	JPanel mainPanel = new JPanel();
	JPanel top = new JPanel();
	JPanel board = new JPanel();
	JPanel bottom = new JPanel();
	JPanel right = new JPanel();
	JPanel left = new JPanel();
	JPanel left1 = new JPanel();
	JLabel tit1 = new JLabel("wo c");
	JLabel tit2 = new JLabel("w c");
	JButton[][] selections;
	JPanel left2 = new JPanel();
	JScrollPane digestView;
	JTextArea digest = new JTextArea("digest", 40, 20);
	JPanel[][] subBoards;
	JButton solve = new JButton("Solve!");
	JButton new1 = new JButton("New!");
	JButton load = new JButton("Load!");
	JButton candidate = new JButton("Show Candidates!");
	JTextField address = new JTextField("File Name (.extension)", 12);
	JTextField[][][][] grid;
	Sudoku sudokuBoard;
	
	
	SudokuGUI(final int size){
		setTitle("Sudoku Solver");
		mainPanel.setLayout(new BorderLayout());
		top.setLayout(new FlowLayout());
		left.setLayout(new BoxLayout(left, BoxLayout.LINE_AXIS));
		left1.setLayout(new BoxLayout(left1, BoxLayout.PAGE_AXIS));
		left2.setLayout(new BoxLayout(left2, BoxLayout.PAGE_AXIS));
		left1.add(tit1); left2.add(tit2);
		digestView = new JScrollPane(digest);
		right.add(digestView);
		selections = new JButton[2][size*size];
		for(int k = 0; k < size*size; k++){
			selections[0][k] = new JButton(""+(k+1));
			selections[1][k] = new JButton(""+(k+1));
			left1.add(selections[0][k]);
			left2.add(selections[1][k]);
		}
		grid = new JTextField[size][size][size][size];
		sudokuBoard = new Sudoku(size);
		board.setLayout(new GridLayout(size,size));
		subBoards = new JPanel[size][size];
		for(int j0 = 0; j0 < size; j0++){
			for(int j1 = 0; j1 < size; j1++){
				subBoards[j0][j1] = new JPanel(new GridLayout(size,size));
			}
		}
		for(int j0 = 0; j0 < size; j0++){
			for(int j1 = 0; j1 < size; j1++){
				for(int k0 = 0; k0 < size; k0++){
					for(int k1 = 0; k1 < size; k1++){
						grid[j0][j1][k0][k1] = new JTextField("0",1);
						/*grid[j0][j1][k0][k1].addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								for(int j0 = 0; j0 < size; j0++){
									for(int j1 = 0; j1 < size; j1++){
										for(int k0 = 0; k0 < size; k0++){
											for(int k1 = 0; k1 < size; k1++){
												if(grid[j0][j1][k0][k1].getBackground() == Color.white){
													if(Integer.parseInt(grid[j0][j1][k0][k1].getText()) != 0){
														grid[j0][j1][k0][k1].setBackground(Color.cyan);
													}
												}
											}
										}
									}
								}
							}
						});*/
						subBoards[j0][j1].setBackground(Color.black);
						subBoards[j0][j1].add(grid[j0][j1][k0][k1]);
					}
				}
				subBoards[j0][j1].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
				board.add(subBoards[j0][j1]);
			}
		}
		board.setBackground(Color.RED);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		double windowWidth = size*size*30+400;
		double windowHeight = size*size*30+200;
		double localWidth = (width-windowWidth)/2;
		double localHeight = (height-windowHeight)/2;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)windowWidth,(int)windowHeight);
		setLocation((int)localWidth, (int)localHeight);
		setVisible(true);
		bottom.add(candidate);
		top.add(solve); top.add(new1); top.add(load); top.add(address);
		left.add(left1); left.add(left2);
		mainPanel.add(top, BorderLayout.NORTH);
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(left, BorderLayout.WEST);
		mainPanel.add(right, BorderLayout.EAST);
		candidate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				for(int j0 = 0; j0 < size; j0++){
					for(int j1 = 0; j1 < size; j1++){
						for(int k0 = 0; k0 < size; k0++){
							for(int k1 = 0; k1 < size; k1++){
								sudokuBoard.board[j0][j1][k0][k1] = Integer.parseInt(grid[j0][j1][k0][k1].getText());
								if(Integer.parseInt(grid[j0][j1][k0][k1].getText()) > 0 && Integer.parseInt(grid[j0][j1][k0][k1].getText()) <= size*size){
									grid[j0][j1][k0][k1].setBackground(Color.GREEN);
								} else {
									if(grid[j0][j1][k0][k1].getBackground() == Color.GREEN){
										grid[j0][j1][k0][k1].setBackground(Color.white);
									}
								}
							}
						}
					}
				}
				sudokuBoard.initialiseBoard();
				if(!sudokuBoard.errorExists()){
					sudokuBoard.solveBoard();
					for(int j0 = 0; j0 < size; j0++){
						for(int j1 = 0; j1 < size; j1++){
							for(int k0 = 0; k0 < size; k0++){
								for(int k1 = 0; k1 < size; k1++){
									grid[j0][j1][k0][k1].setText(""+sudokuBoard.board[j0][j1][k0][k1]);
									if(grid[j0][j1][k0][k1].getBackground() != Color.GREEN){
										if(sudokuBoard.board[j0][j1][k0][k1] != 0){
											grid[j0][j1][k0][k1].setBackground(Color.YELLOW);
										}
									}
									if(!sudokuBoard.cellFilled[j0][j1][k0][k1]){
										
									}
								}
							}
						}
					}
				} else {
					for(int j0 = 0; j0 < size; j0++){
						for(int j1 = 0; j1 < size; j1++){
							for(int k0 = 0; k0 < size; k0++){
								for(int k1 = 0; k1 < size; k1++){
									if(sudokuBoard.errorInSquare[j0][j1][k0][k1]){
										grid[j0][j1][k0][k1].setBackground(Color.RED);
									}
								}
							}
						}
					}
				}
			}
		});
		new1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int j0 = 0; j0 < size; j0++){
					for(int j1 = 0; j1 < size; j1++){
						for(int k0 = 0; k0 < size; k0++){
							for(int k1 = 0; k1 < size; k1++){
								grid[j0][j1][k0][k1].setText("0");
								grid[j0][j1][k0][k1].setBackground(Color.white);
							}
						}
					}
				}
			}
		});
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){/*
				for(int j0 = 0; j0 < size; j0++){
					for(int j1 = 0; j1 < size; j1++){
						for(int k0 = 0; k0 < size; k0++){
							for(int k1 = 0; k1 < size; k1++){
								grid[j0][j1][k0][k1].setText("0");
								grid[j0][j1][k0][k1].setBackground(Color.white);
							}
						}
					}
				}
				String input = System.getProperty("user.dir")+"/"+address.getText();
				sudokuImageExtract a = new sudokuImageExtract();
				try {
					a.theImage = ImageIO.read(new File(input));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				sudokuBoard.board = a.solveWebdoku();
				for(int j0 = 0; j0 < size; j0++){
					for(int j1 = 0; j1 < size; j1++){
						for(int k0 = 0; k0 < size; k0++){
							for(int k1 = 0; k1 < size; k1++){
								if(sudokuBoard.board[j0][j1][k0][k1] > 0 && sudokuBoard.board[j0][j1][k0][k1] <= size*size){
									grid[j0][j1][k0][k1].setBackground(Color.CYAN);
									grid[j0][j1][k0][k1].setText(sudokuBoard.board[j0][j1][k0][k1]+"");
								} 
							}
						}
					}
				}
			*/}
		});
		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int j0 = 0; j0 < size; j0++){
					for(int j1 = 0; j1 < size; j1++){
						for(int k0 = 0; k0 < size; k0++){
							for(int k1 = 0; k1 < size; k1++){
								sudokuBoard.board[j0][j1][k0][k1] = Integer.parseInt(grid[j0][j1][k0][k1].getText());
								if(Integer.parseInt(grid[j0][j1][k0][k1].getText()) > 0 && Integer.parseInt(grid[j0][j1][k0][k1].getText()) <= size*size){
									grid[j0][j1][k0][k1].setBackground(Color.GREEN);
								} else {
									if(grid[j0][j1][k0][k1].getBackground() == Color.GREEN){
										grid[j0][j1][k0][k1].setBackground(Color.white);
									}
								}
							}
						}
					}
				}
				sudokuBoard.initialiseBoard();
				if(!sudokuBoard.errorExists()){
					sudokuBoard.solveBoard();
					for(int j0 = 0; j0 < size; j0++){
						for(int j1 = 0; j1 < size; j1++){
							for(int k0 = 0; k0 < size; k0++){
								for(int k1 = 0; k1 < size; k1++){
									grid[j0][j1][k0][k1].setText(""+sudokuBoard.board[j0][j1][k0][k1]);
									if(grid[j0][j1][k0][k1].getBackground() != Color.GREEN){
										if(sudokuBoard.board[j0][j1][k0][k1] != 0){
											grid[j0][j1][k0][k1].setBackground(Color.YELLOW);
										}
									}
								}
							}
						}
					}
				} else {
					for(int j0 = 0; j0 < size; j0++){
						for(int j1 = 0; j1 < size; j1++){
							for(int k0 = 0; k0 < size; k0++){
								for(int k1 = 0; k1 < size; k1++){
									if(sudokuBoard.errorInSquare[j0][j1][k0][k1]){
										grid[j0][j1][k0][k1].setBackground(Color.RED);
									}
								}
							}
						}
					}
				}
			}
		});
		add(mainPanel);
	}

	public static void main(String[] args) {
		SudokuGUI a = new SudokuGUI(3);
	}
}
