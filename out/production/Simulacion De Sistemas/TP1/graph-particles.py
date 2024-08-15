import matplotlib.pyplot as plt
from matplotlib.widgets import Slider

file_name = "./TP1/dynamic_CIM_input.txt"

radius = 0.25

# Create a plot
fig, ax = plt.subplots()
particles = []
vecinas = []
plt.subplots_adjust(left=0.1, bottom=0.25)  # Adjust space for sliders

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

for p in particles:
    ax.add_patch(plt.Circle((p[0], p[1]), radius, color='g', alpha=0.7))

with open("./TP1/vecinas.txt",'r') as file:
    lines = file.readlines()
    for l in lines:
        vec = l.split()
        vecinas.append(vec)
        
file.close()
slider = Slider(ax, 'Index', 0, len(particles)-1, valinit=0)
def update(s):

    ax.add_patch(plt.Circle((particles[s.val][0], particles[s.val][1]), radius, color='r', alpha=0.7))

    for v in vecinas[s.val]:
        ax.add_patch(plt.Circle((particles[int(v)][0], particles[int(s)][1]), radius, color='b', alpha=0.7))        
        
    ax.set_xlim(0, 25)
    ax.set_ylim(0, 25)

    # Set equal scaling 
    ax.set_aspect('equal')
    
    fig.canvas.draw_idle()
    
slider.on_changed(update)

# Show the plot
plt.show()

        