package lifeGame;

import java.io.*;

public class MyFileRW {

    private int width;
    private int height;
    private int[][] world;

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


    public void readFile(String fileName) {
        try (BufferedReader widthHeightReader = new BufferedReader(new FileReader(fileName))) {
            String lineLenght;
            while ((lineLenght = widthHeightReader.readLine()) != null) {
                width = lineLenght.length();
                height++;
            }
        } catch (IOException e) {
            System.err.println("Не найден файл начальной конфигурации!");
        }

        world = new int[width][height];
        String oneLine;
        int row = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (bufferedReader.ready()) {
                oneLine = bufferedReader.readLine();
                for (int i = 0; i < width; i++) {
                    world[row][i] = Integer.parseInt(String.valueOf(oneLine.charAt(i)));
//                    System.out.println("row = " + row + " i = " + i + " value = " + world[row][i]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Ширина: " + width + " Высота: " + (height));
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++)
//                System.out.print(world[i][j] + " ");
//            System.out.println();
//        }
    }

    public void writeFile(String fileName) {
        int row = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bufferedWriter.write(String.valueOf(world[i][j]));
//                    System.out.println("add " + world[i][j]);
                }
                bufferedWriter.flush();
                bufferedWriter.newLine();
            }
            System.out.println("Создан файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Не удалось создать файл!");
        }

    }
}
