import javax.swing.*;
import java.awt.*;

public class TicTacToeButton extends JButton {
    private final int row;
    private final int column;
    public TicTacToeButton(int row, int column) {
        super(" ");
        this.row = row;
        this.column = column;
        setFont(new Font("Arial", Font.BOLD, 12));
        setBackground(Color.cyan);
    }
    public int getRow() {return row;}
    public int getColumn() {return column;}
}
