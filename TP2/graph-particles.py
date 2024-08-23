import matplotlib.pyplot as plt
import os
from PIL import Image

def plot_particle_interactions(particles, M, L,file_name):
    # Create a figure and axis
    fig, ax = plt.subplots()
    stripped_name = file_name.replace('.txt', '')
    # Draw grid
    for i in range(M + 1):
        ax.axhline(i * (L / M), color='gray', linewidth=0.5)
        ax.axvline(i * (L / M), color='gray', linewidth=0.5)

    x_coords = [p[0] for p in particles]
    y_coords = [p[1] for p in particles]
    
    plt.scatter(x_coords, y_coords, color='blue')

    # Customize the plot
    plt.xlim(0, L)
    plt.ylim(0, L)
    plt.title("Particles and Neighbors")
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.gca().set_aspect('equal', adjustable='box')
    plt.savefig(f'./TP2/graphs/{stripped_name}.png')
    plt.close()

file_name = "./TP2/dynamic_CIM_input.txt"

radius = 0.25
particles = []
times = []
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

plot_particle_interactions(particles, 10, 10, "particles_time_0.txt")

directory_path = './TP2/times'
file_names = os.listdir(directory_path)

for file_name in file_names:
    with open("./TP2/times/"+file_name,'r') as file:
        particles = []
        lines = file.readlines()
        id = 1
        t = 0
        for l in lines:
            pos = l.split(":")
            x = float(pos[1])
            y = float(pos[2])
            part = [x,y]
            particles.append(part)
        times.append(particles)
        t+= 1;
        file.close()

# for i in range(len(times)):
#     plot_particle_interactions(times[i], 10, 10, file_names[i])



frames = []
for i in range(252):
    frames.append(f"./TP2/graphs/particles_time_{i}.png")
images = [Image.open(frame) for frame in frames]

images[0].save(
    'output.gif',
    save_all=True,
    append_images=images[1:],
    duration=200,  # 200ms per frame
    loop=0,
    optimize=True  # Optional: reduces the file size
)