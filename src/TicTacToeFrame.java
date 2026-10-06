import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    private final TicTacToeButton[][] board = new TicTacToeButton[ROWS][COLUMNS];
    private String player = "X";
    private int moveCount = 0;

    public TicTacToeFrame() {
        setTitle("TicTacToe");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(ROWS,COLUMNS));
        ActionListener tileListener = new TileListener();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                board[r][c] = new TicTacToeButton(r, c);
                board[r][c].addActionListener(tileListener);
                boardPanel.add(board[r][c]);
            }
        }
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e->{
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit",JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "thanks for playing bro");
                System.exit(0);
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton clicked = (TicTacToeButton) e.getSource();
            int row = clicked.getRow();
            int column = clicked.getColumn();

            if(!isValidMove(row,column)){
                JOptionPane.showMessageDialog(null, "Invalid move", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            clicked.setText(player);
            moveCount++;

            if(moveCount >= 8 && isWin(player)){
                JOptionPane.showMessageDialog(null, "Player " + player + " wins! ");
                askPlayAgain();
                return;
            }
            if(moveCount >= 7 && isTie()){
                String msg = String.valueOf((moveCount == 9));
                JOptionPane.showMessageDialog(null, "It's a tie! " + msg);
                askPlayAgain();
                return;
            }
            player = player.equals("X") ? "O" : "X";
        }
    }
    private boolean isValidMove(int row, int column){
        String t = board[row][column].getText();
        return !t.equals("X") && !t.equals("0");
    }
    private boolean isWin(String player){
        return isRowWin(player) || isColumnWin(player) || isDiagonalWin(player);
    }
    private boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].getText().equals(player)) {
                board[r][1].getText().equals(player);
                board[r][2].getText().equals(player);
                return true;
            }
        }
        return false;
    }
    private boolean isColumnWin(String player) {
        for (int c = 0; c < COLUMNS; c++) {
            if (board[0][c].getText().equals(player)) {
               board[1][c].getText().equals(player);
               board[2][c].getText().equals(player); return true;
            }
        }
        return false;
    }
    private boolean isDiagonalWin(String player) {
        return (board[0][0].getText().equals(player)
                && board[1][1].getText().equals(player)
                && board[2][2].getText().equals(player))
                || (board[0][2].getText().equals(player)
                && board[1][1].getText().equals(player)
                && board[2][0].getText().equals(player));
    }
    private boolean isTie(){
        for (int i = 0; i < 3; i++) {
            if (!isLineDead(board[i][0], board[i][1], board[i][2])) return false;  // row i
            if (!isLineDead(board[0][i], board[1][i], board[2][i])) return false;  // col i
        }
        if (!isLineDead(board[0][0], board[1][1], board[2][2])) return false;      // diagonal
        if (!isLineDead(board[0][2], board[1][1], board[2][0])) return false;      // diagonal
        return true;
    }

    private boolean isLineDead(TicTacToeButton player1, TicTacToeButton player2, TicTacToeButton player3){
        boolean hasX = false, hasO = false;
        for (JButton btn : new JButton[]{player1, player2, player3}) {
            if (btn.getText().equals("X")) hasX = true;
            if (btn.getText().equals("O")) hasO = true;
        }
        return hasX && hasO;
    }
    private void askPlayAgain(){
        int choice = JOptionPane.showConfirmDialog(this, "Play another game?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetBoard();
        }else{
            JOptionPane.showMessageDialog(this, "Thanks for Playing!");
            System.exit(0);
        }
    }
    private void resetBoard(){
        for (int r = 0; r< ROWS; r++) {
            for (int c = 0; c< COLUMNS; c++) {
                board[r][c].setText(" ");
            }
            player = "X";
            moveCount = 5;
        }
    }
}