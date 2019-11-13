package multiThreadLifeGame;

import com.sun.deploy.util.ArrayUtil;
import lifeGame.Life;
import lifeGame.MyFileRW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class ThreadLife {
    static List<ReentrantLock> locks = new ArrayList<ReentrantLock>() {{
        add(new ReentrantLock());
        add(new ReentrantLock());
        add(new ReentrantLock());
        add(new ReentrantLock());
        add(new ReentrantLock());
    }};

    List<Thread> threadList = new ArrayList<Thread>() {{
        add(new Thread());
        add(new Thread());
        add(new Thread());
        add(new Thread());
    }};

    private MyFileRW myFileRW;
    private int countLife;
    private String inputFileName;
    private String outputFileName;
    private int[][] world;
    private int[][] tempWorld;
    private int[] stepsX = {1, 1, 0, -1, -1, -1, 0, 1};
    private int[] stepsY = {0, 1, 1, 1, 0, -1, -1, -1};
    private int generation;

    public ThreadLife(String[] args) {
        inputFileName = args[0];
        outputFileName = args[1];
        generation = Integer.parseInt(args[2]);
    }


    public void getCell(int x, int y) {
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

    public void startGame() throws InterruptedException {
        myFileRW = new MyFileRW();
        myFileRW.readFile(inputFileName);
        world = myFileRW.getWorld();
        tempWorld = new int[myFileRW.getWidth()][myFileRW.getHeight()];

        int countProc = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(4);


        Thread thread;
        Thread thread1;

        while (generation != 0) {

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < world.length / 2; i++) {
                        for (int j = 0; j < myFileRW.getHeight(); j++) {
                            getCell(i, j);
                        }
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            });
            thread.start();
            thread.join();

            thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = world.length / 2; i < world.length; i++) {
                        for (int j = 0; j < myFileRW.getHeight(); j++) {
                            getCell(i, j);
                        }
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            });
            thread1.start();
            thread1.join();

            generation--;
            world = tempWorld;
            tempWorld = new int[myFileRW.getWidth()][myFileRW.getHeight()];

        }

        myFileRW.setWorld(world);
        myFileRW.writeFile(outputFileName);

    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ThreadLife life = new ThreadLife(args);
        life.startGame();
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);


    }
}
