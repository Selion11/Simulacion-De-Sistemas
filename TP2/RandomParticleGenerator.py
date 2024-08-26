import random
N = 200
L = 20
M = 10
rc = 5

static_file = open("static_CIM_input.txt", "w")
static_file.write(str(N) + "\n")
static_file.write(str(L) + "\n")
dynamic_file = open("dynamic_CIM_input.txt", "w")
dynamic_file.write("t0" + "\n")

for i in range(N):
    static_file.write(str(rc) + "\n")
    x = random.uniform(0,L)
    y = random.uniform(0,L)
    theta = random.uniform(0,2*(3.14159265359))
    dynamic_file.write(str(x) + " " + str(y) + " " + str(theta) + " " +"\n")

static_file.close()
dynamic_file.close()