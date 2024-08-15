import random
N = 10
L = 10
M = 5
r = 0.5
rc = 0.25

static_file = open("static_CIM_input.txt", "w")
static_file.write(str(N) + "\n")
static_file.write(str(L) + "\n")
dynamic_file = open("dynamic_CIM_input.txt", "w")
dynamic_file.write("t0" + "\n")

for i in range(N):
    static_file.write(str(rc) + "\n")
    x = random.uniform(0,L)
    y = random.uniform(0,L)
    dynamic_file.write(str(x) + " " + str(y) + " " + "\n")

static_file.close()
dynamic_file.close()