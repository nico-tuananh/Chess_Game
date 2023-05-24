package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import pieces.*;


public class PromotionPopup extends JFrame implements ActionListener {
    int cols = 4;
    int rows = 1;
    int tileSize = 80;
    JButton knight;
    JButton bishop;
    JButton rook;
    JButton queen;
    Board board;
    int row;
    int col;
    boolean isWhite;
    public PromotionPopup(Board board, boolean isWhite, int row, int col){
        this.board = board;
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        knight = new JButton();
        knight.setBounds(0, 0, tileSize, tileSize);
        knight.addActionListener(this);
        bishop = new JButton();
        bishop.setBounds(tileSize, 0, tileSize, tileSize);
        bishop.addActionListener(this);
        rook = new JButton();
        rook.setBounds(2 * tileSize, 0, tileSize, tileSize);
        rook.addActionListener(this);
        queen = new JButton();
        queen.setBounds(2 * tileSize, 0, tileSize, tileSize);
        queen.addActionListener(this);


        this.setSize(4 * tileSize, tileSize);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(knight);
        this.add(bishop);
        this.add(rook);
        this.add(queen);

    }

    public void actionPerformed(ActionEvent choose) {
        if (choose.getSource() == knight) {
            board.pieceList.remove(board.get(row, col));
            board.pieceList.add(new Knight(board, row, col, isWhite));
            System.out.println("Promoted to a knight!");
        }
        else if (choose.getSource() == bishop) {
            board.pieceList.remove(board.get(row, col));
            board.pieceList.add(new Bishop(board, row, col, isWhite));
            System.out.println("Promoted to a bishop!");
        }
        else if (choose.getSource() == rook) {
            board.pieceList.remove(board.get(row, col));
            board.pieceList.add(new Rook(board, row, col, isWhite));
            System.out.println("Promoted to a rook!");
        }
        else if (choose.getSource() == queen) {
            board.pieceList.remove(board.get(row, col));
            board.pieceList.add(new Queen(board, row, col, isWhite));
            System.out.println("Promoted to a queen!");
        }
        this.setVisible(false);
    }
}
