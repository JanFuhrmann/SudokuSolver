public class SudokuSolver {
    private static final String[][] num = new String[9][9];
    private static final String[][][] tips = new String[9][9][9];

    private final static String SUDOKU =
            "4,1,0,0,6,5,0,0,7," +
                    "0,0,6,0,0,7,4,8,0," +
                    "2,0,7,4,9,0,0,0,6," +
                    "0,6,0,0,7,0,1,0,0," +
                    "3,0,1,5,0,0,0,7,2," +
                    "0,9,0,0,4,2,3,0,8," +
                    "1,0,8,6,0,0,0,2,9," +
                    "0,2,0,0,1,8,6,4,0," +
                    "6,0,0,3,0,0,0,1,0";

    public static void main(String[] args) {
        String[] numbers = SUDOKU.split(",");
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                num[i][j] = numbers[counter];
                counter++;
                // Fill tips
                for (int k = 0; k < 9; k++) {
                    if (num[i][j].equals("0")) {
                        tips[i][j][k] = (k + 1) + "";
                    } else {
                        tips[i][j][k] = num[i][j];
                    }
                }
            }
        }

        for (int b = 0; b < 10; b++) {
            start();
            for (int i = 0; i < 9; i++) {
                checkNumbers(i, b+1);
            }
        }
        System.err.println("Solution:");
        printResult();
    }

    /**
     * Start the Sudoku Solver
     */
    private static void start() {
        // Create rules
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (num[i][j].equals("0")) {
                    firstCheckOfField(i, j);
                }
            }
        }
    }

    /**
     * fill out the tips array
     *
     * @param i
     * @param j
     */
    private static void firstCheckOfField(int i, int j) {
        // horizontal row
        for (int x = 0; x < 9; x++) {
            int value = Integer.parseInt(num[i][x]);
            if (value != 0) {
                tips[i][j][value - 1] = "X";
            }
        }
        // vertical row
        for (int x = 0; x < 9; x++) {
            int value = Integer.parseInt(num[x][j]);
            if (value != 0) {
                tips[i][j][value - 1] = "X";
            }
        }

        // Square
        int row = i / 3;
        int column = j / 3;
        for (int x = row * 3; x < row * 3 + 3; x++) {
            for (int y = column * 3; y < column * 3 + 3; y++) {
                int value = Integer.parseInt(num[x][y]);
                if (value != 0) {
                    tips[i][j][value - 1] = "X";
                }
            }
        }
    }

    /**
     * Solve the Sudoku and print the steps
     *
     * @param i
     * @param iteration
     */
    private static void checkNumbers(int i, int iteration) {
        // Square
        int ze = i / 3;
        int sp = i % 3;
        int goalZ = 0, goalS = 0;
        for (int w = 1; w < 10; w++) {
            int counter = 0;
            for (int x = ze * 3; x < ze * 3 + 3; x++) {
                for (int y = sp * 3; y < sp * 3 + 3; y++) {
                    if (tips[x][y][w - 1].equals(w + "")) {
                        counter++;
                        goalZ = x;
                        goalS = y;
                    }
                }
            }
            if (counter == 1 && num[goalZ][goalS].equals("0")) {
                for (int z = 0; z < 9; z++) {
                    tips[goalZ][goalS][z] = w + "";
                }
                num[goalZ][goalS] = w + "";
                System.err.println(iteration + ". Iteration: Number " + w + " is " + counter + " times in Square " + ze + "-" + sp + " - " + (goalZ + 1) + "/" + (goalS + 1));
            }
        }

        // horizontal row
        for (int x = 1; x < 10; x++) {
            int counter = 0, spalte = 0;
            for (int y = 0; y < 9; y++) {
                if (tips[i][y][x - 1].equals(x + "")) {
                    counter++;
                    spalte = y;
                }
            }
            if (counter == 1 && num[i][spalte].equals("0")) {
                for (int z = 0; z < 9; z++) {
                    tips[i][spalte][z] = x + "";
                }
                num[i][spalte] = x + "";
                System.err.println(iteration + ". Iteration: NUmber " + x + " is just " + counter + " times in row " + (i + 1) + " - " + (i + 1) + "/" + (spalte + 1));
            }
        }

        // vertical row
        for (int x = 1; x < 10; x++) {
            int counter = 0, zeile = 0;
            for (int y = 0; y < 9; y++) {
                if (tips[y][i][x - 1].equals(x + "")) {
                    counter++;
                    zeile = y;
                }
            }
            if (counter == 1 && num[zeile][i].equals("0")) {
                for (int z = 0; z < 9; z++) {
                    tips[zeile][i][z] = x + "";
                }
                num[zeile][i] = x + "";
                System.err.println(iteration + ". Iteration: Number " + x + " is just " + counter + " times in column " + (i + 1) + " - " + (zeile + 1) + "/" + (i + 1));
            }
        }
    }

    /**
     * Print the solved Sudoku on the console
     */
    private static void printResult() {
        for (int x = 0; x < 9; x++) {
            System.out.print("| ");
            for (int y = 0; y < 9; y++) {
                for (int z = 0; z < 8; z++) {
                    if (tips[x][y][0].equals(tips[x][y][1]) && !tips[x][y][0].equals("X")) {
                        z = 8;
                    } else if (!tips[x][y][z].equals("X")) {
                        System.out.print(tips[x][y][z] + ",");
                    }
                }
                System.out.print(tips[x][y][8] + " | ");
            }
            System.out.println();
        }
    }
}
