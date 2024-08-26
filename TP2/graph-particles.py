import matplotlib.pyplot as plt
import os
import math 
from PIL import Image

speed = 0.03

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
    new_x_coords = [math.cos(p[2])*speed for p in particles]
    new_y_coords = [math.sin(p[2])*speed for p in particles]
    
    
    # plt.scatter(x_coords, y_coords, color='blue')
    plt.quiver(x_coords,y_coords, new_x_coords  ,new_y_coords  ,color='black',angles='xy')

    # Customize the plot
    plt.xlim(0, L)
    plt.ylim(0, L)
    plt.title("Particles and Neighbors")
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.gca().set_aspect('equal', adjustable='box')
    #plt.savefig('./TP2/graphs/eta1/Density_100/'+stripped_name+'.png')
    #plt.close()
    plt.savefig("simulation.png")
    plt.show()


radius = 0.25
particles = []
times = []


# plot_particle_interactions(particles, 10, 10, "particles_time_0.txt")

directory_path = './TP2/times/eta1/Density_100'
file_names = os.listdir(directory_path)


for file_name in file_names:
    with open("./TP2/times/eta1/Density_100/"+file_name,'r') as file:
        particles = []
        lines = file.readlines()
        for l in lines:
            pos = l.split(":")
            x = float(pos[1])
            y = float(pos[2])
            theta = float(pos[3])
            # new_y = float(pos[6])
            part = [x,y,theta]
            particles.append(part)
        times.append(particles)
        file.close()

for i in range(len(times)):
    plot_particle_interactions(times[i], 100, 20, file_names[i])



# frames = []
# for i in range(1,252):
#     frames.append(f"./graphs/eta1/Density_100/particles_time_{i}.png")
# images = [Image.open(frame) for frame in frames]

# images[0].save(
#     'output.gif',
#     save_all=True,
#     append_images=images[1:],
#     duration=200,  # 200ms per frame
#     loop=0,
#     optimize=True  # Optional: reduces the file size
# )