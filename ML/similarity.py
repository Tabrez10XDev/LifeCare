import pandas as pd
import numpy as np
import re

def read_glove_vecs(file):
    with open(file, 'r') as f:
        words = set()
        word_to_vec_map = {}
        
        for line in f:
            line = line.strip().split()
            word = line[0]
            words.add(word)
            word_to_vec_map[word] = np.array(line[1:], dtype=np.float64)
            
    return words, word_to_vec_map

words, word_to_vec_map = read_glove_vecs('data/glove.6B.50d.txt') # replace file path with your location for 50-d embeddings

# for use later on; finds the cosine similarity b/w 2 vectors
def cosine_similarity(x, y):
    
    # Compute the dot product between x and y 
    dot = np.dot(x,y)
    # Compute the L2 norm of x 
    norm_x = np.sqrt(np.sum(x**2))
    # Compute the L2 norm of y
    norm_y = np.sqrt(np.sum(y**2))
    # Compute the cosine similarity
    cosine_similarity = dot/(norm_x * norm_y)
    

df = pd.read_excel('Disease_Symptoms.xlsx').drop('Count of Disease Occurrence', axis = 1).fillna(method = 'ffill')

df.Symptom = df.Symptom.map(lambda x: re.sub('^.*_', '', x))
df.Disease = df.Disease.map(lambda x: re.sub('^.*_', '', x))

df.Symptom = df.Symptom.map(lambda x: x.lower())
df.Disease = df.Disease.map(lambda x: x.lower())

df.Symptom = df.Symptom.map(lambda x: re.sub('(.*)\/(.*)', r'\1 \2', x))
df.Disease = df.Disease.map(lambda x: re.sub('(.*)\/(.*)', r'\1 \2', x))

df.Symptom = df.Symptom.map(lambda x: re.sub('(.*)\(.*\)(.*)', r'\1\2', x))
df.Disease = df.Disease.map(lambda x: re.sub('(.*)\(.*\)(.*)', r'\1\2', x))

df.Symptom = df.Symptom.map(lambda x: re.sub('\'', '', x))
df.Disease = df.Disease.map(lambda x: re.sub('\'', '', x))
df.Disease = df.Disease.map(lambda x: re.sub('\\xa0', ' ', x))


counts = {}
def remove(x):
    for i in x.split():
        if not i in word_to_vec_map.keys():
            counts[i] = counts.get(i, 0) + 1
df.Symptom.map(lambda x: remove(x))
df.Disease.map(lambda x: remove(x))

unrepresented_words = pd.DataFrame()
unrepresented_words['Words'] = counts.keys()
unrepresented_words['No. of Occurences'] = counts.values()
unrepresented_words.to_csv('Unrepresented Words.csv')

frame = pd.DataFrame(df.groupby(['Symptom', 'Disease']).size()).drop(0, axis = 1)
frame = frame.iloc[1:]

frame = frame.reset_index().set_index('Symptom')

counts = {}
for i in frame.index:
    counts[i] = counts.get(i, 0) + 1
	
import operator
sym, ct = zip(*sorted(counts.items(), key = operator.itemgetter(1), reverse = True))
sym_count = pd.DataFrame()
sym_count['Symptom'] = sym
sym_count['Count'] =  ct
sym_count.to_csv('Symptom Counts.csv')

[frame.drop(i, inplace = True) for i in frame.index if counts[i] < 6]
    
lst = []
frame.Disease.map(lambda x: lst.append(x))


couples_and_labels = []

import random
for i in frame.index.unique():
    a = list(frame.Disease.loc[i].values)
    for j in a:
        non_context = random.choice(list(set(lst) ^ set(a)))
        couples_and_labels.append((i, j, 1))
        couples_and_labels.append((i, non_context, 0))

b = random.sample(couples_and_labels, len(couples_and_labels))
symptom, disease, label = zip(*b)


x = '25_epochs_0.6_similarity_seems_better.npy'

new_vecs = np.load(x)

similarity_score = float(re.findall('\d{1,}\.\d{1,}', x)[0])



d = pd.read_csv('Dictionary.csv')
dic = {}
for i in d.index:
    dic[d.Key.loc[i]] = d.Values.loc[i]

symp = input('Enter symptom for which similar symptoms are to be found: ')
print ('\nThe similar symptoms are: ')

for i in set(symptom):
    if (cosine_similarity(new_vecs[dic[i]], new_vecs[dic[symp]])) > similarity_score:
        # remove the same symptom from the list of outputs
        if i != symp:
            print (i)