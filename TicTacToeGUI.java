import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private char currentPlayer = 'X';
    private JButton[][] buttons = new JButton[3][3];
    private boolean gameOver = false;
    private JLabel statusLabel;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeBoard();
        initializeStatusPanel();

        setVisible(true);
    }

    private void initializeBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                buttons[row][col].setBackground(Color.WHITE);
                boardPanel.add(buttons[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        statusPanel.add(resetButton, BorderLayout.EAST);

        add(statusPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton clickedButton = (JButton) e.getSource();
        if (!clickedButton.getText().equals("")) return;

        clickedButton.setText(String.valueOf(currentPlayer));
        clickedButton.setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);

        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            gameOver = true;
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Tie!");
            gameOver = true;
        } else {
            switchPlayer();
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    private boolean checkRows() {
        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getText().equals("") &&
                buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                buttons[row][0].getText().equals(buttons[row][2].getText())) {
                highlightWinningButtons(buttons[row][0], buttons[row][1], buttons[row][2]);
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int col = 0; col < 3; col++) {
            if (!buttons[0][col].getText().equals("") &&
                buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                buttons[0][col].getText().equals(buttons[2][col].getText())) {
                highlightWinningButtons(buttons[0][col], buttons[1][col], buttons[2][col]);
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) {
            highlightWinningButtons(buttons[0][0], buttons[1][1], buttons[2][2]);
            return true;
        }
        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) {
            highlightWinningButtons(buttons[0][2], buttons[1][1], buttons[2][0]);
            return true;
        }
        return false;
    }

    private void highlightWinningButtons(JButton... winningButtons) {
        for (JButton button : winningButtons) {
            button.setBackground(Color.GREEN);
        }
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetGame() {
        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Player X's Turn");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
