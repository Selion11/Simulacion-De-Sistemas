import matplotlib.pyplot as plt
from matplotlib.widgets import Slider

file_name = "./dynamic_CIM_input.txt"

radius = 0.25
particles = []
vecinas = []

with open(file_name,'r') as file:
    file.readline()
    lines = file.readlines()
    id = 1
    for l in lines:
        pos = l.split()
        x = float(pos[0])
        y = float(pos[1])
        part = [x,y,id]
        id += 1
        particles.append(part)

file.close()

with open("./vecinas.txt",'r') as file:
    lines = file.readlines()

    for l in lines:
        vec = l.split(":")
        vec_num = []
        aux = vec[1].split()
        for v in aux:
            vec_num.append(int(v))
        vecinas.append((int(vec[0]),vec_num))
file.close()

def plot_particle_interactions(particle_data, interactions, target_id, ir, M, L):
    # Create a figure and axis
    fig, ax = plt.subplots()

    # Draw grid
    for i in range(M + 1):
        ax.axhline(i * (L / M), color='gray', linewidth=0.5)
        ax.axvline(i * (L / M), color='gray', linewidth=0.5)

    aux_vec = []
    for v in interactions:
        if v[0] == target_id:
            aux_vec = v[1]
            print(aux_vec)

    for p in range(len(particle_data)):
        if(p == target_id-1):
            circle = plt.Circle((particle_data[p][0], particle_data[p][1]), radius, color='green', fill=True)
            # interaction_circle = plt.Circle((particle_data[p][0], particle_data[p][1]), ir, color='blue', alpha=0.5, fill=True)
            # ax.add_patch(interaction_circle)
        elif p+1 in aux_vec:
            circle = plt.Circle((particle_data[p][0], particle_data[p][1]), radius, color='red', fill=True)
        else:
            circle = plt.Circle((particle_data[p][0], particle_data[p][1]), radius, color='gray', fill=True)
        ax.add_patch(circle)
        ax.text(particle_data[p][0], particle_data[p][1], str(p+1), color='black', ha='center', va='center', fontsize=10)

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
ir = 5
M = 10
target_id = 85

# Llamar a la función para graficar
plot_particle_interactions(particles, vecinas, target_id, ir, M, 20)
# print("Original:",target_id, ":", particles[target_id-1])
# for v in vecinas[target_id-1]:
#     print("Vecina:",v, ":", particles[v-1])
