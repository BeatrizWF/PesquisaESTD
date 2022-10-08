import pandas as pd
from matplotlib import pyplot as plt

plt.rcParams["figure.figsize"] = [7.00, 3.50]
plt.rcParams["figure.autolayout"] = True
 
columns = ['ÁrvoreAVl', 'ÁrvoreFlamenguista']

df = pd.read_csv("Result.csv", usecols=columns, sep=";")
df.plot()

plt.yscale("log")
plt.show()