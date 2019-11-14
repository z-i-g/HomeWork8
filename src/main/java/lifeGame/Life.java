package lifeGame;

/**
 * Однопоточная реализация игры "Жизнь". Начальная конфигурация считывается с файла.
 * Конечная также записывается в файл после прохождения переданного количества итераций.
 *
 * @version 1.0
 * @autor Айрат Загидуллин
 */
public class Life {

    private MyFileRW myFileRW;
    private int[][] world;
    private int[][] tempWorld;
    private final int[] stepsX = {1, 1, 0, -1, -1, -1, 0, 1};
    private final int[] stepsY = {0, 1, 1, 1, 0, -1, -1, -1};
    private final int width;
    private final int height;
    private int generation;

    /**
     * Конструктор
     *
     * @param args - args[0] имя входного файла, args[1] имя выходного файла, args[2] количество "поколений"
     */
    public Life(String[] args) {
        myFileRW = new MyFileRW(args[0], args[1]);
        generation = Integer.parseInt(args[2]);
        myFileRW.readFile();
        width = myFileRW.getWidth();
        height = myFileRW.getHeight();
    }


    private void getCell(int x, int y, int countLife) {
        int tempX = x;
        int tempY = y;
        for (int i = 0; i < stepsX.length; i++) {
            x = getStepX(i, tempX);
            y = getStepY(i, tempY);

            if (world[x][y] == 1) {
                countLife++;
            }
        }

        if (world[tempX][tempY] == 1) {
            if ((countLife >= 2) && (countLife <= 3)) {
                tempWorld[tempX][tempY] = 1;
            } else {
                tempWorld[tempX][tempY] = 0;
            }
        } else if ((world[tempX][tempY] == 0) && (countLife == 3)) {
            tempWorld[tempX][tempY] = 1;
        }
        countLife = 0;
    }

    private int getStepX(int i, int x) {
        x += stepsX[i];
        if (x < 0) {
            return (x % height) + height;
        }
        return x % height;
    }

    private int getStepY(int i, int y) {
        y += stepsY[i];
        if (y < 0) {
            return (y % width) + width;
        }
        return y % width;
    }

    /**
     * Старт игры
     */
    public void startGame() throws InterruptedException {
        world = myFileRW.getWorld();
        tempWorld = new int[height][width];

        while (generation != 0) {
            int counLife = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    getCell(i, j, counLife);
                }
            }
            generation--;
            world = tempWorld;
            tempWorld = new int[height][width];
        }

        myFileRW.setWorld(world);
        myFileRW.writeFile();
    }
}

