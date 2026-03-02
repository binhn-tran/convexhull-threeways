# convexhull-threeways

A Java implementation of the scan method to compute the convex hull of a set of 2D points.

The convex hull is the smallest convex polygon that encloses all given points.

# Algorithm

Sort points by x-coordinate (then y-coordinate)

Remove duplicate points

Construct the lower hull

Construct the upper hull

Merge both to form the final convex hull

Use the cross product to determine left and right turns

Time Complexity: O(n log n)
Space Complexity: O(n)

# How to Run
javac ConvexHull.java
java ConvexHull

# Example

Input:

(0,0), (2,0), (2,2), (0,2), (1,1)

Output:

(0, 0)
(2, 0)
(2, 2)
(0, 2)

Duplicate and interior points are automatically removed.
