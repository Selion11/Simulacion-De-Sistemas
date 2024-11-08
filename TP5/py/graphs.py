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
# outputFiles = ["./TP5/output/output_15.0_1.0471975511965976.csv","./TP5/output/output_30.0.csv",
#                "./TP5/output/output_45.0.csv","./TP5/output/output_60.0.csv",
#                "./TP5/output/output_75.0.csv","./TP5/output/output_90.0.csv",
#                "./TP5/output/output_100.0.csv"]

outputFiles = ["./TP5/output/output_15.0_1.5707963267948966.csv","./TP5/output/output_15.0_2.268928028.csv",
               "./TP5/output/output_15.0_1.0471975511965976.csv","./TP5/output/output_15.0_1.919862177.csv",
               "./TP5/output/output_15.0_0.7853981633974483.csv",#"./TP5/output/output_15.0_1.0471975511965976.csv"
               ]


totalData = {}
proportions = {}
distanceRun = {}
angle_values = [90,45,60,110,130,120]

for i in range(len(outputFiles)):
    file = outputFiles[i]
    # Extract 'n' value from the filename (assuming it's the last number before the file extension)
    #n_value = float(file.split('_')[-1].replace('.csv', ''))
    n_value = angle_values[i]


    # Read the CSV file
    df = pd.read_csv(file, delimiter=';')

    # Group data by 'run'
    grupos_por_run = {run: grupo for run, grupo in df.groupby('run')}

    # Store the 'grupos_por_run' dictionary under the corresponding 'n' value
    totalData[n_value] = grupos_por_run
    
    # Calculate the number of runs for this 'n'
    num_runs = len(grupos_por_run)

    # Calculate the proportion of 'Tackled' == True for this 'n'
    tackled_true_count = 0
    total_distance = 0
    for run, data in grupos_por_run.items():
        tackled_true_count += data['tackled'].sum()  # Sum of True values (True = 1, False = 0)

        # Calculate the distance for each tackled position
        tackled_positions = data[data['tackled'] == True]
        for _, row in tackled_positions.iterrows():
            total_distance += calculate_distance(row['x'], row['y'])
            
    proportion_tackled_true = tackled_true_count / num_runs
    proportions[n_value] = 1 - proportion_tackled_true  # Store the result in the dictionary
    
    # Calculate the average distance for this n value
    avg_distance = total_distance / num_runs
    distanceRun[n_value] = avg_distance
    

for n_value, runs_dict in totalData.items():
    num_runs = len(runs_dict)  # Count the number of runs for each n
    print(f"n = {n_value}: {num_runs} runs")
    
def plotTryPercent(proportions):
    plt.figure(figsize=(10, 6))
    plt.bar(proportions.keys(), proportions.values(), color='blue')

    # Add labels and title
    plt.xlabel('Jugadores enemigos')
    plt.ylabel('Llegadas al ingol [%]')
    plt.title('Promedio de llegadas al ingol')
    plt.xticks(list(proportions.keys()), rotation=45)  # Rotate x-axis labels for readability

    # Show the plot
    plt.tight_layout()
    plt.savefig("plotTry60.png")# Adjust layout to prevent clipping
    #plt.show()
    
def plotAvgDistance(distanceRun):
    plt.figure(figsize=(10, 6))
    plt.scatter(distanceRun.keys(), distanceRun.values(), color='blue', marker='o')
    
    plt.plot(list(distanceRun.keys()), list(distanceRun.values()), color='blue', linestyle='--', linewidth=1)

    # Add labels and title
    plt.xlabel('Jugadores enemigos')
    plt.ylabel('Distancia[m]')
    plt.title('Distancia promedio antes del tacle')
    plt.xticks(list(distanceRun.keys()), rotation=45)  # Rotate x-axis labels for readability

    # Show the plot
    plt.tight_layout()
    plt.savefig("plotDist60.png")# Adjust layout to prevent clipping
    # plt.show()
    
    
plotTryPercent(proportions)
plotAvgDistance(distanceRun)

