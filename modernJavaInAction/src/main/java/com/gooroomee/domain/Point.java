package com.gooroomee.domain;

import java.util.Comparator;

public record Point(int x, int y) {
    public final static Comparator<Point> compareByXAndThenY = Comparator.comparing(Point::x).thenComparing(Point::y);

    public Point moveRightBy(int x) {
        return new Point(this.x + x, this.y);
    }
}
