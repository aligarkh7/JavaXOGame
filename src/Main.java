
import java.util.Random;
import java.util.Scanner;

public class Main {
    static char[][] map;
    static final int SIZE = 3;
    static final char DOT_PLAYER = 'X';
    static final char DOT_AI = '0';
    static final char DOT_EMPTY = '-';
    static final Scanner sc = new Scanner(System.in);
    static final Random random = new Random();

    public static void main(String[] args) {
        prepareMap();
        printMap();

        while (!isDraw()) {
            playerTurn();
            printMap();
            if (isWin(DOT_PLAYER)) {
                System.out.println("*** Выиграли вы ***");
                break;
            } else if (isDraw()) {
                System.out.println("=== Ничья ===");
                break;
            }

            aiTurn();
            printMap();
            if (isWin(DOT_AI)) {
                System.out.println("*** Выиграл AI ***");
                break;
            } else if (isDraw()) {
                System.out.println("=== Ничья ===");
                break;
            }
        }

    }


    public static boolean isWin(char dot) {
        int indexXY = 0;
        int indexYX = 0;
        int indexX = 0;
        int indexY = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == dot) {
                    indexX++;
                    if (indexX == SIZE) {
                        return true;
                    }
                }

                if (map[j][i] == dot) {
                    indexY++;
                    if (indexY == SIZE) {
                        return true;
                    }
                }

                if (i == j) {
                    if (map[i][j] == dot) {
                        indexXY++;
                        if (indexXY == SIZE) {
                            return true;
                        }
                    }
                }

                if ((i + j) == (SIZE - 1)) {
                    if (map[i][j] == dot) {
                        indexYX++;
                        if (indexYX == SIZE) {
                            return true;
                        }
                    }
                }
            }
            indexX = 0;
            indexY = 0;
        }
        return false;
    }

    public static boolean isDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void aiTurn() {
        int index = checkAi();
        if (index == 0) {
            int x;
            int y;

            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isValidation(x, y));

            map[x][y] = DOT_AI;
            System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));
        } else {
            checkTurnAi(index);
        }


    }

    public static void checkTurnAi(int index) {
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (DOT_PLAYER == map[i][j]) {
                    sum += 4;
                } else if (DOT_AI == map[i][j]) {
                    sum += 2;
                } else if (DOT_EMPTY == map[i][j]) {
                    sum += 1;
                }
            }
            if (sum == index) {
                int x = i;
                int y;

                do {
                    y = random.nextInt(SIZE);
                } while (!isValidation(x, y));

                map[x][y] = DOT_AI;
                System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));
                return;
            } else {
                sum = 0;
            }


            for (int j = 0; j < SIZE; j++) {
                if (DOT_PLAYER == map[j][i]) {
                    sum += 4;
                } else if (DOT_AI == map[j][i]) {
                    sum += 2;
                } else if (DOT_EMPTY == map[j][i]) {
                    sum += 1;
                }
            }
            if (sum == index) {
                int x;
                int y = i;

                do {
                    x = random.nextInt(SIZE);
                } while (!isValidation(x, y));

                map[x][y] = DOT_AI;
                System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));
                return;
            } else {
                sum = 0;
            }
        }

        sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == j) {
                    if (DOT_PLAYER == map[i][j]) {
                        sum += 4;
                    } else if (DOT_AI == map[i][j]) {
                        sum += 2;
                    } else if (DOT_EMPTY == map[i][j]) {
                        sum += 1;
                    }
                }
            }
            if (sum == index) {
                int x;
                int y;

                do {
                    x = random.nextInt(SIZE);
                    y = x;
                } while (!isValidation(x, y));

                map[x][y] = DOT_AI;
                System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));
                return;
            }
        }

        sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i + j == SIZE - 1) {
                    if (DOT_PLAYER == map[i][j]) {
                        sum += 4;
                    } else if (DOT_AI == map[i][j]) {
                        sum += 2;
                    } else if (DOT_EMPTY == map[i][j]) {
                        sum += 1;
                    }
                }
            }
            if (sum == index) {
                int x;
                int y;

                do {
                    do {
                        x = random.nextInt(SIZE);
                        y = random.nextInt(SIZE);
                    } while (!(x + y == SIZE - 1));
                } while (!isValidation(x, y));

                map[x][y] = DOT_AI;
                System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));
                return;
            }
        }


        int x;
        int y;

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isValidation(x, y));

        map[x][y] = DOT_AI;
        System.out.println("\nХод AI: " + (x + 1) + ", " + (y + 1));


    }

    public static int checkAi() {
        int sum[] = new int[8];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (DOT_PLAYER == map[i][j]) {
                    sum[i] += 4;
                } else if (DOT_AI == map[i][j]) {
                    sum[i] += 2;
                } else if (DOT_EMPTY == map[i][j]) {
                    sum[i] += 1;
                }

                if (DOT_PLAYER == map[j][i]) {
                    sum[i + SIZE] += 4;
                } else if (DOT_AI == map[j][i]) {
                    sum[i + SIZE] += 2;
                } else if (DOT_EMPTY == map[j][i]) {
                    sum[i + SIZE] += 1;
                }

                if (i == j) {
                    if (DOT_PLAYER == map[i][j]) {
                        sum[6] += 4;
                    } else if (DOT_AI == map[i][j]) {
                        sum[6] += 2;
                    } else if (DOT_EMPTY == map[i][j]) {
                        sum[6] += 1;
                    }
                }

                if (i + j == SIZE - 1) {
                    if (DOT_PLAYER == map[i][j]) {
                        sum[7] += 4;
                    } else if (DOT_AI == map[i][j]) {
                        sum[7] += 2;
                    } else if (DOT_EMPTY == map[i][j]) {
                        sum[7] += 1;
                    }
                }

            }
        }

        int index = 0;
        int[] lvl = new int[5];
        lvl[0] = 5;
        lvl[1] = 9;
        lvl[2] = 4;
        lvl[3] = 3;
        lvl[4] = 6;

        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < sum.length; j++) {
                if (lvl[i] == sum[j]) {
                    index = sum[j];
                    break;
                }
            }
            if (index == lvl[i]) {
                break;
            }
        }

        return index;

    }


    public static void playerTurn() {
        try {
            System.out.print("x: ");
            int x = Integer.parseInt(sc.nextLine()) - 1;
            System.out.print("y: ");
            int y = Integer.parseInt(sc.nextLine()) - 1;

            if (!isValidation(x, y)) {
                System.out.println("Ошибка\nПовтори снова:");
                playerTurn();
                return;
            }
            map[x][y] = DOT_PLAYER;
            System.out.println("\nВаш ход: " + (x + 1) + ", " + (y + 1));

        } catch (Exception e) {
            System.out.println(e + "\nПовтори снова:");
            playerTurn();
        }
    }

    public static boolean dotIsEmpty(int x, int y) {
        if (map[x][y] == DOT_EMPTY) {
            return true;
        }
        return false;
    }

    public static boolean isValidation(int x, int y) {
        if (x < SIZE && x >= 0 && y < SIZE && y >= 0) {
            return dotIsEmpty(x, y);
        }
        return false;
    }


    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            for (int j = 0; j <= SIZE; j++) {
                System.out.print((i == 0) ? j + " " : (j == 0) ? i + " " : map[i - 1][j - 1] + " ");
            }
            System.out.println();
        }
    }

    public static void prepareMap() {
        map = new char[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }
}
