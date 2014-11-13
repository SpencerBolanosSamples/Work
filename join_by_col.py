import pprint
import csv
import os


fileA = csv.reader(open("/Users/Spencer/Documents/Full_Millie/Project_Col/colleges/end_list10.csv", 'rU'))
fileB = csv.reader(open("/Users/Spencer/Documents/Full_Millie/Project_Col/colleges/M.csv", 'rU'))

t1atts = []
t2atts = []

t1tuples = []
t2tuples = []

header = True
for row in fileA:
	if header:
		t1atts = row
		header = False
	else:
		t1tuples.append(row)

header = True
for row in fileB:
	if header:
		t2atts = row
		header = False
	else:
		t2tuples.append(row)

t1columns = set(t1atts)
t2columns = set(t2atts)

t1map = {k: i for i, k in enumerate(t1atts)}
t2map = {k: i for i, k in enumerate(t2atts)}

join_on = t1columns & t2columns
diff1 = t1columns - join_on
diff2 = t2columns - join_on

results = []
for t1row in t1tuples:
	row = []
	for rn in join_on:
		row.append(t1row[t1map[rn]])
	for rn in diff1:
		row.append(t1row[t1map[rn]])
	for rn in diff2:
		row.append("")
	results.append(row)


for t2row in t2tuples:
	row = []
	for rn in join_on:
		row.append(t2row[t2map[rn]])
	for rn in diff1:
		row.append("")
	for rn in diff2:
		row.append(t2row[t2map[rn]])
	results.append(row)

#pprint.pprint(results)

with open('/Users/Spencer/Documents/Full_Millie/Project_Col/colleges/mergh.csv', 'w') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerows(results)