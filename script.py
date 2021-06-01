import json
from fuzzywuzzy import fuzz 
from fuzzywuzzy import process
import pandas as pd
import numpy as np
from firebase import Firebase
from os.path import dirname, join
import urllib


config = {
    "apiKey": "AIzaSyCc2ZyjPlRVXstiaH0_blZoda-Z6O1DQJY",
    "authDomain": "834243793844-9ctfmflmlarklg2f5vevchtssi7bcs95.apps.googleusercontent.com",
    "databaseURL": "https://pythontrial3-default-rtdb.firebaseio.com",
    "project_id": "pythontrial3",
    "storageBucket": "pythontrial3.appspot.com",
    "serviceAccount": join(dirname(__file__), "serviceAccountKey.json")
}

firebase_storage = Firebase(config)
storage = firebase_storage.storage()

url = storage.child("data/IndianFoodDatasetCSV.json").get_url(None)
json_file = urllib.request.urlopen(url).read()
# filename = join(dirname(__file__), ab)
df = pd.read_json(json_file)
df = df[['TranslatedRecipeName','TranslatedIngredients', 'TranslatedInstructions', 'URL']]
list1 = []
def prediction(input_):
    

    recepie_list = df['TranslatedRecipeName'].unique()
    query = input_
    choices = recepie_list

    out_put = process.extract(query, choices)
    # print(out_put)

    for i in out_put:
        list1.append(i[0])

    return list1



def show_recepie(num):
    i = int(num)
    return (df.loc[df['TranslatedRecipeName'] == str(list1[i]),'TranslatedInstructions'].iloc[0])
    
def show_ingredients(num):
    i = int(num)
    return (df.loc[df['TranslatedRecipeName'] == str(list1[i]),'TranslatedIngredients'].iloc[0])

# print(prediction('Gram flour, ghee, sugar'))
# print(show_ingredients(1))
# print(show_recepie(1))
