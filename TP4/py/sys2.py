import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

files = [   "TP4/outputs/System2/8.0_100.0.csv",
            "TP4/outputs/System2/8.5_100.0.csv",
            "TP4/outputs/System2/9.0_100.0.csv",
            "TP4/outputs/System2/9.5_100.0.csv",
            "TP4/outputs/System2/10.0_100.0.csv",
            "TP4/outputs/System2/10.5_100.0.csv",
            "TP4/outputs/System2/11.0_100.0.csv",
            "TP4/outputs/System2/11.5_100.0.csv",
            "TP4/outputs/System2/12.0_100.0.csv"]

k = 100
#omegas = [8.0, 9.0, 10.0, 11.0, 12.0]
omegas = [8.0,8.5, 9.0,9.5, 10.0,10.5, 11.0,11.5, 12.0]
amplitudes = []

for i in range(len(omegas)):
    df = pd.read_csv(files[i], delimiter=';')
    amplitudes.append(df['a'].tolist())

max_amplitudes = []
for i in range(len(omegas)):
    max_amplitudes.append(max(amplitudes[i]))

plt.plot(omegas, max_amplitudes, color='red', marker='o')
plt.xlabel('Omega [rad/s]')
plt.ylabel('Amplitude [m]')
plt.show()
    