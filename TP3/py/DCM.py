import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import linregress

aux_1 = "../Times/DCM.txt"
aux_2 = "../Times/DCM2.txt"
aux_3 = "../Times/DCM3.txt"
aux_4 = "../Times/DCM4.txt"
aux_5 = "../Times/DCM5.txt"

dcms_1 = []
dcms_2 = []
dcms_3 = []
dcms_4 = []
dcms_5 = []
final = []

with open(aux_1, 'r') as file:
    for l in file:
        dcms_1.append(float(l))

with open(aux_2, 'r') as file:
    for l in file:
        dcms_2.append(float(l))

with open(aux_3, 'r') as file:
    for l in file:
        dcms_3.append(float(l))
        

with open(aux_4, 'r') as file:
    for l in file:
        dcms_4.append(float(l))
        

with open(aux_5, 'r') as file:
    for l in file:
        dcms_5.append(float(l))
        
# for x in range(20):
#     prom = (dcms_1[x] + dcms_2[x] + dcms_3[x] + dcms_4[x] + dcms_5[x])/5
#     final.append(prom)

t = [0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0]

print(len(t) == len(dcms_1) == len(dcms_2) == len(dcms_3) == len(dcms_4) == len(dcms_5))

for x in range(19):
    prom = (dcms_1[x] + dcms_2[x] + dcms_3[x] + dcms_4[x] + dcms_5[x])/5
    final.append(prom)

plt.plot(t, dcms_1, marker="o", color="blue", label='1')
plt.plot(t, dcms_2, marker="x", color="yellow", label='2')
plt.plot(t, dcms_3, marker="+", color="green", label='3')
plt.plot(t, dcms_4, marker="*", color="magenta", label='4')
plt.plot(t, dcms_5, marker=">", color="red", label='5')



plt.xlabel('Tiempo(s)')
plt.ylabel('Desvío Cuadrático Medio (DCM)')
plt.legend().set_title('Iteracion')

# Mostrar la gráfica
plt.savefig('DCM.png')


# slope, intercept, r_value, p_value, std_err = linregress(t, final)

# # Calculamos el coeficiente de difusión en 1D (d = 1)
# D = slope / 2   
# std_dev = np.std(final)
# sem = std_dev / np.sqrt(len(final))
# x = np.arange(len(final))

# # Graficamos los datos junto con la línea ajustada
# plt.figure(figsize=(8, 6))
# plt.errorbar(t, final,yerr=sem,fmt="o",capsize=5,linestyle="-",ecolor="black", label="Datos DCM", color="blue")
# plt.plot(t, intercept + slope * np.array(t), 'r', label=f"Ajuste lineal: y={slope:.5f}x + {intercept:.5f}")
# plt.xlabel("Tiempo (s)")
# plt.ylabel("Desplazamiento cuadrático medio (DCM)")
# plt.legend()
# plt.grid(True)
# plt.savefig('DCM_prom.png')