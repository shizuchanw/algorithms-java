This is a program to recognize line patterns in a given set of points.

Analyzing patterns in visual images involves: 
1. **feature detection**:  selecting important features of the image.
2. **pattern recognition**: discovering patterns in the features.
<br>
In this program, we investigate **pattern recognition**: 
 Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

<hr>

Implement the datatypes: 
1. create an immutable data type Point that represents a point in the plane (including methods compareTo(Point), slopeTo(Point), and Comparator slopeOrder())
2. inside class Point, implement methods draw(), drawTo(Point), and toString().
3. create a data type LineSegment to represent line segments in the plane

Examination: 
1. Brute Force: examines 4 points at a time and checks whether they all lie on the same line segment
2. Faster Algorithms? 

ex. Fix a point p. Sort the points according to the slopes they makes with p. Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
