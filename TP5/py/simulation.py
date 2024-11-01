import matplotlib.pyplot as plt
import os
import math
import numpy as np
import matplotlib.animation as animation
from matplotlib.animation import FuncAnimation as fa
from matplotlib.patches import Circle
import pandas as pd


largo = 100
ancho = 70 

def plot_particle_interactions(x,y, ax):

    ax.clear()

    # Jugador Atacante
    radius = 1

    circle = Circle((x[0], y[0]),radius, edgecolor='red', facecolor='red')  
    ax.add_patch(circle) 
    radius = 1

    # Defensores
    for i in range(1, len(x)):
        circle = Circle((x[i], y[i]), radius, edgecolor='blue', facecolor='lightblue')  # Create a circle
        ax.add_patch(circle)  # Add the circle to the plot

    

    # Customize the plot
    ax.set_xlim(0, largo)
    ax.set_ylim(0, ancho)
    ax.set_xlabel("X (m)")
    ax.set_ylabel("Y (m)")
    ax.set_aspect('equal', adjustable='box')

def animate(i, x,y, tiempo, ax):
    print(i)
    plot_particle_interactions(x,y, ax)
    ax.text(0.5, 1.05, f'Tiempo: {tiempo[i]}', 
        horizontalalignment='center', verticalalignment='center', 
        transform=ax.transAxes, fontsize=12)


# Lee los datos
file = "../output/output.txt"

df = pd.read_csv(file, delimiter=';')

timeElapsed = df['time'].tolist()
raw_x_positions = df['x'].tolist()
raw_y_positions = df['y'].tolist()


seconds = []
x_positions = []
y_positions = []
auxY = []
auxX = []
lastTime = 0
for i in range(len(timeElapsed)):
    
    if(timeElapsed[i] != lastTime):
        x_positions.append(auxX)
        y_positions.append(auxY)
        auxY = []
        auxX = []
        lastTime = timeElapsed[i]
        seconds.append(lastTime)
    
    auxY.append(raw_y_positions[i])
    auxX.append(raw_x_positions[i]) 

       

# Crear la figura y el eje
fig, ax = plt.subplots()

writers = animation.FFMpegWriter(fps=60)



# Crear la animación
anim = fa(fig, animate, frames=len(seconds), fargs=(x_positions,y_positions,seconds, ax), interval=1, blit=False)

# Mostrar la animación en pantalla
# plt.show()

# Guardar la animación como GIF (opcional)
# anim.save('./output_animation_system_with_obstacle.mp4', writer=writers)
anim.save('./output/simulation.mp4', writer=writers)

# anim.save('./output_animation_system_with_big_particle.mp4', writer=writers)



