import matplotlib.pyplot as plt
import os

def generate_bar_chart(name, labels, values):
  fig, ax = plt.subplots()
  ax.bar(labels, values)
  script_dir = os.path.dirname(__file__)
  imgs_dir = os.path.join(script_dir, 'imgs')
  os.makedirs(imgs_dir, exist_ok=True)
  plt.savefig(os.path.join(imgs_dir, f'{name}.png'))
  plt.close()

def generate_pie_chart(labels, values):
  fig, ax = plt.subplots()
  ax.pie(values, labels=labels)
  ax.axis('equal')
  plt.savefig('pie.png')
  plt.close()

if __name__ == '__main__':
  labels = ['a', 'b', 'c']
  values = [10, 40, 800]
  generate_bar_chart('bar_chart', labels, values)
  generate_pie_chart(labels, values)