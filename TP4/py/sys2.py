import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# k3 = 2000
# k4 = 5000
# k5 = 10000

files100 = [   "TP4/outputs/System2/8.0_100.0.csv",
            "TP4/outputs/System2/8.5_100.0.csv",
            "TP4/outputs/System2/9.0_100.0.csv",
            "TP4/outputs/System2/9.5_100.0.csv",
            "TP4/outputs/System2/10.0_100.0.csv",
            "TP4/outputs/System2/10.5_100.0.csv",
            "TP4/outputs/System2/11.0_100.0.csv",
            "TP4/outputs/System2/11.5_100.0.csv",
            "TP4/outputs/System2/12.0_100.0.csv"]

files1000 = [   "TP4/outputs/System2/8.0_1000.0.csv",
            "TP4/outputs/System2/8.5_1000.0.csv",
            "TP4/outputs/System2/9.0_1000.0.csv",
            "TP4/outputs/System2/9.5_1000.0.csv",
            "TP4/outputs/System2/10.0_1000.0.csv",
            "TP4/outputs/System2/10.5_1000.0.csv",
            "TP4/outputs/System2/11.0_1000.0.csv",
            "TP4/outputs/System2/11.5_1000.0.csv",
            "TP4/outputs/System2/12.0_1000.0.csv"]

files2000 = [   "TP4/outputs/System2/8.0_2000.0.csv",
            "TP4/outputs/System2/8.5_2000.0.csv",
            "TP4/outputs/System2/9.0_2000.0.csv",
            "TP4/outputs/System2/9.5_2000.0.csv",
            "TP4/outputs/System2/10.0_2000.0.csv",
            "TP4/outputs/System2/10.5_2000.0.csv",
            "TP4/outputs/System2/11.0_2000.0.csv",
            "TP4/outputs/System2/11.5_2000.0.csv",
            "TP4/outputs/System2/12.0_2000.0.csv"]

files5000 = [   "TP4/outputs/System2/8.0_5000.0.csv",
            "TP4/outputs/System2/8.5_5000.0.csv",
            "TP4/outputs/System2/9.0_5000.0.csv",
            "TP4/outputs/System2/9.5_5000.0.csv",
            "TP4/outputs/System2/10.0_5000.0.csv",
            "TP4/outputs/System2/10.5_5000.0.csv",
            "TP4/outputs/System2/11.0_5000.0.csv",
            "TP4/outputs/System2/11.5_5000.0.csv",
            "TP4/outputs/System2/12.0_5000.0.csv"]


files10000 = ["TP4/outputs/System2/8.0_10000.0.csv",
            "TP4/outputs/System2/8.5_10000.0.csv",
            "TP4/outputs/System2/9.0_10000.0.csv",
            "TP4/outputs/System2/9.5_10000.0.csv",
            "TP4/outputs/System2/10.0_10000.0.csv",
            "TP4/outputs/System2/10.5_10000.0.csv",
            "TP4/outputs/System2/11.0_10000.0.csv",
            "TP4/outputs/System2/11.5_10000.0.csv",
            "TP4/outputs/System2/12.0_10000.0.csv"]


#omegas = [8.0, 9.0, 10.0, 11.0, 12.0]
omegas = [8.0,8.5, 9.0,9.5, 10.0,10.5, 11.0,11.5, 12.0]
ks = [100.0, 1000.0, 2000.0, 5000.0, 10000.0]
amplitudes100 = []
amplitudes1000 = []
amplitudes2000 = []
amplitudes5000 = []
amplitudes10000 = []

for i in range(len(omegas)):
    df = pd.read_csv(files100[i], delimiter=';')
    amplitudes100.append(df['a'].tolist())
    
for i in range(len(omegas)):
    df = pd.read_csv(files1000[i], delimiter=';')
    amplitudes1000.append(df['a'].tolist())

for i in range(len(omegas)):
    df = pd.read_csv(files2000[i], delimiter=';')
    amplitudes2000.append(df['a'].tolist())

for i in range(len(omegas)):
    df = pd.read_csv(files5000[i], delimiter=';')
    amplitudes5000.append(df['a'].tolist())

for i in range(len(omegas)):
    df = pd.read_csv(files10000[i], delimiter=';')
    amplitudes10000.append(df['a'].tolist())    

max_amplitudes100 = []
max_amplitudes1000 = []
max_amplitudes2000 = []
max_amplitudes5000 = []
max_amplitudes10000 = []
for i in range(len(omegas)):
    max_amplitudes100.append(max(amplitudes100[i]))
    max_amplitudes1000.append(max(amplitudes1000[i]))
    max_amplitudes2000.append(max(amplitudes2000[i]))
    max_amplitudes5000.append(max(amplitudes5000[i]))
    max_amplitudes10000.append(max(amplitudes10000[i]))
    
max_amplitudesk = []
for k in ks:
    f = "TP4/outputs/System2/10.0_"+str(k)+".csv"
    df = pd.read_csv(f,delimiter=';')
    max_amplitudesk.append(max(df['a'].tolist()))
    


def graphOnlyOne(maxes,omegas):
    plt.plot(omegas, maxes, color='red', marker='o',label='k = 100')
    plt.xlabel('Omega [rad/s]')
    plt.ylabel('Amplitude [m]')
    plt.savefig('TP4/outputs/System2/amplitudes_100.png')

def graphAll(maxes1,maxes2,maxe3,maxes4,maxes5,omegas):
    plt.plot(omegas, maxes1, color='red', label='k = 100')
    plt.plot(omegas, maxes2, color='blue', label='k = 1000')
    plt.plot(omegas, maxe3, color='green', label='k = 2000')
    plt.plot(omegas, maxes4, color='yellow', label='k = 5000')
    plt.plot(omegas, maxes5, color='purple', label='k = 10000')
    plt.xlabel('Omega [rad/s]')
    plt.ylabel('Amplitude [m]')
    plt.legend()
    plt.savefig('TP4/outputs/System2/amplitudes.png')
    
def graphKVsA(maxes,ks):
    plt.plot(ks, maxes, color='red', marker='o',label='k = 100')
    plt.xlabel('k [kg/s^2]')
    plt.ylabel('Amplitude [m]')
    plt.savefig('TP4/outputs/System2/amplitudes_100.png')

graphOnlyOne(max_amplitudes100,omegas)

    