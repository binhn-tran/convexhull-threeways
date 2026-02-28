import java.util.*;

public class ConvexHull {

    // basic point class to store x and y coordinates
    public static class Point implements Comparable<Point> {
        long x;
        long y;

        // constructor
        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        // used for sorting points by x first, then y
        @Override
        public int compareTo(Point other) {
            if (x != other.x) {
                return Long.compare(x, other.x);
            } else {
                return Long.compare(y, other.y);
            }
        }

        // checks if two point objects represent the same coordinate
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (getClass() != obj.getClass()) {
                return false;
            } else {
                Point p = (Point) obj;
                return x == p.x && y == p.y;
            }
        }

        // needed when overriding equals
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        // makes printing points easier
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // cross product of vectors (b - a) and (c - a)
    // used to decide to either turn left or right
    static long cross(Point a, Point b, Point c) {
        long x1 = b.x - a.x;
        long y1 = b.y - a.y;
        long x2 = c.x - a.x;
        long y2 = c.y - a.y;
        return x1 * y2 - y1 * x2;
    }

    // convex hull using the scan method
    public static List<Point> convexHull(List<Point> points) {

        // if null or fewer than 2 points, just return them
        if (points == null) {
            return new ArrayList<>();
        }
        if (points.size() <= 2) {
            return new ArrayList<>(points);
        }

        // make a copy so we don't change the original list
        List<Point> pts = new ArrayList<>(points);

        // sort points by x then y
        Collections.sort(pts);

        // remove duplicate points after sorting
        List<Point> clean = new ArrayList<>();
        for (Point p : pts) {
            if (clean.isEmpty() || !clean.get(clean.size() - 1).equals(p)) {
                clean.add(p);
            }
        }

        // if only 2 unique points remain, that is the hull
        if (clean.size() <= 2) {
            return clean;
        }

        List<Point> lower = new ArrayList<>();

        // build lower hull from left to right
        for (Point p : clean) {

            // while the last two points and new point make a right turn,
            // remove the last point (it creates a dent)
            while (lower.size() >= 2) {
                Point p1 = lower.get(lower.size() - 2);
                Point p2 = lower.get(lower.size() - 1);

                if (cross(p1, p2, p) <= 0) {
                    lower.remove(lower.size() - 1);
                } else {
                    break;
                }
            }

            // add current point to lower hull
            lower.add(p);
        }

        List<Point> upper = new ArrayList<>();

        // build upper hull from right to left
        for (int i = clean.size() - 1; i >= 0; i--) {
            Point p = clean.get(i);

            // same logic as lower hull
            while (upper.size() >= 2) {
                Point p1 = upper.get(upper.size() - 2);
                Point p2 = upper.get(upper.size() - 1);

                if (cross(p1, p2, p) <= 0) {
                    upper.remove(upper.size() - 1);
                } else {
                    break;
                }
            }

            upper.add(p);
        }

        // remove duplicate endpoints (first and last points repeat)
        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);

        // combine lower and upper parts to form full hull
        lower.addAll(upper);

        return lower;
    }

    // test the convex hull implementation with some example points
    public static void main(String[] args) {

        // example points
        List<Point> pts = new ArrayList<>();
        pts.add(new Point(0, 0));
        pts.add(new Point(2, 0));
        pts.add(new Point(2, 2));
        pts.add(new Point(0, 2));
        pts.add(new Point(1, 1));
        pts.add(new Point(0, 0));  // duplicate test
        pts.add(new Point(2, 2));  // duplicate test

        // compute hull
        List<Point> hull = convexHull(pts);

        // print result
        System.out.println("Convex Hull:");
        for (Point p : hull) {
            System.out.println(p);
        }
    }
}

