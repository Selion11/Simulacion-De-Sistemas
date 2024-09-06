import matplotlib.pyplot as plt
import os
import math
import numpy as np
import matplotlib.animation as animation
from matplotlib.animation import FuncAnimation as fa

speed = 0.03

def plot_particle_interactions(particles, M, L, ax):

    ax.clear()

    # # Draw grid
    # for i in range(M + 1):
    #     ax.axhline(i * (L / M), color='gray', linewidth=0.5)
    #     ax.axvline(i * (L / M), color='gray', linewidth=0.5)

    # Normalizamos los ángulos a un rango de 0 a 1
    normalized_angles = []
    for p in particles:
        aux = (p[2] + np.pi) / (2 * np.pi) 
        if aux > 1:
            normalized_angles.append(aux - 1)
        elif aux < 0:
            normalized_angles.append(aux + 1)
        else:
            normalized_angles.append(aux)

    cmap = plt.cm.hsv 

    new_x_coords = [math.cos(p[2])*speed for p in particles]
    new_y_coords = [math.sin(p[2])*speed for p in particles]

    for particle, new_x, new_y, norm_angle in zip(particles, new_x_coords, new_y_coords, normalized_angles):
        color = cmap(norm_angle)
        ax.quiver(particle[0], particle[1], new_x, new_y, angles='xy', color=color)

    # Customize the plot
    # ax.set_xlim(0, L)
    # ax.set_ylim(0, L)
    # ax.set_xlabel("X")
    # ax.set_ylabel("Y")
    # ax.set_aspect('equal', adjustable='box')

def animate(i, times, M, L, ax):
    plot_particle_interactions(times[i], M, L, ax)

# Parámetros de simulación
M = 10
L = 50

# Lee los datos
directory_path = "../times/particles_time_"
times = []

for i in range(1,138):
    particles = []
    with open(directory_path+str(i)+".txt", 'r') as file:
        lines = file.readlines()
        for l in lines:
            pos = l.split(":")
            x = float(pos[1])
            y = float(pos[2])
            theta = float(pos[3])
            part = [x, y, theta]
            particles.append(part)
    times.append(particles)
    

# Crear la figura y el eje
fig, ax = plt.subplots()

writers = animation.FFMpegWriter(fps=10)



# Crear la animación
anim = fa(fig, animate, frames=len(times), fargs=(times, M, L, ax), interval=10, blit=False)

# Mostrar la animación en pantalla
plt.show()

# Guardar la animación como GIF (opcional)
anim.save('./output_animation_0.7_1000.mp4', writer=writers)


