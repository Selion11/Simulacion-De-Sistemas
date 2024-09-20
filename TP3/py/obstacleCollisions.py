import matplotlib.pyplot as plt
import numpy as np

# Función para leer los datos desde un archivo y devolver listas de tiempos, colisiones únicas y colisiones totales
def leer_datos(ruta_archivo):
    tiempos = []
    colisiones_unicas = []
    colisiones_totales = []
    with open(ruta_archivo, 'r') as archivo:
        for linea in archivo:
            datos = linea.strip().split(",")  # Dividimos por coma
            colisiones_totales.append(int(datos[0]))  # Primer valor: colisiones totales
            colisiones_unicas.append(int(datos[1]))  # Segundo valor: colisiones únicas
            tiempos.append(float(datos[2]))  # Tercer valor: tiempo
    return tiempos, colisiones_unicas, colisiones_totales


# Función para calcular la pendiente (m) usando regresión lineal
def calcular_pendiente_regresion_lineal(tiempos, colisiones_totales):
    N = len(tiempos)
    sum_xy = sum(t * c for t, c in zip(tiempos, colisiones_totales))
    sum_x = sum(tiempos)
    sum_y = sum(colisiones_totales)
    sum_x2 = sum(t ** 2 for t in tiempos)

    # Fórmula para calcular la pendiente (m)
    m = (N * sum_xy - sum_x * sum_y) / (N * sum_x2 - (sum_x) ** 2)
    return m


# Función para encontrar el tiempo cuando las colisiones únicas alcanzan un valor específico (por ejemplo, 125)
def encontrar_tiempo_para_colisiones(tiempos, colisiones, umbral):
    for i in range(len(colisiones)):
        if colisiones[i] >= umbral:
            return tiempos[i]
    return None  # Si no se alcanzan las colisiones especificadas

# Función para encontrar las colisiones únicas y totales en t >= 0.5 segundos
def obtener_colisiones_en_t_05(tiempos, colisiones_unicas, colisiones_totales):
    for i in range(len(tiempos)):
        if tiempos[i] >= 0.5:
            return colisiones_unicas[i], colisiones_totales[i]
    return None, None

# Leer datos de los 4 archivos para cada velocidad inicial
v0_1_tiempos, v0_1_colisiones_unicas, v0_1_colisiones_totales = leer_datos('..\Times\obstacle_collision_count_v0_1.txt')
v0_3_tiempos, v0_3_colisiones_unicas, v0_3_colisiones_totales = leer_datos('..\Times\obstacle_collision_count_v0_3.txt')
v0_6_tiempos, v0_6_colisiones_unicas, v0_6_colisiones_totales = leer_datos('..\Times\obstacle_collision_count_v0_6.txt')
v0_10_tiempos, v0_10_colisiones_unicas, v0_10_colisiones_totales = leer_datos('..\Times\obstacle_collision_count_v0_10.txt')

### Primeras colisiones vs tiempo (partículas que chocaron por primera vez vs tiempo)
plt.figure(figsize=(10, 6))
plt.plot(v0_1_tiempos, v0_1_colisiones_unicas, label='v0 = 1 m/s', marker='o', linestyle='-')
plt.plot(v0_3_tiempos, v0_3_colisiones_unicas, label='v0 = 3 m/s', marker='s', linestyle='-')
plt.plot(v0_6_tiempos, v0_6_colisiones_unicas, label='v0 = 6 m/s', marker='^', linestyle='-')
plt.plot(v0_10_tiempos, v0_10_colisiones_unicas, label='v0 = 10 m/s', marker='x', linestyle='-')

plt.xlabel('Tiempo (s)')
plt.ylabel('# de primeras colisiones')
plt.title('Primeras colisiones vs Tiempo')
plt.xlim(0, 0.8)
plt.legend()
plt.grid(True)
plt.show()


# Calcular la pendiente (m) para cada velocidad
m_v0_1 = calcular_pendiente_regresion_lineal(v0_1_tiempos, v0_1_colisiones_totales)
m_v0_3 = calcular_pendiente_regresion_lineal(v0_3_tiempos, v0_3_colisiones_totales)
m_v0_6 = calcular_pendiente_regresion_lineal(v0_6_tiempos, v0_6_colisiones_totales)
m_v0_10 = calcular_pendiente_regresion_lineal(v0_10_tiempos, v0_10_colisiones_totales)

# Crear listas de valores de x (v0^2) y y (colisiones por unidad de tiempo, que es m)
x_vals_colisiones = [v0_1_cuadrado, v0_3_cuadrado, v0_6_cuadrado, v0_10_cuadrado]
y_vals_colisiones = [m_v0_1, m_v0_3, m_v0_6, m_v0_10]

# Crear el gráfico de colisiones por unidad de tiempo vs v0^2
plt.figure(figsize=(10, 6))
plt.scatter(x_vals_colisiones, y_vals_colisiones, color='b')

