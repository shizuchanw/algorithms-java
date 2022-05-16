This is a 2d-tree datatype that represents a set of points in the unit square,  to support *efficient range search* and *nearest-neighbor search*.
It recursively divides space into two half-planes. 

### range search
Goal. Find all points in a query axis-aligned rectangle.
1. Check if point in node lies in given rectangle.
2. Recursively search left/bottom (if any could fall in rectangle). 
3. Recursively search right/top (if any could fall in rectangle). 


### nearest-neighbor search
Goal. Find closest point to query point. 
1. Check distance from point in node to query point. 
2. Recursively search left/bottom (if it could contain a closer point).
3. Recursively search right/top (if it could contain a closer point). 
4. Organize method so that it begins by searching for query point. 
