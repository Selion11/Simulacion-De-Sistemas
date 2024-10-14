import os
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd


# Directorio base
outputFile = "../outputs/output_0.csv"

# beeman = np.array([])
# verlet = np.array([])
# gear = np.array([])
# analytical = np.array([])
# times = np.array([])

df = pd.read_csv(outputFile, delimiter=';')

# Access each column as a Pandas Series
times = df['time'].tolist()
analytic = df['analytic'].tolist()
gear = df['gear'].tolist()
beeman = df['beeman'].tolist()
verlet = df['verlet'].tolist()



# Create the plot
fig, ax = plt.subplots()

# # Plot each method with different markers/colors
# ax.plot(times, gear, color='blue', label='GEAR_PREDICTOR_CORRECTOR', zorder=0)
# ax.plot(times, verlet, color='orange', label='VERLET',  zorder=3)
# ax.plot(times, beeman, color='green', label='BEEMAN',  zorder=2)
# ax.plot(times, analytic, color='red', label='Solución Analítica', zorder=1)

# # Set the axis labels
# ax.set_xlabel('Tiempo (s)', fontsize=12)
# ax.set_ylabel('Posición (m)', fontsize=12)

# # Add a legend to differentiate between the methods
# ax.legend(loc='upper right')

# # Add a grid
# ax.grid(True)

# # Set limits for better visualization (optional)
# # ax.set_xlim(0, 5)
# # ax.set_ylim(-1, 1)

# # Show the plot
# plt.show()

range_1 = 110
range_2 = 112
snapshot_times = times[range_1:range_2]
snapshot_analytic = analytic[range_1:range_2]
snapshot_gear = gear[range_1:range_2]
snapshot_beeman = beeman[range_1:range_2]
snapshot_verlet = verlet[range_1:range_2]

plt.plot(snapshot_times, snapshot_analytic, label="Analitico")
plt.plot(snapshot_times, snapshot_gear, label="Gear Predict-Correct")
plt.plot(snapshot_times, snapshot_beeman, label="Beeman")
plt.plot(snapshot_times, snapshot_verlet, label="Verlet")

# Agregar etiquetas de ejes y título
ax.set_xlabel('Tiempo (s)',fontsize=12)
ax.set_ylabel('Posición (m)',fontsize=12)
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()