from sklearn.tree import DecisionTreeClassifier, export_graphviz
from sklearn.model_selection import train_test_split, cross_val_score
import pandas as pd
import numpy as np
import pickle


data = pd.read_csv("Scraped-Data/dataset_clean.csv", encoding ="ISO-8859-1")
df = pd.DataFrame(data)
df_1 = pd.get_dummies(df.Target)
df_s = df['Source']
df_pivoted = pd.concat([df_s,df_1], axis=1)
df_pivoted.drop_duplicates(keep='first',inplace=True)
cols = df_pivoted.columns
cols = cols[1:]
df_pivoted = df_pivoted.groupby('Source').sum()
df_pivoted = df_pivoted.reset_index()
df_pivoted.to_csv("Scraped-Data/df_pivoted.csv")


# df_pivoted = pd.read_csv("Scraped-Data/dataset_clean.csv")



x = df_pivoted[cols]
y = df_pivoted['Source']

x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.33, random_state=42)
print ("DecisionTree")
dt = DecisionTreeClassifier()
clf_dt=dt.fit(x,y)

filename = 'finalized_model.sav'
pickle.dump(dt, open(filename, 'wb'))
