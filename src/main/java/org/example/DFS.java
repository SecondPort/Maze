package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DFS implements Runnable{
    public char[][] matrix;
    private final Queue<Point> visited;
    private final Stack<Point> path;
    private Stack<Point> auxPath;
    public Stack<Point> result;
    private Point standing;
    private final Point initial;
    private final Point end;
    private long startTime;
    private long endTime;
    public long timeElapsed;
    private char counter = 65;

    public DFS(char[][] matrix,Point initial, Point end) {
        this.matrix = matrix;
        this.visited = new LinkedList<Point>();
        this.path = new Stack<Point>();
        this.initial = initial;
        this.end = end;
        this.startTime = 0;
        this.endTime = 0;
        this.timeElapsed = 0;
    }

    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", "DFS", message);
    }


    public void run() {
        try{
            startTime = System.nanoTime();
            this.result = algorithm();
            endTime = System.nanoTime();
            timeElapsed = endTime - startTime;
            threadMessage(timeElapsed + " nanos");
        } catch (Exception e) {
            threadMessage("Interrumpido");
        }
    }

    public Boolean checkIfVisited(Queue<Point> q, Point check) {
        for(Point p : q) {
            if(check.x == p.x && check.y == p.y) {
                return true;
            }
        }
        return false;
    }

    public Boolean checker () {
        Point standing = path.peek();

        if(end.x == standing.x && end.y == standing.y) {
            auxPath = (Stack<Point>)path.clone();
            return true;
        }

        //Check up of standing
        Point aux = new Point();
        aux.x = standing.x - 1;
        aux.y = standing.y;
        if(matrix[standing.x - 1][standing.y] != '#' && !checkIfVisited(visited, aux)){
            path.push(aux);
            visited.add(aux);
            matrix[aux.x][aux.y] = (char)counter;
            counter++;
            checker();
        }

        //Check rigth of standing
        aux = new Point();
        aux.x = standing.x;
        aux.y = standing.y + 1;
        if(matrix[standing.x][standing.y + 1] != '#' && !checkIfVisited(visited, aux)){
            path.push(aux);
            visited.add(aux);
            matrix[aux.x][aux.y] = (char)counter;
            counter++;
            checker();
        }

        //Check down of standing
        aux = new Point();
        aux.x = standing.x + 1;
        aux.y = standing.y;
        if(matrix[standing.x + 1][standing.y] != '#' && !checkIfVisited(visited, aux)){
            path.push(aux);
            visited.add(aux);
            matrix[aux.x][aux.y] = (char)counter;
            counter++;
            checker();
        }

        //Check left of standing
        aux = new Point();
        aux.x = standing.x;
        aux.y = standing.y - 1;
        if(matrix[standing.x][standing.y - 1] != '#' && !checkIfVisited(visited, aux)){
            path.push(aux);
            visited.add(aux);
            matrix[aux.x][aux.y] = (char)counter;
            counter++;
            checker();
        }

        path.pop();
        return false;
    }
    public Stack<Point> algorithm() {
        Boolean res;

        path.push(initial);
        visited.add(initial);
        matrix[initial.x][initial.y] = (char)counter;
        counter++;
        res = checker();

        return auxPath;
    }

}
