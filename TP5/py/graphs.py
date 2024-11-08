import os
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import math

def calculate_distance(x2, y2):
    delta_x = x2 - 100
    delta_y = y2 - 53
    return math.sqrt(delta_x ** 2 + delta_y ** 2)


#COn 90 "./TP5/output/output_15.0_1.5707963267948966.csv"
#COn 130 "./TP5/output/output_15.0_2.268928028.csv"
#COn 60 "./TP5/output/output_15.0_1.0471975511965976.csv"
#Con 110 "./TP5/output/output_15.0_1.919862177.csv"
#Con 45 "./TP5/output/output_15.0_0.7853981633974483.csv"


# Directorio base
##CAMBIAR ACA EL NOMBRE DEL PRIMERO ARCHIVO AGREGANDOLE EL NOMBRE DEL ARCHIVO DE N = 15 con el angulo optimo
outputFiles = ["./TP5/output/output_15.0_2.0943951023931953.csv","./TP5/output/output_30.0.csv",
               "./TP5/output/output_45.0.csv","./TP5/output/output_60.0.csv",
               "./TP5/output/output_75.0.csv","./TP5/output/output_90.0.csv",
               "./TP5/output/output_100.0.csv"]

# outputFiles = ["./TP5/output/output_15.0_0.7853981633974483.csv","./TP5/output/output_15.0_1.0471975511965976.csv",
#                "./TP5/output/output_15.0_1.5707963267948966.csv","./TP5/output/output_15.0_1.919862177.csv",
#                "./TP5/output/output_15.0_2.0943951023931953.csv","./TP5/output/output_15.0_2.268928028.csv"]


totalData = {}
proportions = {}
distanceRun = {}
angle_values = [45,60,90,110,120,130]
nvalues = [15,30,45,60,75,90,100]
distanceStdDev = {}

for i in range(len(outputFiles)):
    file = outputFiles[i]
    n_value = nvalues[i]

    df = pd.read_csv(file, delimiter=';')
    grupos_por_run = {run: grupo for run, grupo in df.groupby('run')}
    totalData[n_value] = grupos_por_run

    tackled_true_count = 0
    distances = []  # Lista para almacenar las distancias de cada run

    for run, data in grupos_por_run.items():
        tackled_true_count += data['tackled'].sum()

        tackled_positions = data[data['tackled'] == True]
        for _, row in tackled_positions.iterrows():
            distance = calculate_distance(row['x'], row['y'])
            distances.append(distance)

    proportion_tackled_true = tackled_true_count / len(grupos_por_run)
    proportions[n_value] = 1 - proportion_tackled_true
    
    # Cálculo de la distancia promedio y del desvío estándar para este n_value
    avg_distance = np.mean(distances)
    std_dev_distance = np.std(distances, ddof=1)  # Desvío estándar muestral

    distanceRun[n_value] = avg_distance
    distanceStdDev[n_value] = std_dev_distance  # Almacena el desvío estándar para el gráfico
    
    
def plotTryPercent(proportions,labels=None):
    plt.figure(figsize=(10, 6))
    x_values = list(proportions.keys())
    y_values = list(proportions.values())
    
    # Scatter plot and line plot
    plt.scatter(x_values, y_values, color='blue')
    plt.plot(x_values, y_values, color='blue', linestyle='--', linewidth=1)
    
    # Annotate each point with its value and label
    for i, (x, y) in enumerate(zip(x_values, y_values)):
        label_text = f'{labels[i]}: ({y:.2f}%)' if labels else f'({y:.2f}%)'
        plt.text(x, y, label_text, ha='right', va='bottom', fontsize=10, color='black')
    
    # Labels and title
    plt.xlabel('Cantidad de oponentes', fontsize=14)
    plt.ylabel('Llegadas al ingoal [%]', fontsize=14)
    plt.xticks(x_values, rotation=45)  # Rotate x-axis labels for readability

    # Show the plot
    plt.tight_layout()
    plt.savefig("plotTryAngles.png")  # Save plot
    # plt.show()
    
def plotAvgDistance(distanceRun, distanceStdDev):
    plt.figure(figsize=(10, 6))
    
    # Convert dict keys and values to lists
    x_values = list(distanceRun.keys())
    y_values = list(distanceRun.values())
    y_err_values = list(distanceStdDev.values())
    
    # Plot the average distance with error bars representing the standard deviation
    plt.errorbar(
        x_values, 
        y_values, 
        yerr=y_err_values, 
        fmt='o', color='blue', capsize=5, label='Distancia promedio ± Desvío estándar'
    )

    plt.xlabel('Cantidad de oponentes', fontsize=14)    
    plt.ylabel('Distancia [m]', fontsize=14)
    plt.xticks(x_values, rotation=45)
    plt.legend()
    plt.tight_layout()
    plt.savefig("plotDistPlayers.png")  # Save the plot
    # plt.show()
    

#plotTryPercent(proportions)
plotAvgDistance(distanceRun, distanceStdDev)
