import os
import numpy as np
import matplotlib.pyplot as plt

# Directorio base
base_dir = "TP2/times"

# Lista de valores de eta
etas = [0.1, 0.5, 0.7, 1, 1.5, 2, 2.5, 3, 3.5, 4]

# Lista de valores de N
Ns = [100, 200, 300, 400]

# Diccionario para almacenar los vectores de promedios de Va para cada N
promedios_Va = {N: [] for N in Ns}

# Rango de tiempo para calcular el promedio
t_start = 40
t_end = 250



# Lectura y procesamiento de los archivos
for eta in etas:
    eta_dir = f"eta{eta}"  # Directorio para el valor de eta
    for N in Ns:

        # Construir el nombre del archivo
        filename = os.path.join("times", eta_dir, "va", f"Va_values_{N}.txt")

        # Leer los valores de Va desde el archivo
        with open(filename, 'r') as file:
            lines = file.readlines()
            # Convertir las líneas leídas a una lista de floats
            Va_values = [float(line.strip()) for line in lines]

            # Calcular el promedio de Va desde t = 40 hasta t = 250
            Va_promedio = np.mean(Va_values[t_start-1:t_end])  # -1 porque los índices empiezan en 0

            # Guardar el promedio en el diccionario
            promedios_Va[N].append(Va_promedio)

# Graficar Va vs eta para cada N
plt.figure(figsize=(10, 6))
for N in Ns:
    plt.plot(etas, promedios_Va[N], marker='o', linestyle='-', label=f'N={N}')

plt.xlabel('η (ruido)')
plt.ylabel('Promedio de Va')
plt.legend()
plt.grid(True)
plt.show()