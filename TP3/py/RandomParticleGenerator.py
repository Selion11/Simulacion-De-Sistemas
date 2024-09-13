import random
import math 
N = 200
L = 0.1

static_file = open("../TP3/static_input.txt", "w")
static_file.write(str(N) + "\n")
static_file.write(str(L) + "\n")
dynamic_file = open("../TP3/dynamic_input.txt", "w")
dynamic_file.write("t0" + "\n")

radius = 0.001
object_radius = 0.005
positions = []
for i in range(N):

    next = 0
    
    while next == 0:
        x = random.uniform(0.001,L-0.001)
        y = random.uniform(0.001,L-0.001)
        count = 0 
        for p in positions:
            if (p[0] == x and p[1]==y) or math.sqrt((p[0] - x)**2 + (p[1] - y)**2) <= (2*radius) or math.sqrt((L/2 - x)**2 + (L/2 - y)**2) <= (radius+object_radius):
                break
            else:
                count +=1

        
        if count == len(positions):
            next = 1
            break

    aux = [] 
    aux.append(x)
    aux.append(y)
    positions.append(aux)

    theta = random.uniform(0,2*(math.pi))
    dynamic_file.write(str(x) + " " + str(y) + " " + str(theta) + " " +"\n")

static_file.close()
dynamic_file.close()