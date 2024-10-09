import numpy as np
import matplotlib.pyplot as plt

# Lists to store ECM values
gear_ecm = []
verlet_ecm = []
beeman_ecm = []

# Timesteps for log scale
timesteps = [1e-6, 1e-5, 1e-4, 1e-3, 1e-2]

# File names (or paths) assumed
files = ["../outputs/output_0.txt", "../outputs/output_1.txt", "../outputs/output_2.txt", "../outputs/output_3.txt", "../outputs/output_4.txt"]

# Loop through each file
for file in files:
    print(file)
    with open(file, 'r') as file:

        verlet = np.array([])
        beeman = np.array([])
        analytic = np.array([])
        gear = np.array([])
        times = np.array([])

        lines = file.readlines()
        for l in lines:
            data = l.split(':')

            times = np.append(times,float(data[4]))
            beeman = np.append(beeman,float(data[0]))
            verlet = np.append(verlet,float(data[1]))
            gear = np.append(gear,float(data[2]))
            analytic = np.append(analytic,float(data[3]))

        # Calculate Mean Squared Error for each method (excluding last point)
        mse_gear = ((gear - analytic)**2).mean(axis=0)
        mse_beeman = ((beeman - analytic)**2).mean(axis=0)
        mse_verlet = ((verlet - analytic)**2).mean(axis=0)

        # Append the MSE values to the corresponding lists
        gear_ecm.append(mse_gear)
        beeman_ecm.append(mse_beeman)
        verlet_ecm.append(mse_verlet)
        print(file)
    

# Print ECM for one method to check
# print("Verlet ECM for largest timestep:", verlet_ecm[4])

# Plotting the results
fig, ax = plt.subplots()

gear_ecm = gear_ecm[::-1]
beeman_ecm = beeman_ecm[::-1]
verlet = verlet[::-1]



error = [1, 2, 3, 4, 5]
ticks = ['$10^{-6}$', '$10^{-5}$', '$10^{-4}$', '$10^{-3}$', '$10^{-2}$']

# Plot each method with different markers/colors and lines
ax.scatter(error, gear_ecm, color='blue', label='GEAR_PREDICTOR_CORRECTOR', zorder=3)
ax.plot(error, gear_ecm, color='blue', zorder=3)

ax.scatter(error, verlet_ecm, color='orange', label='VERLET', zorder=2)
ax.plot(error, verlet_ecm, color='orange', zorder=2)

ax.scatter(error, beeman_ecm, color='green', label='BEEMAN', zorder=1)
ax.plot(error, beeman_ecm, color='green', zorder=1)

# Set x and y scale to log
# ax.set_xscale('log')
ax.set_yscale('log')

# Set the axis labels
ax.set_xlabel('Timestep (s)', fontsize=12)
ax.set_ylabel('ECM (m^2)', fontsize=12)

plt.xticks(error, labels=ticks)

# Add a legend to differentiate between the methods
ax.legend(loc='upper center')

# Add a grid for better visualization
ax.grid(True)

# Show the plot
plt.show()
