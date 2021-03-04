package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс задачи
 */
public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "Заданы два множества точек в пространстве.\n" +
            "Требуется построить пересечения и разность этих множеств";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученицы 10-2 Козловой Таисии";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    private ArrayList<Point> points;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
    }

    /**
     * Добавить точку
     *
     * @param x      координата X точки
     * @param y      координата Y точки
     * @param setVal номер множества
     */
    public void addPoint(double x, double y, int setVal) {
        Point point = new Point(x, y, setVal);
        points.add(point);
    }

    /**
     * Решить задачу
     */
    public void solve() {
        // перебираем пары точек
        for (Point p : points) {
            for (Point p2 : points) {
                // если точки являются разными
                if (p != p2) {
                    // если координаты у них совпадают
                    if (Math.abs(p.x - p2.x) < 0.0001 && Math.abs(p.y - p2.y) < 0.0001) {
                        p.isSolution = true;
                        p2.isSolution = true;
                    }
                }
            }
        }
    }

    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        points.clear();
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                int setVal = sc.nextInt();
                sc.nextLine();
                Point point = new Point(x, y, setVal);
                points.add(point);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));
            for (Point point : points) {
                out.printf("%.2f %.2f %d\n", point.x, point.y, point.setNumber);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных точек
     *
     * @param n кол-во точек
     */
    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            Point p = Point.getRandomPoint();
            points.add(p);
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        points.clear();
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
            public void render(GL2 gl) {
                for (Point point : points) {
                    point.render(gl);
                }
                /*прямая*/
                gl.glLineWidth(6);
                gl.glBegin(GL2.GL_LINES);
                gl.glColor3d(0.0, 0.5, 1.0);
                gl.glVertex2d(-0.7, 0.5);
                gl.glColor3d(1.0, 0.0, 0.0);
                gl.glVertex2d(0.8, 0.9);
                gl.glEnd();
                /*треугольник
                gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3d(0.5, 0.0, 0.9);
                gl.glVertex2d(0, 0);
                gl.glColor3d(0.3, 1.0, 1.0);
                gl.glVertex2d(0.5, 0.6);
                gl.glColor3d(0.5, 0.2, 1.0);
                gl.glVertex2d(0.3, 0.7);
                gl.glEnd();
                */
                /*окружность*/
                gl.glColor3f(1.0f,0.0f,0.0f);
                gl.glBegin(GL2.GL_LINE_LOOP);
                float a;
                for(int i = 0; i < 50; i++) {
                    a = (float)i / 50.0f * 3.1415f * 2.0f;
                    gl.glVertex2f(-0.5f + (float)Math.cos(a) * 0.5f, 0 + (float)Math.sin(a) * 0.5f);
                }
                gl.glEnd();
            }
        }
