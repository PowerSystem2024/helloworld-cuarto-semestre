import requests

def get_razas():
    r = requests.get('https://dog.ceo/api/breeds/list/all')
    # print(r.status_code)
    # print(r.text)
    # print(type(r.text))
    razas = r.json()
    for raza in razas.values():
        print(f'Raza: {raza}')