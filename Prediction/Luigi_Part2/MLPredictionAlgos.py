import urllib
from urllib.request import urlopen
from bs4 import BeautifulSoup
import zipfile, io
import pandas as pd
from time import strptime
from datetime import datetime
from sklearn import linear_model
from sklearn.linear_model import LinearRegression,Ridge,Lasso, RandomizedLasso
import requests
from requests import session
import numpy as np
import math
from sklearn.feature_selection import RFE
from sklearn import preprocessing
from sklearn.metrics import mean_squared_error, mean_absolute_error
from sklearn.preprocessing import StandardScaler
from sklearn import *
from sklearn.model_selection import train_test_split
from sklearn.metrics import *
from IPython.display import HTML, display
#import statsmodels.api as sm
#from statsmodels.formula.api import ols
from sklearn.preprocessing import LabelEncoder
from sklearn.linear_model import LinearRegression
from sklearn.ensemble import RandomForestRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn import datasets, linear_model
from sklearn.neural_network import MLPRegressor
from sknn.mlp import Regressor, Layer


#Drop extra columns
def dropExtraColumns(df):
    df = df.drop(['earliest_cr_line_month','last_credit_pull_month','mo_sin_rcnt_rev_tl_op','title','id',
                        'mths_since_recent_bc','num_accts_ever_120_pd','num_actv_bc_tl',
                        'num_actv_rev_tl','num_bc_sats','num_bc_tl','num_il_tl','num_op_rev_tl','num_rev_accts',
                        'num_rev_tl_bal_gt_0','num_tl_120dpd_2m','num_tl_30dpd','num_tl_90g_dpd_24m','num_tl_op_past_12m',
                        'pct_tl_nvr_dlq','emp_title','mo_sin_rcnt_tl','id','percent_bc_gt_75',
                        'total_rec_late_fee','zip_code'],axis=1)
    return df


# Derive new columns
def deriveColumn(df):
    df['credit_history'] = (df['last_credit_pull_year'] - df['earliest_cr_line_year'])
    df['grade_On_Inquiries'] = np.where((df['inq_last_6mths'] == 0), 'A',
                                        np.where(df['inq_last_6mths'].between(1, 2), 'B',
                                                 np.where(df['inq_last_6mths'].between(3, 6), 'C',
                                                          np.where(df['inq_last_6mths'].between(7, 10), 'D', 'E'))))
    df = df[df.loan_status_num != 8]
    df.loan_status_num.fillna(1, inplace=True)
    df['UPB'] = (df['loan_amnt'] - df['total_rec_prncp']+df['total_rec_int'])
    return df

#Model Metrics dataframe


def ModelMetrics(modelname, model, X_train, y_train, X_test, y_test):
    model_metric = pd.DataFrame(
        {'AcceptedLoan_Model': [], 'RMS_train': [], 'RMS_test': [], 'MAE_train': [], 'MAE_test': [], 'MAPE_train': [],
         'MAPE_test': []})
    RMSE_dict = {}
    ytrain_pred = model.predict(X_train)
    ytest_pred = model.predict(X_test)
    RMS_train = mean_squared_error(y_train, ytrain_pred)
    RMS_test = mean_squared_error(y_test, ytest_pred)
    MAE_train = mean_absolute_error(y_train, ytrain_pred)
    MAE_test = mean_absolute_error(y_test, ytest_pred)
    MAPE_train = np.mean(np.abs((y_train - ytrain_pred) / y_train)) * 100
    MAPE_test = np.mean(np.abs((y_test - ytest_pred) / y_test)) * 100
    RMSE_dict[modelname] = RMS_test
    df_metrics = pd.DataFrame({'AcceptedLoan_Model':[modelname],'RMS_train':[RMS_train],'RMS_test': [RMS_test],'MAE_train': [MAE_train],'MAE_test':[MAE_test],
                               'MAPE_train':[MAPE_train],'MAPE_test':[MAPE_test]})
    # model_metric
    model_metric = pd.concat([model_metric, df_metrics])
    print("Completed",modelname)
    return model_metric

def SplitTrainTest(df,feature_cols):
    # Split into training and testing datasets
    X = df[feature_cols]
    y = df.int_rate
    X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)
    return (X_train, X_test, y_train, y_test)

#Linear Regression
def linearRegression(X_train,y_train,X_test,y_test, myfile):
    myfile.write("Linear Regression model computation starts")
    myfile.write('\n')
    lm_model = LinearRegression()
    lm_model.fit(X_train, y_train)
    print("Coefficient is:",lm_model.coef_,file=myfile)
    print("Intercept is:",lm_model.intercept_,file=myfile)
    train_pred = lm_model.predict(X_train)
    print("R-Square Training",r2_score(y_train,train_pred),file=myfile)
    test_pred = lm_model.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred),file=myfile)
    model_metric = ModelMetrics('Regression',lm_model, X_train, y_train, X_test, y_test)
    print(model_metric,file=myfile)
    return('Linear Regression',lm_model)

##Random Forest Algorithm
def randomForest(X_train,y_train,X_test,y_test, myfile):
    print("Random Forest computation starts",file=myfile)
    rForest = RandomForestRegressor(n_estimators=20)
    rForest.fit(X_train, y_train)
    train_pred = rForest.predict(X_train)
    print("R-Square Training",r2_score(y_train,train_pred),file=myfile)
    test_pred = rForest.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred),file=myfile)
    model_metric = ModelMetrics('Random Forest',rForest, X_train, y_train, X_test, y_test)
    print(model_metric,file=myfile)
    return('Random Forest',rForest)

##KNN Algorithm
def knnRegression(X_train, y_train, X_test, y_test, myfile):
    print("Knn Regression computation starts",file=myfile)
    knn = KNeighborsRegressor(n_neighbors=3)
    knn.fit(X_train,y_train)
    test_pred = knn.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred),file=myfile)
    model_metric = ModelMetrics('KNN',knn, X_train, y_train, X_test, y_test)
    print(model_metric,file=myfile)
    return('KNN',knn)

def NeuralNetwork(X_train, y_train,X_test,y_test, myfile):
    print("Neural Network computation starts",file=myfile)
    scaler = StandardScaler()
    # Fit training data
    scaler.fit(X_train)
    X_train = scaler.transform(X_train)
    X_test = scaler.transform(X_test)
    nn = MLPRegressor()
    nn.fit(X_train,y_train)
    test_pred = nn.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred),file=myfile)
    model_metric = ModelMetrics('Neural Network',nn, X_train, y_train, X_test, y_test)
    print(model_metric,file=myfile)
    return('Neural Network',nn)
