import os
import numpy as np
import matplotlib.pyplot as plt

# Directorio base
outputFile = "../outputs/output_0.txt"

beeman = np.array([])
verlet = np.array([])
gear = np.array([])
analytical = np.array([])
times = np.array([])

with open(outputFile, 'r') as file:
    lines = file.readlines()
    for l in lines:
        data = l.split(':')
        beeman = np.append(beeman,float(data[0]))
        verlet = np.append(verlet,float(data[1]))
        gear = np.append(gear,float(data[2]))
        analytical = np.append(analytical,float(data[3]))
        times = np.append(times,float(data[4]))



# Create the plot
fig, ax = plt.subplots()

# Plot each method with different markers/colors
ax.scatter(times, gear, color='blue', label='GEAR_PREDICTOR_CORRECTOR', s=10, zorder=3)
ax.scatter(times, verlet, color='orange', label='VERLET', s=10, zorder=2)
ax.scatter(times, beeman, color='green', label='BEEMAN', s=10, zorder=1)
ax.scatter(times, analytical, color='red', label='Solución Analítica',s=10, zorder=0)

# Set the axis labels
ax.set_xlabel('Tiempo (s)', fontsize=12)
ax.set_ylabel('Posición (m)', fontsize=12)

# Add a legend to differentiate between the methods
ax.legend(loc='upper right')

# Add a grid
ax.grid(True)

# Set limits for better visualization (optional)
# ax.set_xlim(0, 5)
# ax.set_ylim(-1, 1)

# Show the plot
plt.show()