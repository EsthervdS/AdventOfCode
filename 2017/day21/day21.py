import time
import math
current_milli_time = lambda: int(round(time.time() * 1000))
start = current_milli_time()

inputdata=open("input.txt", 'r').read()

lines = inputdata.splitlines()

grid = """.#.
..#
###""".splitlines()

rules = {}

def rotate2(i):
  out = ""
  out += i[1]
  out += i[4]
  out += "/"
  out += i[0]
  out += i[3]
  return out

def rotate3(i):
  out = ""
  out += i[2]
  out += i[6]
  out += i[10]
  out += "/"
  out += i[1]
  out += i[5]
  out += i[9]
  out += "/"
  out += i[0]
  out += i[4]
  out += i[8]
  return out

def mirror3(i):
  out = ""
  out += i[2]
  out += i[1]
  out += i[0]
  out += "/"
  out += i[6]
  out += i[5]
  out += i[4]
  out += "/"
  out += i[10]
  out += i[9]
  out += i[8]
  return out

for line in lines:
  col = line.split(" => ")
  if len(col[0]) == 5:
    rules[col[0]] = col[1]
    a = rotate2(col[0])
    rules[a] = col[1]
    a = rotate2(a)
    rules[a] = col[1]
    a = rotate2(a)
    rules[a] = col[1]
  else:
    rules[col[0]] = col[1]
    a = rotate3(col[0])
    rules[a] = col[1]
    a = rotate3(a)
    rules[a] = col[1]
    a = rotate3(a)
    rules[a] = col[1]
    a = mirror3(a)
    rules[a] = col[1]
    a = rotate3(a)
    rules[a] = col[1]
    a = rotate3(a)
    rules[a] = col[1]
    a = rotate3(a)
    rules[a] = col[1]

for i in range(5):
  if (len(grid) % 2) == 0:
    grid2 = []
    for o in range(int(len(grid)/2)):
      line1 = ""
      line2 = ""
      line3 = ""
      for j in range(int(len(grid)/2)):
        sub = grid[o*2][j*2:(j+1)*2]
        sub += "/"
        sub += grid[o*2+1][j*2:(j+1)*2]
        result = rules[sub].split("/")
        line1 += result[0]
        line2 += result[1]
        line3 += result[2]
      grid2.append(line1)
      grid2.append(line2)
      grid2.append(line3)
    grid = grid2
  else:
    grid2 = []
    for o in range(int(len(grid)/3)):
      line1 = ""
      line2 = ""
      line3 = ""
      line4 = ""
      for j in range(int(len(grid)/3)):
        sub = grid[o*3][j*3:(j+1)*3]
        sub += "/"
        sub += grid[o*3+1][j*3:(j+1)*3]
        sub += "/"
        sub += grid[o*3+2][j*3:(j+1)*3]
        result = rules[sub].split("/")
        line1 += result[0]
        line2 += result[1]
        line3 += result[2]
        line4 += result[3]
      grid2.append(line1)
      grid2.append(line2)
      grid2.append(line3)
      grid2.append(line4)
    grid = grid2

count = 0
for i in range(len(grid)):
  for o in range(len(grid)):
    if grid[i][o] == "#":
      count += 1
print(count)

grid = """.#.
..#
###""".splitlines()

for i in range(18):
  if (len(grid) % 2) == 0:
    grid2 = []
    for o in range(int(len(grid)/2)):
      line1 = ""
      line2 = ""
      line3 = ""
      for j in range(int(len(grid)/2)):
        sub = grid[o*2][j*2:(j+1)*2]
        sub += "/"
        sub += grid[o*2+1][j*2:(j+1)*2]
        result = rules[sub].split("/")
        line1 += result[0]
        line2 += result[1]
        line3 += result[2]
      grid2.append(line1)
      grid2.append(line2)
      grid2.append(line3)
    grid = grid2
  else:
    grid2 = []
    for o in range(int(len(grid)/3)):
      line1 = ""
      line2 = ""
      line3 = ""
      line4 = ""
      for j in range(int(len(grid)/3)):
        sub = grid[o*3][j*3:(j+1)*3]
        sub += "/"
        sub += grid[o*3+1][j*3:(j+1)*3]
        sub += "/"
        sub += grid[o*3+2][j*3:(j+1)*3]
        result = rules[sub].split("/")
        line1 += result[0]
        line2 += result[1]
        line3 += result[2]
        line4 += result[3]
      grid2.append(line1)
      grid2.append(line2)
      grid2.append(line3)
      grid2.append(line4)
    grid = grid2

count = 0
for i in range(len(grid)):
  for o in range(len(grid)):
    if grid[i][o] == "#":
      count += 1
print(count)


print((current_milli_time() - start) / 1000.0)
