SELECT e.`age`,COUNT(e.`age`) FROM oneforall.`user` e
GROUP BY age
HAVING  COUNT(e.`age`) > 1;