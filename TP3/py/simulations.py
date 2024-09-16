import matplotlib.pyplot as plt
import os
import math
import numpy as np
import matplotlib.animation as animation
from matplotlib.animation import FuncAnimation as fa
from matplotlib.patches import Circle



def plot_particle_interactions(particles, L, ax):

    ax.clear()

    # Obstaculo es fijo
    circle = Circle((L/2, L/2), 0.5, edgecolor='red', facecolor='red')  # Create a circle
    ax.add_patch(circle) 
    radius = 0.1
    for p in particles:
        circle = Circle((p[0]*100, p[1]*100), radius, edgecolor='blue', facecolor='lightblue')  # Create a circle
        ax.add_patch(circle)  # Add the circle to the plot

    # Obstaculo es una particula
    # radius = 0.1
    # for p in particles:
    #     if p[2] != 100000:
    #         circle = Circle((p[0]*100, p[1]*100), radius, edgecolor='blue', facecolor='lightblue')  # Create a circle
    #         ax.add_patch(circle)  # Add the circle to the plot
    #     else:
    #         circle = Circle((p[0]*100, p[1]*100), 0.5, edgecolor='red', facecolor='red')  # Create a circle
    #         ax.add_patch(circle) 
    

    # Customize the plot
    ax.set_xlim(0, L)
    ax.set_ylim(0, L)
    ax.set_xlabel("X")
    ax.set_ylabel("Y")
    ax.set_aspect('equal', adjustable='box')

def animate(i, times, L, ax):
    print(i)
    plot_particle_interactions(times[i], L, ax)

# Parámetros de simulación
L = 10

# Lee los datos
directory_path = "../times/system_with_obstacle.txt"
# directory_path = "../times/system_with_big_particle.txt"

times = []
particles = []

with open(directory_path, 'r') as file:
    lines = file.readlines()
    for l in lines:
        if l != '-\n':
            pos = l.split(":")
            x = float(pos[1])
            y = float(pos[2])
            id = float(pos[0])
            part = [x, y,id]
            particles.append(part)
        else:
            times.append(particles)
            particles = []    

# Crear la figura y el eje
fig, ax = plt.subplots()

writers = animation.FFMpegWriter(fps=60)



# Crear la animación
anim = fa(fig, animate, frames=len(times), fargs=(times, L, ax), interval=1, blit=False)

# Mostrar la animación en pantalla
# plt.show()

# Guardar la animación como GIF (opcional)
anim.save('./output_animation_system_with_obstacle.mp4', writer=writers)

# anim.save('./output_animation_system_with_big_particle.mp4', writer=writers)



