import matplotlib.pyplot as plt

file_names = ['Va_values_100.txt', 'Va_values_200.txt', 'Va_values_300.txt', 'Va_values_400.txt', 'Va_values_500.txt']

# Leer los valores de Va desde el archivo
va_values = []
with open('TP2/Va_values_100.txt', 'r') as file:
    for line in file:
        va_values.append(float(line.strip()))

# Crear los valores de t (0, 1, 2, ...)
t_values = list(range(len(va_values)))

va2 = []
with open('TP2/Va_values_200.txt', 'r') as file:
    for line in file:
        va2.append(float(line.strip()))
        
va3 = []
with open('TP2/Va_values_300.txt', 'r') as file:
    for line in file:
        va3.append(float(line.strip()))

va4 = []
with open('TP2/Va_values_400.txt', 'r') as file:
    for line in file:
        va4.append(float(line.strip()))

# Graficar Va vs t
plt.figure(figsize=(10, 6))
plt.plot(t_values, va_values, marker='o', linestyle='-', color='b')
plt.plot(t_values, va2, marker='+', linestyle='-', color='r')
plt.plot(t_values, va3, marker='x', linestyle='-', color='g')
plt.plot(t_values, va4, marker='*', linestyle='-', color='y')
plt.xlabel('t')
plt.ylabel('Va')
plt.title('Gráfico de Va vs t')
plt.grid(True)
plt.show()