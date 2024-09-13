import random
import math 
N = 200
L = 0.1

static_file = open("../TP3/static_input.txt", "w")
static_file.write(str(N) + "\n")
static_file.write(str(L) + "\n")
dynamic_file = open("../TP3/dynamic_input.txt", "w")
dynamic_file.write("t0" + "\n")

for i in range(N):
    # static_file.write(str(rc) + "\n")
    x = random.uniform(0,L)
    y = random.uniform(0,L)
    theta = random.uniform(0,2*(math.pi))
    dynamic_file.write(str(x) + " " + str(y) + " " + str(theta) + " " +"\n")

static_file.close()
dynamic_file.close()