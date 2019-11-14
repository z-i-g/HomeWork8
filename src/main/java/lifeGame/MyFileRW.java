package lifeGame;

import java.io.*;

/**
 * Класс для считывания и сохранения начальной и конечной конфигураций игры.
 *
 * @version 1.0
 * @autor Айрат Загидуллин
 */
public class MyFileRW {

    private int width;
    private int height;
    private int[][] world;
    private String inputFileName;
    private String outputFileName;

    /**
     * Конструктор
     *
     * @param inputFileName - имя файла начальной конфигурации
     * @param  outputFileName - имя файла конечной конфигурации
     */
    public MyFileRW(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getWorld() {
        return world;
    }

    public void setWorld(int[][] world) {
        this.world = world;
    }


    /**
     * Считывание файла
     */
    public void readFile() {
        try (BufferedReader widthHeightReader = new BufferedReader(new FileReader(inputFileName))) {
            String lineLenght;
            while ((lineLenght = widthHeightReader.readLine()) != null) {
                width = lineLenght.length();
                height++;
            }
        } catch (IOException e) {
            System.err.println("Не найден файл начальной конфигурации!");
        }

        world = new int[height][width];
        String oneLine;
        int row = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName))) {
            while (bufferedReader.ready()) {
                oneLine = bufferedReader.readLine();
                for (int i = 0; i < width; i++) {
                    world[row][i] = Integer.parseInt(String.valueOf(oneLine.charAt(i)));
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранение файла
     */
    public void writeFile() {
        int row = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName, false))) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    bufferedWriter.write(String.valueOf(world[i][j]));
                }
                bufferedWriter.flush();
                bufferedWriter.newLine();
            }
            System.out.println("Создан файл: " + outputFileName);
        } catch (IOException e) {
            System.err.println("Не удалось создать файл!");
        }

    }
}
