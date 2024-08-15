import matplotlib.pyplot as plt
from matplotlib.widgets import Slider

file_name = "./TP1/dynamic_CIM_input.txt"

radius = 0.25

# Create a plot
fig, ax = plt.subplots()
particles = []
vecinas = []

with open(file_name,'r') as file:
    file.readline()
    lines = file.readlines()
    for l in lines:
        pos = l.split()
        x = float(pos[0])
        y = float(pos[1])
        part = [x,y]
        particles.append(part)

file.close()


with open("./TP1/vecinas.txt",'r') as file:
    lines = file.readlines()
    for l in lines:
        vec = l.split()
        vecinas.append(vec)
        
file.close()


ax.add_patch(plt.Circle((particles[1][0], particles[1][1]), radius, color='r', alpha=0.7))

for v in vecinas[1]:
    ax.add_patch(plt.Circle((particles[int(v)-1][0], particles[int(v)-1][1]), radius, color='b', alpha=0.7))        
        
ax.set_xlim(0, 25)
ax.set_ylim(0, 25)

    # Set equal scaling 
ax.set_aspect('equal')
    


# Show the plot
plt.show()

        