package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
public class BFS implements Runnable{

    public char[][] matrix;
    private final Queue<Point> visited;
    private final Queue<Point> path;
    private final Queue<Point> frontier;
    public Queue<Point> result;
    public Point standing;
    private Point aux;
    private final Point initial;
    private final Point end;
    private long startTime;
    private long endTime;
    public long timeElapsed;

    public BFS(char[][] matrix,Point initial, Point end) {
        this.matrix = matrix;
        this.visited = new LinkedList<Point>();
        this.frontier = new LinkedList<Point>();
        this.path = new LinkedList<Point>();
        this.aux = new Point();
        this.initial = initial;
        this.standing = initial;
        this.end = end;
        this.startTime = 0;
        this.endTime = 0;
        this.timeElapsed = 0;
    }

    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", "BFS", message);
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

    public void reverse(Queue<Point> q) {
        Stack<Point> s = new Stack<>();  //crear un stack

        //mientras la cola no este vacia
        while(!q.isEmpty())
        {  //agrergar los elementos de la cola en una pila
            s.push(q.poll());
        }

        //mientras que la pila no este vacia
        while(!s.isEmpty())
        { //agregar los elementos de la pila en una cola
            q.add(s.pop());
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

    public Boolean checkIfOnFrontier(Queue<Point> q, Point check) {
        for(Point p : q) {
            if(check.x == p.x && check.y == p.y) {
                return true;
            }
        }
        return false;
    }

    public Point findPoint (Queue<Point> q, int checkx, int checky) {
        Point aux = new Point();
        for(Point p : q) {
            if(checkx == p.x && checky == p.y) {
                return p;
            }
        }
        return aux;
    }

    public void backTrackPath (Point end) {
        Point p;
        if(end.x == initial.x && end.y == initial.y) {
            path.add(initial);
            return;
        }
        path.add(end);
        p = findPoint(visited,end.predX,end.predY);
        backTrackPath(p);
    }

    public Queue<Point> algorithm() {
        frontier.add(this.initial);
        this.aux.x = 0;
        this.aux.y = 0;
        char counter = 65;
        while(!frontier.isEmpty()) {
            Point current = frontier.element(); //Mirar el primero de la cola
            standing = frontier.element();

            //Comprombar si hay un punto en la izquierda de standing
            this.aux.x = standing.x;
            this.aux.y = standing.y - 1;
            if(matrix[standing.x][standing.y - 1] != '#' && !checkIfVisited(visited,aux) && !checkIfOnFrontier(frontier,aux)){
                aux.predX = standing.x;
                aux.predY = standing.y;
                frontier.add(aux);
            }

            //Comprombar si hay un punto en la derecha de standing
            this.aux = new Point();
            this.aux.x = standing.x;
            this.aux.y = standing.y + 1;
            if(matrix[standing.x][standing.y + 1] != '#' && !checkIfVisited(visited,aux) && !checkIfOnFrontier(frontier,aux)){
                aux.predX = standing.x;
                aux.predY = standing.y;
                frontier.add(aux);
            }

            //Comprombar si hay un punto arriba de standing
            this.aux = new Point();
            this.aux.x = standing.x - 1;
            this.aux.y = standing.y;
            if(matrix[standing.x - 1][standing.y] != '#' && !checkIfVisited(visited,aux) && !checkIfOnFrontier(frontier,aux)){
                aux.predX = standing.x;
                aux.predY = standing.y;
                frontier.add(aux);
            }

            //Comprombar si hay un punto abajo de standing
            this.aux = new Point();
            this.aux.x = standing.x + 1;
            this.aux.y = standing.y;
            if(matrix[standing.x + 1][standing.y] != '#' && !checkIfVisited(visited,aux) && !checkIfOnFrontier(frontier,aux)){
                aux.predX = standing.x;
                aux.predY = standing.y;
                frontier.add(aux);
            }

            //Lugar actual visitado
            matrix[current.x][current.y] = (char)counter;
            visited.add(current);
            counter++;

            if(current.x == end.x && current.y == end.y) {
                backTrackPath(current);
                reverse(path);
                break;
            }
            //Frontera actualizada
            frontier.poll();
            this.aux = new Point();
        }
        return path;
    }
}
