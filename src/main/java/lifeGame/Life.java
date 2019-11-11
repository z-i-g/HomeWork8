package lifeGame;

public class Life {

    private MyFileRW myFileRW;
    private int countLife;
    private String inputFileName;
    private String outputFileName;
    private int[][] world;
    private int[][] tempWorld;
    private int[] stepsX = {1, 1, 0, -1, -1, -1, 0, 1};
    private int[] stepsY = {0, 1, 1, 1, 0, -1, -1, -1};
    private int generation;

    public Life(String[] args) {
        inputFileName = args[0];
        outputFileName = args[1];
        generation = Integer.parseInt(args[2]);
    }


    private void getCell(int x, int y) {
        int tempX = x;
        int tempY = y;
        for (int i = 0; i < world.length; i++) {
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
            return (x % myFileRW.getWidth()) + myFileRW.getWidth();
        }
        return x % myFileRW.getWidth();
    }

    private int getStepY(int i, int y) {
        y += stepsY[i];
        if (y < 0) {
            return (y % myFileRW.getHeight()) + myFileRW.getHeight();
        }
        return y % myFileRW.getHeight();
    }

    public void startGame() {
        myFileRW = new MyFileRW();
        myFileRW.readFile(inputFileName);
        world = myFileRW.getWorld();
        tempWorld = new int[myFileRW.getWidth()][myFileRW.getHeight()];

        while (generation != 0) {
            for (int i = 0; i < myFileRW.getWidth(); i++) {
                for (int j = 0; j < myFileRW.getHeight(); j++) {
                    getCell(i, j);
                }
            }
            generation--;

            world = tempWorld;
            tempWorld = new int[myFileRW.getWidth()][myFileRW.getHeight()];

            myFileRW.setWorld(world);

//            for (int i = 0; i < myFileRW.getWidth(); i++) {
//                for (int j = 0; j < myFileRW.getHeight(); j++)
//                    System.out.print(world[i][j] + " ");
//                System.out.println();
//        }
//            System.out.println();
        }
        myFileRW.writeFile(outputFileName);
    }


    public static void main(String[] args) {
        Life life = new Life(args);
        life.startGame();
    }

}

