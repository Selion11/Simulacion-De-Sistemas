import matplotlib.pyplot as plt
import os
import math
import numpy as np
import matplotlib.animation as animation
from matplotlib.animation import FuncAnimation as fa
from matplotlib.patches import Circle
import pandas as pd


# L = 0.001
# N = 100

# def plot_particle_interactions(particles, ax):

#     ax.clear()

#     for p in particles:
#         print(p[0])
#         circle = Circle((p[0], p[1]), L, edgecolor='blue', facecolor='lightblue')  # Create a circle
#         ax.add_patch(circle)  # Add the circle to the plot
    
    

#     # Customize the plot
#     # ax.set_xlim(0, 0.1)
#     ax.set_ylim(-2, 2)
#     # ax.set_xticks(np.arange(0, 0.12, 0.02)) 
#     ax.set_xlabel("Posicion (m)")
#     ax.set_ylabel("Amplitud (m)")
#     ax.set_aspect('equal', adjustable='box')

# def animate(i, times, ax):
#     # print(times[i])
#     plot_particle_interactions(times[i], ax)
#     ax.text(0.5, 1.05, f'Tiempo: {seconds[i]}', 
#         horizontalalignment='center', verticalalignment='center', 
#         transform=ax.transAxes, fontsize=12)

# # Lee los datos
# file = "../outputs/positions10.0_100.0.csv"
# # directory_path = "../times/system_with_big_particle.txt"

# times = []
# particles = []

# df = pd.read_csv(file, delimiter=';')

# # Access each column as a Pandas Series
# timeElapsed = df['time'].tolist()
# x_positions = df['x'].tolist()
# y_positions = df['y'].tolist()

# print

# seconds = []
# times = []
# auxTimes = []
# lastTime = 0
# for i in range(len(timeElapsed)):
    
#     if(timeElapsed[i] != lastTime):
#         times.append(auxTimes)
#         auxTimes = []
#         lastTime = timeElapsed[i]
#         seconds.append(lastTime)


#     aux = []
    
#     aux.append(x_positions[i])
#     aux.append(y_positions[i])
#     aux.append(timeElapsed[i])
#     auxTimes.append(aux)


       

# # Crear la figura y el eje
# fig, ax = plt.subplots()

# writers = animation.FFMpegWriter(fps=60)


# # Crear la animación
# anim = fa(fig, animate, frames=len(times), fargs=(times, ax), interval=1, blit=False)

# # Mostrar la animación en pantalla
# plt.show()

# # Guardar la animación como GIF (opcional)
# # anim.save('./output_animation_system_with_obstacle.mp4', writer=writers)
# anim.save('./simulation.mp4', writer=writers)

# # anim.save('./output_animation_system_with_big_particle.mp4', writer=writers)



import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Supongamos que ya tienes los siguientes vectores con datos:
# x_positions -> Una lista de listas, cada sublista contiene las posiciones 'x' de las partículas en cada instante de tiempo.
# y_positions -> Similar a x_positions, pero con las coordenadas 'y'.
# times -> Vector que contiene los tiempos correspondientes a cada posición.




# # Lee los datos
file = "../outputs/positions10.0_100.0.csv"
# directory_path = "../times/system_with_big_particle.txt"

times = []
particles = []

df = pd.read_csv(file, delimiter=';')

# # Access each column as a Pandas Series
timeElapsed = df['time'].tolist()
raw_x_positions = df['x'].tolist()
raw_y_positions = df['y'].tolist()

# Aquí un ejemplo sintético (debes reemplazar esto con tus datos reales)
n_particles = 100  # Número de partículas
n_frames = len(timeElapsed)  # Número de tiempos

# print

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

# # Generar datos sintéticos como ejemplo (debes cargar tus datos reales aquí)
# x_positions = [np.random.rand(n_particles) for _ in range(n_frames)]
# y_positions = [np.random.rand(n_particles) for _ in range(n_frames)]
# times = np.linspace(0, 10, n_frames)  # Simulación de tiempos

# Crear el gráfico
fig, ax = plt.subplots()
ax.set_xlim(0, 0.1)  # Ajusta según los límites de tu simulación
ax.set_ylim(-5, 5)  # Ajusta según los límites de tu simulación
particles, = ax.plot([], [], 'bo')  # Inicializar las partículas como puntos azules

# Función de inicialización
def init():
    particles.set_data([], [])
    return particles,

# Función que actualiza la posición de las partículas en cada frame
def update(frame):
    x = x_positions[frame]  # Posiciones x en el frame actual
    y = y_positions[frame]  # Posiciones y en el frame actual
    particles.set_data(x, y)
    return particles,

# Crear la animación
ani = FuncAnimation(fig, update, frames=range(n_frames), init_func=init, blit=True, repeat=False)

# Mostrar la animación
plt.show()
