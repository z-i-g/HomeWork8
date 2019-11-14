package multiThreadLifeGame;

import lifeGame.Life;
import org.junit.jupiter.api.Test;

class ThreadLifeTest {

    private String[] args = {"startConf.txt", "finishConf.txt", "8" };
    private String[] argsThread = {"startConf.txt", "threadFinishConf.txt", "8" };
    Life life = new Life(args);
    ThreadLife threadLife = new ThreadLife(argsThread);


    @Test
    void startGame() throws InterruptedException {
        long startLife = System.currentTimeMillis();
        life.startGame();
        long finishLife = System.currentTimeMillis();
        System.out.print("Время работы однопоточной версии = ");
        System.out.println(finishLife - startLife);

        long startThreadLife = System.currentTimeMillis();
        threadLife.startGame();
        long finishThreadLife = System.currentTimeMillis();
        System.out.print("Время работы многопоточной версии = ");
        System.out.println(finishThreadLife - startThreadLife);


    }
}