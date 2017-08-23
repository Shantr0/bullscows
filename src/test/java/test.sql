
SELECT  COUNT(*),(SELECT count(ID) FROM sessions WHERE userName='admin' AND CLOSED=TRUE ) FROM userstries
WHERE userName='admin'

