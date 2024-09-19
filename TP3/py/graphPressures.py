import os
import numpy as np
import matplotlib.pyplot as plt

# Directorio base
pressureOnWallsFile = "../Times/pressuresOnWalls_1.5s.txt"

pressuresOnWall = np.array([])
times = np.array([])
time = 1
interval = 0.05
with open(pressureOnWallsFile, 'r') as file:
    lines = file.readlines()
    for l in lines:
        if l != '0.0\n':
            pressuresOnWall = np.append(pressuresOnWall,float(l))
            times = np.append(times,interval*time)
            time += 1

# Directorio base
pressureOnObjectFile= "../Times/pressuresOnObject_1.5s.txt"

pressuresOnObject = np.array([])
with open(pressureOnObjectFile, 'r') as file:
    lines = file.readlines()
    for l in lines:
        if l != '0.0\n':
            pressuresOnObject = np.append(pressuresOnObject,float(l))
            

print(pressuresOnObject)
print(pressuresOnWall)

mean_wall_pressure = np.mean(pressuresOnWall)
mean_object_pressure = np.mean(pressuresOnObject)


# Create the figure and axis
fig, ax = plt.subplots()

ax.set_xlim(0, 1.5)
ax.set_ylim(130000,230000)

# ax.set_yticks(np.arange(530000, 800001, 50000))


# Plot pressure on wall (blue line)
ax.plot(times, pressuresOnWall, color='red', label='Presion sobre pared',zorder=2)

# Plot pressure on obstacle (orange line)
ax.plot(times, pressuresOnObject, color='blue', label='Presion sobre obstaculo',zorder=0)

ax.axhline(mean_wall_pressure, color='red', linestyle='--', label=f'Presion media del objeto', zorder=1)

ax.axhline(mean_object_pressure, color='blue', linestyle='--', label=f'Presion media de la pared', zorder=1)


# Add labels and title
ax.set_xlabel('Tiempo (s)', fontsize=12)
ax.set_ylabel('Presión $(Pa \\cdot m)$', fontsize=12)

# Add a legend
ax.legend(loc='upper left')

# Add grid
ax.grid(True)

# Format the y-axis with scientific notation
ax.ticklabel_format(style='sci', axis='y', scilimits=(0, 0))

# # Diccionario para almacenar los vectores de promedios de Va para cada N
# promedios_Va = {N: [] for N in Ns}
        
# # Diccionario para almacenar los vectores de promedios de Va para cada N
# promedios_Va = {N: [] for N in Ns}

# Paso 2: Calcular la línea de regresión lineal
# numpy.polyfit(x, y, 1) devuelve los coeficientes (pendiente, intercepto)
# m, b = np.polyfit(times, pressures, 1)  # m es la pendiente, b es el intercepto

# # Paso 3: Graficar los puntos de datos y la línea de regresión
# plt.grid(True,zorder=0)
# plt.scatter(times, pressures, color='blue', label='Datos',zorder=2)  # Graficar puntos
# plt.plot(times, m*times + b, color='red', label='Línea de regresión')  # Graficar línea

# # Graficar Va vs eta para cada N con desvio
# # plt.figure(figsize=(10, 6))
# # # for N in Ns:
# # plt.errorbar(times,pressures,fmt='o',capsize=5,color='red')


# # Graficar Va vs eta para cada N
# # plt.figure(figsize=(10, 6))
# # for N in Ns:
# #     plt.plot(etas, promedios_Va[N], marker='o', linestyle='-', label=f'N={N}')

# plt.xlabel('Tiempo (s)',fontsize=16)
# plt.ylabel('Presion',fontsize=16)
# plt.legend()
plt.show()