import matplotlib.pyplot as plt
import pandas as pd
import matplotlib.animation as animation
from matplotlib.patches import Circle

# Configuración de campo de juego
largo_campo = 100
ancho_campo = 70
radio = 0.2

# Leer archivo CSV
file_path = '../output/output.csv'  # Reemplaza con la ruta de tu archivo
df = pd.read_csv(file_path, delimiter=';')

# Separar las posiciones y velocidades de cada jugador en listas por cada instante de tiempo
time_steps = df['tiempo'].unique()
players_data = {time: df[df['tiempo'] == time] for time in time_steps}

# Configuración de la animación
fig, ax = plt.subplots()
ax.set_xlim(0, largo_campo)
ax.set_ylim(0, ancho_campo)
ax.set_aspect('equal')
ax.set_title("Simulación de Movimiento de Jugadores")
ax.set_xlabel("X (m)")
ax.set_ylabel("Y (m)")

# Función para actualizar la animación en cada cuadro
def animate(i):
    count = 0
    ax.clear()
    ax.set_xlim(0, largo_campo)
    ax.set_ylim(0, ancho_campo)
    
    # Extraer datos de jugadores en el instante actual
    current_time = time_steps[i]
    current_data = players_data[current_time]

    # Dibujar jugadores
    for idx, player in current_data.iterrows():
        aux = count % 16
        color = 'red' if aux == 0 else 'blue'  # Rojo para el jugador rojo, azul para los azules
        circle = Circle((player['x'], player['y']), radio, color=color)  # Radio 1 (ajustar según sea necesario)
        ax.add_patch(circle)
        count += 1
        
    # Mostrar el tiempo actual en la animación
    ax.text(0.5, 1.05, f'Tiempo: {current_time:.2f} s', ha='center', transform=ax.transAxes)

# Crear animación
ani = animation.FuncAnimation(fig, animate, frames=len(time_steps), interval=100)

# Guardar animación (opcional)
ani.save('simulacion.mp4', writer='ffmpeg', fps=10)
plt.show()