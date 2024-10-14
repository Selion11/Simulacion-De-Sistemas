import numpy as np
import matplotlib.pyplot as plt

omegas1 = [8.0,8.5, 9.0,9.5, 10.0,10.5, 11.0,11.5, 12.0]
omegas2 = [28.0,28.5, 29.0,29.5, 30.0,30.5, 31.0,31.5, 32.0]
omegas3 = [42.0,42.5, 43.0,43.5, 44.0,44.5, 45.0,45.5, 46.0]
omegas4 = [68.0,68.5, 69.0,69.5, 70.0,70.5, 71.0,71.5, 72.0]
omegas5 = [98.0,98.5, 99.0,99.5, 100.0,100.5, 101.0,101.5, 102.0]

omegas = []
omegas.append(omegas1)
omegas.append(omegas2)
omegas.append(omegas3)
omegas.append(omegas4)
omegas.append(omegas5)

ks = [100.0, 1000.0, 2000.0, 5000.0, 10000.0]
maxes = [10.0,31.5,44.5,70.0,99.5]

# Definir la función de error E(c)
def E(c, omega_0_k, k_values):
    return np.sum((omega_0_k - c * np.sqrt(k_values))**2)

# Valores de omega_0_k y k (puedes cambiarlos según tu caso)
k_values = np.arange(1, 11)  # Supongamos que k va de 1 a 10
omega_0_k = np.random.uniform(1, 2, len(k_values))  # Simulamos algunos valores

# Valores de c para evaluar
c_values = np.linspace(0.98, 1.00, 1000)
errors = [0]*1000

for j in range(len(ks)):      
    for i in range(len(c_values)):  
        errors[i] += E(c_values[i], maxes[j], ks[j]) 

# Encontrar el valor de c que minimiza el error
min_error_idx = np.argmin(errors)
min_c = c_values[min_error_idx]
min_error = errors[min_error_idx]

# Graficar
plt.plot(c_values, errors, 'r', label='E(c)')
plt.scatter(min_c, min_error, color='blue', label=f'Error mínimo en c={min_c:.3f}')

# Etiquetas y leyenda
plt.xlabel('c', fontsize=12)
plt.ylabel('E(c)',fontsize=12)
plt.legend()
plt.grid(True)
plt.show()
