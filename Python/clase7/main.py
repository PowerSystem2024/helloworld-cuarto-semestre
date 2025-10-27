import utils
import read_csv
import charts
import pandas as pd

def run():
  '''
  data = read_csv.read_csv('/app/data.csv') # leemos el csv, gracias al módulo línea 2
  data = list(filter(lambda item : item['Continent'] == 'South America',data))

  countries = list(map(lambda x: x['Country'], data))
  percentages = list(map(lambda x: x['World Population Percentage'], data))
  charts.generate_pie_chart(countries, percentages)
  '''
  data = pd.read_csv('cuarto-semestre/Python/clase7/data.csv').to_dict('records')
  df = [item for item in data if item['Continent'] == 'Africa']
  countries = [x['Country'] for x in df]
  percentages = [x['World Population Percentage'] for x in df]
  charts.generate_pie_chart(countries, percentages)
  
  country = input('Type Country => ')
  result = utils.population_by_country(data, country)

  if len(result) > 0:
    country = result[0]
    labels, values = utils.get_population(country)
    charts.generate_bar_chart(country['Country'], labels, values)

if __name__ == '__main__':
  run()