import matplotlib.pyplot as plt
from matplotlib.widgets import Slider

file_name = "./TP1/dynamic_CIM_input.txt"

radius = 0.25
particles = []
vecinas = []

with open(file_name,'r') as file:
    file.readline()
    lines = file.readlines()
    for l in lines:
        pos = l.split()
        x = float(pos[0])
        y = float(pos[1])
        part = [x,y]
        particles.append(part)

file.close()


with open("./TP1/vecinas.txt",'r') as file:
    lines = file.readlines()
    for l in lines:
        vec = l.split()
        vecinas.append(vec)
        
file.close()

def plot_particle_interactions(particle_data, interactions, target_id, ir, M, L):
    # Create a figure and axis
    fig, ax = plt.subplots()

    # Draw grid
    for i in range(M + 1):
        ax.axhline(i * (L / M), color='gray', linewidth=0.5)
        ax.axvline(i * (L / M), color='gray', linewidth=0.5)


    for p in range(len(particle_data)):
        if(p == target_id):
            circle = plt.Circle((particle_data[target_id][0], particle_data[target_id][1]), radius, color='black', fill=True)
            interaction_circle = plt.Circle((particle_data[target_id][0], particle_data[target_id][1]), ir, color='blue', alpha=0.5, fill=True)
            ax.add_patch(interaction_circle)
        elif p in interactions[target_id]:
            circle = plt.Circle((particle_data[p][0], particle_data[p][1]), radius, color='yellow', fill=True)
        else:
            circle = plt.Circle((particle_data[p][0], particle_data[p][1]), radius, color='gray', fill=True)
        ax.add_patch(circle)
        ax.text(particle_data[p][0], particle_data[p][1], str(p), color='black', ha='center', va='center', fontsize=10)

    # Customize the plot
    plt.xlim(0, L)
    plt.ylim(0, L)
    plt.title("Particles and Neighbors")
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.gca().set_aspect('equal', adjustable='box')

    # Show the plot
    plt.show()
    
# ID de la partícula objetivo
ir = 8
M = 5
target_id = 12

# Llamar a la función para graficar
plot_particle_interactions(particles, vecinas, target_id, ir, M, 20)

        