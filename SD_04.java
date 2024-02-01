import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SD_04 {
    private static final int SIZE = 9;
    private static JTextField[][] cells = new JTextField[SIZE][SIZE];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(SIZE, SIZE));

        // Initialize text fields
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField(1);
                frame.add(cells[row][col]);
            }
        }

        // Add a Solve button
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        frame.add(solveButton);

        frame.pack();
        frame.setVisible(true);
    }

    private static void solveSudoku() {
        int[][] grid = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String value = cells[row][col].getText();
                grid[row][col] = value.isEmpty() ? 0 : Integer.parseInt(value);
            }
        }

        if (solve(grid)) {
            // Display the solved grid
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setText(Integer.toString(grid[row][col]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No solution found!");
        }
    }

    private static boolean solve(int[][] grid) {
        int N = grid.length; // Assuming grid is a square (9x9) Sudoku grid
    
        // Find an empty cell (0 represents an empty cell)
        int row = -1, col = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }
    
        // If no empty cell found, the puzzle is solved
        if (row == -1) return true;
    
        // Try placing digits from 1 to 9
        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num; // Place the digit
    
                // Recurse to the next empty cell
                if (solve(grid)) return true;
    
                // Backtrack (undo the placement)
                grid[row][col] = 0;
            }
        }
    
        // No valid placement found, backtrack
        return false;
    }
    
    private static boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        int N = grid.length;
    
        // Check row and column
        for (int i = 0; i < N; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
    
        // Check 3x3 subgrid
        int subgridSize = (int) Math.sqrt(N);
        int startRow = row - row % subgridSize;
        int startCol = col - col % subgridSize;
        for (int i = startRow; i < startRow + subgridSize; i++) {
            for (int j = startCol; j < startCol + subgridSize; j++) {
                if (grid[i][j] == num) {
                    return false;
                }
            }
        }
    
        return true;
    }
    
}
