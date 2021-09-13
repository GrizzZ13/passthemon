import pandas as pd


def read_test_result(filename):
    names = ['threadName', 'success']
    data_set = pd.read_csv(filename, usecols=names)
    data_set['methodName'] = data_set['threadName'].str.split(' ').str[0]
    data_set.drop('threadName', axis=1, inplace=True)
    print(data_set)
    return data_set


def statistics(data_set):
    names = data_set.groupby('methodName')['success'].value_counts().to_frame()
    print(names)
    return names


data_set = read_test_result('E:\\SE-128\\PassThemOnPerformanceTest\\order-test\\testdata-5-2000.csv')
result = statistics(data_set)
