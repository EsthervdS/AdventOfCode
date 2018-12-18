import java.util.ArrayDeque;
import java.util.Queue;
import util.*;
/**
 * Created by Ilya Gazman on 10/17/2018.
 */
public class BFS {

    private static final boolean DEBUG = false;

    public Position[] findPath(final int[][] map,
                            final Position position,
                            final Position destination) {
        if (isOutOfMap(map, position.x, position.y)
                || isOutOfMap(map, destination.x, destination.y)
                || isBlocked(map, position.x, position.y)
                || isBlocked(map, destination.x, destination.y)) {
            return null;
        }

        Queue<Position> queue1 = new ArrayDeque<>();
        Queue<Position> queue2 = new ArrayDeque<>();

        queue1.add(position);

        map[position.x][position.y] = -1;

        for (int i = 2; !queue1.isEmpty(); i++) {
            if (queue1.size() >= map.length * map[0].length) {
                throw new IllegalStateException("Map overload");
            }

            for (Position point : queue1) {
                if (point.x == destination.x && point.y == destination.y) {
                    return arrived(map, i - 1, point);
                }

                final Queue<Position> finalQueue = queue2;
                final int finalStepCount = i;

                lookAround(map, point, (x, y) -> {
                    if (isBlocked(map, x, y)) {
                        return;
                    }

                    Position e = new Position(x, y);

                    finalQueue.add(e);

                    map[e.x][e.y] = -finalStepCount;
                });
            }

            if (DEBUG) {
                printMap(map);
            }

            queue1 = queue2;
            queue2 = new ArrayDeque<>();
        }

        resetMap(map);

        return null;
    }

    private static boolean isOutOfMap(final int[][] map,
                                      final int x,
                                      final int y) {
        return x < 0 || y < 0 || map[0].length <= y || map.length <= x;
    }

    private boolean isBlocked(final int[][] map, final int x, final int y) {
        final int i = map[x][y];
        return i < 0 || i >= 1;
    }

    private Position[] arrived(final int[][] map, final int size, final Position p) {
        final Position[] optimalPath = new Position[size];

        computeSolution(map, p.x, p.y, size, optimalPath);

        resetMap(map);

        return optimalPath;
    }

    private void resetMap(final int[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] < 0) {
                    map[x][y] = 0;
                }
            }
        }
    }

    private void printMap(final int[][] map) {
        for (final int[] r : map) {
            for (final int i : r) {
                System.out.print(i + "\t");
            }

            System.out.println();
        }

        System.out.println("****************************************");
    }

    private void computeSolution(final int[][] map,
                                 final int x,
                                 final int y,
                                 final int stepCount,
                                 final Position[] optimalPath) {
        if (isOutOfMap(map, x, y)
                || map[x][y] == 0
                || map[x][y] != -stepCount) {
            return;
        }

        final Position p = new Position(x, y);

        optimalPath[stepCount - 1] = p;

        lookAround(map, p, (x1, y1) -> computeSolution(map, x1, y1, stepCount - 1, optimalPath));
    }

    private void lookAround(final int[][] map,
                            final Position p,
                            final Callback callback) {
        //callback.look(map, p.x + 1, p.y + 1);
        //callback.look(map, p.x - 1, p.y + 1);
        //callback.look(map, p.x - 1, p.y - 1);
        //callback.look(map, p.x + 1, p.y - 1);
        callback.look(map, p.x + 1, p.y);
        callback.look(map, p.x - 1, p.y);
        callback.look(map, p.x, p.y + 1);
        callback.look(map, p.x, p.y - 1);
    }

    private interface Callback {
        default void look(final int[][] map, final int x, final int y) {
            if (isOutOfMap(map, x, y)) {
                return;
            }
            onLook(x, y);
        }

        void onLook(int x, int y);
    }
}
