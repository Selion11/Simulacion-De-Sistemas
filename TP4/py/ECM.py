import numpy as np
import matplotlib.pyplot as plt
import pandas as pd


# Lists to store ECM values
gear_mse = []
beeman_mse = []
verlet_mse = []

# File names (or paths) assumed
files = ["../outputs/output_4.csv", "../outputs/output_3.csv", "../outputs/output_2.csv","../outputs/output_1.csv","../outputs/output_0.csv"]


error = [1, 2, 3, 4, 5]
ticks = ['$10^{-6}$', '$10^{-5}$', '$10^{-4}$', '$10^{-3}$', '$10^{-2}$']

for file in files:

    df = pd.read_csv(file, delimiter=';')

    # Access each column as a Pandas Series
    times = df['time'].tolist()
    analytic = df['analytic'].tolist()
    gear = df['gear'].tolist()
    beeman = df['beeman'].tolist()
    verlet = df['verlet'].tolist()

    mse_gear = ((np.array(gear[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)
    mse_beeman = ((np.array(beeman[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)
    mse_verlet = ((np.array(verlet[:-1]) - np.array(analytic[1:]))**2).mean(axis=0)


    gear_mse.append(mse_gear)
    beeman_mse.append(mse_beeman)
    verlet_mse.append(mse_verlet)


plt.scatter(error, gear_mse, color='red', label="Gear Predict-Correct", marker='o')
plt.scatter(error, beeman_mse, color='blue', label="Beeman", marker='o')
plt.scatter(error, verlet_mse, color='green',label="Verlet", marker='o')
plt.plot(error, gear_mse,color='red')
plt.plot(error, beeman_mse,color='blue')
plt.plot(error, verlet_mse, color='green')

# Agregar etiquetas de ejes y título
plt.xlabel('Delta T (s)')
plt.xticks(error, labels=ticks)
plt.ylabel('ECM (m²)')
plt.yscale('log')
plt.grid(True)
plt.legend()
# Mostrar el gráfico
plt.show()
