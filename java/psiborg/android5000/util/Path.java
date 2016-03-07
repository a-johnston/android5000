package psiborg.android5000.util;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 3D path and allows for constant speed linear movement along that path.
 */
public class Path {
    private final List<Pair<Vector3, Double>> points;

    public Path() {
        points = new ArrayList<>();
    }

    /**
     * Returns the point 'x' percent along this path
     * @param x range of 0 to 1
     */
    public Vector3 get(double x) {
        if (x < 0) {
            x = 0;
        }
        if (x > 1) {
            x = 1;
        }
        x *= length();
        Pair<Vector3, Double> subStart = null;
        Pair<Vector3, Double> subEnd   = null;
        for (Pair<Vector3, Double> point : points) {
            if (point.second <= x) {
                subStart = point;
            } else {
                subEnd = point;
                break;
            }
        }
        if (subStart == null) {
            return null;
        }
        if (subEnd == null) {
            return subStart.first;
        }
        x -= subStart.second;
        x /= subEnd.second - subStart.second;
        return Vector3.lerp(subStart.first, subEnd.first, x);
    }

    public void addPoint(Vector3 v) {
        double distance = 0;
        if (numPoints() > 0) {
            distance = Vector3.distance(end(), v);
        }
        points.add(new Pair<>(v, length() + distance));
    }

    public Vector3 start() {
        return points.isEmpty() ? null : points.get(0).first;
    }

    public Vector3 end() {
        return points.isEmpty() ? null : points.get(points.size()-1).first;
    }

    public double length() {
        return points.isEmpty() ? 0 : points.get(points.size()-1).second;
    }

    public double numPoints() {
        return points.size();
    }
}
