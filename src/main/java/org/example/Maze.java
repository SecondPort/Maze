package org.example;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Maze {
    public static void main(String[] args) throws Exception  {
        int sizeMatrix = 0;
        int coordinate_x,coordinate_y;
        Reader r = new Reader("src/main/resources/maze.txt");
        Scanner input = new Scanner( System.in );
        Point beg;
        Point end;
        BFS bfs;
        DFS dfs;
        Queue<Point> resultb;
        Stack<Point> resultd;

        System.out.print( "\nIngrese el tamaño del laberinto (el eje x e y deben ser iguales): ");
        sizeMatrix = input.nextInt();
        System.out.print(sizeMatrix);
        char[][] matrixb;
        char[][] matrixd;
        //Leer matriz
        matrixb = r.fileReader(sizeMatrix);
        matrixd = r.fileReader(sizeMatrix);
        //Crear punto para iniciar laberinto (o)
        System.out.print( "\nIngrese la coordenada del eje x para el inicio: " );
        coordinate_x = input.nextInt();
        System.out.print( "\nIngrese la coordenada del eje y para el inicio: " );
        coordinate_y = input.nextInt();
        beg = new Point(coordinate_x,coordinate_y);
        //Crear punto para finalizar laberinto (x)
        System.out.print( "\nIngrese la coordenada x para el final del laberinto: " );
        coordinate_x = input.nextInt();
        System.out.print( "\nIngrese la coordenada y para el final del laberinto: " );
        coordinate_y = input.nextInt();
        end = new Point(coordinate_x,coordinate_y);


        //Laberinto vacío
        for(int i = 0;i< 10;i++) {
            for(int j = 0; j < 10; j++){
                System.out.print(matrixb[i][j]);
            }
            System.out.println("\n");
        }
        System.out.print('\n');

        //Ejecución
        bfs = new BFS(matrixb,beg,end);
        dfs = new DFS(matrixd,beg,end);
        Thread t1 = new Thread(bfs);
        Thread t2 = new Thread(dfs);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        resultb = bfs.result;
        resultd = dfs.result;

        System.out.println("\n");
        //BFS
        for(int i = 0;i< 10;i++) {
            for(int j = 0; j < 10; j++){
                System.out.print(bfs.matrix[i][j]);
            }
            System.out.println("\n");
        }

        System.out.print("Camino optimo BFS:");
        for(Point p : resultb) {
            System.out.print("(" + p.x + ", " + p.y + ") ");
        }
        System.out.println("\n");
        //DFS
        for(int i = 0;i< 10;i++) {
            for(int j = 0; j < 10; j++){
                System.out.print(dfs.matrix[i][j]);
            }
            System.out.println("\n");
        }

        System.out.print("Camino optimo DFS:");
        for(Point p : resultd) {
            System.out.print("(" + p.x + ", " + p.y + ") ");
        }
    }
}