# Etiquetas de los ejes y título
plt.xlabel(r'$v_0^2$')
plt.ylabel('Colisión con Obstáculo por unidad de tiempo')
plt.title('Colisión con Obstáculo por unidad de tiempo con Temperatura variable')

# Añadir una cuadrícula para mayor visibilidad
plt.grid(True)

# Mostrar el gráfico en pantalla
plt.show()


### Colisiones totales vs tiempo
plt.figure(figsize=(10, 6))
plt.plot(v0_1_tiempos, v0_1_colisiones_totales, label='v0 = 1 m/s', marker='o', linestyle='none')
plt.plot(v0_3_tiempos, v0_3_colisiones_totales, label='v0 = 3 m/s', marker='s', linestyle='none')
plt.plot(v0_6_tiempos, v0_6_colisiones_totales, label='v0 = 6 m/s', marker='^', linestyle='none')
plt.plot(v0_10_tiempos, v0_10_colisiones_totales, label='v0 = 10 m/s', marker='x', linestyle='none')

plt.xlabel('Tiempo (s)')
plt.ylabel('# de colisiones totales')
plt.title('Colisiones Totales vs Tiempo')
plt.xlim(0, 0.8)
plt.legend()
plt.grid(True)
plt.show()

###Tiempo en llegar al 50% (125 colisiones únicas) con temperatura variable
# Calcular v0^2 para cada velocidad
v0_1_cuadrado = 1**2
v0_3_cuadrado = 3**2
v0_6_cuadrado = 6**2
v0_10_cuadrado = 10**2

# Encontrar los tiempos cuando se alcanzan 125 colisiones únicas
v0_1_tiempo_125 = encontrar_tiempo_para_colisiones(v0_1_tiempos, v0_1_colisiones_unicas, 125)
v0_3_tiempo_125 = encontrar_tiempo_para_colisiones(v0_3_tiempos, v0_3_colisiones_unicas, 125)
v0_6_tiempo_125 = encontrar_tiempo_para_colisiones(v0_6_tiempos, v0_6_colisiones_unicas, 125)
v0_10_tiempo_125 = encontrar_tiempo_para_colisiones(v0_10_tiempos, v0_10_colisiones_unicas, 125)

# Crear listas para los valores de x (v0^2) y y (tiempos para alcanzar 125 colisiones)
x_vals_125 = [v0_1_cuadrado, v0_3_cuadrado, v0_6_cuadrado, v0_10_cuadrado]
y_vals_125 = [v0_1_tiempo_125, v0_3_tiempo_125, v0_6_tiempo_125, v0_10_tiempo_125]

plt.figure(figsize=(10, 6))
plt.scatter(x_vals_125, y_vals_125, color='b', label='Tiempo para 125 colisiones únicas')
plt.xlabel(r'$v_0^2$')
plt.ylabel('Tiempo (s)')
plt.title('Tiempo en llegar al 50% de colisiones únicas con Temperatura variable')
plt.grid(True)
plt.show()

### Gráfico de barras (colisiones únicas vs totales en t=0.5 segundos)
# Obtener colisiones únicas y totales en t >= 0.5 segundos para cada velocidad
v0_1_colision_unicas, v0_1_total_colisiones = obtener_colisiones_en_t_05(v0_1_tiempos, v0_1_colisiones_unicas, v0_1_colisiones_totales)
v0_3_colision_unicas, v0_3_total_colisiones = obtener_colisiones_en_t_05(v0_3_tiempos, v0_3_colisiones_unicas, v0_3_colisiones_totales)
v0_6_colision_unicas, v0_6_total_colisiones = obtener_colisiones_en_t_05(v0_6_tiempos, v0_6_colisiones_unicas, v0_6_colisiones_totales)
v0_10_colision_unicas, v0_10_total_colisiones = obtener_colisiones_en_t_05(v0_10_tiempos, v0_10_colisiones_unicas, v0_10_colisiones_totales)

# Crear los datos para el gráfico de barras
velocidades = ['v0 = 1 m/s', 'v0 = 3 m/s', 'v0 = 6 m/s', 'v0 = 10 m/s']
colisiones_unicas_t_05 = [v0_1_colision_unicas, v0_3_colision_unicas, v0_6_colision_unicas, v0_10_colision_unicas]
colisiones_totales_t_05 = [v0_1_total_colisiones, v0_3_total_colisiones, v0_6_total_colisiones, v0_10_total_colisiones]

# Crear el gráfico de barras
plt.figure(figsize=(10, 6))
bar_width = 0.5
x = np.arange(len(velocidades))  # Posiciones en el eje X

plt.bar(x, colisiones_totales_t_05, width=bar_width, label='Colisiones Totales', color='b')
plt.bar(x, colisiones_unicas_t_05, width=bar_width, label='Colisiones Únicas', color='r')

# Añadir etiquetas y leyenda
plt.xlabel('Velocidad Inicial (v0)')
plt.ylabel('Cantidad de Colisiones')
plt.title('Colisiones Únicas y Totales en t >= 0.5 segundos')
plt.xticks(x, velocidades)
plt.legend()
plt.grid(True)
plt.show()
