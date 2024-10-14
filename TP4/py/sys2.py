import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# k3 = 2000
# k4 = 5000
# k5 = 25000
template = "TP4/outputs/System2/"
ext = ".csv"

# omegas = [5.0,6.0,7.0,8.0, 9.0, 10.0, 11.0, 12.0,13.0]
omegas1 = [8.0,8.5, 9.0,9.5, 10.0,10.5, 11.0,11.5, 12.0]
omegas2 = [28.0,28.5, 29.0,29.5, 30.0,30.5, 31.0,31.5, 32.0]
omegas3 = [42.0,42.5, 43.0,43.5, 44.0,44.5, 45.0,45.5, 46.0]
omegas4 = [68.0,68.5, 69.0,69.5, 70.0,70.5, 71.0,71.5, 72.0]
omegas5 = [98.0,98.5, 99.0,99.5, 100.0,100.5, 101.0,101.5, 102.0]
ks = [100.0, 1000.0, 2000.0, 5000.0, 10000.0]
amplitudes100 = []
amplitudes1000 = []
amplitudes2000 = []
amplitudes5000 = []
amplitudes10000 = []

for i in range(len(omegas1)):
    df = pd.read_csv(template+str(ks[0])+"_"+str(omegas1[i])+ext, delimiter=';')
    amplitudes100.append(df['a'].tolist())
    
for i in range(len(omegas1)):
    df = pd.read_csv(template+str(ks[1])+"_"+str(omegas2[i])+ext, delimiter=';')
    amplitudes1000.append(df['a'].tolist())

for i in range(len(omegas1)):
    df = pd.read_csv(template+str(ks[2])+"_"+str(omegas3[i])+ext, delimiter=';')
    amplitudes2000.append(df['a'].tolist())

for i in range(len(omegas1)):
    df = pd.read_csv(template+str(ks[3])+"_"+str(omegas4[i])+ext, delimiter=';')
    amplitudes5000.append(df['a'].tolist())

for i in range(len(omegas1)):
    df = pd.read_csv(template+str(ks[4])+"_"+str(omegas5[i])+ext, delimiter=';')
    amplitudes10000.append(df['a'].tolist())    

max_amplitudes100 = []
max_amplitudes1000 = []
max_amplitudes2000 = []
max_amplitudes5000 = []
max_amplitudes10000 = []
for i in range(len(omegas1)):
    max_amplitudes100.append(max(amplitudes100[i]))
    max_amplitudes1000.append(max(amplitudes1000[i]))
    max_amplitudes2000.append(max(amplitudes2000[i]))
    max_amplitudes5000.append(max(amplitudes5000[i]))
    max_amplitudes10000.append(max(amplitudes10000[i]))
    #print("MAXES: " + str(max(amplitudes100[i]))+ " OMEGA: " + str(omegas[i]) + "\n")

    


def graphOnlyOne(maxes,omegas,num):
    plt.plot(omegas, maxes, color='red', marker='o',label='k = '+str(num))
    plt.xlabel('Omega [rad/s]')
    plt.ylabel('Amplitude [m]')
    plt.legend()
    plt.grid()
    plt.savefig('TP4/outputs/System2/amplitudes_'+str(num)+'.png')
    # for i in range(len(maxes)):
    #     print("OMEGA: "+str(omegas[i]) + " MAX: " + str(maxes[i]))
    plt.close()
    

def graphAll(maxes1,maxes2,maxes3,maxes4,maxes5,omegas1,omegas2,omegas3,omegas4,omegas5):
    plt.plot(omegas1, maxes1, color='red', marker='o', label='k = 100')
    plt.plot(omegas2, maxes2, color='blue', marker='o', label='k = 1000')
    plt.plot(omegas3, maxes3, color='green', marker='o', label='k = 2000')
    plt.plot(omegas4, maxes4, color='yellow', marker='o', label='k = 5000')
    plt.plot(omegas5, maxes5, color='purple', marker='o', label='k = 10000')
    plt.xlabel('Omega [rad/s]')
    plt.ylabel('Amplitude [m]')
    plt.grid()
    plt.legend()
    plt.savefig('TP4/outputs/System2/amplitudes.png')
    plt.show()
    
def graphKVsW(maxes,ks):
    y = np.sqrt(ks)
    plt.figure(figsize=(10,5))
    #plt.plot(maxes,color='blue',marker='o')
    plt.plot(ks,y,color='blue',linestyle='--')
    plt.scatter(ks,maxes,color='red',marker='o')
    plt.ylabel('Omega [rad/s]')
    plt.xlabel('K [kg/s^2]')
    plt.savefig('TP4/outputs/System2/KvsW.png')
    plt.show()

# graphOnlyOne(max_amplitudes100,omegas1,100)
# graphOnlyOne(max_amplitudes1000,omegas2,1000)
# graphOnlyOne(max_amplitudes2000,omegas3,2000)
# graphOnlyOne(max_amplitudes5000,omegas4,5000)
# graphOnlyOne(max_amplitudes10000,omegas5,10000)
#graphAll(max_amplitudes100,max_amplitudes1000,max_amplitudes2000,max_amplitudes5000,max_amplitudes10000,omegas1,omegas2,omegas3,omegas4,omegas5)
maxes = [10.0,31.5,44.5,70.0,99.5]
graphKVsW(maxes,ks)



