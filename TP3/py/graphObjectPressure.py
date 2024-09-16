import os
import numpy as np
import matplotlib.pyplot as plt

# Directorio base
directory_path= "../Times/pressuresOnObject.txt"

pressures = np.array([])
times = np.array([])
time = 1
with open(directory_path, 'r') as file:
    lines = file.readlines()
    for l in lines:
        pressures = np.append(pressures,float(l))
        times = np.append(times,0.2*time)
        time += 1



# # Diccionario para almacenar los vectores de promedios de Va para cada N
# promedios_Va = {N: [] for N in Ns}
        
# # Diccionario para almacenar los vectores de promedios de Va para cada N
# promedios_Va = {N: [] for N in Ns}

# Paso 2: Calcular la línea de regresión lineal
# numpy.polyfit(x, y, 1) devuelve los coeficientes (pendiente, intercepto)
m, b = np.polyfit(times, pressures, 1)  # m es la pendiente, b es el intercepto

# Paso 3: Graficar los puntos de datos y la línea de regresión
plt.grid(True,zorder=0)
plt.scatter(times, pressures, color='blue', label='Datos',zorder=2)  # Graficar puntos
plt.plot(times, m*times + b, color='red', label='Línea de regresión')  # Graficar línea

# Graficar Va vs eta para cada N con desvio
# plt.figure(figsize=(10, 6))
# # for N in Ns:
# plt.errorbar(times,pressures,fmt='o',capsize=5,color='red')


# Graficar Va vs eta para cada N
# plt.figure(figsize=(10, 6))
# for N in Ns:
#     plt.plot(etas, promedios_Va[N], marker='o', linestyle='-', label=f'N={N}')

plt.xlabel('Tiempo (s)',fontsize=16)
plt.ylabel('Presion',fontsize=16)
# plt.legend()
plt.show()