package java_snake;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;
    private boolean growing = false;
    private boolean canChangeDirection = true;

    public Snake(Point start) {
        body = new LinkedList<>();
        body.add(start);
        direction = Direction.RIGHT; // Default
    }

    public List<Point> getBody() {
        return Collections.unmodifiableList(body);
    }

    public Point getHead() {
        return body.getFirst();
    }

    public void setDirection(Direction newDirection) {
        if (!canChangeDirection) {
            return;
        }

        // Prevent 180 degree turns
        if (this.direction == Direction.UP && newDirection == Direction.DOWN) return;
        if (this.direction == Direction.DOWN && newDirection == Direction.UP) return;
        if (this.direction == Direction.LEFT && newDirection == Direction.RIGHT) return;
        if (this.direction == Direction.RIGHT && newDirection == Direction.LEFT) return;
        
        this.direction = newDirection;
        canChangeDirection = false;
    }
    
    public Direction getDirection() {
        return direction;
    }

    public Point peekNextHead() {
        Point head = getHead();
        return switch (direction) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };
    }

    public void move() {
        Point newHead = peekNextHead();
        
        body.addFirst(newHead);
        
        if (!growing) {
            body.removeLast();
        } else {
            growing = false;
        }
        canChangeDirection = true;
    }

    public void setGrowing(boolean growing) {
        this.growing = growing;
    }
    
    public boolean checkSelfCollision() {
        Point head = getHead();
        // Check if head exists anywhere else in the body
        // We skip the first element because that is the head itself
        return body.stream().skip(1).anyMatch(p -> p.equals(head));
    }
}
