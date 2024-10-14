import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

file = "TP4/positions10.0_100.0.csv"

df = pd.read_csv(file, delimiter=';')

# Access each column as a Pandas Series
t = df['time'].tolist()
y = df['y'].tolist()


# Calcular el desplazamiento en el tiempo


# Gráfico
plt.figure(figsize=(8, 6))
plt.plot(t, np.abs(y), 'b', label='|y(t)|', linewidth=2)
plt.xlim(0,100)

# Etiquetas y título
plt.xlabel('Tiempo (s)', fontsize=14)
plt.ylabel('|y| (m)', fontsize=14)
plt.title('Oscilador amortiguado', fontsize=16)


# Mostrar el gráfico
plt.grid(True)
plt.savefig('TP4/outputs/System2/AvsT.png')