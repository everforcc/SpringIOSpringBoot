
-- insert into tbl(filed) select * from tbl2
INSERT INTO cc_websitedata(parentid)
SELECT e.id FROM cc_websitetag e WHERE e.id = '1';