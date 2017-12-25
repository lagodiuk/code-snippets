-- Solution of https://www.hackerrank.com/challenges/binary-search-tree-1
SELECT b1.N, if(b1.P is null, 'Root', if(b2.N is null, 'Leaf', 'Inner'))
FROM BST b1
LEFT OUTER JOIN BST b2
ON b1.N = b2.P
GROUP BY b1.N
ORDER BY b1.N;
