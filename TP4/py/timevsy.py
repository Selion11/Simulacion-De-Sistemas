import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

file = "../outputs/positions10.0_100.0.csv"

df = pd.read_csv(file, delimiter=';')

# Access each column as a Pandas Series
t = df['time'].tolist()
y = df['y'].tolist()


# Calcular el desplazamiento en el tiempo


# Gráfico
plt.figure(figsize=(8, 6))
plt.plot(t, np.abs(y), 'b', label='|y(t)|', linewidth=2)

# Etiquetas y título
plt.xlabel('Tiempo (s)', fontsize=14)
plt.ylabel('|y| (m)', fontsize=14)
plt.title('Oscilador amortiguado', fontsize=16)

# Parámetros en el gráfico
plt.text(102, 0.5, f'k = {100} kg/s²\nω = {10} rad/s', fontsize=12, ha='left', va='center')

# Mostrar el gráfico
plt.grid(True)
plt.show()