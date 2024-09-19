import numpy as np
import matplotlib.pyplot as plt

vis = [1,3,6,10]
temperatura = []
presiones_obstaculo = []
presiones_paredes = []



for vi in vis:
    print(vi*vi)
    temperatura.append(vi*vi)
    
    aux = "../Times/pressuresOnObject_"+str(vi)+".txt"
    with open(aux, 'r') as file:
        lines = file.readlines()
        pressures = []
        for l in lines:
            pressures = np.append(pressures,float(l))
        presiones_obstaculo.append(pressures)

    aux = "../Times/pressuresOnWalls_"+str(vi)+".txt"
    with open(aux, 'r') as file:
        lines = file.readlines()
        pressures = []
        for l in lines:
            pressures = np.append(pressures,float(l))
        presiones_paredes.append(pressures)
            

promedio_presion_paredes = []  # Presión (Pa·m)
promedio_presion_obstaculo = [] # Presión (Pa·m)

error_paredes = []
error_obstaculo = []

for i in presiones_paredes:
    aux = 0
    for p in i:
        aux += p
    promedio_presion_paredes.append(aux/len(i))
    error_paredes.append(np.std(i))


for i in presiones_obstaculo:
    aux = 0
    for p in i:
        aux += p
    promedio_presion_obstaculo.append(aux/len(i))
    error_obstaculo.append(np.std(i))

# Crear la figura y los ejes
fig, ax = plt.subplots()

# Graficar los puntos con barras de error para las paredes
ax.errorbar(temperatura, promedio_presion_paredes, yerr=error_paredes, fmt='o', color='blue',
            label='Presión promedio de las paredes', capsize=5, markersize=8)

# Graficar los puntos con barras de error para el obstáculo
ax.errorbar(temperatura, promedio_presion_obstaculo, yerr=error_obstaculo, fmt='*', color='red',
            label='Presión promedio del obstáculo', capsize=5, markersize=10)

# Etiquetas de los ejes
ax.set_xlabel('Temperatura (v0^2)', fontsize=12)
ax.set_ylabel('Presión $(Pa \\cdot m)$', fontsize=12)

# Añadir la leyenda
ax.legend()

# Añadir una cuadrícula
ax.grid(True)

# Formato del eje Y en notación científica
ax.ticklabel_format(style='sci', axis='y', scilimits=(0, 0))

# Mostrar el gráfico
plt.show()