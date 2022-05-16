/* *****************************************************************************
 * Flora Wang
 * 12/08, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class KdTree {
    private enum Separator {VERTICAL, HORIZONTAL}

    ;
    private Node root;
    private int size;

    // node class
    private static class Node {
        private final Separator sepr;
        private final RectHV rect;
        private double key;
        private Point2D point;
        private Node LB, RT;

        public Node(Point2D point, Separator sepr, RectHV rect) {
            this.point = point;
            this.sepr = sepr;
            this.rect = rect;
        }

        public boolean isRT(Point2D that) {
            return (sepr == Separator.HORIZONTAL && point.y() > that.y())
                    || (sepr == Separator.VERTICAL && point.x() > that.x());
        }

        public RectHV rectLB() {
            return sepr == Separator.VERTICAL
                   ? new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax())
                   : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        }

        public RectHV rectRT() {
            return sepr == Separator.VERTICAL
                   ? new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax())
                   : new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
        }

        public Separator nextSepr() {
            return sepr == Separator.HORIZONTAL ? Separator.VERTICAL : Separator.HORIZONTAL;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p, Separator.VERTICAL, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }

        // traverse to the position
        Node curr = root;
        Node prev = null;

        do {
            if (curr.point.equals(p)) {
                return;
            }
            prev = curr;
            if (curr.isRT(p)) {
                curr = curr.LB;
            }
            else curr = curr.RT;
        } while (curr != null);

        // insert at this position
        if (prev.isRT(p)) prev.LB = new Node(p, prev.nextSepr(), prev.rectLB());
        else prev.RT = new Node(p, prev.nextSepr(), prev.rectRT());

        size++;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node curr = root;
        while (curr != null) {
            if (curr.point.equals(p)) return true;
            else if (curr.isRT(p)) {
                curr = curr.LB;
            }
            else curr = curr.RT;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw_rec(root);
    }

    private void draw_rec(Node node) {
        if (node == null) return;
        StdDraw.point(node.point.x(), node.point.y());
        draw_rec(node.LB);
        draw_rec(node.RT);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> list = new LinkedList<Point2D>();
        range_rec(root, rect, list);
        return list;
    }

    private void range_rec(Node node, RectHV rect, LinkedList<Point2D> list) {
        if (node == null) return;
        // DFS
        if (rect.contains(node.point)) {
            list.add(node.point);
            range_rec(node.LB, rect, list);
            range_rec(node.RT, rect, list);
            return;
        }
        if (node.isRT(new Point2D(rect.xmin(), rect.ymin()))) {
            range_rec(node.LB, rect, list);
        }
        if (!node.isRT(new Point2D(rect.xmax(), rect.ymax()))) {
            range_rec(node.RT, rect, list);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return isEmpty() ? null : nearest(p, root.point, root);
    }

    private Point2D nearest(Point2D target, Point2D near, Node node) {
        if (node == null) return near;
        double minDist = near.distanceTo(target);
        if (node.rect.distanceTo(target) < minDist) {
            double nodeDist = node.point.distanceTo(target);
            if (nodeDist < minDist) {
                near = node.point;
            }
            if (node.isRT(target)) {
                near = nearest(target, near, node.LB);
                near = nearest(target, near, node.RT);
            }
            else {
                near = nearest(target, near, node.RT);
                near = nearest(target, near, node.LB);
            }
        }
        return near;
    }

    public static void main(String[] args) {

    }
}
