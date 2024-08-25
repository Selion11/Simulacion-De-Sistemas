import matplotlib.pyplot as plt

# Leer los valores de Va desde el archivo
va_values = []
with open('TP2/Va_values.txt', 'r') as file:
    for line in file:
        va_values.append(float(line.strip()))

# Crear los valores de t (0, 1, 2, ...)
t_values = list(range(len(va_values)))

# Graficar Va vs t
plt.figure(figsize=(10, 6))
plt.plot(t_values, va_values, marker='o', linestyle='-', color='b')
plt.xlabel('t')
plt.ylabel('Va')
plt.title('Gráfico de Va vs t')
plt.grid(True)
plt.show()